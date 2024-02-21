package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.parameters.Parameters;
import model.parameters.ParametersFacade;
import model.personalTrainer.PersonalTrainer;
import model.personalTrainer.PersonalTrainerFacade;
import model.user.UserBean;
import model.user.UserBeanFacade;
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
            Parameters uParam = new ParametersFacade().getParameters((String) session.getAttribute("email"));
            uParam.setWeight(Double.parseDouble(request.getParameter("parweight")));
            uParam.setLean_mass(Double.parseDouble(request.getParameter("parlean")));
            uParam.setFat_mass(Double.parseDouble(request.getParameter("parfat")));
            uParam.setArm_mis(Double.parseDouble(request.getParameter("pararm")));
            uParam.setLeg_mis(Double.parseDouble(request.getParameter("parleg")));
            uParam.setChest_mis(Double.parseDouble(request.getParameter("parchest")));
            uParam.setHips_mis(Double.parseDouble(request.getParameter("parhips")));
            uParam.setShoulders_mis(Double.parseDouble(request.getParameter("parshoulders")));
            uParam.setWorkoutYears(Integer.parseInt(request.getParameter("parworkout")));

            if (uParam.getWeight()<10 || uParam.getWeight()>400)
                throw new Exception("Weight format is not respected");
            if (uParam.getLean_mass()<10 || uParam.getLean_mass()>400)
                throw new Exception("Lean mass format is not respected");
            if (uParam.getFat_mass()<10 || uParam.getFat_mass()>400)
                throw new Exception("Fat mass format is not respected");
            if (uParam.getArm_mis()<10 || uParam.getArm_mis()>100)
                throw new Exception("Arm Mensuration format is not respected");
            if (uParam.getLeg_mis()<10 || uParam.getLeg_mis()>100)
                throw new Exception("Leg Mensuration format is not respected");
            if (uParam.getChest_mis()<10 || uParam.getChest_mis()>100)
                throw new Exception("Chest Mensuration format is not respected");
            if (uParam.getHips_mis()<10 || uParam.getHips_mis()>100)
                throw new Exception("Hips Mensuration format is not respected");
            if (uParam.getShoulders_mis()<10 || uParam.getShoulders_mis()>100)
                throw new Exception("Shoulders Mensuration format is not respected");
            if (uParam.getWorkoutYears()<0 || uParam.getWorkoutYears()>60)
                throw new Exception("Workout Years format is not respected");

            String psw = request.getParameter("currentpassword1");
            psw = PasswordEncryptionUtil.encryptPassword(psw);
            UserBean ub = new UserBeanFacade().loginUser((String) session.getAttribute("email"),psw);

            if(ub.getEmail().equalsIgnoreCase("ERRORE")){
                throw new Exception("Wrong password");
            }

            String ptYears = request.getParameter("parptyears");
            out.println("ptYears: "+ptYears);
            if (ptYears != null && !ptYears.isEmpty()){
                if(Integer.parseInt(ptYears)<0 || Integer.parseInt(ptYears)>60)
                    throw new Exception("Personal Trainer Years format is not respected");
                PersonalTrainer pt = new PersonalTrainer(ub);
                pt = new PersonalTrainerFacade().changePTYears(pt, Integer.valueOf(ptYears));
                if (pt == null)
                    throw new Exception("Something went wrong");
            }

            uParam = new ParametersFacade().changeParameters(uParam);
            if(uParam == null)
                throw new Exception("Something went wrong");
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