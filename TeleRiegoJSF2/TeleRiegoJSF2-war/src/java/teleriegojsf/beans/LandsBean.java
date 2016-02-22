/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.math.BigDecimal;
import javax.faces.bean.RequestScoped;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import teleriegojsf.ejb.MembershipFacade;
import teleriegojsf.model.Land;
import teleriegojsf.model.Membership;

/**
 *
 * @author inftel12
 */
@ManagedBean
@RequestScoped
public class LandsBean {
    @EJB
    private MembershipFacade membershipFacade;    
    private Collection<Land> lands;

    /**
     * Creates a new instance of LandsBean
     */
    public LandsBean() {
    }
 
    @PostConstruct
    public void init() {
        BigDecimal memberNumber = new BigDecimal("123456");
        Membership membershipSelected = membershipFacade.getMembership(memberNumber);
        lands = membershipSelected.getLandCollection();
        for (Land land : lands) {
            System.out.println("Tierras: " + land.getNameland());
        }
    }
     
    public Collection<Land> getLands() {
        return lands;
    }

    public void setLands(Collection<Land> lands) {
        this.lands = lands;
    }
    
}
