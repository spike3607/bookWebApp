/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Spike
 */
public class MySqlDbAccessor implements DbAccessor {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public void openConnection(String driverClass, String url, String username, String pwd)
            throws ClassNotFoundException, SQLException {
        //needs validation
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, username, pwd);
    }

    @Override
    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    
//    public List<Map<String,Object>> getAllRecords(String table, int maxRecords,
//                                            List<String> colNames) throws SQLException {
//        
//    }
    
    @Override
    public List<Map<String,Object>> getAllRecords(String table, int maxRecords) throws SQLException {
        String sql = "";
        if (maxRecords >= 1) {
            sql = "SELECT * FROM " + table + " LIMIT " + maxRecords;
        }
        else {
            sql = "SELECT * FROM " + table;
        }
        
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        
        List<Map<String,Object>> results = new ArrayList<>();
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        
        while(rs.next()) {
            Map<String,Object> record = new LinkedHashMap<>();
            for(int col=1; col < colCount+1; col++) {
                record.put(rsmd.getColumnName(col), rs.getObject(col));
            }
            results.add(record);
        }
        
        return results;
    }
    
public int deleteRecordByPK(String tableName, String primaryKey, int value) throws Exception {
        //DELETE FROM author WHERE author_id = 1
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1, value);
        int recordsUpdated = stmt.executeUpdate();
        return recordsUpdated;
    }

public int createRecord(String tableName, List<String> colNames, List<Object> colValues) throws Exception {
        //INSERT INTO author (author_id, author_name, date_added) VALUES ('4', 'max maxian', '2009-12-24')

        String sql = "INSERT INTO " + tableName + "("; //first_name,last_name)" + " VALUES('Billy','Carter')";

        for (int i = 0; i < colNames.size(); i++) {
            if (i == (colNames.size() - 1)) {
                sql += colNames.get(i) + ") VALUES(";
            } else {
                sql += colNames.get(i) + ",";
            }
        }

        for (int i = 0; i < colValues.size(); i++) {
            if (i == (colValues.size() - 1)) {
                sql += " ? )";
            } else {
                sql += " ? ,";
            }
        }

        System.out.println(sql);

        PreparedStatement pstmt = conn.prepareStatement(sql);

        for (int i = 0; i < colValues.size(); i++) {
            pstmt.setObject(i + 1, colValues.get(i));
        }

        int updateCount = pstmt.executeUpdate();

        return updateCount;
    }
    
    public static void main(String[] args) throws Exception {
        
        
    }
}
