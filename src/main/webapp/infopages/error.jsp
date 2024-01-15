<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<html>
<head>
    <%@include file="../parts/meta.jsp" %>
    <%@include file="../parts/head.jsp" %>
    <title>Errore - StayUp</title>
</head>
<body class="bg-light">
<%--<%@include file="../parts/navbar.jsp" %>--%>
<div class="container h-100">
    <div class="card w-50 text-white bg-danger my-5 mx-auto">
        <div class="card-header">
            <span class="small"><i class="fas fa-exclamation-circle"></i></span> Errore
        </div>
        <div class="card-body">
            <h5 class="card-title">Non è stato possibile eseguire la richiesta.</h5>
            <p class="card-text">Exception:
                <% Exception e = (Exception) request.getAttribute("exception");
                    out.println("<span class=\"card-text small text-white\">"+e.getMessage()+"</span>");
                %>
            </p>
            <p class="card-text text-right"><%out.print("<a href=\""+((String) request.getAttribute("exceptionURL"))+"\" class=\"text-white font-weight-bold\">Torna alla pagina precedente</a>");%></p>
        </div>
    </div>
</div>
<div class="container-fluid h-25"></div>
<%--<%@include file="../parts/footer.jsp" %>--%>
</body>
</html>