package ru.akirakozov.sd.refactoring.formatter;

import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ProductHtmlWriter {
    HtmlWriter writer;

    public ProductHtmlWriter(Writer writer) {
        this.writer = new HtmlWriter(writer);
    }

    public void get(List<Product> products) throws IOException {
        writer.writeHeader();
        for (Product p : products) {
            writer.writeProduct(p);
        }

        writer.writeFooter();
    }

    public void amount(int p) throws IOException {
        writer.writeHeader();
        writer.writeln("Number of products: ");
        writer.writeln(String.valueOf(p));
        writer.writeFooter();
    }

    public void sum(long r) throws IOException {
        writer.writeHeader();
        writer.writeln("Summary price: ");
        writer.writeln(String.valueOf(r));
        writer.writeFooter();
    }

    public void min(Product p) throws IOException {
        writer.writeHeader();
        writer.writeln("<h1>Product with min price: </h1>");
        writer.writeProduct(p);
        writer.writeFooter();
    }

    public void max(Product p) throws IOException {
        writer.writeHeader();
        writer.writeln("<h1>Product with max price: </h1>");
        writer.writeProduct(p);
        writer.writeFooter();
    }
}
