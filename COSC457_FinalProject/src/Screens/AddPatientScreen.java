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
public class AddPatientScreen extends JFrame {

    public AddPatientScreen(String mrn) {
        super("Rectangle");
        setTitle("Patient Information");
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
        JLabel nameLabel = new JLabel("name:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel insuranceLabel = new JLabel("Insurance:");
        JLabel genderLabel = new JLabel("gender:");
        JLabel chLabel = new JLabel("Clinical Hours:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel lbsLabel = new JLabel("last badge swipe:");
        JLabel emrLabel = new JLabel("EMR:");

        JButton processBtn = new JButton("Process edits");

        JTextField mrnField = new JTextField("", 40);
        JTextField nameField = new JTextField("", 40);
        JTextField addressField = new JTextField("", 40);
        JTextField insuranceField = new JTextField("", 40);
        JTextField genderField = new JTextField("", 40);
        JTextField chField = new JTextField("", 40);
        JTextField phoneField = new JTextField("", 40);
        JTextField lbsField = new JTextField("", 40);
        JTextField emrField = new JTextField("", 40);

        panel.add(mrnLabel);
        panel.add(mrnField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(insuranceLabel);
        panel.add(insuranceField);
        panel.add(genderLabel);
        panel.add(genderField);
        panel.add(chLabel);
        panel.add(chField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(lbsLabel);
        panel.add(lbsField);
        panel.add(emrLabel);
        panel.add(emrField);

        panel.add(processBtn);
        mrnField.setText(mrn);
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
                    String address = addressField.getText();
                    if (address.equals("")) {
                        address = null;
                    }
                    String insurance = insuranceField.getText();
                    if (insurance.equals("")) {
                        insurance = null;
                    }
                    String gender = genderField.getText();
                    if (gender.equals("")) {
                        gender = null;
                    }
                    String ch = chField.getText();
                    if (ch.equals("")) {
                        ch = null;
                    }
                    String phone = phoneField.getText();
                    if (phone.equals("")) {
                        phone = null;
                    }
                    String lbs = lbsField.getText();
                    if (lbs.equals("")) {
                        lbs = null;
                    }
                    String emr = emrField.getText();
                    if (emr.equals("")) {
                        emr = null;
                    }
                    String query = ("INSERT INTO Patients (MRN, PatientName, Address, Insurance, Gender, ClinicalHours, PhoneNumber, LastBadgeSwipe, EMR) VALUES (" + mrn + ", '" + name + "', '" + address + "', '" + insurance + "', '" + gender + "', " + ch + ", " + phone + ", " + lbs + ", " + emr + ");");
                    try {
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Patients WHERE MRN = " + mrn);
                        if (rs.next()) {
                            mrnField.setText("patient has already been entered");
                        } else {

                            stmt.executeUpdate(query);
                            mrnField.setText("Success");
                        }

                    } catch (SQLException x) {
                        mrnField.setText("" + x);

                    }

                }

            }
            processBtn.addActionListener(new ButtonListener());
        } catch (Exception f) {
            JOptionPane.showMessageDialog(null, f.getMessage());

        }
    }
}

