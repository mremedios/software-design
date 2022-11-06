package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;

import java.io.IOException;
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
    }

    @Test
    public void minQueryTest() throws IOException {
        queryTest("min");
    }

    @Test
    public void sumQueryTest() throws IOException {
        queryTest("sum");
    }

    @Test
    public void countQueryTest() throws IOException {
        queryTest("count");
    }

}

