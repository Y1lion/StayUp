<!-- start navbar -->
<%@ include file="cookie.jsp" %>
<nav class="navbar navbar-expand-lg navbar-dark bgGradient sticky-top text-light">
  <div class="container-fluid">
    <a class="navbar-brand ms-3" href="./"><img src="./images/StayUp_White.png" class="img-fluid" width="100" height="30"></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
      <ul class="navbar-nav me-auto mt-2 mt-lg-0">
        <li class="nav-item">
          <a class="nav-link active" href="./">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="./">Another link</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="./">Another link</a>
        </li>
      </ul>
      <ul class="navbar-nav text-inline mt-2 mt-lg-0 me-2">
        <% if (dataFromSession  != null) { %>
        <li class="nav-item">
          <a class="nav-link active" href="${pageContext.request.contextPath}/userpage.jsp">Bentornato, <%=nameSession%> <%=surnameSession%>!</a>
        </li>
        <li class="nav-item">
          <form class="form-inline" action="logout" method="post">
            <button class="btn btn-sm btn-outline-light" type="submit" id="logout">
              <i class="fas fa-sign-out-alt nav-link active"></i>
            </button>
          </form>
        </li>
        <%} else {%>
        <li class="nav-item">
          <a href="login.jsp"><button type="button" class="btn btn-sm btn-outline-light">Accedi <i class="fas fa-sign-in-alt"></i></button></a>
        </li>
        <%}%>
      </ul>
    </div>
  </div>
</nav>
<!-- end navbar -->