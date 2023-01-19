package es.unex.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * Servlet implementation class ProfileServlet
 */
@WebServlet(urlPatterns = { "/user/Profile" })
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET ProfileServlet");

		HttpSession session = request.getSession(true);
		if (session.getAttribute("currentSessionUser") != null) {
			User user = (User) session.getAttribute("currentSessionUser");
			request.setAttribute("usernameRegister", user.getUsername());
			request.setAttribute("emailRegister", user.getEmail());
			request.setAttribute("passwordRegister", user.getPassword());
		}

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/register.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean errorPasswd = false;
		logger.info("Atendiendo POST ProfileServlet");
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User user = new User();

		HttpSession session = request.getSession(true);
		user = (User) session.getAttribute("currentSessionUser");

		String contrasenaRecibida = request.getParameter("passwordRegister");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(contrasenaRecibida);

		user.setId(user.getId());
		user.setUsername(request.getParameter("usernameRegister"));
		user.setEmail(request.getParameter("emailRegister"));
		user.setPassword(contrasenaRecibida);

		if (m.matches()) {
			userDAO.save(user);
			doGet(request, response);
		} else {
			logger.info("Error en ProfileServlet. Contrasenya debil");
			errorPasswd = true;
			session = request.getSession(true);
			session.setAttribute("errorPasswd", errorPasswd);
			doGet(request, response);
		}
	}

}
