package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;

import java.io.IOException;

@WebServlet(name = "ChangeName", value = "/ChangeName")
public class ServletChangeName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            String name = request.getParameter("newname");
            String surname = request.getParameter("newsurname");

            if(!name.matches("^[A-Z][a-zA-Z]{1,50}$"))
                throw new Exception("Name format is not respected");
            if(!surname.matches("^[A-Z][a-zA-Z]{1,50}$"))
                throw new Exception("Surname format is not respected");

            String psw = request.getParameter("current_password1");
            psw = PasswordEncryptionUtil.encryptPassword(psw);
            UserBean ub = new UserBeanDAO().loginUser((String) session.getAttribute("email"),psw);

            if(ub.getEmail().equalsIgnoreCase("ERRORE")){
                throw new Exception("Wrong password");
            }

            if (ub.getNome().equalsIgnoreCase(name)){
                throw new Exception("New name is the same as the old name");
            }

            if (ub.getCognome().equalsIgnoreCase(surname)){
                throw new Exception("New surname is the same as the old surname");
            }
            ub = new UserBeanDAO().changeName(ub, name, surname);
            if(ub == null)
                throw new Exception("Something went wrong");
            session.setAttribute("name",ub.getNome());
            session.setAttribute("surname",ub.getCognome());
            request.setAttribute("success","./userpage.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request, response);
        }catch (Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
        }
    }
}