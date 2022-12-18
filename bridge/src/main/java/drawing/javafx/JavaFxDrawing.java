package drawing.javafx;

import drawing.DrawingApi;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.Point;

import java.util.ArrayList;
import java.util.List;


public class JavaFxDrawing implements DrawingApi {
    private static final List<Shape> shapes = new ArrayList<>();

    @Override
    public void drawCircle(Point center, double radius) {
        shapes.add(new Circle(center.x(), center.y(), radius));
    }

    @Override
    public void drawLine(Point a, Point b) {
        shapes.add(new Line(a.x(), a.y(), b.x(), b.y()));
    }

    @Override
    public void draw() {
        Application.launch(DrawingApp.class);
    }

    public static class DrawingApp extends Application {

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("JavaFX graph visualization");
            Group root = new Group();

            shapes.forEach(shape -> root.getChildren().add(shape));

            primaryStage.setScene(new Scene(root, W_WIDTH, W_HEIGHT));
            primaryStage.show();
        }

    }

}