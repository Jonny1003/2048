import java.util.ArrayList;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;

public class AbstractSimulation
{
    
    public static double calculateMean(ArrayList<Integer> data){
        double sum = 0;
        for (int i = 0; i < data.size(); i++){
            sum += data.get(i);
        }
        return sum/data.size();
    }
    
    public static double calculateDeviation(ArrayList<Integer> data){
        double mean = calculateMean(data);
        double sum = 0;
        for (int i : data){
            sum += (i-mean)*(i-mean);           
        }
        double var = sum/data.size();       
        return Math.pow(var,0.5);
    }
    
    public static void drawHistogram(int containerSize, ArrayList<Integer> data){
        int max = data.get(0);
        for (int i : data){
            if (max < i){
                max = i;
            }
        }

        int[] containers = new int[max/containerSize+2];
        for (int i = 0; i < containers.length; i++){
            containers[i] = 0;
        }

        for (int i : data){
            containers[i/containerSize] ++;
        }

        int highestY = containers[0];
        int lowestContainer = 0; 
        boolean done = false;
        for (int i = 0; i < containers.length; i++){
            if (highestY < containers[i]) highestY = containers[i];
            if (containers[i] != 0 && !done) {
                lowestContainer = i;
                done = true;
            }
        }
        
        
        if (lowestContainer > 2) lowestContainer -= 2;

        JFrame f = new JFrame("ALG Sample");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final BufferedImage b = new BufferedImage(880,440,BufferedImage.TYPE_INT_RGB);

        Graphics2D g = b.createGraphics();

        //draw background
        g.setColor(Color.WHITE);
        g.fillRect(0,0,880,440);

        g.setFont(new Font("Gujarati MT",Font.PLAIN,16));

        //draw y-axis
        g.setColor(Color.BLACK);
        g.drawLine(40,410,40,15);       
        g.drawString("Frequency",2,12);

        g.setFont(new Font("Gujarati MT",Font.PLAIN,13));
        for (int i = 0; i < 10; i++){
            int y = 410-i*40;
            g.drawString(highestY*(i+0.0)/10+"",10, y);
            g.drawLine(40,y,48,y);
        }

        //draw bars
        int numContainers = containers.length-lowestContainer;
        for (int i = 0; i < numContainers; i++){
            int xCoord = 40+800*i/numContainers;
            g.setColor(Color.BLACK);
            g.drawString((i+lowestContainer)*containerSize+"",xCoord,420);
            g.setColor(Color.RED);
            int length = containers[i+lowestContainer]*400/highestY;
            g.fillRect(xCoord, 410-length,
                800/numContainers, length);
            g.drawRect(xCoord, 410-length,
                800/numContainers, length);
            g.setColor(Color.BLACK);
            g.drawLine(xCoord,410,xCoord,402);
        }
        
        //draw x-axis
        g.setColor(Color.BLACK);
        g.drawLine(40,410,840,410);
        g.drawString("Score", 420, 435);

        g.dispose();              

        //display data
        JLabel l = new JLabel(new ImageIcon(b));
        f.add(l);     
        f.pack();
        f.setFocusable(true);
        f.setVisible(true); 
    }
}
