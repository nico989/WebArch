<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>
<jsp:include page="banner.jsp"></jsp:include>
<h1>Account list</h1>
<% HashMap<String,String> credentials = (HashMap<String, String>) application.getAttribute("credentials"); %>
<% for (HashMap.Entry<String, String> entry : credentials.entrySet()) { %>
<div>
    <p>
        Username:<%=entry.getKey()%> Password:<%=entry.getValue()%>
    </p>
</div>
<% } %>
<div>
    <h2>Create new account</h2>
    <form action="admin" method="post">
        Username:<input type="text" name="newUsername"/><br/><br/>
        Password:<input type="password" name="newPassword"/><br/><br/>
        <input type="submit" value="Create"/>
    </form>
</div>
<jsp:include page="inputError.jsp">
    <jsp:param name="error" value="<% request.getAtrribute('error') %>"/>
</jsp:include>
</body>
</html>
