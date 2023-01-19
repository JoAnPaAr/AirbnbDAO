package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(urlPatterns = { "/Register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET RegisterServlet");
		
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
		logger.info("Atendiendo POST RegisterServlet");
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User user = new User();
		String contrasenaRecibida = request.getParameter("passwordRegister");
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(contrasenaRecibida);

		user.setUsername(request.getParameter("usernameRegister"));
		user.setEmail(request.getParameter("emailRegister"));
		user.setPassword(contrasenaRecibida);
		if (m.matches() && contrasenaRecibida.equals(request.getParameter("passwordconfirmRegister"))) {
			userDAO.add(user);
			response.sendRedirect("./Login");// La pagina que se debe mostrar tras registrarse
		} else {
			logger.info("Error en RegisterServlet. Contrasenya debil / Distinta de confirmar");
			errorPasswd = true;
			HttpSession session = request.getSession(true);
			session.setAttribute("errorPasswd", errorPasswd);
			doGet(request, response);
		}
	}

}
