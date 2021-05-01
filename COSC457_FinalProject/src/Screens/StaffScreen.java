/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Arun
 */
public class StaffScreen extends JFrame{
    public StaffScreen(){
        //init layout
        super("Rectangle");
        setTitle("Staff Information");
        setSize(425,350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        final String ID = "root";
        final String PW = "Cosc4572!";
        final String SERVER = "jdbc:mysql://104.155.181.126:3306/mydb";
        
        //labels
        JLabel optionLabel = new JLabel("Select option:");
        JLabel staffNoLabel = new JLabel("Staff EID:");
        JLabel resultsLabel = new JLabel("Results:");
        JLabel jobLabel = new JLabel("Job Title:");
        
        //text fields
        JTextField staffNoField = new JTextField("",15);
        
        //text areas
        JTextArea resultsArea = new JTextArea(5,5);
        resultsArea.setEditable(false);
        
        //buttons
        JButton processBtn = new JButton("Perform Operation");
        
        //initialize JComboBox
        String options[] = {"Add","View","Update","Delete"};
        JComboBox optionBox = new JComboBox(options);
        String job[] = {"Counselor","SpiritualCounselor","PatientCareOther","SupportOther","Driver","Alumni","Administration","SupportStaff"};
        JComboBox jobBox = new JComboBox(job);
        
        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4,2,5,5));
        topPanel.add(optionLabel);
        topPanel.add(optionBox);
        topPanel.add(staffNoLabel);
        topPanel.add(staffNoField);
        topPanel.add(jobLabel);
        topPanel.add(jobBox);
        
        
        topPanel.add(processBtn);
        add(topPanel, BorderLayout.NORTH);
        
        //bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2,1));
        bottomPanel.add(resultsLabel);
        bottomPanel.add(resultsArea);
        add(bottomPanel, BorderLayout.CENTER);
        
        //this fires when the button is clicked
        class ButtonListener implements ActionListener{
            
            public void actionPerformed(ActionEvent e){
                //TODO: add screen behavior logic here
                try{
                    String eid = staffNoField.getText();
                    String jobType = (String)jobBox.getSelectedItem();
                    String selectedOption = (String)optionBox.getSelectedItem();
                    switch (selectedOption){
                        case "Add":
                            //logic to add here
                            try {
                                Connection conn = DriverManager.getConnection(SERVER, ID, PW);
                                Statement stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery("SELECT * FROM Staff WHERE EID = " + eid + ";");
                            if(rs.next())
                                resultsArea.setText("Staff already exists. Please use edit, view or delete");
                            else{
                                new AddStaffScreen(eid,jobType);
                            }

                            } catch (SQLException x) {

                            }
                            break;
                        case "View":
                            //logic to view here
                            break;
                        case "Update":
                            //logic to update here
                            break;
                        case "Delete":
                            //logic to delete here
                            break;
                    }
                }
                catch (Exception f){
                    JOptionPane.showMessageDialog(null, f.getMessage());
                }
            }
        }
        
        processBtn.addActionListener(new ButtonListener());
    }
}
