package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ServletChangePasswordTest {

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
    private ServletChangePassword servlet;

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
    }
    @AfterAll
    static void removeRegisteredUser(){
        new UserBeanDAO().deleteUser("testing@testing.org");
    }

    @Test
    void doPostTrue() throws Exception {
        when(request.getParameter("newpassword")).thenReturn("Ciaoprova1!");
        when(request.getParameter("confirmpassword")).thenReturn("Ciaoprova1!");
        when(request.getParameter("currentpassword")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(3)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("success", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostPasswordFormatInvalid() throws Exception {
        when(request.getParameter("newpassword")).thenReturn("Lollipop");
        when(request.getParameter("confirmpassword")).thenReturn("Lollipop");
        when(request.getParameter("currentpassword")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(3)).getParameter(anyString());
        verify(session, times(0)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostCurrentPasswordWrong() throws Exception {
        when(request.getParameter("newpassword")).thenReturn("Ciaoprova1!");
        when(request.getParameter("confirmpassword")).thenReturn("Ciaoprova1!");
        when(request.getParameter("currentpassword")).thenReturn("Ciaoprova1$");
        when(session.getAttribute("email")).thenReturn("testing@testing.org");

        servlet.doPost(request, response);

        verify(request, times(3)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }
    @Test
    void doPostEmailNotFound() throws Exception {
        when(request.getParameter("newpassword")).thenReturn("Ciaoprova1!");
        when(request.getParameter("confirmpassword")).thenReturn("Ciaoprova1!");
        when(request.getParameter("currentpassword")).thenReturn("Ciaoprova1@");
        when(session.getAttribute("email")).thenReturn("test@test.org");

        servlet.doPost(request, response);

        verify(request, times(3)).getParameter(anyString());
        verify(session, times(1)).getAttribute(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./userpage.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}