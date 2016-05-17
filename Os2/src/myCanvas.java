import java.awt.*;

/**
 * Created by hongjiayong on 16/5/15.
 */
public class myCanvas extends Canvas {
    private Graphics pen;
    private Color color;
    private int wide;
    private int lenth;
    private int start;

    public myCanvas(Color color, int start, int wide) {
        this.wide = (int) (wide * 1.56);
        lenth = wide;
        setSize(this.wide, 350);
        this.color = color;
        this.start = start;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        pen = g;
        pen.setColor(color);
        pen.fillRect(0, 0, wide, 350);
        pen.setFont(new Font("宋体",Font.BOLD,10));
        pen.setColor(Color.orange);
        pen.drawString(String.valueOf(start) + "K", 5, 20);
        pen.drawString(String.valueOf(start + lenth) + "K", wide - 40, 20);
    }

    public Color getColor() {
        return color;
    }
}
