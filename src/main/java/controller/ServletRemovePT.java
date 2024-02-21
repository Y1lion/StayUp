package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.personalTrainer.PersonalTrainer;
import model.personalTrainer.PersonalTrainerFacade;
import model.user.UserBean;
import model.user.UserBeanFacade;

import java.io.IOException;

@WebServlet(name = "ServletRemovePT", value = "/ServletRemovePT")
public class ServletRemovePT extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        PersonalTrainerFacade ptDAO=new PersonalTrainerFacade();
        PersonalTrainer pt=ptDAO.retrieveInfo(email);
        UserBean ub=pt.getUser();
        ptDAO.deletePT(email);
        new UserBeanFacade().userRegistration(email,ub.getPsw(),ub.getNome(),ub.getCognome(),ub.getTelefono(),ub.getGender());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"success\": true }");
    }
}
