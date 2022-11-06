package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class QueryServletTest extends ServletTest {

    private void queryTest(String function) throws IOException {
        when(request.getParameter("command")).thenReturn(function);
        new QueryServlet().doGet(request, response);
        verifyResponse();
    }

    @Test
    public void maxQueryTest() throws IOException {
        queryTest("max");
        assertEquals("""
                        <html><body>
                        <h1>Product with max price: </h1>
                        bike\t600</br>
                        </body></html>
                        """.replaceAll("\n", System.lineSeparator()),
                stringWriter.toString());

    }

    @Test
    public void minQueryTest() throws IOException {
        queryTest("min");
        assertEquals("""
                        <html><body>
                        <h1>Product with min price: </h1>
                        crutch\t200</br>
                        </body></html>
                        """.replaceAll("\n", System.lineSeparator()),
                stringWriter.toString());
    }

    @Test
    public void sumQueryTest() throws IOException {
        queryTest("sum");
        assertEquals("""
                        <html><body>
                        Summary price:\s
                        1200
                        </body></html>
                        """.replaceAll("\n", System.lineSeparator()),
                stringWriter.toString());
    }

    @Test
    public void countQueryTest() throws IOException {
        queryTest("count");
        assertEquals("""
                        <html><body>
                        Number of products:\s
                        4
                        </body></html>
                        """.replaceAll("\n", System.lineSeparator()),
                stringWriter.toString());
    }

}

