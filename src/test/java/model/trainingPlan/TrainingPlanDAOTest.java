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
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"Petto\"\n" +
                "}";

        UserBean ub1=new UserBeanDAO().userRegistration("test1@test1.it", PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"Andrea","Abbate","1234567890","m");
        UserBean ubForPT1=new UserBeanDAO().userRegistration("personal1@personal1.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"AndreaPT","AbbatePT","1234567890","m");
        PersonalTrainer pt1=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT1);

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
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"Petto\"\n" +
                "}";
        Boolean tp=new TrainingPlanDAO().deleteTrainingPlan("test1@test1.it","personal1@personal1.it",exercises);
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
        new UserBeanDAO().deleteUser("test2@test2.it");
        new UserBeanDAO().deleteUser("personal1@personal1.it");
        new UserBeanDAO().deleteUser("personal2@personal2.it");
        new UserBeanDAO().deleteUser("successful@user.it");
        new UserBeanDAO().deleteUser("successful@personal.it");
    }

    //TC_2.1_1
    @Test
    void startDateFail() {
        LocalDate startDate = LocalDate.of(2024, 2, 10);
        Date dateStart = Date.valueOf(startDate);
        String message="Data inizio antecedente a quella attuale";
        System.out.println("CAZZO DAIIII: "+dateStart);
        LocalDate endDate = startDate.plusMonths(1);
        Date dateEnd = Date.valueOf(endDate);
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"Petto\"\n" +
                "}";

        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_2
    @Test
    void EndBeforeStartDateFail(){
        String message="Data fine precedente a Data inizio";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"Petto\"\n" +
                "}";

        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_3
    @Test
    void titleLenghtFail(){
        String message="Lunghezza del titolo non adeguata";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \" \"\n" +
                "}";

        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_4
    @Test
    void titleFormatFail(){
        String message="Formato del titolo non adeguato";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"Mio titolo\"\n" +
                "}";

        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_5
    @Test
    void exerciseNameLenghtFail(){
        String message="Lunghezza del nome dell'esercizio non adeguata";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \" \"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"title\"\n" +
                "}";

        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_6
    @Test
    void exerciseNameFormatFail(){
        String message="Formato del nome dell'esercizio non adeguato";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana@\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"title\"\n" +
                "}";

        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_7
    @Test
    void setsLenghtFail(){
        String message="Lunghezza delle serie non adeguata";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"0\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"title\"\n" +
                "}";

        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_8
    @Test
    void setsFormatFail(){
        String message="Formato delle serie non adeguato";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"3s\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"title\"\n" +
                "}";


        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_9
    @Test
    void repsLenghtFail(){
        String message="Lunghezza delle ripetizioni non adeguata";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"0\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"title\"\n" +
                "}";

        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_10
    @Test
    void repsFormatFail() {
        String message="Formato delle ripetizioni non adeguato";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10s\",\n" +
                "                    \"Rest\": \"90sec\",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"title\"\n" +
                "}";


        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_11
    @Test
    void restLenghtFail(){
        String message="Formato delle ripetizioni non adeguato";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        String exercises = "{\n" +
                "    \"Days\": [\n" +
                "        {\n" +
                "            \"Day\": \"1\",\n" +
                "            \"Exercises\": [\n" +
                "                {\n" +
                "                    \"Reps\": \"10\",\n" +
                "                    \"Rest\": \" \",\n" +
                "                    \"Sets\": \"4\",\n" +
                "                    \"Exercise\": \"Panca piana\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Title\": \"title\"\n" +
                "}";


        Boolean tp=new TrainingPlanDAO().addTrainingPlan("personalfail@personalfail.it","userfail@userfail.it",exercises,dateStart,dateEnd);

        assertFalse(tp);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    //TC_2.1_12
     @Test
    void successfulTest() throws NoSuchAlgorithmException {
         Date dateStart = new Date(System.currentTimeMillis());
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(dateStart);
         calendar.add(Calendar.MONTH, 1);
         Date dateEnd = new Date(calendar.getTimeInMillis());
         String exercises = "{\n" +
                 "    \"Days\": [\n" +
                 "        {\n" +
                 "            \"Day\": \"1\",\n" +
                 "            \"Exercises\": [\n" +
                 "                {\n" +
                 "                    \"Reps\": \"10\",\n" +
                 "                    \"Rest\": \"1,30m\",\n" +
                 "                    \"Sets\": \"4\",\n" +
                 "                    \"Exercise\": \"Panca piana\"\n" +
                 "                }\n" +
                 "            ]\n" +
                 "        }\n" +
                 "    ],\n" +
                 "    \"Title\": \"Petto\"\n" +
                 "}";

         UserBean ub=new UserBeanDAO().userRegistration("successful@user.it", PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"Andrea","Abbate","1234567890","m");
         UserBean ubForPT=new UserBeanDAO().userRegistration("successful@personal.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"AndreaPT","AbbatePT","1234567890","m");
         PersonalTrainer pt=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT);
         Boolean tp=new TrainingPlanDAO().addTrainingPlan(pt.getUser().getEmail(), ub.getEmail(),exercises,dateStart,dateEnd);

     }
}