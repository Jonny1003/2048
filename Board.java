public class Board
{
    private int[][] board;
    private int points;
    private boolean markedChange;
    private int limit;

    public Board(){
        board = new int[4][4];
        for (int[] c: board){
            for (int i = 0; i < 4; i++){
                c[i] = 0;
            }
        }
        points = 0;
        markedChange = false;
        
    }

    public int[][] getBoard(){
        return board;
    }
    
    public int getNumberTiles(){
        int count = 0;
        for (int[] c: board){
            for (int i : c){
                if (i != 0) count++;
            }
        }
        return count;
    }
    
    public void change(){
        markedChange = true;
    }
    
    public boolean wasChanged(){
        return markedChange;
    }
    
    public void noChange(){
        markedChange = false;
    }

    public void setValue(int x, int y, int val){
        board[x][y] = val;
    }

    public int getValue(int x, int y){
        if (x < 0 || x > 3 || y < 0 || y > 3){
            return -1;
        }
        return board[x][y];
    }

    public int getPoints(){
        return points;
    }
    
    public void addPoints(int pts){
        points += pts;
    }

    //simulates down swipe on board
    public void pushDown(){
        markedChange = false;
        for (int i = 0; i < 4; i++){
            limit = 3;
            //combine and shift values as needed
            for (int y = 3; y >= 0; y--){               
                //found block with value
                if (getValue(i,y) != 0){
                    down(i,y);
                }
            }
        }
    }

    public void down(int x, int y){
        if (y < limit){
            if (getValue(x,y+1) == 0){
                setValue(x,y+1,getValue(x,y));
                setValue(x,y,0);
                down(x,y+1);
                markedChange = true;
            } else if (getValue(x,y+1) == getValue(x,y)){
                setValue(x,y+1,getValue(x,y)*2);
                setValue(x,y,0);
                limit = y;
                markedChange = true;
                
                points += getValue(x,y+1);               
            }    
        }
    }

    public void pushUp(){
        markedChange = false;
        for (int i = 0; i < 4; i++){
            limit = 0;
            //combine and shift values as needed
            for (int y = 0; y < 4; y++){               
                //found block with value
                if (getValue(i,y) != 0){
                    up(i,y);
                }
            }
        }
    }

    public void up(int x, int y){
        if (y > limit){
            if (getValue(x,y-1) == 0){
                setValue(x,y-1,getValue(x,y));
                setValue(x,y,0);
                up(x,y-1);
                markedChange = true;
            } else if (getValue(x,y-1) == getValue(x,y)){
                setValue(x,y-1,getValue(x,y)*2);
                setValue(x,y,0);
                limit = y;
                markedChange = true;
                
                points += getValue(x,y-1);
            }         
        }
    }

    public void pushLeft(){
        markedChange = false;
        for (int y = 0; y < 4; y++){
            limit = 0;
            //combine and shift values as needed
            for (int x = 0; x < 4; x++){               
                //found block with value
                if (getValue(x,y) != 0){
                    left(x,y);
                }
            }
        }
    }

    public void left(int x, int y){
        if (x > limit){
            if (getValue(x-1,y) == 0){
                setValue(x-1,y,getValue(x,y));
                setValue(x,y,0);
                left(x-1,y);
                markedChange = true;
            } else if (getValue(x-1,y) == getValue(x,y)){
                setValue(x-1,y,getValue(x,y)*2);
                setValue(x,y,0);
                limit = x;
                markedChange = true;
                
                points += getValue(x-1,y);
            }      
        }
    }
    
    public void pushRight(){
        markedChange = false;
        for (int y = 0; y < 4; y++){
            limit = 3;
            //combine and shift values as needed
            for (int x = 3; x >= 0; x--){               
                //found block with value
                if (getValue(x,y) != 0){
                    right(x,y);
                }
            }
        }
    }

    public void right(int x, int y){
        if (x < limit){
            if (getValue(x+1,y) == 0){
                setValue(x+1,y,getValue(x,y));
                setValue(x,y,0);
                right(x+1,y);
                markedChange = true;
            } else if (getValue(x+1,y) == getValue(x,y)){
                setValue(x+1,y,getValue(x,y)*2);
                setValue(x,y,0);
                limit = x;
                markedChange = true;
                
                points += getValue(x+1,y);
            }      
        }
    }

    //add random new block to board
    public int addRandomValue(){
        int count = 0;
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                if (getValue(x,y) == 0){
                    count++;
                }
            }
        }
        int pick = (int) (Math.random()*count)+1;

        count = 0;
        int index = -1;
        while (count != pick){
            index++;
            if (getValue(index/4,index%4) == 0) count++;           
        }  

        int num = (int)(Math.random()*2+1) * 2;
        setValue(index/4,index%4,num);
        noChange();
        return index;
    }

    public boolean isGameOver(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (getValue(i,j) != 0){
                    if (getValue(i-1,j) == getValue(i,j) || getValue(i+1,j) == getValue(i,j)
                    || getValue(i,j-1) == getValue(i,j) || getValue(i,j+1) == getValue(i,j)){
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString(){
        String out = "";
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                out += getValue(j,i)+"\t";
            }
            out += "\n";
        }
        return out;
    }
}
