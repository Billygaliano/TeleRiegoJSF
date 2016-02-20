/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.ejb;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import teleriegojsf.model.Land;
import teleriegojsf.model.Membership;
import teleriegojsf.model.Transaction;

/**
 *
 * @author inftel12
 */
@Stateless
public class TransactionFacade extends AbstractFacade<Transaction> {
    @PersistenceContext(unitName = "TeleRiegoJSF2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransactionFacade() {
        super(Transaction.class);
    }
    
    public void setTransaction(BigDecimal landId,BigDecimal memberNumber,double amount) {

        Membership membership = em.find(Membership.class,memberNumber);
        Land land = em.find(Land.class, landId);
        Date date = new Date();
        Transaction transaction = new Transaction();
        double price = 0.22;
        try {
           
            double total = amount*price; 
            
            transaction.setMemberNumber(membership);
            transaction.setAmount(amount);
            transaction.setLandId(land);
            transaction.setDateOrder(date);
            transaction.setStateOrder("pendiente");
            transaction.setPrice(total);
            em.persist(transaction);
        } catch (Exception ex) {
            Logger.getLogger(MembershipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void acceptAdminTransaction(BigDecimal norder){
        Transaction transaction = em.find(Transaction.class,norder);
        transaction.setStateOrder("pagado");
        em.persist(transaction);
    }
    
    public void deniedAdminTransaction(BigDecimal norder){
        Transaction transaction = em.find(Transaction.class,norder);
        transaction.setStateOrder("rechazado");
        em.persist(transaction);
    }
    
    public Collection<Transaction> getTransactions(){
        Query query = em.createNamedQuery("Transaction.findAll",Transaction.class);
        Collection<Transaction> transactions = query.getResultList();
        return transactions;
    }

    public Collection<Transaction> getTransactionsByMember(BigDecimal memberNumber) {
        Membership membership = em.find(Membership.class, memberNumber);
        Query query = em.createNamedQuery("Transaction.findByMemberNumber",Transaction.class).setParameter("memberNumber", membership).setMaxResults(50);
        Collection<Transaction> transactions = query.getResultList();
        return transactions;
    }

    public String getTransactionState(BigDecimal nOrder) {
        return em.find(Transaction.class, nOrder).getStateOrder();
    }

    public Collection<Transaction> getPendantTransactions() {
        Query query = em.createNamedQuery("Transaction.findByStateOrder",Transaction.class).setParameter("stateOrder", "pendiente");
        Collection<Transaction> transactions = query.getResultList();
        return transactions;
    }
    
}
