import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;

public class GraphicsBoard extends JFrame{

    private JLabel screen;
    private Block[][] board;
    private BufferedImage finalImage;
    private final Block emptyBlock = new Block(0);
    
    private JLabel currentScore;
    private JLabel highScore;

    public GraphicsBoard(BoardGUI g){
        super("2048");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        board = new Block[4][4];

        JPanel p = new JPanel(new BorderLayout(1,1));
        screen = new JLabel();
        p.add(screen,BorderLayout.PAGE_START);
        
        //create current score and high score labels       
        currentScore = new JLabel();  
        final BufferedImage img = new BufferedImage(160,40,BufferedImage.TYPE_INT_RGB);
        Graphics2D graph= img.createGraphics();
        
        Rectangle rect1 = new Rectangle(160,40);
        graph.draw(rect1);
        graph.setColor(Color.CYAN);
        graph.fill(rect1);  
        Rectangle rect2 = new Rectangle(10,5,140,30);
        graph.draw(rect2);
        graph.setColor(Color.WHITE);
        graph.fill(rect2); 
        graph.dispose();
        
        currentScore.setIcon(new ImageIcon(img));
        currentScore.setHorizontalTextPosition(JLabel.CENTER);
        currentScore.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        
        updateCurrentScore(0);
        
        
        highScore = new JLabel();
        final BufferedImage img2 = new BufferedImage(160,40,BufferedImage.TYPE_INT_RGB);
        graph= img2.createGraphics();
        
        graph.draw(rect1);
        graph.setColor(Color.CYAN);
        graph.fill(rect1);        
        graph.draw(rect2);
        graph.setColor(Color.WHITE);
        graph.fill(rect2); 
        graph.dispose();
        
        highScore.setIcon(new ImageIcon(img2));
        highScore.setHorizontalTextPosition(JLabel.CENTER);
        highScore.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        
        updateHighScore(0);
        
        
        p.add(currentScore, BorderLayout.LINE_START);
        p.add(highScore, BorderLayout.LINE_END);
        
        add(p);     
    }

    /**
     * update fields of graphics board:
     **/
    //update display image based on current board field in GraphicsBoard
    public void updateFinalImage(){
        final BufferedImage img = new BufferedImage(320,320,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                g.drawImage(board[x][y].getImage(),x*80,y*80,null);
            }
        }
        g.dispose();
        finalImage = img;
        display();
    }

    //update elements in board at coordinates x and y with specific number
    public void updateBoard(int x, int y, int num){
        board[x][y] = new Block(num);
    }

    public void display(){
        screen.setIcon(new ImageIcon(finalImage));
    }

    /**
     * Update this board with most recent movement coordinates
     */
    public void downUpdate(int[][] coordinates){
        for (int i = 0; i < 4; i++){
            for (int j = 3; j >= 0; j--){
                if (coordinates[i][j] == 1){
                    updateBoard(i,j+1,board[i][j].getValue());
                    updateBoard(i,j,0);
                } else if (coordinates[i][j] > 1){
                    updateBoard(i,j+1,board[i][j].getValue()*2);
                    updateBoard(i,j,0);
                }                
            }
        }
    }

    public void upUpdate(int[][] coordinates){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (coordinates[i][j] == 1){
                    updateBoard(i,j-1,board[i][j].getValue());
                    updateBoard(i,j,0);
                } else if (coordinates[i][j] > 1){
                    updateBoard(i,j-1,board[i][j].getValue()*2);
                    updateBoard(i,j,0);
                }                
            }
        }
    }

    public void leftUpdate(int[][] coordinates){
        for (int j = 3; j >= 0; j--){
            for (int i = 0; i < 4; i++){            
                if (coordinates[i][j] == 1){
                    updateBoard(i-1,j,board[i][j].getValue());
                    updateBoard(i,j,0);
                } else if (coordinates[i][j] > 1){
                    updateBoard(i-1,j,board[i][j].getValue()*2);
                    updateBoard(i,j,0);
                }                
            }
        }
    }

    public void rightUpdate(int[][] coordinates){
        for (int j = 3; j >= 0; j--){
            for (int i = 3; i >= 0; i--){            
                if (coordinates[i][j] == 1){
                    updateBoard(i+1,j,board[i][j].getValue());
                    updateBoard(i,j,0);
                } else if (coordinates[i][j] > 1){
                    updateBoard(i+1,j,board[i][j].getValue()*2);
                    updateBoard(i,j,0);
                }                
            }
        }
    }

    /**
     * Update screen with most recent image based on coordinates 
     * and movement updates 
     */
    public void drawMove(int[][] coordinates, int xLength, int yLength){
        final BufferedImage img = new BufferedImage(320,320,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        //layer 1: nonmoving blocks        
        for (int x = 0;  x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (coordinates[x][y] == 0){
                    g.drawImage(board[x][y].getImage(),80*x,80*y,null);                    
                } else{
                    g.drawImage(emptyBlock.getImage(),80*x, 80*y, null);
                }
            }
        }

        //layer 2: moving blocks
        for (int x = 0;  x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (coordinates[x][y] != 0){
                    g.drawImage(board[x][y].getImage(),80*x+xLength,80*y+yLength,null);                    
                } 
            }
        }

        g.dispose();

        finalImage = img;
        display();
    }

    public void drawCombines(int[][] coordinates, int xLength, int yLength, int dilation){
        Graphics2D g = finalImage.createGraphics();
        for (int x = 0;  x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (coordinates[x][y] > 1){
                    int newNumber = coordinates[x][y];
                    g.drawImage((new Block(newNumber,dilation)).getImage(),80*x+xLength,80*y+yLength,null);                                   
                } 
            }
        }
        g.dispose();
        display();
    }

    public void drawNewValue(int x, int y, int val, int dilation){
        Graphics2D g = finalImage.createGraphics();
        g.drawImage((new Block(val,dilation)).getImage(),80*x,80*y,null);   
        g.dispose();
        display();
    }

    public void drawPlayAgain(){
        Graphics2D g = finalImage.createGraphics();
        Font f = new Font("TIMES NEW ROMAN", Font.BOLD, 30);
        g.setFont(f);
        g.setColor(Color.YELLOW);
        g.drawString("PRESS ENTER" ,40,60);
        g.drawString("TO PLAY AGAIN",40,90);
        g.dispose();
        display();
    }
    
    public void updateCurrentScore(int score){      
        currentScore.setText("SCORE: "+score);
    }
    
    public void updateHighScore(int score){
        highScore.setText("HIGH SCORE: "+score);
    }
    
    

}
