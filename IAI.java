

public abstract class IAI
{
    public static final int LEFT = 1, RIGHT = 2, UP = 3, DOWN = 4;
    
    private int score;
    
    public static int makeRandomMove(){
        return (int) (Math.random()*4)+1;
    }
    
        public int getScore(){
        return score;
    }

    public void setScore(int num){
        score = num;
    }
    
        public static void move(Board b, int move){
        if (move == UP){
            b.pushUp();
        } else if (move == DOWN){
            b.pushDown();
        } else if (move == LEFT){
            b.pushLeft();
        } else if (move == RIGHT){
            b.pushRight();
        } else {
            b.noChange();
        }
        if (b.wasChanged()){
            b.addRandomValue();
        }          
    }
    
}
