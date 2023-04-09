package market.service;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import market.db.ReactiveMongoDriver;
import market.model.Currency;
import market.model.Product;
import market.model.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class MarketService {
    private final ReactiveMongoDriver db = new ReactiveMongoDriver();

    public Observable<User> getAllUsers() {
        return db.getAllUsers();
    }

    public Observable<String> addUser(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> param = req.getQueryParameters();
        User user = Helper.parseUser(param);
        db.addUser(user);
        return Observable.just(user.toString());
    }

    public Observable<Product> getProductsForUser(HttpServerRequest<ByteBuf> req) {
        int id = Integer.parseInt(req.getQueryParameters().get("id").get(0));
        Currency userCurrency = db.getCurrencyById(id);
        Observable<Product> allProducts = db.getAllProducts();
        return allProducts.map(p -> new Product(
                p.id,
                p.name,
                Helper.convertTo(p.currency, p.value, userCurrency),
                userCurrency
        )
        );
    }

    public Observable<String> addProduct(HttpServerRequest<ByteBuf> req) {
        Map<String, List<String>> param = req.getQueryParameters();
        Product product = Helper.parseProduct(param);
        db.addProduct(product);
        return Observable.just(product.toString());
    }

}
