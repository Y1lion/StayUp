<%@ page import="model.parameters.Parameters" %>
<%@ page import="model.user.UserBean" %>
<%@ page import="model.user.UserBeanDAO" %>
<%@ page import="model.parameters.ParametersDAO" %>
<%@ page import="model.personalTrainer.PersonalTrainer" %>
<%@ page import="model.personalTrainer.PersonalTrainerDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="parts/head.jsp" %>
    <%@include file="parts/meta.jsp" %>
    <%@include file="parts/cookie.jsp"%>
    <link rel="stylesheet" href="css/register.css">
    <link rel="stylesheet" href="css/custom.css">
    <title>User Profile</title>
</head>
<body class="bg-primary">
<%
    UserBean ub = new UserBeanDAO().recoverInfos(dataFromSession);
    PersonalTrainer pt = null;
    ub = new UserBean("test","fake");
    ub.setTelefono("123123123");
    Parameters userParam = new ParametersDAO().getParameters(dataFromSession);
    if (roleSession!=null && roleSession.equalsIgnoreCase("personalTrainer")){
        pt = new PersonalTrainerDAO().retrieveInfo(dataFromSession);
    }
%>
<section>
    <div class="container py-5">
        <div class="row">
            <div class="col-lg-4">
                <div class="card mb-4">
                    <div class="card-body text-center">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3"><%=nameSession%> <%=surnameSession%></h5>
                        <p class="text-muted mb-1"><%=roleSession%></p>
                        <div class="d-flex justify-content-center mb-2">
                            <a href="mailto:email" type="button" class="btn btn-outline-primary">Email me</a>
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
                                <p class="mb-0 text-primary">Mobile</p>
                            </div>
                            <div class="col-sm-9">
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
                                    <p class="mb-1 text-primary" style="font-size: .77rem;">Weight</p>
                                    <div class="progress rounded" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 80%" aria-valuenow="80"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Lean Mass</p>
                                    <div class="progress rounded" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 72%" aria-valuenow="72"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Fat Mass</p>
                                    <div class="progress rounded" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 89%" aria-valuenow="89"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Arm Mensuration</p>
                                    <div class="progress rounded" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 55%" aria-valuenow="55"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Leg Mensuration</p>
                                    <div class="progress rounded mb-2" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 66%" aria-valuenow="66"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Chest Mensuration</p>
                                    <div class="progress rounded mb-2" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 66%" aria-valuenow="66"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Hips Mensuration</p>
                                    <div class="progress rounded mb-2" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 66%" aria-valuenow="66"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Shoulders Mensuration</p>
                                    <div class="progress rounded mb-2" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 66%" aria-valuenow="66"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Workout Years</p>
                                    <div class="progress rounded mb-2" style="height: 5px;">
                                        <div class="progress-bar" role="progressbar" style="width: 66%" aria-valuenow="66"
                                             aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                <%} else {%>
                                <p class="mb-1 text-primary" style="font-size: .77rem;">No Parameters Found</p>
                                <button type="button" class="btn btn-primary mt-2">Add Parameters</button>
                                <%}%>
                            </div>
                        </div>
                    </div>
                    <%if(roleSession!=null && roleSession.equalsIgnoreCase("personalTrainer") && pt != null){%>
                        <div class="col-md-6">
                            <div class="card mb-4 mb-md-0">
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
</body>
</html>
