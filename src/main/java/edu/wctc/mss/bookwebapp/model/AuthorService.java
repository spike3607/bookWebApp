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

    public AuthorService(String driverClass, String url, String username, String password) {
        authorDao = new AuthorDao(new MySqlDbAccessor(), driverClass, url, username, password);
    }
   
    public List<Author> getAllAuthors() throws ClassNotFoundException, SQLException {
        return authorDao.getAuthorList(500);
    }
    
    public void addAuthor(String name, Date dateCreated) throws SQLException, ClassNotFoundException {
        authorDao.addAuthor(name, dateCreated);
    }

    public void updateAuthor(Object key, String newName) throws SQLException, ClassNotFoundException  {
        authorDao.updateAuthor(key, newName);
    }

    public void deleteAuthor(Object key) throws SQLException, ClassNotFoundException {
        authorDao.deleteAuthor(key);
    }
    public static void main(String[] args) throws Exception {
      AuthorService serv = new AuthorService("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book", "root", "admin");
      
      List<Author> authors = serv.getAllAuthors();
      for (Author a : authors) {
          System.out.println(a);
      }
    }

    public IAuthorDao getAuthorDao() {
        return authorDao;
    }

    public void setAuthorDao(IAuthorDao authorDao) {
        this.authorDao = authorDao;
    }
    
}
