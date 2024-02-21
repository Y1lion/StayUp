package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanFacade;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletUpgradeUser", value = "/ServletUpgradeUser")
public class ServletUpgradeUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        try {
            if (!email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,})+$"))
                throw new Exception("Email format is not respected");
            if (email.length() < 6 || email.length() > 40)
                throw new Exception("Email length is not respected");
            UserBeanFacade ubd = new UserBeanFacade();
            UserBean ub = ubd.recoverInfos(email);
            ubd.upgradeToPT(ub);
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("email", ub.getEmail());
            jsonUser.put("nome", ub.getNome());
            jsonUser.put("cognome", ub.getCognome());
            jsonUser.put("telefono", ub.getTelefono());
            // Invia la risposta contenente l'oggetto User in formato JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonUser.toJSONString());
            out.flush();
        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.setAttribute("exceptionURL", "./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
        }
    }

}
