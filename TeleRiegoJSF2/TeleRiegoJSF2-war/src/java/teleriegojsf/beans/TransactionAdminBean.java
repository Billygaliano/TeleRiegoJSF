/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import teleriegojsf.ejb.LandFacade;
import teleriegojsf.ejb.MembershipFacade;
import teleriegojsf.ejb.TransactionFacade;
import teleriegojsf.model.Land;
import teleriegojsf.model.Membership;
import teleriegojsf.model.Transaction;

/**
 *
 * @author inftel12
 */
@ManagedBean
@RequestScoped
public class TransactionAdminBean {
    private LoginBean loginBean;    
    @EJB
    private LandFacade landFacade;
    @EJB
    private TransactionFacade transactionFacade;
    @EJB
    private MembershipFacade membershipFacade;
    
    private Collection<Transaction> transactions;
    
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
        
        transactions = transactionFacade.getTransactions();
    } 
    
    public String doAccept(Transaction transaction){
        Land land = transaction.getLandId();
        BigDecimal landId = land.getLandId();
        BigDecimal nOrder = transaction.getNorder();
        
        double amountWaterInteger = transaction.getAmount();
        BigInteger amountWater = new BigDecimal(amountWaterInteger).toBigInteger();
        BigDecimal memberNumber = new BigDecimal(loginBean.getMemberNumber());
        Membership membership = membershipFacade.getMembership(memberNumber);
        
        //Only can access if membership is an administrator
        if(!membership.getRole().equalsIgnoreCase("administrador")){
            return("login");
        }
        
        //Only accept transaction if state is pendant.
        if(transactionFacade.getTransactionState(nOrder).equalsIgnoreCase("pendiente")){
           transactionFacade.acceptAdminTransaction(nOrder);
           landFacade.updateAdminLand(landId,amountWater);
           membershipFacade.sendTransactionEmail(nOrder);
        }
        
        transactions = transactionFacade.getTransactions();
        
        return ("transactionAdmin");
    }
    
    public String doRefuse(Transaction transaction){
        Land land = transaction.getLandId();
        BigDecimal landId = land.getLandId();
        BigDecimal nOrder = transaction.getNorder();
        
        double amountWaterInteger = transaction.getAmount();
        BigInteger amountWater = new BigDecimal(amountWaterInteger).toBigInteger();
        BigDecimal memberNumber = new BigDecimal("1234");
        Membership membership = membershipFacade.getMembership(memberNumber);
        
        //Only can access if membership is an administrator
        if(!membership.getRole().equalsIgnoreCase("administrador")){
            return("login");
        }
        
        //Only accept transaction if state is pendant.
        if(transactionFacade.getTransactionState(nOrder).equalsIgnoreCase("pendiente")){
           transactionFacade.deniedAdminTransaction(nOrder);
           membershipFacade.sendTransactionEmail(nOrder);
        }
        
        transactions = transactionFacade.getTransactions();
        
        return ("transactionAdmin");
    }

    /**
     * Creates a new instance of TransactionAdmin
     */
    public TransactionAdminBean() {
    }

    public TransactionFacade getTransactionFacade() {
        return transactionFacade;
    }

    public void setTransactionFacade(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<Transaction> transactions) {
        this.transactions = transactions;
    }
    
}
