<%--
  Created by IntelliJ IDEA.
  User: Andrea
  Date: 15/01/2024
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="parts/meta.jsp"%>
    <%@ include file="parts/head.jsp"%>
    <link rel="stylesheet" href="css/forgotCSS.css">
    <title>Forgot_Password</title>
</head>
<body style="background: #dddddd">
    <div class="container-fluid h-100 d-flex justify-content-center align-items-center" style="color: aliceblue">
        <div class="h-auto d-flex w-25 p-4 flex-column shadow-lg" id="divForForgot" style="background-color:#1789b5;border-radius: 5px">
            <p class="h1 align-self-center">Reset Password</p>
            <form method="post"  action="sendEmail">
                <div class="d-flex mt-2 flex-column w-100">
                    <input type="email" name="emailLog" style="margin-top:20px" class="form-control" placeholder="Email">
                    <a href="login.jsp" class="mt-3 text-end" style="color: aliceblue;font-size: 15px;margin-right: 5px">Return to login</a>
                    <button type="submit" class="btn btn-primary mx-auto shadow-lg rounded-pill w-75"style="margin-top: 40px">Submit</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
