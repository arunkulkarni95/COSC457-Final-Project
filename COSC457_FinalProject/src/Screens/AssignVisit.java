/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import dbproject.MainCarter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.Statement;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Carter
 */
public class AssignVisit extends JFrame{
    public AssignVisit(String MRN) {
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
        JLabel patientMRN = new JLabel("Patient MRN: "+MRN);
        JLabel none = new JLabel("");
        JLabel reason = new JLabel("Reason:");
        JLabel cStatus = new JLabel("COVID Status (T/F):");
        JLabel startDate = new JLabel("Start Date: YYYY-MM-DD");
        JLabel ret = new JLabel("");
        JTextField rInput = new JTextField("",15);
        JTextField cInput = new JTextField("",15);
        JTextField dateInput = new JTextField("",15);
        
        JButton processBtn = new JButton("Add Visit");

        panel.add(patientMRN);
        panel.add(none);
        panel.add(reason);
        panel.add(rInput);
        panel.add(cStatus);
        panel.add(cInput);
        panel.add(startDate);
        panel.add(dateInput);
        panel.add(processBtn);
        panel.add(ret);
        class ButtonListener implements ActionListener{
            
                public void actionPerformed(ActionEvent e){
                    ret.setText("");
                    if (validate(cInput.getText(),dateInput.getText())) {
                        String nD = add28(dateInput.getText());
                        if (rInput.getText().length() < 1) {
                            rInput.setText("Addiction help");
                        }
                        try {
                            Connection conn = DriverManager.getConnection(SERVER, ID, PW);
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT MAX(VisitNumber) m FROM Visit WHERE PatientMRN=\""+MRN+"\"");
                            String vNum;
                            if (rs.next()) {
                                vNum = ""+(Integer.parseInt(rs.getString("m"))+1);
                            } else {
                                vNum = "1";
                            }
                            String query = "INSERT INTO Visit VALUES (";
                            query = query+addQ(rInput.getText(),false);
                            query = query+addQ(cInput.getText(),false);
                            query = query+addQ(nD,false);
                            query = query+addQ(MRN,false);
                            query = query+addQ(vNum,false);
                            query = query+addQ(dateInput.getText(),true);
                            query = query + ");";
                            PreparedStatement ASSIGN = conn.prepareStatement(query);
                            ASSIGN.execute();
                            ret.setText("Success!");
                            conn.close();
                            
                        } catch (SQLException ex) {
                            ret.setText("Error!");
                            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        ret.setText("Please make sure values are in the proper format.");
                    }
                    
                }
        }
        processBtn.addActionListener(new ButtonListener());
         
    }
    private String addQ(String q, Boolean end) {
        String ret = "\""+q+"\"";
        if (!end) {
            ret = ret + ", ";
        }
        return ret;
    }
    private String add28(String dateInput) {
        String[] ds = dateInput.split("[-]");
        String newDate = "";
        int day = Integer.parseInt(ds[2]);
        int month = Integer.parseInt(ds[1]);
        int year = Integer.parseInt(ds[0]);
        
        day = day + 28;
        if (day > 30) {
            day = day % 30;
            month = month + 1;
        }
        if (month > 12) {
            month = month % 12;
            year = year + 1;
        }
        newDate = ""+year+"-"+month+"-"+day;
        return newDate;
    }
    private Boolean validate(String cInput,String dateInput) {
        Boolean ret = false;
        if (cInput.equals("F") || cInput.equals("T")) {
            
            String[] ds = dateInput.split("[-]");
            if (ds.length == 3) {
                if (ds[0].length() == 4) {
                    if (ds[1].length() < 3 && ds[2].length() <3) {
                        try {
                            int year = Integer.parseInt(ds[0]);
                            int month = Integer.parseInt(ds[1]);
                            int day = Integer.parseInt(ds[2]);
                            if (month > 0 && month < 13) {
                                if (day > 0 && day < 32) {
                                    ret = true;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error parsing...");
                            ret = false;
                        }
                    }
                }
            } else {
                System.out.println("Less than 3");
            }
        }
        return ret;
    }
}
