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
    private String member;
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
        return member;
    }

    public void setMemberNumber(String member) {
        this.member = member;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String doLogin() {
        
        int memberNumberInteger = Integer.parseInt(member);
        BigDecimal memberNumber = new BigDecimal(memberNumberInteger);
        boolean testingUser = membershipFacade.testingMemberUser(memberNumber);
        boolean passwordAutenticated = false;
        if(testingUser){
            passwordAutenticated = membershipFacade.autentication(memberNumber, password);
        }
        
        if (member == null || password == null) {
            return("login");
        }else if(!testingUser || !passwordAutenticated){
            errorPassword = true;
            return("login");
        } else {            
            membershipSelected = membershipFacade.getMembership(memberNumber);
            
            if(membershipSelected.getRole().equalsIgnoreCase("administrador")){
                if(membershipFacade.getMembershipAccountId(memberNumber) != null){
                    accountId = membershipFacade.getMembershipAccountId(memberNumber);
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
