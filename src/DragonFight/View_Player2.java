package DragonFight;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 
 * @author Zerbini&Zuech
 */

public class View_Player2 extends View_Entity{
    
    private int xPlayerMoving,xPlayerFalling,xPlayerFlying,direction,directionDelta;
    private boolean firing;
    private int dimension;
    private int health;
    
    //Le immagini del giocatore
    public static BufferedImage playerStanding;
    public static BufferedImage[] playerMoving = new BufferedImage[8];
    public static BufferedImage playerJumping;
    public static BufferedImage[] playerFalling = new BufferedImage[2];
    public static BufferedImage[] playerFlying = new BufferedImage[4];
    public static BufferedImage playerFiring;
    
    private Controller_KeyManagerPlayer1 pKeyManager;
    
    public View_Player2(Controller_KeyManagerPlayer1 pKeyManager, BufferedImage spriteSheet, int x, int y) {
        super(x, y);
        this.health = 3;
        this.dimension = 90;
        this.firing = false;
        this.directionDelta = dimension;
        this.direction = -1;
        this.xPlayerFlying = 0;
        this.xPlayerFalling = 0;
        this.xPlayerMoving = 0;

        this.pKeyManager=pKeyManager;
        
        BufferedImage tmpPlayer=spriteSheet;
        
        //Carica l'immagine del personaggio mentre resta fermo
        playerStanding=spriteSheet.getSubimage(0,0,dimension,dimension);
        
        //Carica le immagini del personaggio mentre cammina
        int z=0;
        for(int tmpX=0;tmpX<8;tmpX++){
            playerMoving[z]=spriteSheet.getSubimage(tmpX*dimension,dimension,dimension,dimension);
            z++;
        }
        
        //Carica l'immagine del personaggio mentre salta
        playerJumping=spriteSheet.getSubimage(0,dimension*2,dimension,dimension);
        
        //Carica le immagini del personaggio mentre cade
        z=0;
        for(int tmpX=0;tmpX<2;tmpX++){
            playerFalling[z]=spriteSheet.getSubimage(tmpX*dimension,dimension*3,dimension,dimension);
            z++;
        }
        
        //Carica le immagini del personaggio mentre vola
        z=0;
        for(int tmpX=0;tmpX<4;tmpX++){
            playerFlying[z]=spriteSheet.getSubimage(tmpX*dimension,dimension*4,dimension,dimension);
            z++;
        }
        
        //Carica l'immagine del personaggio mentre spara
        playerFiring=spriteSheet.getSubimage(dimension,dimension*5,dimension,dimension);
    }

    @Override
    public void tick() {
        if(pKeyManager.w&&y>yMax)     
            y -= 4;
        
        if(pKeyManager.s&&y<yMin)     
            y += 4;
        
        if(pKeyManager.a&&x>xMin)     
            x -= 4;
        
        if(pKeyManager.d&&x<xMax)     
            x += 4;    
    }

    @Override
    public void render(Graphics g) {
        tick();
        
        if(pKeyManager.a){
            direction=-1;
            directionDelta=dimension;
        }
        
        if(pKeyManager.d){
            direction=1;
            directionDelta=0;
        }
        
        //PLAYER FIRING
        if(pKeyManager.v){
            g.drawImage(playerFiring, (int)x+directionDelta, (int)y, dimension*direction, dimension, null);
            firing=true;
        }
        //PLAYER JUMPING
        else if(pKeyManager.w&&!pKeyManager.d&&!pKeyManager.a){
            g.drawImage(playerJumping, (int)x+directionDelta, (int)y, dimension*direction, dimension, null);
            firing=false;
        }
        //PLAYER FALLING
        else if(pKeyManager.s){
            g.drawImage(playerFalling[xPlayerFalling], (int)x+directionDelta, (int)y, dimension*direction, dimension, null);
            
            xPlayerFalling++;
            if(xPlayerFalling==2)
                xPlayerFalling=0;
            firing=false;
        }
        //PLAYER FLYING
        else if(pKeyManager.w&&pKeyManager.d||pKeyManager.w&&pKeyManager.a){
            g.drawImage(playerFlying[xPlayerFlying], (int)x+directionDelta, (int)y, dimension*direction, dimension, null);
            
            xPlayerFlying++;
            if(xPlayerFlying==4)
                xPlayerFlying=0;
            firing=false;
        }
        //PLAYER MOVING
        else if(pKeyManager.d&&!pKeyManager.w&&!pKeyManager.s||pKeyManager.a&&!pKeyManager.w&&!pKeyManager.s){
            g.drawImage(playerMoving[xPlayerMoving], (int)x+directionDelta, (int)y, dimension*direction, dimension, null);
            
            xPlayerMoving++;
            if(xPlayerMoving==8)
                xPlayerMoving=0;
            firing=false;
        }
        //PLAYER STANDING
        else{
            g.drawImage(playerStanding, (int)x+directionDelta, (int)y, dimension*direction, dimension, null);
            firing=false;
        }
    }
    
    @Override
    public Rectangle getBounds() {
        return (new Rectangle(x,y,dimension,dimension));
    }
    
    public boolean getFiring(){
        return firing;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public int getDirectionDelta(){
        return directionDelta;
    }
    
    public int getHealth(){
        return health;
    }
    
    public void hit(){
        health--;
    }
}
