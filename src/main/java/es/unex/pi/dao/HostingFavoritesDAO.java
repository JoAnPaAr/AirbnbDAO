package es.unex.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.pi.model.HostingFavorites;


public interface HostingFavoritesDAO {

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
	
	public List<HostingFavorites> getAll();

	/**
	 *Gets all the HostingFavorite that are related to a user.
	 * 
	 * @param idu
	 *            Favorite identifier
	 * 
	 * @return List of all the HostingFavorite related to a Favorite.
	 */
	public List<HostingFavorites> getAllByUser(long idu);
	
	/**
	 * Gets all the HostingFavorite that contains an specific hosting.
	 * 
	 * @param idh
	 *            Hosting Identifier
	 * 
	 * @return List of all the HostingFavorite that contains an specific hosting
	 */
	public List<HostingFavorites> getAllByHosting(long idh);

	/**
	 * Gets a HostingFavorite from the DB using idh and idu.
	 * 
	 * @param idh 
	 *            hosting identifier.
	 *            
	 * @param idu
	 *            Favorite Identifier
	 * 
	 * @return HostingFavorite with that idh and idu.
	 */
	
	public HostingFavorites get(long idh,long idu);

	/**
	 * Adds an HostingFavorite to the database.
	 * 
	 * @param HostingFavorite
	 *            HostingFavorite object with the details of the relation between the hosting and the user.
	 * 
	 * @return hosting identifier or -1 in case the operation failed.
	 */
	
	public boolean add(HostingFavorites HostingFavorite);

	/**
	 * Updates an existing HostingFavorite in the database.
	 * 
	 * @param HostingFavorite
	 *            HostingFavorite object with the new details of the existing relation between the hosting and the Favorite. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean save(HostingFavorites HostingFavorite);

	/**
	 * Deletes an HostingFavorite from the database.
	 * 
	 * @param idh
	 *            Hosting identifier.
	 *            
	 * @param idu
	 *            Favorite Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idh, long idu);
}