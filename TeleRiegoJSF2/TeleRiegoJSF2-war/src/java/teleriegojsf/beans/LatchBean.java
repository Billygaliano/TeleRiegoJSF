/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import com.elevenpaths.latch.LatchApp;
import com.elevenpaths.latch.LatchResponse;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import teleriegojsf.ejb.MembershipFacade;

/**
 *
 * @author inftel10
 */
@ManagedBean
@RequestScoped
public class LatchBean {
    @EJB
    private MembershipFacade membershipFacade;
    private static final String LATCH_APP_ID = "W2i6TtX6N8C9GNugxkRh";
    private static final String LATCH_SECRET = "T67WcbigW69xjzqJMFuYp9xFRFRj3YrcgMVaxwTQ";
    private String pairCode;
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private String message;
    
    /**
     * Creates a new instance of LatchBean
     */
    public LatchBean() {
    }
    
    @PostConstruct
    public void init(){
        message = null;
    }

    public String getPairCode() {
        return pairCode;
    }

    public void setPairCode(String pairCode) {
        this.pairCode = pairCode;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    public String doPair(){
        
        LatchApp latch = new LatchApp(LATCH_APP_ID, LATCH_SECRET);
        
        LatchResponse pairResponse = latch.pair(pairCode);
        if(pairResponse.getError() == null){
                String accountId = pairResponse.getData().get("accountId").getAsString();
        
                membershipFacade.setMembershipAccountId(accountId, loginBean.getMembershipSelected().getMemberNumber());        
        
                loginBean.setMembershipSelected(membershipFacade.getMembership(loginBean.getMembershipSelected().getMemberNumber()));
                
        }else{
            message = "Error al parearse";
        }

        return "profile";
    }
    
    public String doUnpair(){
        
        LatchApp latch = new LatchApp(LATCH_APP_ID, LATCH_SECRET);
                
        String accountId = membershipFacade.getMembershipAccountId(loginBean.getMembershipSelected().getMemberNumber());
        LatchResponse unPairResponse = latch.unpair(accountId);
        membershipFacade.deleteMembershipAccountId(loginBean.getMembershipSelected().getMemberNumber());
        loginBean.setMembershipSelected(membershipFacade.getMembership(loginBean.getMembershipSelected().getMemberNumber()));
        
        return "profile";
    }
   
}
