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
<jsp:include page="inputError.jsp">
    <jsp:param name="error" value="<% request.getParameter('error') %>"/>
</jsp:include>
</body>
</html>
