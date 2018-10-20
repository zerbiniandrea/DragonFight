package DragonFight;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Zerbini&Zuech
 */

public class Controller_KeyManagerPlayer2 implements KeyListener{
    
    private boolean[] keys;
    public boolean up, down, left, right, l;
    
    public Controller_KeyManagerPlayer2(){
        keys = new boolean[256];
    }

    public void tick(){
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        l = keys[KeyEvent.VK_L];
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
