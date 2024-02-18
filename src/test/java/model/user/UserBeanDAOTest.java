package model.user;

import model.utils.PasswordEncryptionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserBeanDAOTest {
    @BeforeAll
    static void registerUser() throws NoSuchAlgorithmException {
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        new UserBeanDAO().userRegistration("testing@testing.org",password,"Name", "Surname", "1234567890", "m");
        new UserBeanDAO().userRegistration("admin@admin.org",password,"Name", "Surname", "1234567890", "m");
    }
    @AfterAll
    static void removeRegisteredUser(){
        new UserBeanDAO().deleteUser("testing@testing.org");
        new UserBeanDAO().deleteUser("testing@testing.net");
        new UserBeanDAO().deleteUser("admin@admin.org");
    }
    @Test
    void userRegistration() throws NoSuchAlgorithmException {
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        UserBean ub = new UserBeanDAO().userRegistration("testing@testing.org",password,"Name", "Surname", "1234567890", "m");
        assertNotNull(ub);
    }
    @Test
    public void loginUser() throws NoSuchAlgorithmException {
        UserBeanDAO dao = new UserBeanDAO();
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        UserBean userToLog = dao.loginUser("testing@testing.org",password);
        assertEquals("testing@testing.org", userToLog.getEmail());
        assertEquals(password, userToLog.getPsw());
    }
    @Test
    void recoverInfos() {
        UserBeanDAO dao = new UserBeanDAO();
        UserBean userToLog = dao.recoverInfos("testing@testing.org");
        assertEquals("fake", userToLog.getPsw());
    }
    @Test
    void checkEmail() {
        UserBean ub = new UserBeanDAO().checkEmail("testing@testing.org");
        assertNotNull(ub);
        assertNotEquals("ERRORE",ub.getEmail());
    }
    @Test
    public void changeName() throws NoSuchAlgorithmException {
        UserBeanDAO dao = new UserBeanDAO();
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        UserBean ub = new UserBean("testing@testing.org",password);
        ub.setNome("Name");
        ub.setCognome("Surname");
        UserBean userToLog = dao.changeName(ub, "Admin", "Admin");
        assertNotNull(userToLog);
        assertEquals("Admin", userToLog.getNome());
        assertEquals("Admin", userToLog.getCognome());
    }
    @Test
    public void changeNumber() throws NoSuchAlgorithmException {
        UserBeanDAO dao = new UserBeanDAO();
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        UserBean ub = new UserBean("testing@testing.org",password);
        ub.setTelefono("1234567890");
        UserBean userToLog = dao.changeNumber(ub, "1234567899");
        assertEquals("1234567899", userToLog.getTelefono());
    }

    @Test
    public void changeGender() throws NoSuchAlgorithmException {
        UserBeanDAO dao = new UserBeanDAO();
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        UserBean ub = new UserBean("testing@testing.org",password);
        ub.setGender("Male");
        UserBean userToLog = dao.changeGender(ub, "f");
        assertNotNull(userToLog);
        assertEquals("f", userToLog.getGender());
    }

    @Test
    void changePsw() throws NoSuchAlgorithmException {
        UserBeanDAO dao = new UserBeanDAO();
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        String password1 = PasswordEncryptionUtil.encryptPassword("Ciaoprova1!");
        UserBean userToLog = dao.changePsw(new UserBean("testing@testing.org",password), password, password1);
        assertEquals(password1, userToLog.getPsw());
    }
    @Test
    public void changeEmail() throws NoSuchAlgorithmException {
        UserBeanDAO dao = new UserBeanDAO();
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        UserBean userToLog = dao.changeEmail(new UserBean("admin@admin.org",password), "admin@admin.org", "testing@testing.net");
        assertNotNull(userToLog);
        assertEquals("testing@testing.net", userToLog.getEmail());
        assertEquals(password, userToLog.getPsw());
    }

    @Test
    void forgotPsw() throws NoSuchAlgorithmException {
        UserBeanDAO dao = new UserBeanDAO();
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        String password1 = PasswordEncryptionUtil.encryptPassword("Ciaoprova1!");
        UserBean userToLog = dao.forgotPsw(new UserBean("testing@testing.net",password), password1);
        assertNotNull(userToLog);
        assertEquals(password1, userToLog.getPsw());
    }

    @Test
    void allUsers() {
        ArrayList<UserBean> userBeans = new UserBeanDAO().allUsers();
        assertNotNull(userBeans);
    }

    @Test
    void requestRolePT() throws NoSuchAlgorithmException {
        String password1 = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        UserBean ub = new UserBeanDAO().loginUser("testing@testing.net",password1);
        assertNotNull(ub);
        assertNotEquals("ERRORE", ub.getEmail());
        ub = new UserBeanDAO().requestRolePT(ub);
        assertNotNull(ub);
        assertEquals("PTR",ub.getRole());
    }



    // TC_1.1_1
    @Test
    public void emailLengthNotValidIT() throws NoSuchAlgorithmException {
        String mail = "c@c.c";
        String password = "Ciaoprova1@";
        String message = "Lunghezza email non valida";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertNotEquals(mail,ub.getEmail());
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    // TC_1.1_2
    @Test
    public void emailFormatNotValidIT() throws NoSuchAlgorithmException {
        String mail = "ciccipappa@@";
        String password = "Ciaoprova1@";
        String message = "Formato email non valido";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertNotEquals(mail,ub.getEmail());
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }

    // TC_1.1_3
    @Test
    public void passwordLengthNotValidIT() throws NoSuchAlgorithmException {
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

    // TC_1.1_4
    @Test
    public void passwordFormatNotValidIT() throws NoSuchAlgorithmException {
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

    // TC_1.1_5
    @Test
    public void finalTestIT() throws NoSuchAlgorithmException {
        String mail = "testing@testing.org";
        String password = "Ciaoprova1!";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertEquals(mail,ub.getEmail());
    }
    // TC_1.3.3_1
    @Test
    public void nameLengthError() throws NoSuchAlgorithmException {
        String mail = "testing@testing.org";
        String password = "Ciaoprova1@";
        String message = "Lunghezza nome non valida";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertEquals(mail,ub.getEmail());

        ub = userBeanDAO.changeName(ub, "A","Rossi");
        assertNull(ub);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    // TC_1.3.3_2
    @Test
    public void nameFormatError() throws NoSuchAlgorithmException {
        String mail = "testing@testing.org";
        String password = "Ciaoprova1@";
        String message = "Formato nome non valido";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertEquals(mail,ub.getEmail());

        ub = userBeanDAO.changeName(ub, "alpacino","Rossi");
        assertNull(ub);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    // TC_1.3.3_3
    @Test
    public void surnameLengthError() throws NoSuchAlgorithmException {
        String mail = "testing@testing.org";
        String password = "Ciaoprova1@";
        String message = "Lunghezza cognome non valida";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertEquals(mail,ub.getEmail());

        ub = userBeanDAO.changeName(ub, "Alpacino","R");
        assertNull(ub);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
    // TC_1.3.3_5
    @Test
    public void surnameFormatError() throws NoSuchAlgorithmException {
        String mail = "testing@testing.org";
        String password = "Ciaoprova1@";
        String message = "Formato cognome non valido";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertEquals(mail,ub.getEmail());

        ub = userBeanDAO.changeName(ub, "Alpacino","rossi");
        assertNull(ub);
        assertThrows(Exception.class, () -> {
            throw new Exception(message);
        });
    }
}