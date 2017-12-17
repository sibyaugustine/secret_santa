package com.devdolphin.games.scretsanta.server;

import java.net.URL;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.devdolphin.games.scretsanta.service.ScretSantaAccessService;
import com.devdolphin.games.scretsanta.service.ScretSantaFilter;
import com.devdolphin.games.scretsanta.service.ScretSantaServcieProvider;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	Server server = new Server(8090);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new ScretSantaAccessService()),"/request_service/*");
        context.addServlet(new ServletHolder(new ScretSantaServcieProvider()),"/service");
        context.addFilter((Class<? extends Filter>) ScretSantaFilter.class,"/service/*",EnumSet.of(DispatcherType.INCLUDE,DispatcherType.REQUEST));
        
        String  baseStr  = "webapp";
        URL baseUrl  = App.class.getClassLoader().getResource( baseStr ); 
        String basePath = baseUrl.toExternalForm();
        
        ResourceHandler webHandler = new ResourceHandler();
        webHandler.setDirectoriesListed(true);
        webHandler.setResourceBase(basePath);
        webHandler.setWelcomeFiles(new String[]{"index.html"});
        
        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(webHandler);
        handlers.addHandler(context);
        
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
