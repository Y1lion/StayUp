package model.user;

import model.personalTrainer.PersonalTrainerDAO;
import model.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User bean dao.
 */
public class UserBeanDAO {
    /**
     * Login user user bean.
     *
     * @param email    the email
     * @param password the password
     * @return the user bean
     */
//TODO: Admin check and approve request
    public synchronized UserBean loginUser(String email, String password){ //ricerco nel db l'utente con email e psw dati in input

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            UserBean ub = new UserBean(email, password);
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet res = ps.executeQuery();


            // 4. Prendi il risultato
            if(res.next())
            {
                ub.setNome(res.getString("name"));
                ub.setCognome(res.getString("surname"));
                ub.setRole(res.getString("role"));
                ub.setTelefono(res.getString("telephone"));
                if (res.getString("gender").equalsIgnoreCase("m"))
                    ub.setGender("Male");
                else if (res.getString("gender").equalsIgnoreCase("f"))
                    ub.setGender("Female");
                else ub.setGender("Other");

                return ub;
            }else{
                ub.setEmail("ERRORE");
                return ub;
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

    /**
     * Change email user bean.
     *
     * @param ub       the user bean
     * @param oldemail the old email
     * @param newemail the new email
     * @return the user bean
     */
    public synchronized UserBean changeEmail(UserBean ub, String oldemail, String newemail) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM user WHERE email = ? AND password = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, ub.getEmail());
            ps.setString(2, ub.getPsw());

            ResultSet res = ps.executeQuery();
            String checkOld = null;

            if(res.next()) {
                checkOld = res.getString("email");
                System.out.println("test old and new");
                System.out.println(oldemail);
                System.out.println(checkOld);
                if(checkOld.contentEquals(oldemail)) {
                    PreparedStatement prepstat = conn.prepareStatement("UPDATE user SET email = ? WHERE email = ?");
                    prepstat.setString(1, newemail);
                    prepstat.setString(2, oldemail);
                    int state = prepstat.executeUpdate();

                    if(state != 0) {
                        ub.setEmail(newemail);
                        System.out.println("email modificata");

                        return ub;
                    }
                    else {
                        System.out.println("No changes");
                        return null;
                    }

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
     * Change name user bean.
     *
     * @param ub         the ub
     * @param newName    the new name
     * @param newSurname the new surname
     * @return the user bean
     */
    public synchronized UserBean changeName(UserBean ub, String newName, String newSurname) {
        Connection conn = null;
        PreparedStatement ps = null;
        UserBean testing = new UserBean(ub.getEmail(),"testing");
        testing.setNome(newName);
        testing.setCognome(newSurname);
        testing.setTelefono("1234567890");
        testing.setGender("m");
        if (!checkFormat(testing))
            return null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM user WHERE email = ? AND password = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, ub.getEmail());
            ps.setString(2, ub.getPsw());

            ResultSet res = ps.executeQuery();
            if(res.next()) {
                PreparedStatement prepstat = conn.prepareStatement("UPDATE user SET name = ?, surname = ? WHERE email = ?");
                prepstat.setString(1, newName);
                prepstat.setString(2, newSurname);
                prepstat.setString(3, ub.getEmail());
                int state = prepstat.executeUpdate();

                if(state != 0) {
                    ub.setNome(newName);
                    ub.setCognome(newSurname);
                    System.out.println("name edit");

                    return ub;
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
                if (ps != null) {
                    ps.close();
                    ConnectionPool.releaseConnection(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Change number user bean.
     *
     * @param ub        the ub
     * @param newNumber the new number
     * @return the user bean
     */
    public synchronized UserBean changeNumber(UserBean ub, String newNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        UserBean testing = new UserBean(ub.getEmail(),"testing");
        testing.setNome("Testing");
        testing.setCognome("Testing");
        testing.setTelefono(newNumber);
        testing.setGender("m");
        if (!checkFormat(testing))
            return null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM user WHERE email = ? AND password = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, ub.getEmail());
            ps.setString(2, ub.getPsw());

            ResultSet res = ps.executeQuery();
            if(res.next()) {
                PreparedStatement prepstat = conn.prepareStatement("UPDATE user SET telephone = ? WHERE email = ?");
                prepstat.setString(1, newNumber);
                prepstat.setString(2, ub.getEmail());
                int state = prepstat.executeUpdate();

                if(state != 0) {
                    ub.setTelefono(newNumber);
                    System.out.println("number edit");

                    return ub;
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
                if (ps != null) {
                    ps.close();
                    ConnectionPool.releaseConnection(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Change gender user bean.
     *
     * @param ub        the ub
     * @param newGender the new gender
     * @return the user bean
     */
    public synchronized UserBean changeGender(UserBean ub, String newGender) {
        Connection conn = null;
        PreparedStatement ps = null;
        UserBean testing = new UserBean(ub.getEmail(),"testing");
        testing.setNome("Testing");
        testing.setCognome("Testing");
        testing.setTelefono("1234567890");
        testing.setGender(newGender);
        if (!checkFormat(testing))
            return null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM user WHERE email = ? AND password = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, ub.getEmail());
            ps.setString(2, ub.getPsw());

            ResultSet res = ps.executeQuery();
            if(res.next()) {
                PreparedStatement prepstat = conn.prepareStatement("UPDATE user SET gender = ? WHERE email = ?");
                prepstat.setString(1, newGender);
                prepstat.setString(2, ub.getEmail());
                int state = prepstat.executeUpdate();

                if(state != 0) {
                    ub.setGender(newGender);
                    System.out.println("gender edit");

                    return ub;
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
     * Recover infos user bean.
     *
     * @param email the email
     * @return the user bean
     */
    public synchronized UserBean recoverInfos(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        UserBean ub = null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = "SELECT * FROM user WHERE email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet res = ps.executeQuery();
            if(res.next()) {
                ub = new UserBean(email,"fake");
                ub.setNome(res.getString("name"));
                ub.setCognome(res.getString("surname"));
                ub.setRole(res.getString("role"));
                ub.setGender(res.getString("gender"));
                ub.setTelefono(res.getString("telephone"));
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

        return ub;
    }

    /**
     * Change psw user bean.
     *
     * @param ub     the user bean
     * @param oldpsw the old password
     * @param newpsw the new password
     * @return the user bean
     */
    public synchronized UserBean changePsw(UserBean ub, String oldpsw, String newpsw) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM user WHERE email = ? AND password = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, ub.getEmail());
            ps.setString(2, ub.getPsw());

            ResultSet res = ps.executeQuery();
            String checkOld = null;

            if(res.next()) {
                checkOld = res.getString("password");

                if(checkOld.contentEquals(oldpsw)) {
                    PreparedStatement prepstat = conn.prepareStatement("UPDATE user SET password = ? WHERE email = ? AND password = ?");
                    prepstat.setString(1, newpsw);
                    prepstat.setString(2, ub.getEmail());
                    prepstat.setString(3, oldpsw);
                    int state = prepstat.executeUpdate();

                    if(state != 0) {
                        ub.setPsw(newpsw);
                        System.out.println("psw modificata");

                        return ub;
                    }
                    else {
                        System.out.println("No changes");
                        return null;
                    }

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
     * Forgot psw user bean.
     *
     * @param ub     the user bean
     * @param newpsw the new password
     * @return the user bean
     */
    public synchronized UserBean forgotPsw(UserBean ub, String newpsw) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM user WHERE email = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, ub.getEmail());
            ResultSet res = ps.executeQuery();
            if(res.next()) {
                PreparedStatement prepstat = conn.prepareStatement("UPDATE user SET password = ? WHERE email = ?");
                prepstat.setString(1, newpsw);
                prepstat.setString(2, ub.getEmail());
                int state = prepstat.executeUpdate();

                if(state != 0) {
                    ub.setPsw(newpsw);
                    System.out.println("psw modificata");

                    return ub;
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
     * User registration user bean.
     *
     * @param email     the email
     * @param password  the password
     * @param name      the name
     * @param surname   the surname
     * @param telephone the telephone
     * @param gender    the gender
     * @return the user bean
     */
    public synchronized UserBean userRegistration(String email, String password, String name, String surname, String telephone, String gender){
        Connection conn =  null;
        PreparedStatement ps = null;

        try {
            System.out.println(password);
            conn = ConnectionPool.getConnection();
            String sqlString = new String("INSERT INTO user(email,password,name,surname,telephone,gender) VALUES(?,?,?,?,?,?)");
            ps = conn.prepareStatement(sqlString);

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.setString(5, telephone);
            ps.setString(6, gender);

            int upd = ps.executeUpdate();

            if(upd != 0) {
                UserBean ub = new UserBean(email, password);
                ub.setNome(name);
                ub.setCognome(surname);
                ub.setRole("user");
                System.out.println("Registrazione effettuata con successo");
                ps.close();
                ConnectionPool.releaseConnection(conn);
                return ub;
            }
        }
        catch(SQLException e){
            if(e.getErrorCode() == 1062) {
                return new UserBean("duplicato","duplicato");
            }

        }
        finally {
            try {
                /*ps.close();
                ConnectionPool.releaseConnection(conn);*/
                //conn.commit();
            }
            catch(Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Check email user bean.
     *
     * @param email the email
     * @return the user bean
     */
    public synchronized UserBean checkEmail(String email){

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            UserBean ub = new UserBean(email,"fasulla");
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            ps.setString(1, email);

            ResultSet res = ps.executeQuery();


            // 4. Prendi il risultato
            if(res.next())
            {
                ub.setNome(res.getString("name"));
                ub.setCognome(res.getString("surname"));
                return ub;
            }else{
                ub.setEmail("ERRORE");
                return ub;
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

    /**
     * Delete user.
     *
     * @param uEmail the user email
     */
    public synchronized void deleteUser(String uEmail) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("DELETE FROM user WHERE email = ?");
            ps.setString(1, uEmail);

            int status = ps.executeUpdate();

            if(status != 0) {
                System.out.println("Elemento eliminato con successo");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                ps.close();
                ConnectionPool.releaseConnection(conn);
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * All users array list.
     *
     * @return the array list
     */
    public synchronized ArrayList<UserBean> allUsers(){

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            ArrayList<UserBean> allUsers=new ArrayList<>();
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT email FROM user");
            ResultSet res = ps.executeQuery();
            int i=0;
            while(res.next())
            {
                allUsers.add(new UserBean(res.getString("email"),"fasulla"));
                System.out.println(allUsers.get(i).getEmail());
                System.out.println(i);
                i++;
            }
            return allUsers;
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

    /**
     * Request role pt user bean.
     *
     * @param ub the user bean
     * @return the user bean
     */
    public synchronized UserBean requestRolePT(UserBean ub){
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = ConnectionPool.getConnection();
            String sql = new String("SELECT * FROM user WHERE email = ? AND password = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, ub.getEmail());
            ps.setString(2, ub.getPsw());

            ResultSet res = ps.executeQuery();
            if(res.next()){
                PreparedStatement prepstat = conn.prepareStatement("UPDATE user SET role = ? WHERE email = ? AND password = ?");
                prepstat.setString(1, "PTR");
                prepstat.setString(2, ub.getEmail());
                prepstat.setString(3, ub.getPsw());
                int state = prepstat.executeUpdate();

                if(state != 0) {
                    ub.setRole("PTR");
                    System.out.println("Role updated");

                    return ub;
                }
                else {
                    System.out.println("No changes");
                    return null;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{
                ps.close();
                ConnectionPool.releaseConnection(conn);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return ub;
    }

    public synchronized List<UserBean> retrieveAllPending(){

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            List<UserBean> allUsers=new ArrayList<>();
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM user WHERE role = ?");
            ps.setString(1,"PTR");
            ResultSet res = ps.executeQuery();
            int i=0;
            while(res.next())
            {
                UserBean ub=new UserBean(res.getString("email"),"fasulla");
                ub.setRole(res.getString("role"));
                ub.setNome(res.getString("name"));
                ub.setCognome(res.getString("surname"));
                allUsers.add(ub);
                System.out.println(allUsers.get(i));
                i++;
            }
            return allUsers;
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

    public synchronized void upgradeToPT(UserBean ub){

        Connection conn = null;
        PreparedStatement ps1 = null;

        try {
            ArrayList<UserBean> allUsers=new ArrayList<>();
            UserBean upgradeUser=ub;
            conn = ConnectionPool.getConnection();
            ps1 = conn.prepareStatement("UPDATE user SET role = ? WHERE email = ?");
            ps1.setString(1,"PT");
            ps1.setString(2,ub.getEmail());
            int state1 = ps1.executeUpdate();
            new PersonalTrainerDAO().personalTrainerRegistration(ub);
            if (state1!=0)
                System.out.println("Upgrade da user a personal trainer completato!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                ps1.close();
                ConnectionPool.releaseConnection(conn);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private synchronized Boolean checkFormat(UserBean ub){
        if (!ub.getNome().matches("^[A-Z][a-zA-Z]{1,50}$"))
            return false;
        if (!ub.getCognome().matches("^[A-Z][a-zA-Z]{1,50}$"))
            return false;
        if (!ub.getTelefono().matches("\\d{10}"))
            return false;
        if (!ub.getGender().equalsIgnoreCase("m") && !ub.getGender().equalsIgnoreCase("f") && !ub.getGender().equalsIgnoreCase("o"))
            return false;
        return true;
    }
}