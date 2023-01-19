package es.unex.giiis.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import es.unex.giiis.pi.resources.exceptions.CustomBadRequestException;
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

@Path("/Hosting")
public class HostingResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{hostid:[0-9]+}") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public Hosting getHostingJSON(@PathParam("hostid") long hostid, @Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		Hosting host = new Hosting();
		// First = Hosting
		host = hostingDAO.get(hostid);

		if (host == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return host;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hosting> getAllHostingByUserJSON(@Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		List<Hosting> hosts = null;
		User user;
		HttpSession session = request.getSession(false);

		user = (User) session.getAttribute("currentSessionUser");
		hosts = hostingDAO.getAllByUser(user.getId());
		if (hosts == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return hosts;
		}
	}

	@GET
	@Path("/todos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hosting> getHostingsJSON(@Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		List<Hosting> hosts;

		hosts = hostingDAO.getAll();
		return hosts;

	}

	@GET
	@Path("/recomendacion/{hostid:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hosting> getHostingsJSON(@PathParam("hostid") long hostid, @Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		List<Hosting> hosts;
		Hosting hosting = hostingDAO.get(hostid);
		hosts = hostingDAO.getAll();

		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);

		List<Hosting> listarecomendad = new ArrayList<Hosting>();
		for (int i = 0; i < hosts.size(); i++) {
			if (hosts.get(i).getId() != hostid && (hosts.get(i).getPrecio() == hosting.getPrecio()
					|| hosts.get(i).getLocation().equals(hosting.getLocation()))) {
				listarecomendad.add(hosts.get(i));
			}
		}
		return listarecomendad;
	}

	@GET
	@Path("/{FilterText}/{FilterMinLikes:[0-9]+}/{FilterReserv:[0-9]+}/{FilterOrder:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Hosting> getBusquedaJSON(@PathParam("FilterText") String FilterText,
			@PathParam("FilterMinLikes") int FilterMinLikes, @PathParam("FilterReserv") int FilterReserv,
			@PathParam("FilterOrder") int FilterOrder, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		List<Hosting> listSearch;

		if (FilterText.equals("Sharkshuisers")) {
			listSearch = HostingDAO.getAllBySearchAllLikes("");
			if (FilterOrder == 1) {
				listSearch = HostingDAO.getAllBySearchAll("");
			}
		} else {
			listSearch = HostingDAO.getAllBySearchAllLikes(FilterText);
			if (FilterOrder == 1) {
				listSearch = HostingDAO.getAllBySearchAll(FilterText);
			}
		}
		// En caso de querer ordenar el resultado de la busqueda por el numero de Likes
		// se cumple el else

		long LikesMin = 0;
		if (FilterMinLikes == 999) {
			LikesMin = 0;
		} else {
			LikesMin = FilterMinLikes;
		}

		ArrayList<Hosting> listMostrar = new ArrayList<Hosting>();

		// Se guardan los valores de la busqueda que cumplan los filtros definidos por
		// el usuario
		for (int i = 0; i < listSearch.size(); i++) {
			// Si cumple el minimo de likes se cumple el if
			if (listSearch.get(i).getLikes() >= LikesMin) {
				// Comprueba si importa que la casa este reservada o no reservada
				if (FilterReserv == 2) {
					listMostrar.add(listSearch.get(i));
				} else {
					// Comprueba que la disponibilidad de la casa sea la que busque el usuario
					if (listSearch.get(i).getAvailable() == FilterReserv) {
						listMostrar.add(listSearch.get(i));
					}
				}
			}
		}

		return listMostrar;
	}

	// POST that receives a new user
	@POST
	@Path("/{catid:[0-9]+}") // TODO Comlete the path
	@Consumes(MediaType.APPLICATION_JSON)
	public Long post(@PathParam("catid") long catid, Hosting hostCreate, @Context HttpServletRequest request) {

		// Connect to the database.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		Hosting hosting = new Hosting();
		HostingCategories hc = new HostingCategories();
		User user = new User();

		// Se obtienen los datos de la sesion
		HttpSession session = request.getSession(false);
		user = (User) session.getAttribute("currentSessionUser");

		// Iniciamos las conexiones con los DAO que se van a utilizar
		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);

		// Comprobamos que haya al menos un service antes de crear la casa. En caso de
		// no haber al menos uno, no se crea la casa

		long id;
		// Introducimos la casa en la BD

		hosting.setTitle(hostCreate.getTitle());
		hosting.setDescription(hostCreate.getDescription());
		hosting.setTelephone(hostCreate.getTelephone());
		hosting.setLocation(hostCreate.getLocation());
		hosting.setContactEmail(hostCreate.getContactEmail());
		hosting.setLikes(0);
		hosting.setAvailable(1);
		hosting.setIdu((int) user.getId());
		hosting.setPrecio(hostCreate.getPrecio());

		// save Hosting in DB
		id = hostingDAO.add(hosting);

		// Introducimos la casa y la categoria en la BD
		hc.setIdh(id);
		hc.setIdct(catid);
		HostingCategoriesDAO.add(hc);

		return id;
	}

	@PUT
	@Path("/{hostid:[0-9]+}/{catid:[0-9]+}") // TODO Comlete the path
	@Consumes(MediaType.APPLICATION_JSON)
	public Long put(@PathParam("hostid") long hostid, @PathParam("catid") long catid, Hosting hostUpdate,
			@Context HttpServletRequest request) throws Exception {

		// Connect to the database.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		Hosting hosting = new Hosting();
		HostingCategories hc = new HostingCategories();
		User user = new User();

		// Se obtienen los datos de la sesion
		HttpSession session = request.getSession(false);
		user = (User) session.getAttribute("currentSessionUser");

		// Iniciamos las conexiones con los DAO que se van a utilizar
		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);

		hosting = hostingDAO.get(hostid);
		if (hosting.getIdu() == user.getId()) {
			// Introducimos la casa en la BD

			hosting.setTitle(hostUpdate.getTitle());
			hosting.setDescription(hostUpdate.getDescription());
			hosting.setTelephone(hostUpdate.getTelephone());
			hosting.setLocation(hostUpdate.getLocation());
			hosting.setContactEmail(hostUpdate.getContactEmail());
			hosting.setAvailable(hostUpdate.getAvailable());
			hosting.setIdu((int) user.getId());
			hosting.setPrecio(hostUpdate.getPrecio());
			hosting.setAvailable(hostUpdate.getAvailable());

			// save Hosting in DB
			hostingDAO.save(hosting);

			// Introducimos la casa y la categoria en la BD
			hc.setIdh(hostid);
			hc.setIdct(catid);
			HostingCategoriesDAO.save(hc);

		} else
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		return hostid;
	}

	@DELETE
	@Path("/{hostid:[0-9]+}") // TODO Comlete the path
	public Response deleteUser(@PathParam("hostid") long hostid, @Context HttpServletRequest request) {

		// 1. You must connect to the database by using an OrderDAO object.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		Hosting hosting = new Hosting();

		HostingDAO hostingDAO = new JDBCHostingDAOImpl();
		hostingDAO.setConnection(conn);
		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);
		HostingFavoritesDAO HostingFavoritesDAO = new JDBCHostingFavoritesDAOImpl();
		HostingFavoritesDAO.setConnection(conn);

		hosting = hostingDAO.get(hostid);
		if (hosting != null) {
			hostingDAO.delete(hostid);

			List<HostingCategories> Categories = HostingCategoriesDAO.getAllByHosting(hostid);
			for (int i = 0; i < Categories.size(); i++) {
				HostingCategoriesDAO.delete(hostid, Categories.get(i).getIdct());
			}

			List<HostingServices> Services = HostingServicesDAO.getAllByHosting(hostid);
			for (int i = 0; i < Services.size(); i++) {
				HostingServicesDAO.delete(hostid, Services.get(i).getIds());
			}

			List<HostingFavorites> Favorites = HostingFavoritesDAO.getAllByHosting(hostid);
			for (int i = 0; i < Favorites.size(); i++) {
				HostingFavoritesDAO.delete(hostid, Favorites.get(i).getIdu());
			}

			return Response.noContent().build(); // 204 no content
		} else
			throw new CustomBadRequestException("Error in user or id");

	}

}
