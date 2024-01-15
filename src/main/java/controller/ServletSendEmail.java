//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import model.PasswordEncryptionUtil;
import model.RandomStringGenerator;
import model.UserBean;
import model.UserBeanDAO;

@WebServlet({"/sendEmail"})
public class ServletSendEmail extends HttpServlet {
    public ServletSendEmail() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String recipientEmail = request.getParameter("forgotemail");
            UserBeanDAO ubd = new UserBeanDAO();
            UserBean ub = ubd.checkEmail(recipientEmail);
            if (ub.getEmail().equals("ERRORE")) {
                throw new Exception("Email inesistente");
            }

            String senderEmail = "andrea.abbate20@studenti.unisa.it";
            String subject = "New password for FlyHigh";
            String content = RandomStringGenerator.generateRandomString();
            String sendinblueEndpoint = "https://api.sendinblue.com/v3/smtp/email";
            URL url = new URL(sendinblueEndpoint);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("api-key", "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");
            connection.setDoOutput(true);
            String emailParams = String.format("{\n  \"sender\": {\n    \"email\": \"%s\"\n  },\n  \"to\": [\n    {\n      \"email\": \"%s\"\n    }\n  ],\n  \"subject\": \"%s\",\n  \"htmlContent\": \"%s\"\n}\n", senderEmail, recipientEmail, subject, content);
            OutputStream outputStream = connection.getOutputStream();

            try {
                byte[] input = emailParams.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            } catch (Throwable var19) {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Throwable var18) {
                        var19.addSuppressed(var18);
                    }
                }

                throw var19;
            }

            if (outputStream != null) {
                outputStream.close();
            }

            StringBuilder responseBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            String line;
            try {
                while((line = reader.readLine()) != null) {
                    responseBuilder.append(line).append("\n");
                }
            } catch (Throwable var20) {
                try {
                    reader.close();
                } catch (Throwable var17) {
                    var20.addSuppressed(var17);
                }

                throw var20;
            }

            reader.close();
            connection.disconnect();
            ubd.forgotPsw(new UserBean(recipientEmail, "forgot"), PasswordEncryptionUtil.encryptPassword(content));
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
        } catch (Exception var21) {
            request.setAttribute("exception", var21);
            request.getRequestDispatcher("./infopages/error.jsp").forward(request, response);
        }

    }
}
