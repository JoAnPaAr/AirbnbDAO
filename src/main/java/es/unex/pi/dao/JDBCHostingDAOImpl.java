package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Hosting;

public class JDBCHostingDAOImpl implements HostingDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCHostingDAOImpl.class.getName());
	
	@Override
	public Hosting get(long id) {
		if (conn == null) return null;
		
		Hosting hosting = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM hosting WHERE id ="+id);			 
			if (!rs.next()) return null; 
			hosting  = new Hosting();	 
			hosting.setId(rs.getInt("id"));
			hosting.setTitle(rs.getString("title"));
			hosting.setDescription(rs.getString("description"));
			hosting.setTelephone(rs.getString("telephone"));
			hosting.setIdu(rs.getInt("idu"));
			hosting.setLikes(rs.getInt("likes"));
			hosting.setLocation(rs.getString("location"));
			hosting.setAvailable(rs.getInt("available"));
			hosting.setContactEmail(rs.getString("contactemail"));
			hosting.setPrecio(rs.getInt("precio"));

			logger.info("fetching hostings: "+hosting.getId()+" "+hosting.getTitle()+" "+hosting.getDescription()+ " " + hosting.getTelephone() + " " + hosting.getIdu() 
						+ " " + hosting.getLikes() + " " + hosting.getLocation() + " " + hosting.getAvailable() + " " + hosting.getContactEmail()+ " " + hosting.getPrecio());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hosting;
	}
	
	public List<Hosting> getAll() {

		if (conn == null) return null;
		
		ArrayList<Hosting> hostings = new ArrayList<Hosting>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM hosting");
			while ( rs.next() ) {
				Hosting hosting = new Hosting();
				hosting.setId(rs.getInt("id"));
				hosting.setTitle(rs.getString("title"));
				hosting.setDescription(rs.getString("description"));
				hosting.setTelephone(rs.getString("telephone"));
				hosting.setIdu(rs.getInt("idu"));
				hosting.setLikes(rs.getInt("likes"));
				hosting.setLocation(rs.getString("location"));
				hosting.setAvailable(rs.getInt("available"));
				hosting.setContactEmail(rs.getString("contactemail"));
				hosting.setPrecio(rs.getInt("precio"));

				hostings.add(hosting);
				logger.info("fetching hostings: "+hosting.getId()+" "+hosting.getTitle()+" "+hosting.getDescription()+ " " + hosting.getTelephone() + " " + hosting.getIdu() 
				+ " " + hosting.getLikes() + " " + hosting.getLocation() + " " + hosting.getAvailable() + " " + hosting.getContactEmail()+ " " + hosting.getPrecio());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hostings;
	}
	
	public List<Hosting> getAllBySearchTitle(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Hosting> hostings = new ArrayList<Hosting>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM hosting WHERE UPPER(title) LIKE '%" + search + "%'");

			while (rs.next()) {
				Hosting hosting = new Hosting();
				
				hosting.setId(rs.getInt("id"));
				hosting.setTitle(rs.getString("title"));
				hosting.setDescription(rs.getString("description"));
				hosting.setTelephone(rs.getString("telephone"));
				hosting.setIdu(rs.getInt("idu"));
				hosting.setLikes(rs.getInt("likes"));
				hosting.setLocation(rs.getString("location"));
				hosting.setAvailable(rs.getInt("available"));
				hosting.setContactEmail(rs.getString("contactemail"));
				hosting.setPrecio(rs.getInt("precio"));

				hostings.add(hosting);
				
				logger.info("fetching hostings: "+hosting.getId()+" "+hosting.getTitle()+" "+hosting.getDescription()+ " " + hosting.getTelephone() + " " + hosting.getIdu() 
				+ " " + hosting.getLikes() + " " + hosting.getLocation() + " " + hosting.getAvailable() + " " + hosting.getContactEmail()+ " " + hosting.getPrecio());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostings;
	}
	
	public List<Hosting> getAllBySearchDescription(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Hosting> hostings = new ArrayList<Hosting>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM hosting WHERE UPPER(description) LIKE '%" + search + "%'");

			while (rs.next()) {
				Hosting hosting = new Hosting();
				
				hosting.setId(rs.getInt("id"));
				hosting.setTitle(rs.getString("title"));
				hosting.setDescription(rs.getString("description"));
				hosting.setTelephone(rs.getString("telephone"));
				hosting.setIdu(rs.getInt("idu"));
				hosting.setLikes(rs.getInt("likes"));
				hosting.setLocation(rs.getString("location"));
				hosting.setAvailable(rs.getInt("available"));
				hosting.setContactEmail(rs.getString("contactemail"));
				hosting.setPrecio(rs.getInt("precio"));

				hostings.add(hosting);
				
				logger.info("fetching hostings: "+hosting.getId()+" "+hosting.getTitle()+" "+hosting.getDescription()+ " " + hosting.getTelephone() + " " + hosting.getIdu() 
				+ " " + hosting.getLikes() + " " + hosting.getLocation() + " " + hosting.getAvailable() + " " + hosting.getContactEmail()+ " " + hosting.getPrecio());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostings;
	}
	
	public List<Hosting> getAllBySearchAll(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Hosting> hostings = new ArrayList<Hosting>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM hosting WHERE UPPER(title) LIKE '%" + search + "%' OR UPPER(description) LIKE '%" + search + "%'");

			while (rs.next()) {
				Hosting hosting = new Hosting();				
				hosting.setId(rs.getInt("id"));
				hosting.setTitle(rs.getString("title"));
				hosting.setDescription(rs.getString("description"));
				hosting.setTelephone(rs.getString("telephone"));
				hosting.setIdu(rs.getInt("idu"));
				hosting.setLikes(rs.getInt("likes"));
				hosting.setLocation(rs.getString("location"));
				hosting.setAvailable(rs.getInt("available"));
				hosting.setContactEmail(rs.getString("contactemail"));
				hosting.setPrecio(rs.getInt("precio"));

				hostings.add(hosting);
				
				logger.info("fetching hostings: "+hosting.getId()+" "+hosting.getTitle()+" "+hosting.getDescription()+ " " + hosting.getTelephone() + " " + hosting.getIdu() 
				+ " " + hosting.getLikes() + " " + hosting.getLocation() + " " + hosting.getAvailable() + " " + hosting.getContactEmail()+ " " + hosting.getPrecio());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostings;
	}
	
	public List<Hosting> getAllBySearchAllLikes(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Hosting> hostings = new ArrayList<Hosting>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM hosting WHERE UPPER(title) LIKE '%" + search + "%' OR UPPER(description) LIKE '%" + search + "%' ORDER BY likes DESC");

			while (rs.next()) {
				Hosting hosting = new Hosting();				
				hosting.setId(rs.getInt("id"));
				hosting.setTitle(rs.getString("title"));
				hosting.setDescription(rs.getString("description"));
				hosting.setTelephone(rs.getString("telephone"));
				hosting.setIdu(rs.getInt("idu"));
				hosting.setLikes(rs.getInt("likes"));
				hosting.setLocation(rs.getString("location"));
				hosting.setAvailable(rs.getInt("available"));
				hosting.setContactEmail(rs.getString("contactemail"));
				hosting.setPrecio(rs.getInt("precio"));

				hostings.add(hosting);
				
				logger.info("fetching hostings: "+hosting.getId()+" "+hosting.getTitle()+" "+hosting.getDescription()+ " " + hosting.getTelephone() + " " + hosting.getIdu() 
				+ " " + hosting.getLikes() + " " + hosting.getLocation() + " " + hosting.getAvailable() + " " + hosting.getContactEmail()+ " " + hosting.getPrecio());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostings;
	}

	
	public List<Hosting> getAllByUser(long idu) {
		
		if (conn == null)
			return null;

		ArrayList<Hosting> hostings = new ArrayList<Hosting>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM hosting WHERE idu = "+ idu);

			while (rs.next()) {
				Hosting hosting = new Hosting();
				hosting.setId(rs.getInt("id"));
				hosting.setTitle(rs.getString("title"));
				hosting.setDescription(rs.getString("description"));
				hosting.setTelephone(rs.getString("telephone"));
				hosting.setIdu(rs.getInt("idu"));
				hosting.setLikes(rs.getInt("likes"));
				hosting.setLocation(rs.getString("location"));
				hosting.setAvailable(rs.getInt("available"));
				hosting.setContactEmail(rs.getString("contactemail"));
				hosting.setPrecio(rs.getInt("precio"));

				hostings.add(hosting);
				
				logger.info("fetching hostings: "+hosting.getId()+" "+hosting.getTitle()+" "+hosting.getDescription()+ " " + hosting.getTelephone() + " " + hosting.getIdu() 
				+ " " + hosting.getLikes() + " " + hosting.getLocation() + " " + hosting.getAvailable() + " " + hosting.getContactEmail()+ " " + hosting.getPrecio());
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hostings;
	}

	
	
	
	@Override
	public long add(Hosting hosting) {
		long id=-1;
		long lastid=-1;
		if (conn != null){

			Statement stmt;
			
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='hosting'");			 
				if (!rs.next()) return -1; 
				lastid=rs.getInt("seq");
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO hosting (title,description,telephone,idu,likes,location,available,contactemail,precio) VALUES('"
									+hosting.getTitle()+"','"+hosting.getDescription()+"','" + hosting.getTelephone() + "'," 
									+ hosting.getIdu() + "," + hosting.getLikes()+",'"+ hosting.getLocation() +"',"+ hosting.getAvailable() +",'" + hosting.getContactEmail()+"'," + hosting.getPrecio() + ")");
				
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='hosting'");			 
				if (!rs.next()) return -1; 
				id=rs.getInt("seq");
				if (id<=lastid) return -1;
											
				logger.info("CREATING hosting("+id+"): "+hosting.getTitle()+" "+hosting.getDescription());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return id;
	}

	@Override
	public boolean save(Hosting hosting) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE hosting SET title='"+hosting.getTitle()
				+"', description='"+hosting.getDescription()
				+"', telephone='"+hosting.getTelephone()
				+"', idu="+hosting.getIdu()
				+", likes="+hosting.getLikes()
				+", location='"+hosting.getLocation()
				+"', available="+hosting.getAvailable()
				+", contactemail='"+hosting.getContactEmail()
				+"', precio="+hosting.getPrecio()				
				+" WHERE id = "+hosting.getId());
				logger.info("updating hosting: "+hosting.getId()+" "+hosting.getTitle()+" "+hosting.getDescription());
						
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;

	}

	@Override
	public boolean delete(long id) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM hosting WHERE id ="+id);
				logger.info("deleting hosting: "+id);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
