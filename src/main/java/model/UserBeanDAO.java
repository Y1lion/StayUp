package model;

import java.sql.*;
import java.util.ArrayList;

public class UserBeanDAO {
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
    public synchronized UserBean UserRegistration(String email, String password, String name, String surname, String telephone, String gender, Integer workoutYears){
        Connection conn =  null;
        PreparedStatement ps = null;

        try {
            System.out.println(password);
            conn = ConnectionPool.getConnection();
            String sqlString = new String("INSERT INTO user(email,password,name,surname,telephone,gender,workoutYears) VALUES(?,?,?,?,?,?,?)");
            ps = conn.prepareStatement(sqlString);

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.setString(5, telephone);
            ps.setString(6, gender);
            ps.setInt(7, workoutYears);

            int upd = ps.executeUpdate();

            if(upd != 0) {
                UserBean ub = new UserBean(email, password);
                ub.setNome(name);
                ub.setCognome(surname);
                ub.setRole("user");
                System.out.print("Registrazione effettuata con successo");
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
}