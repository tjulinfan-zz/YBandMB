/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.controller;

import com.ybmb.ejb.BookEJB;
import com.ybmb.entity.Book;
import com.ybmb.entity.User;
import com.ybmb.tool.Bookinfo;
import com.ybmb.tool.SAXParseService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author LinFan
 */
@ManagedBean
@RequestScoped
public class SellBook {

    /**
     * Creates a new instance of SellBook
     */
    public SellBook() {
    }
    
    @EJB
    private BookEJB bookEJB;
    
    public String doSellBook(User user) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String isbn = (String)((UIInput)facesContext.getViewRoot().findComponent("sellForm:isbn")).getValue();
            float price = Float.parseFloat((String)((UIInput)facesContext.getViewRoot().findComponent("sellForm:price")).getValue());
            String description = (String)((UIInput)facesContext.getViewRoot().findComponent("sellForm:description")).getValue();;
            Bookinfo bookinfo = SAXParseService.getBookinfo("http://api.douban.com/book/subject/isbn/" + isbn);
            bookinfo.setPrice(price);
            bookinfo.setDescription(description);
            Book book = bookEJB.addBook(bookinfo, user);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SellBook.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(SellBook.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SellBook.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return "mystore.faces";
    }
}
