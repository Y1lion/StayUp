package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.parameters.Parameters;
import model.parameters.ParametersDAO;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;

@WebServlet(name = "AddParameters", value = "/AddParameters")
public class ServletAddParameters extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            ArrayList<Double> params = new ArrayList<>();
            params.add(Double.parseDouble(request.getParameter("parweight")));
            params.add(Double.parseDouble(request.getParameter("parlean")));
            params.add(Double.parseDouble(request.getParameter("parfat")));
            params.add(Double.parseDouble(request.getParameter("pararm")));
            params.add(Double.parseDouble(request.getParameter("parleg")));
            params.add(Double.parseDouble(request.getParameter("parchest")));
            params.add(Double.parseDouble(request.getParameter("parhips")));
            params.add(Double.parseDouble(request.getParameter("parshoulders")));
            params.add(Double.parseDouble(request.getParameter("parworkout")));
            Parameters uParam = new ParametersDAO().setParameters((String) session.getAttribute("email"), params);
            request.setAttribute("success","./userpage.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request,response);
        }catch (Exception e){
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}