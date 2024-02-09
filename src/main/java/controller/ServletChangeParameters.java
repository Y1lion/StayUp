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
            uParam.setWorkoutYears(Integer.parseInt(request.getParameter("parworkout")));

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