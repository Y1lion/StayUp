package model.trainingPlan;

import model.personalTrainer.PersonalTrainer;
import model.personalTrainer.PersonalTrainerDAO;
import model.subscription.Subscription;
import model.subscription.SubscriptionDAO;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TrainingPlanDAOTest {

    @BeforeAll
    static void addTrainingPlan() throws NoSuchAlgorithmException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\"Esercizio\": \"Panca piana\"}";

        UserBean ub1=new UserBeanDAO().userRegistration("test1@test1.it", PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"Andrea","Abbate","1234567890","m");
        UserBean ubForPT1=new UserBeanDAO().userRegistration("personal1@personal1.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"AndreaPT","AbbatePT","1234567890","m");
        PersonalTrainer pt1=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT1);

        String exercise2 = "{\"Esercizio\": \"Panca piana\"}";
        UserBean ub2=new UserBeanDAO().userRegistration("test2@test2.it", PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"Raffaele","Sulipano","1234567890","m");
        UserBean ubForPT2=new UserBeanDAO().userRegistration("personal2@personal2.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"RaffaelePT","SulipanoPT","1234567890","m");
        PersonalTrainer pt2=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT2);

        Boolean tp1=new TrainingPlanDAO().addTrainingPlan(pt1.getUser().getEmail(), ub1.getEmail(),exercises,dateStart,dateEnd);
        Boolean tp2=new TrainingPlanDAO().addTrainingPlan(pt2.getUser().getEmail(), ub2.getEmail(),exercises,dateStart,dateEnd);

        assertEquals(tp1,true);
        assertEquals(tp2,true);
    }

    @Test
    void deleteTrainingPlan() {
        Boolean tp=new TrainingPlanDAO().deleteTrainingPlan("test1@test1.it","personal1@personal1.it","{\"Esercizio\": \"Panca piana\"}");
        assertEquals(tp,true);
    }

    @Test
    void getAvailableTrainingPlan() {
        UserBean ub=new UserBeanDAO().recoverInfos("test2@test2.it");
        ArrayList<TrainingPlan>tps=new TrainingPlanDAO().getAvailableTrainingPlan(ub);
        assertNotNull(tps);
    }

    @Test
    void getAvailablePtTrainingPLan() {
        UserBean ub=new UserBeanDAO().recoverInfos("test2@test2.it");
        PersonalTrainer pt=new PersonalTrainerDAO().retrieveInfo("personal2@personal2.it");
        ArrayList<TrainingPlan>tps = new TrainingPlanDAO().getAvailablePtTrainingPLan(ub,pt);
        assertNotNull(tps);
    }

    @Test
    void getAllTrainingPlans() {
        PersonalTrainer pt=new PersonalTrainerDAO().retrieveInfo("personal2@personal2.it");
        ArrayList<TrainingPlan>tps = new TrainingPlanDAO().getAllTrainingPlans(pt);
        assertNotNull(tps);
    }

    @Test
    void getAllUserTrainingPlans() {
        UserBean ub=new UserBeanDAO().recoverInfos("test2@test2.it");
        ArrayList<TrainingPlan>tps = new TrainingPlanDAO().getAllUserTrainingPlans(ub);
        assertNotNull(tps);
    }

    @AfterAll
    static void clearAll(){
        new UserBeanDAO().deleteUser("test1@test1.it");
        new UserBeanDAO().deleteUser("testfail1@testfail1.it");
        new UserBeanDAO().deleteUser("test2@test2.it");
        new UserBeanDAO().deleteUser("personal1@personal1.it");
        new UserBeanDAO().deleteUser("personalfail1@personalfail1.it");
        new UserBeanDAO().deleteUser("personal2@personal2.it");
    }

    //TC_2.1_1
    @Test
    void startDateFail() throws NoSuchAlgorithmException {
        LocalDate startDate = LocalDate.of(2024, 2, 10);
        Date dateStart = Date.valueOf(startDate);
        String message="Data inizio antecedente a quella attuale";

        LocalDate endDate = startDate.plusMonths(1);
        Date dateEnd = Date.valueOf(endDate);
        String exercises = "{\"Esercizio\": \"Panca piana\"}";

        UserBean ub=new UserBeanDAO().userRegistration("testfail1@testfail1.it", PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"Andrea","Abbate","1234567890","m");
        UserBean ubForPT=new UserBeanDAO().userRegistration("personalfail1@personalfail1.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"AndreaPT","AbbatePT","1234567890","m");
        PersonalTrainer pt=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT);


        Boolean tp=new TrainingPlanDAO().addTrainingPlan(pt.getUser().getEmail(), ub.getEmail(),exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
}