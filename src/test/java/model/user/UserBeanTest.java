package model.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserBeanTest {
    @Test
    void testUserBean(){
        String name = "Alessio";
        String surname = "Fico";
        String phone = "1234567890";
        String email = "alessio@gmail.com";
        String password = "Palestrato1!";
        String gender = "Male";
        String role = "user";
        UserBean account = new UserBean(email, password);
        UserBean account1 = new UserBean(email, password);
        assertNotNull(account);
        account.setNome(name);
        account.setCognome(surname);
        account.setTelefono(phone);
        account.setRole(role);
        account.setGender(gender);
        String stringTest=account.toString();
        assertEquals(name, account.getNome());
        assertEquals(surname, account.getCognome());
        assertEquals(phone, account.getTelefono());
        assertEquals(email, account.getEmail());
        assertEquals(password, account.getPsw());
        assertEquals(role, account.getRole());
        account1.setNome(name);
        account1.setCognome(surname);
        account1.setTelefono(phone);
        account1.setEmail(email);
        account1.setPsw(password);
        account1.setRole(role);
        assertEquals(name, account1.getNome());
        assertEquals(surname, account1.getCognome());
        assertEquals(phone, account1.getTelefono());
        assertEquals(email, account1.getEmail());
        assertEquals(password, account1.getPsw());
        assertEquals(role, account1.getRole());
        assertEquals(stringTest,account.toString());
    }
}