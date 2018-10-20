package DragonFight;

/**
 *
 * @author Zerbini&Zuech
 */

public class DragonFight {

    public static void main(String[] args){
        
        ApplicationModel m=new ApplicationModel();
	ApplicationView v=new ApplicationView(m);
	ApplicationController c=new ApplicationController(v);
	
	v.setController(c); // importante per associare il Controller al View

        m.game("Dragon Fight", 1000, 500);
        m.start();
    }
}