/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import teleriegojsf.ejb.TransactionFacade;
import teleriegojsf.model.Transaction;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import teleriegojsf.ejb.LandFacade;
import teleriegojsf.ejb.MembershipFacade;

/**
 *
 * @author inftel11
 */
@ManagedBean
@RequestScoped
public class TransactionBean {
    private LoginBean loginBean;    
    @EJB
    private LandFacade landFacade;
    @EJB
    private MembershipFacade membershipFacade;
    @EJB
    private TransactionFacade transactionFacade;
    
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
        
        BigDecimal memberNumber = new BigDecimal(loginBean.getMemberNumber());
        transactions = transactionFacade.getTransactionsByMember(memberNumber);
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

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
}
