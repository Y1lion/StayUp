package model.user;

import model.utils.PasswordEncryptionUtil;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class UserBeanDAOTest {

    // TC_1.3_1
    @Test
    public void emailLengthNotValidIT() throws NoSuchAlgorithmException {
        String mail = "c@c.c";
        String password = "Ciaoprova1@";
        String message = "Lunghezza email non valida";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        if (ub == null || ub.getEmail().equalsIgnoreCase("errore")){
            assertThrows(Exception.class, () -> {
                throw new Exception(message);
            });
        }
    }

    // TC_1.3_2
    @Test
    public void emailFormatNotValidIT() throws NoSuchAlgorithmException {
        String mail = "ciccipappa@@";
        String password = "Ciaoprova1@";
        String message = "Formato email non valido";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        if (ub == null || ub.getEmail().equalsIgnoreCase("errore")){
            assertThrows(Exception.class, () -> {
                throw new Exception(message);
            });
        }
    }

    // TC_1.3_3
    @Test
    public void passwordLengthNotValidIT() throws NoSuchAlgorithmException {
        String mail = "ciao@prova.it";
        String password = "C1@o";
        String message = "Lunghezza password non valida";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        if (ub == null || ub.getEmail().equalsIgnoreCase("errore")){
            assertThrows(Exception.class, () -> {
                throw new Exception(message);
            });
        }
    }

    // TC_1.3_4
    @Test
    public void passwordFormatNotValidIT() throws NoSuchAlgorithmException {
        String mail = "ciao@prova.it";
        String password = "ciaoprovola";
        String message = "Formato password non valida";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        if (ub == null || ub.getEmail().equalsIgnoreCase("errore")){
            assertThrows(Exception.class, () -> {
                throw new Exception(message);
            });
        }
    }

    // TC_1.3_5
    @Test
    public void finalTestIT() throws NoSuchAlgorithmException {
        String mail = "hi@hi.it";
        String password = "Ciaoprova1@";
        password = PasswordEncryptionUtil.encryptPassword(password);

        UserBeanDAO userBeanDAO = new UserBeanDAO();
        UserBean ub = userBeanDAO.loginUser(mail,password);
        assertNotNull(ub);
        assertEquals(mail,ub.getEmail());
    }
}