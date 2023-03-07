/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment03;

/**
 *
 * @author Subhan Bawany 19686
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Curve extends JFrame{
    private static int width = 800;
    private static int height = 600;

    private ArrayList<int[]> arrList = new ArrayList<int[]>();

    public Curve() {
        super("Bezier Curve");

        getContentPane().setBackground(Color.LIGHT_GRAY);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {

                Curve test = new Curve();
                test.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            Point p = e.getPoint();
                            test.addPoint(p.x, p.y);
                        }
                        test.re();
                    }
                });
                test.setVisible(true);

            }
        });
    }

    public void addPoint(int x, int y) {
        int[] temp = {x, y};
        arrList.add(temp);
    }

    void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        int length = arrList.size();

        if(length > 6){
            arrList = new ArrayList<int[]>();
            length = 0;
            System.out.println("You have exceeded six control points. Click on canvas to begin!");
        }

        for (int i = 0; i < length; i++) {
            drawPoint(0, g);
            if(i>0){
            drawPoint(i, g);
            g2d.setColor(Color.WHITE);
            g2d.drawLine(arrList.get(i-1)[0], arrList.get(i-1)[1], arrList.get(i)[0], arrList.get(i)[1]);
            }
        }

        if(length >= 2 && length <= 6) {
            int steps = 500;
            double del_u = 1.0 / (steps - 1);
            double[] u = new double[steps];
            for (int i = 0; i < steps; i++) {
                if(i == 0){
                    u[i] = 0;
                    continue;
                }
                u[i] = u[i - 1] + del_u;
            }

            ArrayList<double[]> final_point = new ArrayList<double[]>();

            for (int j = 0; j < u.length; j++) {
                double var_u = u[j];
                double var_u2 = 1 - var_u;
                double temp_x = 0;
                double temp_y = 0;
                for (int i = 0; i < length; i++) {
                    int numerator = factorial(length - 1);
                    int denominator = factorial(length - 1 - i) * factorial(i);
                    double u1 = Math.pow(var_u, i);
                    double u2 = Math.pow((var_u2), length - 1 - i);
                    int nCr = (numerator / denominator);
                    temp_x += for_xu(nCr, u1, u2, i);
                    temp_y += for_yu(nCr, u1, u2, i);
                }
                double[] temp_p = {temp_x, temp_y};
                final_point.add(temp_p);
            }
            
            g2d.setColor(Color.blue);
            for (int i = 1; i < final_point.size(); i++) {
                g2d.drawLine((int) final_point.get(i - 1)[0], (int) final_point.get(i - 1)[1], (int) final_point.get(i)[0], (int) final_point.get(i)[1]);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    
    public void drawPoint(int i, Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
            g2d.fillOval(arrList.get(i)[0] - 5, arrList.get(i)[1] - 5, 10,10);
    }
    public double for_xu(int nCr, double u1, double u2, int i){
        return (nCr * u1 * u2 * arrList.get(i)[0]);
    }
    public double for_yu(int nCr, double u1, double u2, int i){
        return (nCr * u1 * u2 * arrList.get(i)[1]);
    }
    public void re(){
        repaint();
    }

    public static int factorial(int number){
        if (number == 0)
            return 1;
        else
            return(number * factorial(number-1));
    }
}

