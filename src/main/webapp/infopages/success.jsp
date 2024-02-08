<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <%@include file="../parts/meta.jsp" %>
    <%@include file="../parts/head.jsp" %>
    <title>FlyHigh</title>
    <%
        String success = (String) request.getAttribute("success");
        int tempoDiRidirezione = 1; // Tempo in secondi
        String contentValue = tempoDiRidirezione + "; url=" + success;
        response.setHeader("refresh", contentValue);
    %>
</head>
<body class="bg-white">
<%--<%@include file="../parts/navbar.jsp"%>--%>
<div class="container">
    <div class="card w-50 text-white bg-success my-5 mx-auto">
        <div class="card-header">
            <span class="small"><i class="fas fa-check-circle"></i></span> Operazione eseguita
        </div>
        <div class="card-body">
            <h5 class="card-title">Stai per essere reindirizzato alla pagina precedente.</h5>
            <p class="card-text small"><%out.print("<a href=\""+ success +"\" class=\"text-white\">Nel caso in cui dovessero esserci problemi con il reindirizzameno, clicca per tornare alla pagina precedente.</a>");%></p>
        </div>
    </div>
</div>
<div class="container-fluid h-25"></div>
<%--<%@include file="../parts/footer.jsp"%>--%>
</body>
</html>