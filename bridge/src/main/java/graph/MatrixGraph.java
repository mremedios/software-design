package graph;

import drawing.DrawingApi;
import model.Edge;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

public class MatrixGraph extends Graph {
    private final List<List<Boolean>> matrix;

    public MatrixGraph(DrawingApi drawingApi, List<List<Boolean>> matrix) {
        super(drawingApi);
        this.matrix = matrix;
    }

    @Override
    public int size() {
        return matrix.size();
    }

    @Override
    protected List<Edge> createEdges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j)) {
                    edges.add(new Edge(points.get(i), points.get(j)));
                }
            }
        }
        return edges;
    }

    public MatrixGraph(DrawingApi drawingApi, BufferedReader reader) {
        super(drawingApi);

        this.matrix = readFromFile(reader).stream().map(line -> line.stream()
                        .map(x -> x == 1)
                        .toList()
                )
                .toList();

    }

    public List<List<Boolean>> getMatrix() {
        return matrix;
    }
}

