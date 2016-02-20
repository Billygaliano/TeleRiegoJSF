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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author inftel12
 */
@Entity
@Table(name = "MEMBERSHIP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Membership.findAll", query = "SELECT m FROM Membership m"),
    @NamedQuery(name = "Membership.findByMemberNumber", query = "SELECT m FROM Membership m WHERE m.memberNumber = :memberNumber"),
    @NamedQuery(name = "Membership.findByDni", query = "SELECT m FROM Membership m WHERE m.dni = :dni"),
    @NamedQuery(name = "Membership.findByUserName", query = "SELECT m FROM Membership m WHERE m.userName = :userName"),
    @NamedQuery(name = "Membership.findBySurname", query = "SELECT m FROM Membership m WHERE m.surname = :surname"),
    @NamedQuery(name = "Membership.findByEmail", query = "SELECT m FROM Membership m WHERE m.email = :email"),
    @NamedQuery(name = "Membership.findByPhone", query = "SELECT m FROM Membership m WHERE m.phone = :phone"),
    @NamedQuery(name = "Membership.findByAddress", query = "SELECT m FROM Membership m WHERE m.address = :address"),
    @NamedQuery(name = "Membership.findByRole", query = "SELECT m FROM Membership m WHERE m.role = :role"),
    @NamedQuery(name = "Membership.findByAccountid", query = "SELECT m FROM Membership m WHERE m.accountid = :accountid"),
    @NamedQuery(name = "Membership.findBySalt", query = "SELECT m FROM Membership m WHERE m.salt = :salt"),
    @NamedQuery(name = "Membership.findByPassword", query = "SELECT m FROM Membership m WHERE m.password = :password"),
    @NamedQuery(name = "Membership.findByIterations", query = "SELECT m FROM Membership m WHERE m.iterations = :iterations")})
public class Membership implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEMBER_NUMBER")
    private BigDecimal memberNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "DNI")
    private String dni;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USER_NAME")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "SURNAME")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PHONE")
    private BigInteger phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ADDRESS")
    private String address;
    @Lob
    @Column(name = "IMAGE")
    private byte[] image;
    @Size(max = 20)
    @Column(name = "ROLE")
    private String role;
    @Size(max = 400)
    @Column(name = "ACCOUNTID")
    private String accountid;
    @Size(max = 400)
    @Column(name = "SALT")
    private String salt;
    @Size(max = 300)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ITERATIONS")
    private BigInteger iterations;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberNumber")
    private Collection<Land> landCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberNumber")
    private Collection<Transaction> transactionCollection;

    public Membership() {
    }

    public Membership(BigDecimal memberNumber) {
        this.memberNumber = memberNumber;
    }

    public Membership(BigDecimal memberNumber, String dni, String userName, String surname, String email, BigInteger phone, String address) {
        this.memberNumber = memberNumber;
        this.dni = dni;
        this.userName = userName;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public BigDecimal getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(BigDecimal memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getPhone() {
        return phone;
    }

    public void setPhone(BigInteger phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getIterations() {
        return iterations;
    }

    public void setIterations(BigInteger iterations) {
        this.iterations = iterations;
    }

    @XmlTransient
    public Collection<Land> getLandCollection() {
        return landCollection;
    }

    public void setLandCollection(Collection<Land> landCollection) {
        this.landCollection = landCollection;
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
        hash += (memberNumber != null ? memberNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membership)) {
            return false;
        }
        Membership other = (Membership) object;
        if ((this.memberNumber == null && other.memberNumber != null) || (this.memberNumber != null && !this.memberNumber.equals(other.memberNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Membership[ memberNumber=" + memberNumber + " ]";
    }
    
}
