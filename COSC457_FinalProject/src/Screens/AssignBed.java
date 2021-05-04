/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import main.MainCarter;
import static main.MainCarter.convertRSToString;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Carter
 */
public class AssignBed extends JFrame{
    public AssignBed(String MRN, String Gender) {
        super("Rectangle");
        setTitle("Assign Bed");
        setSize(425, 350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));
        
        
        add(panel, BorderLayout.NORTH);
        final String ID = "root";
        final String PW = "Cosc4572!";
        final String SERVER = "jdbc:mysql://104.155.181.126:3306/mydb";
        //Labels
        JLabel patientMRN = new JLabel("Patient MRN: "+MRN);
        JLabel selectOpt = new JLabel("Select Bed:");
        JLabel none = new JLabel("");
        panel.add(patientMRN);
        panel.add(none);
        panel.add(selectOpt);
        try {
            Connection conn = DriverManager.getConnection(SERVER, ID, PW);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT BedNumber,BuildingNumber FROM Bed WHERE BedMRN IS NULL AND (Gender = \""+Gender+"\" OR Gender = \"Any\") AND (NOT BuildingNumber IS NULL);");
            List<List<String>> asString = convertRSToString(rs);
            String[] opts = new String[asString.size()];
            for (int i=0;i<asString.size();i++) {
                opts[i] = bedToString(asString.get(i));
            }
            
            JComboBox optionBox = new JComboBox(opts);
            panel.add(optionBox);
            JLabel res = new JLabel("");
            JButton processBtn = new JButton("Assign Bed");
            panel.add(processBtn);
            panel.add(res);
            
            class ButtonListener implements ActionListener{
            
                public void actionPerformed(ActionEvent e){
                    //TODO: add screen behavior logic here
                    res.setText("");
                    try{   
                        Connection con = DriverManager.getConnection(SERVER, ID, PW);

                        int opt = optionBox.getSelectedIndex();
                        //System.out.println(opt);
                        String query = "UPDATE Bed SET BedMRN = NULL WHERE BedMRN=\""+MRN+"\";";
                        PreparedStatement ASSIGN = con.prepareStatement(query);
                        ASSIGN.execute();
                        query = "UPDATE Bed SET BedMRN=\""+MRN+"\""+" WHERE BedNumber =\""+asString.get(opt).get(0)+"\"";
                        ASSIGN = con.prepareStatement(query);
                        ASSIGN.execute();
                        con.close();
                        res.setText("Success");
                        
                    } catch (Exception f){
                        JOptionPane.showMessageDialog(null, f.getMessage());
                        
                    }
                    
                    
                }
            }
            processBtn.addActionListener(new ButtonListener());

            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    private String bedToString(List<String> l) {
        
        return "Bed "+l.get(0) + " in building: "+l.get(1);
    }
}
