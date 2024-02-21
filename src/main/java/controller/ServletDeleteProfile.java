package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanFacade;
import model.utils.PasswordEncryptionUtil;

import java.io.IOException;

@WebServlet(name = "DeleteProfile", value = "/DeleteProfile")
public class ServletDeleteProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            String password = request.getParameter("deleteProfilePassword");
            if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$"))
                throw new Exception("Password format is not respected");
            password = PasswordEncryptionUtil.encryptPassword(password);
            UserBean us = new UserBeanFacade().loginUser((String) session.getAttribute("email"),password);
            if(us.getEmail().equalsIgnoreCase("ERRORE"))
                throw new Exception("Wrong password");
            new UserBeanFacade().deleteUser((String) session.getAttribute("email"));
            request.getSession().invalidate();
            request.setAttribute("success","./index.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request, response);
        }catch (Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request,response);
        }
    }
}