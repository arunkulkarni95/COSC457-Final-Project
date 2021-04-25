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
public class BuildingScreen extends JFrame{
    public BuildingScreen(){
        //init layout
        super("Rectangle");
        setTitle("Building Information");
        setSize(425,350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        //labels
        JLabel buildingNumLabel = new JLabel("Building Number:");
        JLabel resultsLabel = new JLabel("Results:");
        
        //text fields
        JTextField buildingNumField = new JTextField("",15);
        
        //text areas
        JTextArea resultsArea = new JTextArea(5,5);
        
        //buttons
        JButton processBtn = new JButton("View Building Information");
        
        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2,5,5));
        topPanel.add(buildingNumLabel);
        topPanel.add(buildingNumField);
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
                    
                }
                catch (Exception f){
                    JOptionPane.showMessageDialog(null, f.getMessage());
                }
            }
        }
        
        processBtn.addActionListener(new ButtonListener());
    }
}
