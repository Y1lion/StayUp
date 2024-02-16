<%@ page import="model.user.UserBean" %>
<%@ page import="model.user.UserBeanDAO" %>
<%@ page import="model.personalTrainer.PersonalTrainer" %>
<%@ page import="java.util.List" %>
<%@ page import="model.personalTrainer.PersonalTrainerDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Home</title>
    <%@include file="parts/head.jsp" %>
    <%@include file="parts/meta.jsp" %>
    <%@include file="parts/navbar.jsp"%>
    <script src="js/utils.js"></script>
    <link rel="stylesheet" href="css/custom.css">
</head>
<body class="bgGradient">
<%if(dataFromSession==null)
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    else{
        UserBean ub=new UserBeanDAO().recoverInfos(dataFromSession);
        List<PersonalTrainer> pts=new PersonalTrainerDAO().retrieveAll();
        List<UserBean>ubs=new UserBeanDAO().retrieveAllPending();
%>
<div class="d-flex container-fluid justify-content-center align-items-center h-100">
    <div class="card w-auto shadow-lg" >
        <div class="card-body">
            <span class="h1 card-title" style="color:#101010">Welcome, <%=nameSession%> <%=surnameSession%></span>
            <hr>
            <span class="h5" style="color:#101010">This is your </span><span class="h5 fw-bold text-primary" style="">admin</span> <span class="h5" style="color:#101010"> page</span>
            <button class="btn col-sm-12 btn-outline-primary mt-4" data-bs-toggle="modal" data-bs-target="#Pending">Pending requests</button>
            <button class="btn col-sm-12 btn-outline-primary mt-3" data-bs-toggle="modal" data-bs-target="#Pts">Delete personal trainers</button>
        </div>
    </div>
    <div class="modal fade" id="Pending" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="PendingLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary">
                    <h5 class="modal-title" id="PendingLabel">Pending requests</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th class="text-center" scope="col">Email</th>
                                <th class="text-center" scope="col">Name</th>
                                <th class="text-center" scope="col">Surname</th>
                                <th class="text-center" scope="col">Role</th>

                            </tr>
                            </thead>
                            <tbody>
                            <%for(UserBean ubPending: ubs){
                            String idWithUserEmailReplaced=ubPending.getEmail().replaceAll("@","-").trim();%>
                            <tr id="<%=idWithUserEmailReplaced%>1" onclick="setConfirmationEmailForPendingUser('<%=ubPending.getEmail()%>')" data-bs-toggle="modal" data-bs-target="#dialogForPending">
                                <td class="text-center align-middle"><%=ubPending.getEmail()%></td>
                                <td class="text-center align-middle"><%=ubPending.getNome()%></td>
                                <td class="text-center align-middle"><%=ubPending.getCognome()%></td>
                                <td class="text-center align-middle"><%=ubPending.getRole()%></td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Understood</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="Pts" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="PtsLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary">
                    <h5 class="modal-title " id="PtsLabel">Personal trainers list</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th class="text-center" scope="col">Email</th>
                                <th class="text-center" scope="col">Name</th>
                                <th class="text-center" scope="col">Surname</th>
                                <th class="text-center" scope="col">Telephone</th>

                            </tr>
                            </thead>
                            <tbody id="tabellaPTBody">
                            <%for(PersonalTrainer pt: pts){
                                String idWithPTEmailReplaced = pt.getUser().getEmail().replaceAll("@", "-").trim();
                            %>
                            <tr id="<%=idWithPTEmailReplaced%>" onclick="setConfirmationEmailForPTDelete('<%=pt.getUser().getEmail()%>')" data-bs-toggle="modal" data-bs-target="#dialogForElimination">
                                <td class="text-center align-middle"><%=pt.getUser().getEmail()%></td>
                                <td class="text-center align-middle"><%=pt.getUser().getNome()%></td>
                                <td class="text-center align-middle"><%=pt.getUser().getCognome()%></td>
                                <td class="text-center align-middle"><%=pt.getUser().getTelefono()%></td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="dialogForElimination" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="Elimination" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header" style="background-color:darkred">
                    <span class="h4 modal-title" id="Elimination">Warning</span>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <span class="h5 text-black">Are you sure to delete <span class="text-black" id="confirmationEmailForPTDeleteSpan"></span> from the personal trainers?</span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" onclick="removeThisPT()" data-bs-dismiss="modal" class="btn btn-primary">Confirm</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="dialogForPending" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="PendingConfirm" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-secondary">
                    <span class="h4 modal-title" id="PendingConfirm">Warning</span>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <span class="h5 text-black">Are you sure to upgrade <span class="text-black" id="confirmationEmailForPendingUserSpan" class="text-black" ></span> to a personal trainer?</span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" onclick="upgradeThisUser()" data-bs-dismiss="modal" class="btn btn-primary">Confirm</button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="parts/footer.jsp"%>
<%}%>
</body>
</html>
