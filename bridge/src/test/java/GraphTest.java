import drawing.DrawingApi;
import drawing.awt.AwtDrawing;
import graph.Graph;
import graph.MatrixGraph;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphTest {

    @Test
    public void reader() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/matrix.txt"));
        DrawingApi api  = new AwtDrawing();
        MatrixGraph matrixGraph = new MatrixGraph(api, reader);
        matrixGraph.drawGraph();
    }
}
