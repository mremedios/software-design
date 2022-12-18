package drawing.awt;

import drawing.DrawingApi;
import model.Point;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;


public class AwtDrawing implements DrawingApi {

    private final DrawingFrame frame;

    public AwtDrawing() {
        frame = new DrawingFrame();
    }

    @Override
    public void drawCircle(Point center, double radius) {
        frame.circles.add(new Ellipse2D.Double(center.x() - radius, center.y() - radius,
                2 * radius, 2 * radius));
    }

    @Override
    public void drawLine(Point a, Point b) {
        frame.lines.add(new Line2D.Double(a.x(), a.y(), b.x(), b.y()));
    }

    public void draw() {
        frame.initialize();
    }
}