package DragonFight;

/**
 *
 * @author Zerbini&Zuech
 */

public class View implements Observer {
    Controller c;
    protected Model m;
    
    public View(Model m){
        this.m=m;
        this.m.attach(this);
    }
    
    public void update(){
        this.draw();
    }
    
    public void draw(){
        // Override in ApplicationView
    }
    
    public Controller getController(){
        return this.c;
    }
    
    public Model getModel(){
        return this.m;
    }
    
    public void setController(Controller c){
        this.c=c;
    }
    
    public void setModel(Model m){
        this.m=m;
    }
}
