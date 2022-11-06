package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;


public abstract class ServletTest {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected PrintWriter printWriter;

    @BeforeEach
    public void init() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        printWriter = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(printWriter);
    }

    protected void verifyResponse() {
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
