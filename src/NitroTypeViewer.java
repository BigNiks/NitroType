import javax.swing.*;
import java.awt.*;

public class NitroTypeViewer extends JFrame {
    public static final int WINDOW_WIDTH = 1000,
                            WINDOW_HEIGHT = 500;
    private Image back;
    private Image carimg;
    private NitroType hi;
    public NitroTypeViewer(NitroType hi) {
        back = new ImageIcon("Resources/back.png").getImage();
        this.hi = hi;

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("Nitro Type");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.drawImage(back,0,0,this);
        g.drawImage(carimg, 0, 250, this);
        g.setFont(new Font("SansSerif", Font.PLAIN, 40 ));
        g.drawString("|" + hi.getTest(), 100, 400);
        if(!hi.getStatus()) {
            g.drawString("You WIN!", 380, 175);
            g.drawString("Your time: " + hi.getTime() + " seconds", 300, 350);
        }
        hi.getCar().draw(g);
    }
}
