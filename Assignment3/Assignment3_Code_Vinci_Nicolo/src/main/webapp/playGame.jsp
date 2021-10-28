<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="playGameCSS.css" />
    <title>Play Game</title>
</head>
<body>
    <h1>Welcome to Memory</h1>
    <h2 id="tentativi">Tentativi rimasti: 4</h2>
    <% for (int i=0; i<4; i++) { %>
        <div>
            <% for (int j=4*i; j<4*(i+1); j++) { %>
                <div class="card">
                    <img id="<%=j%>" src="./imgs/cardBack.jpg" class="cardImg" alt="blank" onclick="onClickEv(this.id)"/>
                </div>
            <% } %>
        </div>
    <% } %>
    <h2 id="score">punti:0</h2>
    <script>let requestContextPath = "${pageContext.request.contextPath}";</script>
    <script type="text/javascript" src="./playGameJS.js"></script>
</body>
</html>
