package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class ServletLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ID = request.getParameter("emailLog");
        String password = request.getParameter("passwordLog");
        try {
            password = PasswordEncryptionUtil.encryptPassword(password);
            UserBeanDAO usDAO = new UserBeanDAO();
            UserBean us=usDAO.loginUser(ID,password);
            if(!us.getEmail().equals("ERRORE")){
                HttpSession sess=request.getSession(true);
                sess.setAttribute("email",ID);
                sess.setAttribute("username",us.getNome());
                sess.setAttribute("surname",us.getCognome());
                sess.setAttribute("role",us.getRole());
                String sessID=sess.getId();
                Cookie sessionIdCk=new Cookie("JSESSIONID",sessID);
                sessionIdCk.setMaxAge(60*60*24);
                response.addCookie(sessionIdCk);
                request.getRequestDispatcher("./infopages/success.jsp").forward(request,response);
            }else{
                throw new Exception("Utente non esistente");
            }
        }catch(Exception e){
            request.setAttribute("exception",e);
            request.getRequestDispatcher("./infopages/error.jsp").forward(request,response);
        }
    }
}
