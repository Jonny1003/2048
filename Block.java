import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
public class Block{
    
    private int val;
    private BufferedImage img;
    
    public Block(int val){
        this(val,70);
    }

    public Block(int val, int rectSize){
        img = new BufferedImage(80,80,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        //add rectangle background to image
        Shape rect = new Rectangle(40-rectSize/2,40-rectSize/2, rectSize,rectSize);   
        g.draw(rect);

        if (val == 0){
            g.setColor(Color.LIGHT_GRAY);
            g.fill(rect);
        } else{  

            int variant = (int) Math.log(val);
            g.setColor(new Color(Math.max(255-variant*30,0),Math.min(255,variant*25),Math.min(variant*45,255)));
            g.fill(rect);

            //draw number
            g.setColor(Color.BLACK);
            g.setFont(new Font("Hiragino Kaku Gothic Pro", Font.PLAIN, 30));
            if (val < 10){                
                g.drawString(val+"",31,50);
            } else if (val < 100){
                g.drawString(val+"",20,50);
            } else if (val < 1000) {
                g.drawString(val+"",10,50);
            } else {
                g.setFont(new Font(null, Font.BOLD, 25));
                g.drawString(val+"",8,50);
            }
        }

        g.dispose();
        
        this.val = val;
    }
    
    public int getValue(){
        return val;
    }
    
    public BufferedImage getImage(){
        return img;
    }
}
