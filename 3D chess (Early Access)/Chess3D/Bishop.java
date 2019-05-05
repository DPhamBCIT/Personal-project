import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Bishop implements Piece{
    private Text initial = new Text("♝");
    private Player owner;
    
    public Bishop(Player p) {
        owner = p;
        color(p.mark());
    }
    
    @Override
    public boolean isValidMove(ChessBoard c, int row, int col, int textRow, int textCol) {
        if(Math.abs(chess3D.piecesBoard.getLayer() - c.getLayer()) == 2 ) {
            if(Math.abs(textCol - col) * Math.abs(textRow - row) == 4)       
                return true;
        } else if(Math.abs(chess3D.piecesBoard.getLayer() - c.getLayer()) == 1 ) {
            if(Math.abs(textCol - col) * Math.abs(textRow - row) == 1)               
                return true;
        } else if(chess3D.piecesBoard.getLayer() == c.getLayer()){
            if(Math.abs(textCol - col) == Math.abs(textRow - row))
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
        if(pX < x) {
            if(pY < y) {
                for(int i = 1; i <= x - pX; i++)
                    for(int pos = 0; pos < 32; pos++)                      
                        if(chess3D.allYs[pos] == pY + i) 
                            if(chess3D.allXs[pos] == pX + i) {
                                System.out.println("There's a piece in the way (" +
                                                (pX + i) + ", " + (pY + i) + ")");
                                return false;           
                            }                        
            } else {                
                for(int i = 1; i <= x - pX; i++)
                    for(int pos = 0; pos < 32; pos++)                        
                        if(chess3D.allYs[pos] == pY - i)
                            if(chess3D.allXs[pos] == pX + i) {
                                System.out.println("There's a piece in the way (" +
                                                (pX + i) + ", " + (pY - i) + ")");
                                return false;           
                            }
            }
        } else {
            if(pY < y) {
                for(int i = 1; i <= pX - x; i++)
                    for(int pos = 0; pos < 32; pos++)                      
                        if(chess3D.allYs[pos] == pY + i) 
                            if(chess3D.allXs[pos] == pX - i) {
                                System.out.println("There's a piece in the way (" +
                                                (pX - i) + ", " + (pY + i) + ")");
                                return false;           
                            }
            } else {
                for(int i = 1; i <= pX - x; i++)
                    for(int pos = 0; pos < 32; pos++)                      
                        if(chess3D.allYs[pos] == pY - i) 
                            if(chess3D.allXs[pos] == pX - i) {
                                System.out.println("There's a piece in the way (" +
                                                (pX - i) + ", " + (pY - i) + ")");
                                return false;           
                            }
            }
        }
        return true;
    }
    
    static boolean isValid(ChessBoard c, int row, int col, int textRow, int textCol) {
        if(Math.abs(chess3D.piecesBoard.getLayer() - c.getLayer()) == 2 ) {
            if(Math.abs(textCol - col) * Math.abs(textRow - row) == 4)       
                return true;
        } else if(Math.abs(chess3D.piecesBoard.getLayer() - c.getLayer()) == 1 ) {
            if(Math.abs(textCol - col) * Math.abs(textRow - row) == 1)               
                return true;
        } else if(chess3D.piecesBoard.getLayer() == c.getLayer()){
            if(Math.abs(textCol - col) == Math.abs(textRow - row))
                return true;
        }        
        return false;
    }
    
    static boolean usedCheckAround(int pY, int pX, int y, int x) {
        if(pX < x) {
            if(pY < y) {
                for(int i = 1; i <= x - pX; i++)
                    for(int pos = 0; pos < 32; pos++)                      
                        if(chess3D.allYs[pos] == pY + i) 
                            if(chess3D.allXs[pos] == pX + i) {
                                System.out.println("There's a piece in the way (" +
                                                (pX + i) + ", " + (pY + i) + ")");                                
                                return false;           
                            }                        
            } else {                
                for(int i = 1; i <= x - pX; i++)
                    for(int pos = 0; pos < 32; pos++)                        
                        if(chess3D.allYs[pos] == pY - i)
                            if(chess3D.allXs[pos] == pX + i) {
                                System.out.println("There's a piece in the way (" +
                                                (pX + i) + ", " + (pY - i) + ")");                                
                                return false;           
                            }
            }
        } else {
            if(pY < y) {
                for(int i = 1; i <= pX - x; i++)
                    for(int pos = 0; pos < 32; pos++)                      
                        if(chess3D.allYs[pos] == pY + i) 
                            if(chess3D.allXs[pos] == pX - i) {
                                System.out.println("There's a piece in the way (" +
                                                (pX - i) + ", " + (pY + i) + ")");                                
                                return false;           
                            }
            } else {
                for(int i = 1; i <= pX - x; i++)
                    for(int pos = 0; pos < 32; pos++)                      
                        if(chess3D.allYs[pos] == pY - i) 
                            if(chess3D.allXs[pos] == pX - i) {
                                System.out.println("There's a piece in the way (" +
                                                (pX - i) + ", " + (pY - i) + ")");                                
                                return false;           
                            }
            }
        }
        return true;
    }
}