package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import model.utils.RandomStringGenerator;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("sendEmail")
public class ServletSendEmail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String emailuser=request.getParameter("emailLog");
            String psw=RandomStringGenerator.generateRandomString();
            UserBean ub=new UserBeanDAO().checkEmail(emailuser);
            psw=PasswordEncryptionUtil.encryptPassword(psw);
            new UserBeanDAO().forgotPsw(ub,psw);
            Email email = new SimpleEmail();
            email.setHostName("smtp.postmarkapp.com"); // Indirizzo del server SMTP di Postmark
            email.setSmtpPort(587); // Numero di porta SMTP di Postmark
            email.setAuthenticator(new DefaultAuthenticator("Y1lion", "")); // Sostituisci con le tue credenziali
            email.setStartTLSEnabled(true); // Abilita STARTTLS
            email.setFrom("a.abbate20@studenti.unisa.it"); // Il mittente dell'email
            email.setSubject("New StayUp password");
            email.setMsg("Password: "+psw);
            email.addTo(emailuser); // Il destinatario dell'email
            email.send();
            System.out.println("Email sent successfully!");
        } catch (EmailException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
