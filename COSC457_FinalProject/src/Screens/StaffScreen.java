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

/**
 *
 * @author Arun
 */
public class StaffScreen extends JFrame{
    public StaffScreen(){
        //init layout
        super("Rectangle");
        setTitle("Staff Information");
        setSize(425,350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        //labels
        JLabel optionLabel = new JLabel("Select option:");
        JLabel staffNoLabel = new JLabel("Staff ID #:");
        JLabel resultsLabel = new JLabel("Results:");
        JLabel jobLabel = new JLabel("Job Title:");
        
        //text fields
        JTextField staffNoField = new JTextField("",15);
        
        //text areas
        JTextArea resultsArea = new JTextArea(5,5);
        
        //buttons
        JButton processBtn = new JButton("Perform Operation");
        
        //initialize JComboBox
        String options[] = {"Add","View","Update","Delete"};
        JComboBox optionBox = new JComboBox(options);
        String job[] = {"Counselor","Spiritual Counselor","Patient Care Other","Support Other","Driver","Alumni"};
        JComboBox jobBox = new JComboBox(job);
        
        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4,2,5,5));
        topPanel.add(optionLabel);
        topPanel.add(optionBox);
        topPanel.add(staffNoLabel);
        topPanel.add(staffNoField);
        topPanel.add(jobLabel);
        topPanel.add(jobBox);
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
                    String selectedOption = (String)optionBox.getSelectedItem();
                    switch (selectedOption){
                        case "Add":
                            //logic to add here
                            break;
                        case "View":
                            //logic to view here
                            break;
                        case "Update":
                            //logic to update here
                            break;
                        case "Delete":
                            //logic to delete here
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
