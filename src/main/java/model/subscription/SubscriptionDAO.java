package model.subscription;

import model.user.UserBean;
import model.personalTrainer.PersonalTrainer;
import model.utils.ConnectionPool;

import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class SubscriptionDAO {
    public synchronized Subscription addSubscription(UserBean ub, PersonalTrainer pt, Date dateEnd){
        if(ub==null || pt==null || dateEnd.before(new java.util.Date(System.currentTimeMillis())))
            return  null;
        Connection conn =  null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sqlString = "INSERT INTO subscription(emailPT, emailUser, dateStart, dateEnd, isActive) VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(sqlString);

            ps.setString(1, pt.getUser().getEmail());
            ps.setString(2, ub.getEmail());
            ps.setDate(3, new Date(System.currentTimeMillis()));
            ps.setDate(4, dateEnd);
            ps.setInt(5, 2);

            int upd = ps.executeUpdate();

            if(upd != 0) {
                Subscription sub = new Subscription(pt.getUser().getEmail(),ub.getEmail(),new java.sql.Date(System.currentTimeMillis()), dateEnd, 2);
                System.out.print("Registered");
                return sub;
            }
        }
        catch(SQLException e){
            e.printStackTrace();

        }
        finally {
            try {
                if(ps!=null) {
                    ps.close();
                    ConnectionPool.releaseConnection(conn);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public synchronized Subscription acceptSubscription(Subscription s){
        if(s==null)
            return null;
        Connection conn =  null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM subscription WHERE emailUser = ? AND emailPT = ? AND isActive = 2");
            ps.setString(1, s.getEmailUser());
            ps.setString(2, s.getEmailPt());
            Date today = new Date(System.currentTimeMillis());
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                ps = conn.prepareStatement("UPDATE subscription SET isActive = 1, dateEnd = ?, dateStart = ? WHERE emailUser = ? AND emailPT = ? AND dateStart = ? ");
                Date dateStart = resultSet.getDate("dateStart");
                Date dateEnd = resultSet.getDate("dateEnd");
                long diffInMonths = ChronoUnit.MONTHS.between(dateStart.toLocalDate(), dateEnd.toLocalDate());
                System.out.println("Differenza in mesi: " + diffInMonths);
                dateEnd = Date.valueOf(today.toLocalDate().plusMonths(diffInMonths));

                ps.setDate(1,dateEnd);
                ps.setDate(2,today);
                ps.setString(3, s.getEmailUser());
                ps.setString(4, s.getEmailPt());
                ps.setDate(5,s.getDateStart());
                int upd = ps.executeUpdate();
                if (upd != 0){
                    s.setActive(1);
                    s.setDateEnd(dateEnd);
                    s.setDateStart(today);
                    System.out.println("Subscription accepted");
                    return s;
                } else {
                    System.out.println("No changes");
                    return null;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();

        }
        finally {
            try {
                if(ps!=null) {
                    ps.close();
                    ConnectionPool.releaseConnection(conn);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return s;
    }
    public synchronized Subscription refuseSubscription(Subscription s){
        Connection conn =  null;
        PreparedStatement ps = null;
        if(s==null)
            return null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM subscription WHERE emailUser = ? AND emailPT = ? AND isActive = 2");
            ps.setString(1, s.getEmailUser());
            ps.setString(2, s.getEmailPt());
            Date today = new Date(System.currentTimeMillis());
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                ps = conn.prepareStatement("DELETE FROM subscription WHERE emailUser = ? AND emailPT = ? AND isActive = 2 ");
                ps.setString(1, s.getEmailUser());
                ps.setString(2, s.getEmailPt());
                int upd = ps.executeUpdate();
                if (upd != 0){
                    s.setActive(0);
                    System.out.println("Subscription refused");
                    return s;
                } else {
                    System.out.println("No changes");
                    return null;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                    ConnectionPool.releaseConnection(conn);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return s;
    }
    public synchronized Subscription getSubscription(UserBean ub){
        if(ub==null)
            return null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Subscription sub = new Subscription(ub.getEmail());
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM subscription WHERE emailUser = ?");
            ps.setString(1, sub.getEmailUser());

            ResultSet res = ps.executeQuery();

            if(res.next())
            {
                sub.setEmailPt(res.getString("emailPT"));
                sub.setActive(res.getInt("isActive"));
                sub.setDateStart(res.getDate("dateStart"));
                sub.setDateEnd(res.getDate("dateEnd"));
                return sub;
            }else{
                sub.setEmailUser("ERROR");
                return sub;
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }finally{
            try {
                if(ps!=null) {
                    ps.close();
                    ConnectionPool.releaseConnection(conn);
                }
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
        return null;
    }
    public synchronized ArrayList<Subscription> getAllSubscriptions(PersonalTrainer pt){
        if(pt==null)
            return null;
        Connection conn = null;
        PreparedStatement ps = null;
        ArrayList<Subscription> subs = new ArrayList<>();
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM subscription WHERE emailPT = ?");
            ps.setString(1, pt.getUser().getEmail());

            ResultSet res = ps.executeQuery();

            while(res.next())
            {
                subs.add(new Subscription(res.getString("emailPT"),res.getString("emailUser"), res.getDate("dateStart"), res.getDate("dateEnd"), res.getInt("isActive")));
            }
            return subs;

        } catch (SQLException e) {
            
            e.printStackTrace();
        }finally{
            try {
                if(ps!=null) {
                    ps.close();
                    ConnectionPool.releaseConnection(conn);
                }
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
        return null;
    }
}
