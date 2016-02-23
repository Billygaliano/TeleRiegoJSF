/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import com.elevenpaths.latch.LatchApp;
import com.elevenpaths.latch.LatchResponse;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.json.JsonArray;
import teleriegojsf.client.WeatherClient;
import teleriegojsf.ejb.LandFacade;
import teleriegojsf.model.Land;
import teleriegojsf.model.rs.Recommendation;

/**
 *
 * @author inftel12
 */
@ManagedBean
@SessionScoped
public class LandBean implements Serializable{
    Recommendation recommendation = new Recommendation();
    private Land landSelected;
    private boolean needIrrigate;
    private JsonArray wsResult;
    
    @EJB
    private LandFacade landFacade;

    /**
     * Creates a new instance of LandBean
     */
    public LandBean() {
    }
    
    public String doAccess(BigDecimal landId){        
        WeatherClient weatherClient = new WeatherClient();
        wsResult = weatherClient.findAll_JSON(JsonArray.class);
        
        landSelected = landFacade.getLand(landId);
        System.out.println("Estado: " + landSelected.getState());
        needIrrigate = recommendation.suggestIrrigation(landSelected.getHumidity(), landSelected.getLastDateIrrigation(), landSelected.getWMAvailable(), wsResult);
        return("land");
    }
    
    public String stopIrrigation(){
        return("buyWater");
    }
    
    public String startIrrigation(){
        return("buyWater");
    }

    public Land getLandSelected() {
        return landSelected;
    }

    public void setLandSelected(Land landSelected) {
        this.landSelected = landSelected;
    }

    public boolean isNeedIrrigate() {
        return needIrrigate;
    }

    public void setNeedIrrigate(boolean needIrrigate) {
        this.needIrrigate = needIrrigate;
    }

    public JsonArray getWsResult() {
        return wsResult;
    }

    public void setWsResult(JsonArray wsResult) {
        this.wsResult = wsResult;
    }
    
}
