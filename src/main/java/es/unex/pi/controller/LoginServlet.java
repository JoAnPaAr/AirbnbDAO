package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

/**
 * Servlet implementation class ListHostingServlet
 */

@WebServlet(urlPatterns= {"/Login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET LoginServlet");

		RequestDispatcher view = request.getRequestDispatcher("/pages/login.jsp");
		view.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		boolean error = false;
		logger.info("Atendiendo POST LoginServlet");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User user = new User();

		user = userDAO.get(request.getParameter("usernameLogin"));

		if (user != null) {// El usuario existe
			if (request.getParameter("passwordLogin").equals(user.getPassword())) {// La contrasenya de la BD coincide con la
																			// introducida
				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", user);
				response.sendRedirect("pages/index.html");// La pagina que se debe mostrar tras loguearse
			} else {
				logger.info("Error en LoginServlet. Contrasenya equivocada");
				error = true;
				HttpSession session = request.getSession(true);
				session.setAttribute("error", error);
				doGet(request, response);
			}
		} else {// Error en el login
			logger.info("Error en LoginServlet. Usuario no existe");
			error = true;
			HttpSession session = request.getSession(true);
			session.setAttribute("error", error);
			doGet(request, response);
		}

	}

}
