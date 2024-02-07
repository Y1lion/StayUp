package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.parameters.Parameters;
import model.parameters.ParametersDAO;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;

@WebServlet(name = "ChangeParameters", value = "/ChangeParameters")
public class ServletChangeParameters extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            Parameters uParam = new ParametersDAO().getParameters((String) session.getAttribute("email"));
            uParam.setWeight(Double.parseDouble(request.getParameter("parweight")));
            uParam.setLean_mass(Double.parseDouble(request.getParameter("parlean")));
            uParam.setFat_mass(Double.parseDouble(request.getParameter("parfat")));
            uParam.setArm_mis(Double.parseDouble(request.getParameter("pararm")));
            uParam.setLeg_mis(Double.parseDouble(request.getParameter("parleg")));
            uParam.setChest_mis(Double.parseDouble(request.getParameter("parchest")));
            uParam.setHips_mis(Double.parseDouble(request.getParameter("parhips")));
            uParam.setShoulders_mis(Double.parseDouble(request.getParameter("parshoulders")));
            out.println("BEFORE SET: "+Integer.parseInt(request.getParameter("parworkout")));
            uParam.setWorkoutYears(Integer.parseInt(request.getParameter("parworkout")));
            out.println("AFTER SET: "+uParam.getWorkoutYears());
            uParam = new ParametersDAO().changeParameters(uParam);
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