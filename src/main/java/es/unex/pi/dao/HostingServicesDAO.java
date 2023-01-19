package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.HostingServices;


public interface HostingServicesDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets all the hosting and the categories related to them from the database.
	 * 
	 * @return List of all the hosting and the categories related to them from the database.
	 */
	
	public List<HostingServices> getAll();

	/**
	 *Gets all the HostingService that are related to a category.
	 * 
	 * @param ids
	 *            Category identifier
	 * 
	 * @return List of all the HostingService related to a category.
	 */
	public List<HostingServices> getAllByService(long ids);
	
	/**
	 * Gets all the HostingService that contains an specific hosting.
	 * 
	 * @param idh
	 *            Hosting Identifier
	 * 
	 * @return List of all the HostingService that contains an specific hosting
	 */
	public List<HostingServices> getAllByHosting(long idh);

	/**
	 * Gets a HostingService from the DB using idh and ids.
	 * 
	 * @param idr 
	 *            hosting identifier.
	 *            
	 * @param ids
	 *            Category Identifier
	 * 
	 * @return HostingService with that idh and ids.
	 */
	
	public HostingServices get(long idh,long ids);

	/**
	 * Adds an HostingService to the database.
	 * 
	 * @param HostingService
	 *            HostingService object with the details of the relation between the hosting and the category.
	 * 
	 * @return hosting identifier or -1 in case the operation failed.
	 */
	
	public boolean add(HostingServices HostingService);

	/**
	 * Updates an existing HostingService in the database.
	 * 
	 * @param HostingService
	 *            HostingService object with the new details of the existing relation between the hosting and the category. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean save(HostingServices HostingService);

	/**
	 * Deletes an HostingService from the database.
	 * 
	 * @param idh
	 *            Hosting identifier.
	 *            
	 * @param ids
	 *            Category Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idh, long ids);
}