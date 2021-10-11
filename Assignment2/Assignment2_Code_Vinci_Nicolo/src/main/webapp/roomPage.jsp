<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.chat_system.model.Room" %>
<%@ page import="java.net.URLDecoder" %>
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
        <form action="room" method="post">
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
    <% ArrayList<Room> rList = rooms.getRooms();%>
    <% for (Room r: rList) { %>
        <div>
            <a href="RoomServlet"> <%=URLDecoder.decode(r.getName(), "UTF-8")%></a>
        </div>
    <% } %>
    <div>
        <form action="room" method="post">
            Room name:<input type="text" name="title"/><br/><br/>
            <input type="submit" value="Create"/>
        </form>
    </div>
<% } %>
</body>
</html>
