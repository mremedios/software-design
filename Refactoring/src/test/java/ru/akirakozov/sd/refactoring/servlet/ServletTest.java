package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.akirakozov.sd.refactoring.database.Executor;
import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;


public abstract class ServletTest {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected StringWriter stringWriter;
    protected PrintWriter printWriter;
    protected static ProductDatabase db;

    @BeforeAll
    static void initDb() throws SQLException, IOException {
        String dir = Files.createTempDirectory("temp").toString();
        db = new ProductDatabase(new Executor("jdbc:sqlite:" + dir + "\\test.db"));
        db.createTable();
        db.insert("bike", 600);
        db.insert("crutch", 200);
    }


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
