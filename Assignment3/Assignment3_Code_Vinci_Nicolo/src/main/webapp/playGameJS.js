let clickedCard = null;

function changeImg(id) {
    if (!clickedCard) {
        clickedCard = id;
        const xhttp = new XMLHttpRequest();
        xhttp.open("GET", "card?id="+id, true);
        xhttp.responseType = "json";
        xhttp.send();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                let obj = this.response;
                console.log(obj.cardValue);
                //document.getElementById(id).src = obj.cardValue;
            }
        };
    } else {
        console.log(clickedCard);
        alert("Card UNCLICKABLE!");
    }
}
