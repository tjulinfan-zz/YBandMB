package com.ybmb.tool;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author LinFan
 */
public class SAXParseService extends DefaultHandler {

    public static Bookinfo getBookinfo(String bookinfoUrl) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        SAXParseService sps = new SAXParseService();
        sp.parse(bookinfoUrl, sps);
        return sps.getBookinfo();
    }
    
    private String preTag = "";
    private Bookinfo bookinfo = new Bookinfo();

    public Bookinfo getBookinfo() {
        return bookinfo;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("db:attribute")) {
            preTag = attributes.getValue("name");
        } else if (qName.equals("summary")) {
            preTag = "summary";
        } else if (qName.equals("link")) {
            if (attributes.getValue("rel").equals("image")) {
                bookinfo.setImgUrl(attributes.getValue("href"));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        preTag = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        if (preTag.equals("title")) {
            bookinfo.setTitle(text);
        } else if (preTag.equals("author")) {
            bookinfo.setAuthor(text);
        } else if (preTag.equals("isbn10")) {
            bookinfo.setIsbn10(text);
        } else if (preTag.equals("isbn13")) {
            bookinfo.setIsbn13(text);
        } else if (preTag.equals("summary")) {
            bookinfo.setSummary(text);
        }
    }
}