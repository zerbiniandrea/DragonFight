package DragonFight;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Zerbini&Zuech
 */

public class ApplicationModel extends Model implements Runnable{
    
    private String title;
    private int width, height;
    
    private boolean running;
    private Thread thread;
    
    private Clip ol;

    ////////////////////////////
    
    public ApplicationModel(){
        super();
    }
    
    public String getTitle(){
        return title;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    
    public void game(String title, int width, int height){ // metodo chiamato all'inizio per far partire l'applicazione
        this.title=title;
        this.width=width;
        this.height=height;
    }
    
    /////////////////// MUSICA ///////////////////
    
    public void gameMusic(){
        File sf = new File("music/nobody's listening.wav");
        AudioFileFormat aff;
        AudioInputStream ais;
        
        try{
            aff=AudioSystem.getAudioFileFormat(sf);
            ais=AudioSystem.getAudioInputStream(sf);
            AudioFormat af = aff.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(), ((int)ais.getFrameLength()*af.getFrameSize()));
            ol = (Clip) AudioSystem.getLine(info);
            ol.open(ais);
            ol.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch(UnsupportedAudioFileException ee){
            System.out.println("Formato audio non supportato!");
            System.out.println(ee);
        }
        catch(IOException ea){
            System.out.println(ea);
        }
        catch(LineUnavailableException LUE){
            System.out.println(LUE);
        }
    }
    
    public void winnerMusic(){
        ol.stop();
        File sf = new File("music/winner.wav");
        AudioFileFormat aff;
        AudioInputStream ais;
        
        try{
            aff=AudioSystem.getAudioFileFormat(sf);
            ais=AudioSystem.getAudioInputStream(sf);
            AudioFormat af = aff.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(), ((int)ais.getFrameLength()*af.getFrameSize()));
            ol = (Clip) AudioSystem.getLine(info);
            ol.open(ais);
            ol.loop(0);
        }
        catch(UnsupportedAudioFileException ee){
            System.out.println("Formato audio non supportato!");
            System.out.println(ee);
        }
        catch(IOException ea){
            System.out.println(ea);
        }
        catch(LineUnavailableException LUE){
            System.out.println(LUE);
        }
    }
    
    
    /////////////// METODI THREAD ///////////////
    
    private void init(){
        _notify(); // Quando cambia lo stato dell'applicazione, il View e il Controller vanno aggiornati per invocare update()
    }
    
    @Override
    //Chiama la funzione per disegnare un numero determinato di volte al secondo
    public void run(){
        init();
        
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;
        
        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            
            if(delta >= 1){
                ((ApplicationView)registry[0]).render();
                ticks++;
                delta--;
            }
            if(timer >= 1000000000){
                ticks = 0;
                timer = 0;
            }
        }
        
        stop();
    }
    
    public synchronized void start(){
        if(running)
            return; // Se l'applicazione è già partita esco dal metodo
        
        running=true;
        thread=new Thread(this);
        thread.start(); // Fa partire il metodo run()
    }
    
    public synchronized void stop(){
        if(!running)
            return; // Se l'applicazione si è già fermata esco dal metodo
        
        running=false;        
        try {
            thread.join();
        }
        catch (InterruptedException ex) {
            Logger.getLogger(ApplicationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /////////////// METODI IMMAGINI ///////////////
    
    //Carica un immagine
    public BufferedImage loadImage(String path){
        try {
            return ImageIO.read(ApplicationModel.class.getResource(path));
        } catch (IOException ex) {
            Logger.getLogger(ApplicationModel.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return null;
    }
    
    //Divide un immagine in una sottoimmagine
    public BufferedImage crop(BufferedImage sheet, int x, int y, int width, int height){
        return sheet.getSubimage(x,y,width,height);
    }
}