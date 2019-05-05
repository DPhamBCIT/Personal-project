import javafx.stage.*;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert.*;

public class ChessBoard extends GridPane{          
    // Color set for the boards
    private Color[][] tilesC = {{Color.rgb(245,209,159), Color.rgb(63,44,14)}, 
                                {Color.WHITE, Color.BLACK},
                                {Color.rgb(157,206,252), Color.rgb(2,45,147)}};
    private Player[] localPlayers;
    int layer;
    
    public ChessBoard(int l, Player[] ps) {
        localPlayers = ps;
        layer = l;         
        
        drawSquares();
        if(layer == 0) { 
            showHelp();
            initPieces();            
        }        
    }
    
    private void showHelp() {
        String tut = "1\\ Caramel piece goes first\n"
                   + "2\\ Right click anywhere on the board to cancel a selection\n";
        Alert alert = new Alert(AlertType.INFORMATION, tut);
        alert.setTitle(null);
        alert.setHeaderText("Instruction");
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.show();         
    }
    
    private void drawSquares() {
        for (int y = 0; y < chess3D.NUM_GRID; y++) {
            for (int x = 0; x < chess3D.NUM_GRID; x++) {                    
                Rectangle background = new Rectangle();                
                background.setFill(((x + y) % 2 == 0) ? tilesC[layer][0] : tilesC[layer][1]);
                background.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        checkSquare(event, background);
                    }
                });
                
                /* This automatically calculates the length of the tiles, compared
                 to the chess board */                                
                background.widthProperty().bind(this.widthProperty().divide(chess3D.NUM_GRID));
                background.heightProperty().bind(this.heightProperty().divide(chess3D.NUM_GRID));
                this.add(background, x, y);
            }
        }        
    }
    
    private void initPieces() {
        for(int i = 0; i < 2; i++) {
            int conv = 16*i;
            Player tempP = localPlayers[i];
            Piece[] tempSet = pieceSet(tempP);
            
            int count = 0 + conv;            
            for(Piece p: tempSet) {
                Text temp = p.getSymbol();          
                if(count < (chess3D.NUM_GRID + conv)) {
                    int cur = count;                    
                    setPiece(count, count - conv, 7*i + 0, temp);
                    temp.setOnMouseClicked(e -> process(e, p, cur));
                    count++;
                } else {
                    int cur = count;
                    setPiece(count, count - (chess3D.NUM_GRID + conv), 5*i + 1, temp);  
                    temp.setOnMouseClicked(e -> process(e, p, cur));                
                    count++;
                }
            }
        }             
    }
                
    // check the piece being clicked
    private void process(MouseEvent ev, Piece p, int pos) {
        if(chess3D.moving == false) {
           chess3D.piecesBoard = (ChessBoard)(((Node)ev.getSource()).getScene().getRoot());
           
           if(chess3D.whoTurn == p.getOwner().getId()) {                
              chess3D.curPos = pos;
              chess3D.cP = p;            
              chess3D.cP.color(Color.RED);
              chess3D.moving = true;
           } else {
               System.out.println("It's player " + chess3D.whoTurn + "'s turn!");
           }            
        } // else for capturing a piece.
    }
    
    // check the square being clicked
    private void checkSquare(MouseEvent e, Node n) {  
        if (e.getButton() == MouseButton.SECONDARY) {
            if(chess3D.cP != null) {
                chess3D.cP.color(chess3D.cP.getOwner().mark());
                chess3D.cP = null;
                chess3D.moving  = false;
            } else {
                System.out.println("No piece to cancel");
            }
        } else {
            if(chess3D.cP != null) {            
                if(chess3D.moving == true) {
                   ChessBoard curBoard = (ChessBoard)(((Node)e.getSource()).getScene().getRoot());
                   int x = GridPane.getColumnIndex(n);
                   int y = GridPane.getRowIndex(n);
                                  
                   if(chess3D.cP.isValidMove(curBoard, y, x,
                      chess3D.allYs[chess3D.curPos], chess3D.allXs[chess3D.curPos])) {
                       if(waysClear(chess3D.allYs[chess3D.curPos], 
                          chess3D.allXs[chess3D.curPos], y, x, curBoard)) {
                          chess3D.cP.getSymbol().toBack(); //Fixing a bug
                          chess3D.piecesBoard.getChildren().remove(chess3D.cP.getSymbol());     
                          setPiece(chess3D.curPos, x, y, chess3D.cP.getSymbol());
                          chess3D.whoTurn = (chess3D.whoTurn == 1) ? 2 : 1;
                       }
                   } else {
                       System.out.println("Invalid move!(" +
                       chess3D.allXs[chess3D.curPos] + ", " + chess3D.allYs[chess3D.curPos] + 
                       ") to (" + x + ", " + y + ")");
                   }
               }
               chess3D.cP.color(chess3D.cP.getOwner().mark());
               chess3D.cP = null;
            }
            chess3D.moving = false;
        }
    }
    
    // Do way clearing
    private boolean waysClear(int textY, int textX, int y, int x, ChessBoard c) {        
        if(chess3D.piecesBoard.getLayer() == c.getLayer()) {
            return chess3D.cP.checkAround(textY, textX, y, x);
        }
        return true;
    }
    
    private void setPiece(int pos, int c, int r, Text t) {
        chess3D.allXs[pos] = c;
        chess3D.allYs[pos] = r;                
        add(t, c, r);
    }

    private Piece[] pieceSet(Player p) {
        Piece[] set = {new Rook(p),
                       new Knight(p),
                       new Bishop(p),
                       new King(p),
                       new Queen(p),
                       new Bishop(p),
                       new Knight(p),
                       new Rook(p)};

        Piece[] allPiece = new Piece[16];
        for(int i = chess3D.NUM_GRID; i < 16; i++) {
            allPiece[i] = new Pawn(p);
            allPiece[i-chess3D.NUM_GRID] = set[i-chess3D.NUM_GRID];
        }

        return allPiece;
    }

    public int getLayer() {
        return layer;
    }
}