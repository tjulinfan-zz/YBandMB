/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.controller;

import com.ybmb.ejb.BookEJB;
import com.ybmb.entity.Book;
import com.ybmb.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


/**
 *
 * @author LinFan
 */
@ManagedBean
@RequestScoped
public class StoredBooksBean {
    @EJB
    private BookEJB bookEJB;
    
    private LoginBean loginBean;

    /**
     * Creates a new instance of StoredBooksBean
     */
    public StoredBooksBean() {
        loginBean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
    }
    
    private List<Book> storedBooks = new ArrayList<Book>();

    public List<Book> getStoredBooks() {
        User loggedInUser = loginBean.getUser();
        //System.out.println("Username " + loggedInUser.getUsername());
        //System.out.println("Userid " + loggedInUser.getId());
        storedBooks = bookEJB.findByUserid(loggedInUser.getId());
        //System.out.println(storedBooks.size());
        return storedBooks;
    }

    public void setStoredBooks(List<Book> storedBooks) {
        this.storedBooks = storedBooks;
    }
}
