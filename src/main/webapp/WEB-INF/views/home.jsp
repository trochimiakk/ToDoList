<%--
  Created by IntelliJ IDEA.
  User: Kacper T
  Date: 31.01.2018
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Spring MVC ToDoList</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</head>
<body>
<div id="nav" class="sticky-top">
    <nav class="navbar navbar-expand-md navbar-light bg-light">
        <a class="navbar-brand" href="/"><img src="<c:url value="/resources/img/logo-small.png"/>" width="40" height="35"> ToDoList</a>        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tasks">Create task</a>
                </li>
                <li class="nav-item">
                    <sec:authentication var="principal" property="principal" scope="session"/>
                    <a class="nav-link" href="/users/${principal.username}/tasks/today">Today's tasks</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/users/${principal.username}/tasks/other">Other tasks</a>
                </li>
            </ul>
            <a href="<c:url value="/users/${principal.username}"/>"><button type="button" class="btn btn-outline-success">Welcome, ${principal.username}</button></a>
            <sf:form action="/logout" method="post">
                <button type="submit" class="btn btn-outline-info">Sign out</button>
            </sf:form>
        </div>
    </nav>
</div>
<div id="header" class="container-fluid">
    <img src="<c:url value="/resources/img/logo.png"/>" class="logoImg"/>
</div>
<div id="content" class="container p-2">
    <div id="features" class="card-group text-center p-2">
        <div class="card border-info">
            <img class="card-img-top" src="<c:url value="/resources/img/first_feaure.jpg"/>" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">First feature</h5>
                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut egestas finibus orci et mattis.</p>
            </div>
        </div>
        <div class="card border-dark">
            <img class="card-img-top" src="<c:url value="/resources/img/second_feature.jpg"/>" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">Second feature</h5>
                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut egestas finibus orci et mattis.</p>
            </div>
        </div>
        <div class="card border-info">
            <img class="card-img-top" src="<c:url value="/resources/img/third_feature.jpg"/>" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">Third feature</h5>
                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut egestas finibus orci et mattis.</p>
            </div>
        </div>
    </div>
    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut egestas finibus orci et mattis. Cras a mollis augue, ac congue mi. Duis luctus dui in eros ullamcorper placerat. Aliquam velit massa, euismod quis risus et, rutrum tempor magna. Nulla facilisi. Fusce aliquam quis ante eu posuere. Ut a leo enim. Duis cursus faucibus odio eget vestibulum. Pellentesque congue euismod sem, sit amet porttitor est pretium in. Fusce pharetra risus eget enim suscipit, vel efficitur lacus vehicula. Sed in neque ut metus auctor blandit. Curabitur lectus nibh, molestie id nibh ac, venenatis ornare nisi. Nullam facilisis mauris lorem, et auctor nisl euismod vitae.

    In sit amet congue eros. Fusce gravida congue libero et blandit. Curabitur posuere, odio quis molestie vehicula, erat enim eleifend est, sed vulputate lectus tortor a lacus. Pellentesque vitae diam gravida, auctor sapien sit amet, convallis nibh. Suspendisse sodales erat et tortor finibus vehicula. Integer nec quam tellus. Nullam orci risus, pellentesque sed egestas at, pellentesque a arcu. Nulla venenatis ultrices quam sed aliquet. Integer eu nisi leo. Cras vitae libero quis orci auctor dictum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum lobortis vel justo eget congue.
</div>
<div id="footer" class="container-fluid bg-dark text-light text-center fixed-bottom">
    &copy; 2018 ToDoList
</div>
</body>
</html>
