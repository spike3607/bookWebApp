package edu.wctc.mss.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author Spike
 */
public class MySqlDbAccessor implements DbAccessor {

    private Connection conn;

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

    @Override
    public List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException {
        String sql = "";
        if (maxRecords >= 1) {
            sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        } else {
            sql = "SELECT * FROM " + tableName;
        }

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        List<Map<String, Object>> results = new ArrayList<>();

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        while (rs.next()) {
            Map<String, Object> record = new LinkedHashMap<>();
            for (int col = 1; col < colCount + 1; col++) {
                record.put(rsmd.getColumnName(col), rs.getObject(col));
            }
            results.add(record);
        }

        return results;
    }
    
    @Override
    public Map<String,Object> getRecordByPK(String tableName, String keyIdentifier, Object key) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + keyIdentifier + "= ?";
        System.out.println(sql);

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, key);
        ResultSet rs = pstmt.executeQuery();
        
        Map<String,Object> result = new LinkedHashMap<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        
        rs.next();
        for (int col = 1; col < colCount + 1; col++) {
                result.put(rsmd.getColumnName(col), rs.getObject(col));
            }
        
        return result;
    }

    @Override
    public int deleteRecordByPK(String tableName, String keyIdentifier, Object key) throws SQLException {
        //DELETE FROM author WHERE author_id = 1
        String sql = "DELETE FROM " + tableName + " WHERE " + keyIdentifier + " = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, key);
        int recordsUpdated = pstmt.executeUpdate();
        return recordsUpdated;
    }

    @Override
    public int createRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException {
        //INSERT INTO author (author_id, author_name, date_added) VALUES ('4', 'max maxian', '2009-12-24')

        //Using StringJoiner
        StringJoiner sj = new StringJoiner(",", "(", ")");
        String sql = "INSERT INTO " + tableName + " ";
        for (int i = 0; i < colNames.size(); i++) {
            sj.add(colNames.get(i));
        }
        sql += sj.toString() + " VALUES ";
        sj = new StringJoiner(",", "(", ")");
        for (int i = 0; i < colValues.size(); i++) {
            sj.add("?");
        }
        sql += sj.toString();

        //Old Method
//        String sql = "INSERT INTO " + tableName + "(";
//
//        for (int i = 0; i < colNames.size(); i++) {
//            if (i == (colNames.size() - 1)) {
//                sql += colNames.get(i) + ") VALUES(";
//            } else {
//                sql += colNames.get(i) + ",";
//            }
//        }
//
//        for (int i = 0; i < colValues.size(); i++) {
//            if (i == (colValues.size() - 1)) {
//                sql += " ? )";
//            } else {
//                sql += " ? ,";
//            }
//        }
        System.out.println(sql);

        PreparedStatement pstmt = conn.prepareStatement(sql);

        for (int i = 0; i < colValues.size(); i++) {
            pstmt.setObject(i + 1, colValues.get(i));
        }

        int updateCount = pstmt.executeUpdate();

        return updateCount;
    }

    @Override
    public int updateRecordByPK(String tableName, List<String> colNames, List<Object> colValues,
            String keyIdentifier, Object key) throws SQLException {
        //UPDATE author SET author_id='1', author_name='Mike Schoenauer', date_added='2009-12-24 WHERE author_id=1
        String sql = "UPDATE " + tableName + " SET ";
        StringJoiner sj = new StringJoiner(", ");

        for (int i = 0; i < colNames.size(); i++) {
            String s = colNames.get(i);
            s += "=?";
            sj.add(s);
        }
        sql += sj.toString();
        sql += " WHERE " + keyIdentifier + "=" + key.toString();

        System.out.println(sql);

        PreparedStatement pstmt = conn.prepareStatement(sql);

        for (int i = 0; i < colValues.size(); i++) {
            pstmt.setObject(i + 1, colValues.get(i));
        }

        int updateCount = pstmt.executeUpdate();

        return updateCount;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        DbAccessor db = new MySqlDbAccessor();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");

//        ArrayList<String> newRecordColumns = new ArrayList<>();
//        newRecordColumns.add("author_id");
//        newRecordColumns.add("author_name");
//        newRecordColumns.add("date_added");
//
//        ArrayList<Object> newRecordValues = new ArrayList<>();
//        newRecordValues.add(13);
//        newRecordValues.add("Test Complete Schoenauer");
//        newRecordValues.add(new Date(15, 4, 19));
//
//        int recordsUpdated = db.updateRecordByPK("author", newRecordColumns, newRecordValues, "author_id", 13);
//        System.out.println(recordsUpdated);
//        db.createRecord("author", newRecordColumns, newRecordValues);
//        int recordsUpdated = db.deleteRecordByPK("author", "author_id", 11);
//        System.out.println(recordsUpdated);

        Map<String, Object> record = db.getRecordByPK("author", "author_id", 6);
        System.out.println(record);

        List<Map<String, Object>> records = db.getAllRecords("author", 500);
        for (Map<String, Object> r : records) {
            System.out.println(r);
        }

        db.closeConnection();
    }
}
