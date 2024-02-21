package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.trainingPlan.TrainingPlanFacade;
import model.user.UserBean;
import model.user.UserBeanFacade;
import org.json.JSONObject;

import java.io.IOException;

import static java.lang.System.out;

@WebServlet(name = "DeleteTrainingPlan", value = "/DeleteTrainingPlan")
public class ServletDeleteTrainingPlan extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            String emailPT = (String) session.getAttribute("email");
            String exercises = request.getParameter("exercisesString");
            String emailUser = request.getParameter("visitEmail");
            out.println("JSON: "+exercises);
            UserBean ub = new UserBeanFacade().checkEmail(emailUser);
            if (ub == null || ub.getEmail().equalsIgnoreCase("errore"))
                throw new Exception("Email user is not valid");
            Boolean delete = new TrainingPlanFacade().deleteTrainingPlan(emailUser, emailPT, exercises);
            if (!delete) throw new Exception("Something went wrong");
            request.setAttribute("success","./userpage.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request, response);
        }catch (Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
        }
    }
}