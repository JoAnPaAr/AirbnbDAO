package es.unex.giiis.pi.resources;

import java.sql.Connection;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.HostingFavoritesDAO;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.dao.JDBCHostingFavoritesDAOImpl;
import es.unex.pi.model.Hosting;
import es.unex.pi.model.HostingFavorites;
import es.unex.pi.model.User;

@Path("/Fav")
public class FavoritoResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	// PUT that updates the table of Favorites
	@PUT
	@Path("/{hostid:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putFav(@PathParam("hostid") long hostid, @Context HttpServletRequest request) {

		// 1. You must connect to the database by using an OrderDAO object.
		Connection conn = (Connection) sc.getAttribute("dbConn");
		HostingDAO HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
		HostingFavoritesDAO FavoritesDAO = new JDBCHostingFavoritesDAOImpl();
		FavoritesDAO.setConnection(conn);

		Hosting hosting = new Hosting();
		HostingFavorites hf ;

		Response res;

		// 2. You must obtain the user that has logged into the system
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("currentSessionUser");

		hosting = HostingDAO.get(hostid);
		hf = FavoritesDAO.get(hostid, user.getId());
		if (hf == null) {
			hf = new HostingFavorites();
			// Si no existe en la tabla, se incrementa en uno el valor y se crea en la tabla
			hosting.setLikes(hosting.getLikes() + 1);
			HostingDAO.save(hosting);
			hf.setIdh(hostid);
			hf.setIdu(user.getId());
			FavoritesDAO.add(hf);
		} else {
			// Si existe en la tabla, se reduce en uno el valor y se borra de la tabla
			hosting.setLikes(hosting.getLikes() - 1);
			HostingDAO.save(hosting);
			FavoritesDAO.delete(hostid, user.getId());
		}

		res = Response // return 201
				.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(hostid)).build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(hostid)).build()).build();
		return res;

	}

}
