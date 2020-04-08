<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container row grow justify-content-center align-self-center my-2">
    <div class="col-6 my-auto">
        <div class="card card-block center-block primary">
            <form action="profile" name="signIn" method="post" class="m-2 p-2 accent2">
                <h3>Welcome to 49er's Mountain Biking!</h3>
                <div class="form-group">
                    <label for="username">Username</label>  
                    <input type="text" name="username" class="form-control" required/>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>  
                    <input type="password" name="password" class="form-control" required/>
                </div>

                <button type="submit" name="action" value="signIn" class="btn btn-primary">Login</button>
            </form>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
