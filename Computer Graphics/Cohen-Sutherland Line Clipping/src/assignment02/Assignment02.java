
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment02;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//import Graph.GraphArea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Subhan Bawany 19686
 */
public class Assignment02  extends JFrame implements ActionListener{
    
    private static int width = 800;
    private static int height = 800;

    public Assignment02() {
        super("Cohen-Sutherland LineClipping");
        
        getContentPane().setBackground(Color.WHITE);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
 
    void draw(Graphics g) 
    {
    	
        Graphics2D g2d = (Graphics2D) g;
        //*****Add your region here*****

        int x_max = 600;//right side of viewport
        int x_min = 200;//left side corner of viewport
        int y_max = 600;//top side of viewport
        int y_min = 200;//bottom side of viewport
        
        g2d.setColor(Color.yellow);
        g2d.fillRect(x_min, y_min, (x_max - x_min), (y_max - y_min));
        g2d.setColor(Color.black);
        g2d.drawRect(x_min, y_min, (x_max - x_min), (y_max - y_min));

        double P1[] = {100,200};// First Point
        double P2[] = {700,600};//Second Point
        
        g2d.drawLine((int) P1[0], (int) P1[1], (int) P2[0], (int) P2[1]);
        
        int left = 1;// denotes 0001
        int right = 2;// denotes 0010
        int bottom = 4;// denotes 0100
        int top = 8;// denotes 1000
        int region[] = {0, 0};
        
        //Assigning region to first point
        if (P1[0] < x_min)
            region[0] |= left;//belongs to left region
        else if (P1[0] > x_max) 
            region[0] |= right;//belongs to right region
        if (P1[1] < y_min) 
            region[0] |= bottom;//belongs to bottom region
        else if (P1[1] > y_max) 
            region[0] |= top;//belongs to top region
        
        //Assigning region to second point
        if (P2[0] < x_min) 
            region[1] |= left;//belongs to left region
        else if (P2[0] > x_max) 
            region[1] |= right;//belongs to right region
        if (P2[1] < y_min) 
            region[1] |= bottom;//belongs to bottom region
        else if (P2[1] > y_max) 
            region[1] |= top;//belongs to top region
        
        boolean accept = false;
        
        while(true){
            if((region[0] == 0) && (region[1] == 0)){//checks if both point lies inside viewport
                accept = true;
                break;
            }
            else if((region[0] & region[1]) != 0){
                break;
            }
            else{
                int region_out;
                double x = 0, y = 0;
                double m = (P2[1] - P1[1])/(P2[0] - P1[0]);
 
                if (region[0] != 0)
                    region_out = region[0];
                else
                    region_out = region[1];

                if ((region_out & top) == top) {
                    x = P1[0] + (1/m)*(y_max - P1[1]);
                    y = y_max;
                }
                else if ((region_out & bottom) == bottom) {
                    x = P1[0] + (1/m)*(y_min - P1[1]);
                    y = y_min;
                }
                else if ((region_out & right)==right) {
                    y = P1[1] + (m)* (x_max - P1[0]);
                    x = x_max;
                }
                else if ((region_out & left) == left) {
                    y = P1[1] + (m)* (x_min - P1[0]);
                    x = x_min;
                }
 
                if (region_out == region[0]) {
                    P1[0] = x;
                    P1[1] = y;
                    
                    region[0] = 0;
                    
                    if (P1[0] < x_min) 
                        region[0] |= left;
                    else if (P1[0] > x_max) 
                        region[0] |= right;
                    if (P1[1] < y_min) 
                        region[0] |= bottom;
                    else if (P1[1] > y_max) 
                        region[0] |= top;
                }
                else {
                    P2[0] = x;
                    P2[1] = y;
        
                    region[1] = 0;
                    
                    if (P2[0] < x_min) 
                        region[1] |= left;
                    else if (P2[0] > x_max) 
                        region[1] |= right;
                    if (P2[1] < y_min) 
                        region[1] |= bottom;
                    else if (P2[1] > y_max) 
                        region[1] |= top;
                }
            }
        }
        
        if(accept){
            g2d.setColor(Color.RED);
            g2d.drawLine((int) P1[0], (int) P1[1], (int) P2[0], (int) P2[1]);
        }
    }
    
    public void paint(Graphics g) 
    {
        draw(g);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO region application logic here
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() 
            {
  //              new JavaGraphs().setVisible(true);
                new Assignment02().setVisible(true);
                
            }
        });
    }
    
    
	@Override
	public void actionPerformed(ActionEvent e) {
		
		repaint();
	}
    
}

