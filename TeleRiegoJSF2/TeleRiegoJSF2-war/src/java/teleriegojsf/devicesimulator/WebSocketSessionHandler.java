/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.devicesimulator;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import teleriegojsf.ejb.LandFacade;
import teleriegojsf.model.Land;



@ApplicationScoped
public class WebSocketSessionHandler {
    private final ArrayList<Session> sessions = new ArrayList<>();
    
    @EJB
    LandFacade landFacade;
    
    @PostConstruct
    public void init(){
        System.out.println("Hola Fernando");
    }
    
    public void addSession(Session session) {
        sessions.add(session);
    }
    
    public void requestLand(String landId, Session session) throws InterruptedException{
        BigDecimal id = new BigDecimal(landId);
        Land landdefault = landFacade.getLand(id);
        while (landdefault.getState().equalsIgnoreCase("regando") && session.isOpen()) { 
            landdefault = landFacade.getLand(id);
            
            sendToSession(session, createAddMessage(landdefault));
        }
    }


    private JsonObject createAddMessage(Land land) {
        JsonProvider provider = JsonProvider.provider();
            JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("Humedad", land.getHumidity())
                .add("Estado", land.getState())
                .add("landid", land.getLandId())
                .add("Agua", land.getWMAvailable())
                .add("Meters", land.getSquareMeters())
                .build();
            System.out.println("Agua del mensaje" + land.getWMAvailable());
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(WebSocketSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeSession(Session session) {
        sessions.remove(session);
    }
}
