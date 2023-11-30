package org.messenger.Http;

import com.sun.net.httpserver.HttpExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

public class HttpExchangePool {
    private static HashSet<HttpExchange> exchanges = new HashSet<>();
    private static final Logger logger = LogManager.getLogger(HttpExchangePool.class);

    public static void addExchange(HttpExchange exchange) {
        exchanges.add(exchange);
        logger.debug("Added exchange to pool");
    }

    public static void removeExchange(HttpExchange exchange) {
        exchanges.remove(exchange);
        logger.debug("Removed exchange from pool");
    }

    public static void closeAllOpenExchanges() {
        logger.warn("Forfully closing all open exchanges");
        for(HttpExchange exchange: exchanges) {
            exchange.close();
            removeExchange(exchange);
        }
        logger.warn("Done closing all open exchanges");
    }
}
