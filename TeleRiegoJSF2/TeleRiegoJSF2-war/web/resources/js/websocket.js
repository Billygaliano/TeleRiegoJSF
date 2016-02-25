/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = init;
var socket = new WebSocket("wss://localhost:8181/TeleRiegoJSF2-war/actions");
socket.onmessage = onMessage;




function onMessage(event) {
    var device = JSON.parse(event.data);
//    if (device.action === "add") {
//        printDeviceElement(device);
//    }
    if(device.landid == document.getElementById("landId").innerHTML){
            document.getElementById("aguaDisponible").innerHTML ="Agua disponible para regar: <strong>" + device.Agua + "m3 </strong>";
            document.getElementById("humedad").innerHTML ="Humedad de la tierra: <strong>" + device.Humedad + "% </strong>";
           resta = device.Agua - (device.Meters / 1000);
        if((device.Estado == "parado" && resta <= 0) || device.Humedad ==100){
            window.location.reload(true);
        }
    }

}


function init() {
    var message = {
        action: "add",
        state: "regando",
        landid: document.getElementById("landId").innerHTML
    };
    if (document.getElementById("regando")){
        socket.send(JSON.stringify(message));
    }
}


