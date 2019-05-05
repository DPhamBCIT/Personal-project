import javafx.stage.*;
import javafx.scene.Scene;
import javafx.application.Application;

public class JavaPaint extends Application {
    final static int SCREEN[] = {1280, 720};
    
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage paint){
        GUI paintGUI = new GUI();
        Scene aScene = new Scene(paintGUI, SCREEN[0], SCREEN[1]);
        paint.setTitle("Paint");
        paint.setScene(aScene);
        paint.show();
    }
}