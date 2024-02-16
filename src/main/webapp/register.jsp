<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="parts/head.jsp" %>
    <%@include file="parts/meta.jsp" %>
    <link rel="stylesheet" href="css/register.css">
    <title>Sign Up</title>
    <script src="js/utils.js"></script>
</head>
<body>
<div class="d-flex h-100 align-items-center justify-content-center" id="logDiv">
    <div class="d-flex p-5 flex-column text-white bgGradient shadows">
        <form method="post" action="register">
            <div class="d-flex text-center flex-column  h2 mb-3">
                <span class="card-text">Sign Up</span>
            </div>
            <div class="d-flex flex-column form-group h5">
                <input type="email" class="form-control" placeholder="your@email.com" name="regemail" id="regemail" required pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,})+$">
            </div>
            <div class="d-flex flex-column mt-4 form-group h5">
                <div class="input-group">
                    <input id="psw" type="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$" oninvalid="alert('Password must contains: \n8 characters; \nOne lowercase character; \nOne uppercase character; \nOne number; \nOne of the special characters: @,$,!,%,*,?,&;')" class="form-control rounded" placeholder="password" name="regpass" id="regpass">
                    <button type="button" class="input-group-append ml-2 align-items-center bg-transparent rounded text-light border border-0" id="toggle-button2" onclick="togglePasswordVisibility('psw','toggle-button2')"><i class="fa-solid fa-eye"></i></button>
                </div>
            </div>
            <div class="d-flex flex-column mt-4 form-group h5">
                <input type="text" class="form-control" placeholder="name" name="regname" id="regname" pattern="^[A-Z][a-zA-Z]{1,50}$" oninvalid="alert('Name must contains at least 1 character and maximum 50, furthermore the only acceptable characters are: \nA to Z; \na to z; \nÀ to ÿ; \n')" required>
            </div>
            <div class="d-flex flex-column mt-4 form-group h5">
                <input type="text" class="form-control" placeholder="surname" name="regsurname" id="regsurname" required pattern="^[A-Z][a-zA-Z]{1,50}$" oninvalid="alert('Surname must contains at least 1 character and maximum 50, furthermore the only acceptable characters are: \nA to Z; \na to z; \nÀ to ÿ; \n')">
            </div>
            <div class="d-flex flex-column mt-4 form-group h5">
                <input type="tel" class="form-control" placeholder="telephone" name="regtel" id="regtel" required pattern="\d{10}" oninvalid="alert('Telephone must contains only 10 numbers.')">
            </div>
            <div class="d-flex flex-column mt-4 form-group h5">
                <div class="d-flex flex-row justify-content-start align-items-center form-check">
                    <input type="radio" class="form-check-input" name="gender" id="regmale" value="m" required>
                    <label for="regmale" class="form-check-label mt-1">Male</label>
                </div>
                <div class="d-flex flex-row justify-content-start align-items-center form-check">
                    <input type="radio" class="form-check-input" name="gender" id="regfemale" value="f" required>
                    <label for="regfemale" class="form-check-label mt-1">Female</label>
                </div>
                <div class="d-flex flex-row justify-content-start align-items-center form-check">
                    <input type="radio" class="form-check-input" name="gender" id="regother" value="o" required>
                    <label for="regother" class="form-check-label mt-1">Other</label>
                </div>
            </div>
            <div class="d-flex flex-column mt-4 form-group h5">
                <div class="d-flex flex-row justify-content-start align-items-center form-check" id="checklist">
                    <input type="checkbox" class="form-check-input"  name="ptCheck" id="ptCheck" value="true">
                    <label for="ptCheck" class="form-check-label mt-1">Are you a Personal Trainer?</label>
                </div>
            </div>


            <div class="d-flex align-items-baseline justify-content-between">
                <a href="login.jsp" style="font-size: 1rem; font-weight: 500" class="link-body-emphasis link-offset-2 link-underline-opacity-25 link-underline-opacity-75-hover text-white mt-1" id="aLogin">Log-in</a>
                <button type="submit" class="btn btn-light mt-4 text-primary">SUBMIT</button>
            </div>
            <div class="d-flex align-items-end justify-content-end" style="transform: translateY(50px);">
                <img src="images/StayUp_White.png" class="img-fluid" width="80" height="80">
            </div>
        </form>
    </div>
</div>
</body>
</html>
