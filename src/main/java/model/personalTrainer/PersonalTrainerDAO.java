package model.personalTrainer;

import model.user.UserBean;
import model.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Personal trainer dao.
 */
public class PersonalTrainerDAO {
    /**
     * Personal trainer registration personal trainer.
     *
     * @param ub the user bean
     * @return the personal trainer
     */
    public synchronized PersonalTrainer personalTrainerRegistration(UserBean ub){
        Connection conn =  null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sqlString = new String("INSERT INTO personalTrainer(email) VALUES(?)");
            ps = conn.prepareStatement(sqlString);

            ps.setString(1, ub.getEmail());

            int upd = ps.executeUpdate();

            if(upd != 0) {
                PersonalTrainer pt = new PersonalTrainer(ub);
                System.out.print("Registered");
                ps.close();
                ConnectionPool.releaseConnection(conn);
                return pt;
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
            catch(Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Change description personal trainer.
     *
     * @param pt             the personal trainer
     * @param newDescription the new description
     * @return the personal trainer
     */
    public synchronized PersonalTrainer changeDescription(PersonalTrainer pt, String newDescription){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM personalTrainer WHERE email = ? AND password = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, pt.getUser().getEmail());
            ps.setString(2, pt.getUser().getPsw());

            ResultSet res = ps.executeQuery();

            if(res.next()) {
                PreparedStatement prepstat = conn.prepareStatement("UPDATE personalTrainer SET description = ? WHERE email = ?");
                prepstat.setString(1, newDescription);
                prepstat.setString(2, pt.getUser().getEmail());
                int state = prepstat.executeUpdate();

                if(state != 0) {
                    pt.setDescription(newDescription);
                    System.out.println("Description modified");
                    return pt;
                }
                else {
                    System.out.println("No changes");
                    return null;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                ps.close();
                ConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Change pt years personal trainer.
     *
     * @param pt       the personal trainer
     * @param newYears the new years
     * @return the personal trainer
     */
    public synchronized PersonalTrainer changePTYears(PersonalTrainer pt, Integer newYears){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM personalTrainer WHERE email = ? AND password = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, pt.getUser().getEmail());
            ps.setString(2, pt.getUser().getPsw());

            ResultSet res = ps.executeQuery();

            if(res.next()) {
                PreparedStatement prepstat = conn.prepareStatement("UPDATE personalTrainer SET ptYears = ? WHERE email = ?");
                prepstat.setInt(1, newYears);
                prepstat.setString(2, pt.getUser().getEmail());
                int state = prepstat.executeUpdate();

                if(state != 0) {
                    pt.setPtYears(newYears);
                    System.out.println("ptYears modified");
                    return pt;
                }
                else {
                    System.out.println("No changes");
                    return null;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                ps.close();
                ConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
