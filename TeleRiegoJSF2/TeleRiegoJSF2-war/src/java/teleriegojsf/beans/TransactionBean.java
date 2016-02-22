/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.math.BigDecimal;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import teleriegojsf.ejb.TransactionFacade;
import teleriegojsf.model.Transaction;


 
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import teleriegojsf.ejb.LandFacade;
import teleriegojsf.ejb.MembershipFacade;
import teleriegojsf.model.Land;
import teleriegojsf.model.Membership;

/**
 *
 * @author inftel11
 */
@ManagedBean
@RequestScoped
public class TransactionBean {
    @EJB
    private LandFacade landFacade;
    @EJB
    private MembershipFacade membershipFacade;
    @EJB
    private TransactionFacade transactionFacade;

    public LandFacade getLandFacade() {
        return landFacade;
    }

    public void setLandFacade(LandFacade landFacade) {
        this.landFacade = landFacade;
    }

    public MembershipFacade getMembershipFacade() {
        return membershipFacade;
    }

    public void setMembershipFacade(MembershipFacade membershipFacade) {
        this.membershipFacade = membershipFacade;
    }

    public Collection<Land> getLands() {
        return lands;
    }

    public void setLands(Collection<Land> lands) {
        this.lands = lands;
    }
    
    
    
    private Collection<Transaction> transactions;
    private Collection<Land> lands;


    /**
     * Creates a new instance of TransactionBean
     */
    public TransactionBean() {
        
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
    
    @PostConstruct
    public void init(){
        BigDecimal number = new BigDecimal("123456");
        
        Membership member = membershipFacade.getMembership(number);
        lands = member.getLandCollection();
        
        for(Land land: lands){
            System.out.println("Tierra: " + land.getNameland());
        }
        
        
        
        transactions = transactionFacade.getTransactionsByMember(number);
     

        
      
    } 
    
}
