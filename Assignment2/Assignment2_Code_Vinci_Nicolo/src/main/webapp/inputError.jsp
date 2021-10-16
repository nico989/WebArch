<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Input Error</title>
</head>
<body>
<% if ((boolean) request.getAttribute("error")) { %>
    <div>
        <p><b>Error in Input Field</b></p>
    </div>
<% } %>
</body>
</html>
