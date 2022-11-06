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
    protected StringWriter stringWriter;
    protected PrintWriter printWriter;

    @BeforeEach
    public void init() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    protected void verifyResponse() {
        verify(response).setContentType("text/html");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
