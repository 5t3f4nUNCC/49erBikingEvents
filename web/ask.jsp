<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="edu.uncc.Events.Event"%>

<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>

<div class="container row grow justify-content-center align-self-center my-2">
    <div class="col-12 my-auto">
        <div class="card card-block center-block primary align-items-center">
            <div class="container row">
                <%
                    //get the event object from the request
                    Event event = (Event) request.getAttribute("eventToDelete");
                    //create a date format ex: Tuesday, September 9, 2018
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM W, u");
                    //create a time format ex: 03:56 PM
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
                %>
                <div class="col-lg">
                    <h3><%=event.getTitle()%></h3>
                    <div class="row">
                        <div class="col-lg my-2 p-0">
                            <img src="pictures/<%=event.getTitle()%>.jpg" class="img-fluid" alt="" title="<%=event.getTitle()%>">
                        </div>
                        <div class="col align-self-center">
                            <p>
                                <%=event.getDate().format(dateFormatter)%><br>
                                <%=event.getDate().format(timeFormatter)%>
                            </p>
                            <p>
                                <%=event.getAddress()%>
                                <% if (!event.getCity().isEmpty()) {%>
                                <br>
                                <%=event.getCity()%>, <%=event.getState()%>, <%=event.getZip()%><br>
                                <%=event.getCountry()%>
                                <% }%>
                            </p>
                            <p>
                                <%=event.getCountGoing()%> going
                            </p>
                        </div>
                    </div>
                </div>              
                <form class="col accent2 align-self-center" action="events" method="post">
                    <div class="form-group text-center">
                        Are you sure you want to delete this event?<br>
                        <input type="hidden" name="task" value="deleteE">
                        <input type="hidden" name="id" value="<%=event.getEventID()%>">
                        <button type="submit" name="answer" value="Yes" class="btn btn-success">Yes</button>
                        <button type="submit" name="answer" value="No" class="btn btn-danger">No</button>
                    </div>
                </form>
                <div class="container row">
                    <div class="col">
                        <h4>Description</h4>
                        <p><%=event.getMessage()%></p>
                        <p><%=event.getDescription()%></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>