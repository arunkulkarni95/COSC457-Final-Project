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
public class ProgramScreen extends JFrame{
    public ProgramScreen(){
        //init layout
        super("Rectangle");
        setTitle("Program Information");
        setSize(425,350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        //sql connection stuff
        final String ID = "root";
        final String PW = "Cosc4572!";
        final String SERVER = "jdbc:mysql://104.155.181.126:3306/mydb";
        
        //labels
        //JLabel programNumLabel = new JLabel("Program Number:");
        JLabel resultsLabel = new JLabel("Results:");
        JLabel optionLabel = new JLabel("Select Option:");
        
        //text fields
        //JTextField programNumField = new JTextField("",15);
        
        //text areas
        JTextArea resultsArea = new JTextArea(5,5);
        resultsArea.setEditable(false);
        
        //buttons
        JButton processBtn = new JButton("Perform Operation");
        
        //initialize JComboBox
        String options[] = {"View Patients in Program","Add Patient to Program"};
        String programs[] = {"Emerging Adults","Primary Program","Relapse Program","Pain Recovery Program"};
        JComboBox optionBox = new JComboBox(options);
        JComboBox programBox = new JComboBox(programs);
        
        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3,2,5,5));
        //topPanel.add(programNumLabel);
        //topPanel.add(programNumField);
        topPanel.add(optionLabel);
        topPanel.add(optionBox);
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
                    Connection con = DriverManager.getConnection(SERVER, ID, PW);
                    Statement stmt = con.createStatement();
                    
                    String selectedOption = (String)optionBox.getSelectedItem();
                    switch (selectedOption){
                        case "Add Patient to Program":
                            //logic to add here
                            //maybe use a popup window for both of these (?)
                            new AddPatientToProgramScreen();
                            break;
                        case "View Patients in Program":
                            //logic to view here
                            JOptionPane.showMessageDialog(null,programBox,"Choose a program:",JOptionPane.QUESTION_MESSAGE);
                            String program = (String)programBox.getSelectedItem();
                            
                            ResultSet rs2 = stmt.executeQuery("SELECT PatientName, MRN FROM Patients p WHERE EXISTS"
                                                            + "(SELECT * FROM EnrolledIn e WHERE ProgramNumber="
                                                            + "(SELECT ProgramNumber FROM Programs WHERE Description = \""+program+"\") AND e.MRN = p.MRN);");
                            while (rs2.next()){
                                String pName = rs2.getString("PatientName");
                                String mrn = rs2.getString("MRN");
                                allResults += "Patient Name: " + pName + " MRN: " + mrn + "\n";
                            }
                            resultsArea.setText(allResults);
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
