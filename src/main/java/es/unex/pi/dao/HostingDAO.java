package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Hosting;


public interface HostingDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Gets a route from the DB using id.
	 * 
	 * @param id
	 *            Route Identifier.
	 * 
	 * @return Route object with that id.
	 */
	public Hosting get(long id);

	/**
	 * Gets all the notes from the database.
	 * 
	 * @return List of all the routes from the database.
	 */
	public List<Hosting> getAll();
	
	/**
	 * Gets all the routes from the database that contain a text in the title.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the routes from the database that contain a text either in the title.
	 */	
	public List<Hosting> getAllBySearchTitle(String search);


	/**
	 * Gets all the routes from the database that belong to a user.
	 * 
	 * @param idu
	 *            User identifier.
	 * 
	 * @return List of all the routes from the database that belong to a user
	 */	
	public List<Hosting> getAllByUser(long idu);
	
	
	/**
	 * Gets all the routes from the database that contain a text in the description.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the routes from the database that contain a text in the description.
	 */	
	public List<Hosting> getAllBySearchDescription(String search);
	
	/**
	 * Gets all the routes from the database that contain a text either in the title or in the description.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the routes from the database that contain a text either in the title or in the description.
	 */	
	public List<Hosting> getAllBySearchAll(String search);

	/**
	 * Gets all the routes from the database that contain a text either in the title or in the description.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the routes from the database that contain a text either in the title or in the description.
	 */	
	public List<Hosting> getAllBySearchAllLikes(String search);
	
	/**
	 * Adds a route to the database.
	 * 
	 * @param route
	 *            Route object with the route details.
	 * 
	 * @return Route identifier or -1 in case the operation failed.
	 */
	
	public long add(Hosting route);

	/**
	 * Updates an existing route in the database.
	 * 
	 * @param route
	 *            Route object with the new details of the existing route.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	public boolean save(Hosting route);

	/**
	 * Deletes a route from the database.
	 * 
	 * @param id
	 *            Route identifier.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	public boolean delete(long id);
}
