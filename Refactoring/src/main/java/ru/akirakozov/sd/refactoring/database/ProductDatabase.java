package ru.akirakozov.sd.refactoring.database;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProductDatabase {
    Executor executor;

    public ProductDatabase(Executor executor) {
        this.executor = executor;
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        executor.executeUpdate(sql);
    }

    public void insert(String name, long price) throws SQLException {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
        executor.executeUpdate(sql);
    }

    public List<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM PRODUCT";
        return getProductList(sql);
    }

    public int getAmount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM PRODUCT";
        return executor.executeQuery(sql, rs -> rs.getInt(1));
    }

    public int getSum() throws SQLException {
        String sql = "SELECT SUM(price) FROM PRODUCT";
        return executor.executeQuery(sql, rs -> rs.getInt(1));
    }

    public Product getMin() throws SQLException {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        return getProductList(sql).get(0);
    }

    public Product getMax() throws SQLException {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        return getProductList(sql).get(0);
    }

    private List<Product> getProductList(String sql) throws SQLException {
        return executor.executeQuery(sql, rs -> {
                    LinkedList<Product> products = new LinkedList<>();
                    while (rs.next()) {
                        products.add(
                                new Product(rs.getString("name"),
                                        rs.getInt("price")));
                    }
                    return products;
                }
        );
    }

}
