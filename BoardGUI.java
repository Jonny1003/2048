public class BoardGUI extends Board{

    private int[][][] sets;

    private int limit;

    public BoardGUI(){

        sets = new int[3][4][4];

        //0 = not moving, 1 = moving to empty,  >1 = combine, 
        resetMoveTracker();

        addRandomValue();
        addRandomValue();
      
    }
    
    public int[][][] getMoves(){
        return sets;
    }

    public void resetMoveTracker(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 4; k++){
                    sets[i][j][k] = 0;
                }
            }
        }
    }

    //simulates down swipe on board
    public void pushDown(){
        resetMoveTracker();
        noChange();
        for (int i = 0; i < 4; i++){
            limit = 3;
            //combine and shift values as needed
            for (int y = 3; y >= 0; y--){               
                //found block with value
                if (getValue(i,y) != 0){
                    down(i,y,0);
                }
            }
        }
    }

    public void down(int x, int y, int round){
        if (y < limit){
            if (getValue(x,y+1) == 0){
                setValue(x,y+1,getValue(x,y));
                setValue(x,y,0);

                sets[round][x][y] = 1;               

                down(x,y+1,round+1);
                change();
            } else if (getValue(x,y+1) == getValue(x,y)){
                setValue(x,y+1,getValue(x,y)*2);
                setValue(x,y,0);

                sets[round][x][y] = getValue(x,y+1);

                limit = y;
                change();
                
                addPoints(getValue(x,y+1));
            }     
        }        
    }

    public void pushUp(){
        resetMoveTracker();
        noChange();
        for (int i = 0; i < 4; i++){
            limit = 0;
            //combine and shift values as needed
            for (int y = 0; y < 4; y++){               
                //found block with value
                if (getValue(i,y) != 0){
                    up(i,y, 0);
                }
            }
        }
    }

    public void up(int x, int y, int round){
        if (y > limit){
            if (getValue(x,y-1) == 0){
                setValue(x,y-1,getValue(x,y));
                setValue(x,y,0);

                sets[round][x][y] = 1;

                up(x,y-1, round+1);
                change();
            } else if (getValue(x,y-1) == getValue(x,y)){
                setValue(x,y-1,getValue(x,y)*2);
                setValue(x,y,0);

                sets[round][x][y] = getValue(x,y+1);

                limit = y;
                change();
                
                addPoints(getValue(x,y-1));
            }       
        }

    }

    public void pushLeft(){
        resetMoveTracker();
        noChange();
        for (int y = 0; y < 4; y++){
            limit = 0;
            //combine and shift values as needed
            for (int x = 0; x < 4; x++){               
                //found block with value
                if (getValue(x,y) != 0){
                    left(x,y,0);
                } 
            }
        }
    }

    public void left(int x, int y, int round){
        if (x > limit){
            if (getValue(x-1,y) == 0){
                setValue(x-1,y,getValue(x,y));
                setValue(x,y,0);

                sets[round][x][y] = 1;

                left(x-1,y,round+1);
                change();
            } else if (getValue(x-1,y) == getValue(x,y)){
                setValue(x-1,y,getValue(x,y)*2);
                setValue(x,y,0);

                sets[round][x][y] = getValue(x-1,y);

                limit = x;
                change();
                
                addPoints(getValue(x-1,y));
            }              
        }      
    }

    public void pushRight(){
        resetMoveTracker();
        noChange();
        for (int y = 0; y < 4; y++){
            limit = 3;
            //combine and shift values as needed
            for (int x = 3; x >= 0; x--){               
                //found block with value
                if (getValue(x,y) != 0){
                    right(x,y,0);
                }
            }
        }
    }

    public void right(int x, int y, int round){
        if (x < limit){
            if (getValue(x+1,y) == 0){
                setValue(x+1,y,getValue(x,y));
                setValue(x,y,0);

                sets[round][x][y] = 1;

                right(x+1,y,round+1);

                change();
            } else if (getValue(x+1,y) == getValue(x,y)){
                setValue(x+1,y,getValue(x,y)*2);
                setValue(x,y,0);

                sets[round][x][y] = getValue(x+1,y);

                limit = x;
                change();
                
                addPoints(getValue(x+1,y));
            }     
        }

    }
}