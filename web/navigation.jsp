<%@page import="edu.uncc.Users.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="mt-2 navbar navbar-dark accent">
    <div class="navbar-nav">
        <a class="nav-item nav-link" href="events?id=all">Events</a>
    </div>
    <% if (session.getAttribute("User") != null) {%>
    <span class="ml-auto text-primary align-middle">Welcome <%=((User) session.getAttribute("User")).getFirstName()%>!</span>
    <%}%>            
</nav>
<c:if test="${not empty headerError}">
    <div class="alert alert-danger m-0" role="alert">
        <c:out value="${headerError}"/>
    </div>
</c:if>
<c:if test="${not empty headermessage}">
    <div class="alert alert-success m-0" role="alert">
        <c:out value="${headermessage}"/>
    </div>
</c:if>
</header>
