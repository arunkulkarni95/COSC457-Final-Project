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
public class AddPatientToProgramScreen extends JFrame {

    public AddPatientToProgramScreen() {
        super("Rectangle");
        setTitle("Add Patient to Program");
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

        JLabel mrnLabel = new JLabel("Patient MRN:");
        JLabel programLabel = new JLabel("Program Name");
        JLabel primaryLabel = new JLabel("Primary?");
        JLabel resultLabel = new JLabel("Result:");
        
        JButton processBtn = new JButton("Add to Program");

        JTextField mrnField = new JTextField("", 40);
        JTextField resultField = new JTextField("", 40);

        String programs[] = {"Emerging Adults","Primary Program","Relapse Program","Pain Recovery Program"};
        JComboBox programBox = new JComboBox(programs);
        
        String primaryOptions[] = {"Y","N"};
        JComboBox primaryOptionBox = new JComboBox(primaryOptions);
        
        panel.add(mrnLabel);
        panel.add(mrnField);
        panel.add(programLabel);
        panel.add(programBox);
        panel.add(primaryLabel);
        panel.add(primaryOptionBox);
        panel.add(resultLabel);
        panel.add(resultField);

        panel.add(processBtn);
        try {
            Connection conn = DriverManager.getConnection(SERVER, ID, PW);

            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            //this fires when the button is clicked
            class ButtonListener implements ActionListener {

                public void actionPerformed(ActionEvent e) {

                    String mrn = mrnField.getText();
                    String program = (String)programBox.getSelectedItem();
                    String primaryOption = (String)primaryOptionBox.getSelectedItem();
                    
                    try {
                        ResultSet getProgramName = stmt.executeQuery("SELECT ProgramNumber FROM Programs WHERE Description=\"" + program +"\";");
                        getProgramName.next();
                        String progNum = getProgramName.getString("ProgramNumber");
                        
                        String query = "INSERT INTO EnrolledIn (PrimaryProgram, MRN, ProgramNumber) VALUES (\""+primaryOption+"\",\""+mrn+"\","+progNum+");";
                        String tryQuery = "SELECT PrimaryProgram, MRN, ProgramNumber FROM EnrolledIn WHERE PrimaryProgram=\""+primaryOption+"\"AND MRN=\""+mrn+"\"AND ProgramNumber="+progNum+";";
                        String tryGetPatientQuery = "SELECT MRN FROM Patients WHERE MRN="+mrn+";";
                        
                        ResultSet tryRs = stmt.executeQuery(tryQuery);
                        ResultSet tryGetPatientRs = stmt2.executeQuery(tryGetPatientQuery);
                        if (tryRs.next()) {
                            resultField.setText("Patient already in program.");
                        }
                        else if (!tryGetPatientRs.next()){
                            resultField.setText("Patient does not exist.");
                        }
                        else {
                            stmt.executeUpdate(query);
                            resultField.setText("Success");
                        }

                    } catch (SQLException x) {
                        resultField.setText("" + x);

                    }

                }

            }
            processBtn.addActionListener(new ButtonListener());
        } catch (Exception f) {
            JOptionPane.showMessageDialog(null, f.getMessage());

        }
    }
}

