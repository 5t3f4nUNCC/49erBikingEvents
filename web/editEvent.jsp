<%@page import="edu.uncc.Events.Event"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>

<div class="container row grow justify-content-center align-self-center my-2">
    <div class="col-12 my-auto">
        <div class="card card-block center-block primary">
            <form action="events" name="update" method="post" class="m-2 p-2 accent2">
                <% Event e = (Event) request.getAttribute("event");%>
                <input type="hidden" name="id" value="<c:out value="<%=e.getEventID()%>"/>">
                <div class="form-group">
                    <label for="title" class="labels">Title:</label>  
                    <input type="text" name="title" class="form-control" value="<c:out value="<%=e.getTitle()%>"/>" required/>
                </div>

                <div class="form-group">
                    <label for="type" class="labels">Ride Type:</label>  
                    <select name="type" class="form-control" required size="1">
                        <c:if test='<%=e.getType().equals("Road")%>' >
                            <option selected value="Road">Road</option>  
                            <option value="Trail">Trail</option>  
                            <option value="Downhill">Downhill</option>  
                        </c:if>
                        <c:if test='<%=e.getType().equals("Trail")%>' >
                            <option value="Road">Road</option>  
                            <option selected value="Trail">Trail</option>  
                            <option value="Downhill">Downhill</option>  
                        </c:if>
                        <c:if test='<%=e.getType().equals("Downhill")%>' >
                            <option value="Road">Road</option>  
                            <option value="Trail">Trail</option>  
                            <option selected value="Downhill">Downhill</option>  
                        </c:if>
                    </select>
                </div>

                <div class="form-group">
                    <label for="message">Message(optional):</label>  
                    <input type="text" name="message" value="<c:out value="<%=e.getMessage()%>"/>" class="form-control"/>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>  
                    <input type="text" name="description" value="<c:out value="<%=e.getDescription()%>"/>" class="form-control" required/>
                </div>

                <!-- Address can be a street or a location name -->
                <div class="form-group">
                    <label for="address">Address/Location:</label>  
                    <input type="text" name="address" class="form-control" value="<c:out value="<%=e.getAddress()%>"/>" required/>
                </div> 

                <!-- If just a location name is entered, city, state, country, and zip are not required -->
                <div class="form-row">
                    <div class="form-group col">
                        <label for="city">City</label>  
                        <input type="text" name="city" value="<c:out value="<%=e.getCity()%>"/>" class="form-control"/>
                    </div>

                    <div class="form-group col">
                        <label for="state">State/Province/Region:</label>  
                        <input type="text" name="state" value="<c:out value="<%=e.getState()%>"/>" class="form-control"/>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col">
                        <label for="country">Country:</label>  
                        <input type="text" name="country" value="<c:out value="<%=e.getCountry()%>"/>" class="form-control"/>
                    </div>

                    <div class="form-group col">
                        <label for="zip">ZIP Code:</label>  
                        <input type="text" name="zip" value="<c:out value="<%=e.getZip()%>"/>" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="date">Date and Time of Event:</label>
                    <input type="datetime-local" value="<c:out value="<%=e.getDate()%>"/>" name="date" class="form-control" required/>
                </div>

                <button type="submit" name="task" value="update" class="btn btn-primary">Update Event</button>
            </form>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>