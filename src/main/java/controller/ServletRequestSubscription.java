package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.personalTrainer.PersonalTrainer;
import model.personalTrainer.PersonalTrainerFacade;
import model.subscription.Subscription;
import model.subscription.SubscriptionFacade;
import model.user.UserBean;
import model.user.UserBeanFacade;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "RequestSubscription", value = "/RequestSubscription")
public class ServletRequestSubscription extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            UserBean ub = new UserBeanFacade().recoverInfos((String) session.getAttribute("email"));
            PersonalTrainer pt = new PersonalTrainerFacade().retrieveInfo(request.getParameter("visitEmail"));
            if (pt == null || pt.getUser() == null){
                throw new Exception("Personal Trainer email not valid");
            }
            Date dateEnd = Date.valueOf(request.getParameter("dateEnd"));
            if(dateEnd.before(new Date(System.currentTimeMillis()))){
                throw new Exception("Date end is before today date");
            }

            Subscription s = new SubscriptionFacade().addSubscription(ub, pt, dateEnd);

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