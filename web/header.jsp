<%-- 
    Document   : header
    Created on : Oct 10, 2019, 10:18:21 AM
    Author     : Stefan Dybka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>49er MB</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="styles/main.css">
    </head>
    <body>
        <header class="secondary">
            <nav class="navbar navbar-expand-md navbar-dark accent">
                <a class="navbar-brand m-0" href="index.jsp">49er Mountain Biking</a>

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarNav">
                    <div class="ml-auto navbar-nav">
                        <% if (session.getAttribute("User") == null) { %>
                            <a class="nav-item nav-link" href="login.jsp">Log in</a>
                            <a class="nav-item nav-link" href="signUp.jsp">Sign up</a>
                        <%} else {%>
                            <a class="nav-item nav-link" href="newEvent.jsp">Create a new event</a>
                            <a class="nav-item nav-link" href="profile">My events</a>
                            <form method="post" action="profile">
                                <button type="submit" name="action" value="signout" class="btn nav-item nav-link">Sign out</button>
                            </form>
                        <%}%>
                    </div>
                </div>
            </nav>
