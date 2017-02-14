/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    //    public List<Map<String,Object>> getAllRecords(String table, int maxRecords,
    //                                            List<String> colNames) throws SQLException {
    //
    //    }
    List<Map<String, Object>> getAllRecords(String table, int maxRecords) throws SQLException;

    void openConnection(String driverClass, String url, String username, String pwd) throws ClassNotFoundException, SQLException;
    
}
