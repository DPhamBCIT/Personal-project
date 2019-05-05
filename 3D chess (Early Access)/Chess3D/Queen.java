import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Queen implements Piece{
    private Text initial = new Text("♛");
    private Player owner;
    private boolean likeBishop = false;
    private boolean likeRook = false;
    
    public Queen(Player p) {
        owner = p;
        color(p.mark());
    }
    
    @Override
    public boolean isValidMove(ChessBoard c, int row, int col, int textRow, int textCol) {        
        if(Math.abs(chess3D.piecesBoard.getLayer() - c.getLayer()) == 1 ) {
            if(row != textRow && (Math.abs(row - textRow) == 1 || Math.abs(col - textCol) == 1))               
            return true;
        } else if(chess3D.piecesBoard.getLayer() == c.getLayer()){
            if(Bishop.isValid(c, row, col, textRow, textCol)) {
                likeBishop = true;
                likeRook = false;
                return true;
            } else if(Rook.isValid(row, col, textRow, textCol)){
                likeRook = true;
                likeBishop = false;
                return true;
            }
        }       
        likeRook = false;
        likeBishop = false;
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
    public boolean checkAround(int pY, int pX, int y, int x) {        
        if(likeBishop && Bishop.usedCheckAround(pY, pX, y, x)) {            
            return true;
        } else if(likeRook && Rook.usedCheckAround(pY, pX, y, x)){
            return true;
        }        
        return false;
    }
}