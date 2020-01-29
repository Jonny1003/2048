import java.util.Scanner;
public class Terminal2048
{
    public static void main(String[] args){
        Board b = new Board();
        b.addRandomValue();
        b.addRandomValue();
        
        Scanner con = new Scanner(System.in);
        
        do{
            if (b.wasChanged()){
                b.addRandomValue();
            }            
            System.out.println(b);
            System.out.print("\nNext Move: ");
            String command = con.nextLine();
            if (command.equals("u")){
                b.pushUp();
            } else if (command.equals("d")){
                b.pushDown();
            } else if (command.equals("l")){
                b.pushLeft();
            } else if (command.equals("r")){
                b.pushRight();
            } else {
                b.noChange();
            }
        } while (!b.isGameOver());
           
        System.out.println(b);
        System.out.println("GAME OVER");
    }
}
