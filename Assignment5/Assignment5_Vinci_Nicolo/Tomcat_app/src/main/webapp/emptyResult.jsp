<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Input Error</title>
</head>
<body>
    <% if ((boolean) request.getAttribute("empty")) { %>
    <div>
        <p><b>Empty Results</b></p>
    </div>
    <% } %>
</body>
</html>
