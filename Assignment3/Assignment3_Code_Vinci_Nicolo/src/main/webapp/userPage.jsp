<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>
    <h1>Welcome <%= request.getSession(false).getAttribute("usernameInSession") %> </h1>
</body>
</html>
