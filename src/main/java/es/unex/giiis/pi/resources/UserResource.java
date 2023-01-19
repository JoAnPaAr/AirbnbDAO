package es.unex.giiis.pi.resources;

import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

@Path("/User")
public class UserResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserJSON(@Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("currentSessionUser");
		if (user == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return user;
		}
	}

	@GET
	@Path("/todos") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersJSON(@Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		List<User> user = null;

		user = userDAO.getAll();
		if (user == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return user;
		}
	}

	@GET
	@Path("/{userid:[0-9]+}") // TODO: Complete the path
	@Produces(MediaType.APPLICATION_JSON)
	public User getUsersJSON(@PathParam("userid") long userid, @Context HttpServletRequest request) {

		// Connect to the database
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);

		User user = userDAO.get(userid);
		if (user == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			return user;
		}
	}

	// POST that receives a new user
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUser(User userCreated, @Context HttpServletRequest request) {

		// Connect to the database.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User user = new User();
		long id = -1;

		Response res;
		if (userDAO.get(userCreated.getUsername()) != null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
			String contrasenaRecibida = userCreated.getPassword();
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(contrasenaRecibida);

			user.setUsername(userCreated.getUsername());
			user.setEmail(userCreated.getEmail());
			user.setPassword(contrasenaRecibida);

			if (m.matches()) {
				// save User in DB
				id = userDAO.add(user);
			}
			user = userDAO.get(id);
			HttpSession session = request.getSession();
			session.setAttribute("currentSessionUser", user);
			res = Response // return 201
					.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
					.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();
		}
		return res;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putUser(User userUpdate, @Context HttpServletRequest request) throws Exception {

		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		// 1. You must connect to the database by using an OrderDAO object.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		// 2. You must obtain the user that has logged into the system
		HttpSession session = request.getSession(false);
		User sessionuser = (User) session.getAttribute("currentSessionUser");
		User user = userDAO.get(userUpdate.getId());

		String contrasenaRecibida = userUpdate.getPassword();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(contrasenaRecibida);

		Response response = null;
		// We check that the User exists
		if (user != null) {
			if (sessionuser.getId() == user.getId()) {
				// 3. If the password is valid
				if (m.matches()) {
					user.setId(userUpdate.getId());
					user.setUsername(userUpdate.getUsername());
					user.setEmail(userUpdate.getEmail());
					user.setPassword(contrasenaRecibida);
					userDAO.save(user);
					session.setAttribute("currentSessionUser", user);
				} else {
					throw new CustomBadRequestException("Error in user or id");
				}
			}
		} else
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		return response;
	}

	@DELETE
	public Response deleteUser(@Context HttpServletRequest request) {
		// 1. You must connect to the database by using an OrderDAO object.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		User user = null;

		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		HostingCategoriesDAO HostingCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingCategoriesDAO.setConnection(conn);
		HostingServicesDAO HostingServicesDAO = new JDBCHostingServicesDAOImpl();
		HostingServicesDAO.setConnection(conn);
		HostingFavoritesDAO HostingFavoritesDAO = new JDBCHostingFavoritesDAOImpl();
		HostingFavoritesDAO.setConnection(conn);

		// 2. You must obtain the user that has logged into the system
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("currentSessionUser");

		if (user != null) {
			List<Hosting> listHost = HostingDAO.getAllByUser(user.getId());

			List<HostingFavorites> Favorites = HostingFavoritesDAO.getAllByUser(user.getId());

			for (int i = 0; i < Favorites.size(); i++) {
				Hosting hosting = HostingDAO.get(Favorites.get(i).getIdh());
				hosting.setLikes(hosting.getLikes() - 1);
				HostingDAO.save(hosting);
				HostingFavoritesDAO.delete(Favorites.get(i).getIdh(), user.getId());
			}

			for (int j = 0; j < listHost.size(); j++) {
				HostingDAO.delete(listHost.get(j).getId());

				List<HostingCategories> Categories = HostingCategoriesDAO.getAllByHosting(listHost.get(j).getId());
				for (int i = 0; i < Categories.size(); i++) {
					HostingCategoriesDAO.delete(listHost.get(j).getId(), Categories.get(i).getIdct());
				}

				List<HostingServices> Services = HostingServicesDAO.getAllByHosting(listHost.get(j).getId());
				for (int i = 0; i < Services.size(); i++) {
					HostingServicesDAO.delete(listHost.get(j).getId(), Services.get(i).getIds());
				}
			}
			userDAO.delete(user.getId());
			session.invalidate();

			return Response.noContent().build(); // 204 no content
		} else
			throw new CustomBadRequestException("Error in user or id");

	}

}
