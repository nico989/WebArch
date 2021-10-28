<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.memory_game.model.Game" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>
    <h1>Welcome <%= request.getSession(false).getAttribute("usernameInSession") %> </h1>
    <h2>Classifica</h2>
    <jsp:useBean id="gamesBean" class="com.example.memory_game.model.Games" scope="application"/>
    <% if (gamesBean.getGames().isEmpty()) { %>
        <div>
            <p>Classifica vuota - Nessuna partita giocata</p>
        </div>
    <% } else { %>
        <% ArrayList<Game> games = gamesBean.getOrderedGames();%>
        <% for (Game game: games) { %>
        <div>
            <p>
                <%=game%>
            </p>
        </div>
        <% } %>
    <% } %>
    <div>
        <form action="game" method="get">
            <input type="submit" value="Play Game"/>
        </form>
    </div>
</body>
</html>
