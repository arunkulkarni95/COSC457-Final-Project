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
public class ViewPatientScreen extends JFrame {

    public ViewPatientScreen(String mrn) {
        super("Rectangle");
        setTitle("Patient Information");
        setSize(425, 350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));
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
        
        try {
            Connection conn = DriverManager.getConnection(SERVER, ID, PW);

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM Patients WHERE MRN = " + mrn + ";");
  
                

            if (rs.next()) {
                String name = rs.getString("PatientName");
                String address = rs.getString("Address");
                String insurance = rs.getString("Insurance");
                String gender = rs.getString("Gender");
                String ch = rs.getString("ClinicalHours");
                String phone = rs.getString("PhoneNumber");
                String lbs = rs.getString("LastBadgeSwipe");
                String emr = rs.getString("EMR");

                mrnField.setText(mrn);
                nameField.setText(name);
                addressField.setText(address);
                insuranceField.setText(insurance);
                genderField.setText(gender);
                chField.setText(ch);
                phoneField.setText(phone);
                lbsField.setText(lbs);
                emrField.setText(emr);

            } else {
                mrnField.setText(mrn);
                nameField.setText("Patient doesn't exist");
            }
        } catch (SQLException x) {
            mrnField.setText("Internal Error");
            nameField.setText("" + x);

        }
    }
}
