<%@ page import="model.parameters.Parameters" %>
<%@ page import="model.user.UserBean" %>
<%@ page import="model.user.UserBeanDAO" %>
<%@ page import="model.parameters.ParametersDAO" %>
<%@ page import="model.personalTrainer.PersonalTrainer" %>
<%@ page import="model.personalTrainer.PersonalTrainerDAO" %>
<%@ page import="model.subscription.Subscription" %>
<%@ page import="model.subscription.SubscriptionDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="parts/head.jsp" %>
    <%@include file="parts/meta.jsp" %>
    <%@include file="parts/navbar.jsp"%>
    <link rel="stylesheet" href="css/register.css">
    <link rel="stylesheet" href="css/custom.css">
    <title>User Profile</title>
    <script src="js/utils.js"></script>
</head>
<body class="bg-primary">
<% if (dataFromSession == null){
    response.sendRedirect(request.getContextPath() + "/index.jsp");
}else{
    UserBean ub = new UserBeanDAO().recoverInfos(dataFromSession);
    String gender;
    PersonalTrainer pt = null;
    Parameters userParam = new ParametersDAO().getParameters(dataFromSession);
    if (roleSession!=null && roleSession.equalsIgnoreCase("pt")){
        pt = new PersonalTrainerDAO().retrieveInfo(dataFromSession);
    }
    if (ub.getGender().equalsIgnoreCase("m")){
        gender = "Male";
    }else if (ub.getGender().equalsIgnoreCase("f")){
        gender = "Female";
    }else{
        gender = "Other";
    }
    Subscription sub = new SubscriptionDAO().getSubscription(ub);
%>
<section>
    <div class="container py-5">
        <div class="row">
            <div class="col-lg-4">
                <div class="card mb-4 shadow">
                    <div class="card-body text-center">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                             class="rounded-circle img-fluid" style="width: 150px;">
                        <h5 class="my-3"><%=nameSession%> <%=surnameSession%></h5>
                        <p class="text-muted mb-1"><%=roleSession%></p>
                        <div class="d-flex justify-content-center mb-2">
                            <a href="mailto:<%=ub.getEmail()%>" type="button" class="btn btn-outline-primary">Email me</a>
                        </div>
                    </div>
                </div>
                <div class="card mb-4 mb-md-0 shadow">
                    <div class="card-body">
                        <span class="text-primary font-italic me-1">training plan</span> <span class="text-muted">Your Training Plans</span>
                        <% if (roleSession!=null && roleSession.equalsIgnoreCase("user")){%>
                            <% if(sub != null && !sub.getEmailUser().equalsIgnoreCase("error")){%>
                            <p class="mb-4"><span class="text-primary font-italic me-1">subscription</span> <span class="text-muted"><%=new UserBeanDAO().checkEmail(sub.getEmailPt()).getNome()%> <%=new UserBeanDAO().checkEmail(sub.getEmailPt()).getCognome()%>, <%=sub.getDateEnd()%></span></p>
                            <div class="container-fluid overflow-auto">
                                <span class="text-muted">TODO: ADD TRAINING PLAN</span>
                            </div>
                            <%} else {%>
                        <p class="mb-4"><span class="text-primary font-italic me-1">subscription</span> <span class="text-muted">No Active Subscription</span></p>
                        <%} }else{}%>
                    </div>
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
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card mb-4 mb-md-0 shadow">
                            <div class="card-body">
                                <p class="mb-4"><span class="text-primary font-italic me-1">parameters</span> <span class="text-muted">Personal Parameters</span>
                                </p>
                                <% if(userParam != null && !userParam.getEmail().equalsIgnoreCase("error")){  %>
                                    <p class="mb-1 text-primary" style="font-size: .77rem;">Weight <span class="text-black"><%=userParam.getWeight()%></span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Lean Mass <span class="text-black"><%=userParam.getLean_mass()%></span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Fat Mass <span class="text-black"><%=userParam.getFat_mass()%></span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Arm Mensuration <span class="text-black"><%=userParam.getArm_mis()%></span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Leg Mensuration <span class="text-black"><%=userParam.getLeg_mis()%></span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Chest Mensuration <span class="text-black"><%=userParam.getChest_mis()%></span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Hips Mensuration <span class="text-black"><%=userParam.getHips_mis()%></span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Shoulders Mensuration <span class="text-black"><%=userParam.getShoulders_mis()%></span></p>
                                    <p class="mt-4 mb-1 text-primary" style="font-size: .77rem;">Workout Years <span class="text-black"><%=userParam.getWorkoutYears()%></span></p>
                                    <button type="button" class="btn btn-outline-primary mt-2" data-bs-toggle="modal" data-bs-target="#addParameters">Change Parameters</button>
                                <%} else {%>
                                <p class="mb-1 text-primary" style="font-size: .77rem;">No Parameters Found</p>
                                <button type="button" class="btn btn-outline-primary mt-2" data-bs-toggle="modal" data-bs-target="#addParameters">Add Parameters</button>
                                <%}%>
                            </div>
                        </div>
                    </div>
                    <%if(roleSession!=null && roleSession.equalsIgnoreCase("pt") && pt != null){%>
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
                        <input type="text" class="form-control text-primary" placeholder="name" name="newname" id="newname" pattern="[A-Za-zÀ-ÿ\s']{1,50}" oninvalid="alert('Name must contains at least 1 character and maximum 50, furthermore the only acceptable characters are: \nA to Z; \na to z; \nÀ to ÿ; \n')" required>
                    </div>
                    <div class="d-flex flex-column form-group h5">
                        <input type="text" class="form-control text-primary" placeholder="surname" name="newsurname" id="newsurname" required pattern="[A-Za-zÀ-ÿ\s']{1,50}" oninvalid="alert('Surname must contains at least 1 character and maximum 50, furthermore the only acceptable characters are: \nA to Z; \na to z; \nÀ to ÿ; \n')">
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
</body>
</html>
