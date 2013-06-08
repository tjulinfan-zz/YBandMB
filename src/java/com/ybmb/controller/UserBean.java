/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.controller;

import com.ybmb.ejb.UserEJB;
import com.ybmb.entity.User;
import com.ybmb.exception.NoSuchUserException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author LinFan
 */
@ManagedBean
@ApplicationScoped
public class UserBean {

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }
    
    @EJB
    private UserEJB userEJB;
    
    private User user = new User();
    
    public User findById(int userid) {
        try {
            return userEJB.findById(userid);
        } catch (NoSuchUserException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String doCreateUser() {
        userEJB.createUser(user);
        user = new User();
        return "home.faces";
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void validateUserExsiting(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        String username = (String)value;
        try {
            userEJB.findByUsername(username);
        } catch (NoSuchUserException e) {
            return;
        }
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "The user already exists", 
                    username + " already exists");
        throw new ValidatorException(facesMessage);
    }
}
