package model.personalTrainer;

import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class PersonalTrainerDAOTest {

    @BeforeAll
    static void personalTrainerRegistration() throws NoSuchAlgorithmException {
        UserBean ubForPT1=new UserBeanDAO().userRegistration("personal1@personal1.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"AndreaPT","AbbatePT","1234567890","m");
        PersonalTrainer pt1=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT1);
        pt1.setDescription("Hi, I'm personal trainer 1");
        pt1.setPtYears(15);
        assertNotNull(pt1);

        UserBean ubForPT2=new UserBeanDAO().userRegistration("personal2@personal2.it",PasswordEncryptionUtil.encryptPassword("Ciaoprova1@"),"RaffaelePT","SulipanoPT","1234567890","m");
        PersonalTrainer pt2=new PersonalTrainerDAO().personalTrainerRegistration(ubForPT2);
        pt1.setDescription("Hi, I'm personal trainer 2");
        pt1.setPtYears(12);
        assertNotNull(pt2);
    }

    @AfterAll
    static void clearAll(){
        new UserBeanDAO().deleteUser("personal1@personal1.it");
        new UserBeanDAO().deleteUser("personal2@personal2.it");
    }

    @Test
    void changeDescription() {
        PersonalTrainer pt=new PersonalTrainerDAO().retrieveInfo("personal1@personal1.it");
        assertNotNull(new PersonalTrainerDAO().changeDescription(pt,"I changed my description!"));
    }

    @Test
    void changePTYears() {
        PersonalTrainer pt=new PersonalTrainerDAO().retrieveInfo("personal1@personal1.it");
        assertNotNull(new PersonalTrainerDAO().changePTYears(pt,20));
    }

    @Test
    void retrieveInfo() {
        assertNotNull(new PersonalTrainerDAO().retrieveInfo("personal1@personal1.it"));
    }

    @Test
    void retrieveAll() {
        assertNotNull(new PersonalTrainerDAO().retrieveAll());
    }

    @Test
    void deletePT() {
        assertEquals(new PersonalTrainerDAO().deletePT("personal2@personal2.it"),true);
    }
}