package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import es.unex.pi.dao.HostingDAO;
import es.unex.pi.dao.JDBCHostingDAOImpl;
import es.unex.pi.model.Hosting;

import org.junit.Test;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestHostingDAO {

	static DBConn dbConn;
	static HostingDAO HostingDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    HostingDAO = new JDBCHostingDAOImpl();
		HostingDAO.setConnection(conn);
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
		
		Hosting hosting01 = HostingDAO.get(0);
		assertEquals(hosting01.getId(),0);
		assertEquals(hosting01.getTitle(),"Castillo de las Herguijuelas");

		Hosting hosting02 = HostingDAO.get(1);
		assertEquals(hosting02.getId(),1);
		assertEquals(hosting02.getTitle(),"Hotel Atrio");
		
		Hosting hosting03 = HostingDAO.get(2);
		assertEquals(hosting03.getId(),2);
		assertEquals(hosting03.getTitle(),"Palacio de Oquendo");
		
		Hosting hosting04 = HostingDAO.get(3);
		assertEquals(hosting04.getId(),3);
		assertEquals(hosting04.getTitle(),"Hotel Palace");
		
		Hosting hosting05 = HostingDAO.get(4);
		assertEquals(hosting05.getId(),4);
		assertEquals(hosting05.getTitle(),"Hotel Ritz");
		
	}
	
	
	@Test
	public void test2Add(){
		Hosting hosting01 = new Hosting();
		hosting01.setTitle("newHosting");
		hosting01.setDescription("new description");
		hosting01.setTelephone("777777777");
		hosting01.setIdu(0);
		hosting01.setLikes(15);
		hosting01.setLocation("Mérida");
		hosting01.setAvailable(1);
		hosting01.setContactEmail("info@merida.com");
		
		
		HostingDAO.add(hosting01);
		
		Hosting hosting02 = HostingDAO.getAllBySearchTitle("newHosting").iterator().next();
		assertEquals(hosting01.getDescription(),hosting02.getDescription());
	}
	
	@Test
	public void test3Modify(){
		Hosting hosting01 = HostingDAO.getAllBySearchTitle("newHosting").iterator().next();
		hosting01.setTitle("newHostingUpdated");
		hosting01.setDescription("new description updated");
		HostingDAO.save(hosting01);
		
		Hosting hosting02 = HostingDAO.getAllBySearchTitle("newHostingUpdated").iterator().next();
		assertEquals(hosting01.getDescription(),hosting02.getDescription());
		
		HostingDAO.getAll();
	}
	
	@Test
	public void test4Delete(){
		
		 Hosting hosting01 = HostingDAO.getAllBySearchTitle("newHostingUpdated").iterator().next();
		 long idHosting= hosting01.getId();
		 HostingDAO.delete(idHosting);
		 
		 Hosting hosting02 = HostingDAO.get(idHosting);
		 assertEquals(null,hosting02);
		 
		HostingDAO.getAll();
	}

}
