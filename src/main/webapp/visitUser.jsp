<%@ page import="model.user.UserBean" %>
<%@ page import="model.user.UserBeanDAO" %>
<%@ page import="model.parameters.Parameters" %>
<%@ page import="model.parameters.ParametersDAO" %>
<%@ page import="model.subscription.Subscription" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.personalTrainer.PersonalTrainer" %>
<%@ page import="model.personalTrainer.PersonalTrainerDAO" %>
<%@ page import="model.subscription.SubscriptionDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="parts/head.jsp" %>
    <%@include file="parts/meta.jsp" %>
    <%@include file="parts/navbar.jsp"%>
    <title>User Profile</title>
</head>
<body>
<% if(dataFromSession == null){
    response.sendRedirect(request.getContextPath() + "/index.jsp");
}else{
    UserBean ub = new UserBeanDAO().recoverInfos((String) request.getAttribute("emailUser"));
    String role = ub.getRole();
    String gender;
    Parameters userParam = new ParametersDAO().getParameters((String) request.getAttribute("emailUser"));
    PersonalTrainer pt = null;
    ArrayList<Subscription> subs = new ArrayList<>();
    if (role != null && role.equalsIgnoreCase("pt")) {
        pt = new PersonalTrainerDAO().retrieveInfo(dataFromSession);
        role = "personal trainer";
    } else if (role != null && role.equalsIgnoreCase("ptr")) {
        role = "requested personal trainer";
    } else {
        subs.add(new SubscriptionDAO().getSubscription(ub));
    }
    if (ub.getGender().equalsIgnoreCase("m")) {
        gender = "Male";
    } else if (ub.getGender().equalsIgnoreCase("f")) {
        gender = "Female";
    } else {
        gender = "Other";
    }%>
<section class="bgGradient" style="height: 100vh;">
    <div class="container py-5">
        <div class="row">
            <div class="col-lg-4">
                <div class="card mb-4">
                    <div class="card-body text-center">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3"><%=ub.getNome()%> <%=ub.getCognome()%></h5>
                        <p class="text-muted mb-1"><%=role%></p>
                        <div class="d-flex justify-content-center mb-2">
                            <a href="mailto:<%=ub.getEmail()%>" type="button" class="btn btn-outline-primary">Email me</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="card mb-4">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0 text-primary">Full Name</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%=ub.getNome()%> <%=ub.getCognome()%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0 text-primary">Email</p>
                            </div>
                            <div class="col-sm-9">
                                <p class="text-muted mb-0"><%=ub.getEmail()%></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <p class="mb-0 text-primary">Mobile</p>
                            </div>
                            <div class="col-sm-9 text-primary">
                                <p class="text-muted mb-0"><%=ub.getTelefono()%></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card mb-4 mb-md-0">
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
                                <%if(role.equalsIgnoreCase("pt") && pt != null){%>
                                <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Years of work <span class="text-black"><%=pt.getPtYears()%></span></p>
                                <%}%>
                                <%} else {%>
                                <p class="mb-1 text-primary" style="font-size: .77rem;">No Parameters Found</p>
                                <%}%>
                            </div>
                        </div>
                    </div>
                    <%if(role.equalsIgnoreCase("pt") && pt != null){%>
                    <div class="col-md-6">
                        <div class="card mb-4 mb-md-0 shadow">
                            <div class="card-body">
                                <p class="mb-4"><span class="text-primary font-italic me-1">description</span> <span class="text-muted">Your Description</span></p>
                                <p class="mb-1 text-muted" style="font-size: .77rem; text-align: justify"><%=pt.getDescription()%></p>
                            </div>
                        </div>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</section>
<%}%>
</body>
</html>
