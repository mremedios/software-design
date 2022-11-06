package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServletTest extends ServletTest{

    @Test
    public void getProductTest() throws IOException {
        new GetProductsServlet(db).doGet(request, response);
        verifyResponse();
    }

    @Test
    public void addProductTest() throws IOException {
        when(request.getParameter("name")).thenReturn("bike");
        when(request.getParameter("price")).thenReturn("200");

        new AddProductServlet(db).doGet(request, response);

        verifyResponse();
        verify(response).getWriter();
    }

}
