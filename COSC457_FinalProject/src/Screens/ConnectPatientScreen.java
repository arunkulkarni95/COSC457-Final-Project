/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;
import main.MainCarter;
import static main.MainCarter.convertRSToString;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carter
 */
public class ConnectPatientScreen extends JFrame{
    public ConnectPatientScreen() {
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
        
        JLabel res = new JLabel("");


        try {
            JLabel selectOpt = new JLabel("Select Option:");
        JLabel patientMRN = new JLabel("Patient Name:");
            Connection conn = DriverManager.getConnection(SERVER, ID, PW);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MRN, PatientName FROM Patients;");
            List<List<String>> asString = convertRSToString(rs);
            String[] opts = new String[asString.size()];
            Boolean gotOpts = asString.size() > 0;
            for (int i=0;i<asString.size();i++) {
                opts[i] = (asString.get(i).get(1));
            }
            JComboBox pBox = new JComboBox(opts);
            panel.add(pBox);
            conn.close();
      
            String options[] = {"Assign Bed","Assign Visit","Meet with Counselor"};
            JComboBox optionBox = new JComboBox(options);
            JPanel topPanel = new JPanel();
            JButton processBtn = new JButton("Perform Operation");

            topPanel.setLayout(new GridLayout(3,2));
            topPanel.add(selectOpt);
            topPanel.add(optionBox);
            topPanel.add(patientMRN);
            topPanel.add(pBox);
            topPanel.add(processBtn);
            topPanel.add(res);
            //Three buttons:
            //First must enter patient ID
            //Assign Visit
            //Assign Bed
            //Meet with counsolor
            add(topPanel, BorderLayout.NORTH);
            class ButtonListener implements ActionListener{

                public void actionPerformed(ActionEvent e){
                    //TODO: add screen behavior logic here
                    try{   
                        res.setText("");
                        //String MRN = pMRNField.getText();
                        int idx = pBox.getSelectedIndex();
                        String MRN = asString.get(idx).get(0);
                        System.out.println(MRN);
                        if (MRN.length() == 0) {
                            res.setText("Please enter a patient MRN.");
                        } else {
                            Connection con = DriverManager.getConnection(SERVER, ID, PW);
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT Gender FROM Patients WHERE MRN = " + MRN + ";");
                            if (rs.next()) {
                                String gender = rs.getString("Gender");
                                String chosen = (String)optionBox.getSelectedItem();
                                switch (chosen) {
                                    case "Assign Bed":
                                        new AssignBed(MRN,gender);
                                        break;
                                    case "Assign Visit":
                                        new AssignVisit(MRN);
                                        break;
                                    case "Meet with Counselor":
                                        new AssignPatientCounselor(MRN);
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                res.setText("Not a valid patient MRN.");

                            }
                            con.close();
                        }
                    }
                    catch (Exception f){
                        JOptionPane.showMessageDialog(null, f.getMessage());
                    }
                }
            }

            processBtn.addActionListener(new ButtonListener());

        }
        catch (SQLException ex) {
            res.setText("Error!");
            Logger.getLogger(MainCarter.class.getName()).log(Level.SEVERE, null, ex);
            panel.add(new JComboBox());
        }
    }
}
