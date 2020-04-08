<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="edu.uncc.Events.Event"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>

<div class="container row grow justify-content-center align-self-center my-2">
    <div class="col-12 my-auto">
        <div class="card card-block center-block primary align-items-center">
            <div class="container row">
                <%
                    //get the event object from the request
                    Event event = (Event) request.getAttribute("event");
                    //create a date format ex: Tuesday, September 9, 2018
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, u");
                    //create a time format ex: 03:56 PM
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
                %>
                <div class="col-lg">
                    <h3><c:out value="<%=event.getTitle()%>"/></h3>
                    <div class="row">
                        <div class="col-lg my-2 p-0">
                            <img src="pictures/<c:out value="<%=event.getTitle()%>"/>.jpg" class="img-fluid" alt="" title="<c:out value="<%=event.getTitle()%>"/>">
                        </div>
                        <div class="col align-self-center">
                            <p>
                                <c:out value="<%=event.getDate().format(dateFormatter)%>"/><br>
                                <c:out value="<%=event.getDate().format(timeFormatter)%>"/>
                            </p>
                            <p>
                                <c:out value="<%=event.getAddress()%>"/>
                                <c:if test="<%=!event.getCity().isEmpty()%>">
                                    <br>
                                    <c:out value="<%=event.getCity()%>"/>, <c:out value="<%=event.getState()%>"/>, <c:out value="<%=event.getZip()%>"/><br>
                                    <c:out value="<%=event.getCountry()%>"/>
                                </c:if>
                            </p>
                            <p>
                                <c:out value="<%=event.getCountGoing()%>"/> going
                            </p>
                        </div>
                    </div>
                </div>              
                <form class="col accent2 align-self-center" action="profile" method="post">
                    <div class="form-group text-center">
                        Are you going?<br>
                        <input type="hidden" name="action" value="save">
                        <input type="hidden" name="viewEvents" value="<c:out value="<%=event.getEventID()%>"/>">
                        <input type="hidden" name="id" value="<c:out value="<%=event.getEventID()%>"/>">
                        <button type="submit" name="rsvp" value="Yes" class="btn btn-success">Yes</button>
                        <button type="submit" name="rsvp" value="Maybe" class="btn btn-warning">Maybe</button>
                        <button type="submit" name="rsvp" value="No" class="btn btn-danger">No</button>
                    </div>
                    <div class="form-group text-center">
                        <input type="checkbox" class="form-check-input" >
                        <label class="form-check-label text-muted">I can drive others</label>
                    </div>
                    <div class="form-group row">
                        <label class="ml-auto col-sm-4 col-form-label text-muted">How many?</label>
                        <div class="col-sm-2 mr-auto">
                            <input type="text" class="form-control" >
                        </div>
                    </div>
                </form>
                <div class="container row">
                    <div class="col">
                        <h4>Description</h4>
                        <p><c:out value="<%=event.getMessage()%>"/></p>
                        <p><c:out value="<%=event.getDescription()%>"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>