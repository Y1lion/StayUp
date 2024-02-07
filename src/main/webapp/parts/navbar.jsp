<!-- start navbar -->
<%@ include file="cookie.jsp" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary sticky-top">
  <a class="navbar-brand ms-3" href="./"><img src="./images/StayUp_White.png" class="img-fluid" width="100" height="30"></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
    <ul class="navbar-nav me-auto mt-2 mt-lg-0">
      <li class="nav-item active">
        <a class="nav-link" href="./">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="./">Another link</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="./">Another link</a>
      </li>
    </ul>
    <ul class="navbar-nav text-inline mt-2 mt-lg-0">
      <% if (dataFromSession  != null) { %>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/userpage.jsp">Bentornato, <%=nameSession%>!</a>
      </li>
      <li class="nav-item">
        <form class="form-inline" action="logout" method="post">
          <button class="btn btn-sm btn-primary" type="submit" id="logout">
            <i class="fas fa-sign-out-alt nav-link"></i>
          </button>
        </form>
      </li>
      <%} else {%>
      <li class="nav-item">
        <a href="login.jsp"><button type="button" class="btn btn-sm btn-primary me-2">Accedi <i class="fas fa-sign-in-alt"></i></button></a>
      </li>
      <%}%>
    </ul>
  </div>
</nav>
<!-- end navbar -->