/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.devicesimulator;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import teleriegojsf.model.Land;
import teleriegojsf.ejb.Recommendation;
import teleriegojsf.ejb.LandFacade;

/**
 *
 * @author inftel12
 */
@ApplicationScoped
@ClientEndpoint
public class DeviceSimulator implements Runnable{
    LandFacade landFacade = lookupLandFacadeBean();
    BigDecimal landId;
    Thread threadIrrigation;
    Recommendation recommendation = new Recommendation();
    Session session;
    
    public DeviceSimulator(BigDecimal landId) {
        
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI("ws://localhost:8080/TeleRiegoJSF2-war/actions"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.landId = landId;
        threadIrrigation = new Thread(this);
        threadIrrigation.start();
        
 
    }
    
    @Override
    public void run() {
        landFacade.updateStateLand(landId, "regando");
        WebSocketContainer wsc = ContainerProvider.getWebSocketContainer();
        Date today = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String todayString = formateador.format(today);
        
        Date todayDate = null;
        try {
            todayDate = formateador.parse(todayString);
        } catch (ParseException ex) {
            Logger.getLogger(DeviceSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        landFacade.updateLastDateIrrigation(landId, todayDate);
        
        Land specificLand = landFacade.getLand(landId);
        BigInteger wMAvailable = specificLand.getWMAvailable();
        BigInteger humidity = specificLand.getHumidity(); 
        int trueWMAvailable = wMAvailable.intValue();
        int trueHumidity = humidity.intValue();
        int area = specificLand.getSquareMeters().intValue();
        int spentCubicMetersPerPulse = area/1000;
        
        while (specificLand.getState().equals("regando") && recommendation.thereIsWaterAvailable(wMAvailable)) {
            specificLand = landFacade.getLand(landId);
            
            //System.out.println("Resta: " + (trueWMAvailable - spentCubicMetersPerPulse) + "\n");
            
            if((trueWMAvailable - spentCubicMetersPerPulse) <= 0){
                landFacade.updateStateLand(landId, "parado");
                specificLand.setState("parado");
            }
            if((trueWMAvailable - spentCubicMetersPerPulse) >= 0){
                trueWMAvailable = trueWMAvailable - spentCubicMetersPerPulse;
                trueHumidity++;

                try {
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DeviceSimulator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            System.out.println("Nombre del terreno: " + specificLand.getNameland() + " - Humedad: " + trueHumidity + " - Agua disponible: " + trueWMAvailable + " - Estado: " + specificLand.getState() + "\n");
            session.getAsyncRemote().sendText("Holaaaaaaaaa!!!!!!!");
            humidity = BigInteger.valueOf(trueHumidity);
            wMAvailable = BigInteger.valueOf(trueWMAvailable);
            landFacade.updateWMAvailableHumidityLand(landId, humidity, wMAvailable);
        }        
    }    

    private LandFacade lookupLandFacadeBean() {
        try {
            Context c = new InitialContext();
            return (LandFacade) c.lookup("java:global/TeleRiegoJSF2/TeleRiegoJSF2-ejb/LandFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    @OnOpen
    public void onOpen(Session p) throws IOException {
        this.session = p;
        System.out.println("Hola he abierto conexion" + p );
        p.getAsyncRemote().sendText("Holaaa!!!");
        
        
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(String.format("%s %s", "Received message: ", message));
    }
    
    @OnClose
    public void onClose(Session userSession) {
        System.out.println("closing websocket");
        this.session = null;
    }
}
