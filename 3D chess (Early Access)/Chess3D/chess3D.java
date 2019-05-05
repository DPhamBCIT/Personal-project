import javafx.stage.*;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.application.Application;

public class chess3D extends Application {
    // Preference settings
    final static int[] offsetX = {380, 730, 30};
    final static int[] offsetY = {100, 0, 0};
    final static int NUM_GRID  = 8;
    final static int SCREEN    = 60 * 10;
    final static int FONT      = SCREEN / 10;    
    
    // Stored data for moving between boards
    static int[] allYs      = new int[32];
    static int[] allXs      = new int[32];
    static int whoTurn      = 1;
    static boolean moving   = false;    
    static ChessBoard piecesBoard; 
    static int curPos;
    static Piece cP;
    
    Scene[] localBoards =  new Scene[3];
    Stage[] chessLayer  =  new Stage[3];        
    GUI[] chess3D       = {new GUI(0, localBoards[0]),
                           new GUI(1, localBoards[1]),
                           new GUI(2, localBoards[2])};
    
    @Override
    public void start(Stage useless) {                        
        for(int counter = 2; counter >= 0; counter--) {
            localBoards[counter] = new Scene(chess3D[counter].local3DChess, SCREEN, SCREEN);            
            chessLayer[counter] = new Stage();
            init(counter, chessLayer[counter]);
            // Exit one to exit all
            chessLayer[counter].setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent t) {
                    System.exit(0);
                }
            });                        
        }                        
    }
            
    public static void main(String[] args) {
        launch(args);
    }
    
    private void init(int n, Stage s) {
        s.setTitle("Layer " + n);
        s.setScene(localBoards[n]);
        s.setX(offsetX[n]);
        s.setY(offsetY[n]);
        s.show();
    }
}
