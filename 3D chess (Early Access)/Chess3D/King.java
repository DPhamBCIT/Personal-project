import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class King implements Piece{
    private Text initial = new Text("♚");
    private Player owner;
    
    public King(Player p) {
        owner = p;
        color(p.mark());
    }
    
    @Override
    public boolean isValidMove(ChessBoard c, int row, int col, int textRow, int textCol) {
        if(Math.abs(chess3D.piecesBoard.getLayer() - c.getLayer()) == 1 ) {
            if(row != textRow && Math.abs(row - textRow) <= 1 && col == textCol)                
                return true;
        } else if(chess3D.piecesBoard.getLayer() == c.getLayer()){
            if(Math.abs(row - textRow) <= 1 && Math.abs(col - textCol) <= 1)        
                return true;
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
    public boolean checkAround(int pY, int pX, int y, int x) {
        if(pY == y) {              
            for(int i = 0; i < 32; i++)         
                if(chess3D.allXs[i] == x)
                    if(chess3D.allYs[i] == pY) {
                        System.out.println("There's a piece in the way");
                        return false;           
                    }
        } else if(pX == x){ // Same column, moving vertically
            for(int i = 0; i < 32; i++)  
                if(chess3D.allYs[i] == y)
                    if(chess3D.allXs[i] == x) {
                        System.out.println("There's a piece in the way");
                        return false;
                    }                                                    
        }
        return true;
    }
}