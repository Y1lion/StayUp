package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

import static java.lang.System.out;

@WebServlet(name = "SeeProfile", value = "/SeeProfile")
public class ServletSeeProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getParameter("visitEmail");
        out.println("EMAIL ATTRIBUTE:"+email);
        request.setAttribute("emailUser",email);
        request.getRequestDispatcher("./visitUser.jsp").forward(request, response);
    }
}