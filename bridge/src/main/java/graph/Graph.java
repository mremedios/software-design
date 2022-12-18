package graph;

import drawing.DrawingApi;
import model.Edge;
import model.Point;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class Graph {
    private final int RADIUS = 25;
    private final DrawingApi drawingApi;

    protected List<Point> points;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }


    public abstract int size();
    public void drawGraph() {
        points = createRoundVertices(size());
        var edges = createEdges();
        points.forEach(p -> drawingApi.drawCircle(p, RADIUS));
        edges.forEach(e -> drawingApi.drawLine(e.from(), e.to()));
        drawingApi.draw();
    }

    protected List<Point> createRoundVertices(int size) {
        Point center = new Point(
                drawingApi.W_WIDTH/ 2.0,
                drawingApi.W_HEIGHT / 2.0
        );
        double radius = Math.min(
                drawingApi.W_WIDTH,
                drawingApi.W_HEIGHT
        ) / 2.0 - 50;

         return IntStream.range(0, size).mapToObj(id -> {
                            var angle = 2 * Math.PI / size * id;
                            double x = radius * Math.cos(angle) + center.x();
                            double y = radius * Math.sin(angle) + center.y();
                            return new Point(x, y);
                        }
                )
                .toList();
    }

    public List<List<Integer>> readFromFile(BufferedReader reader) {
        return reader.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> Arrays.stream(line.split("\\s")).map(Integer::parseInt).toList())
                .toList();
    }
    protected abstract List<Edge> createEdges();
}
