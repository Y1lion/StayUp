package model.trainingPlan;

import model.personalTrainer.PersonalTrainer;
import model.user.UserBean;
import model.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public class TrainingPlanDAO {
    //TODO: ADD TRAINING PLAN BUT BEFORE TODO FORM FOR TRAINING PLAN
    public synchronized Boolean addTrainingPlan(String emailPT, String emailUser, String exercises, Date dateStart, Date dateEnd){
        Connection conn =  null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionPool.getConnection();
            String sqlString = "INSERT INTO trainingPlan(emailUser, emailPT, exercises, dateStart, dateEnd) VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(sqlString);

            ps.setString(1, emailUser);
            ps.setString(2, emailPT);
            ps.setString(3, exercises);
            ps.setDate(4, dateStart);
            ps.setDate(5, dateEnd);

            int upd = ps.executeUpdate();

            if(upd != 0) {
                System.out.print("Added training plan");
                return true;
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
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public synchronized Boolean deleteTrainingPlan(String emailUser, String emailPT, String exercisesString){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("DELETE FROM trainingPlan WHERE emailUser = ? AND emailPT = ? AND JSON_CONTAINS(exercises,?)");
            ps.setString(1, emailUser);
            ps.setString(2, emailPT);
            ps.setString(3, exercisesString);

            int res = ps.executeUpdate();
            return res != 0;
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
        return false;
    }
    public synchronized ArrayList<TrainingPlan> getAvailableTrainingPlan(UserBean ub){
        Connection conn = null;
        PreparedStatement ps = null;
        ArrayList<TrainingPlan> trainingPlans = new ArrayList<>();
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM trainingPlan WHERE emailUser = ? AND dateEnd > ?");
            ps.setString(1, ub.getEmail());
            ps.setDate(2, new Date(System.currentTimeMillis()));

            ResultSet res = ps.executeQuery();

            while(res.next())
            {
                trainingPlans.add(new TrainingPlan(res.getString("emailUser"),res.getString("emailPT"), res.getString("exercises"), res.getDate("dateStart"), res.getDate("dateEnd")));
            }
            return  trainingPlans;

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
    public synchronized ArrayList<TrainingPlan> getAvailablePtTrainingPLan(UserBean ub, PersonalTrainer pt){
        Connection conn = null;
        PreparedStatement ps = null;
        ArrayList<TrainingPlan> trainingPlans = new ArrayList<>();
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM trainingPlan WHERE emailUser = ? AND emailPT = ? AND dateEnd > ?");
            ps.setString(1, ub.getEmail());
            ps.setString(2, pt.getUser().getEmail());
            ps.setDate(3, new Date(System.currentTimeMillis()));

            ResultSet res = ps.executeQuery();

            while(res.next())
            {
                trainingPlans.add(new TrainingPlan(res.getString("emailUser"),res.getString("emailPT"), res.getString("exercises"), res.getDate("dateStart"), res.getDate("dateEnd")));
            }
            return  trainingPlans;

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
    public synchronized ArrayList<TrainingPlan> getAllTrainingPlans(PersonalTrainer pt){
        Connection conn = null;
        PreparedStatement ps = null;
        ArrayList<TrainingPlan> trainingPlans = new ArrayList<>();
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM trainingPlan WHERE emailPT = ?");
            ps.setString(1, pt.getUser().getEmail());

            ResultSet res = ps.executeQuery();

            while(res.next())
            {
                trainingPlans.add(new TrainingPlan(res.getString("emailUser"),res.getString("emailPT"), res.getString("exercises") ,res.getDate("dateStart"), res.getDate("dateEnd")));
            }
            return trainingPlans;

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
    public synchronized ArrayList<TrainingPlan> getAllUserTrainingPlans(UserBean ub){
        Connection conn = null;
        PreparedStatement ps = null;
        ArrayList<TrainingPlan> trainingPlans = new ArrayList<>();
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM trainingPlan WHERE emailUser = ?");
            ps.setString(1, ub.getEmail());

            ResultSet res = ps.executeQuery();

            while(res.next())
            {
                trainingPlans.add(new TrainingPlan(res.getString("emailUser"),res.getString("emailPT"), res.getString("exercises") ,res.getDate("dateStart"), res.getDate("dateEnd")));
            }
            return trainingPlans;

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
