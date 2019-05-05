import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class GUI {
    ChessBoard local3DChess;
    Player[] players = new Player[2];
    
    public GUI(int l, Scene curScreen) {
        players[0] = new Player(1, Color.rgb(200,131,72));        
        players[1] = new Player(2, Color.rgb(105,83,75));
        
        local3DChess = new ChessBoard(l, players);
    }
}
