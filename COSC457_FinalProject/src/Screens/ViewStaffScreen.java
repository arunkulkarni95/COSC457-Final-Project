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
public class ViewStaffScreen extends JFrame {

    public ViewStaffScreen(String eid) {
        super("Rectangle");
        setTitle("Staff Information");
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
        JLabel pidLabel = new JLabel("Position ID:");
        JLabel bNumLabel = new JLabel("Building Number:");
        JLabel jTypeLabel = new JLabel("Staff Type:");

        JTextField eidField = new JTextField("", 40);
        JTextField nameField = new JTextField("", 40);
        JTextField phoneField = new JTextField("", 40);
        JTextField addressField = new JTextField("", 40);
        
        JTextField dobField = new JTextField("", 40);
        JTextField salaryField = new JTextField("", 40);
        JTextField locField = new JTextField("", 40);
        JTextField pidField = new JTextField("", 40);
        JTextField bNumField = new JTextField("", 40);
        JTextField jTypeField = new JTextField("", 40);

        panel.add(eidLabel);
        panel.add(eidField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(dobLabel);
        panel.add(dobField);
        panel.add(salaryLabel);
        panel.add(salaryField);
        panel.add(locLabel);
        panel.add(locField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(pidLabel);
        panel.add(pidField);
        panel.add(bNumLabel);
        panel.add(bNumField);
        panel.add(jTypeLabel);
        panel.add(jTypeField);
        
        try {
            Connection conn = DriverManager.getConnection(SERVER, ID, PW);

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM Staff WHERE EID = " + eid + ";");
            
            if (rs.next()) {
                String name = rs.getString("StaffName");
                String phone = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                String dob = rs.getString("DOB");
                String salary = rs.getString("Salary");
                String loc = rs.getString("Location");
                String pid = rs.getString("PositionID");
                String bNum = rs.getString("StaffBuildingNumber");
                String jType = rs.getString("StaffType");

                eidField.setText(eid);
                nameField.setText(name);
                addressField.setText(address);
                dobField.setText(dob);
                salaryField.setText(salary);
                locField.setText(loc);
                phoneField.setText(phone);
                pidField.setText(pid);
                bNumField.setText(bNum);
                jTypeField.setText(jType);

            } else {
                eidField.setText(eid);
                nameField.setText("staff doesn't exist");
            }
        } catch (SQLException x) {
            eidField.setText("Internal Error");
            nameField.setText("" + x);

        }
    }
}
