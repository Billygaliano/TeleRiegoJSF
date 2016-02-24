/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = init;
var socket = new WebSocket("ws://localhost:8080/TeleRiegoJSF2-war/actions");
socket.onmessage = onMessage;



function finish(){
    alert("Hola me voy")
    socket.close();
}

function onMessage(event) {
    var device = JSON.parse(event.data);
//    if (device.action === "add") {
//        printDeviceElement(device);
//    }
    if(device.landid == document.getElementById("landId").innerHTML){
           
           resta = device.Agua - (device.Meters / 1000);
        if(device.Estado == "parado" && resta <= 0){   
            window.location.reload(true);
        }
        document.getElementById("humedad").innerHTML ="<strong>" + device.Humedad + "% </strong>";
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


