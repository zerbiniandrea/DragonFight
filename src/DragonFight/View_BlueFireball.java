package DragonFight;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 
 * @author Zerbini&Zuech
 */

public class View_BlueFireball extends View_Entity{

    private int xFireballMoving;
    private static BufferedImage[] fireballMoving = new BufferedImage[4];
    private static BufferedImage[] fireballExploding = new BufferedImage[3];
    private boolean fired,hit;
    private int direction,dimension;
    
    public View_BlueFireball(BufferedImage spriteSheet, int direction, int x, int y) {
        super(x, y);
        this.hit = false;
        this.fired = true;
        this.xFireballMoving = 0;
        this.direction=direction;
        this.dimension=60;
        
        //Carica l'immagine della palla di fuoco in movimento
        int z=0;
        for(int tmpX=0;tmpX<4;tmpX++){
            fireballMoving[z]=spriteSheet.getSubimage(tmpX*dimension,0,dimension,dimension);
            z++;
        }
        
        //Carica l'immagine dellapalla di fuoco mentre esplode
        z=0;
        for(int tmpX=0;tmpX<3;tmpX++){
            fireballExploding[z]=spriteSheet.getSubimage(tmpX*dimension,dimension,dimension,dimension);
            z++;
        }
    }

    @Override
    public void tick() {
            x+=8*direction;
    }

    @Override
    public void render(Graphics g) {
        tick();
        if(!hit){
            if(x<xMax && x>xMin){
                g.drawImage(fireballMoving[xFireballMoving], (int)x, (int)y, dimension, dimension, null);
            }else{
                for(int i=0;i<3;i++){
                    g.drawImage(fireballExploding[i], (int)x, (int)y, dimension, dimension, null);
                }
                fired=false;
            }
        }else{
            for(int i=0;i<3;i++)
                g.drawImage(fireballExploding[i], (int)x, (int)y, dimension, dimension, null);

            fired=false;  
        }
        
        xFireballMoving++;
        if(xFireballMoving==4)
            xFireballMoving=0;
    }
 
    @Override
    public Rectangle getBounds() {
        return (new Rectangle(x-(15*direction),y,dimension,dimension));
    }
    
    public boolean getFired(){
        return fired;
    }

    
    public void setX(int f){
        x=f;
    }
    
    public void setY(int f){
        y=f;
    }
    
    public void fireballHit(){
        hit=true;
    }
        
    public boolean getHit(){
        return hit;
    }
}
