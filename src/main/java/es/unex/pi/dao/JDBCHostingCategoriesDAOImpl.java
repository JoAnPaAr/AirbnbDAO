package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.HostingCategories;

public class JDBCHostingCategoriesDAOImpl implements HostingCategoriesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCHostingCategoriesDAOImpl.class.getName());

	@Override
	public List<HostingCategories> getAll() {

		if (conn == null) return null;
						
		ArrayList<HostingCategories> HostingCategoriesList = new ArrayList<HostingCategories>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingCategories");
						
			while ( rs.next() ) {
				HostingCategories HostingCategories = new HostingCategories();
				HostingCategories.setIdh(rs.getInt("idh"));
				HostingCategories.setIdct(rs.getInt("idct"));
						
				HostingCategoriesList.add(HostingCategories);
				logger.info("fetching all HostingCategories: "+HostingCategories.getIdh()+" "+HostingCategories.getIdct());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HostingCategoriesList;
	}

	@Override
	public List<HostingCategories> getAllByCategory(long idct) {
		
		if (conn == null) return null;
						
		ArrayList<HostingCategories> HostingCategoriesList = new ArrayList<HostingCategories>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingCategories WHERE idct="+idct);

			while ( rs.next() ) {
				HostingCategories HostingCategories = new HostingCategories();
				HostingCategories.setIdh(rs.getInt("idh"));
				HostingCategories.setIdct(rs.getInt("idct"));

				HostingCategoriesList.add(HostingCategories);
				logger.info("fetching all HostingCategories by idh: "+HostingCategories.getIdh()+"->"+HostingCategories.getIdct());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HostingCategoriesList;
	}
	
	@Override
	public List<HostingCategories> getAllByHosting(long idh) {
		
		if (conn == null) return null;
						
		ArrayList<HostingCategories> HostingCategoriesList = new ArrayList<HostingCategories>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingCategories WHERE Idh="+idh);

			while ( rs.next() ) {
				HostingCategories HostingCategories = new HostingCategories();
				HostingCategories.setIdh(rs.getInt("idh"));
				HostingCategories.setIdct(rs.getInt("idct"));
							
				HostingCategoriesList.add(HostingCategories);
				logger.info("fetching all HostingCategories by idct: "+HostingCategories.getIdct()+"-> "+HostingCategories.getIdh());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return HostingCategoriesList;
	}
	
	
	@Override
	public HostingCategories get(long idh,long idct) {
		if (conn == null) return null;
		
		HostingCategories HostingCategories = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingCategories WHERE Idh="+idh+" AND idct="+idct);			 
			if (!rs.next()) return null;
			HostingCategories= new HostingCategories();
			HostingCategories.setIdh(rs.getInt("idh"));
			HostingCategories.setIdct(rs.getInt("idct"));

			logger.info("fetching HostingCategories by idh: "+HostingCategories.getIdh()+"  and idct: "+HostingCategories.getIdct());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return HostingCategories;
	}
	
	

	@Override
	public boolean add(HostingCategories HostingCategories) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO HostingCategories (idh,idct) VALUES('"+
									HostingCategories.getIdh()+"','"+
									HostingCategories.getIdct()+"')");
						
				logger.info("creating HostingCategories:("+HostingCategories.getIdh()+" "+HostingCategories.getIdct());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(HostingCategories HostingCategories) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM HostingCategories WHERE idh="+HostingCategories.getIdh());			 
				if (!rs.next()) 
					return false;
				long idct = rs.getInt("idct");

				stmt.executeUpdate("UPDATE HostingCategories SET idct="+HostingCategories.getIdct()
				+" WHERE idh = "+HostingCategories.getIdh() + " AND idct = " + idct);
				
				logger.info("updating HostingCategories:("+HostingCategories.getIdh()+" "+HostingCategories.getIdct());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idh, long idct) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM HostingCategories WHERE idh ="+idh+" AND idct="+idct);
				logger.info("deleting HostingCategories: "+idh+" , idct="+idct);
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
