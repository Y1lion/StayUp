package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;

import java.io.IOException;

import static java.lang.System.out;

@WebServlet(name = "ChangeGender", value = "/ChangeGender")
public class ServletChangeGender extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            String gender = request.getParameter("gender");
            String newGender;
            String psw = request.getParameter("current_password2");
            psw = PasswordEncryptionUtil.encryptPassword(psw);
            UserBean ub = new UserBeanDAO().loginUser((String) session.getAttribute("email"),psw);

            if (gender == null || gender.isEmpty()){
                throw new Exception("Gender format is not respected");
            }
            if(ub.getEmail().equalsIgnoreCase("ERRORE")){
                throw new Exception("Wrong password");
            }
            if(gender.equalsIgnoreCase("m")) {
                newGender = "male";
            } else if(gender.equalsIgnoreCase("f")) {
                newGender = "female";
            } else if(gender.equalsIgnoreCase("o")){
                newGender = "other";
            } else{
                throw new Exception("Gender format is not respected");
            }
            if (ub.getGender().equalsIgnoreCase(newGender)){
                throw new Exception("New gender is the same as the old gender");
            }
            ub = new UserBeanDAO().changeGender(ub, gender);
            if (ub == null)
                throw new Exception("Something went wrong");
            request.setAttribute("success","./userpage.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request, response);
        }catch (Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
        }
    }
}