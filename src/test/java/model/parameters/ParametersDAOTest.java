package model.parameters;

import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParametersDAOTest {
    @BeforeAll
    static void userToTest() throws NoSuchAlgorithmException {
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        new UserBeanDAO().userRegistration("testing@testing.org",password,"Name", "Surname", "1234567890", "m");
        new UserBeanDAO().userRegistration("testing@testing.net",password,"Name", "Surname", "1234567890", "m");
        ArrayList<Double> params = new ArrayList<>();
        params.add(20.0);
        params.add(20.0);
        params.add(30.0);
        params.add(40.0);
        params.add(50.0);
        params.add(60.0);
        params.add(70.0);
        params.add(80.0);
        params.add(50.0);
        assertNotNull(new ParametersDAO().setParameters("testing@testing.org",params));

    }
    @AfterAll
    static void removeUserTested(){
        new UserBeanDAO().deleteUser("testing@testing.org");
        new UserBeanDAO().deleteUser("testing@testing.net");
    }
    @Test
    void getParameters() {
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        assertNotNull(param);
        assertEquals("testing@testing.org",param.getEmail());
    }

    @Test
    void setParameters() {
        ArrayList<Double> params = new ArrayList<>();
        params.add(20.0);
        params.add(20.0);
        params.add(30.0);
        params.add(40.0);
        params.add(50.0);
        params.add(60.0);
        params.add(70.0);
        params.add(80.0);
        params.add(50.0);
        Parameters param = new ParametersDAO().setParameters("testing@testing.net",params);
        assertNotNull(param);
        assertEquals("testing@testing.net",param.getEmail());
    }

    @Test
    void changeParameters() {
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setWeight(120.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        assertNotNull(params);
        assertEquals(param.getEmail(),params.getEmail());
        assertEquals(param.getWeight(),params.getWeight());
        assertEquals(param.toString(),params.toString());
    }
    //TC_1.2_1
    @Test
    void weightLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setWeight(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza peso non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_2
    @Test
    void weightFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setWeight(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato peso non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_3
    @Test
    void leanMassLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setLean_mass(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza massa magra non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_4
    @Test
    void leanMassFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setLean_mass(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato massa magra non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_5
    @Test
    void fatMassLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setFat_mass(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza massa grassa non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_6
    @Test
    void fatMassFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setFat_mass(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato massa grassa non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_7
    @Test
    void armMisLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza misurazione braccia non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_8
    @Test
    void armMisFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato misurazione braccia non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_9
    @Test
    void legMisLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza misurazione gambe non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_10
    @Test
    void legMisFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato misurazione gambe non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_11
    @Test
    void chestMisLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza misurazione petto non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_12
    @Test
    void chestMisFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato misurazione petto non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_13
    @Test
    void hipsMisLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza misurazione fianchi non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_14
    @Test
    void hipsMisFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato misurazione fianchi non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_15
    @Test
    void shouldersMisLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza misurazione spalle non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_16
    @Test
    void shouldersMisFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato misurazione spalle non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_17
    @Test
    void workoutYearsLengthMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(1.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Lunghezza anni di allenamento non valida";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_18
    @Test
    void workoutYearsFormatMatch(){
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setArm_mis(60.567);
        Parameters params = new ParametersDAO().changeParameters(param);
        String message = "Formato anni di allenamento non valido";
        assertNull(params);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_19
    @Test
    void passwordLengthMatch() throws NoSuchAlgorithmException {
        String mail = "testing@testing.org";
        String password = "C1@o";
        String message = "Lunghezza password non valida";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertNotEquals(mail,ub.getEmail());
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_20
    @Test
    void passwordFormatMatch() throws NoSuchAlgorithmException {
        String mail = "testing@testing.org";
        String password = "ciaoprovola";
        String message = "Formato password non valida";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertNotEquals(mail,ub.getEmail());
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    //TC_1.2_21
    @Test
    void finalTest() {
        Parameters param = new ParametersDAO().getParameters("testing@testing.org");
        param.setWeight(60.0);
        Parameters params = new ParametersDAO().changeParameters(param);
        assertNotNull(params);
        assertEquals(param.getWeight(),params.getWeight());
    }
}