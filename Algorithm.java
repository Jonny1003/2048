import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Algorithm extends IAI{

    public Algorithm(){
        setScore(0);
    }

    public void playGame(){
        Board b = new Board();
        b.addRandomValue();
        b.addRandomValue();
        do{         
            int move = findBestMove(b);
            if (move == -1){
                move = makeRandomMove();
            } 

            move(b,move);
        } while (!b.isGameOver());
        setScore(b.getPoints());
    }

    public static void playGUI(){
        GameEngine g = new GameEngine();
        BoardGUI b = g.getBoard();

        Timer t = new Timer(500, new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        int move = findBestMove(b);
                        if (move == -1){
                            move = makeRandomMove();
                        } 
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

    //if direction is -1, no best move is available
    public static int findBestMove(Board board){   
        //int direction = highOrderCheck(board);
        //if (direction != -1){
        //    return direction;
        //}
        int direction = findBestTwoCombo(board);
        if (direction != -1){
            return direction;
        }
        direction = findBestCombo(board);
        if (direction != -1){
            return direction;
        }
        direction = findBestPlay(board);
        if (direction != -1){
            return direction;
        }
        direction = findBestTwoPlays(board);
        if (direction != -1){
            return direction;              
        }   
        direction = playToEdge(direction, board); 
        return direction;
    }

    public static int highOrderCheck(Board board){
        int[][] b = board.getBoard();

        int max = b[0][0];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (b[i][j] > max){
                    max = b[i][j];
                }
            }
        }

        if (b[0][3] != max){
            System.out.println(true+" "+max);
            if (isMoveable(board, LEFT)){
                return LEFT;
            }            
        }
        return -1;
    }

    //if there are no optimal options, picks moves to corner
    public static int playToCorner(int direction){
        if (direction == -1 && Math.random() > 0.1){
            return (int) (Math.random()*2+2);
        } 
        return direction;
    }

    //if there are no optimal options, picks moves to bottom
    public static int playToEdge(int direction, Board b){
        if (direction != -1) {
            return direction;
        } else if (isMoveable(b,DOWN)){
            return DOWN;
        } else if (isMoveable(b,RIGHT) && isMoveable(b,LEFT)){
            return (int) (Math.random()*2+1);   
        } else if (isMoveable(b,LEFT)){
            return LEFT;
        } else if (isMoveable(b,RIGHT)){
            return RIGHT;
        }
        return UP;
    }
    
    public static int findBestTwoCombo(Board board){
        int direction = -1;
        //preference placed on going down       
        if (hasTwoCombo(board, DOWN)){
            return DOWN;
        } else if (hasTwoCombo(board,LEFT)){
            return LEFT;
        } else if (hasTwoCombo(board,RIGHT)){
            return RIGHT;
        } 
        return -1;
    }

    public static boolean hasTwoCombo(Board board, int direction){
        int[][] b = board.getBoard();
        int[][] copy = new int[4][4];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                copy[i][j] = b[i][j];
            }
        }

        int index = 0;
        while (!hasCombo(board, index/4, index%4, direction)){
            index++;
            if (index == 16) {
                return false;
            }
        }

        if (direction == DOWN){
            board.pushDown();
        } else if (direction == LEFT){
            board.pushLeft();
        } else if (direction == RIGHT){
            board.pushRight();
        } else if (direction == UP){
            board.pushUp();
        } 
        
        int move = findBestCombo(board);

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                b[i][j] = copy[i][j];
            }
        }
        
        if (move > 0){
            return true;
        }
        
        return false;
    }

    //finds move with most combination potential
    public static int findBestCombo(Board board){    
        int max = 0; 
        int direction = -1;
        int[][] b = board.getBoard();
        //preference placed on going down
        for (int i : new int[]{4,1,3}){
            int count = 0;
            //look for number of combos created
            for (int x = 0; x < 4; x++){
                for (int y = 0; y < 4; y++){
                    if (hasCombo(board,x,y,i)){
                        count+= board.getBoard()[x][y];
                    }                    
                }
            }

            if (count > max){
                max = count;
                direction = i;
            }
        }
        return direction;
    }

    //combination finder
    public static boolean hasCombo(Board board, int x, int y, int direction){      
        int[][] b = board.getBoard();

        if (b[x][y] == 0){
            return false;
        }

        if (direction == UP){
            for (int i = y-1; i >= 0; i--){
                if (b[x][i] == b[x][y]){
                    return true;
                } else if (b[x][i] != 0){
                    return false;
                }
            }
        } else if (direction == DOWN){
            for (int i = y+1; i < 4; i++){
                if (b[x][i] == b[x][y]){
                    return true;
                } else if (b[x][i] != 0){
                    return false;
                }
            }
        } else if (direction == LEFT){
            for (int i = x-1; i >= 0; i--){
                if (b[i][y] == b[x][y]){
                    return true;
                } else if (b[i][y] != 0){
                    return false;
                }
            }
        } else if (direction == RIGHT){
            for (int i = x+1; i <= 0; i++){
                if (b[i][y] == b[x][y]){
                    return true;
                } else if (b[i][y] != 0){
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean hasTwoPlays(Board board, int direction){
        int[][] b = board.getBoard();
        int[][] copy = new int[4][4];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                copy[i][j] = b[i][j];
            }
        }

        if (direction == DOWN){
            board.pushDown();
        } else if (direction == LEFT){
            board.pushLeft();
        } else if (direction == RIGHT){
            board.pushRight();
        } else if (direction == UP){
            board.pushUp();
        } 

        int dir = findBestCombo(board);

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                b[i][j] = copy[i][j];
            }
        }

        if (dir != -1){
            return true;
        } 
        return false;
    }

    public static int findBestTwoPlays(Board board){
        int direction = -1;
        //preference placed on going down       
        if (hasTwoPlays(board, DOWN)){
            return DOWN;
        } else if (hasTwoPlays(board,LEFT)){
            return LEFT;
        } else if (hasTwoPlays(board,RIGHT)){
            return RIGHT;
        } 
        return -1;
    }

    public static boolean hasPlay(Board board, int direction){
        int[][] b = board.getBoard();
        int[][] copy = new int[4][4];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                copy[i][j] = b[i][j];
            }
        }

        if (direction == DOWN){
            board.pushDown();
        } else if (direction == LEFT){
            board.pushLeft();
        } else if (direction == RIGHT){
            board.pushRight();
        } else if (direction == UP){
            board.pushUp();
        } 

        int dir = findBestCombo(board);

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                b[i][j] = copy[i][j];
            }
        }
        if (dir != -1){
            return true;
        } 
        return false;
    }

    public static int findBestPlay(Board board){
        int max = 0; 
        int direction = -1;
        //preference placed on going down
        ArrayList<Integer> a = new ArrayList();          
        if (hasPlay(board, DOWN)){
            return DOWN;
        } else if (hasPlay(board,LEFT)){
            return LEFT;
        } else if (hasPlay(board,RIGHT)){
            return RIGHT;
        } 
        return -1;
    }

    //checks for moveability of the direction
    public static boolean isMoveable(Board board, int direction){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (checkMoveable(board,i,j,direction)) return true;
            }
        }
        return false;
    }

    public static boolean checkMoveable(Board board, int x, int y, int direction){
        int[][] b = board.getBoard();
        if (b[x][y] == 0){
            return false;
        }
        if (direction == UP){
            for (int i = y-1; i >= 0; i--){
                if (b[x][i] == 0){
                    return true;
                } 
            }
        } else if (direction == DOWN){
            for (int i = y+1; i < 4; i++){
                if (b[x][i] == 0){
                    return true;
                } 
            }           
        } else if (direction == RIGHT){
            for (int i = x+1; i < 4; i++){
                if (b[i][y] == 0){
                    return true;
                } 
            }            
        } else if (direction == LEFT){
            for (int i = x-1; i >= 0; i--){
                if (b[i][y] == 0){
                    return true;
                } 
            }          
        }
        return false;
    }
}