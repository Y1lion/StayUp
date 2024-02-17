package model.trainingPlan;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TrainingPlanTest {
    @Test
    void testTrainingPlan() {
        String emailPT = "andreapt@gmail.com";
        String emailUser = "raffaeleuser@gmail.com";
        String exercises = "{\"Esercizio\": \"Panca piana\"}";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        TrainingPlan tp1=new TrainingPlan(emailUser,emailPT,exercises,dateStart,dateEnd);
        TrainingPlan tp2=new TrainingPlan(emailUser,emailPT,exercises,dateStart,dateEnd);
        String stringTp1=tp1.toString();
        String stringTp2=tp2.toString();
        assertNotNull(tp1);
        assertEquals(emailUser,tp1.getEmailUser());
        assertEquals(emailPT,tp1.getEmailPt());
        assertEquals(exercises,tp1.getExercises());
        assertEquals(dateStart,tp1.getDateStart());
        assertEquals(dateEnd,tp1.getDateEnd());
        assertNotNull(tp2);
        assertEquals(emailUser,tp2.getEmailUser());
        assertEquals(emailPT,tp2.getEmailPt());
        assertEquals(exercises,tp2.getExercises());
        assertEquals(dateStart,tp2.getDateStart());
        assertEquals(dateEnd,tp2.getDateEnd());
        assertEquals(stringTp1,stringTp2);
    }
}