package ru.akirakozov.sd.refactoring.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueriesTest {
    private final static List<Product> data = List.of(
            new Product("item1", 500),
            new Product("item2", 400),
            new Product("item3", 500)
    );

    protected ProductDatabase db;

    @BeforeEach
     void initDb() throws SQLException, IOException {
        String dir = Files.createTempDirectory("temp").toString();
        db = new ProductDatabase(new Executor("jdbc:sqlite:" + dir + "\\test.db"));
        db.createTable();
        data.forEach(p -> {
            try {
                db.insert(p.getName(), p.getPrice());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void queries() throws SQLException {
        assertEquals(data.size(), db.getAll().size());
        assertEquals(data.size(), db.getAmount());
        assertEquals(500, db.getMax().getPrice());
        assertEquals(400, db.getMin().getPrice());
        assertEquals(data.stream().mapToInt(Product::getPrice).sum(), db.getSum());
    }

    @Test
    public void insert() throws SQLException {
        db.insert("test", 5);
        assertEquals(db.getAll()
                        .stream()
                        .filter(p -> p.getName().equals("test") && p.getPrice() == 5)
                        .count(),
                1);
    }
}
