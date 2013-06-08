/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.controller;

import com.ybmb.ejb.BookEJB;
import com.ybmb.ejb.UserEJB;
import com.ybmb.entity.User;
import com.ybmb.exception.NoSuchUserException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author LinFan
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    @EJB
    private UserEJB userEJB;
    
    @EJB
    private BookEJB bookEJB;
    
    private User user = null;
    private boolean loggedIn = false;

    public void validateUser(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        UIInput usernameInput = (UIInput)facesContext.getViewRoot().findComponent("loginForm:username");
        String username = (String)usernameInput.getValue();
        String password = (String)value;
        if (!userEJB.isPasswordCorrect(username, password)) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "There was a problem with your request", 
                    "There was an error with your Username/Password combination. Please try again.");
            throw new ValidatorException(facesMessage);
        }
    }
    
    public String logIn() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIInput usernameInput = (UIInput)facesContext.getViewRoot().findComponent("loginForm:username");
        String username = (String)usernameInput.getValue();
        try {
            this.setUser(userEJB.findByUsername(username));
            this.setLoggedIn(true);
        } catch(NoSuchUserException e) {
        } finally {
            return "home.faces";
        }
    }
    
    public String logOut() {
        this.setUser(null);
        this.setLoggedIn(false);
        return "home.faces";
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
