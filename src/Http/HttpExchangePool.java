package Http;

import Debug.ServerLogger;
import com.sun.net.httpserver.HttpExchange;

import java.util.HashSet;

public class HttpExchangePool {
    private static HashSet<HttpExchange> exchanges = new HashSet<>();
    private static ServerLogger serverLogger = new ServerLogger(HttpExchangePool.class.getName());

    public static void addExchange(HttpExchange exchange) {
        exchanges.add(exchange);
        serverLogger.working("Added exchange to pool");
    }

    public static void removeExchange(HttpExchange exchange) {
        exchanges.remove(exchange);
        serverLogger.working("Removed exchange from pool");
    }

    public static void closeAllOpenExchanges() {
        serverLogger.error("Forfully closing all open exchanges");
        for(HttpExchange exchange: exchanges) {
            exchange.close();
            removeExchange(exchange);
        }
        serverLogger.error("Done closing all open exchanges");
    }
}
