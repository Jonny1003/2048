import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
public class AI1 extends IAI{

    private ArrayList<Integer> moves;
    private ArrayList<Integer> tracker;
    private ArrayList<boolean[][]> stateTracker;
    private int size;

    public AI1(){
        moves = new ArrayList();
        tracker = new ArrayList();
        stateTracker = new ArrayList();
        size = 0;
    }

    public void playGame(){
        Board b = new Board();
        b.addRandomValue();
        b.addRandomValue(); 
        
        moves = new ArrayList();
        tracker = new ArrayList();
        stateTracker = new ArrayList();
        size = stateTracker.size();

        do{       
            //choose move
            int[][] boardState = b.getBoard();

            int move = findBestMove(boardState);

            if (moves.size() < 1000){
                moves.add(move);           
                tracker.add(b.getNumberTiles());
                boolean[][] stateCopy = new boolean[4][4];

                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        if (boardState[i][j] > 0){
                            stateCopy[i][j] = true;
                        } else {
                            stateCopy[i][j] = false;
                        }

                    }
                }
                stateTracker.add(stateCopy);
            }

            move(b,move);         
        } while (!b.isGameOver());

        setScore(b.getPoints());
        filterData(5);
    }

    public void playGUI(){
        GameEngine g = new GameEngine();
        BoardGUI b = g.getBoard();

        Timer t = new Timer(500, new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        int move = findBestMove(b.getBoard());                       
                        g.aIPlay(move);
                        if (b.isGameOver()){
                            Timer t = (Timer) e.getSource();
                            t.stop();
                            playGUI();
                        }
                    }

                });
        t.start();
    }
    
    public void filterData(int size){
        for (int i = 0; i < moves.size();){
            if (!checkDecrease(i,size)){
                for (int j = 0; j < size; j++){
                    stateTracker.remove(i);
                    moves.remove(i);
                    tracker.remove(i);
                }
            } else {
                i++;
            }
        }
        this.size = stateTracker.size();
    }

    //true if states are the same, false otherwise
    public static boolean checkState(int[][] a, boolean[][] b){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if ((a[i][j] > 0 && !b[i][j]) || (a[i][j] == 0 && b[i][j])){
                    return false;
                }           
            }
        }
        return true;
    }

    public boolean checkDecrease(int index, int size){
        if (index+size >= tracker.size()) return true;

        if (tracker.get(index) - tracker.get(index+size) < 0){
            return true;
        } 

        return false;
    }

    public int findBestMove(int[][] boardState){
        int r = size;

        for (int i = 0; i < r; i++){
            if (checkState(boardState,stateTracker.get(i))){
                return moves.get(i);
            }
        }
        return (int)(Math.random()*4+1);
    }

}
