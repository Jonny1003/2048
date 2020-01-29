import java.util.ArrayList;
public class SimulationAlgorithm extends AbstractSimulation{
    
    public static void main(String[] args){
        ArrayList<Integer> data = simulateSamples(1000);
        drawHistogram(3000,data);
        System.out.println("MEAN-> "+calculateMean(data));
        System.out.println("DEVIATION-> "+calculateDeviation(data));
    }

    public static ArrayList<Integer> simulateSamples(int numSamples){
        ArrayList<Integer> scores = new ArrayList();

        Algorithm r = new Algorithm();

        for (int i = 1; i <= numSamples; i++){
            r.playGame();
            scores.add(r.getScore());
        }

        return scores;
    }
 
    public static ArrayList<Integer> samplingDistribution(){
        ArrayList<Integer> vals = new ArrayList();

        for (int i = 0; i < 100; i++){
            ArrayList<Integer> a = simulateSamples(100);
            int sum = 0;
            for (int j : a){
                sum += j;
            }           
            vals.add(sum/a.size());
        }
        
        return vals;
    }
}
