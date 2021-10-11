<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.chat_system.model.Message" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.example.chat_system.model.Room" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--<meta http-equiv="refresh" content="15" >-->
    <title>Room Page</title>
</head>
<body>
<jsp:include page="banner.jsp"></jsp:include>
<h1><%=request.getAttribute("titleRoom")%>
</h1>
<jsp:useBean id="rooms" class="com.example.chat_system.model.Rooms" scope="application"/>
<div>
    <p>Add new message</p>
    <form action="room" method="post">
        Message:<input type="text" name="message"/><br/><br/>
        <input type="text" name="idRoom" value="<%=request.getParameter("idRoom")%>" hidden>
        <input type="submit" value="Send"/>
    </form>
</div>
<div>
    <form action="room" method="get">
        <input type="text" name="idRoom" value="<%=request.getParameter("idRoom")%>" hidden>
        <input type="submit" value="Reload"/>
    </form>
</div>
<%  UUID id = (UUID) request.getAttribute("id");
    Room actualRoom = rooms.getRoomById(id);
    ArrayList<Message> messages = actualRoom.getMessages();
%>
<% if (!messages.isEmpty()) { %>
    <% for (Message m : messages) { %>
        <div>
            <p>
                <%=m%>
            </p>
        </div>
    <% } %>
<% } %>
<a href="user">Return to User Page</a>
</body>
</html>
