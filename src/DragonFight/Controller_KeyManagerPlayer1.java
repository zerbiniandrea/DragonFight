package DragonFight;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Zerbini&Zuech
 */

public class Controller_KeyManagerPlayer1 implements KeyListener{
    
    private boolean[] keys;
    public boolean w, a, s, d, v;
    
    public Controller_KeyManagerPlayer1(){
        keys = new boolean[256];
    }

    public void tick(){
        w = keys[KeyEvent.VK_W];
        s = keys[KeyEvent.VK_S];
        a = keys[KeyEvent.VK_A];
        d = keys[KeyEvent.VK_D];
        v = keys[KeyEvent.VK_V];
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
       keys[ke.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
    }
    
}
