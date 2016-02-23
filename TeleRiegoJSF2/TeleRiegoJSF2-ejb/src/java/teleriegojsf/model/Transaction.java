/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author inftel12
 */
@Entity
@Table(name = "TRANSACTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
    @NamedQuery(name = "Transaction.findByNorder", query = "SELECT t FROM Transaction t WHERE t.norder = :norder"),
    @NamedQuery(name = "Transaction.findByAmount", query = "SELECT t FROM Transaction t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transaction.findByPrice", query = "SELECT t FROM Transaction t WHERE t.price = :price"),
    @NamedQuery(name = "Transaction.findByMemberNumber", query = "SELECT t FROM Transaction t WHERE t.memberNumber = :memberNumber ORDER BY t.norder DESC"),
    @NamedQuery(name = "Transaction.findByDateOrder", query = "SELECT t FROM Transaction t WHERE t.dateOrder = :dateOrder"),
    @NamedQuery(name = "Transaction.findByStateOrder", query = "SELECT t FROM Transaction t WHERE t.stateOrder = :stateOrder")})
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "idGenerator", allocationSize = 1, sequenceName = "TRANSACTION_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @Basic(optional = false)
    @NotNull
    @Column(name = "N_ORDER")
    private BigDecimal norder;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private double amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private double price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_ORDER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOrder;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "STATE_ORDER")
    private String stateOrder;
    @JoinColumn(name = "LAND_ID", referencedColumnName = "LAND_ID")
    @ManyToOne(optional = false)
    private Land landId;
    @JoinColumn(name = "MEMBER_NUMBER", referencedColumnName = "MEMBER_NUMBER")
    @ManyToOne(optional = false)
    private Membership memberNumber;

    public Transaction() {
    }

    public Transaction(BigDecimal norder) {
        this.norder = norder;
    }

    public Transaction(BigDecimal norder, double amount, double price, Date dateOrder, String stateOrder) {
        this.norder = norder;
        this.amount = amount;
        this.price = price;
        this.dateOrder = dateOrder;
        this.stateOrder = stateOrder;
    }

    public BigDecimal getNorder() {
        return norder;
    }

    public void setNorder(BigDecimal norder) {
        this.norder = norder;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getStateOrder() {
        return stateOrder;
    }

    public void setStateOrder(String stateOrder) {
        this.stateOrder = stateOrder;
    }

    public Land getLandId() {
        return landId;
    }

    public void setLandId(Land landId) {
        this.landId = landId;
    }

    public Membership getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(Membership memberNumber) {
        this.memberNumber = memberNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (norder != null ? norder.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.norder == null && other.norder != null) || (this.norder != null && !this.norder.equals(other.norder))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Transaction[ norder=" + norder + " ]";
    }
    
}
