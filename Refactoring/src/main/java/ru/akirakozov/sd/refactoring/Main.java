package ru.akirakozov.sd.refactoring;

import ru.akirakozov.sd.refactoring.database.Executor;
import ru.akirakozov.sd.refactoring.database.ProductDatabase;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
            ProductDatabase db = new ProductDatabase(new Executor("jdbc:sqlite:test.db"));
            db.createTable();

            ProductServer.start(db);
    }
}
