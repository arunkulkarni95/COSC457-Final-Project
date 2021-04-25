/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;
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
            for (var i=0;i<L.size()-1;i++) {
                
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
