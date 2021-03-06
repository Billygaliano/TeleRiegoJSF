/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import com.elevenpaths.latch.LatchApp;
import com.elevenpaths.latch.LatchResponse;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.JsonArray;
import teleriegojsf.client.WeatherClient;
import teleriegojsf.devicesimulator.DeviceSimulator;
import teleriegojsf.ejb.LandFacade;
import teleriegojsf.ejb.MembershipFacade;
import teleriegojsf.ejb.Recommendation;
import teleriegojsf.model.Land;

/**
 *
 * @author inftel12
 */
@ManagedBean
@SessionScoped
public class LandBean implements Serializable{    
    private static final String LATCH_APP_ID = "W2i6TtX6N8C9GNugxkRh";
    private static final String LATCH_SECRET = "T67WcbigW69xjzqJMFuYp9xFRFRj3YrcgMVaxwTQ";
    private Land landSelected;
    private boolean needIrrigate;
    private JsonArray wsResult;
    private boolean lockedState;
    private LoginBean loginBean;
    @EJB
    private Recommendation recommendation;    
    
    @EJB
    private LandFacade landFacade;
    
    @EJB
    private MembershipFacade membershipFacade;
    
    /**
     * Creates a new instance of LandBean
     */
    public LandBean() {
    }
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        loginBean = (LoginBean)fc.getApplication().evaluateExpressionGet(fc, "#{loginBean}", LoginBean.class);
        if(loginBean.getMembershipSelected() == null){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lockedState = false;
    }
    
    public String doAccess(BigDecimal landId){        
        WeatherClient weatherClient = new WeatherClient();
        wsResult = weatherClient.findAll_JSON(JsonArray.class);
        
        landSelected = landFacade.getLand(landId);
        needIrrigate = recommendation.suggestIrrigation(landSelected.getHumidity(), landSelected.getLastDateIrrigation(), landSelected.getWMAvailable(), wsResult);
        return("land");
    }
    
    public String stopIrrigation(){
        landFacade.updateStateLand(landSelected.getLandId(), "parado");
        landSelected.setState("parado");
        landSelected = landFacade.getLand(landSelected.getLandId());
        return("land");
    }
    
    public String startIrrigation(){
        LatchApp latch = new LatchApp(LATCH_APP_ID, LATCH_SECRET);
        BigDecimal ownerId = new BigDecimal(landSelected.getIdAdmin());
        LatchResponse opStatusResponse = latch.operationStatus(membershipFacade.getMembership(ownerId).getAccountid(), "q7QmGeQZukAihrETBbbT");
        
        BigDecimal bigLandId = new BigDecimal(landSelected.getLandId().toString());
        landSelected.setHumidity(landFacade.getHumidityLand(bigLandId));
        
        if(membershipFacade.getMembership(ownerId).getAccountid() == null && landSelected.getHumidity().intValue() < 100){
            if (!landFacade.getStateIrrigate(landSelected.getLandId()) && landFacade.thereIsWaterAvailable(landSelected.getLandId())) {
                    DeviceSimulator deviceSimulator = new DeviceSimulator(landSelected.getLandId());
                    landSelected.setState("regando");
                    lockedState = false;
            }
            return("land");
        }
        
        if (opStatusResponse != null && opStatusResponse.getData() != null) {
            String status = opStatusResponse.getData().get("operations").getAsJsonObject().get("q7QmGeQZukAihrETBbbT").getAsJsonObject().get("status").getAsString();
            if (status.equals("off")) {
                lockedState = true;
                
            }
            else {

                if (!landFacade.getStateIrrigate(landSelected.getLandId()) && landFacade.thereIsWaterAvailable(landSelected.getLandId()) && landSelected.getHumidity().intValue() < 100) {
                    DeviceSimulator deviceSimulator = new DeviceSimulator(landSelected.getLandId());
                    landSelected.setState("regando");
                    lockedState = false;
                }
            }
        }
        
        return("land");
    }

    public Land getLandSelected() {
        return landFacade.getLand(landSelected.getLandId());
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

    public boolean getLockedState() {
        return lockedState;
    }

    public void setLockedState(boolean lockedState) {
        this.lockedState = lockedState;
    }
    
}
