package DragonFight;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Zerbini&Zuech
 */

public abstract class View_Entity {
    
    protected int x,y;
    
    //Le coordinate dei bordi della finestra
    protected final int xMin, xMax, yMin, yMax;
  
    public View_Entity(int x, int y){
        this.x = x;
        this.y = y;

        this.xMin = 0;
        this.xMax = 900;
        this.yMin = 400;
        this.yMax = 5;
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
        
    public abstract Rectangle getBounds();
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
}
