/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.ejb;

import com.ybmb.entity.Book;
import com.ybmb.entity.User;
import com.ybmb.tool.Bookinfo;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LinFan
 */
@Stateless
public class BookEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "YBandMBPU")
    private EntityManager em;
    
    public Book addBook(Book book) {
        em.persist(book);
        return book;
    }
    
    public Book addBook(Bookinfo bookinfo, User holder) {
        Calendar cal = Calendar.getInstance();
        Date sellDate = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        Book book = new Book(holder.getId(), 
                bookinfo.getIsbn10(), bookinfo.getIsbn13(), 
                bookinfo.getTitle(), bookinfo.getAuthor(), bookinfo.getPrice(), bookinfo.getImgUrl(), 
                sellDate,
                bookinfo.getDescription());
        em.persist(book);
        return book;
    }
    
    public void deleteBook(int id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
    
    public List<Book> findByIsbn(String isbn) {
        Query q = em.createQuery("SELECT b FROM Book b WHERE b.isbn13 = :isbn13");
        q.setParameter("isbn13", isbn);
        return q.getResultList();
    }
    
    public List<Book> findByTitle(String title) {
        Query q = em.createQuery("SELECT b FROM Book b WHERE b.title = :title");
        q.setParameter("title", title);
        return q.getResultList();
    }
    
    public List<Book> findByUserid(int userid) {
        Query q = em.createQuery("SELECT b FROM Book b WHERE b.userid = :userid");
        q.setParameter("userid", userid);
        return q.getResultList();
    }
    
    public List<Book> findAll() {
        Query q = em.createQuery("SELECT b FROM Book b");
        return q.getResultList();
    }
    
    public Book findById(int id) {
        Query q = em.createQuery("SELECT b FROM Book b WHERE b.id = :id");
        q.setParameter("id", id);
        return (Book)q.getSingleResult();
    }
}
