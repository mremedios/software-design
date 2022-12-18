package drawing.awt;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static drawing.DrawingApi.W_HEIGHT;
import static drawing.DrawingApi.W_WIDTH;

public class DrawingFrame extends Frame {

    final List<Ellipse2D> circles = new ArrayList<>();
    final List<Line2D> lines = new ArrayList<>();

    public DrawingFrame() {
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D ga = (Graphics2D) g;
        ga.setPaint(Color.green);
        circles.forEach(ga::fill);
        lines.forEach(ga::draw);
    }

    public void initialize() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        this.setSize(W_WIDTH, W_HEIGHT);
        this.setVisible(true);
    }

}
