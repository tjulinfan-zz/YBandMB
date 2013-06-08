/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.controller;

import com.ybmb.ejb.BookEJB;
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
public class SearchBookBean {

    /**
     * Creates a new instance of SearchBookBean
     */
    public SearchBookBean() {
    }
    
    @EJB
    private BookEJB bookEJB;
    
    private boolean searching = false;
    
    public String searchBookByTitle(String title) {
        this.setSearching(true);
        List bookList = bookEJB.findByTitle(title);
        return "search_result.faces";
    }

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }
}
