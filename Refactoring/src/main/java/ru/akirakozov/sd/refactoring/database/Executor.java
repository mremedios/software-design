package ru.akirakozov.sd.refactoring.database;

import java.sql.*;

public class Executor {
    private final String url;

    public Executor(String url) {
        this.url = url;
    }

    public <T> T executeQuery(String query, SQLFunction<T> processing) {
        try (Connection connection = DriverManager.getConnection(url)) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            T res = processing.apply(rs);

            rs.close();
            stmt.close();
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void executeUpdate(String query) {
        try (Connection connection = DriverManager.getConnection(url)) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface SQLFunction<T> {
        T apply(ResultSet rs) throws SQLException;
    }

}
