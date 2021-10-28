let clickedCard = false;
let previousCard = null;
let card1 = null;
let card2 = null;
let tentativi = 4;
let score = 0;

function onClickEv(id) {
    if (!clickedCard) {
        clickedCard = true;
        previousCard = id;
        document.getElementById(id).removeAttribute("onclick");
        const xhttp = new XMLHttpRequest();
        xhttp.open("GET", "card?id=" + id, true);
        xhttp.responseType = "json";
        xhttp.send();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                card1 = this.response.cardValue;
                document.getElementById(id).src = "./imgs/number-" + card1.toString() + ".jpg";
            }
        };
    } else {
        document.getElementById(id).removeAttribute("onclick");
        tentativi -= 1;
        document.getElementById("tentativi").innerText = "Tentativi rimasti:" + tentativi.toString();
        const xhttp = new XMLHttpRequest();
        xhttp.open("GET", "card?id=" + id, true);
        xhttp.responseType = "json";
        xhttp.send();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                card2 = this.response.cardValue;
                document.getElementById(id).src = "./imgs/number-" + card2.toString() + ".jpg";
                if (card1 === card2) {
                    score += 2 * card2;
                    document.getElementById("score").innerText = "punti:" + score.toString();
                } else {
                    score -= 1;
                    document.getElementById("score").innerText = "punti:" + score.toString();
                    setTimeout(function () {
                        document.getElementById(previousCard).src = "./imgs/cardBack.jpg";
                        document.getElementById(id).src = "./imgs/cardBack.jpg";
                        document.getElementById(previousCard).setAttribute("onclick", "onClickEv(this.id)");
                        document.getElementById(id).setAttribute("onclick", "onClickEv(this.id)");
                    }, 1000);
                }
                if (tentativi === 0) {
                    let cards = document.getElementsByClassName("cardImg");
                    for (let i=0; i<cards.length; i++) {
                        cards[i].removeAttribute("onclick");
                    }
                    let gameOver = document.createElement("H2");
                    gameOver.innerText = "Game over";
                    document.getElementById("tentativi").appendChild(gameOver);
                    setTimeout(function () {
                        // post request
                        const xhttp1 = new XMLHttpRequest();
                        xhttp1.open("POST", "game", true);
                        xhttp1.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                        xhttp1.send("score=" + score.toString());
                        xhttp1.onreadystatechange = function () {
                            if (this.readyState === 4 && this.status === 200) {
                                window.location = requestContextPath + "/userPage.jsp";
                            }
                        };
                    }, 1000);
                }
            }
            clickedCard = false;
        };
    }
}
