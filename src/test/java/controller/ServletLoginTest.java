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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ServletLoginTest {
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
    private ServletLogin servlet;

    @BeforeEach
    void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        when(request.getSession(true)).thenReturn(session);
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
    void doPostSuccess() throws ServletException, IOException {
        when(request.getParameter("emailLog")).thenReturn("testing@testing.org");
        when(request.getParameter("passwordLog")).thenReturn("Ciaoprova1@");
        servlet.doPost(request, response);
        verify(request, times(2)).getParameter(anyString());
        verify(request, times(1)).setAttribute("success", "./index.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostEmailFail() throws ServletException, IOException {
        when(request.getParameter("emailLog")).thenReturn("a@a.it");
        when(request.getParameter("passwordLog")).thenReturn("Ciaoprova1@");
        servlet.doPost(request, response);
        verify(request, times(2)).getParameter(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./login.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPostPasswordFail() throws ServletException, IOException {
        when(request.getParameter("emailLog")).thenReturn("testing@testing.it");
        when(request.getParameter("passwordLog")).thenReturn("Pas1@");
        servlet.doPost(request, response);
        verify(request, times(2)).getParameter(anyString());
        verify(request, times(1)).setAttribute("exceptionURL", "./login.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}