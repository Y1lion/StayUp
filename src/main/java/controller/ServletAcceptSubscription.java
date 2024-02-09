package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.subscription.Subscription;
import model.subscription.SubscriptionDAO;
import model.user.UserBeanDAO;

import java.io.IOException;

@WebServlet(name = "AcceptSubscription", value = "/AcceptSubscription")
public class ServletAcceptSubscription extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String emailUser = request.getParameter("emailUser");
            Subscription s = new SubscriptionDAO().getSubscription(new UserBeanDAO().recoverInfos(emailUser));
            s = new SubscriptionDAO().acceptSubscription(s);

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