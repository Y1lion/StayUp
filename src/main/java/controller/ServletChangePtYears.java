package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.personalTrainer.PersonalTrainer;
import model.personalTrainer.PersonalTrainerFacade;
import model.user.UserBean;
import model.user.UserBeanFacade;
import model.utils.PasswordEncryptionUtil;

import java.io.IOException;

@WebServlet(name = "ChangePtYears", value = "/ChangePtYears")
public class ServletChangePtYears extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            Integer years = Integer.parseInt(request.getParameter("newyears"));
            String psw = request.getParameter("current_password4");

            if(!psw.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$"))
                throw new Exception("Password format is not respected");
            if(years<0 || years>60)
                throw new Exception("Years format is not respected");

            psw = PasswordEncryptionUtil.encryptPassword(psw);
            UserBean ub = new UserBeanFacade().loginUser((String) session.getAttribute("email"),psw);

            if(ub.getEmail().equalsIgnoreCase("ERRORE")){
                throw new Exception("Wrong password");
            }

            PersonalTrainer pt = new PersonalTrainerFacade().retrieveInfo((String) session.getAttribute("email"));

            if (pt.getPtYears() == years){
                throw new Exception("New years are the same as the old years");
            }

            pt = new PersonalTrainerFacade().changePTYears(pt,years);

            if (pt == null){
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