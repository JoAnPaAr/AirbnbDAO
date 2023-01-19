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

import es.unex.pi.dao.CategoryDAO;
import es.unex.pi.dao.JDBCCategoryDAOImpl;
import es.unex.pi.model.Category;

@Path("/Category")
public class CategoryResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Path("/{categorid:[0-9]+}") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public Category getCategoryJSON(@PathParam("categorid") long categorid, @Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		categoryDAO.setConnection(conn);
		Category category = null;

		category = categoryDAO.get(categorid);
		if (category == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return category;
		}
	}

	@GET
	@Path("/todos") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategoryJSON(@Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CategoryDAO categoryDAO = new JDBCCategoryDAOImpl();
		categoryDAO.setConnection(conn);
		List<Category> categories = null;

		categories = categoryDAO.getAll();
		if (categories == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return categories;
		}
	}

}
