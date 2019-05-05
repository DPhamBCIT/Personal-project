import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public interface Piece {    
    boolean isValidMove(ChessBoard c, int row, int col, int textRow, int textCol);
    void color(Color c);
    Player getOwner();
    Text getSymbol();
    boolean checkAround(int x, int y, int x2, int y2);
}
