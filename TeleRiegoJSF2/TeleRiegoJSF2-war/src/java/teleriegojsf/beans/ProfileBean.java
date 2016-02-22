/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import teleriegojsf.ejb.MembershipFacade;
import teleriegojsf.model.Membership;

/**
 *
 * @author inftel10
 */
@ManagedBean
@RequestScoped
public class ProfileBean implements Serializable{
    
    @EJB
    private MembershipFacade membershipFacade;
    
    private Membership membershipSelected;
    private List<Membership> list;
    /**
     * Creates a new instance of ProfileBean
     */
    
    public ProfileBean() {
    }
    
    @PostConstruct
    public void init () {
        BigDecimal number = new BigDecimal("123456");
        membershipSelected = membershipFacade.getMembership(number);
        
    }

    public Membership getMembershipSelected() {
        return membershipSelected;
    }

    public void setMembershipSelected(Membership membershipSelected) {
        this.membershipSelected = membershipSelected;
    }

}
