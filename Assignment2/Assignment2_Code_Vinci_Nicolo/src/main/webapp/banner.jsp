<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<div>
    <div style="float: right; margin-top: 15px">
        <form action="<%=request.getContextPath()+"/logout"%>" method="get">
            <input type="submit" value="Logout"/>
        </form>
    </div>
    <jsp:useBean id="user" class="com.example.chat_system.model.User" scope="session"/>
    <div style="float: right;">
        <p><%=user.getUsername()%></p>
    </div>
</div>
</body>
</html>
