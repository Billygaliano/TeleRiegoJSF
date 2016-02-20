/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.devicesimulator;

import static java.lang.Thread.sleep;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import teleriegojsf.controller.ServletLand;
import teleriegojsf.controller.ServletStartIrrigation;
import teleriegojsf.model.Land;
import teleriegojsf.model.rs.Recommendation;
import teleriegojsf.ejb.LandFacade;

/**
 *
 * @author inftel12
 */
public class DeviceSimulator implements Runnable{
    LandFacade landFacade = lookupLandFacadeBean();
    BigDecimal landId;
    Thread threadIrrigation;
    Recommendation recommendation = new Recommendation();

    public DeviceSimulator(BigDecimal landId) {
        this.landId = landId;
        threadIrrigation = new Thread(this);
        threadIrrigation.start();
    }
    
    @Override
    public void run() {
        landFacade.updateStateLand(landId, "regando");
        
        Date today = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String todayString = formateador.format(today);
        try {
            Date todayDate = formateador.parse(todayString);
            landFacade.updateLastDateIrrigation(landId, todayDate);
        } catch (ParseException ex) {
            Logger.getLogger(ServletLand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Land specificLand = landFacade.getLand(landId);
        BigInteger wMAvailable = specificLand.getWMAvailable();
        BigInteger humidity = specificLand.getHumidity(); 
        int trueWMAvailable = wMAvailable.intValue();
        int trueHumidity = humidity.intValue();
        int area = specificLand.getSquareMeters().intValue();
        int spentCubicMetersPerPulse = area/1000;
        
        while (specificLand.getState().equals("regando") && recommendation.thereIsWaterAvailable(wMAvailable)) {
            specificLand = landFacade.getLand(landId);
            
            System.out.println("Resta: " + (trueWMAvailable - spentCubicMetersPerPulse) + "\n");
            
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
                    Logger.getLogger(ServletStartIrrigation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            System.out.println("Nombre del terreno: " + specificLand.getNameland() + " - Humedad: " + trueHumidity + " - Agua disponible: " + trueWMAvailable + " - Estado: " + specificLand.getState() + "\n");

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
}
