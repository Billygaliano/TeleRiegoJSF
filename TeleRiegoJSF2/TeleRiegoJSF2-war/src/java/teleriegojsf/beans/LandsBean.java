/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.io.IOException;
import javax.faces.bean.RequestScoped;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import teleriegojsf.ejb.LandFacade;
import teleriegojsf.model.Land;

/**
 *
 * @author inftel12
 */
@ManagedBean
@RequestScoped
public class LandsBean {
    @EJB
    private LandFacade landFacade;
    private LoginBean loginBean;    
    private Collection<Land> lands;

    /**
     * Creates a new instance of LandsBean
     */
    public LandsBean() {
    }
 
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        loginBean = (LoginBean)fc.getApplication().evaluateExpressionGet(fc, "#{loginBean}", LoginBean.class);        
        
        if(loginBean.getMembershipSelected() == null){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(loginBean.getMembershipSelected().getRole().equalsIgnoreCase("propietario")){
            lands = landFacade.getOwnerCollection(loginBean.getMembershipSelected().getMemberNumber());
        }else{
            lands = landFacade.getUserCollection(loginBean.getMembershipSelected());
        } 
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
     
    public Collection<Land> getLands() {
        if(loginBean.getMembershipSelected().getRole().equalsIgnoreCase("propietario")){
            return lands = landFacade.getOwnerCollection(loginBean.getMembershipSelected().getMemberNumber());
        }else{
            return lands = landFacade.getUserCollection(loginBean.getMembershipSelected());
        }
    }

    public void setLands(Collection<Land> lands) {
        this.lands = lands;
    }
}
