package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.subscription.Subscription;
import model.subscription.SubscriptionDAO;
import model.user.UserBeanDAO;

import java.io.IOException;

@WebServlet(name = "RefuseSubscription", value = "/RefuseSubscription")
public class ServletRefuseSubscription extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String emailUser = request.getParameter("emailUser");
            Subscription s = new SubscriptionDAO().getSubscription(new UserBeanDAO().recoverInfos(emailUser));
            if (s == null || s.getEmailUser().equalsIgnoreCase("error")){
                throw new Exception("User email not valid");
            }

            s = new SubscriptionDAO().refuseSubscription(s);

            if (s == null){
                throw new Exception("Something went wrong");
            }
            request.setAttribute("success","./userpage.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request, response);
        }catch (Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
        }
    }
}