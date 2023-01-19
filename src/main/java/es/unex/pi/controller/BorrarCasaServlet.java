package es.unex.pi.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.HostingFavorites;
import es.unex.pi.model.HostingServices;

/**
 * Servlet implementation class BorrarCasaServlet
 */
@WebServlet(urlPatterns = { "/user/BorrarCasa" })
public class BorrarCasaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BorrarCasaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET BorrarCasa");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);
		HostingFavoritesDAO HostingFavoritesDAO = new JDBCHostingFavoritesDAOImpl();
		HostingFavoritesDAO.setConnection(conn);

		int idHosting = -1;
		try {
			idHosting = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {

		}
		HostingDAO.delete(idHosting);
		
		List<HostingCategories> Categories = HostingCategoriesDAO.getAllByHosting(idHosting);
		for (int i = 0; i < Categories.size(); i++) {
			HostingCategoriesDAO.delete(idHosting, Categories.get(i).getIdct());
		}

		List<HostingServices> Services = HostingServicesDAO.getAllByHosting(idHosting);
		for (int i = 0; i < Services.size(); i++) {
			HostingServicesDAO.delete(idHosting, Services.get(i).getIds());
		}
		
		List<HostingFavorites> Favorites = HostingFavoritesDAO.getAllByHosting(idHosting);
		for (int i = 0; i < Favorites.size(); i++) {
			HostingFavoritesDAO.delete(idHosting, Favorites.get(i).getIdu());
		}
		response.sendRedirect("./ListaUser");

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
