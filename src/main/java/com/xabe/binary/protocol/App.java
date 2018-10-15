package com.xabe.binary.protocol;

import com.xabe.binary.protocol.config.CustomResourceConfig;
import com.xabe.binary.protocol.config.spring.SpringConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class App {

    private static final String BIND_IP = "0.0.0.0";
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static HttpServer server;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("global").setLevel(Level.ALL);
        Runtime.getRuntime().addShutdownHook( new Thread( () ->  server.shutdownNow() ));
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        final ResourceConfig rc = new CustomResourceConfig(applicationContext);
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(getUriInfo("http" ,8008)), rc,false);
        server.start();
        LOGGER.info( "Stop the application using CTRL+C" );
        Thread.currentThread().join();
    }

    private static String getUriInfo(String protocol,int port) {
        return String.format(protocol + "://%s:%d", BIND_IP, port);
    }

}
