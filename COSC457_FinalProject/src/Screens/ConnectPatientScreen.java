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
 * @author Carter
 */
public class ConnectPatientScreen extends JFrame{
    public ConnectPatientScreen() {
        super("Rectangle");
        setTitle("Connect Patients");
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
        JLabel selectOpt = new JLabel("Select Option:");
        JLabel patientMRN = new JLabel("Patient MRN:");
        JLabel res = new JLabel("");

        JTextField pMRNField = new JTextField("",15);
        String options[] = {"Assign Bed","Assign Visit","Meet with Counselor"};
        JComboBox optionBox = new JComboBox(options);
        JPanel topPanel = new JPanel();
        JButton processBtn = new JButton("Perform Operation");
        
        topPanel.setLayout(new GridLayout(3,2));
        topPanel.add(selectOpt);
        topPanel.add(optionBox);
        topPanel.add(patientMRN);
        topPanel.add(pMRNField);
        topPanel.add(processBtn);
        topPanel.add(res);
        //Three buttons:
        //First must enter patient ID
        //Assign Visit
        //Assign Bed
        //Meet with counsolor
        add(topPanel, BorderLayout.NORTH);
        class ButtonListener implements ActionListener{
            
            public void actionPerformed(ActionEvent e){
                //TODO: add screen behavior logic here
                try{   
                    res.setText("");
                    String MRN = pMRNField.getText();
                    if (MRN.length() == 0) {
                        res.setText("Please enter a patient MRN.");
                    } else {
                        Connection conn = DriverManager.getConnection(SERVER, ID, PW);
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT Gender FROM Patients WHERE MRN = " + MRN + ";");
                        if (rs.next()) {
                            String gender = rs.getString("Gender");
                            String chosen = (String)optionBox.getSelectedItem();
                            switch (chosen) {
                                case "Assign Bed":
                                    System.out.println("Assigned bed");
                                    new AssignBed(MRN,gender);
                                    break;
                                case "Assign Visit":
                                    System.out.println("Assigned visit");
                                    break;
                                case "Meet with Counselor":
                                    System.out.println("Assigned counselor");
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            res.setText("Not a valid patient MRN.");
                            
                        }
                        conn.close();
                        /*
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
                                conn.close();

                                } catch (SQLException x) {

                                }
                                break;
                            case "View":
                                //logic to view here
                                new ViewStaffScreen(eid);
                                break;
                            case "Update":
                                //logic to update here
                                new EditStaffScreen(eid);
                                break;
                            case "Delete":
                                //logic to delete here
                                new DeleteStaffScreen(eid);
                                break;
                        }
                        */
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
