/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.controller;

import com.ybmb.ejb.BookEJB;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author LinFan
 */
@ManagedBean
@RequestScoped
public class SoldBook {

    /**
     * Creates a new instance of SoldBook
     */
    public SoldBook() {
    }
    
    @EJB
    private BookEJB bookEJB;
    
    public String soldBook(int id) {
        bookEJB.deleteBook(id);
        return "mystore.faces";
    }
}
