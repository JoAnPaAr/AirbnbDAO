package es.unex.giiis.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
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
import es.unex.pi.model.Category;
import es.unex.pi.model.HostingCategories;
import es.unex.pi.model.HostingServices;
import es.unex.pi.model.Services;

@Path("/HostCat")
public class HostingCategoriesResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{hostid:[0-9]+}") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public List<HostingCategories> getHostingCategoriesHostJSON(@PathParam("hostid") long hostingid,
			@Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingCategoriesDAO hostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		hostingCategoriesDAO.setConnection(conn);
		List<HostingCategories> categories = new ArrayList<HostingCategories>();

		categories = hostingCategoriesDAO.getAllByHosting(hostingid);
		if (categories == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return categories;
		}
	}

}
