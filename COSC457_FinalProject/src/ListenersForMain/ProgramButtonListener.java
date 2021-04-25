/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListenersForMain;

import Screens.*;
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
public class ProgramButtonListener implements ActionListener{
    
    public void actionPerformed(ActionEvent e){
        new ProgramScreen();
    }
}
