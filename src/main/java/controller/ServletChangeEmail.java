package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;

import java.io.IOException;

@WebServlet(name = "ChangeEmail", value = "/ChangeEmail")
public class ServletChangeEmail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String mail  = request.getParameter("newemail");
            String psw = request.getParameter("current_password");
            psw = PasswordEncryptionUtil.encryptPassword(psw);
            String oldMail = (String) session.getAttribute("email");
            if (!mail.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,})+$"))
                throw new Exception("Email format is not respected");
            UserBeanDAO ubd = new UserBeanDAO();
            UserBean user = ubd.loginUser(oldMail, psw);
            if(user.getEmail().equalsIgnoreCase("ERRORE") || mail.equalsIgnoreCase(oldMail)){
                throw new Exception("Wrong password or new email is the same as the old email");
            }
            user = ubd.changeEmail(user, user.getEmail(), mail); //mail cambiata
            if (user == null)
                throw new Exception("Something went wrong");
            session.setAttribute("email",user.getEmail());
            request.setAttribute("success","./userpage.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request,response);
        }
        catch(Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request,response);
        }
    }
}