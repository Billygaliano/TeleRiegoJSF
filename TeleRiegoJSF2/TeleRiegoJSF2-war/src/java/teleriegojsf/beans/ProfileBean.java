/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import sun.misc.IOUtils;
import teleriegojsf.ejb.MembershipFacade;

/**
 *
 * @author inftel10
 */
@ManagedBean
@SessionScoped
public class ProfileBean implements Serializable{
    @EJB
    private MembershipFacade membershipFacade;
    private LoginBean loginBean;
    private String newPassword;
    private String oldPassword;
    private FacesMessage facesMessage;
    private boolean passwordChange;
    private boolean passwordValid;
    private StreamedContent photo;
    private boolean uploadImage;
    private UploadedFile file;
    private String message;

    /**
     * Creates a new instance of ProfileBean
     */
    
    @PostConstruct
    public void init () {
        FacesContext fc = FacesContext.getCurrentInstance();
        loginBean = (LoginBean)fc.getApplication().evaluateExpressionGet(fc, "#{loginBean}", LoginBean.class);
        if(loginBean.getMembershipSelected() == null){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        passwordChange = false;
    }
        
    public ProfileBean() {
    }
    
    public boolean isPasswordChange() {
        return passwordChange;
    }

    public void setPasswordChange(boolean passwordChange) {
        this.passwordChange = passwordChange;
    }
    
    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public boolean isPasswordValid() {
        return passwordValid;
    }

    public void setPasswordValid(boolean passwordValid) {
        this.passwordValid = passwordValid;
    }

    public StreamedContent getPhoto() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if(context.getRenderResponse()){
            return new DefaultStreamedContent();
        }
        else{
            byte[] image = membershipFacade.getMembershipImage(loginBean.getMembershipSelected().getMemberNumber());
            return new DefaultStreamedContent(new ByteArrayInputStream(image));
        }
        
    }

    public void setPhoto(StreamedContent photo) {
        this.photo = photo;
    }

    public boolean isUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(boolean uploadImage) {
        this.uploadImage = uploadImage;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String showChangePassword(){
        passwordChange = true;
        if(loginBean.getMembershipSelected().getRole().equalsIgnoreCase("administrador")){
            return "profileAdmin";
        }else{
            return "profile";
        }
    }
    
    public String doChangePassword() {
        passwordChange = false;
        Boolean correctUpate = membershipFacade.changePassword(oldPassword, newPassword, loginBean.getMembershipSelected().getMemberNumber());
        
        if(loginBean.getMembershipSelected().getRole().equalsIgnoreCase("administrador") && correctUpate == true){
            message = "La contraseña ha sido actualizada correctamente";
            passwordValid = true;
                
            return "profileAdmin";
        }else if(loginBean.getMembershipSelected().getRole().equalsIgnoreCase("administrador") && correctUpate == false){
            message = "Error al actualizar contraseña";
            passwordValid = false;
                
            return "profileAdmin";
        }
        else if(correctUpate==true){
            message = "La contraseña ha sido actualizada correctamente";
            passwordValid = true;
            
            return "profile";
        }
        else{
            message = "Error al actualizar contraseña";
            passwordValid = false;
            
            return "profile";
        }
    }
    
    public String showUploadImage(){
        uploadImage = true;
        
        if(loginBean.getMembershipSelected().getRole().equalsIgnoreCase("administrador")){
            return "profileAdmin";
        }else{
            return "profile";
        }
       
    }
    
    
    public String doUploadImage() throws IOException{
        
        if (file != null){
            
            byte[] image = null;
            image = IOUtils.readFully(file.getInputstream(), (int) file.getSize(), true);
            membershipFacade.setMembershipImage(loginBean.getMembershipSelected().getMemberNumber(), image);
            FacesContext context = FacesContext.getCurrentInstance();
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imagen actualizada correctamente", null);
            context.addMessage("image", facesMessage);
        }

        uploadImage = false;
        if(loginBean.getMembershipSelected().getRole().equalsIgnoreCase("administrador")){
            return "profileAdmin";
        }else{
            return "profile";
        }
    }
    
}