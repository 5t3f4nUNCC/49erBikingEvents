<%@page import="edu.uncc.Events.Event"%>
<%@page import="java.util.List"%>
<%@page import="edu.uncc.Users.UserEvent"%>
<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container row grow justify-content-center align-self-center my-2">
    <div class="col-12 my-auto">
        <div class="card card-block center-block primary p-2">
            <h4>Saved Events</h4>
            <div class="table-responsive-md">
                <% if (((List<UserEvent>) session.getAttribute("UserEvents")).size() != 0) { %>
                <table class="table accent2 p-2">
                    <thead class="text-center">
                        <tr>
                            <th scope="col">Trail Name</th>
                            <th scope="col">Trail Type</th>
                            <th scope="col">People Going</th>
                            <th scope="col">Going?</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (UserEvent e : ((List<UserEvent>) session.getAttribute("UserEvents"))) {%>
                        <tr class="text-center">
                            <th scope="row">
                                <a href="events?id=<c:out value="<%=e.getEvent().getEventID()%>"/>" class="w-100">
                                    <div class="container row m-0">
                                        <div class="col">
                                            <img src="pictures/<c:out value="<%=e.getEvent().getTitle()%>"/>.jpg" alt="" class="img-fluid" title="<c:out value="<%=e.getEvent().getTitle()%>"/>">
                                        </div>
                                        <div class="col text-left align-self-center"><c:out value="<%=e.getEvent().getTitle()%>"/></div>
                                    </div>
                                </a>
                            </th>
                            <td class="align-middle"><c:out value="<%=e.getEvent().getType()%>"/></td>
                            <td class="align-middle"><c:out value="<%=e.getEvent().getCountGoing()%>"/></td>
                            <td class="align-middle"><c:out value="<%=e.getRsvp()%>"/></td>
                            <td class="align-middle">
                                <form action="profile" method="post">
                                    <input type="hidden" name="viewEvents" value="<c:out value="<%=e.getEvent().getEventID()%>"/>">
                                    <input type="hidden" name="id" value="<c:out value="<%=e.getEvent().getEventID()%>"/>">
                                    <div class="btn-group" role="group" aria-label="Event tools">
                                        <button class="btn btn-warning" type="submit" name="action" value="updateProfile">Edit</button>
                                        <button class="btn btn-danger" type="submit" name="action" value="delete">Remove</button>
                                    </div>
                                </form>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <% } else {%>
                <p>You do not have any events saved! Head over to the <a href="events?id=all">events</a> and rsvp to some events.</p>
                <%}%>
            </div>
            
            <hr>
            
            <h4>Your Events</h4>
            <div class="table-responsive-md">
                <% if (((List<Event>) session.getAttribute("CreatedEvents")).size() != 0) { %>
                <table class="table accent2 p-2">
                    <thead class="text-center">
                        <tr>
                            <th scope="col">Trail Name</th>
                            <th scope="col">Trail Type</th>
                            <th scope="col">People Going</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Event e : ((List<Event>) session.getAttribute("CreatedEvents"))) {%>
                        <tr class="text-center">
                            <th scope="row">
                                <a href="events?id=<c:out value="<%=e.getEventID()%>"/>" class="w-100">
                                    <div class="container row m-0">
                                        <div class="col">
                                            <img src="pictures/<c:out value="<%=e.getTitle()%>"/>.jpg" alt="" class="img-fluid" title="<c:out value="<%=e.getTitle()%>"/>">
                                        </div>
                                        <div class="col text-left align-self-center"><c:out value="<%=e.getTitle()%>"/></div>
                                    </div>
                                </a>
                            </th>
                            <td class="align-middle"><c:out value="<%=e.getType()%>"/></td>
                            <td class="align-middle"><c:out value="<%=e.getCountGoing()%>"/></td>
                            <td class="align-middle">
                                <form action="events" method="post">
                                    <input type="hidden" name="id" value="<c:out value="<%=e.getEventID()%>"/>">
                                    <div class="btn-group" role="group" aria-label="Event tools">
                                        <button class="btn btn-warning" type="submit" name="task" value="edit">Edit</button>
                                        <button class="btn btn-danger" type="submit" name="task" value="delete">Delete</button>
                                    </div>
                                </form>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
                <% } else {%>
                <p>You haven't made any events! Head over to <a href="newEvent.jsp">Create new event</a> and create some events for people to come to!</p>
                <%}%>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp"%>
