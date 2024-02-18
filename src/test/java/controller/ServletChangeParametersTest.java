package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.parameters.ParametersDAO;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ServletChangeParametersTest {

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
    private ServletChangeParameters servlet;

    @BeforeEach
    void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        servlet.init(servletConfig);
    }

    @BeforeAll
    static void registerUser() throws NoSuchAlgorithmException {
        String password = PasswordEncryptionUtil.encryptPassword("Ciaoprova1@");
        new UserBeanDAO().userRegistration("testing@testing.org",password,"Name", "Surname", "1234567890", "m");
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
    static void removeRegisteredUser(){
        new UserBeanDAO().deleteUser("testing@testing.org");
    }

    @Test
    void doPostTrue() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(11)).getParameter(anyString());
        verify(session, times(2)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("success", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostWeightFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("1");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostLeanMassFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("1");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostFatMassFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("1");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostArmMisFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("1");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostLegMisFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("1");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostChestMisFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("1");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostHipsMisFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("1");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostShouldersMisFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("1");
        when(request.getParameter("parworkout")).thenReturn("5");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostWorkoutYearsFormatInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("100");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostPasswordInvalid() throws ServletException, IOException {
        when(request.getParameter("parweight")).thenReturn("80.0");
        when(request.getParameter("parlean")).thenReturn("80.0");
        when(request.getParameter("parfat")).thenReturn("80.0");
        when(request.getParameter("pararm")).thenReturn("80.0");
        when(request.getParameter("parleg")).thenReturn("80.0");
        when(request.getParameter("parchest")).thenReturn("80.0");
        when(request.getParameter("parhips")).thenReturn("80.0");
        when(request.getParameter("parshoulders")).thenReturn("80.0");
        when(request.getParameter("parworkout")).thenReturn("100");
        when(request.getParameter("parptyears")).thenReturn(null);
        when(request.getParameter("currentpassword1")).thenReturn("Ciaoprova");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(9)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}