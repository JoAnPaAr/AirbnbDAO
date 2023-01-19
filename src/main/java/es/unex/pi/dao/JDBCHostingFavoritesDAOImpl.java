package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.HostingFavorites;

public class JDBCHostingFavoritesDAOImpl implements HostingFavoritesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCHostingFavoritesDAOImpl.class.getName());

	@Override
	public List<HostingFavorites> getAll() {

		if (conn == null) return null;
						
		ArrayList<HostingFavorites> HostingFavoritesList = new ArrayList<HostingFavorites>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites");
						
			while ( rs.next() ) {
				HostingFavorites HostingFavorites = new HostingFavorites();
				HostingFavorites.setIdh(rs.getInt("idh"));
				HostingFavorites.setIdu(rs.getInt("idu"));
						
				HostingFavoritesList.add(HostingFavorites);
				logger.info("fetching all HostingFavorites: "+HostingFavorites.getIdh()+" "+HostingFavorites.getIdu());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HostingFavoritesList;
	}

	@Override
	public List<HostingFavorites> getAllByUser(long idu) {
		
		if (conn == null) return null;
						
		ArrayList<HostingFavorites> HostingFavoritesList = new ArrayList<HostingFavorites>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites WHERE idu="+idu);

			while ( rs.next() ) {
				HostingFavorites HostingFavorites = new HostingFavorites();
				HostingFavorites.setIdh(rs.getInt("idh"));
				HostingFavorites.setIdu(rs.getInt("idu"));

				HostingFavoritesList.add(HostingFavorites);
				logger.info("fetching all HostingFavorites by idh: "+HostingFavorites.getIdh()+"->"+HostingFavorites.getIdu());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HostingFavoritesList;
	}
	
	@Override
	public List<HostingFavorites> getAllByHosting(long idh) {
		
		if (conn == null) return null;
						
		ArrayList<HostingFavorites> HostingFavoritesList = new ArrayList<HostingFavorites>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites WHERE idh="+idh);

			while ( rs.next() ) {
				HostingFavorites HostingFavorites = new HostingFavorites();
				HostingFavorites.setIdh(rs.getInt("idh"));
				HostingFavorites.setIdu(rs.getInt("idu"));
							
				HostingFavoritesList.add(HostingFavorites);
				logger.info("fetching all HostingFavorites by idu: "+HostingFavorites.getIdu()+"-> "+HostingFavorites.getIdh());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return HostingFavoritesList;
	}
	
	
	@Override
	public HostingFavorites get(long idh,long idu) {
		if (conn == null) return null;
		
		HostingFavorites HostingFavorites = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites WHERE idh="+idh+" AND idu="+idu);			 
			if (!rs.next()) return null;
			HostingFavorites= new HostingFavorites();
			HostingFavorites.setIdh(rs.getInt("idh"));
			HostingFavorites.setIdu(rs.getInt("idu"));

			logger.info("fetching HostingFavorites by idh: "+HostingFavorites.getIdh()+"  and idu: "+HostingFavorites.getIdu());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return HostingFavorites;
	}
	
	

	@Override
	public boolean add(HostingFavorites HostingFavorites) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO HostingFavorites (idh,idu) VALUES('"+
									HostingFavorites.getIdh()+"','"+
									HostingFavorites.getIdu()+"')");
						
				logger.info("creating HostingFavorites:("+HostingFavorites.getIdh()+" "+HostingFavorites.getIdu());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(HostingFavorites HostingFavorites) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM HostingFavorites WHERE idh="+HostingFavorites.getIdh());			 
				if (!rs.next()) 
					return false;
				long idu = rs.getInt("idu");

				stmt.executeUpdate("UPDATE HostingFavorites SET idu="+HostingFavorites.getIdu()
				+" WHERE idh = "+HostingFavorites.getIdh() + " AND idu = " + idu);
				
				logger.info("updating HostingFavorites:("+HostingFavorites.getIdh()+" "+HostingFavorites.getIdu());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idh, long idu) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM HostingFavorites WHERE idh ="+idh+" AND idu="+idu);
				logger.info("deleting HostingFavorites: "+idh+" , idu="+idu);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
}
