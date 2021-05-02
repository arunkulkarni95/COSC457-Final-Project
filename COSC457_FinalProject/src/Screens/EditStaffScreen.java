/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Bethany
 */
public class EditStaffScreen extends JFrame{
    
    public EditStaffScreen(String eid) {
        super("Rectangle");
        setTitle("Edit Staff Information");
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
        
        JButton processBtn = new JButton("Process edits");

        JLabel eidLabel = new JLabel("Staff EID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel dobLabel = new JLabel("DOB:");
        JLabel salaryLabel = new JLabel("Salary:");
        JLabel locLabel = new JLabel("Location:");
        JLabel bNumLabel = new JLabel("Building Number:");
        JLabel jobTypeLabel = new JLabel("Job Type:");
                
        JTextField eidField = new JTextField("", 40);
        JTextField nameField = new JTextField("", 40);
        JTextField phoneField = new JTextField("", 40);
        JTextField addressField = new JTextField("", 40);
        JTextField dobField = new JTextField("", 40);
        JTextField salaryField = new JTextField("", 40);
        JTextField locField = new JTextField("", 40);
        JTextField bNumField = new JTextField("", 40);
        JTextField jobTypeField = new JTextField("", 40);
        
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
        panel.add(locField);
        
        panel.add(bNumLabel);
        panel.add(bNumField);
        panel.add(jobTypeLabel);
        panel.add(jobTypeField);
        
        panel.add(processBtn);

        try {
            Connection conn = DriverManager.getConnection(SERVER, ID, PW);

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM Staff WHERE EID = " + eid);

            if (rs.next()) {
                String name = rs.getString("StaffName");
                String phone = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                String dob = rs.getString("DOB");
                String salary = rs.getString("Salary");
                String loc = rs.getString("Location");
                String jobType = rs.getString("PositionID");
                String bNum = rs.getString("StaffBuildingNumber");
                //String jType = rs.getString("StaffType");
                
                eidField.setText(eid);
                nameField.setText(name);
                addressField.setText(address);
                dobField.setText(dob);
                salaryField.setText(salary);
                locField.setText(loc);
                phoneField.setText(phone);
                jobTypeField.setText(jobType);
                bNumField.setText(bNum);
                //jTypeField.setText(jType);
                                
                //this fires when the button is clicked
                class ButtonListener implements ActionListener {

                    public void actionPerformed(ActionEvent e) {

                        try {
                            if (!eid.equals(eidField.getText())) {
                                eidField.setText("Cannot change EID.  All other fields were changed");
                            }
                            String name = nameField.getText();
                            String address = addressField.getText();
                            String dob = dobField.getText();
                            String salary = salaryField.getText();
                            String loc = locField.getText();
                            String phone = phoneField.getText();
                            String jobType = jobTypeField.getText();
                            String bNum = bNumField.getText();
                            
                            
                            String query1 = ("UPDATE Staff SET StaffName = '" + name + "', PhoneNumber = '" 
                                    + phone + "', Address = '" + address + "', DOB = '" + dob + "', Salary = '" 
                                    + salary + "', Location = '" + loc + "', PositionID = '" + jobType + "', StaffBuildingNumber = '" 
                                    + bNum + "' WHERE EID = " + eid + ";");
                            
                            String query2 = "";
                            String query3 = "";
                            String query4 = "";
                            switch(jobType){
                                case "1": //Counselor
                                    JTextField licField = new JTextField("",40);
                                    try{
                                        ResultSet temp_rs = stmt.executeQuery("SELECT * FROM Counselor WHERE CounselorEID = "+eid);
                                        
                                        if(temp_rs.next()){
                                            String lic = temp_rs.getString("Liscense");
                                            licField.setText(lic);
                                        }
                                        
                                    }catch(SQLException x){
                                        System.out.println("Error: "+x);
                                    }
                                    
                                    JOptionPane.showMessageDialog(null,licField,"Current License:",JOptionPane.QUESTION_MESSAGE);
                                    
                                    String currentLicense = licField.getText();
                                    query2 = " UPDATE Counselor SET Liscense = '"+currentLicense+"' WHERE CounselorEID = "+eid+";";
                                    
                                    break;
                                case "2": //Spiritual Counselor
                                    JTextField faithField = new JTextField("",40);
                                    try{
                                        ResultSet temp_rs = stmt.executeQuery("SELECT * FROM SpiritualCounselor WHERE SpiritualCounselorEID = "+eid);
                                        
                                        if(temp_rs.next()){
                                            String faith = temp_rs.getString("PracticingFaith");
                                            faithField.setText(faith);
                                        }
                                        
                                    }catch(SQLException x){
                                        System.out.println("Error: "+x);
                                    }
                                    
                                    JOptionPane.showMessageDialog(null,faithField,"Current Faith: ",JOptionPane.QUESTION_MESSAGE);
                                    
                                    String newFaith = faithField.getText();
                                    query2 = " UPDATE SpiritualCounselor SET PracticingFaith = '"+newFaith+"' WHERE SpiritualCounselorEID = "+eid+";";
                                    
                                    break;
                                case "6": //Driver
                                    JTextField dLicField = new JTextField("",15);
                                    JTextField routeField = new JTextField("",40);
                                    JTextField vehicleField = new JTextField("",40);
                                    try{
                                        ResultSet temp_rs = stmt.executeQuery("SELECT * FROM Driver WHERE DriverEID = "+eid);
                                        
                                        if(temp_rs.next()){
                                            String dLic = temp_rs.getString("DriversLiscence");
                                            String route = temp_rs.getString("Route");
                                            String vehicle = temp_rs.getString("Vehicle");
                                            
                                            dLicField.setText(dLic);
                                            routeField.setText(route);
                                            vehicleField.setText(vehicle);
                                        }
                                        
                                    }catch(SQLException x){
                                        System.out.println("Error: "+x);
                                    }
                                    
                                    JOptionPane.showMessageDialog(null,dLicField,"Current Driver's Liscence: ",JOptionPane.QUESTION_MESSAGE);
                                    String curr_dLic = dLicField.getText();
                                    query2 = " UPDATE Driver SET DriversLiscence = '"+curr_dLic+"' WHERE DriverEID = "+eid+";";
                                    
                                    JOptionPane.showMessageDialog(null,routeField,"Current Route: ",JOptionPane.QUESTION_MESSAGE);
                                    String curr_route = routeField.getText();
                                    query3 = " UPDATE Driver SET Route = '"+curr_route+"' WHERE DriverEID = "+eid+";";
                                    
                                    JOptionPane.showMessageDialog(null,vehicleField,"Current Vehicle: ",JOptionPane.QUESTION_MESSAGE);
                                    String curr_vehicle = vehicleField.getText();
                                    query4 = " UPDATE Driver SET Vehicle = '"+curr_vehicle+"' WHERE DriverEID = "+eid+";";
                                    
                                    break;
                                default:
                                    break;

                            }
                            
                            try {

                                stmt.executeUpdate(query1);
                                
                                if(!"".equals(query2)){
                                    stmt.executeUpdate(query2);
                                }
                                if(!"".equals(query3)){
                                    stmt.executeUpdate(query3);
                                }
                                if(!"".equals(query4)){
                                    stmt.executeUpdate(query4);
                                }
                                eidField.setText("Success");

                            } catch (SQLException x) {
                                eidField.setText("" + x);

                            }

                        } catch (Exception f) {
                            JOptionPane.showMessageDialog(null, f.getMessage());
                        }
                    }
                }
                
                processBtn.addActionListener(new ButtonListener());

            } else {
                eidField.setText(eid);
                nameField.setText("Staff doesn't exist");
            }
        } catch (SQLException x) {
            eidField.setText("Internal Error");
            nameField.setText("" + x);

        }
    }
}
