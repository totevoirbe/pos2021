package be.panidel.restServer;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class PosServer {
	public static void main(String[] args) throws Exception {
		ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContextHandler.setContextPath("/");

		FilterHolder filterHolder = servletContextHandler.addFilter(CorsFilter.class, "/*",
				EnumSet.of(DispatcherType.REQUEST));
		filterHolder.setInitParameter("allowedOrigins", "*");

		Server jettyServer = new Server(8080);
		jettyServer.setHandler(servletContextHandler);

		ServletHolder jerseyServlet = servletContextHandler
				.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		// Tells the Jersey Servlet which REST service/class to load.
		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", PosService.class.getCanonicalName());

		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}
}
