package drawing;

import model.Point;

public interface DrawingApi {
    void drawCircle(Point center, double radius);

    void drawLine(Point from, Point to);

    void draw();

    int W_WIDTH = 600;
    int W_HEIGHT = 400;
}