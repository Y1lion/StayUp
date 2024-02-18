package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;

import java.io.IOException;

@WebServlet(name = "ChangeNumber", value = "/ChangeNumber")
public class ServletChangeNumber extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            String number = request.getParameter("newnumber");
            String psw = request.getParameter("current_password3");
            psw = PasswordEncryptionUtil.encryptPassword(psw);
            UserBean ub = new UserBeanDAO().loginUser((String) session.getAttribute("email"),psw);
            if(!number.matches("\\d{10}"))
                throw new Exception("Number format is not respected");
            if(ub.getEmail().equalsIgnoreCase("ERRORE")){
                throw new Exception("Wrong password");
            }
            if (ub.getGender().equalsIgnoreCase(number)){
                throw new Exception("New number is the same as the old number");
            }
            ub = new UserBeanDAO().changeNumber(ub, number);
            if(ub==null)
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