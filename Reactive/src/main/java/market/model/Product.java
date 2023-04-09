package market.model;

import org.bson.Document;

public class Product {
    public final int id;
    public final String name;
    public final double value;
    public final Currency currency;

    public Product(Document doc) {
        this(
            doc.getInteger("id"),
            doc.getString("name"),
            doc.getDouble("value"),
            Currency.valueOf(doc.getString("currency"))
        );
    }

    public Product(int id, String name, double value, Currency currency) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.currency = currency;
    }

    public Document toDocument() {
        return new Document()
            .append("id", id)
            .append("name", name)
            .append("value", value)
            .append("currency", currency.toString());
    }

    @Override
    public String toString() {
        return "Product(" + "id=" + id + ", name=" + name + ", value=" + value + ", currency=" + currency + ")\n";
    }
}
