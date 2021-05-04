/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import  main.MainCarter;
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
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author retra
 */
public class AssignPatientCounselor extends JFrame {
    AssignPatientCounselor(String MRN) {
        super("Rectangle");
        setTitle("Meet With Staff");
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
        JLabel counsEID = new JLabel("Staff Member: ");
        JLabel notesL = new JLabel("Notes: ");
        JLabel dateL = new JLabel("Date: YYYY-MM-DD ");
        JLabel none = new JLabel("");
        JLabel ret = new JLabel("");
        //Editable texts
        JTextField eidIN = new JTextField("",15);
        JTextField noteIN = new JTextField("",15);
        JTextField dateIN = new JTextField("",15);
        //Button
        JButton processBtn = new JButton("Add Meeting");
        //Select counsEID option
        
        //Add to panel
        panel.add(patientMRN);
        panel.add(none);
        panel.add(counsEID);
        
        
        try {
            Connection conn = DriverManager.getConnection(SERVER, ID, PW);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT StaffName, EID FROM Staff WHERE EID IN (SELECT * FROM PatientCare);");
            List<List<String>> asString = convertRSToString(rs);
            String[] opts = new String[asString.size()];
            Boolean gotOpts = asString.size() > 0;
            for (int i=0;i<asString.size();i++) {
                opts[i] = (asString.get(i).get(0));
            }
            JComboBox optionBox = new JComboBox(opts);
            panel.add(optionBox);
            conn.close();
            
        
        //panel.add(eidIN);
        panel.add(notesL);
        panel.add(noteIN);
        panel.add(dateL);
        panel.add(dateIN);
        panel.add(processBtn);
        panel.add(ret);
        
        class ButtonListener implements ActionListener{
            
            public void actionPerformed(ActionEvent e){
                ret.setText("");
                if (validate(dateIN.getText()) ) {
                    //if (eidIN.getText().length() == 9) {
                        if (noteIN.getText().length() > 0) {
                            try {
                                int idx = (optionBox.getSelectedIndex());
                                String sEID = asString.get(idx).get(1);
                                System.out.println(sEID);
                                Connection conn = DriverManager.getConnection(SERVER, ID, PW);
                                Statement stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery("SELECT PatientCareEID FROM PatientCare WHERE PatientCareEID=\""+sEID+"\";");
                                if (rs.next()) {
                                    String date = dateIN.getText();
                                    if (gotOpts) {
                                        
                                        
                                        
                                        String note = noteIN.getText();
                                        rs = stmt.executeQuery("SELECT * FROM MeetsWith WHERE Date=\""+date+"\" AND StaffEID=\""+sEID+"\" AND PatientMRN = \""+MRN+"\";");
                                        if (rs.next()) {
                                            ret.setText("Meeting already exists between those people on this date.");
                                        } else {

                                            String query = "INSERT INTO MeetsWith VALUES(";
                                            query = query +addQ(date,false);
                                            query = query + addQ(note,false);
                                            query = query + addQ(sEID,false);
                                            query = query + addQ(MRN,true);
                                            query = query + ");";
                                            PreparedStatement ASSIGN = conn.prepareStatement(query);
                                            ASSIGN.execute();
                                            ret.setText("Success!");

                                        }
                                    } else {
                                        ret.setText("Error. option box not initialized.");
                                    }
                                    
                                    
                                } else {
                                    ret.setText("Staff member is not in patient care.");
                                }

                                conn.close();
                            } catch (SQLException ex) {
                                    ret.setText("Error!");
                                    Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            ret.setText("Please enter a description of the meeting.");
                        }
                    //} else {
                    //    ret.setText("Not a valid EID.");
                    //}
                    
                } else {
                    ret.setText("Not a valid date.");
                }
                
            }
        }
        processBtn.addActionListener(new ButtonListener());
        } catch (SQLException ex) {
            ret.setText("Error!");
            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
            panel.add(new JComboBox());
        }
        
        
    }
    private Boolean validate(String dateInput) {
        Boolean ret = false;
        String[] ds = dateInput.split("[-]");
        if (ds.length == 3) {
            if (ds[0].length() == 4) {
                if (ds[1].length() < 3 && ds[2].length() <3) {
                    try {
                        int year = Integer.parseInt(ds[0]);
                        int month = Integer.parseInt(ds[1]);
                        int day = Integer.parseInt(ds[2]);
                        if (month > 0 && month < 13) {
                            if (day > 0 && day < 32) {
                                ret = true;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing...");
                        ret = false;
                    }
                }
            }
        } else {
            System.out.println("Less than 3");
        }
        
        return ret;
    }
    private String addQ(String q, Boolean end) {
        String ret = "\""+q+"\"";
        if (!end) {
            ret = ret + ", ";
        }
        return ret;
    }
}
