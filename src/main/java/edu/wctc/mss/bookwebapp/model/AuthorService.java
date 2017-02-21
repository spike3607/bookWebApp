/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Spike
 */
public class AuthorService {
    private IAuthorDao authorDao;
    private String driverClass;
    private String url;
    private String username;
    private String password;

    public AuthorService(IAuthorDao authorDao, String driverClass, String url, String username, String password) {
        this.authorDao = authorDao;
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
    }
   
    public List<Author> getAllAuthors() throws ClassNotFoundException, SQLException {
        return authorDao.getAuthorList("author", 500);
    }
    
    public void addAuthor(String name, Date dateCreated) throws Exception {
        authorDao.addAuthor(name, dateCreated);
    }

    public void updateAuthor(Object key, String columnName, Object newObject) throws Exception {
        authorDao.updateAuthor(key, columnName, newObject);
    }

    public void deleteAuthor(Object key) throws Exception {
        authorDao.deleteAuthor(key);
    }
    public static void main(String[] args) throws Exception {
      
    }
}
