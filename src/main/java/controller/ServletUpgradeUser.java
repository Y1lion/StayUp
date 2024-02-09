package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.personalTrainer.PersonalTrainer;
import model.personalTrainer.PersonalTrainerDAO;
import model.user.UserBean;
import model.user.UserBeanDAO;
import org.json.simple.JSONObject;

import java.io.IOException;

@WebServlet(name = "ServletUpgradeUser", value = "/ServletUpgradeUser")
public class ServletUpgradeUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        UserBeanDAO ubd=new UserBeanDAO();
        UserBean ub=ubd.recoverInfos(email);
        ubd.upgradeToPT(ub);
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", ub.getEmail());
        jsonUser.put("nome", ub.getNome());
        jsonUser.put("cognome", ub.getCognome());
        jsonUser.put("telefono", ub.getTelefono());
        // Invia la risposta contenente l'oggetto User in formato JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonUser.toString());
    }
}
