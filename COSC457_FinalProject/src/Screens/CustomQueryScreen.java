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
public class CustomQueryScreen extends JFrame{
    public CustomQueryScreen(){
        //init layout
        super("Rectangle");
        setTitle("Premade Custom Queries");
        setSize(425,350);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());
        
        //labels
        JLabel querySelect = new JLabel("Select a premade custom query:");
        JLabel resultsLabel = new JLabel("Results:");
        
        //comboBox
        String[] queries = {"Query 1","Query 2","Query 3","Query 4","Query 5","Query 6","Query 7","Query 8","Query 9","Query 10"};
        JComboBox queryBox = new JComboBox(queries);
        
        //text area
        JTextArea resultsArea = new JTextArea(5,5);
        resultsArea.setEditable(false);
        
        //buttons
        JButton processBtn = new JButton("Perform Operation");
        
        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2));
        topPanel.add(querySelect);
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
                    String selectedOption = (String)queryBox.getSelectedItem();
                    switch (selectedOption){
                        case "Query 1":
                            //perform query
                            break;
                        case "Query 2":
                            //perform query
                            break;
                        case "Query 3":
                            //perform query
                            break;
                        case "Query 4":
                            //perform query
                            break;
                        case "Query 5":
                            //perform query
                            break;
                        case "Query 6":
                            //perform query
                            break;
                        case "Query 7":
                            //perform query
                            break;
                        case "Query 8":
                            //perform query
                            break;
                        case "Query 9":
                            //perform query
                            break;
                        case "Query 10":
                            //perform query
                            break;
                    }
                }
                catch (Exception f){
                    JOptionPane.showMessageDialog(null, f.getMessage());
                }
            }
        }       
    }
}