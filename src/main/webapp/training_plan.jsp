<%--
  Created by IntelliJ IDEA.
  User: Andrea
  Date: 09/02/2024
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Training plan</title>
  <%@include file="parts/head.jsp" %>
  <%@include file="parts/meta.jsp" %>
  <%@include file="parts/navbar.jsp"%>
  <link rel="stylesheet" href="css/custom.css">
  <link rel="stylesheet" href="css/training_plane.css">
</head>
<body>
<%
dataFromSession="yo";
if(dataFromSession==null)
  response.sendRedirect(request.getContextPath() + "/index.jsp");
else{
  int gg=1;%>
<div class="container-fluid">
  <form>
    <input name="title" id="title" type="text" class="mt-3 mx-auto text-center form-control" required placeholder="Title">
    <div name="trainingSheet<%=gg%>"class="card w-75 shadow-lg mx-auto mt-5" >
      <div class="card-body">
        <div class="d-flex justify-content-between">
          <span class="h1 card-title text-primary fw-bold mb-0" >Day <%=gg%></span>
          <button type="button" class="btn btn-outline-secondary" onclick="addDay()">Add day</button>
        </div>
        <hr>
        <div class="d-flex flex-wrap row g-3">
          <div class="col-md-6 col-sm-12"><input type="text" class="form-control" placeholder="Exercise name"></div>
          <div class="col-md-2 col-sm-12"><input type="number" class="form-control" placeholder="Sets N°" min="0" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 109"></div>
          <div class="col-md-2 col-sm-12"><input type="number" class="form-control" placeholder="Reps N°" min="0" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 109"></div>
          <div class="col-md-2 col-sm-12"><input class="form-control" placeholder="Rest pause"></div>
        </div>
        <div class="mt-3 d-flex justify-content-end">
            <span class=" h-4 text-secondary">Add new exercise</span>
        </div>
      </div>
    </div>
  </form>
</div>
<%}%>
<script src="js/utils.js"></script>
</body>
</html>
