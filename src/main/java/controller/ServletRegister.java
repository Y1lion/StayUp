package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import model.utils.PasswordEncryptionUtil;
import model.user.UserBean;
import model.user.UserBeanDAO;

import static java.lang.System.out;

/**
 * The type Servlet register.
 */
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
        String ptCheck = request.getParameter("ptCheck");

        out.println("email: "+ID);
        out.println("ptCheck: "+ptCheck);
        try{
            if(!ID.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,})+$"))
                throw new Exception("Email format is not respected");
            if(ID.length()<6 || ID.length()>40)
                throw new Exception("Email length not respected");
            if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$"))
                throw new Exception("Password format is not respected");
            if(!nome.matches("^[A-Z][a-zA-Z]{1,50}$"))
                throw new Exception("Name format is not respected");
            if(!cognome.matches("^[A-Z][a-zA-Z]{1,50}$"))
                throw new Exception("Surname format is not respected");
            if(!telefono.matches("\\d{10}"))
                throw new Exception("Phone Number format is not respected");
            if(!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f") && !gender.equalsIgnoreCase("o"))
                throw new Exception("Gender format is not respected");
            if(ptCheck!=null && !ptCheck.equalsIgnoreCase("true") && !ptCheck.isEmpty())
                throw new Exception("Personal Trainer check format is not respected");


            password = PasswordEncryptionUtil.encryptPassword(password);
            UserBeanDAO ubDAO=new UserBeanDAO();
            UserBean ub = ubDAO.userRegistration(ID,password, nome, cognome, telefono, gender);
            if(ptCheck!=null && ptCheck.equalsIgnoreCase("true")){
                //TODO: Admin check and approve request
                ub = ubDAO.requestRolePT(ub);
            }
            out.println(ub.getEmail());
            if(!ub.getEmail().equals("duplicato") && !ub.getPsw().equals("duplicato")) {
                HttpSession sess=request.getSession(true);
                sess.setAttribute("email",ID);
                sess.setAttribute("name",ub.getNome());
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
            request.setAttribute("exceptionURL","./register.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request,response);
        }
    }
}