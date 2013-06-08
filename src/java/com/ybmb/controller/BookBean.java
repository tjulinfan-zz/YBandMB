/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.controller;

import com.ybmb.ejb.BookEJB;
import com.ybmb.entity.Book;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author LinFan
 */
@ManagedBean
@RequestScoped
public class BookBean {

    /**
     * Creates a new instance of BookBean
     */
    public BookBean() {
    }
    
    @EJB
    private BookEJB bookEJB;
    
    private List<Book> bookList;

    public Book findById(int id) {
        return bookEJB.findById(id);
    }
    
    public List<Book> getBookList() {
        bookList = bookEJB.findAll();
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
