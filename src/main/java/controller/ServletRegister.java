package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import model.PasswordEncryptionUtil;
import model.UserBean;
import model.UserBeanDAO;

import static java.lang.System.out;

@WebServlet(name = "register", value = "/register")
public class ServletRegister extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ID = request.getParameter("regemail");
        String password = request.getParameter("regpass");
        String nome = request.getParameter("regname");
        String cognome = request.getParameter("regsurname");
        String telefono = request.getParameter("regtel");
        String gender = request.getParameter("gender");
        int workoutYears = Integer.parseInt(request.getParameter("regwYears"));


        out.println("email: "+ID);
        try{
            password = PasswordEncryptionUtil.encryptPassword(password);
            UserBeanDAO ubDAO=new UserBeanDAO();
            UserBean ub = ubDAO.UserRegistration(ID,password, nome, cognome, telefono, gender, workoutYears);
            out.println(ub.getEmail());
            if(ub.getEmail() != "duplicato" &&  ub.getPsw() != "duplicato") {
                HttpSession sess=request.getSession(true);
                sess.setAttribute("email",ID);
                sess.setAttribute("username",ub.getNome());
                sess.setAttribute("surname",ub.getCognome());
                sess.setAttribute("role",ub.getRole());
                String sessID=sess.getId();
                Cookie sessionIdCk=new Cookie("JSESSIONID",sessID);
                sessionIdCk.setMaxAge(60*60*24);
                response.addCookie(sessionIdCk);
                request.setAttribute("success","./index.jsp");
                request.getRequestDispatcher("./infopages/success.jsp").forward(request,response);
            }else{
                throw new Exception("Utente esistente");
            }
        }catch (Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./Register.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request,response);
        }
    }
}