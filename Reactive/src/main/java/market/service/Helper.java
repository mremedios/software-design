package market.service;

import market.model.Currency;
import market.model.Product;
import market.model.User;

import java.util.List;
import java.util.Map;

public class Helper {

    public static final double RUB_DOLLAR = 82.4f;
    public static final double RUB_EURO = 90.29f;


    public static User parseUser(Map<String, List<String>> param) {
        int id = Integer.parseInt(param.get("id").get(0));
        String name = param.get("name").get(0);
        Currency currency = Currency.valueOf(param.get("currency").get(0));
        return new User(id, name, currency);
    }

    public static Product parseProduct(Map<String, List<String>> param) {
        int id = Integer.parseInt(param.get("id").get(0));
        String name = param.get("name").get(0);
        int value = Integer.parseInt(param.get("value").get(0));
        Currency currency = Currency.valueOf(param.get("currency").get(0));
        return new Product(id, name, value, currency);
    }

    public static double convertTo(Currency currencyFrom, double value, Currency currencyTo) {
        double valueInRubbles =
                switch (currencyFrom) {
                    case RUBLE -> value;
                    case EURO -> value * RUB_EURO;
                    case DOLLAR -> value * RUB_DOLLAR;
                };

        return
                switch (currencyTo) {
                    case RUBLE -> value;
                    case EURO -> valueInRubbles / RUB_EURO;
                    case DOLLAR -> valueInRubbles / RUB_DOLLAR;
                };

    }

}
