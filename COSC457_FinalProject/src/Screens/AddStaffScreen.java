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
 * @author nilso
 */
public class AddStaffScreen extends JFrame {

    public AddStaffScreen(String eid, String jobType) {
        super("Rectangle");
        setTitle("Add Staff");
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

        JLabel eidLabel = new JLabel("Staff EID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel dobLabel = new JLabel("DOB:");
        JLabel salaryLabel = new JLabel("Salary:");
        JLabel locLabel = new JLabel("Location:");
        JLabel bNumLabel = new JLabel("Building Number:");
        JLabel jobTypeLabel = new JLabel("Job Type:");

        JButton processBtn = new JButton("Add Staff");

        JTextField eidField = new JTextField("", 40);
        JTextField nameField = new JTextField("", 40);
        JTextField phoneField = new JTextField("", 40);
        JTextField addressField = new JTextField("", 40);
        JTextField dobField = new JTextField("", 40);
        JTextField salaryField = new JTextField("", 40);
        JTextField jobTypeField = new JTextField("", 40);
        
        JTextField faithField = new JTextField("", 40);
        
        String bNums[] = {"1","2","3","4","5","6"};
        JComboBox bNumBox = new JComboBox(bNums);
        
        String locs[] = {"Off Campus","Main Campus"};
        JComboBox locBox = new JComboBox(locs);

        panel.add(eidLabel);
        panel.add(eidField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(addressLabel);
        panel.add(addressField);
        
        panel.add(dobLabel);
        panel.add(dobField);
        panel.add(salaryLabel);
        panel.add(salaryField);
        panel.add(locLabel);
        panel.add(locBox);
        
        panel.add(bNumLabel);
        panel.add(bNumBox);
        panel.add(jobTypeLabel);
        panel.add(jobTypeField);

        panel.add(processBtn);
        eidField.setText(eid);
        jobTypeField.setText(jobType);
        eidField.setEditable(false);
        jobTypeField.setEditable(false);
        
        try {
            Connection conn = DriverManager.getConnection(SERVER, ID, PW);

            Statement stmt = conn.createStatement();

            //this fires when the button is clicked
            class ButtonListener implements ActionListener {

                public void actionPerformed(ActionEvent e) {

                    String name = nameField.getText();
                    if (name.equals("")) {
                        name = null;
                    }
                    String phone = phoneField.getText();
                    if (phone.equals("")) {
                        phone = null;
                    }
                    String address = addressField.getText();
                    if (address.equals("")) {
                        address = null;
                    }
                    String dob = dobField.getText();
                    if (dob.equals("")) {
                        dob = null;
                    }
                    String salary = salaryField.getText();
                    if (salary.equals("")) {
                        salary = null;
                    }
                    
                    String location = (String)locBox.getSelectedItem();
                    
                    String pid = "";
                    String query2 = "";
                    String query3 = "";
                    switch(jobType){
                        case "PatientCare":
                            pid = "1";
                            query2 = "INSERT INTO PatientCare (PatientCareEID) VALUES ("+eid+");";
                            break;
                        case "Administration":
                            pid = "4";
                            query2 = "INSERT INTO Administration (AdminEID) VALUES ("+eid+");";
                            break;
                        case "SpiritualCounselor":
                            pid = "2";
                            JOptionPane.showMessageDialog(null,faithField,"What faith?:",JOptionPane.QUESTION_MESSAGE);
                            String faith = faithField.getText();
                            query2 = "INSERT INTO PatientCare (PatientCareEID) VALUES("+eid+");";
                            query3 = "INSERT INTO SpiritualCounselor (SpiritualCounselorEID, PracticingFaith) VALUES ("+eid+",'"+faith+"');";
                            break;
                        case "PatientCareOther":
                            pid = "3";
                            
                            query2 = "INSERT INTO PatientCare (PatientCareEID) VALUES("+eid+");";
                            query3 = "INSERT INTO PatientCareOther (PatientCareOtherEID) VALUES("+eid+");";
                            break;
                        case "Counselor":
                            pid = "1";
                            JTextField licField = new JTextField("",40);
                            JOptionPane.showMessageDialog(null,licField,"Enter License:",JOptionPane.QUESTION_MESSAGE);
                            String license = licField.getText();
                            
                            query2 = "INSERT INTO PatientCare (PatientCareEID) VALUES("+eid+");";
                            query3 = "INSERT INTO Counselor (CounselorEID, Liscense) VALUES ("+eid+", '"+license+"');";
                            break;
                        case "SupportOther":
                            pid = "5";
                            
                            query2 = "INSERT INTO SupportStaff (SupportStaffID) VALUES("+eid+");";
                            query3 = "INSERT INTO SupportStaffOther (SupportOtherEID) VALUES("+eid+");";
                            break;
                        case "SupportStaff":
                            pid = "8";
                            
                            query2 = "INSERT INTO SupportStaff (SupportStaffID) VALUES("+eid+");";
                            break;
                        case "Driver":
                            pid = "6";
                            JTextField dLicField = new JTextField("",15);
                            JOptionPane.showMessageDialog(null,dLicField,"Enter License:",JOptionPane.QUESTION_MESSAGE);
                            String dlicense = dLicField.getText();
                            
                            JTextField routeField = new JTextField("",40);
                            JOptionPane.showMessageDialog(null,routeField,"Enter Route:",JOptionPane.QUESTION_MESSAGE);
                            String route = routeField.getText();
                            
                            JTextField vehField = new JTextField("",40);
                            JOptionPane.showMessageDialog(null,vehField,"Enter Route:",JOptionPane.QUESTION_MESSAGE);
                            String vehicle = vehField.getText();
                            
                            query2 = "INSERT INTO SupportStaff (SupportStaffID) VALUES("+eid+");";
                            query3 = "INSERT INTO Driver (DriverEID, DriversLiscence, Route, Vehicle) VALUES ("+eid+",'"+dlicense+"','"+route+"','"+vehicle+"');";
                            break;
                        case "Alumni":
                            pid = "7";
                            
                            query2 = "INSERT INTO Alumni (AlumniEID) VALUES("+eid+");";
                            break;
                    }
                    
                    String bNum = (String)bNumBox.getSelectedItem();
                    
                    String query1 = "INSERT INTO Staff (EID, StaffName, PhoneNumber, Address, DOB, Salary, Location, PositionID, StaffBuildingNumber, StaffType) VALUES (" 
                                    + eid + ", '" + name + "', '" + phone + "', '" + address + "', '" + dob + "', " + salary + ", '" + location + "', " + pid + ", " + bNum + ",'" + jobType + "');";
                    
                    
                    try {
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Staff WHERE EID = " + eid+";");
                        if (rs.next()) {
                            eidField.setText("Staff has already been entered");
                        } else {

                            stmt.executeUpdate(query1);
                            stmt.executeUpdate(query2);
                            if (!query3.equals("")){
                                stmt.executeUpdate(query3);
                            }
                            eidField.setText("Success.");
                        }

                    } catch (SQLException x) {
                        eidField.setText("" + x);

                    }

                }

            }
            processBtn.addActionListener(new ButtonListener());
        } catch (Exception f) {
            JOptionPane.showMessageDialog(null, f.getMessage());

        }
    }
}

