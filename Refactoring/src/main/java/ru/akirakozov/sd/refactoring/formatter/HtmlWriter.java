package ru.akirakozov.sd.refactoring.formatter;

import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.io.Writer;

public class HtmlWriter {
    protected final Writer writer;


    public HtmlWriter(Writer writer) {
        this.writer = writer;
    }

    public void writeHeader() throws IOException {
        String header = "<html><body>";
        writeln(header);
    }

    public void writeFooter() throws IOException {
        String footer = "</body></html>";
        writeln(footer);
    }

    public void writeln(String s) throws IOException {
        writer.write(s);
        writer.write(System.lineSeparator());
    }

    public void writeProduct(Product p) throws IOException {
        writeln(p.getName() + "\t" + p.getPrice() + "</br>");
    }
}
