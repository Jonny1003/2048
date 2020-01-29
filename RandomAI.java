import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
public class RandomAI extends IAI{
    
    public RandomAI(){

        setScore(0);

    }

    public static void playGUI(){
        GameEngine g = new GameEngine();
        BoardGUI b = g.getBoard();

        Timer t = new Timer(500, new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        int move = makeRandomMove();                       
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

    
    public void playGame(){
        Board b = new Board();
        b.addRandomValue();
        b.addRandomValue();
        do{          
            int move = makeRandomMove();
            move(b,move);
            
        } while (!b.isGameOver());
        setScore(b.getPoints());
    }

}
