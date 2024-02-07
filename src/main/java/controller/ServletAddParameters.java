package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.parameters.Parameters;
import model.parameters.ParametersDAO;
import model.personalTrainer.PersonalTrainer;
import model.personalTrainer.PersonalTrainerDAO;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;

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

            String psw = request.getParameter("currentpassword1");
            psw = PasswordEncryptionUtil.encryptPassword(psw);
            UserBean ub = new UserBeanDAO().loginUser((String) session.getAttribute("email"),psw);

            if(ub.getEmail().equalsIgnoreCase("ERRORE")){
                throw new Exception("Wrong password");
            }

            String ptYears = request.getParameter("parptyears");
            out.println("ptYears: "+ptYears);
            if (ptYears != null && !ptYears.isEmpty()){
                PersonalTrainer pt = new PersonalTrainer(ub);
                pt = new PersonalTrainerDAO().changePTYears(pt, Integer.valueOf(ptYears));
            }

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