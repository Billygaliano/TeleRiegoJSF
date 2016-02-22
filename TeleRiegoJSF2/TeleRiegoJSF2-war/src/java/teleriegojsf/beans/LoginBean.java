package teleriegojsf.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import teleriegojsf.ejb.MembershipFacade;
import teleriegojsf.model.Membership;

/**
 *
 * @author inftel12
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
    @EJB
    private MembershipFacade membershipFacade;
    private String member;
    private String password;
    private boolean accountIdExists;
    private String accountId;
    private Membership membershipSelected;
    private FacesMessage facesMessage;
    
    @PostConstruct
    public void init(){
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
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (this.member.isEmpty() || this.password.isEmpty()) {
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Introduzca un usuario y una contraseña", null);
            context.addMessage(null, facesMessage);
            return("login");
        }
        int memberNumberInteger = Integer.parseInt(this.member);
        BigDecimal memberNumber = new BigDecimal(memberNumberInteger);
        boolean testingUser = membershipFacade.testingMemberUser(memberNumber);
        if(!testingUser){
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario incorrecto", null);
            context.addMessage(null, facesMessage);
            return "login";
        }
        boolean passwordAutenticated = membershipFacade.autentication(memberNumber, this.password);
        if(!passwordAutenticated){
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña incorrecta", null);
            context.addMessage(null, facesMessage);
            return "login";
        }
        else {
            membershipSelected = membershipFacade.getMembership(memberNumber);
            context.getExternalContext().getSessionMap().put("membershipSelected", membershipSelected);

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
    
    public String existUserLoged(){
        
        if(membershipSelected != null){
            return("profile");
        }else{
            return("login");
        }
    }
    
    public String doLogout() {
        HttpSession session =  (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        this.membershipSelected = new Membership();
        this.member = null;
        this.password = null;
        return("login");
    }
}
