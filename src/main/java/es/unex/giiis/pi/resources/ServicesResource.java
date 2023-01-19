package es.unex.giiis.pi.resources;

import java.sql.Connection;
import java.util.List;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import es.unex.pi.dao.JDBCServicesDAOImpl;
import es.unex.pi.dao.ServicesDAO;
import es.unex.pi.model.Services;

@Path("/Services")
public class ServicesResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{serviceid:[0-9]+}") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public Services getServiceJSON(@PathParam("serviceid") long serviceid, @Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		ServicesDAO servicesDAO = new JDBCServicesDAOImpl();
		servicesDAO.setConnection(conn);
		Services service = new Services();

		service = servicesDAO.get(serviceid);
		if (service == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return service;
		}
	}

	@GET
	@Path("/todos") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public List<Services> getServicesJSON(@Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		ServicesDAO servicesDAO = new JDBCServicesDAOImpl();
		servicesDAO.setConnection(conn);
		List<Services> Services = null;

		Services = servicesDAO.getAll();
		if (Services == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return Services;
		}
	}

}
