import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Pawn implements Piece{
    private Text initial = new Text("♟");
    private Player owner;

    public Pawn(Player p) {
        owner = p;
        color(p.mark());
    }
    
    @Override
    public boolean isValidMove(ChessBoard c, int row, int col, int textRow, int textCol) {
        if(Math.abs(chess3D.piecesBoard.getLayer() - c.getLayer()) == 1 ) {
            if(owner.getId() == 1) {
                if(row != textRow && row - textRow <= 1 && col == textCol)               
                    return true;                
            } else {
                if(row != textRow && textRow - row <= 1 && col == textCol)                    
                    return true;
            }
        } else if(chess3D.piecesBoard.getLayer() == c.getLayer()){
            if(owner.getId() == 1) {
                if(textCol == col) {
                    if(row - textRow <= 2 && row >= textRow) {  
                        return true;     
                    }                                          
                }
            } else {
                if(textCol == col) {
                    if(textRow - row <= 2 && textRow >= row) {
                        return true;     
                    }                                          
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
    public boolean checkAround(int pY, int pX, int y, int x) {
        if(owner.getId() == 1) {
            for(int curY = pY + 1; curY <= y; curY++)                       
                for(int i = 0; i < 32; i++)        
                    if(chess3D.allYs[i] == curY)
                        if(chess3D.allXs[i] == x) {
                            System.out.println("There's a piece in the way (" +
                            chess3D.allYs[i] + ", " + chess3D.allXs[i] + ")");
                            return false;
                        }  
        } else {
            for(int curY = pY - 1; curY >= y; curY--)
                for(int i = 0; i < 32; i++)                    
                    if(chess3D.allYs[i] == curY)
                        if(chess3D.allXs[i] == x) {
                            System.out.println("There's a piece in the way (" + 
                            chess3D.allYs[i] + ", " + chess3D.allXs[i] + ")");
                            return false;
                        }
        }
        return true;
    }
}

