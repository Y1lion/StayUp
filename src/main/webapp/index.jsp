<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <%@ include file="parts/meta.jsp"%>
    <%@ include file="parts/head.jsp"%>
    <link rel="stylesheet" href="css/homeCSS.css">
    <script src="js/home.js"></script>
    <title>Home</title>
</head>
<body>
<%@ include file="parts/navbar.jsp"%>
<div class="container-fluid p-0 h-100">
    <div class="container-fluid p-0 h-auto position-relative">
        <button onclick="imageScrolled('leftButton')" style="position:absolute!important;width: 55px;height: 55px;left:150px" class="position-absolute btn btn-scrollable rounded-pill translate-middle-y top-50 "><i style="font-size: 20px" class="fa-solid fa-arrow-left"></i></button>
        <button  onclick="imageScrolled('rightButton')" style="position:absolute!important;width: 55px;height: 55px;right:150px" class="position-absolute btn btn-scrollable rounded-pill translate-middle-y top-50 "><i style="font-size:20px" class="fa-solid fa-arrow-right"></i></button>
        <img id="scrollableImg" src="images/fitness1.jpg" class="img-fluid w-100">
    </div>
</div>
</body>
</html>