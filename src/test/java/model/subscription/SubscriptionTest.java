package model.subscription;

import model.user.UserBean;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {
    @Test
    void testSubscription(){
        String emailPT = "andreapt@gmail.com";
        String emailUser = "raffaeleuser@gmail.com";
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        Integer isActive = 1;
        Subscription sub1=new Subscription(emailPT,emailUser,dateStart,dateEnd,isActive);
        Subscription sub2=new Subscription(emailPT,emailUser,dateStart,dateEnd,isActive);
        String stringSub1=sub1.toString();
        String stringSub2=sub2.toString();
        assertNotNull(sub1);
        assertEquals(emailPT,sub1.getEmailPt());
        assertEquals(emailUser,sub1.getEmailUser());
        assertEquals(dateStart,sub1.getDateStart());
        assertEquals(dateEnd,sub1.getDateEnd());
        assertEquals(isActive,sub1.getActive());
        assertNotNull(sub2);
        assertEquals(emailPT,sub2.getEmailPt());
        assertEquals(emailUser,sub2.getEmailUser());
        assertEquals(dateStart,sub2.getDateStart());
        assertEquals(dateEnd,sub2.getDateEnd());
        assertEquals(isActive,sub2.getActive());
        assertEquals(stringSub1,stringSub2);
    }
}