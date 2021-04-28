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
public class BuildingScreen extends JFrame{
    public BuildingScreen(){
        //init layout
        super("Rectangle");
        setTitle("Building Information");
        setSize(425,350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        //sql connection stuff
        final String ID = "root";
        final String PW = "Cosc4572!";
        final String SERVER = "jdbc:mysql://104.155.181.126:3306/mydb";
        
        //labels
        JLabel buildingNumLabel = new JLabel("Building Number:");
        JLabel resultsLabel = new JLabel("Results:");
        
        //text fields
        String[] queries = {"1","2","3","4","5","6"};
        JComboBox queryBox = new JComboBox(queries);
        //JTextField buildingNumField = new JTextField("",15);
        
        //text areas
        JTextArea resultsArea = new JTextArea(5,5);
        resultsArea.setEditable(false);
        
        //buttons
        JButton processBtn = new JButton("View Building Information");
        
        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2,5,5));
        topPanel.add(buildingNumLabel);
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
                    //mor sql stuff
                    try{
                         Connection con = DriverManager.getConnection(SERVER, ID, PW);
                         Statement stmt = con.createStatement();
                         
                         ResultSet rs = stmt.executeQuery("SELECT * FROM Building WHERE BuildingNumber = " + (String)queryBox.getSelectedItem());
           
                         if(rs.next()){
                            String BN = rs.getString("BuildingNumber");
                            String MNB = rs.getString("MaxNumberOfBeds");
                            String BT = rs.getString("BuildingType");
                            String result = ("Building Number: " + BN + " \nMax Number of Beds: " + MNB + "\nBuilding type: " + BT);
                            resultsArea.setText(result);
                         }
                         else
                             resultsArea.setText("Building doesn't exist");
                    }catch(SQLException x){
                        resultsArea.setText("" + x);
        
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
