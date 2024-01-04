package model;

import model.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParametersDAO {
    public synchronized Parameters getParameters(String email){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Parameters param = null;
        try{
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM parameters where email = ?");
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                param = new Parameters(email);
                param.setWeight(resultSet.getDouble("weight"));
                param.setLean_mass(resultSet.getDouble("lean_mass"));
                param.setFat_mass(resultSet.getDouble("fat_mass"));
                param.setArm_mis(resultSet.getDouble("arm_mis"));
                param.setLeg_mis(resultSet.getDouble("leg_mis"));
                param.setChest_mis(resultSet.getDouble("chest_mis"));
                param.setHips_mis(resultSet.getDouble("hips_mis"));
                param.setShoulders_mis(resultSet.getDouble("shoulders_mis"));
                param.setWorkoutYears(resultSet.getInt("workoutYears"));
                return param;
            }else{
                param = new Parameters("ERROR");
                return param;
            }
        }catch(SQLException e){
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                ConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
    public synchronized Parameters setParameters(String email, ArrayList<Double> params){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO parameters values (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,email);
            int i = 2;
            for(Double p : params){
                if (i == 10) {
                    preparedStatement.setInt(i, p.intValue());
                } else {
                    preparedStatement.setDouble(i, p);
                }
                i++;
            }
            int upd = preparedStatement.executeUpdate();

            if(upd != 0) {
                Parameters param = new Parameters(email);
                param.setWeight(params.get(0));
                param.setLean_mass(params.get(1));
                param.setFat_mass(params.get(2));
                param.setArm_mis(params.get(3));
                param.setLeg_mis(params.get(4));
                param.setChest_mis(params.get(5));
                param.setHips_mis(params.get(6));
                param.setShoulders_mis(params.get(7));
                param.setWorkoutYears(params.get(8).intValue());
                System.out.print("Parameters registered");
                preparedStatement.close();
                ConnectionPool.releaseConnection(connection);
                return param;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                ConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public synchronized Parameters changeParameters(Parameters params, Double newParam, String param){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getConnection();
            String query = "UPDATE parameters SET"+ param +" = ? WHERE email = ?";
            preparedStatement = connection.prepareStatement(query);
            if(param.equalsIgnoreCase("workoutYears")) {
                preparedStatement.setInt(1, newParam.intValue());
            } else {
                preparedStatement.setDouble(1, newParam);
            }
            preparedStatement.setString(2, params.getEmail());
            int state = preparedStatement.executeUpdate();

            if(state != 0) {
                params.setWeight(newParam);
                System.out.println(param+" changed");

                return params;
            }else {
                System.out.println("No changes");
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                ConnectionPool.releaseConnection(connection);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
