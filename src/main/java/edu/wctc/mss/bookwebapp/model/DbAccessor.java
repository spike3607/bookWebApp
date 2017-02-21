package edu.wctc.mss.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Spike
 */
public interface DbAccessor {

    void closeConnection() throws SQLException;

    List<Map<String, Object>> getAllRecords(String table, int maxRecords) throws SQLException;
    
    Map<String,Object> getRecordByPK(String tableName, String keyIdentifier, Object key) throws SQLException;

    void openConnection(String driverClass, String url, String username, String pwd) throws ClassNotFoundException, SQLException;
    
    int createRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException;
    
    int deleteRecordByPK(String tableName, String keyIdentifier, Object key) throws SQLException;
    
    int updateRecordByPK(String tableName, List<String> colNames, List<Object> colValues, 
                            String keyIdentifier, Object key) throws SQLException;
}
