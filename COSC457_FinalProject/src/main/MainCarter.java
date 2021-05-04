/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author retra
 */
public class MainCarter {
    public static List<List<String>> showTable(Connection con, String table) {
        Statement stmt;
        List<List<String>> retString = new ArrayList<List<String>>();
        try {
            stmt = con.createStatement();
        
            stmt.executeQuery("use "+"mydb");
            ResultSet rs = stmt.executeQuery("Select * from "+table+";");
            retString = convertRSToString(rs);
            System.out.println(retString);
        } catch (SQLException ex) {
            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retString;
    }
    
    public static boolean insert(Connection con,String table,List<String> L) {
        boolean success = false;
        try {
            Statement switch_tables = con.createStatement();
        
            switch_tables.executeQuery("use "+"mydb");
            String stmt = "INSERT INTO table VALUES (";
            String args = "";
            for (int i=0;i<L.size()-1;i++) {
                
                args = args + "\'"+L.get(i)+"\'" + ", ";
            }
            args = args + "\'"+L.get(L.size()-1)+"\'";
            stmt = stmt + args + ");";
            PreparedStatement ins = con.prepareStatement(stmt);
            ins.execute();
            success = true;
        } catch (SQLException ex) {
            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }
        return success;
    }
    
    public static boolean update(Connection con, String table, List<String> newElem) {
        boolean success = false;
        try {
            Statement switch_tables = con.createStatement();
        
            switch_tables.executeQuery("use "+"mydb");
            ResultSet rs = switch_tables.executeQuery("SELECT * FROM "+table+" WHERE 0=1");
            String updateStr = "";
            String primaryKey = "WHERE ";
            
            ResultSet primaryKeys = (con.getMetaData().getPrimaryKeys(null,null,table));
            List<String> pKs = new ArrayList<String>();
            //Get primary keys
            while(primaryKeys.next()) {
                String pkColumnName = primaryKeys.getString("COLUMN_NAME");
                //Integer pkPosition = primaryKeys.getInt("KEY_SEQ");
                //System.out.println(""+pkColumnName+" is the "+pkPosition+". column of the primary key of the table "+table);
                //pkColumnSet.add(pkColumnName);
                pKs.add(pkColumnName);
            }
            int pKsFound = 0;
            java.sql.ResultSetMetaData mD = rs.getMetaData();
            for (int i=1;i<mD.getColumnCount()+1;i++) {
                updateStr = updateStr + mD.getColumnName(i)+"="+"\'"+newElem.get(i-1)+"\'";
                if (i!=mD.getColumnCount()) {
                    updateStr = updateStr + ", ";
                }
                for (int j=0;j<pKs.size();j++) {
                    if (mD.getColumnName(i).equalsIgnoreCase(pKs.get(j))){
                        if (pKsFound == 0) {
                        primaryKey = primaryKey + pKs.get(j)+"=\'"+newElem.get(i-1)+"\'";
                        } else {
                            primaryKey = primaryKey + " AND "+pKs.get(j)+"=\'"+newElem.get(i-1)+"\'";
                        }
                        pKsFound += 1;
                    }
                }
                //System.out.println(mD.getColumnType(i));
            }
            //System.out.println(updateStr);
            //System.out.println(primaryKey);
            String query = "UPDATE "+table+" \nSET "+updateStr+"\n"+primaryKey+";";
            //System.out.println(query);
//String stmt = "";
            //PreparedStatement upd = con.prepareStatement(stmt);
            //upd.execute();
            PreparedStatement upd = con.prepareStatement(query);
            upd.execute();
            success = true;
        }
        catch (SQLException ex) {
            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }
        return success;
    }
    public static boolean delete(Connection con,String table, List<String> pKeys) {
        boolean success = false;
        try {
            Statement switch_tables = con.createStatement();
        
            switch_tables.executeQuery("use "+"mydb");
            String query = "DELETE FROM "+table +" WHERE ";
            String pKeysPart = "";
            ResultSet primaryKeys = (con.getMetaData().getPrimaryKeys(null,null,table));
            List<String> pKs = new ArrayList<String>();
            //Get primary keys
            while(primaryKeys.next()) {
                String pkColumnName = primaryKeys.getString("COLUMN_NAME");
                pKs.add(pkColumnName);
            }
            int pKsFound = 0;
            ResultSet rs = switch_tables.executeQuery("SELECT * FROM "+table);
            java.sql.ResultSetMetaData mD = rs.getMetaData();
            for (int i=1;i<mD.getColumnCount()+1;i++) {
                for (int j=0;j<pKs.size();j++) {
                    if (mD.getColumnName(i).equalsIgnoreCase(pKs.get(j))){
                        if (pKsFound == 0) {
                            pKeysPart = pKeysPart + pKs.get(j)+"=\'"+pKeys.get(pKsFound)+"\'";
                            success = true;

                        } else {
                            if (pKeys.size() < j) {
                                pKeysPart = pKeysPart + " AND "+pKs.get(j)+"=\'"+pKeys.get(pKsFound)+"\'";
                                success = true;
                            } else {
                                System.out.println("Table has more primary keys than provided.");
                                success = false;
                            }
                        }
                        pKsFound += 1;
                    }
                }
                
            }
            if (success) {
                query = query + pKeysPart + ";";
                //System.out.println(query);
                PreparedStatement del = con.prepareStatement(query);
                del.execute();
                success = true;
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }
        return success;
    }
    
    public static List<List<String>> convertRSToString(ResultSet rs) {
        List<List<String>> retString = new ArrayList<List<String>>();

        try {
            java.sql.ResultSetMetaData rMD = rs.getMetaData();
            final int columnCount = rMD.getColumnCount();
            System.out.println(columnCount);
            
            while(rs.next()) {
                List<String> values = new ArrayList<String>();
                for (int i=1;i<columnCount+1;i++) {
                    values.add(""+rs.getObject(i));
                }
                retString.add(values);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retString;
    }
}
