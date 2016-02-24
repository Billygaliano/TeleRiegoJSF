/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author inftel12
 */
@Entity
@Table(name = "LAND")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Land.findAll", query = "SELECT l FROM Land l"),
    @NamedQuery(name = "Land.findByLandId", query = "SELECT l FROM Land l WHERE l.landId = :landId"),
    @NamedQuery(name = "Land.findBySquareMeters", query = "SELECT l FROM Land l WHERE l.squareMeters = :squareMeters"),
    @NamedQuery(name = "Land.findByLongitude", query = "SELECT l FROM Land l WHERE l.longitude = :longitude"),
    @NamedQuery(name = "Land.findByLatitude", query = "SELECT l FROM Land l WHERE l.latitude = :latitude"),
    @NamedQuery(name = "Land.findByHumidity", query = "SELECT l FROM Land l WHERE l.humidity = :humidity"),
    @NamedQuery(name = "Land.findByWMAvailable", query = "SELECT l FROM Land l WHERE l.wMAvailable = :wMAvailable"),
    @NamedQuery(name = "Land.findByState", query = "SELECT l FROM Land l WHERE l.state = :state"),
    @NamedQuery(name = "Land.findByLastDateIrrigation", query = "SELECT l FROM Land l WHERE l.lastDateIrrigation = :lastDateIrrigation"),
    @NamedQuery(name = "Land.findByNameland", query = "SELECT l FROM Land l WHERE l.nameland = :nameland"),
    @NamedQuery(name = "Land.findByIdAdmin", query = "SELECT l FROM Land l WHERE l.idAdmin = :idAdmin"),
    @NamedQuery(name = "Land.findByMemberNumber", query = "SELECT l FROM Land l WHERE l.memberNumber = :memberNumber")})
public class Land implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LAND_ID")
    private BigDecimal landId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SQUARE_METERS")
    private BigInteger squareMeters;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LONGITUDE")
    private String longitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LATITUDE")
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HUMIDITY")
    private BigInteger humidity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "W_M_AVAILABLE")
    private BigInteger wMAvailable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "STATE")
    private String state;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LAST_DATE_IRRIGATION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDateIrrigation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NAMELAND")
    private String nameland;
    @Column(name = "ID_ADMIN")
    private BigInteger idAdmin;
    @JoinColumn(name = "MEMBER_NUMBER", referencedColumnName = "MEMBER_NUMBER")
    @ManyToOne(optional = false)
    private Membership memberNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "landId")
    private Collection<Transaction> transactionCollection;

    public Land() {
    }

    public Land(BigDecimal landId) {
        this.landId = landId;
    }

    public Land(BigDecimal landId, BigInteger squareMeters, String longitude, String latitude, BigInteger humidity, BigInteger wMAvailable, String state, Date lastDateIrrigation, String nameland) {
        this.landId = landId;
        this.squareMeters = squareMeters;
        this.longitude = longitude;
        this.latitude = latitude;
        this.humidity = humidity;
        this.wMAvailable = wMAvailable;
        this.state = state;
        this.lastDateIrrigation = lastDateIrrigation;
        this.nameland = nameland;
    }

    public BigDecimal getLandId() {
        return landId;
    }

    public void setLandId(BigDecimal landId) {
        this.landId = landId;
    }

    public BigInteger getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(BigInteger squareMeters) {
        this.squareMeters = squareMeters;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public BigInteger getHumidity() {
        return humidity;
    }

    public void setHumidity(BigInteger humidity) {
        this.humidity = humidity;
    }

    public BigInteger getWMAvailable() {
        return wMAvailable;
    }

    public void setWMAvailable(BigInteger wMAvailable) {
        this.wMAvailable = wMAvailable;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getLastDateIrrigation() {
        return lastDateIrrigation;
    }

    public void setLastDateIrrigation(Date lastDateIrrigation) {
        this.lastDateIrrigation = lastDateIrrigation;
    }

    public String getNameland() {
        return nameland;
    }

    public void setNameland(String nameland) {
        this.nameland = nameland;
    }

    public BigInteger getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(BigInteger idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Membership getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(Membership memberNumber) {
        this.memberNumber = memberNumber;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (landId != null ? landId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Land)) {
            return false;
        }
        Land other = (Land) object;
        if ((this.landId == null && other.landId != null) || (this.landId != null && !this.landId.equals(other.landId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Land[ landId=" + landId + " ]";
    }
    
}
