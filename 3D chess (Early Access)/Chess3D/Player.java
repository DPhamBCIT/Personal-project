import javafx.scene.paint.Color;

public class Player {
    private int id;
    private Color rep;
    public Player(int i, Color c) {
        id = i;
        rep = c;
    }
    
    public int getId() {
        return id;
    }
    
    public Color mark() {
        return rep;
    }
}
