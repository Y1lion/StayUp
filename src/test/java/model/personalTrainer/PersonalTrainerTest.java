package model.personalTrainer;

import model.user.UserBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalTrainerTest {
    @Test
    void testPersonalTrainer() {
        UserBean ub = new UserBean("andreapt@gmail.com", "Ciaoprova1@");
        String description = "Hi, I'm a personal trainer";
        Integer ptYears = 15;
        PersonalTrainer pt1 = new PersonalTrainer(ub);
        PersonalTrainer pt2 = new PersonalTrainer(ub);
        String stringPt1 = pt1.toString();
        String stringPt2 = pt2.toString();

        assertNotNull(pt1);
        pt1.setDescription(description);
        pt1.setPtYears(ptYears);
        assertEquals(ub,pt1.getUser());
        assertEquals(description,pt1.getDescription());
        assertEquals(ptYears,pt1.getPtYears());

        assertNotNull(pt2);
        pt2.setDescription(description);
        pt2.setPtYears(ptYears);
        assertEquals(ub,pt2.getUser());
        assertEquals(description,pt2.getDescription());
        assertEquals(ptYears,pt2.getPtYears());

        assertEquals(stringPt1,stringPt2);
    }
}