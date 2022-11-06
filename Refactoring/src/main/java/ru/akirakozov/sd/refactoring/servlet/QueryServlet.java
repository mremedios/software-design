package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.formatter.HtmlWriter;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final ProductDatabase db;

    public QueryServlet(ProductDatabase db) {
        this.db = db;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        HtmlWriter writer = new HtmlWriter(response.getWriter());

        if ("max".equals(command)) {
            try {

                Product r = db.getMax();

                writer.writeHeader();
                writer.writeln("<h1>Product with max price: </h1>");
                writer.writeProduct(r);
                writer.writeFooter();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                Product r = db.getMin();

                writer.writeHeader();
                writer.writeln("<h1>Product with min price: </h1>");
                writer.writeProduct(r);
                writer.writeFooter();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                int r = db.getSum();

                writer.writeHeader();
                writer.writeln("Summary price: ");
                writer.writeln(String.valueOf(r));
                writer.writeFooter();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                int r = db.getAmount();
                writer.writeHeader();
                writer.writeln("Number of products: ");
                writer.writeln(String.valueOf(r));
                writer.writeFooter();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
