package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;

import java.io.IOException;

import static java.lang.System.out;

@WebServlet(name = "ChangePassword", value = "/ChangePassword")
public class ServletChangePassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String nPsw = request.getParameter("newpassword");
            String supPsw = request.getParameter("confirmpassword");
            String psw = request.getParameter("currentpassword");
            if(!nPsw.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$"))
                throw new Exception("Password format is not respected");
            if(!supPsw.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$"))
                throw new Exception("Password format is not respected");
            if(!psw.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$"))
                throw new Exception("Password format is not respected");
            nPsw = PasswordEncryptionUtil.encryptPassword(nPsw);
            supPsw = PasswordEncryptionUtil.encryptPassword(supPsw);
            psw = PasswordEncryptionUtil.encryptPassword(psw);

            String mail = (String) session.getAttribute("email");
            UserBeanDAO ubd = new UserBeanDAO();
            UserBean user = ubd.loginUser(mail, psw);

            if(user.getEmail().equalsIgnoreCase("ERRORE")){
                throw new Exception("Wrong password");
            }

            if (nPsw.equals(psw)) {
                // nPsw è uguale a psw, restituisci un errore
                throw new Exception("New password is the same as the current password");
            }

            if (!nPsw.equals(supPsw)) {
                // nPsw non è uguale a SupportPsw, restituisci un errore
                throw new Exception("New Password and Confirm Password are not the same");
            }

            user = ubd.changePsw(user, psw, nPsw); // password cambiata
            if (user == null)
                throw new Exception("Something went wrong");
            request.setAttribute("success","./userpage.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
        }
    }
}