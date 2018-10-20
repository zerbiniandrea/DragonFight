package DragonFight;

/**
 *
 * @author Zerbini&Zuech
 */

public class ApplicationController extends Controller {
    public ApplicationController(ApplicationView v){
        super(v);
	((ApplicationModel)m).gameMusic();
    }

    @Override
    public void update(){
        ((ApplicationView)v).display(((ApplicationModel)m).getTitle(), ((ApplicationModel)m).getWidth(), ((ApplicationModel)m).getHeight()); // Disegna la finestra dal View
    }
    
    public void winner(){
        ((ApplicationModel)m).winnerMusic();
    }
}
