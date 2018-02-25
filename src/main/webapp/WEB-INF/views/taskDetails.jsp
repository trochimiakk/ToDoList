<%--
  Created by IntelliJ IDEA.
  User: Kacper T
  Date: 31.01.2018
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="C" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>Spring MVC ToDoList</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/ajax.js"/>"></script>
</head>
<body>
<div id="nav" class="sticky-top">
    <nav class="navbar navbar-expand-md navbar-light bg-light">
        <a class="navbar-brand" href="/"><img src="<c:url value="/resources/img/logo-small.png"/>" width="40"
                                              height="35"/> ToDoList</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tasks">Create task</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/users/${principal.username}/tasks/today">Today's tasks</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="/users/${principal.username}/tasks/other">Other tasks <span
                            class="sr-only">(current)</span></a>
                </li>
            </ul>

            <a href="<c:url value="/users/${principal.username}"/>">
                <button type="button" class="btn btn-outline-success">Welcome, ${principal.username}</button>
            </a>
            <sf:form action="/logout" method="post">
                <button type="submit" class="btn btn-outline-info">Sign out</button>
            </sf:form>
        </div>
    </nav>
</div>
<div id="header" class="container-fluid">
    <img src="<c:url value="/resources/img/logo.png"/>" class="logoImg"/>
    <div id="pageTitle" class="mt-1 card text-center rounded border-info border-left-0 border-right-0">
        <div class="card-body">
            <h2>
                Task: ${task.title}
            </h2>
        </div>
    </div>
</div>
<div id="content" class="container p-2 mb-5 text-center">
    <div class="table-responsive">
        <table id="taskDetails" class="table">
            <tbody>
            <tr class="bg-info text-light font-weight-bold">
                <td>
                    Date:
                </td>
            </tr>
            <tr>
                <td>
                    <c:out value="${task.formattedDate}"/>
                </td>
            </tr>
            <tr class="bg-info text-light font-weight-bold">
                <td>
                    Description:
                </td>
            </tr>
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${task.description.equals('')}">
                            Task does not contain description
                        </c:when>
                        <c:otherwise>
                            ${task.description}
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr class="bg-info text-light font-weight-bold">
                <td>
                    Done:
                </td>
            </tr>
            <c:choose>
            <c:when test="${task.done}">
                <tr>
                    <td>
                        <img id="imgTask${task.id}" src="<c:url value="/resources/img/checked.png"/>" width="35" height="35"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button id="markAsDoneTask${task.id}" type="button" disabled class="btn btn-outline-primary">Done!</button>
                        <button id="deleteTask${task.id}" type="button" class="btn btn-outline-dark deleteButton">Delete</button>
                    </td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <td>
                        <img id="imgTask${task.id}" src="<c:url value="/resources/img/unchecked.png"/>" width="35" height="35"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button id="markAsDoneTask${task.id}" type="button" class="btn btn-outline-primary doneButton">Done!</button>
                        <button id="deleteTask${task.id}" type="button" class="btn btn-outline-dark deleteButton">Delete
                        </button>
                    </td>
                </tr>
            </c:otherwise>
            </c:choose>
        </table>
    </div>
</div>
<div id="footer" class="container-fluid bg-dark text-light text-center fixed-bottom">
    &copy; 2018 ToDoList
</div>
</body>
</html>
