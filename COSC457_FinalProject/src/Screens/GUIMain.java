/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import ListenersForMain.*;
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
public class GUIMain extends JFrame{
    
//panel format and size
    public GUIMain(){
        super("Rectangle");
        setTitle("Ashley Addiction Treatment");
        setSize(425,350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //labels
        JLabel optionLabel = new JLabel("Choose an option:");

        //buttons
        JButton patientBtn = new JButton("Patient Information");
        JButton staffBtn = new JButton("Staff Information");
        JButton buildingBtn = new JButton("Building Information");
        JButton programBtn = new JButton("Program Information");
        JButton premadeQueriesBtn = new JButton("Premade Custom Queries");

        //text fields
        
        //set layout
        setLayout(new BorderLayout());
        add(optionLabel, BorderLayout.NORTH);
        
        //button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,2,5,5));
        buttonPanel.add(patientBtn);
        buttonPanel.add(staffBtn);
        buttonPanel.add(buildingBtn);
        buttonPanel.add(programBtn);
        buttonPanel.add(premadeQueriesBtn);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        //set screen visible
        setVisible(true);
        
        //add event handlers for buttons
        patientBtn.addActionListener(new PatientButtonListener());
        staffBtn.addActionListener(new StaffButtonListener());
        buildingBtn.addActionListener(new BuildingButtonListener());
        programBtn.addActionListener(new ProgramButtonListener());
        premadeQueriesBtn.addActionListener(new CustomQueryButtonListener());
    } 
}
