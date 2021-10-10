<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LOGOUT</title>
</head>
<body>
<div>
    <jsp:useBean id="userBean" class="com.example.chat_system.model.User" scope="session"/>
    <% if (Objects.nonNull(userBean.getUsername())) { %>
        <div style="float: left;">
            <p><%=userBean.getUsername()%></p>
        </div>
    <% } %>
    <div style="float: left; margin-top: 15px">
        <form action="logout" method="get">
            <input type="submit" value="Logout"/>
        </form>
    </div>
</div>
</body>
</html>
