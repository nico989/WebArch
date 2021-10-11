<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.chat_system.model.Room" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>
<jsp:include page="banner.jsp"></jsp:include>
<h1>Available rooms</h1>
<jsp:useBean id="rooms" class="com.example.chat_system.model.Rooms" scope="application"/>
<% if (rooms.getRooms().isEmpty()) { %>
    <div>
        <p>Sorry, no rooms are available, but you can create one</p>
        <form action="user" method="post">
            Room name:<input type="text" name="title"/><br/><br/>
            <input type="submit" value="Create"/>
        </form>
    </div>
<% } else { %>
    <div>
        <p>
            Enter in a room or create a new one
        </p>
    </div>
    <% HashMap<UUID, Room> hashRooms = rooms.getRooms();%>
    <% for (Map.Entry<UUID, Room> entry : hashRooms.entrySet()) { %>
        <div>
            <a href="room?idRoom=<%=entry.getKey()%>"> <%=URLDecoder.decode(entry.getValue().getName(), "UTF-8")%></a>
        </div>
    <% } %>
    <div>
        <form action="user" method="post">
            Room name:<input type="text" name="title"/><br/><br/>
            <input type="submit" value="Create"/>
        </form>
    </div>
<% } %>
</body>
</html>
