/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import javax.faces.bean.RequestScoped;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;    
    private Collection<Land> lands;
    
    

    /**
     * Creates a new instance of LandsBean
     */
    public LandsBean() {
    }
 
    @PostConstruct
    public void init() {
        if(loginBean.getMembershipSelected().getRole().equalsIgnoreCase("propietario")){
            lands = landFacade.getOwnerCollection(loginBean.getMembershipSelected().getMemberNumber());
        }else{
            lands = loginBean.getMembershipSelected().getLandCollection();
        } 
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
     
    public Collection<Land> getLands() {
        return lands;
    }

    public void setLands(Collection<Land> lands) {
        this.lands = lands;
    }
}
