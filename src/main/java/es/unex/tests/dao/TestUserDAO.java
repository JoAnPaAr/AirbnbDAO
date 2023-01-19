package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.model.User;
import es.unex.pi.dao.JDBCUserDAOImpl;
import es.unex.pi.dao.UserDAO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestUserDAO {

	static DBConn dbConn;
	static UserDAO userDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		dbConn.destroy(conn);
	    
	}

	@Before
	public void setUpBeforeMethod() throws Exception {
	
	}

	@Test
	public void test1BaseData() {
		User user01 = userDAO.get(0);
		assertEquals(user01.getId(),0);
		assertEquals(user01.getUsername(),"Jesus");
		assertEquals(user01.getPassword(),"jj123");
		assertEquals(user01.getEmail(),"jj@jj.com");
		
		User user02 = userDAO.get(1);
		assertEquals(user02.getId(),1);
		assertEquals(user02.getUsername(),"Chema");
		assertEquals(user02.getPassword(),"cc123");
		assertEquals(user02.getEmail(),"cc@cc.com");
		
		User user03 = userDAO.get("Tote");
		assertEquals(user03.getId(),2);
		assertEquals(user03.getUsername(),"Tote");
		assertEquals(user03.getPassword(),"tt123");
		assertEquals(user03.getEmail(),"tt@tt.com");

		User user04 = userDAO.get("Pepe");
		assertEquals(user04.getId(),3);
		assertEquals(user04.getUsername(),"Pepe");
		assertEquals(user04.getPassword(),"pp123");
		assertEquals(user04.getEmail(),"pp@pp.com");
	}
	
	
	@Test
	public void test2Add(){
		User user01 = new User();
		user01.setUsername("newUser");
		user01.setEmail("newUser@unex.es");
		user01.setPassword("12345");
		long value = userDAO.add(user01);
		
		User user02 = userDAO.get("newUser");
		assertEquals(user01.getEmail(),user02.getEmail());
		assertEquals(user01.getPassword(),user02.getPassword());
	}
	
	@Test
	public void test3Modify(){
		User user01 = userDAO.get("newUser");
		user01.setUsername("newUserUpdated");
		userDAO.save(user01);
		
		User user02 = userDAO.get("newUserUpdated");		
		assertEquals(user01.getUsername(),user02.getUsername());
	}
	
	@Test
	public void test4Delete(){
		 User user01 = userDAO.get("newUserUpdated");
		 userDAO.delete(user01.getId());
		 
		 User user02 = userDAO.get("newUserUpdated");
 		 assertEquals(null, user02);
 		 
 		userDAO.getAll();
	}

}
