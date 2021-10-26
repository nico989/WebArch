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
    <h2>Tentativi rimasti:</h2>
    <div>
        <div class="card">
            <img id="0x0" src="./imgs/cardBack.jpg" class="cardImg" alt="blank" onclick="document.get('pippo').src=('./imgs/number-1.jpg')"/>
        </div>
        <div class="card">
            <img id="0x1" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="0x2" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="0x3" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
    </div>
    <div>
        <div class="card">
            <img id="1x0" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="1x1" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="1x2" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="1x3" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
    </div>
    <div>
        <div class="card">
            <img id="2x0" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="2x1" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="2x2" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="2x3" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
    </div>
    <div>
        <div class="card">
            <img id="3x0" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="3x1" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="3x2" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
        <div class="card">
            <img id="3x3" src="./imgs/cardBack.jpg" class="cardImg" alt="blank"/>
        </div>
    </div>
    <h2>punti:</h2>
    <!--<script type="text/javascript" src="./playGameJS.js"></script>-->
</body>
</html>
