import java.util.ArrayList;
public class SimulationAI1 extends AbstractSimulation
{
    public static void main(String[] args){
        ArrayList<Integer> data = samplingDistribution();
        SimulationRandom.drawHistogram(10,data);
        System.out.println("MEAN-> "+calculateMean(data));
        System.out.println("DEVIATION-> "+calculateDeviation(data));
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

    public static ArrayList<Integer> simulateSamples(int numSamples){
        ArrayList<Integer> scores = new ArrayList();

        AI1 aI = new AI1();

        for (int i = 1; i <= numSamples; i++){
            aI.playGame();
            scores.add(aI.getScore());          
        }

        return scores;
    }
    
    
}
