package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.HostingServices;

public class JDBCHostingServicesDAOImpl implements HostingServicesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCHostingServicesDAOImpl.class.getName());

	@Override
	public List<HostingServices> getAll() {

		if (conn == null) return null;
						
		ArrayList<HostingServices> HostingServicesList = new ArrayList<HostingServices>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices");
						
			while ( rs.next() ) {
				HostingServices HostingServices = new HostingServices();
				HostingServices.setIdh(rs.getInt("idh"));
				HostingServices.setIds(rs.getInt("ids"));
						
				HostingServicesList.add(HostingServices);
				logger.info("fetching all HostingServices: "+HostingServices.getIdh()+" "+HostingServices.getIds());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HostingServicesList;
	}

	@Override
	public List<HostingServices> getAllByService(long ids) {
		
		if (conn == null) return null;
						
		ArrayList<HostingServices> HostingServicesList = new ArrayList<HostingServices>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices WHERE ids="+ids);

			while ( rs.next() ) {
				HostingServices HostingServices = new HostingServices();
				HostingServices.setIdh(rs.getInt("idh"));
				HostingServices.setIds(rs.getInt("ids"));

				HostingServicesList.add(HostingServices);
				logger.info("fetching all HostingServices by idh: "+HostingServices.getIdh()+"->"+HostingServices.getIds());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return HostingServicesList;
	}
	
	@Override
	public List<HostingServices> getAllByHosting(long idh) {
		
		if (conn == null) return null;
						
		ArrayList<HostingServices> HostingServicesList = new ArrayList<HostingServices>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices WHERE Idh="+idh);

			while ( rs.next() ) {
				HostingServices HostingServices = new HostingServices();
				HostingServices.setIdh(rs.getInt("idh"));
				HostingServices.setIds(rs.getInt("ids"));
							
				HostingServicesList.add(HostingServices);
				logger.info("fetching all HostingServices by ids: "+HostingServices.getIds()+"-> "+HostingServices.getIdh());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return HostingServicesList;
	}
	
	
	@Override
	public HostingServices get(long idh,long ids) {
		if (conn == null) return null;
		
		HostingServices HostingServices = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices WHERE Idh="+idh+" AND ids="+ids);			 
			if (!rs.next()) return null;
			HostingServices= new HostingServices();
			HostingServices.setIdh(rs.getInt("idh"));
			HostingServices.setIds(rs.getInt("ids"));

			logger.info("fetching HostingServices by idh: "+HostingServices.getIdh()+"  and ids: "+HostingServices.getIds());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return HostingServices;
	}
	
	

	@Override
	public boolean add(HostingServices HostingServices) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO HostingServices (idh,ids) VALUES('"+
									HostingServices.getIdh()+"','"+
									HostingServices.getIds()+"')");
						
				logger.info("creating HostingServices:("+HostingServices.getIdh()+" "+HostingServices.getIds());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(HostingServices HostingServices) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM HostingServices WHERE idh="+HostingServices.getIdh());			 
				if (!rs.next()) 
					return false;
				long ids = rs.getInt("ids");

				stmt.executeUpdate("UPDATE HostingServices SET ids="+HostingServices.getIds()
				+" WHERE idh = "+HostingServices.getIdh() + " AND ids = " + ids);
				
				logger.info("updating HostingServices:("+HostingServices.getIdh()+" "+HostingServices.getIds());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idh, long ids) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM HostingServices WHERE idh ="+idh+" AND ids="+ids);
				logger.info("deleting HostingServices: "+idh+" , ids="+ids);
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
