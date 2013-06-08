/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ybmb.controller;

import com.ybmb.ejb.BookEJB;
import com.ybmb.ejb.UserEJB;
import com.ybmb.entity.Book;
import com.ybmb.entity.User;
import com.ybmb.exception.NoSuchUserException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author LinFan
 */
@ManagedBean
@RequestScoped
public class SendEmail {

    /**
     * Creates a new instance of SendEmail
     */
    public SendEmail() {
    }
    @Resource(name = "mail/mail163Account")
    private Session mailSession;
    @EJB
    private UserEJB userEJB;
    @EJB
    private BookEJB bookEJB;

    public String sendEmail(int buyerId, int sellerId, int bookId) throws AddressException, MessagingException, NoSuchUserException {

        User buyer = userEJB.findById(buyerId);
        User seller = userEJB.findById(sellerId);
        Book book = bookEJB.findById(bookId);

        String text = "hi, " + seller.getUsername() + "!\n"
                + buyer.getUsername() + " wants to buy your book!\n"
                + book.getTitle() + " , " + book.getAuthor() + "\n"
                + "Please response him/her: " + buyer.getEmail();

        MimeMessage message = new MimeMessage(mailSession);

        Address toAddress = new InternetAddress(seller.getEmail());
        message.setRecipient(Message.RecipientType.TO, toAddress);
        message.setSubject("From YBMB");
        message.setText(text);
        message.saveChanges();

        Transport tr = mailSession.getTransport();
        String serverPassword = mailSession.getProperty("mail.password");
        tr.connect(null, serverPassword);
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();

        return "search.faces";
    }
}