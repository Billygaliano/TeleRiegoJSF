package teleriegojsf.devicesimulator;


import java.io.StringReader;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author inftel10
 */
@ApplicationScoped
@ServerEndpoint("/actions")
public class SimulatorWebSocketServer {
    
    @Inject
    private WebSocketSessionHandler sessionHandler;
 
    

    @OnOpen
        public void open(Session session) {
            System.out.println("Esta sesión se ha abierto" + session);
            sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Hola me estoy cerrando " +  session.getId());
        sessionHandler.removeSession(session);
    }

    @OnError
        public void onError(Throwable error) {

    }

    @OnMessage
        public void handleMessage(String message, Session session) throws InterruptedException {
           try (JsonReader reader = Json.createReader(new StringReader(message))) {
           JsonObject jsonMessage = reader.readObject();
           sessionHandler.requestLand(jsonMessage.getString("landid"), session);
           }
           
           System.out.println("Hola He recibido un mensaje " + message + "\n\n");
            
             

    }
}
