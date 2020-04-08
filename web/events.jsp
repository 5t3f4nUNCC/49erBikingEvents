<%@page import="java.util.List"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="edu.uncc.Events.Event"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>

<div class="container row grow justify-content-center align-self-center">
    <div class="list-group list-group-flush my-5"> 
        <%
            //get the collection of events from the request object
            List<Event> events = (List<Event>) request.getAttribute("events");
            //create a local time when the user loads the page for refference to the event times
            LocalDateTime time = LocalDateTime.now();
        %>
        <div class="list-group-item primary">
            <h5>Road Rides</h5>
            <div class="list-group list-group-flush">
                <% for (Event e : events) {
                        if (e.getType().equals("Road"))//display each event that is a road type
                        {%>
                <a href="events?id=<c:out value="<%=e.getEventID()%>"/>" class="list-group-item list-group-item-action flex-colum align-items-start accent2">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1"><c:out value="<%=e.getTitle()%>"/></h5>
                        <% if (time.until(e.getDate(), ChronoUnit.YEARS) > 0) {%>
                        <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.YEARS)%>"/> year(s)</small>
                        <% } else if (time.until(e.getDate(), ChronoUnit.MONTHS) > 0) {%>
                        <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.MONTHS)%>"/> month(s)</small>
                        <% } else if (time.until(e.getDate(), ChronoUnit.DAYS) > 0) {%>
                        <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.DAYS)%>"/> day(s)</small>
                        <% } else if (time.until(e.getDate(), ChronoUnit.HOURS) > 0) {%>
                        <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.HOURS)%>"/> hour(s)</small>
                        <% } %>
                    </div>
                    <% if (e.getMessage().isEmpty()) {%>
                    <p class="mb-1"><c:out value="<%=e.getDescription()%>"/></p>
                    <%} else {%>
                    <p class="mb-1"><c:out value="<%=e.getMessage()%>"/></p>
                    <%}%>
                    <small><c:out value="<%=e.getCountGoing()%>"/> people going</small>
                </a>
                <% }
                    }%>
            </div>
        </div>

        <div class="list-group-item primary">
            <h5>Trail Rides</h5>
            <div class="list-group list-group-flush">
                <% for (Event e : events) {//display each event that is a trail type
                        if (e.getType().equals("Trail")) {%>
                <a href="events?id=<c:out value="<%=e.getEventID()%>"/>" class="list-group-item list-group-item-action flex-colum align-items-start accent2">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1"><c:out value="<%=e.getTitle()%>"/></h5>
                        <% if (time.until(e.getDate(), ChronoUnit.MONTHS) > 0) {%>
                        <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.MONTHS)%>"/> month(s)</small>
                        <% } else if (time.until(e.getDate(), ChronoUnit.DAYS) > 0) {%>
                        <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.DAYS)%>"/> day(s)</small>
                        <% } else if (time.until(e.getDate(), ChronoUnit.HOURS) > 0) {%>
                        <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.HOURS)%>"/> hour(s)</small>
                        <% } %>
                    </div>
                    <% if (e.getMessage().isEmpty()) {%>
                    <p class="mb-1"><c:out value="<%=e.getDescription()%>"/></p>
                    <%} else {%>
                    <p class="mb-1"><c:out value="<%=e.getMessage()%>"/></p>
                    <%}%>
                    <small><c:out value="<%=e.getCountGoing()%>"/> people going</small>
                </a>
                <% }
                    }%>
            </div>
        </div>

        <div class="list-group-item primary">
            <h5>Downhill Rides</h5>
            <div class="list-group list-group-flush"> 
                <% for (Event e : events) {//display each event that is a downhill type
                    if (e.getType().equals("Downhill")) {%>
                        <a href="events?id=<c:out value="<%=e.getEventID()%>"/>" class="list-group-item list-group-item-action flex-colum align-items-start accent2">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1"><c:out value="<%=e.getTitle()%>"/></h5>
                            <% if (time.until(e.getDate(), ChronoUnit.MONTHS) > 0) {%>
                                <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.MONTHS)%>"/> month(s)</small>
                            <% } else if (time.until(e.getDate(), ChronoUnit.DAYS) > 0) {%>
                                <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.DAYS)%>"/> day(s)</small>
                            <% } else if (time.until(e.getDate(), ChronoUnit.HOURS) > 0) {%>
                                <small>In <c:out value="<%= time.until(e.getDate(), ChronoUnit.HOURS)%>"/> hour(s)</small>
                            <% } %>
                        </div>
                            <% if (e.getMessage().isEmpty()) {%>
                                <p class="mb-1"><c:out value="<%=e.getDescription()%>"/></p>
                            <%} else {%>
                                <p class="mb-1"><c:out value="<%=e.getMessage()%>"/></p>
                            <%}%>
                            <small><c:out value="<%=e.getCountGoing()%>"/> people going</small>
                        </a>
                <% }
                }%>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
