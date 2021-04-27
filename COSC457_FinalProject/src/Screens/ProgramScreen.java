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
public class ProgramScreen extends JFrame{
    public ProgramScreen(){
        //init layout
        super("Rectangle");
        setTitle("Program Information");
        setSize(425,350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        //labels
        JLabel programNumLabel = new JLabel("Program Number:");
        JLabel resultsLabel = new JLabel("Results:");
        JLabel optionLabel = new JLabel("Select Option:");
        
        //text fields
        JTextField programNumField = new JTextField("",15);
        
        //text areas
        JTextArea resultsArea = new JTextArea(5,5);
        resultsArea.setEditable(false);
        
        //buttons
        JButton processBtn = new JButton("Perform Operation");
        
        //initialize JComboBox
        String options[] = {"View Patients in Program","Add Patient to Program"};
        JComboBox optionBox = new JComboBox(options);
        
        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3,2,5,5));
        topPanel.add(programNumLabel);
        topPanel.add(programNumField);
        topPanel.add(optionLabel);
        topPanel.add(optionBox);
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
                        case "Add Patient to Program":
                            //logic to add here
                            //maybe use a popup window for both of these (?)
                            break;
                        case "View Patients in Program":
                            //logic to view here
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
