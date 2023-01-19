package es.unex.pi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingServicesDAO;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingServicesDAOImpl;
import es.unex.pi.dao.JDBCServicesDAOImpl;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.ServicesDAO;
import es.unex.pi.dao.UserDAO;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.HostingServices;
import es.unex.pi.model.Services;
import es.unex.pi.model.User;

/**
 * Servlet implementation class DetalleServlet
 */
@WebServlet(urlPatterns = { "/user/Detalles" })
public class DetalleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetalleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET DetalleServlet");

		int idHosting = -1;
		try {
			idHosting = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {

		}

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		HostingServicesDAO hostingServicesDAO = new JDBCHostingServicesDAOImpl();
		hostingServicesDAO.setConnection(conn);
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		ServicesDAO ServicesDAO = new JDBCServicesDAOImpl();
		ServicesDAO.setConnection(conn);
		HostingCategoriesDAO HostingCategories = new JDBCHostingCategoriesDAOImpl();
		HostingCategories.setConnection(conn);

		Hosting hosting = HostingDAO.get(idHosting);
		User user = userDAO.get(hosting.getIdu());

		List<HostingServices> servicesCasa = hostingServicesDAO.getAllByHosting(idHosting);
		List<Services> listServices = ServicesDAO.getAll();
		List<Services> servicesAux = new ArrayList<Services>();

		for (int i = 0; i < listServices.size(); i++) {
			for (int j = 0; j < servicesCasa.size(); j++) {
				if (servicesCasa.get(j).getIds() == listServices.get(i).getId()) {
					servicesAux.add(listServices.get(i));
				}
			}
		}

		List<Hosting> listaTodo = HostingDAO.getAll();
		List<Hosting> listarecomendad = new ArrayList<Hosting>();

		for (int i = 0; i < listaTodo.size(); i++) {
			if (listaTodo.get(i).getPrecio() == hosting.getPrecio()
					|| listaTodo.get(i).getLocation().equals(hosting.getLocation())) {
				listarecomendad.add(listaTodo.get(i));

			}
		}

		request.setAttribute("user", user);
		request.setAttribute("Hosting", hosting);
		request.setAttribute("listaServices", servicesAux);
		request.setAttribute("HostingsList", listarecomendad);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/details.jsp");
		view.forward(request, response);
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
