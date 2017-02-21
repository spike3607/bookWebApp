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
public interface IAuthorDao {

    boolean equals(Object obj);

    List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException;

    void addAuthor(String name, Date date) throws Exception;
    
    void deleteAuthor(Object key) throws Exception;
    
    void updateAuthor(Object key, String columnName, Object newObject) throws Exception;
    
    DbAccessor getDb();

    String getDriverClass();

    String getPassword();

    String getUrl();

    String getUsername();

    int hashCode();

    void setDb(DbAccessor db);

    void setDriverClass(String driverClass);

    void setPassword(String password);

    void setUrl(String url);

    void setUsername(String username);
    
}
