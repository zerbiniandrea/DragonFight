package DragonFight;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 *
 * @author Zerbini&Zuech
 */

public class ApplicationView extends View {  
    
    //Graphics
    private JFrame frame;
    private Canvas canvas;
    private Graphics g;
    private BufferStrategy bs;
    
    
    //Images
    private final BufferedImage background;
    private final BufferedImage fireballPlayer1,fireballPlayer2;
    private final BufferedImage heart;
    private final BufferedImage winner1, winner2;
    private final BufferedImage keys1, keys2;
    
    private View_Player1 player1;
    private View_Player2 player2;
    private View_YellowFireball fireball1;
    private View_BlueFireball fireball2;
    private Controller_KeyManagerPlayer2 keyManagerPlayer1;
    private Controller_KeyManagerPlayer1 keyManagerPlayer2;
    private Controller_KeyManagerEnd keyManagerEnd;
    private int winner;
    private float timeElapsed;
    private boolean finish;
    
    public ApplicationView(ApplicationModel m){
        super(m);
        this.keyManagerEnd = new Controller_KeyManagerEnd();
        this.finish = false;
        this.timeElapsed = System.nanoTime();
        this.winner = 0;
        this.fireballPlayer2 = ((ApplicationModel)m).loadImage("/textures/fireball_redPlayer.gif");
        this.fireballPlayer1 = ((ApplicationModel)m).loadImage("/textures/fireball_greenPlayer.gif");
        this.heart = ((ApplicationModel)m).loadImage("/textures/heart2.png");
        this.winner1=((ApplicationModel)m).loadImage("/textures/Player1_winner.png");
        this.winner2=((ApplicationModel)m).loadImage("/textures/Player2_winner.png");
        this.keys1=((ApplicationModel)m).loadImage("/textures/Player1_keybuttons.png");
        this.keys2=((ApplicationModel)m).loadImage("/textures/Player2_keybuttons.png");
        this.keyManagerPlayer1 = new Controller_KeyManagerPlayer2();
        this.keyManagerPlayer2 = new Controller_KeyManagerPlayer1();
        
        BufferedImage redPlayer = ((ApplicationModel)m).loadImage("/textures/redPlayer.gif");
        BufferedImage greenPlayer = ((ApplicationModel)m).loadImage("/textures/greenPlayer.gif");
        
        //Carica l'immagine dello sfondo
        background=((ApplicationModel)m).loadImage("/textures/stages/stage1.png");
        
        //Crea un nuovo giocatore
        player1 = new View_Player1(keyManagerPlayer1, greenPlayer, 0, 175);
        player2 = new View_Player2(keyManagerPlayer2, redPlayer, 900, 175);
    }
    
