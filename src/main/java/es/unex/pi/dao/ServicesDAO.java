package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.Services;

public interface ServicesDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets a Services from the DB using id.
	 * 
	 * @param id Services Identifier.
	 * 
	 * @return Services object with that id.
	 */
	public Services get(long id);

	/**
	 * Gets a Services from the DB using name.
	 * 
	 * @param name Services name.
	 * 
	 * @return Services object with that name.
	 */
	public Services get(String name);

	/**
	 * Gets all the categories from the database.
	 * 
	 * @return List of all the categories from the database.
	 */
	public List<Services> getAll();

	/**
	 * Gets all the categories from the database that contain a text in the name.
	 * 
	 * @param search Search string .
	 * 
	 * @return List of all the categories from the database that contain a text in
	 *         the name.
	 */
	public List<Services> getAllBySearchName(String search);

	/**
	 * Adds a Services to the database.
	 * 
	 * @param Services Services object with the Services details.
	 * 
	 * @return Services identifier or -1 in case the operation failed.
	 */
}
