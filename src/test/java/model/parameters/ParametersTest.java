package model.parameters;

import model.user.UserBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {
    @Test
    void testParameters(){
        Double weight = 60.0;
        Double lean_mass = 20.0;
        Double fat_mass = 30.0;
        Double arm_mis = 40.0;
        Double leg_mis = 50.0;
        Double chest_mis = 60.0;
        Double hips_mis = 70.0;
        Double shoulders_mis = 80.0;
        Integer workoutYears = 6;
        String email = "gianluca@gmail.com";
        Parameters param = new Parameters(email);
        Parameters param2 = new Parameters(email);
        assertNotNull(param);
        param.setWeight(weight);
        param.setLean_mass(lean_mass);
        param.setFat_mass(fat_mass);
        param.setArm_mis(arm_mis);
        param.setLeg_mis(leg_mis);
        param.setChest_mis(chest_mis);
        param.setHips_mis(hips_mis);
        param.setShoulders_mis(shoulders_mis);
        param.setWorkoutYears(workoutYears);
        String stringTest=param.toString();
        assertEquals(email, param.getEmail());
        assertEquals(weight, param.getWeight());
        assertEquals(lean_mass, param.getLean_mass());
        assertEquals(fat_mass, param.getFat_mass());
        assertEquals(arm_mis, param.getArm_mis());
        assertEquals(leg_mis, param.getLeg_mis());
        assertEquals(chest_mis, param.getChest_mis());
        assertEquals(hips_mis, param.getHips_mis());
        assertEquals(shoulders_mis, param.getShoulders_mis());
        assertEquals(workoutYears, param.getWorkoutYears());
        param2.setWeight(weight);
        param2.setLean_mass(lean_mass);
        param2.setFat_mass(fat_mass);
        param2.setArm_mis(arm_mis);
        param2.setLeg_mis(leg_mis);
        param2.setChest_mis(chest_mis);
        param2.setHips_mis(hips_mis);
        param2.setShoulders_mis(shoulders_mis);
        param2.setWorkoutYears(workoutYears);
        assertEquals(email, param.getEmail());
        assertEquals(weight, param.getWeight());
        assertEquals(lean_mass, param.getLean_mass());
        assertEquals(fat_mass, param.getFat_mass());
        assertEquals(arm_mis, param.getArm_mis());
        assertEquals(leg_mis, param.getLeg_mis());
        assertEquals(chest_mis, param.getChest_mis());
        assertEquals(hips_mis, param.getHips_mis());
        assertEquals(shoulders_mis, param.getShoulders_mis());
        assertEquals(workoutYears, param.getWorkoutYears());
        assertEquals(stringTest,param2.toString());
    }

}