    //Inizializza la finestra di gioco
    public void display(String title, int width, int height){
        frame=new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quando chiudo la finestra termina anche l'esecuzione
        frame.setResizable(false); // La finestra non può essere modificata dall'utente
        frame.setLocationRelativeTo(null); // Finestra al centro
        frame.setVisible(true);
        
        canvas=new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        
        frame.add(canvas);
        frame.pack();
        
        frame.addKeyListener(keyManagerPlayer1);
        frame.addKeyListener(keyManagerPlayer2);
        
        frame.setCursor(frame.getToolkit().createCustomCursor(
            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null")
        );
    }
    
    //Disegna nella finestra
    public void render(){      
        bs=canvas.getBufferStrategy();
        if(bs==null){
            canvas.createBufferStrategy(3);
            return;
        }
        g=bs.getDrawGraphics();
        
        /////////// CLEAR SCREEN ///////////
            
        clearScreen();
        
        /////////// DRAW HERE ///////////
       
        g.drawImage(background, 0, 0, null);
        
        if((System.nanoTime()-timeElapsed)/1000000000<10){
            g.drawImage(keys1, 0, 0, 265, 135, null);
            g.drawImage(keys2, ((ApplicationModel)m).getWidth()-270, 0, 265, 135, null);
        }
        if(winner==0){
            keyManagerPlayer1.tick();
            keyManagerPlayer2.tick();

            //Player 1 Health
            for(int i=0;i<player1.getHealth();i++){
                g.drawImage(heart, 60*i, ((ApplicationModel)m).getHeight()-60, null);
            }

            //Player 2 Health
            for(int i=1;i<player2.getHealth()+1;i++){
                g.drawImage(heart, ((ApplicationModel)m).getWidth()-(60*i), ((ApplicationModel)m).getHeight()-60, null);
            }

            ////// FIREBALLS //////

            //Fireball 1
            if(player1.getFiring()){
                if(fireball1==null)
                    fireball1 = createYellowFireball(player1.getDirection(), player1.getX()+15,player1.getY()+20);
            }

            if(fireball1!=null){
                 if(fireball1.getFired())
                        fireball1.render(g);
                    else
                        fireball1=null;
            }

            //Fireball 2
            if(player2.getFiring()){
                if(fireball2==null)
                    fireball2 = createBlueFireball(player2.getDirection(), player2.getX()+15,player2.getY()+20);
            }

            if(fireball2!=null){
                 if(fireball2.getFired())
                        fireball2.render(g);
                    else
                        fireball2=null;
            }

            ////// PLAYERS //////
            player1.render(g);
            player2.render(g);

            ////////// COLLISION DETECTION //////////

            int collision = checkCollision();
            int fireballCollision = checkFireballCollision();
            
            if(fireball1 != null && fireball2 != null){
                if(fireballCollision==1){
                    fireball1.fireballHit();
                    fireball2.fireballHit();
                }
            }
            
            if(collision==1){
                if(!fireball1.getHit()){
                    player2.hit();
                    fireball1.fireballHit();
                }
            }else if(collision==2){
                if(!fireball2.getHit()){
                    player1.hit();
                    fireball2.fireballHit();
                }
            }else if(collision==0){
                if(fireball1!=null)
                    fireball1.fireballHit();
                
                if(fireball2!=null)
                    fireball2.fireballHit();
            }

            if(player1.getHealth()==0)
                winner=2;
            else if(player2.getHealth()==0)
                winner=1;

        }else if(winner==1){
            g.drawImage(winner1, 0, 0, null);
            if(!finish)
                ((ApplicationController)c).winner();
            
            finish=true;
            
            frame.addKeyListener(keyManagerEnd);
        }else if(winner==2){
            g.drawImage(winner2, 0, 0, null);
            if(!finish)
                ((ApplicationController)c).winner();
            
            finish=true;
            
            frame.addKeyListener(keyManagerEnd);
        }

        ////////// END DRAWING //////////
        bs.show();
        g.dispose();
    }
    
    //Cancella tutto ciò che è stato disegnato sullo schermo
    public void clearScreen(){
        g.clearRect(0,0,((ApplicationModel)m).getWidth(),((ApplicationModel)m).getHeight());
    }
    
    //Disegna un'immagine
    public void drawPicture(BufferedImage img, int x, int y, ImageObserver io){
        g.drawImage(img,x,y,io);
    }
    
    //Crea una nuova palla di fuoco gialla
    public View_YellowFireball createYellowFireball(int direction, int x, int y){
        return new View_YellowFireball(fireballPlayer1, direction, x, y);
    }
    
    //Crea una nuova palla di fuoco blu
    public View_BlueFireball createBlueFireball(int direction, int x, int y){
        return new View_BlueFireball(fireballPlayer2, direction, x, y);
    }
    
    public int checkCollision(){
        Rectangle p1Rect = player1.getBounds();
        Rectangle p2Rect = player2.getBounds();
        Rectangle f1Rect = null, f2Rect = null;
        
                
        if(p1Rect.intersects(p2Rect))
            return 0;
        
        if(fireball1 != null)
            f1Rect = fireball1.getBounds();
        
        if(fireball2 != null)
            f2Rect = fireball2.getBounds();
        
        if(f1Rect != null){
            if(f1Rect.intersects(p2Rect))
                return 1;
        }
        if(f2Rect != null){
            if(f2Rect.intersects(p1Rect))
                return 2;
        }

        return -1;
    }
    
    public int checkFireballCollision(){
        Rectangle f1 = null, f2 = null;
        
        if(fireball1 != null)
            f1 = fireball1.getBounds();
        
        if(fireball2 != null)
            f2 = fireball2.getBounds();
        
        if(f1 != null && f2 != null)
            if(f1.intersects(f2))
                return 1;
        
        return 0;
    }
}
