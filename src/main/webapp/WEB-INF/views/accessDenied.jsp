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
    <title>Access Denied - Spring MVC ToDoList</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</head>
<body>
<div id="nav" class="sticky-top">
    <nav class="navbar navbar-expand-md navbar-light bg-light">
        <a class="navbar-brand" href="#"><img src="<c:url value="/resources/img/logo-small.png"/>" width="40" height="35"> ToDoList</a>        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Home</a>
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
<div id="content" class="container p-2 mb-5">
    <div class="card text-center">
        <div class="card-header bg-danger text-light">
            <h1>403</h1>
        </div>
        <div class="card-body">
            <p class="card-text"><h3>You don't have permission to access this page.</h3></p>
        </div>
        <div class="card-footer text-muted">
            <a href="/"><button class="btn btn-outline-danger">Home page</button></a>
        </div>
    </div>
</div>
<div id="footer" class="container-fluid bg-dark text-light text-center fixed-bottom">
    &copy; 2018 ToDoList
</div>
</body>
</html>
