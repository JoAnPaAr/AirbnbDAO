package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import es.unex.pi.dao.HostingFavoritesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingFavoritesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingFavorites;
import es.unex.pi.model.User;

/**
 * Servlet implementation class DarQuitarFavoritosServlet
 */

@WebServlet(urlPatterns = { "/user/Favorito" })
public class DarQuitarFavoritosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DarQuitarFavoritosServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET DarQuitarFavoritosServlet");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		HostingFavoritesDAO FavoritesDAO = new JDBCHostingFavoritesDAOImpl();
		FavoritesDAO.setConnection(conn);

		User user = new User();
		Hosting hosting = new Hosting();
		HostingFavorites hf = new HostingFavorites();

		HttpSession session = request.getSession(true);
		user = (User) session.getAttribute("currentSessionUser");

		long idHosting = -1;
		try {
			idHosting = Long.parseLong(request.getParameter("id"));
		} catch (Exception e) {

		}

		hosting = HostingDAO.get(idHosting);
		hf = FavoritesDAO.get(idHosting, user.getId());
		if (hf == null) {
			hf = new HostingFavorites();
			// Si no existe en la tabla, se incrementa en uno el valor y se crea en la tabla
			hosting.setLikes(hosting.getLikes() + 1);
			HostingDAO.save(hosting);
			hf.setIdh(idHosting);
			hf.setIdu(user.getId());
			FavoritesDAO.add(hf);
		} else {
			// Si existe en la tabla, se reduce en uno el valor y se borra de la tabla
			hosting.setLikes(hosting.getLikes() - 1);
			HostingDAO.save(hosting);
			FavoritesDAO.delete(idHosting, user.getId());
		}

		response.sendRedirect("./ListaCasas");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
