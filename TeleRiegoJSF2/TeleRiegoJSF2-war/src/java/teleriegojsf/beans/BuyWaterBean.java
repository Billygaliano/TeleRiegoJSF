/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import teleriegojsf.ejb.TransactionFacade;
import teleriegojsf.model.Land;

/**
 *
 * @author inftel12
 */
@ManagedBean
@SessionScoped
public class BuyWaterBean implements Serializable{
    private boolean confirmedBuyWater;
    private String quantity;
    private double total;
    private Land selectedLand;
    private LoginBean loginBean;
    
    @EJB
    private TransactionFacade transactionFacade;
    
    @PostConstruct
    public void init () {
        FacesContext fc = FacesContext.getCurrentInstance();
        loginBean = (LoginBean)fc.getApplication().evaluateExpressionGet(fc, "#{loginBean}", LoginBean.class);
        if(loginBean.getMembershipSelected() == null){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Creates a new instance of BuyWaterBean
     */
    public BuyWaterBean() {
    }
    
    public String doBuyWater(){                
        return("buyWater");
    }    
    
    public String doConfirmationBuyWater(Land landSelected){        
        double quantityDouble = Integer.parseInt(quantity);  
        transactionFacade.setTransaction(selectedLand.getLandId(),selectedLand.getMemberNumber().getMemberNumber(),quantityDouble);
        quantity = null;
        total = quantityDouble * 0.22;
        confirmedBuyWater = true;
        
        return("buyWater");
    }

    public Land getSelectedLand() {
        return selectedLand;
    }

    public void setSelectedLand(Land selectedLand) {
        this.selectedLand = selectedLand;
    }

    public boolean isConfirmedBuyWater() {
        return confirmedBuyWater;
    }

    public void setConfirmedBuyWater(boolean confirmedBuyWater) {
        this.confirmedBuyWater = confirmedBuyWater;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
