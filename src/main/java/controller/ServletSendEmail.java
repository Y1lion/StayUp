package controller;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import model.utils.RandomStringGenerator;


import java.io.IOException;


@WebServlet("/sendEmail")
public class ServletSendEmail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String emailuser=request.getParameter("emailLog");
            if (!emailuser.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,})+$"))
                throw new Exception("Email format is not respected");
            if(emailuser.length()<6 || emailuser.length()>40)
                throw new Exception("Email lenght is not respected");
            String psw=RandomStringGenerator.generateRandomString();
            UserBean ub=new UserBeanDAO().checkEmail(emailuser);
            if (ub.getEmail().equalsIgnoreCase("ERRORE"))
                throw new Exception("Account doesn't exist");
            ApiClient client = Postmark.getApiClient("dc790940-a28a-4e55-9824-8cb69f51d804");
            Message message = new Message("a.abbate20@studenti.unisa.it", emailuser, "New Password for StayUp", "Your password: "+psw);
            message.setMessageStream("stayup");
            MessageResponse sending = client.deliverMessage(message);
            psw=PasswordEncryptionUtil.encryptPassword(psw);
            new UserBeanDAO().forgotPsw(ub,psw);
            System.out.println("Email sent successfully!");
            request.setAttribute("success","./login.jsp");
            request.getRequestDispatcher("./infopages/success.jsp").forward(request,response);
        } catch (Exception e) {
            request.setAttribute("exception",e);
            request.setAttribute("exceptionURL","./userpage.jsp");
            request.getRequestDispatcher("./infopages/error.jsp").forward(request,response);
        }
    }
}
