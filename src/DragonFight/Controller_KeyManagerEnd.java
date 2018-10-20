package DragonFight;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.System.exit;

/**
 *
 * @author Zerbini&Zuech
 */

public class Controller_KeyManagerEnd implements KeyListener{
    
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode()==KeyEvent.VK_ENTER)
            exit(0);
    }

    @Override
    public void keyReleased(KeyEvent ke) {}
    
}
