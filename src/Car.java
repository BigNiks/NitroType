import javax.swing.*;
import java.awt.*;

public class Car {
    private final int y = 250;
    private int dx;
    private Image cat;
    private NitroTypeViewer ntv;
    public Car(int dxIn, NitroTypeViewer ntv) {
        this.ntv = ntv;
        cat = new ImageIcon("Resources/img_1.png").getImage();
        dx = dxIn;
    }


    public void setX(int x) {
        dx = x;
    }

    public int getX() {
        return dx;
    }

    public void draw(Graphics g) {
        g.drawImage(cat, dx, y,200, 100, ntv);
    }

    public void move() {

    }
}
