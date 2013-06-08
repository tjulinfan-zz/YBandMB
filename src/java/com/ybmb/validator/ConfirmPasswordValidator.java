/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author LinFan
 */
public class ConfirmPasswordValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIInput passwordInput = (UIInput)context.getViewRoot().findComponent("registerForm:password");
        String password = (String)passwordInput.getValue();
        if (!password.equals((String)value)) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "There was a problem with your request", 
                    "Please check that your passwords match and try again.");
            throw new ValidatorException(facesMessage);
        }
    }
    
}
