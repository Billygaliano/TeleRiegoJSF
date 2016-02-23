/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
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
    
    @EJB
    private TransactionFacade transactionFacade;

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
