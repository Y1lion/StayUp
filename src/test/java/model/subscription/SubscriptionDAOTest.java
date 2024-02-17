package model.subscription;

import model.personalTrainer.PersonalTrainer;
import model.personalTrainer.PersonalTrainerDAO;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


import static org.junit.jupiter.api.Assertions.*;

class SubscriptionDAOTest {

    @BeforeAll
    static void addSubscription() throws NoSuchAlgorithmException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());

        UserBean ub1=new UserBeanDAO().userRegistration("test1@test1.it", PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"Andrea","Abbate","1234567890","m");
        UserBean ubForPT1=new UserBeanDAO().userRegistration("personal1@personal1.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"AndreaPT","AbbatePT","1234567890","m");
        PersonalTrainer pt1=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT1);

        UserBean ub2=new UserBeanDAO().userRegistration("test2@test2.it", PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"Raffaele","Sulipano","1234567890","m");
        UserBean ubForPT2=new UserBeanDAO().userRegistration("personal2@personal2.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"RaffaelePT","SulipanoPT","1234567890","m");
        PersonalTrainer pt2=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT2);

        Subscription s1=new SubscriptionDAO().addSubscription(ub1,pt1,dateEnd);
        Subscription s2=new SubscriptionDAO().addSubscription(ub2,pt2,dateEnd);

        assertNotNull(s1);
        assertNotNull(s2);
    }

    @Test
    void acceptSubscription() {
        Subscription sub=new SubscriptionDAO().getSubscription(new UserBeanDAO().recoverInfos("test1@test1.it"));
        Subscription subscription=new SubscriptionDAO().acceptSubscription(sub);
        assertNotNull(subscription);
    }

    @Test
    void refuseSubscription() {
        Subscription sub=new SubscriptionDAO().getSubscription(new UserBeanDAO().recoverInfos("test2@test2.it"));
        Subscription subscription=new SubscriptionDAO().refuseSubscription(sub);
        assertNotNull(subscription);
    }

    @AfterAll
    static void clearAll(){
        new UserBeanDAO().deleteUser("test1@test1.it");
        new UserBeanDAO().deleteUser("test2@test2.it");
        new UserBeanDAO().deleteUser("personal1@personal1.it");
        new UserBeanDAO().deleteUser("personal2@personal2.it");
    }

    @Test
    void getSubscription(){
        Subscription subscription=new SubscriptionDAO().getSubscription(new UserBeanDAO().recoverInfos("test2@test2.it"));
        assertNotNull(subscription);
    }

    @Test
    void getAllSubscriptions() {
        PersonalTrainer pt = new PersonalTrainerDAO().retrieveInfo("personal2@personal2.it");
        ArrayList<Subscription> subscriptions= new SubscriptionDAO().getAllSubscriptions(pt);
        assertNotNull(subscriptions);
    }
}