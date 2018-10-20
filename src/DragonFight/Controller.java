package DragonFight;

/**
 *
 * @author Zerbini&Zuech
 */

public class Controller implements Observer{
    protected Model m;
    protected View v;
    
    public Controller(View v){
        this.v=v;
        m=v.getModel();
        m.attach(this);
    }
    
    @Override
    public void update(){
        
    }
}
