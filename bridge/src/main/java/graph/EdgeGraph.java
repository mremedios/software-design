package graph;

import drawing.DrawingApi;
import model.Edge;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.List;

public class EdgeGraph extends Graph {

    private final List<List<Integer>> edges;

    public EdgeGraph(DrawingApi drawingApi, List<List<Integer>> edges) {
        super(drawingApi);
        this.edges = edges;
    }

    public EdgeGraph(DrawingApi drawingApi, BufferedReader reader) {
        super(drawingApi);
        this.edges = readFromFile(reader);
    }

    @Override
    public int size() {
        return edges.stream()
                .flatMap(Collection::stream).max(Integer::compare).orElse(0);
    }

    @Override
    protected List<Edge> createEdges() {
        return edges.stream().map(list ->
                new Edge(points.get(list.get(0) - 1), points.get(list.get(1) - 1))
        ).toList();
    }

}
