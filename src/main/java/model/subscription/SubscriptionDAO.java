package model.subscription;

import model.user.UserBean;
import model.personalTrainer.PersonalTrainer;
import model.utils.ConnectionPool;

import java.sql.*;

public class SubscriptionDAO {
    public synchronized Subscription addSubscription(UserBean ub, PersonalTrainer pt, Date dateEnd){
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
            ps.setBoolean(5, Boolean.TRUE);

            int upd = ps.executeUpdate();

            if(upd != 0) {
                Subscription sub = new Subscription(pt.getUser().getEmail(),ub.getEmail(),new java.sql.Date(System.currentTimeMillis()), dateEnd, Boolean.TRUE);
                System.out.print("Registered");
                ps.close();
                ConnectionPool.releaseConnection(conn);
                return sub;
            }
        }
        catch(SQLException e){
            e.printStackTrace();

        }
        finally {
            try {
                ps.close();
                ConnectionPool.releaseConnection(conn);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public synchronized Subscription getSubscription(UserBean ub){
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
                sub.setActive(res.getBoolean("isActive"));
                sub.setDateStart(res.getDate("dateStart"));
                sub.setDateEnd(res.getDate("dateEnd"));
                return sub;
            }else{
                sub.setEmailUser("ERROR");
                return sub;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                ConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
