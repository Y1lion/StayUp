/*package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import model.utils.RandomStringGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;


@WebServlet("/sendEmail")
public class ServletSendEmail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String senderEmail = "andymatty36@gmail.com";
        String recipientEmail = request.getParameter("emailLog");
        String subject = "New email for StayUp";
        UserBeanDAO ubd=new UserBeanDAO();
        UserBean ub=ubd.checkEmail(recipientEmail);
        String brevoApiKey=System.getenv("BREVO_API_KEY");
        if(!ub.getEmail().equals("ERRORE")) {
            String content = RandomStringGenerator.generateRandomString();
            try {
                ubd.forgotPsw(ub,PasswordEncryptionUtil.encryptPassword(content));
            } catch (NoSuchAlgorithmException e) {
                request.setAttribute("exception",e);
                request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
            }
            try {
                sendEmail(senderEmail, recipientEmail, subject, content, brevoApiKey);
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } catch (Exception e) {
                request.setAttribute("exception",e);
                request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
            }
        }
    }

    private void sendEmail(String senderEmail, String recipientEmail, String subject, String content,
                           String brevoApiKey) throws IOException {
        String brevoEndpoint = "https://api.brevo.com/v3/emailCampaigns";
        URL url = new URL(brevoEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("api-key", brevoApiKey);
        connection.setDoOutput(true);

        String emailParams = String.format("{\n  \"sender\": {\n    \"email\": \"%s\"\n  },\n  \"to\": [\n    {\n      \"email\": \"%s\"\n    }\n  ],\n  \"subject\": \"%s\",\n  \"htmlContent\": \"%s\"\n}\n", senderEmail, recipientEmail, subject, content);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = emailParams.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Email sent successfully
        } else {
            // Failed to send email
        }

        connection.disconnect();
    }
}*/
package controller;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.UserBean;
import model.user.UserBeanDAO;
import model.utils.PasswordEncryptionUtil;
import model.utils.RandomStringGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/sendEmail")
public class ServletSendEmail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ottenere i parametri dalla richiesta HTTP
        String recipient = request.getParameter("emailLog");
        String subject = "New password for StayUp";
        String content = RandomStringGenerator.generateRandomString();

        // Configurare le credenziali API SendGrid
        String apiKey = System.getenv("API_KEY");

        // Creare un oggetto Email
        Email from = new Email("a.abbate20@studenti.unisa.it");
        Email to = new Email(recipient);
        UserBeanDAO udb=new UserBeanDAO();
        UserBean ub=udb.checkEmail(recipient);

        Content messageContent = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, to, messageContent);

        // Creare un oggetto SendGrid
        SendGrid sg = new SendGrid(apiKey);

        // Creare una richiesta di invio email
        Request sendRequest = new Request();
        if(!ub.getEmail().equals("ERRORE")) {
            try {
                udb.forgotPsw(ub, PasswordEncryptionUtil.encryptPassword(content));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            try {
                // Impostare il payload della richiesta
                sendRequest.setMethod(Method.POST);
                sendRequest.setEndpoint("mail/send");
                sendRequest.setBody(mail.build());

                // Eseguire la richiesta di invio email
                Response sendResponse = sg.api(sendRequest);

                // Verificare il codice di stato della risposta
                if (sendResponse.getStatusCode() == 202) {
                    // Risposta alla richiesta HTTP
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
            } catch (IOException e) {
                // Gestire eventuali eccezioni
                request.setAttribute("exception",e);
                request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
            }
        }
    }
}
