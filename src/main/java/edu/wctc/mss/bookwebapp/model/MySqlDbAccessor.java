/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mss.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
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
    
//    public int deleteById(String table, String idColName, Object id) {
//        String sql1 = "DELETE FROM " + table + " WHERE " + idColName + " = ";
//        String sql2 = "";
//        
//        if(id instanceof String) {
//            sql2 = "'" + id.toString() + "'";
//        }
//        else {
//            sql2 = id.toString();
//        }
//        
//        String sql = sql1 + sql2;
//    }
    
    public static void main(String[] args) throws Exception {
        MySqlDbAccessor db = new MySqlDbAccessor();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book",
                "root", "admin");
        
        List<Map<String,Object>> records = db.getAllRecords("author", 50);
        
        db.closeConnection();
        for (Map<String,Object> rec : records) {
            System.out.println(rec);
        }
        
    }
}
