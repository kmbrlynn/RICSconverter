
//===============================================================================================

// RICSconverter

// Kimberly Douglas
// 2/13/12

// A simple Java GUI application designed to integrate conflicting inventory systems.
// Combs a .txt file generated by an Opticon 2001 UPC scan gun, then reformats it
// to be compatible with a feature in Retail Inventory Control System (RICS)
// that allows external inventory files to be uploaded.

// Update: 7/2/2013

//===============================================================================================

import java.io.*;
import javax.swing.*;
import java.awt.event.*;	//ActionListener and ActionEvent live here
import java.awt.*;          //BorderLayout lives here

public class RICSconverter implements ActionListener {	//IMPLEMENT THE ActionListener INTERFACE
    
    int lines; //a UPC string in the original file

    JButton button;
    
    public static void main(String args[]) throws Exception {	//LAUNCH THE GUI
        RICSconverter gui = new RICSconverter();
        gui.go();
    }
    
    public void go(){	//GUI CONSTRUCTOR CLASS
        Font tinyFont = new Font ("helvetica", Font.PLAIN, 12);
        Font smFont = new Font ("helvetica", Font.PLAIN, 20);
        Font medFont = new Font ("helvetica", Font.BOLD, 24);
        Font largeFont = new Font("helvetica", Font.BOLD, 32);
        
        JFrame frame = new JFrame();	//stuff that shows up in the GUI window
        JLabel title = new JLabel("RICSconverter");
        JLabel copyright = new JLabel("Kim Douglas 2013");
        JLabel space = new JLabel("\r\n");
        JLabel instructions1 = new JLabel(" 1. Save scanned .txt file as 'scan' (case-sensitive)");
        JLabel instructions2 = new JLabel(" in RICSconverter folder");
        JLabel space2 = new JLabel("\r\n");
        JLabel instructions3 = new JLabel(" 2. Click 'Convert!'");
        JLabel space3 = new JLabel("\r\n");
        JLabel instructions4 = new JLabel(" 3. 'scan_UPLOAD' has replaced 'scan'");
        button = new JButton("Convert!");
        
        JPanel northPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        
        button.addActionListener(this);	//button's ready!
        
        title.setFont(largeFont);	//give the words their fonts
        copyright.setFont(tinyFont);
        space.setFont(medFont);
        instructions1.setFont(smFont);
        instructions2.setFont(smFont);
        space2.setFont(smFont);
        instructions3.setFont(smFont);
        space3.setFont(smFont);
        instructions4.setFont(smFont);
        button.setFont(medFont);
        
        northPanel.add(title);	//put words and buttons in their panels
        centerPanel.add(copyright);
        centerPanel.add(space);
        centerPanel.add(instructions1);
        centerPanel.add(instructions2);
        centerPanel.add(space2);
        centerPanel.add(instructions3);
        centerPanel.add(space3);
        centerPanel.add(instructions4);
        
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));	//arguments for the panels
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);	//put the panels in the frame
        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//frame
        frame.setSize(600, 350);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {	//CLICK AND CONVERT!
        try{
            String upcString;
            
            File myScan = new File ("scan.txt");
            
            FileReader myFileReader = new FileReader("scan.txt");
            BufferedReader myBufferedReader = new BufferedReader(myFileReader);
            
            FileWriter myFileWriter = new FileWriter("scan_UPLOAD.txt");
            
            while((upcString = myBufferedReader.readLine()) != null) {	//make new file
                	
		    lines++; 

		    if (upcString.length() == 13){      //don't truncate a UPC if it's 13 chars
                    
                    System.out.println("found a 13-character UPC: truncation ignored");
                    myFileWriter.write("\r\n" + upcString + "   01");	
                    button.setText("Convert!");
                    button.setText("UPCs converted: " + lines);
                }
                
                else{
                    myFileWriter.write("\r\n" + upcString.substring(0,12) + "   01");	//top newline + truncate to 12 chars + tab + 01
                    button.setText("UPCs converted: " + lines);
                }
                
            }
            
            System.out.println("file [scan.txt] converted to RICS-compatible file [scan_UPLOAD.txt]");
            
            myFileReader.close();
            myFileWriter.close();
            
            if(myScan.exists() && myScan.isFile()){	//destroy original file
                myScan.delete();
            }
        }catch (Exception myException){	//whoopsies. 
            System.out.println("it probably can't find your scan.txt file, or there are less than 12 UPCs in one of the UPCs.");
            myException.printStackTrace();
            button.setText("Oops! Missing or corrupt 'scan.txt' file");
        }
        
    }
}

