package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.personalTrainer.PersonalTrainerDAO;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ServletAddTrainingPlanTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private ServletContext servletContext;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private ServletAddTrainingPlan servlet;

    @BeforeEach
    void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        servlet.init(servletConfig);
    }

    @BeforeAll
    static void populate() throws NoSuchAlgorithmException {
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        new UserBeanDAO().userRegistration("testing@testing.org",password,"Name", "Surname", "1234567890", "m");
        UserBean ub=new UserBeanDAO().userRegistration("personal@personal.org",password,"Name", "Surname", "1234567890", "m");
        new PersonalTrainerDAO().personalTrainerRegistration(ub);
    }

    @AfterAll
    static void cleanAll(){
        new UserBeanDAO().deleteUser("testing@testing.org");
        new UserBeanDAO().deleteUser("personal@personal.org");
    }

    @Test
    void doPostSuccess() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(20)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("success", "./index.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostDateStartFail() throws ServletException, IOException {
        LocalDate startDate = LocalDate.of(2024, 2, 10);
        Date dateStart = Date.valueOf(startDate);
        LocalDate endDate = startDate.plusMonths(1);
        Date dateEnd = Date.valueOf(endDate);
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(3)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostEndBeforeStartDateFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(3)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostTitleFormatFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo mio");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(4)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostTitleLenghtFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn(" ");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(4)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostExerciseNameFormatFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana@");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(8)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostExerciseNameLenghtFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn(" ");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(8)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostSetsFormatFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4d");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostSetsLenghtFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("0");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostRepsFormatFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10s");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(10)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostRepsLenghtFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("0");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("90sec");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(10)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostRestLenghtFail() throws ServletException, IOException {
        Date dateStart = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.add(Calendar.MONTH, 1);
        Date dateEnd = new Date(calendar.getTimeInMillis());
        when(request.getParameter("dateStart")).thenReturn(String.valueOf(dateStart));
        when(request.getParameter("title")).thenReturn("Titolo");
        when(request.getParameter("gg1")).thenReturn("1");
        when(request.getParameter("gg2")).thenReturn("0");
        when(request.getParameter("gg3")).thenReturn("0");
        when(request.getParameter("gg4")).thenReturn("0");
        when(request.getParameter("gg5")).thenReturn("0");
        when(request.getParameter("formNameExerciseN1D1")).thenReturn("Panca piana");
        when(request.getParameter("formNameSetsN1D1")).thenReturn("4");
        when(request.getParameter("formNameRepsN1D1")).thenReturn("10");
        when(request.getParameter("formNamePauseN1D1")).thenReturn("");
        when(request.getParameter("dateEnd")).thenReturn(String.valueOf(dateEnd));
        when(request.getParameter("emailSub")).thenReturn("testing@testing.org");
        when(session.getAttribute("email")).thenReturn("personal@personal.org");
        servlet.doPost(request,response);
        verify(request,times(11)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./training_plan.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}