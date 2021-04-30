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
public class CustomQueryScreen extends JFrame{
    public CustomQueryScreen(){
        
        //init layout
        super("Rectangle");
        setTitle("Premade Custom Queries");
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        final String ID = "root";
        final String PW = "Cosc4572!";
        final String SERVER = "jdbc:mysql://104.155.181.126:3306/mydb";
        
        //labels
        JLabel querySelect = new JLabel("Select a premade custom query:");
        JLabel resultsLabel = new JLabel("Results:");
        
        //comboBox
        String[] queries = {"Query 1","Query 2","Query 3","Query 4","Query 5","Query 6","Query 7","Query 8","Query 9","Query 10"};
        JComboBox queryBox = new JComboBox(queries);
        
        //text area
        JTextArea resultsArea = new JTextArea(5,5);
        resultsArea.setEditable(false);
        
        //buttons
        JButton processBtn = new JButton("Perform Operation");
        
        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2));
        topPanel.add(querySelect);
        topPanel.add(queryBox);
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
                    String allResults = "";
                    Connection conn = DriverManager.getConnection(SERVER, ID, PW);
                    Statement stmt = conn.createStatement();
                    String selectedOption = (String)queryBox.getSelectedItem();
                    switch (selectedOption){
                        case "Query 1":
                            //perform query
                            ResultSet rs1 = stmt.executeQuery("SELECT visitNumber FROM Visit v WHERE EXISTS"
                                                            + "(SELECT * FROM MeetsWith m WHERE StaffEID = "
                                                            + "(SELECT EID FROM Staff WHERE StaffName = \"Packet Margin\") AND v.PatientMRN = m.PatientMRN); ");
                            while (rs1.next()){
                                String visitNumber = rs1.getString("visitNumber");
                                allResults += "Visit Number: " + visitNumber + "\n";
                            }
                            resultsArea.setText("Visit Number of each patient assigned to Packet Margin:\n\n" + allResults);
                            break;
                        case "Query 2":
                            //perform query
                            ResultSet rs2 = stmt.executeQuery("SELECT StaffName, PositionID FROM Staff WHERE"
                                                            + "(StaffBuildingNumber = 1) ORDER BY PositionID");
                            while (rs2.next()){
                                String sName = rs2.getString("StaffName");
                                String pID = rs2.getString("PositionID");
                                allResults += "Staff Name: " + sName + " Position ID: " + pID + "\n";
                            }
                            resultsArea.setText("Name and Position ID of all staff, by dept. who work in Building #1\n\n" + allResults);
                            break;
                        case "Query 3":
                            //perform query
                            ResultSet rs3 = stmt.executeQuery("SELECT PatientName, MRN FROM Patients;");
                            while (rs3.next()){
                                String pName = rs3.getString("PatientName");
                                String mrn = rs3.getString("MRN");
                                String result = "Name: " + pName + " MRN: " + mrn;
                                allResults += result + "\n";
                            }
                            resultsArea.setText("All current patients:\n\n"+allResults);
                            break;
                        case "Query 4":
                            //perform query
                            ResultSet rs4 = stmt.executeQuery("SELECT PatientName, MRN FROM Patients p WHERE EXISTS"
                                                            + "(SELECT * FROM EnrolledIn e WHERE ProgramNumber="
                                                            + "(SELECT ProgramNumber FROM Programs WHERE Description = \"Emerging Adults\") AND e.MRN = p.MRN);");
                            while (rs4.next()){
                                String pName = rs4.getString("PatientName");
                                String mrn = rs4.getString("MRN");
                                allResults += "Patient Name: " + pName + " MRN: " + mrn + "\n";
                            }
                            resultsArea.setText("Patient Name and MRN of all patients enrolled in the Emerging Adult Program:\n\n" + allResults);
                            break;
                        case "Query 5":
                            //perform query
                            ResultSet rs5 = stmt.executeQuery("SELECT PatientName, MRN FROM Patients p WHERE Insurance=\"Blue Cross Blue Shield\" AND EXISTS"
                                                            + "(SELECT * FROM Visit v WHERE COVIDStatus = \"F\" AND p.MRN = v.PatientMRN);");
                            while (rs5.next()){
                                String pName = rs5.getString("PatientName");
                                String mrn = rs5.getString("MRN");
                                allResults += "Patient Name: " + pName + " MRN: " + mrn + "\n";
                            }
                            resultsArea.setText("Patient Name and MRN of all COVID-Negative patients with BCBS Insurance:\n\n" + allResults);
                            break;
                        case "Query 6":
                            //perform query
                            ResultSet rs6 = stmt.executeQuery("SELECT GuestName, PatientName FROM Guest g, Patients p, Bed b WHERE p.MRN=g.PatientMRN AND b.BedMRN = p.MRN AND b.BuildingNumber=3 ORDER BY p.PatientName;");
                            while (rs6.next()){
                                String pName = rs6.getString("PatientName");
                                String gName = rs6.getString("GuestName");
                                allResults += "Patient Name: " + pName + " Guest Name: " + gName + "\n";
                            }
                            resultsArea.setText("Patient and Guest Names of all patients living in Building #3:\n\n" + allResults);
                            break;
                        case "Query 7":
                            //perform query
                            ResultSet rs7 = stmt.executeQuery("SELECT DISTINCT StaffName FROM Staff s, MeetsWith m, Patients p WHERE s.EID = m.StaffEID AND p.MRN = m.PatientMRN AND s.StaffType = \"Counselor\" "
                                                            + "AND EXISTS (SELECT pcount FROM (SELECT COUNT(PatientMRN) as pcount FROM Staff s, MeetsWith m, Patients p WHERE s.EID = m.StaffEID AND p.MRN = m.PatientMRN) counts "
                                                            + "WHERE pcount < 10) GROUP BY StaffName;");
                            while (rs7.next()){
                                String sName = rs7.getString("StaffName");
                                allResults += "Staff Name: " + sName + "\n";
                            }
                            resultsArea.setText("Counselors who meet with fewer than 10 patients:\n\n" + allResults);
                            break;
                        case "Query 8":
                            //perform query
                            ResultSet rs8 = stmt.executeQuery("SELECT Notes FROM FollowUp f, Patients p WHERE p.MRN IN (SELECT FollowUpMRN FROM FollowUp WHERE f.FollowUpMRN = p.MRN);");
                            while (rs8.next()){
                                String notes = rs8.getString("Notes");
                                allResults += "Notes: " + notes + "\n";
                            }
                            resultsArea.setText("Notes of patients who have followed-up with Alumni Department:\n\n" + allResults);
                            break;
                        case "Query 9":
                            //perform query
                            ResultSet rs9 = stmt.executeQuery("SELECT AVG(SALARY) as AvgSalary FROM Staff, Driver WHERE EID = DriverEID;");
                            while (rs9.next()){
                                String avgSalary = rs9.getString("AvgSalary");
                                allResults += "Average Salary: $" + avgSalary + "\n";
                            }
                            resultsArea.setText("Average salary of all drivers:\n\n" + allResults);
                            break;
                        case "Query 10":
                            //perform query
                            ResultSet rs10 = stmt.executeQuery("SELECT BuildingNumber, BuildingType FROM Building WHERE MaxNumberOfBeds > 15");
                            while (rs10.next()){
                                String bNum = rs10.getString("BuildingNumber");
                                String bDesc = rs10.getString("BuildingType");
                                allResults += "Building Number: " + bNum + " Building Description: " + bDesc + "\n";
                            }
                            resultsArea.setText("All buildings with greater than 15 beds:\n\n" + allResults);
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