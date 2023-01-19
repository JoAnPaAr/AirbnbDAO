package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingFavoritesDAO;
import es.unex.pi.dao.HostingServicesDAO;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingFavoritesDAOImpl;
import es.unex.pi.dao.JDBCHostingServicesDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.HostingFavorites;
import es.unex.pi.model.HostingServices;
import es.unex.pi.model.User;

/**
 * Servlet implementation class EliminarPersonaServlet
 */
@WebServlet(urlPatterns = { "/user/EliminarPersona" })
public class EliminarPersonaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EliminarPersonaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET EliminarPersona");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User user = new User();

		HttpSession session = request.getSession();
		user = (User) session.getAttribute("currentSessionUser");
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);
		HostingFavoritesDAO HostingFavoritesDAO = new JDBCHostingFavoritesDAOImpl();
		HostingFavoritesDAO.setConnection(conn);

		List<Hosting> listHost = HostingDAO.getAllByUser(user.getId());

		List<HostingFavorites> Favorites = HostingFavoritesDAO.getAllByUser(user.getId());

		for (int i = 0; i < Favorites.size(); i++) {
			Hosting hosting = HostingDAO.get(Favorites.get(i).getIdh());
			hosting.setLikes(hosting.getLikes() - 1);
			HostingDAO.save(hosting);
			HostingFavoritesDAO.delete(Favorites.get(i).getIdh(), user.getId());
		}

		for (int j = 0; j < listHost.size(); j++) {
			HostingDAO.delete(listHost.get(j).getId());

			List<HostingCategories> Categories = HostingCategoriesDAO.getAllByHosting(listHost.get(j).getId());
			for (int i = 0; i < Categories.size(); i++) {
				HostingCategoriesDAO.delete(listHost.get(j).getId(), Categories.get(i).getIdct());
			}

			List<HostingServices> Services = HostingServicesDAO.getAllByHosting(listHost.get(j).getId());
			for (int i = 0; i < Services.size(); i++) {
				HostingServicesDAO.delete(listHost.get(j).getId(), Services.get(i).getIds());
			}
		}

		userDAO.delete(user.getId());

		response.sendRedirect("./Login");

		session.invalidate();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
