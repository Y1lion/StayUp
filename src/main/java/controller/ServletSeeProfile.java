package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanFacade;

import java.io.IOException;

import static java.lang.System.out;

@WebServlet(name = "SeeProfile", value = "/SeeProfile")
public class ServletSeeProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("visitEmail");
            UserBean ub = new UserBeanFacade().checkEmail(email);
            if (ub == null || ub.getEmail().equalsIgnoreCase("errore"))
                throw new Exception("Clicked email is not valid");
            request.setAttribute("emailUser", email);
            request.getRequestDispatcher("./visitUser.jsp").forward(request, response);
        }catch (Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
        }
    }
}