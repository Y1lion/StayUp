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

            if (Double.parseDouble(request.getParameter("parweight"))<10 || Double.parseDouble(request.getParameter("parweight"))>400)
                throw new Exception("Weight format is not respected");
            if (Double.parseDouble(request.getParameter("parlean"))<10 || Double.parseDouble(request.getParameter("parlean"))>400)
                throw new Exception("Lean mass format is not respected");
            if (Double.parseDouble(request.getParameter("parfat"))<10 || Double.parseDouble(request.getParameter("parfat"))>400)
                throw new Exception("Fat mass format is not respected");
            if (Double.parseDouble(request.getParameter("pararm"))<10 || Double.parseDouble(request.getParameter("pararm"))>100)
                throw new Exception("Arm Mensuration format is not respected");
            if (Double.parseDouble(request.getParameter("parleg"))<10 || Double.parseDouble(request.getParameter("parleg"))>100)
                throw new Exception("Leg Mensuration format is not respected");
            if (Double.parseDouble(request.getParameter("parchest"))<10 || Double.parseDouble(request.getParameter("parchest"))>100)
                throw new Exception("Chest Mensuration format is not respected");
            if (Double.parseDouble(request.getParameter("parhips"))<10 || Double.parseDouble(request.getParameter("parhips"))>100)
                throw new Exception("Hips Mensuration format is not respected");
            if (Double.parseDouble(request.getParameter("parshoulders"))<10 || Double.parseDouble(request.getParameter("parshoulders"))>100)
                throw new Exception("Shoulders Mensuration format is not respected");
            if (Double.parseDouble(request.getParameter("parworkout"))<0 || Double.parseDouble(request.getParameter("parworkout"))>60)
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

            Parameters uParam = new ParametersFacade().setParameters((String) session.getAttribute("email"), params);
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