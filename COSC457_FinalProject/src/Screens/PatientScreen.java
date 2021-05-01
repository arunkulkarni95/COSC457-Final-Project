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
public class PatientScreen extends JFrame {

    public PatientScreen() {
        //init layout
        super("Rectangle");
        setTitle("Patient Information");
        setSize(425, 350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        final String ID = "root";
        final String PW = "Cosc4572!";
        final String SERVER = "jdbc:mysql://104.155.181.126:3306/mydb";

        //labels
        JLabel optionLabel = new JLabel("Select option:");
        JLabel mrnLabel = new JLabel("Patient MRN:");
        JLabel resultsLabel = new JLabel("Results:");

        //text fields
        JTextField mrnField = new JTextField("", 15);

        //text areas
        JTextArea resultsArea = new JTextArea(5, 5);
        resultsArea.setEditable(false);

        //buttons
        JButton processBtn = new JButton("Perform Operation");

        //initialize JComboBox
        String options[] = {"Add", "View", "Update", "Delete"};
        JComboBox optionBox = new JComboBox(options);

        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2, 5, 5));
        topPanel.add(optionLabel);
        topPanel.add(optionBox);
        topPanel.add(mrnLabel);
        topPanel.add(mrnField);
        topPanel.add(processBtn);
        add(topPanel, BorderLayout.NORTH);

        //bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));
        bottomPanel.add(resultsLabel);
        bottomPanel.add(resultsArea);
        add(bottomPanel, BorderLayout.CENTER);

        //this fires when the button is clicked
        class ButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                //TODO: add screen behavior logic here

                try {
                    String mrn = mrnField.getText();
                    String selectedOption = (String) optionBox.getSelectedItem();
                    switch (selectedOption) {
                        case "Add":
                            try {
                                Connection conn = DriverManager.getConnection(SERVER, ID, PW);
                                Statement stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery("SELECT * FROM Patients WHERE MRN = " + mrn + ";");
                            if(rs.next())
                                resultsArea.setText("Patient already exists.  please use edit, view or delete");
                            else{
                                new AddPatientScreen(mrn);
                            }

                            } catch (SQLException x) {

                            }
                            break;
                        case "View":
                            new ViewPatientScreen(mrn);
                            break;
                        case "Update":
                            new EditPatientScreen(mrn);
                            break;
                        case "Delete":
                            try {
                                Connection conn = DriverManager.getConnection(SERVER, ID, PW);

                                Statement stmt = conn.createStatement();
                                String query = "DELETE  FROM MeetsWith WHERE PatientMRN = " + mrn + ";";
                                stmt.executeUpdate(query);
                                query = "DELETE  FROM Visit WHERE PatientMRN = " + mrn + ";";
                                stmt.executeUpdate(query);
                                query = "DELETE  FROM Guest WHERE PatientMRN = " + mrn + ";";
                                stmt.executeUpdate(query);
                                query = "DELETE  FROM EnrolledIn WHERE MRN = " + mrn + ";";
                                stmt.executeUpdate(query);
                                query = "DELETE  FROM Patients WHERE MRN = " + mrn + ";";
                                stmt.executeUpdate(query);
                                resultsArea.setText("Success");
                                
                             } catch (SQLException x) {
                                 resultsArea.setText("" + x);

                        }
                        break;
                    }
                } catch (Exception f) {
                    JOptionPane.showMessageDialog(null, f.getMessage());
                }
            }
        }

        processBtn.addActionListener(new ButtonListener());
    }
}
