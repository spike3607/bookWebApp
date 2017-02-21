/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Spike
 */
public class AuthorDao implements IAuthorDao {

    private DbAccessor db;
    String driverClass;
    String url;
    String username;
    String password;

    public AuthorDao() {
    }

    public AuthorDao(DbAccessor db, String driverClass, String url, String username, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Author> getAuthorList(String tableName, int maxRecords) throws ClassNotFoundException, SQLException {
        List<Author> records = new ArrayList<>();

        db.openConnection(driverClass, url, username, password);
        List<Map<String, Object>> rawData = db.getAllRecords(tableName, maxRecords);

        for (Map<String, Object> rawRec : rawData) {
            Author author = new Author();
            Object objId = rawRec.get("author_id");
            Integer authorId = (Integer) objId;
            author.setAuthorId(authorId);

            Object objName = rawRec.get("author_name");
            String name = (objName != null) ? objName.toString() : "";
            author.setAuthorName(name);

            Object objDate = rawRec.get("date_added");
            Date date = (objDate != null) ? (Date) objDate : null;
            author.setDateAdded(date);

            records.add(author);
        }
        
        db.closeConnection();
        
        return records;
    }
    
    @Override
    public void addAuthor(String name, Date date) throws Exception {
        db.openConnection(driverClass, url, username, password);

        ArrayList columns = new ArrayList();
        columns.add("author_name");
        columns.add("date_added");

        ArrayList values = new ArrayList();
        values.add(name);
        values.add(date);

        db.createRecord("author", columns, values);

        db.closeConnection();
    }
    
    @Override
    public void deleteAuthor(Object key) throws Exception {
        db.openConnection(driverClass, url, username, password);

        db.deleteRecordByPK("author", "author_id", (int) key);

        db.closeConnection();
    }
    
    @Override
    public void updateAuthor(Object key, String columnName, Object newObject) throws Exception {
        db.openConnection(driverClass, url, username, password);

        db.updateRecordByPK("author", columnName, newObject, "author_id", key);

        db.closeConnection();
    }

    @Override
    public DbAccessor getDb() {
        return db;
    }

    @Override
    public void setDb(DbAccessor db) {
        this.db = db;
    }

    @Override
    public String getDriverClass() {
        return driverClass;
    }

    @Override
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.driverClass);
        hash = 41 * hash + Objects.hashCode(this.url);
        hash = 41 * hash + Objects.hashCode(this.username);
        hash = 41 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuthorDao other = (AuthorDao) obj;
        if (!Objects.equals(this.driverClass, other.driverClass)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        IAuthorDao dao = new AuthorDao(new MySqlDbAccessor(),"com.mysql.jdbc.Driver", 
                                        "jdbc:mysql://localhost:3306/book",
                                         "root", "admin");
        
        List<Author> list = dao.getAuthorList("author", 50);
        
        System.out.println(list);
    }
}
