import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {       
    int x, y, width, height;
    Color color;
    
    // For each subclasses to override
    abstract void draw(GraphicsContext g);
    abstract boolean selected(int x, int y);
    
    void setColor(Color color) {
        this.color = color;    
    }
    
    Color getColor() {
        return this.color;    
    }
    
    void reshape(int X, int Y, int w, int h) {
        this.x = X;
        this.y = Y;        
        this.height = h;
        this.width = w;
    }
    
    void relocate(int dx, int dy) {    
        x += dx;
        y += dy;
    }        
}

class RectShape extends Shape {    
    void draw(GraphicsContext g) {
        g.setFill(color);
        g.fillRect(x,y,width,height);
    }
    
    boolean selected(int mouseX, int mouseY) {
        if (mouseX >= this.x && mouseX < (this.x + width) 
        &&  mouseY >= this.y  && mouseY < (this.y + height))
            return true;
        else
            return false;
    } 
}

class SquareShape extends Shape {
    void draw(GraphicsContext g) {
        g.setFill(color);
        g.fillRect(x,y,width,width);       
    }
    
    boolean selected(int mouseX, int mouseY) {
        if (mouseX >= this.x && mouseX < (this.x + width) 
        &&  mouseY >= this.y  && mouseY < (this.y + height))
            return true;
        else
            return false;
    }
}

class CircleShape extends Shape {
    void draw(GraphicsContext g) {
        g.setFill(color);
        g.fillOval(x, y, width, width);
    }
    
    boolean selected(int mouseX, int mouseY) {           
        double radius = width/2; 
        double cx = this.x + radius;
        double cy = this.y + radius;
        if ( (radius*(mouseX-cx))*(radius*(mouseX-cx)) 
           + (radius*(mouseY-cy))*(radius*(mouseY-cy))
           <= Math.pow(radius, 4) )
            return true;
        else
            return false;
    }
}

class OvalShape extends Shape {
    void draw(GraphicsContext g) {
        g.setFill(color);
        g.fillOval(x,y,width,height);
    }
    
    boolean selected(int mouseX, int mouseY) {           
        double rx = width/2;
        double ry = height/2; 
        double cx = this.x + rx;
        double cy = this.y + ry;
        if ( (ry*(mouseX-cx))*(ry*(mouseX-cx)) 
           + (rx*(mouseY-cy))*(rx*(mouseY-cy))
           <= rx*rx*ry*ry )
            return true;
        else
            return false;
    }
}