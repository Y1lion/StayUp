<%@ page import="model.personalTrainer.PersonalTrainerDAO" %>
<%@ page import="model.personalTrainer.PersonalTrainer" %>
<%@ page import="model.subscription.SubscriptionDAO" %>
<%@ page import="model.subscription.Subscription" %>
<%@ page import="java.util.List" %><%--
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
  if(dataFromSession==null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
  }
  else{
    PersonalTrainer pt= new PersonalTrainerDAO().retrieveInfo(dataFromSession);
    List<Subscription> subs=new SubscriptionDAO().getAllSubscriptions(pt);%>
<div class="container-fluid">
  <form action="addTrainingPlan" onsubmit="return controllaDate()">
    <div class="d-flex mx-auto justify-content-between w-75 mt-3 flex-wrap">

      <button class="btn col-lg-4 col-12 col-xl-3 btn-outline-primary align-self-end" onclick="window.location.reload();">Cancel Training plan</button>
      <div class="d-flex justify-content-center flex-column">
        <label for="dateStart" class="text-primary">Start data:</label>
        <input name="dateStart" id="dateStart" type="date" required class="col-lg-5 col-xl-5 col-12 form-control">
      </div>
      <div class="d-flex justify-content-center flex-column">
        <label for="dateEnd" class="text-primary">End data:</label>
        <input name="dateEnd" id="dateEnd" required type="date" class="col-lg-5 col-xl-5 col-12 form-control">
      </div>
      <input name="title" id="title" type="text" class="col-lg-12 col-xl-3 col-12 text-center form-control-lg" required placeholder="Title" pattern="^\S{2,30}$">
    </div>
    <div name="trainingSheet1" id="trainingSheet1" class="card w-75 shadow-lg mx-auto mt-5" >
      <div class="card-body">
        <div class="d-flex justify-content-between">
          <span class="h1 card-title text-primary fw-bold mb-0" >Day 1</span>
          <button type="button" class="btn btn-outline-secondary" onclick="addDay()">Add day</button>
        </div>
        <hr>
        <div id="thisExerciseD1" class="d-flex flex-wrap row g-3">
          <div class="col-md-6 col-sm-12">
            <label class="text-black ms-2" for="exerciseNameN1D1">Exercise name:</label>
            <input type="text" class="form-control" required name="formNameExerciseN1D1" id="exerciseNameN1D1" placeholder="Exercise name" pattern="^(?=\s*\S)([\w\s]{2,30})$">
          </div>
          <div class="col-md-2 col-sm-12">
            <label class="text-black ms-2" for="setsN1D1">Sets N°:</label>
            <input type="number" class="form-control" required name="formNameSetsN1D1" id="setsN1D1" placeholder="Sets N°" min="0" pattern="^[1-9]\d*$" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 109">
          </div>
          <div class="col-md-2 col-sm-12">
            <label class="text-black ms-2"for="repsN1D1">Reps N°:</label>
            <input type="number" class="form-control" required name="formNameRepsN1D1" id="repsN1D1" placeholder="Reps N°" min="0" pattern="^[1-9]\d*$" onkeydown="return event.keyCode !== 69 && event.keyCode !== 189 && event.keyCode !== 109">
          </div>
          <div class="col-md-2 col-sm-12">
            <label class="text-black ms-2"for="pauseN1D1">Rest:</label>
            <input class="form-control" name="formNamePauseN1D1" id="pauseN1D1" placeholder="Rest">
          </div>
        </div>
        <div id="exerciseAdder" class="mt-3 d-flex justify-content-end">
            <span  onclick="addExercise(1)" class="text-secondary">Add new exercise</span>
        </div>
      </div>
    </div>
    <div class="w-75 mx-auto mt-4 d-flex justify-content-end">
      <button class="btn btn-outline-secondary" type="button"  data-bs-toggle="modal" data-bs-target="#Subs">Save</button>
    </div>
    <input type="hidden" name="gg1" id="gg1" value="1">
    <input type="hidden" name="gg2" id="gg2" value="0">
    <input type="hidden" name="gg3" id="gg3" value="0">
    <input type="hidden" name="gg4" id="gg4" value="0">
    <input type="hidden" name="gg5" id="gg5" value="0">

    <div class="modal fade" id="Subs" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="SubsLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header bg-primary">
            <h5 class="modal-title " id="SubsLabel">Subscriptions list</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="table-responsive">
              <table class="table table-hover">
                <thead>
                <tr>
                  <th class="text-center" scope="col">Subscriptions</th>
                </tr>
                </thead>
                <tbody>
                <%for(Subscription sub: subs){
                %>
                <tr onclick="setEmailSub('<%=sub.getEmailUser()%>',this)">
                  <td class="text-center align-middle"><%=sub.getEmailUser()%></td>
                </tr>
                <%}%>
                </tbody>
              </table>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-secondary" data-bs-dismiss="modal">Confirm</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    <input type="hidden" name="emailSub" id="emailSub" value="none">
  </form>
</div>
<%}%>
<script src="js/utils.js"></script>
</body>
</html>
