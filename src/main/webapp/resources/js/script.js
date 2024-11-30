
let timerId = setInterval(() => updateClock(), 1000);
function updateClock() {
    var d = new Date();
    if (d.getHours()<10)d='0'+d.getHours();
    document.getElementById("hours").innerHTML = d.getHours();
    if (d.getMinutes()<10)d='0'+d.getMinutes();
    document.getElementById("minutes").innerHTML = d.getMinutes();
    if (d.getSeconds()<10)d='0'+d.getSeconds();
    document.getElementById("seconds").innerHTML = d.getSeconds();
}

