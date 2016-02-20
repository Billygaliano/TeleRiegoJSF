/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author inftel10
 */
@ManagedBean
@SessionScoped
public class ProfileBean {
    private int memberNumber;
    private String dni;
    private String userName;
    private String surname;
    private String address;
    private String phone;
    private String email;
    /**
     * Creates a new instance of ProfileBean
     */
    public ProfileBean() {
    }
    
}
