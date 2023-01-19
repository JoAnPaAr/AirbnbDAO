package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Services;


public class JDBCServicesDAOImpl implements ServicesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCServicesDAOImpl.class.getName());
	
	@Override
	public Services get(long id) {
		if (conn == null) return null;
		
		Services Services = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Services WHERE id ="+id);			 
			if (!rs.next()) return null; 
			Services  = new Services();	 
			Services.setId(rs.getInt("id"));
			Services.setName(rs.getString("name"));
			
			logger.info("fetching Services by id: "+id+" -> "+Services.getId()+" "+Services.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Services;
	}
	
	
	@Override
	public Services get(String name) {
		if (conn == null) return null;
		
		Services Services = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Categories WHERE name = '"+name+"'");			 
			if (!rs.next()) return null; 
			Services  = new Services();	 
			Services.setId(rs.getInt("id"));
			Services.setName(rs.getString("name"));
			
			logger.info("fetching Services by name: "+Services.getId()+" "+Services.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Services;
	}
	
	
	
	public List<Services> getAll() {

		if (conn == null) return null;
		
		ArrayList<Services> serviceslist = new ArrayList<Services>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Services");
			while ( rs.next() ) {
				Services Services = new Services();
				Services.setId(rs.getInt("id"));
				Services.setName(rs.getString("name"));
				
				serviceslist.add(Services);
				logger.info("fetching Services: "+Services.getId()+" "+Services.getName());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return serviceslist;
	}
	
	public List<Services> getAllBySearchName(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Services> serviceslist = new ArrayList<Services>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Services WHERE UPPER(name) LIKE '%" + search + "%'");

			while (rs.next()) {
				Services Services = new Services();
				
				Services.setId(rs.getInt("id"));
				Services.setName(rs.getString("name"));				
				serviceslist.add(Services);
				
				logger.info("fetching Servicess by text in the name: "+search+": "+Services.getId()+" "+Services.getName());
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return serviceslist;
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	
}
