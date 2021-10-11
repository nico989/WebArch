<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>LOGIN</h1>
<div>
    <form action="login" method="post">
        Username:<input type="text" name="username"/><br/><br/>
        Password:<input type="password" name="password"/><br/><br/>
        <input type="submit" value="Login"/>
    </form>
</div>
<div>
    <% Object error = request.getAttribute("error"); %>
    <% if (Objects.nonNull(error) && (boolean)error) { %>
    <p><b>Wrong login, try again.</b></p>
    <% } %>
</div>
</body>
</html>
