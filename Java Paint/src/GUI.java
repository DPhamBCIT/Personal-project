import java.util.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUI extends BorderPane{    
    final int NUMBUT    = 5;
    final int BARWIDTH  = 250;
    final int DEFWIDTH = 150;
    final int DEFHEIGHT = 100;
    final Font uni = new Font(25);
    final Color BACKGROUND = Color.rgb(250, 233, 226);
    
    private VBox side;
    private ComboBox<String> colors;
    private ComboBox<String> allShapes;    
    private Button delete;
    private Button select;    
    
    private Canvas canvas;                    
    private List<Shape> shapes  = new ArrayList();
    
    private Shape shapeBeingDragged = null;
    private Color curColor;
    private int prevDragX, prevDragY, mouseX, mouseY;              
    private boolean deleting    = false;
    private boolean selecting   = false;
    
    public GUI() {    
        initCanvas();
        initSidesPane();
        initButtons();
        
        // Draw the canvas
        paint();
    }
    
    private void initCanvas() {
        canvas = new Canvas(JavaPaint.SCREEN[0] - BARWIDTH,JavaPaint.SCREEN[1]);
        // An event if the user clicks on the canvas
        canvas.setOnMousePressed(e -> mousePressed(e));        
        // An event if the user drags the mouse around
        canvas.setOnMouseDragged(e -> mouseDragged(e));               
        canvas.setOnMouseReleased(e -> mouseReleased(e));
    }
    
    private void initSidesPane() {
        side = new VBox();
        side.setPrefWidth(BARWIDTH);
                           
        this.setLeft(side);
        this.setCenter(canvas);
    }
        
    private void initButtons() {        
        allShapes = new ComboBox<>();
        colors = new ComboBox<>();
        delete = new Button();
        select = new Button();
        Button help = new Button();
        
        allShapes.getItems().addAll(
            "Rectangle",
            "Circle",
            "Oval",
            "Square"
        );
        
        colors.getItems().addAll(
            "Brown",
            "Grey",
            "Purple",
            "Red",
            "Orange",
            "Surprise me"
        );
        
        // Default values
        allShapes.setValue("Rectangle");
        colors.setValue("Surprise me");        
        delete.setText("Deleting\nOff");
        select.setText("Selecting\nOff");
        help.setText("Help\n");
        
        delete.setFont(uni);
        select.setFont(uni);
        help.setFont(uni);
        
        colors.setMinSize(side.getPrefWidth(), JavaPaint.SCREEN[1]/NUMBUT);
        allShapes.setMinSize(side.getPrefWidth(), JavaPaint.SCREEN[1]/NUMBUT);
        delete.setMinSize(side.getPrefWidth(), JavaPaint.SCREEN[1]/NUMBUT);
        select.setMinSize(side.getPrefWidth(), JavaPaint.SCREEN[1]/NUMBUT);
        help.setMinSize(side.getPrefWidth(), JavaPaint.SCREEN[1]/NUMBUT);
        
        delete.setOnMouseClicked(e -> process(e));                    
        select.setOnMouseClicked(e -> process(e));
        help.setOnMouseClicked(e -> showHelp());
        
        side.getChildren().addAll(allShapes, colors, delete, select, help);                                                                
    }
                   
    private void mousePressed(MouseEvent evt) {
        mouseX = (int)evt.getX();
        mouseY = (int)evt.getY();
        
        for(Shape s: shapes) {
            if (s.selected(mouseX, mouseY)) {                
                shapeBeingDragged = s;                  
                prevDragX = mouseX;
                prevDragY = mouseY;
                
                // Shapes only moves if in Selection mode
                if (shapes.indexOf(shapeBeingDragged) != shapes.size() && selecting == true) {
                   Collections.swap(shapes, shapes.indexOf(shapeBeingDragged), shapes.size() - 1);
                   paint();
                }
            }                        
        }        
        
        if(deleting == true) {
            shapes.remove(shapeBeingDragged);
            paint();
        } else if (selecting != true) {            
            // Add a shape because you clicked on an empty space
            if(shapeBeingDragged == null) {                
                Shape temp = updateShape();
                addShape(temp, mouseX, mouseY);
            }   
        }        
    }
    
    private void mouseDragged(MouseEvent e) {
        mouseX = (int)e.getX();
        mouseY = (int)e.getY();
        
        // Checking if you are pressing on a shape
        if (shapeBeingDragged != null && selecting == false) {
            shapeBeingDragged.relocate(mouseX - prevDragX, mouseY - prevDragY);
            prevDragX = mouseX;
            prevDragY = mouseY;
            paint();
        } else if (shapeBeingDragged != null && selecting == true) {
            shapeBeingDragged.reshape(shapeBeingDragged.x , shapeBeingDragged.y, 
                                      mouseX - shapeBeingDragged.x,
                                      mouseY - shapeBeingDragged.y);
            paint();
        }
    }
    
    private void mouseReleased(MouseEvent evt) {        
        shapeBeingDragged = null;
    }
    
    private void addShape(Shape shape, int x, int y) {
        updateColor();        
        shape.setColor(curColor);        
        if(shapeBeingDragged == null) {
            shape.reshape(x - (DEFWIDTH/2), y - (DEFHEIGHT/2), DEFWIDTH, DEFHEIGHT); 
        }
        shapes.add(shape);  
        paint();
    }
    
    private void paint() {    
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(BACKGROUND);
        g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        
        //Update the screen by drawing all the current shapes in the ArrayList
        for (Shape s: shapes) {
            s.draw(g);            
        }        
    }
    
    private void showHelp() {
        String tut = "- The default state is a rectangle with random colors\n"
                   + "- Click to add a shape, then you can drag it around\n"
                   + "- If \"Selection\" is toggled, you can resize it\n";        
        Alert alert = new Alert(AlertType.INFORMATION, tut);
        alert.setTitle(null);
        alert.setHeaderText("Instruction");
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.show();         
    }
    
    private void process(MouseEvent e) {
        Button me = (Button)e.getSource();
        String result;
        switch(me.getText().substring(0, me.getText().indexOf("\n"))) {
        case "Selecting":                        
            selecting = !selecting;  
            result = (selecting) ? "On" : "Off";
            select.setText("Selecting\n" + result);
            break;
        case "Deleting":
            deleting = !deleting;
            result = (deleting) ? "On" : "Off";
            delete.setText("Deleting\n" + result);
            break;
        case "Help":
        }
    }
    
    // Add extra colors here
    public void updateColor() {
        switch(colors.getValue()) {
            case "Brown":
                curColor = Color.BROWN;
                break;
            case "Grey":
                curColor = Color.GREY;
                break;
            case "Purple":
                curColor = Color.PURPLE;
                break;
            case "Red":
                curColor = Color.RED;
                break;
            case "Orange":
                curColor = Color.ORANGE;
                break;
            case "Surprise me":
                curColor = Color.rgb((int)(Math.random() * 255),
                                     (int)(Math.random() * 255),
                                     (int)(Math.random() * 255));
                break;
        }
    }
    
    // Add extra shapes here
    public Shape updateShape() {
        Shape result = new RectShape();
        switch(allShapes.getValue()) {
            case "Rectangle":
                result = new RectShape();
                break;
            case "Circle":
                result = new CircleShape();                
                break;
            case "Oval":
                result = new OvalShape();
                break;
            case "Square":
                result = new SquareShape();
                break;
        }        
        return result;
    }
}
