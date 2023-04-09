package market;

import io.netty.handler.codec.http.HttpMethod;
import io.reactivex.netty.protocol.http.server.HttpServer;
import market.model.Product;
import market.model.User;
import market.service.MarketService;
import rx.Observable;

public class Server {

    private static final MarketService service = new MarketService();

    public static void main(String[] args) {
        HttpServer
            .newServer(8080)
            .start((req, resp) -> {
                try {
                    String path = req.getDecodedPath();
                    if ("/user".equals(path) && req.getHttpMethod() == HttpMethod.GET) {
                        Observable<String> response = service.getAllUsers().map(User::toString);
                        return resp.writeString(response);
                    }
                    if ("/user".equals(path) && req.getHttpMethod() == HttpMethod.POST) {
                        Observable<String> response = service.addUser(req);
                        return resp.writeString(response);
                    }
                    if ("/product".equals(path) && req.getHttpMethod() == HttpMethod.GET) {
                        Observable<String> response =  service.getProductsForUser(req).map(Product::toString);
                        return resp.writeString(response);
                    }
                    if ("/product".equals(path) && req.getHttpMethod() == HttpMethod.POST) {
                        Observable<String> response = service.addProduct(req);
                        return resp.writeString(response);
                    }
                    return resp.writeString(
                        Observable.just("No path " + req.getDecodedPath())
                    );
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    return resp.writeString(Observable.just(e.getMessage()));
                }
            })
            .awaitShutdown();
    }
}
