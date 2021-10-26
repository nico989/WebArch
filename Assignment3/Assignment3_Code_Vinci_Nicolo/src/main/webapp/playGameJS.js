function changeImg(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "game?id=0x0", true);
    xhttp.responseType = "json";
    xhttp.send();
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            var obj = this.response;
            document.getElementById(id).src = obj.path;
        }
    };
}
