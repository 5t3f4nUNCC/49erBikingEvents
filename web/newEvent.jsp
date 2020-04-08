<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>

<div class="container row grow justify-content-center align-self-center my-2">
    <div class="col-12 my-auto">
        <div class="card card-block center-block primary">
            <form action="events" name="create" method="post" class="m-2 p-2 accent2">
                <div class="form-group">
                    <label for="title" class="labels">Title:</label>  
                    <input type="text" name="title" class="form-control" required/>
                </div>

                <div class="form-group">
                    <label for="type" class="labels">Ride Type:</label>  
                    <select name="type" class="form-control" required>  
                        <option selected="" value="Default">(Please select the type of ride)</option>  
                        <option value="Road">Road</option>  
                        <option value="Trail">Trail</option>  
                        <option value="Downhill">Downhill</option>  
                    </select>
                </div>

                <div class="form-group">
                    <label for="message">Message(optional):</label>  
                    <input type="text" name="message" class="form-control"/>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>  
                    <input type="text" name="description" class="form-control" required/>
                </div>

                <!-- Address can be a street or a location name -->
                <div class="form-group">
                    <label for="address">Address/Location:</label>  
                    <input type="text" name="address" class="form-control" required/>
                </div> 

                <!-- If just a location name is entered, city, state, country, and zip are not required -->
                <div class="form-row">
                    <div class="form-group col">
                        <label for="city">City</label>  
                        <input type="text" name="city" class="form-control"/>
                    </div>

                    <div class="form-group col">
                        <label for="state">State/Province/Region:</label>  
                        <input type="text" name="state" class="form-control"/>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col">
                        <label for="country">Country:</label>  
                        <input type="text" name="country" class="form-control"/>
                    </div>

                    <div class="form-group col">
                        <label for="zip">ZIP Code:</label>  
                        <input type="text" name="zip" class="form-control"/>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="date">Date and Time of Event:</label>  
                    <input type="datetime-local" name="date" class="form-control" required/>
                </div>

                <button type="submit" name="task" value="create" class="btn btn-primary">Create Event</button>
            </form>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
