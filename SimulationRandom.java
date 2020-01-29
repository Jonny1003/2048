import java.util.ArrayList;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;


public class SimulationRandom extends AbstractSimulation
{

    public static void main(String[] args){
        ArrayList<Integer> data = simulateSamples(1000);
        drawHistogram(200,data);
        System.out.println("MEAN-> "+calculateMean(data));
        System.out.println("DEVIATION-> "+calculateDeviation(data));
    }

    public static ArrayList<Integer> simulateSamples(int numSamples){
        ArrayList<Integer> scores = new ArrayList();

        RandomAI r = new RandomAI();

        for (int i = 1; i <= numSamples; i++){
            r.playGame();
            scores.add(r.getScore());
        }

        return scores;
    }

    public static ArrayList<Integer> samplingDistribution(){
        ArrayList<Integer> vals = new ArrayList();

        for (int i = 0; i < 100; i++){
            ArrayList<Integer> a = simulateSamples(1000);

            int sum = 0;
            for (int j : a){
                sum += j;
            }           
            vals.add(sum/a.size());
        }
        
        return vals;
    }
}
