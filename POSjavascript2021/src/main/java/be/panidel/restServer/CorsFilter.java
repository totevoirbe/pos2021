package be.panidel.restServer;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Philippe Franzen
 *
 *         filtre positionné en amont des services pour authoriser le
 *         cross-domain en front = services exposés par diverses sources dans
 *         une même page web
 *
 *         ATTENTION : ceci peut créer une faille de sécurité
 */
@Singleton
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		if (response instanceof HttpServletResponse) {
			HttpServletResponse alteredResponse = ((HttpServletResponse) response);
			addCorsHeaderResponse(alteredResponse);
		}

		filterChain.doFilter(request, response);
	}

	private void addCorsHeaderResponse(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers",
				"X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Max-Age", "1728000");
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}