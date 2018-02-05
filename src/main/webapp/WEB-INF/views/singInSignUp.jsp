<%--
  Created by IntelliJ IDEA.
  User: Kacper T
  Date: 04.02.2018
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Spring MVC ToDoList</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <script src="resources/js/jquery-3.3.1.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <style>
        .img{width: 100%; height: auto;}
    </style>
</head>
<body>
<div id="nav" class="sticky-top">
    <nav class="navbar navbar-expand-md navbar-light bg-light">
        <a class="navbar-brand" href="#"><img src="<c:url value="/resources/img/logo-small.png"/>" width="40" height="35"> ToDoList</a>
    </nav>
</div>
<div id="header" class="container-fluid">
    <img src="<c:url value="/resources/img/logo.png"/>" class="img-fluid"/>
</div>
<div id="content" class="container p-2">
    <div id="features" class="card-group text-center p-2">
        <div class="card text-center">
            <div class="card-header bg-success text-light">
                Sign in
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email"  placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-outline-success">Sign in</button>
                </form>
            </div>
        </div>
        <div class="card text-center">
            <div class="card-header bg-dark text-light">
                Sign up
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email"  placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label for="password1">Password</label>
                        <input type="password" class="form-control" id="registerPassword1" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <label for="password2">Confirm password</label>
                        <input type="password" class="form-control" id="registerPassword2" placeholder="Retype password">
                    </div>
                    <button type="submit" class="btn btn-outline-secondary">Sign up</button>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="footer" class="container-fluid bg-dark text-light text-center fixed-bottom">
    &copy; 2018 ToDoList
</div>
</body>
</html>
