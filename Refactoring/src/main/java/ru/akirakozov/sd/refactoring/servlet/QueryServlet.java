package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.formatter.HtmlWriter;
import ru.akirakozov.sd.refactoring.formatter.ProductHtmlWriter;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        ProductHtmlWriter writer = new ProductHtmlWriter(response.getWriter());

        if ("max".equals(command)) {
                Product r = db.getMax();
                writer.max(r);
        } else if ("min".equals(command)) {
                Product r = db.getMin();
                writer.min(r);
        } else if ("sum".equals(command)) {
                int r = db.getSum();
                writer.sum(r);
        } else if ("count".equals(command)) {
                int r = db.getAmount();
                writer.amount(r);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
