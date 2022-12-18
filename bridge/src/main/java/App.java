import drawing.DrawingApi;
import drawing.awt.AwtDrawing;
import drawing.javafx.JavaFxDrawing;
import graph.EdgeGraph;
import graph.Graph;
import graph.MatrixGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        // javafx matrix matrix.txt
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + args[2]));

        DrawingApi drawingApi = getDrawingApi(args[0]);
        Graph graph = getGraph(drawingApi, args[1], reader);

        graph.drawGraph();
    }

    private static DrawingApi getDrawingApi(String apiType) {
        if (apiType.equals("javafx")) {
            return new JavaFxDrawing();
        }
        if (apiType.equals("awt")) {
            return new AwtDrawing();
        }
        throw new IllegalArgumentException("Invalid drawing api type");
    }

    private static Graph getGraph(DrawingApi drawingApi, String type,  BufferedReader reader) {
        if (type.equals("matrix")) {
            return new MatrixGraph(drawingApi, reader);
        }
        if (type.equals("edge")) {
            return new EdgeGraph(drawingApi, reader);
        }
        throw new IllegalArgumentException("Invalid graph type");
    }

}
