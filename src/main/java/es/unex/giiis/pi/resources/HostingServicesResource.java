package es.unex.giiis.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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
import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingServicesDAO;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingServicesDAOImpl;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.HostingServices;
import es.unex.pi.model.Services;
import es.unex.pi.model.User;

@Path("/HostServ")
public class HostingServicesResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{hostid:[0-9]+}") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public List<HostingServices> getHostingServicesHostJSON(@PathParam("hostid") long hostid,
			@Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingServicesDAO hostingServicesDAO = new JDBCHostingServicesDAOImpl();
		hostingServicesDAO.setConnection(conn);
		List<HostingServices> services = new ArrayList<HostingServices>();

		services = hostingServicesDAO.getAllByHosting(hostid);
		if (services == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return services;
		}
	}

	// POST that receives a new user
	@POST
	@Path("/{hostid:[0-9]+}") // TODO Comlete the path
	@Consumes(MediaType.APPLICATION_JSON)
	public Long post(@PathParam("hostid") long hostid, ArrayList<Long> servicios, @Context HttpServletRequest request) {

		// Connect to the database.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingServices hs = new HostingServices();
		
		System.out.println("------------------------------------------------ANYADIENDO SERVICE" +servicios.size());
		// Iniciamos las conexiones con los DAO que se van a utilizar
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);

		// Introducimos los servicios en la BD
		for (int i = 0; i < servicios.size(); i++) {
			if (servicios.get(i) != -1 && servicios.get(i) != null) {
				hs.setIdh(hostid);
				hs.setIds(servicios.get(i));
				HostingServicesDAO.add(hs);
				System.out.println("------------------------------------------------ANYADIENDO SERVICE" + servicios.get(i));
			}
		}
		return hostid;
	}
	
	// POST that receives a new user
	@PUT
	@Path("/{hostid:[0-9]+}") // TODO Comlete the path
	@Consumes(MediaType.APPLICATION_JSON)
	public Long put(@PathParam("hostid") long hostid, ArrayList<Long> servicios, @Context HttpServletRequest request) {

		// Connect to the database.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingServices hs = new HostingServices();
		
		System.out.println("------------------------------------------------ANYADIENDO SERVICE" +servicios.size());
		// Iniciamos las conexiones con los DAO que se van a utilizar
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);
		
		List<HostingServices> lServices = HostingServicesDAO.getAllByHosting(hostid);
		for (int i = 0; i < lServices.size(); i++) {
			HostingServicesDAO.delete(hostid, lServices.get(i).getIds());
		}
		
		// Introducimos los servicios en la BD
		for (int i = 0; i < servicios.size(); i++) {
			if (servicios.get(i) != -1 && servicios.get(i) != null) {
				hs.setIdh(hostid);
				hs.setIds(servicios.get(i));
				HostingServicesDAO.add(hs);
				System.out.println("------------------------------------------------ANYADIENDO SERVICE" + servicios.get(i));
			}
		}
		return hostid;
	}

	@DELETE
	@Path("/{hostid:[0-9]+}") // TODO Comlete the path
	@Consumes(MediaType.APPLICATION_JSON)
	public Long delete(@PathParam("hostid") long hostid, @Context HttpServletRequest request) throws Exception {

		// Connect to the database.
		Connection conn = (Connection) sc.getAttribute("dbConn");

		// Iniciamos las conexiones con los DAO que se van a utilizar
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);

		// Vaciamos de la BD los servicios anteriores para poder introducir las
		// actualizaciones despues
		List<HostingServices> lServices = HostingServicesDAO.getAllByHosting(hostid);
		for (int i = 0; i < lServices.size(); i++) {
			HostingServicesDAO.delete(hostid, lServices.get(i).getIds());
		}
		return hostid;
	}

}
