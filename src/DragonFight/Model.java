package DragonFight;

/**
 *
 * @author Zerbini&Zuech
 */

public class Model {
    private int numberOfRegistry;
    Observer registry[]=new Observer[100];
    
    public Model(){
        numberOfRegistry=0;
    }
    
    public void attach(Observer o){
        registry[numberOfRegistry++]=o;
    }
    
    protected void _notify(){
    // aggiorna tutti i View e Controller
		for(int i=0;i<numberOfRegistry;i++)
			registry[i].update();
    }
    
}
