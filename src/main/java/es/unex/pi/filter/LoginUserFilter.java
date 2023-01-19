package es.unex.pi.filter;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */

//TODO: Add the necessary code to filter the requests to the REST services

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/user/*", "/rest/*" })
public class LoginUserFilter implements Filter {
	private static final Logger logger = Logger.getLogger(Filter.class.getName());

	/**
	 * Default constructor.
	 */
	public LoginUserFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = ((HttpServletRequest) request).getSession(true);
		logger.info("checking user in session");

		if (!(req.getMethod().equals("GET") && req.getRequestURI().contains("/AirbnbDAO/rest/User"))
				&& session.getAttribute("currentSessionUser") == null
				&& !(req.getMethod().equals("POST") && req.getRequestURI().contains("/AirbnbDAO/rest/User"))) {
			res.sendRedirect(req.getContextPath() + "/Login");
		} else
			// pass the request along the filter chain
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
