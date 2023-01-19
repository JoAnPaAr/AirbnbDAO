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

import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.model.Hosting;

/**
 * Servlet implementation class BuscarServlet
 */
@WebServlet(urlPatterns = { "/user/Buscar" })
public class BuscarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BuscarServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Atendiendo GET BuscarServlet");

		request.setAttribute("buscaLikes", 0);
		request.setAttribute("buscaDispon", "2");
		request.setAttribute("buscaOrden", "0");

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Buscar.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Atendiendo POST BuscarServlet");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);

		List<Hosting> listSearch = HostingDAO.getAllBySearchAll(request.getParameter("buscaCadena"));
		// En caso de querer ordenar el resultado de la busqueda por el numero de Likes
		// se cumple el else
		if (request.getParameter("buscaOrden").equals("1")) {
			listSearch = HostingDAO.getAllBySearchAllLikes(request.getParameter("buscaCadena"));
		}

		int meGustas = 0;
		try {
			meGustas = Integer.parseInt(request.getParameter("buscaLikes"));
		} catch (Exception e) {

		}
		
		List<Hosting> listMostrar = new ArrayList<Hosting>();

		// Se guardan los valores de la busqueda que cumplan los filtros definidos por
		// el usuario
		for (int i = 0; i < listSearch.size(); i++) {
			// Si cumple el minimo de likes se cumple el if
			if (listSearch.get(i).getLikes() >= meGustas) {
				// Comprueba si importa que la casa este reservada o no reservada
				if (Integer.parseInt(request.getParameter("buscaDispon")) == 2) {
					listMostrar.add(listSearch.get(i));
				} else {
					// Comprueba que la disponibilidad de la casa sea la que busque el usuario
					if (listSearch.get(i).getAvailable() == Integer.parseInt(request.getParameter("buscaDispon"))) {
						listMostrar.add(listSearch.get(i));
					}
				}
			}
		}



		request.setAttribute("buscaLikes", meGustas);
		request.setAttribute("buscaDispon", request.getParameter("buscaDispon"));
		request.setAttribute("buscaOrden", request.getParameter("buscaOrden"));
		request.setAttribute("HostingsBuscado", listMostrar);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/Buscar.jsp");
		view.forward(request, response);
	}

}
