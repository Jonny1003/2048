import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class GameEngine implements KeyListener{

    private boolean isPressed;
    private GraphicsBoard g;
    private BoardGUI b;
    
    private int highScore;

    public GameEngine(){
        initialize(); 
        highScore = 0;
    }

    public void initialize(){
        b = new BoardGUI();
        g = new GraphicsBoard(b);
        g.updateHighScore(highScore);
        g.addKeyListener(this);

        updateBoard();

        g.pack();
        g.setFocusable(true);
        g.setVisible(true); 
    }
    
    public void togglePressable(){
        isPressed = !isPressed;
    }

    public void nextTurn(){
        updateBoard();
        g.updateCurrentScore(b.getPoints());
        if (b.wasChanged()){
            addRandomValue();
        } else {
            g.display();
            togglePressable();
        }
    }

    public void addRandomValue(){
        int index = b.addRandomValue();
        int x = index/4, y = index%4;
        animateNewValue(x,y);
    }

    public void updateBoard(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                g.updateBoard(i,j,b.getBoard()[i][j]);
            }
        }
        g.updateFinalImage();
    }
    
    public void aIPlay(int val){
        if (!isPressed){
            if (!b.isGameOver()){
                togglePressable();
                if (IAI.UP == val){
                    b.pushUp();
                    up(b.getMoves(),0);
                } else if (IAI.DOWN == val){
                    b.pushDown();
                    down(b.getMoves(),0);
                } else if (IAI.LEFT == val){
                    b.pushLeft();
                    left(b.getMoves(),0);
                } else if (IAI.RIGHT == val){
                    b.pushRight();
                    right(b.getMoves(),0);
                } else {
                    togglePressable();
                }              
            } else {
                System.out.println("GAME OVER");                
            }
        }
    }

    //key bindings
    @Override
    public void keyPressed(KeyEvent e){
        if (!isPressed){
            if (!b.isGameOver()){
                togglePressable();
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    b.pushUp();
                    up(b.getMoves(),0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    b.pushDown();
                    down(b.getMoves(),0);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT){
                    b.pushLeft();
                    left(b.getMoves(),0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    b.pushRight();
                    right(b.getMoves(),0);
                } else {
                    togglePressable();
                }              
            } else {
                System.out.println("GAME OVER");
                g.drawPlayAgain();
                if (e.getKeyCode() == KeyEvent.VK_ENTER){                  
                    g.removeKeyListener(this);
                    g.dispose();
                    if (b.getPoints() > highScore){
                        highScore = b.getPoints();
                    }
                    initialize();                    
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
    }

    @Override
    public void keyTyped(KeyEvent e){
    }
    

    //movement methods for engine
    public void down(int[][][] coordinates, int iteration){
        //movement step
        Timer t1 = new Timer(1,new ActionListener(){

                    private int count = 0;

                    @Override
                    public void actionPerformed(ActionEvent e){
                        g.drawMove(coordinates[iteration],0,count *4);
                        count++;
                        if (count == 21){
                            Timer t = (Timer) e.getSource();
                            t.stop();
                            //combination step
                            Timer t2 = new Timer(1,new ActionListener(){

                                        private int count = 0;

                                        @Override
                                        public void actionPerformed(ActionEvent e){
                                            g.drawCombines(coordinates[iteration],0,80,80-count);
                                            count++;
                                            if (count == 10){
                                                Timer t = (Timer) e.getSource();
                                                t.stop();
                                                g.downUpdate(coordinates[iteration]);
                                                g.updateFinalImage();
                                                if (iteration < coordinates.length-1){
                                                    down(coordinates,iteration+1);
                                                } else {
                                                    nextTurn();
                                                }
                                            }
                                        }
                                    });
                            t2.start();
                        }
                    }
                });
        t1.start();
    }

    public void up(int[][][] coordinates,int it){
        //movement step
        Timer t1 = new Timer(1,new ActionListener(){

                    private int count = 0;

                    @Override
                    public void actionPerformed(ActionEvent e){
                        g.drawMove(coordinates[it],0,-count *4);
                        count++;
                        if (count == 21){
                            Timer t = (Timer) e.getSource();
                            t.stop();
                            //combination step
                            Timer t2 = new Timer(1,new ActionListener(){

                                        private int count = 0;

                                        @Override
                                        public void actionPerformed(ActionEvent e){
                                            g.drawCombines(coordinates[it],0,-80,80-count);
                                            count++;
                                            if (count == 10){
                                                Timer t = (Timer) e.getSource();
                                                t.stop();
                                                g.upUpdate(coordinates[it]);
                                                g.updateFinalImage();
                                                if (it < coordinates.length-1){
                                                    up(coordinates,it+1);
                                                }
                                                else {
                                                    nextTurn();
                                                }
                                            }
                                        }
                                    });
                            t2.start();
                        }
                    }
                });
        t1.start();
    }

    public void left(int[][][] coordinates, int it){
        //movement step
        Timer t1 = new Timer(1,new ActionListener(){

                    private int count = 0;

                    @Override
                    public void actionPerformed(ActionEvent e){
                        g.drawMove(coordinates[it],-count*4,0);
                        count++;
                        if (count == 21){
                            Timer t = (Timer) e.getSource();
                            t.stop();
                            //combination step
                            Timer t2 = new Timer(1,new ActionListener(){

                                        private int count = 0;

                                        @Override
                                        public void actionPerformed(ActionEvent e){
                                            g.drawCombines(coordinates[it],-80,0,80-count);
                                            count++;
                                            if (count == 10){
                                                Timer t = (Timer) e.getSource();
                                                t.stop();
                                                g.leftUpdate(coordinates[it]);
                                                g.updateFinalImage();
                                                if (it < coordinates.length-1){
                                                    left(coordinates,it+1);
                                                } else {
                                                    nextTurn();
                                                }
                                            }
                                        }
                                    });
                            t2.start();
                        }
                    }
                });
        t1.start();

    }

    public void right(int[][][] coordinates, int it){
        //movement step
        Timer t1 = new Timer(1,new ActionListener(){

                    private int count = 0;

                    @Override
                    public void actionPerformed(ActionEvent e){
                        g.drawMove(coordinates[it],count*4,0);
                        count++;
                        if (count == 21){
                            Timer t = (Timer) e.getSource();
                            t.stop();
                            //combination step
                            Timer t2 = new Timer(1,new ActionListener(){

                                        private int count = 0;

                                        @Override
                                        public void actionPerformed(ActionEvent e){
                                            g.drawCombines(coordinates[it],80,0,80-count);
                                            count++;
                                            if (count == 10){
                                                Timer t = (Timer) e.getSource();
                                                t.stop();
                                                g.rightUpdate(coordinates[it]);                       
                                                g.updateFinalImage();
                                                if (it < coordinates.length-1){
                                                    right(coordinates,it+1);                                                   
                                                } else {
                                                    nextTurn();
                                                }
                                            }
                                        }
                                    });
                            t2.start();
                        }
                    }
                });
        t1.start();
    }

    public void animateNewValue(int x, int y){
        Timer t3 = new Timer(8,new ActionListener(){

                    private int count = 0;

                    @Override
                    public void actionPerformed(ActionEvent e){
                        g.drawNewValue(x,y,b.getValue(x,y),80-count);
                        count++;
                        if (count == 10){
                            Timer t = (Timer) e.getSource();
                            t.stop();
                            updateBoard(); 
                            g.display();
                            togglePressable();
                        }
                    }
                });
        t3.start();
    }
    
    public GraphicsBoard getGraphicsBoard(){
        return g;
    }
    
    public BoardGUI getBoard(){
        return b;
    }
}
