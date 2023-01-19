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
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingServicesDAO;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingServicesDAOImpl;
import es.unex.pi.dao.JDBCServicesDAOImpl;
import es.unex.pi.dao.ServicesDAO;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.HostingServices;
import es.unex.pi.model.Services;
import es.unex.pi.model.User;

/**
 * Servlet implementation class CrearCasaServlet
 */
@WebServlet(urlPatterns = { "/user/CrearCasa" })
public class CrearCasaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CrearCasaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET CrearCasaServlet");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		ServicesDAO ServicesDAO = new JDBCServicesDAOImpl();
		ServicesDAO.setConnection(conn);
		List<Services> servicesList = ServicesDAO.getAll();

		request.setAttribute("listaServices", servicesList);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/create_edithouse.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo POST CrearCasaServlet");

		// Creacion de varias instancias de los objetos que se van a utilizar
		User user = new User();
		Hosting hosting = new Hosting();
		HostingCategories hc = new HostingCategories();
		HostingServices hs = new HostingServices();
		String telefonoRecibido = request.getParameter("casaTelf");

		// Se obtienen los datos de la sesion
		HttpSession session = request.getSession(true);
		user = (User) session.getAttribute("currentSessionUser");

		// Iniciamos las conexiones con los DAO que se van a utilizar
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);

		String Services[] = null;
		try {
			Services = request.getParameterValues("services");
		} catch (Exception e) {
		}

		// Comprobamos que haya al menos un service antes de crear la casa. En caso de
		// no haber al menos uno, no se crea la casa

		if (Services != null) {
			// Introducimos la casa en la BD
			hosting.setTitle(request.getParameter("casaNombre"));
			hosting.setDescription(request.getParameter("casaDesc"));
			hosting.setTelephone(request.getParameter("casaTelf"));
			hosting.setLocation(request.getParameter("casaLocation"));
			hosting.setContactEmail(request.getParameter("casaMail"));
			hosting.setLikes(0);
			hosting.setAvailable(1);
			hosting.setIdu((int) user.getId());
			hosting.setPrecio(Integer.parseInt(request.getParameter("casaPrecio")));
			// Almacenamos la casa y Obtenemos el ID de la casa
			int aux = (int) HostingDAO.add(hosting);

			// Introducimos la casa y la categoria en la BD
			hc.setIdh(aux);
			hc.setIdct(Integer.parseInt(request.getParameter("casaCategoria")));
			HostingCategoriesDAO.add(hc);

			// Introducimos los servicios en la BD
			for (int i = 0; i < Services.length; i++) {
				hs.setIdh(aux);
				hs.setIds(Integer.parseInt(Services[i]));
				HostingServicesDAO.add(hs);
			}
		}

		response.sendRedirect("./ListaUser");// La pagina que se debe mostrar tras registrarse
	}

}
