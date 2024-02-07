<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <%@ include file="parts/meta.jsp"%>
    <%@ include file="parts/head.jsp"%>
    <link rel="stylesheet" href="css/loginCSS.css">
    <title>Login</title>
</head>
<body style="background: #dddddd">
<div class="container-fluid h-100 d-flex justify-content-center align-items-center " style="color: aliceblue">
    <div id="divForSignAndInfo" class="row h-50 d-flex align-items-center m-auto ">
        <div id="divInfos" class="col-lg-8 col-sm-8 h-100 p-4 d-flex flex-column" style="background: rgb(34,193,195);
background: linear-gradient(229deg, rgba(34,193,195,1) 0%, rgba(23,137,181,1) 70%, rgba(23,137,181,1) 100%);border-radius: 5px 0 0 5px;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
            <h1 class="text-center">Welcome User</h1>
            <p class="h5 mt-2">We greet you in our login page, User! We're happy to see you. It's a pleasure for us having you here.</p>
            <button class="btn mt-4 btn-primary align-self-center shadow-lg rounded-pill w-75">Is this your first time? Subscribe here!</button>
        </div>
        <div id="divSignIn" class="col-lg-4 col-sm-4 d-flex flex-column bg-light h-100 p-4 align-items-center" style=" border-radius:0 5px 5px 0;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);color:black">
            <p class="h1">Sign In</p>
            <form action="login" method="post">
                <input type="email" name="emailLog" style="margin-top:20px" class="form-control" placeholder="Email">
                <input type="password" name="passwordLog" style="margin-top:15px" class="form-control" placeholder="Password">
                <p class="text-center mt-2" style="color: darkslategray;font-size: 13px">Forgot password?</p>
                <button type="submit" class="btn btn-primary w-100 mt-2 ">Log-in</button>
            </form>
            <div id="logoDiv" class="d-flex align-items-end  flex-fill align-self-end" >
                    <img src="images/StayUp_Scritta.png"  width="100px"  class="img-fluid bottom-right-image">
            </div>
        </div>
    </div>
</div>
</body>
</html>
