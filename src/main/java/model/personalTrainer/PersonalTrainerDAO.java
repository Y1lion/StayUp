package model.personalTrainer;

import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Personal trainer dao.
 */
public class PersonalTrainerDAO {
    /**
     * Personal trainer registration personal trainer.
     *
     * @param ub the ub
     * @return the personal trainer
     */
    public synchronized PersonalTrainer personalTrainerRegistration(UserBean ub){
        Connection conn =  null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sqlString = "INSERT INTO personalTrainer(email) VALUES(?)";
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
     * @param pt             the pt
     * @param newDescription the new description
     * @return the personal trainer
     */
    public synchronized PersonalTrainer changeDescription(PersonalTrainer pt, String newDescription){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = "SELECT * FROM personalTrainer WHERE email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pt.getUser().getEmail());

            ResultSet res = ps.executeQuery();

            if(res.next()) {
                PreparedStatement prepstat = conn.prepareStatement("UPDATE personalTrainer SET descrizione = ? WHERE email = ?");
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
     * @param pt       the pt
     * @param newYears the new years
     * @return the personal trainer
     */
    public synchronized PersonalTrainer changePTYears(PersonalTrainer pt, Integer newYears){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = "SELECT * FROM personalTrainer WHERE email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pt.getUser().getEmail());

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

    /**
     * Retrieve info personal trainer.
     *
     * @param email the email
     * @return the personal trainer
     */
    public synchronized PersonalTrainer retrieveInfo(String email){
        Connection conn = null;
        PreparedStatement ps = null;
        PersonalTrainer pt = new PersonalTrainer(new UserBeanDAO().recoverInfos(email));

        try {
            conn = ConnectionPool.getConnection();
            String sql = "SELECT * FROM personalTrainer WHERE email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet res = ps.executeQuery();

            if(res.next()) {
                pt.setPtYears(res.getInt("ptYears"));
                pt.setDescription(res.getString("descrizione"));
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

        return pt;
    }
    public synchronized List<PersonalTrainer> retrieveAll(){
        Connection conn = null;
        PreparedStatement ps = null;
        List<PersonalTrainer> pts=new ArrayList<>();
        try {
            conn = ConnectionPool.getConnection();
            String sql = "SELECT * FROM personalTrainer,user WHERE personalTrainer.email=user.email";
            ps = conn.prepareStatement(sql);

            ResultSet res = ps.executeQuery();

            while(res.next()) {
                PersonalTrainer pt=new PersonalTrainer(null,"",0);
                UserBean ub=new UserBean("null","null");
                ub.setEmail(res.getString("email"));
                ub.setGender(res.getString("gender"));
                ub.setNome(res.getString("name"));
                ub.setCognome(res.getString("surname"));
                ub.setTelefono(res.getString("telephone"));
                ub.setRole(res.getString("role"));
                ub.setPsw(res.getString("password"));
                pt.setDescription(res.getString("descrizione"));
                pt.setPtYears(res.getInt("ptYears"));
                pt.setUser(ub);
                pts.add(pt);
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

        return pts;
    }
    public synchronized void deletePT(String email){
        Connection conn = null;
        PreparedStatement prepstat1 = null;
        PreparedStatement prepstat2 = null;


        try {
            conn = ConnectionPool.getConnection();
            String sql1 = "DELETE FROM personalTrainer WHERE email = ?";
            prepstat1 = conn.prepareStatement(sql1);
            prepstat1.setString(1,email);
            int state1 = prepstat1.executeUpdate();
            String sql2 = "UPDATE user SET role = ? WHERE email = ?";
            prepstat2 = conn.prepareStatement(sql2);
            prepstat2.setString(1, "user");
            prepstat2.setString(2, email);
            int state2 = prepstat2.executeUpdate();

            if(state1 != 0 && state2 != 0) {
                System.out.println("Elemento eliminato con successo");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                prepstat1.close();
                prepstat2.close();
                ConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
