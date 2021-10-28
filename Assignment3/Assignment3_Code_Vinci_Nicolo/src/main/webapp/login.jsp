<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
    <h1>Welcome, unknown friend!</h1>
    <div>
        <form action="login" method="post">
            Enter your name:<input type="text" name="usernameInRequest"/><br/><br/>
            <input type="submit" value="Let's go!"/>
        </form>
    </div>
    <% if (Objects.nonNull(request.getAttribute("error")) && (boolean)request.getAttribute("error")) { %>
        <div>
            <p><b>Error in Input Field</b></p>
        </div>
    <% } %>
</body>
</html>
