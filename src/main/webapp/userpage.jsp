<%@ page import="model.parameters.Parameters" %>
<%@ page import="model.user.UserBean" %>
<%@ page import="model.user.UserBeanDAO" %>
<%@ page import="model.parameters.ParametersDAO" %>
<%@ page import="model.personalTrainer.PersonalTrainer" %>
<%@ page import="model.personalTrainer.PersonalTrainerDAO" %>
<%@ page import="model.subscription.Subscription" %>
<%@ page import="model.subscription.SubscriptionDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.trainingPlan.TrainingPlan" %>
<%@ page import="model.trainingPlan.TrainingPlanDAO" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="parts/head.jsp" %>
    <%@include file="parts/meta.jsp" %>
    <%@include file="parts/navbar.jsp"%>
    <title>User Profile</title>
    <script src="js/utils.js"></script>
</head>
<body>
<% if (dataFromSession == null){
    response.sendRedirect(request.getContextPath() + "/index.jsp");
}else {
    UserBean ub = new UserBeanDAO().recoverInfos(dataFromSession);
    String role = roleSession;
    String gender;
    PersonalTrainer pt = null;
    ArrayList<Subscription> subs = new ArrayList<>();
    ArrayList<TrainingPlan> trainingPlans = new ArrayList<>();
    Parameters userParam = new ParametersDAO().getParameters(dataFromSession);
    if (roleSession != null && roleSession.equalsIgnoreCase("pt")) {
        pt = new PersonalTrainerDAO().retrieveInfo(dataFromSession);
        role = "personal trainer";
        subs = new SubscriptionDAO().getAllSubscriptions(pt);
        trainingPlans = new TrainingPlanDAO().getAllTrainingPlans(pt);
    } else if (roleSession != null && roleSession.equalsIgnoreCase("ptr")) {
        role = "requested personal trainer";
    }
    if (ub.getGender().equalsIgnoreCase("m")) {
        gender = "Male";
    } else if (ub.getGender().equalsIgnoreCase("f")) {
        gender = "Female";
    } else {
        gender = "Other";
    }
    if (roleSession != null && (roleSession.equalsIgnoreCase("user") || roleSession.equalsIgnoreCase("admin"))) {
        subs.add(new SubscriptionDAO().getSubscription(ub));
        trainingPlans = new TrainingPlanDAO().getAllUserTrainingPlans(ub);
    }
%>
<section class="bgGradient" style="min-height: 100vh;">
    <div class="container py-5">
        <div class="row">
            <div class="col-lg-4">
                <div class="card mb-4 shadow">
                    <div class="card-body text-center">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3"><%=nameSession%> <%=surnameSession%></h5>
                        <p class="text-muted mb-1"><%=role%></p>
                        <div class="d-flex justify-content-center mb-2">
                            <a href="mailto:<%=ub.getEmail()%>" type="button" class="btn btn-outline-primary">Email me</a>
                        </div>
                    </div>
                </div>
                <div class="card mb-4 mb-md-0 shadow">
                    <div class="card-body">
                        <span class="text-primary font-italic me-1">training plan</span> <span class="text-muted">Your Training Plans</span>
                        <% if (!roleSession.equalsIgnoreCase("pt")){%>
                            <% if(!subs.isEmpty() && !subs.get(0).getEmailUser().equalsIgnoreCase("error") && subs.get(0).getActive()==1){%>
                            <p class="mb-4"><span class="text-primary font-italic me-1">subscription</span> <span class="text-muted"><%=new UserBeanDAO().checkEmail(subs.get(0).getEmailPt()).getNome()%> <%=new UserBeanDAO().checkEmail(subs.get(0).getEmailPt()).getCognome()%>, <%=subs.get(0).getDateEnd()%></span></p>
                            <% if(!trainingPlans.isEmpty() && !trainingPlans.get(0).getEmailUser().equalsIgnoreCase("error")){%>
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">Title</th>
                                        <th scope="col">Personal Trainer</th>
                                        <th scope="col">Date End</th>
                                    </tr>
                                    </thead>
                                    <tbody class="table-group-divider">
                                    <%int modalCount = 0;
                                        for(TrainingPlan t : trainingPlans){
                                        JSONObject jsonObject = new JSONObject(t.getExercises());
                                        String title = jsonObject.getString("Title");
                                        String modalId = "visualizeTPUser"+modalCount;
                                        modalCount++;%>
                                    <tr data-bs-toggle="modal" data-bs-target="#<%=modalId%>">
                                        <td><%=title%></td>
                                        <td><%=new UserBeanDAO().checkEmail(t.getEmailPt()).getNome()%> <%=new UserBeanDAO().checkEmail(t.getEmailPt()).getCognome()%></td>
                                        <td><%=t.getDateEnd()%></td>
                                    </tr>
                                    <div class="modal fade" id="<%=modalId%>" tabindex="-1" aria-labelledby="visualizeTPUserLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <form method="post" action="SeeProfile">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5 text-primary" id="visualizeTPUserLabel">Visualize Training Plan</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body text-black">
                                                        <div class="d-flex flex-column form-group h5">
                                                            <p class="text-primary h5 font-italic">Exercises:</p>
                                                            <textarea class="bgGradient text-light" style="height: 100px" disabled><%
                                                            JSONArray days = jsonObject.getJSONArray("Days");
                                                            for (int i = 0; i<days.length();i++){
                                                                JSONArray exercises = days.getJSONObject(i).getJSONArray("Exercises");
                                                                for(int j = 0; j<exercises.length(); j++){
                                                                    String exercise = exercises.getJSONObject(j).getString("Exercise");
                                                                    String reps = exercises.getJSONObject(i).getString("Reps");
                                                                    String sets = exercises.getJSONObject(i).getString("Sets");
                                                                    String rest = exercises.getJSONObject(i).getString("Rest");
                                                            %>Day: <%=i+1%>&#13;&#10;Exercise: <%=exercise%>&#13;&#10;Reps: <%=reps%>&#13;&#10;Sets: <%=sets%>&#13;&#10;Rest: <%=rest%>&#13;&#10;&#13;&#10;<%}}%></textarea>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-outline-primary" data-bs-dismiss="modal">See profile</button>
                                                    </div>
                                                    <input type="hidden" value="<%=t.getEmailPt()%>" name="visitEmail">
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <%}%>
                                    </tbody>
                                </table>
                            </div>
                            <%}else{%>
                            <p class="mb-4"><span class="text-muted">No Training Plans Found</span></p>
                            <%}%>
                            <%}else if(subs.get(0).getActive() != null && subs.get(0).getActive() == 2){ %>
                            <p class="mb-4"><span class="text-primary font-italic me-1">subscription</span> <span class="text-muted">You requested a subscription</span></p>
                            <%}else {%>
                        <p class="mb-4"><span class="text-primary font-italic me-1">subscription</span> <span class="text-muted">No Active Subscription</span></p>
                        <button type="button" class="btn btn-outline-primary mt-4" data-bs-toggle="modal" data-bs-target="#requestSubscription">Request Subscription</button>
                        <%} }else{%>
                        <% if(!trainingPlans.isEmpty() && !trainingPlans.get(0).getEmailUser().equalsIgnoreCase("error")){%>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th scope="col">Title</th>
                                    <th scope="col">User</th>
                                    <th scope="col">Date End</th>
                                </tr>
                                </thead>
                                <tbody class="table-group-divider">
                                <%int modalCount = 0;
                                    for(TrainingPlan t : trainingPlans){
                                    JSONObject jsonObject = new JSONObject(t.getExercises());
                                    System.out.println("JSONOBJECT: "+t.getExercises());
                                    String title = jsonObject.getString("Title");
                                    String modalId = "visualizeTP"+modalCount;
                                    modalCount++;%>
                                <tr data-bs-toggle="modal" data-bs-target="#<%=modalId%>">
                                    <td><%=title%></td>
                                    <td><%=new UserBeanDAO().checkEmail(t.getEmailUser()).getNome()%> <%=new UserBeanDAO().checkEmail(t.getEmailUser()).getCognome()%></td>
                                    <td><%=t.getDateEnd()%></td>
                                </tr>
                                <div class="modal fade" id="<%=modalId%>" tabindex="-1" aria-labelledby="visualizeTPLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <form method="post" action="#">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5 text-primary" id="visualizeTPLabel">Visualize Training Plan</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body text-black">
                                                    <div class="d-flex flex-column form-group h5">
                                                        <p class="text-primary h5 font-italic">Exercises:</p>
                                                        <textarea class="bgGradient text-light" style="height: 100px" disabled><%
                                                            JSONArray days = jsonObject.getJSONArray("Days");
                                                            for (int i = 0; i<days.length();i++){
                                                                JSONArray exercises = days.getJSONObject(i).getJSONArray("Exercises");
                                                                for(int j = 0; j<exercises.length(); j++){
                                                                    String exercise = exercises.getJSONObject(j).getString("Exercise");
                                                                    String reps = exercises.getJSONObject(i).getString("Reps");
                                                                    String sets = exercises.getJSONObject(i).getString("Sets");
                                                                    String rest = exercises.getJSONObject(i).getString("Rest");
                                                        %>Day: <%=i+1%>&#13;&#10;Exercise: <%=exercise%>&#13;&#10;Reps: <%=reps%>&#13;&#10;Sets: <%=sets%>&#13;&#10;Rest: <%=rest%>&#13;&#10;&#13;&#10;<%}}%></textarea>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                                                    <button type="submit" class="btn btn-outline-primary" formaction="SeeProfile" data-bs-dismiss="modal">See profile</button>
                                                    <button type="submit" class="btn btn-outline-danger" formaction="DeleteTrainingPlan" data-bs-dismiss="modal">Delete Training Plan</button>
                                                </div>
                                                <input type="hidden" value="<%=t.getEmailUser()%>" name="visitEmail">
                                                <textarea class="d-none" name="exercisesString"><%=t.getExercises()%></textarea>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <%}%>
                                </tbody>
                            </table>
                            <a href="training_plan.jsp" type="button" class="btn btn-outline-primary">Create new Training Plan</a>
                        </div>
                        <%}else{%>
                        <p class="mb-4"><span class="text-muted">No Training Plans Found</span></p>
                        <a href="training_plan.jsp" type="button" class="btn btn-outline-primary">Create new Training Plan</a>
                        <%}}%>
                    </div>
                    <%if(roleSession.equalsIgnoreCase("pt")){%>
                    <div class="card-body">
                        <span class="text-primary font-italic me-1">subscribers</span> <span class="text-muted">Your Subscribers</span>
                            <% if(!subs.isEmpty() && !subs.get(0).getEmailUser().equalsIgnoreCase("error")){%>
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">Name</th>
                                        <th scope="col">Surname</th>
                                        <th scope="col">Start Subscription</th>
                                        <th scope="col">Is Active</th>
                                    </tr>
                                    </thead>
                                    <tbody class="table-group-divider">
                                    <%int modalCount = 0;
                                        for (Subscription s : subs){
                                        if(s.getActive() > 0){
                                            if(s.getActive() == 2){
                                                String modalId = "acceptSubscription"+modalCount;
                                                modalCount++;%>
                                    <tr data-bs-toggle="modal" data-bs-target="#<%=modalId%>">
                                        <td><%=new UserBeanDAO().checkEmail(s.getEmailUser()).getNome()%></td>
                                        <td><%=new UserBeanDAO().checkEmail(s.getEmailUser()).getCognome()%></td>
                                        <td><%=s.getDateEnd()%></td>
                                        <td>Pending</td>
                                    </tr>
                                    <div class="modal fade" id="<%=modalId%>" tabindex="-1" aria-labelledby="acceptSubscriptionLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5 text-primary" id="acceptSubscriptionLabel">Accept Subscription</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <form method="post" action="#">
                                                    <div class="modal-body text-black">
                                                        <div class="d-flex flex-column form-group h5">
                                                            <p class="text-black">Do you want to accept <%=new UserBeanDAO().checkEmail(s.getEmailUser()).getNome()%> <%=new UserBeanDAO().checkEmail(s.getEmailUser()).getCognome()%>'s subscription?</p>
                                                            <input type="hidden" name="visitEmail" value="<%=s.getEmailUser()%>">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" formaction="SeeProfile" class="btn btn-outline-warning">Visit Profile</button>
                                                        <button type="submit" formaction="RefuseSubscription" class="btn btn-outline-primary">No</button>
                                                        <button type="submit" formaction="AcceptSubscription" class="btn btn-outline-primary">Yes</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <%} else{
                                            String modalId = "optionsUser"+modalCount;
                                            modalCount++;
                                    %>
                                    <tr data-bs-toggle="modal" data-bs-target="#<%=modalId%>">
                                        <td><%=new UserBeanDAO().checkEmail(s.getEmailUser()).getNome()%></td>
                                        <td><%=new UserBeanDAO().checkEmail(s.getEmailUser()).getCognome()%></td>
                                        <td><%=s.getDateEnd()%></td>
                                        <td>Active</td>
                                    </tr>
                                    <div class="modal fade" id="<%=modalId%>" tabindex="-1" aria-labelledby="optionsUserLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5 text-primary" id="optionsUserLabel">Options User</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <form method="post" action="SeeProfile">
                                                    <div class="modal-body text-black">
                                                        <p><span class="text-primary font-italic h5">Start Subscription</span> <span class="text-muted"><%=s.getDateStart()%></span></p>
                                                        <p><span class="text-primary font-italic h5">End Subscription</span> <span class="text-muted"><%=s.getDateEnd()%></span></p>
                                                        <input class="text-primary" type="hidden" name="visitEmail" id="visitEmail" value="<%=s.getEmailUser()%>">
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-outline-primary">Visit Profile</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <%}}}%>
                                    </tbody>
                                </table>
                            </div>
                            <%} else {%>
                        <p class="mb-4"><span class="text-primary font-italic me-1">subscription</span> <span class="text-muted">No Active Subscriptions</span></p>
                        <%}%>
                    </div>
                    <%}%>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="card mb-4 shadow">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0 text-primary">Full Name</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%=nameSession%> <%=surnameSession%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0 text-primary">Email</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%=dataFromSession%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0 text-primary">Gender</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%=gender%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0 text-primary">Mobile</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%=ub.getTelefono()%></p>
                            </div>
                        </div>
                        <button type="button" class="btn btn-outline-primary mt-4" data-bs-toggle="modal" data-bs-target="#changePersonalInfo">Change Personal Info</button>
                        <button type="button" class="btn btn-outline-danger mt-4" data-bs-toggle="modal" data-bs-target="#deleteProfile">Delete my profile</button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card mb-4 mb-md-0 shadow">
                            <div class="card-body">
                                <p class="mb-4"><span class="text-primary font-italic me-1">parameters</span> <span class="text-muted">Personal Parameters</span>
                                </p>
                                <% if(userParam != null && !userParam.getEmail().equalsIgnoreCase("error")){  %>
                                    <p class="mb-1 text-primary" style="font-size: .77rem;">Weight <span class="text-black"><%=userParam.getWeight()%> kg</span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Lean Mass <span class="text-black"><%=userParam.getLean_mass()%> cm</span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Fat Mass <span class="text-black"><%=userParam.getFat_mass()%> cm</span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Arm Mensuration <span class="text-black"><%=userParam.getArm_mis()%> cm</span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Leg Mensuration <span class="text-black"><%=userParam.getLeg_mis()%> cm</span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Chest Mensuration <span class="text-black"><%=userParam.getChest_mis()%> cm</span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Hips Mensuration <span class="text-black"><%=userParam.getHips_mis()%> cm</span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Shoulders Mensuration <span class="text-black"><%=userParam.getShoulders_mis()%> cm</span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Workout Years <span class="text-black"><%=userParam.getWorkoutYears()%></span></p>
                                <%if(roleSession.equalsIgnoreCase("pt") && pt != null){%>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Years of work <span class="text-black"><%=pt.getPtYears()%></span></p>
                                <%}%>
                                    <button type="button" class="btn btn-outline-primary mt-2" data-bs-toggle="modal" data-bs-target="#addParameters">Change Parameters</button>
                                <%} else {%>
                                <p class="mb-1 text-primary" style="font-size: .77rem;">No Parameters Found</p>
                                <button type="button" class="btn btn-outline-primary mt-2" data-bs-toggle="modal" data-bs-target="#addParameters">Add Parameters</button>
                                <%}%>
                            </div>
                        </div>
                    </div>
                    <%if(roleSession.equalsIgnoreCase("pt") && pt != null){%>
                        <div class="col-md-6">
                            <div class="card mb-4 mb-md-0 shadow">
                                <div class="card-body">
                                    <p class="mb-4"><span class="text-primary font-italic me-1">description</span> <span class="text-muted">Your Description</span></p>
                                    <p class="mb-1 text-muted" style="font-size: .77rem; text-align: justify"><%=pt.getDescription()%></p>
                                    <button type="button" class="btn btn-outline-primary mt-2" data-bs-toggle="modal" data-bs-target="#changeDescription">Change Description</button>
                                </div>
                            </div>
                        </div>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</section>
<div class="modal fade" id="addParameters" tabindex="-1" aria-labelledby="addParametersLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="addParametersLabel">Add Parameters</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <%if(userParam != null && !userParam.getEmail().equalsIgnoreCase("error")){%>
            <form method="get" action="ChangeParameters">
            <%}else{%>
            <form method="get" action="AddParameters">
            <%}%>
                <div class="modal-body text-black">
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Weight" name="parweight" id="parweight" required step="any" min=0 max=400>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Lean Mass" name="parlean" id="parlean" required step="any" min=0 max=400>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Fat Mass" name="parfat" id="parfat" required step="any" min=0 max=400>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Arm Mensuration" name="pararm" id="pararm" required step="any" min=0 max=400>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Leg Mensuration" name="parleg" id="parleg" required step="any" min=0 max=400>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Chest Mensuration" name="parchest" id="parchest" required step="any" min=0 max=400>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Hips Mensuration" name="parhips" id="parhips" required step="any" min=0 max=400>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Shoulder Mensuration" name="parshoulders" id="parshoulders" required step="any" min=0 max=400>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="number" class="form-control text-primary" placeholder="Workout Years" name="parworkout" id="parworkout" required step="1" min=0 max=60>
                    </div>
                    <%if(roleSession.equalsIgnoreCase("pt") && pt != null){%>
                        <div class="d-flex flex-column form-group h5">
                            <input type="number" class="form-control text-primary" placeholder="Years of work" name="parptyears" id="parptyears" required step="1" min=0 max=60>
                        </div>
                    <%}%>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Current Password" name="currentpassword1" id="currentpassword1" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button8" onclick="togglePasswordVisibility('currentpassword1','toggle-button8')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-outline-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%}%>

<div class="modal fade" id="changePersonalInfo" tabindex="-1" aria-labelledby="changePersonalInfoLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="changePersonalInfoLabel">Change Personal Info</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-black">
                <div class="btn-group-vertical" role="group" aria-label="Personal Info Buttons">
                    <button type="button" class="btn btn-sm btn-outline-secondary" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#changePassword">Change Password</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#changeEmail">Change Email</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#changeMobile">Change Mobile</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#changeName">Change Name</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary" data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#changeGender">Change Gender</button>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="changePassword" tabindex="-1" aria-labelledby="changePasswordLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="changePasswordLabel">Change Password</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="ChangePassword">
                <div class="modal-body text-black">
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Password" name="newpassword" id="newpassword" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button" onclick="togglePasswordVisibility('newpassword','toggle-button')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Confirm Password" name="confirmpassword" id="confirmpassword" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button2" onclick="togglePasswordVisibility('confirmpassword','toggle-button2')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Current Password" name="currentpassword" id="currentpassword" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button3" onclick="togglePasswordVisibility('currentpassword','toggle-button3')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-outline-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="changeEmail" tabindex="-1" aria-labelledby="changeEmailLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="changeEmailLabel">Change Email</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="ChangeEmail">
                <div class="modal-body text-black">
                    <div class="d-flex flex-column form-group h5">
                        <input type="email" class="form-control text-primary" placeholder="your@email.com" name="newemail" id="newemail" required pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,})+$">
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Current Password" name="current_password" id="current_password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button0" onclick="togglePasswordVisibility('current_password','toggle-button0')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-outline-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="changeName" tabindex="-1" aria-labelledby="changeNameLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="changeNameLabel">Change Name</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="ChangeName">
                <div class="modal-body text-black">
                    <div class="d-flex flex-column form-group h5">
                        <input type="text" class="form-control text-primary" placeholder="name" name="newname" id="newname" pattern="^[A-Z][a-zA-Z]{1,50}$" oninvalid="alert('Name must contains at least 1 character and maximum 50, furthermore the only acceptable characters are: \nA to Z; \na to z; \nÀ to ÿ; \n')" required>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="text" class="form-control text-primary" placeholder="surname" name="newsurname" id="newsurname" required pattern="^[A-Z][a-zA-Z]{1,50}$" oninvalid="alert('Surname must contains at least 1 character and maximum 50, furthermore the only acceptable characters are: \nA to Z; \na to z; \nÀ to ÿ; \n')">
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Current Password" name="current_password1" id="current_password1" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button4" onclick="togglePasswordVisibility('current_password1','toggle-button4')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-outline-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="changeGender" tabindex="-1" aria-labelledby="changeGenderLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="changeGenderLabel">Change Gender</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="ChangeGender">
                <div class="modal-body text-black">
                    <div class="d-flex flex-column form-group h5">
                        <div class="form-check">
                            <input type="radio" class="form-check-input" name="gender" id="male" value="m" required>
                            <label for="male" class="form-check-label mt-1 text-primary">Male</label>
                        </div>
                        <div class="form-check">
                            <input type="radio" class="form-check-input" name="gender" id="female" value="f" required>
                            <label for="female" class="form-check-label mt-1 text-primary">Female</label>
                        </div>
                        <div class="form-check">
                            <input type="radio" class="form-check-input" name="gender" id="other" value="o" required>
                            <label for="other" class="form-check-label mt-1 text-primary">Other</label>
                        </div>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Current Password" name="current_password2" id="current_password2" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button5" onclick="togglePasswordVisibility('current_password2','toggle-button5')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-outline-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="changeMobile" tabindex="-1" aria-labelledby="changeMobileLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="changeMobileLabel">Change Number</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="ChangeNumber">
                <div class="modal-body text-black">
                    <div class="d-flex flex-column form-group h5">
                        <input type="tel" class="form-control text-primary" placeholder="telephone" name="newnumber" id="newnumber" required pattern="\d{10}" oninvalid="alert('Telephone must contains only 10 numbers.')">
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Current Password" name="current_password3" id="current_password3" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button6" onclick="togglePasswordVisibility('current_password3','toggle-button6')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-outline-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="changeDescription" tabindex="-1" aria-labelledby="changeDescriptionLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="changeDescriptionLabel">Change Description</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="ChangeDescription">
                <div class="modal-body text-black">
                    <div class="d-flex flex-column form-group h5 form-floating">
                        <textarea class="form-control text-muted" placeholder="Leave a comment here" id="newdescription" name="newdescription" style="height: 100px"></textarea>
                        <label for="newdescription" class="text-primary" style="font-size: 0.77rem;">Description</label>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Current Password" name="current_password4" id="current_password4" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button7" onclick="togglePasswordVisibility('current_password4','toggle-button7')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-outline-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade modal-lg" id="requestSubscription" tabindex="-1" aria-labelledby="requestSubscriptionLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="#">
                <div class="modal-header bg-primary">
                    <h5 class="modal-title " id="requestSubscriptionLabel">Personal Trainers list</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="table-responsive">
                        <label for="dateEnd" class="text-muted">Insert date end subscription:</label>
                        <input name="dateEnd" id="dateEnd" type="date" required class="text-muted">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th class="text-center" scope="col">Name</th>
                                <th class="text-center" scope="col">Surname</th>
                                <th class="text-center" scope="col">Email</th>
                                <th class="text-center" scope="col">Years of work</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% List<PersonalTrainer> allPts = new PersonalTrainerDAO().retrieveAll();
                            if(allPts != null && !allPts.isEmpty()){
                                for(PersonalTrainer personalTrainer: allPts){
                            %>
                            <tr onclick="setEmailPT('<%=personalTrainer.getUser().getEmail()%>',this)">
                                <td class="text-center align-middle"><%=personalTrainer.getUser().getNome()%></td>
                                <td class="text-center align-middle"><%=personalTrainer.getUser().getCognome()%></td>
                                <td class="text-center align-middle"><%=personalTrainer.getUser().getEmail()%></td>
                                <td class="text-center align-middle"><%=personalTrainer.getPtYears()%></td>
                            </tr>
                            <%}}%>
                            </tbody>
                        </table>
                        <input type="hidden" name="visitEmail" id="emailPT">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-outline-warning" data-bs-dismiss="modal" formaction="SeeProfile" formnovalidate>Visit Profile</button>
                    <button type="submit" class="btn btn-outline-secondary" formaction="RequestSubscription">Confirm</button>
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="deleteProfile" tabindex="-1" aria-labelledby="deleteProfileLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5 text-primary" id="deleteProfileLabel">Delete Profile</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" action="DeleteProfile">
                <div class="modal-body text-black">
                    <div class="d-flex flex-column form-group h5">
                        <p class="text-black">Are you sure you want to delete your profile? This action is <span class="text-danger fs-4 fw-semibold">IRREVERSIBLE</span></p>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <div class="input-group">
                            <input type="password" class="form-control text-primary" placeholder="Password" name="deleteProfilePassword" id="deleteProfilePassword" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')">
                            <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="deleteProfileToggle" onclick="togglePasswordVisibility('deleteProfilePassword','deleteProfileToggle')"><i class="fa-solid fa-eye"></i></button>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-outline-primary">Yes</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
