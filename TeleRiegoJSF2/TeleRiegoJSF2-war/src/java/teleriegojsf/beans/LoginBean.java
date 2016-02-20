package teleriegojsf.beans;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import teleriegojsf.ejb.MembershipFacade;
import teleriegojsf.model.Membership;

/**
 *
 * @author inftel12
 */
@ManagedBean
@SessionScoped
public class LoginBean {
    @EJB
    private MembershipFacade membershipFacade;
    private String memberNumber;
    private String password;
    private boolean errorPassword;
    private boolean accountIdExists;
    private String accountId;
    private Membership membershipSelected;
    
    @PostConstruct
    public void init(){
        errorPassword = false;
    }
    
    public LoginBean() {
    }

    public Membership getMembershipSelected() {
        return membershipSelected;
    }

    public void setMembershipSelected(Membership membershipSelected) {
        this.membershipSelected = membershipSelected;
    }

    public boolean isAccountIdExists() {
        return accountIdExists;
    }

    public void setAccountIdExists(boolean accountIdExists) {
        this.accountIdExists = accountIdExists;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isErrorPassword() {
        return errorPassword;
    }

    public void setErrorPassword(boolean errorPassword) {
        this.errorPassword = errorPassword;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String doLogin() {
        
        int memberNumberInteger = Integer.parseInt(memberNumber);
        BigDecimal memberNumberBI = new BigDecimal(memberNumberInteger);
        boolean testingUser = membershipFacade.testingMemberUser(memberNumberBI);
        boolean passwordAutenticated = membershipFacade.autentication(memberNumberBI, password);
        
        if (memberNumber == null || password == null) {
            return("login");
        }else if(!testingUser || !passwordAutenticated){
            errorPassword = true;
            return("login");
        } else {            
            membershipSelected = membershipFacade.getMembership(memberNumberBI);
            
            if(membershipSelected.getRole().equalsIgnoreCase("administrador")){
                if(membershipFacade.getMembershipAccountId(memberNumberBI) != null){
                    accountId = membershipFacade.getMembershipAccountId(memberNumberBI);
                    accountIdExists = true;
                }else{
                    accountIdExists = false;
                }
                return("profileAdmin");
            }
            return("profile");
        }
    }
}
