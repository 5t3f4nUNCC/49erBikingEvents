<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>

<div class="container row grow justify-content-center align-self-center my-2">
    <div class="col-12 my-auto">
        <div class="card card-block center-block primary">
            <div class="m-2 p-2 accent2">
                <h1 class="text-center">Get in Contact With Us!</h1>
                <form>
                    <div class="form-row">
                        <div class="form-group col">
                            <label for="first" class="labels">First:</label>  
                            <input type="text" name="first" class="form-control" required/>
                        </div>
                        <div class="form-group col">
                            <label for="last">Last:</label>  
                            <input type="text" name="last" class="form-control" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>  
                        <input type="email" name="email" class="form-control" required/>
                    </div>

                    <div class="form-group">
                        <label for="question">Question:</label>  
                        <input type="textarea" name="question" class="form-control" required/>
                    </div>

                    <button type="submit" name="task" value="contact" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
