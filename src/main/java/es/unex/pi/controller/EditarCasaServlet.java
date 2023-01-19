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
@WebServlet(urlPatterns = { "/user/EditarCasa" })
public class EditarCasaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditarCasaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET EditarCasaServlet");

		int idHosting = -1;
		try {
			idHosting = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {

		}

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		HostingCategoriesDAO hostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		hostingCategoriesDAO.setConnection(conn);
		HostingCategories category = hostingCategoriesDAO.getAllByHosting(idHosting).get(0);
		Hosting hosting = HostingDAO.get(idHosting);
		HostingServicesDAO hostingServicesDAO = new JDBCHostingServicesDAOImpl();
		hostingServicesDAO.setConnection(conn);
		ServicesDAO ServicesDAO = new JDBCServicesDAOImpl();
		ServicesDAO.setConnection(conn);
		List<Services> servicesList = ServicesDAO.getAll();

		request.setAttribute("casaNombre", hosting.getTitle());
		request.setAttribute("casaDesc", hosting.getDescription());
		request.setAttribute("casaTelf", hosting.getTelephone());
		request.setAttribute("casaMail", hosting.getContactEmail());
		request.setAttribute("casaLocation", hosting.getLocation());
		request.setAttribute("casaPrecio", hosting.getPrecio());
		request.setAttribute("casaCategoria", category.getIdct());
		request.setAttribute("casaDispon", hosting.getAvailable());
		request.setAttribute("casaId", idHosting);

		List<HostingServices> servicesAux = hostingServicesDAO.getAllByHosting(idHosting);

		request.setAttribute("listaServices", servicesList);
		request.setAttribute("servicesMarcados", servicesAux);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/create_edithouse.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo POST EditarCasaServlet");

		int idHosting = -1;
		try {
			idHosting = Integer.parseInt(request.getParameter("casaId"));
		} catch (Exception e) {
		}

		// Creacion de varias instancias de los objetos que se van a utilizar
		HostingCategories hc = new HostingCategories();
		HostingServices hs = new HostingServices();

		// Se obtienen los datos de la sesion
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("currentSessionUser");

		// Iniciamos las conexiones con los DAO que se van a utilizar
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);
		Hosting hosting = HostingDAO.get(idHosting);

		String Services[] = null;
		try {
			Services = request.getParameterValues("services");
		} catch (Exception e) {
		}

		// Comprobamos que haya al menos un service antes de actualizar la casa. En caso
		// de no haber ningun servicio, no se guardan los cambios
		if (Services != null) {

			// Comprobamos si el usuario actual es el propietario de la casa antes de
			// modificar la informacion de la casa. En caso de no serlo, no se guardan los
			// cambios
			if (user.getId() == hosting.getIdu()) {
				// Actualizamos la casa en la BD
				hosting.setTitle(request.getParameter("casaNombre"));
				hosting.setDescription(request.getParameter("casaDesc"));
				hosting.setTelephone(request.getParameter("casaTelf"));
				hosting.setLocation(request.getParameter("casaLocation"));
				hosting.setContactEmail(request.getParameter("casaMail"));
				hosting.setAvailable(Integer.parseInt(request.getParameter("casaDispon")));
				hosting.setIdu((int) user.getId());
				hosting.setPrecio(Integer.parseInt(request.getParameter("casaPrecio")));
				// Almacenamos la casa
				HostingDAO.save(hosting);

				// Actualizamos la casa y la categoria en la BD
				hc.setIdh(hosting.getId());
				hc.setIdct(Integer.parseInt(request.getParameter("casaCategoria")));
				HostingCategoriesDAO.save(hc);

				// Vaciamos de la BD los servicios anteriores para poder introducir las
				// actualizaciones despues
				List<HostingServices> lServices = HostingServicesDAO.getAllByHosting(hosting.getId());
				for (int i = 0; i < lServices.size(); i++) {
					HostingServicesDAO.delete(hosting.getId(), lServices.get(i).getIds());
				}

				// Introducimos los servicios en la BD
				for (int i = 0; i < Services.length; i++) {
					hs.setIdh(hosting.getId());
					hs.setIds(Integer.parseInt(Services[i]));
					HostingServicesDAO.add(hs);
				}
			}
		}

		response.sendRedirect("./ListaUser");// La pagina que se debe mostrar tras registrarse
	}

}
