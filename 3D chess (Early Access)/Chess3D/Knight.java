import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Knight implements Piece{
    private Text initial = new Text("♞");
    private Player owner;

    public Knight(Player p) {
        owner = p;
        color(p.mark());
    }
    
    @Override
    public boolean isValidMove(ChessBoard c, int row, int col, int textRow, int textCol) {
        if(Math.abs(chess3D.piecesBoard.getLayer() - c.getLayer()) <= 1 ) {
           if(Math.abs(row - textRow) == 2 || Math.abs(col - textCol) == 2) {
               if(Math.abs(col - textCol) == 1 || Math.abs(row - textRow) == 1) {
                return true;
               }
           }
        }        
        return false;
    }

    @Override
    public void color(Color c) {
        initial.setFont(Font.font ("Verdana", chess3D.FONT));
        initial.setFill(c);
    }

    @Override
    public Player getOwner() {        
        return owner;
    }

    @Override
    public Text getSymbol() {
        return initial;
    }
    @Override
    public boolean checkAround(int pY, int pX, int x, int y) {
        return true;
    }
}