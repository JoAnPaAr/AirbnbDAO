package es.unex.tests.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.unex.pi.dao.HostingCategoriesDAO;
import es.unex.pi.dao.JDBCHostingCategoriesDAOImpl;
import es.unex.pi.model.HostingCategories;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestHostingCategoriesDAO {

	static DBConn dbConn;
	static HostingCategoriesDAO HostingsCategoriesDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    HostingsCategoriesDAO = new JDBCHostingCategoriesDAOImpl();
		HostingsCategoriesDAO.setConnection(conn);
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
		
		List<HostingCategories> HostingsCategoriesList = HostingsCategoriesDAO.getAll();
		
		HostingCategories HostingsCategories = HostingsCategoriesDAO.get(0,4);
		
		assertEquals(HostingsCategories.getIdh(),0);
		assertEquals(HostingsCategories.getIdct(),4);
		
		assertEquals(HostingsCategoriesList.get(0).getIdh(),HostingsCategories.getIdh());			
			
	}
	
	@Test
	public void test2BaseDataByCategory() {
		
		List<HostingCategories> HostingsCategoriesList = HostingsCategoriesDAO.getAllByCategory(4);
		for(HostingCategories HostingsCategories: HostingsCategoriesList)			
			assertEquals(HostingsCategories.getIdct(),4);			
	}
	
	@Test
	public void test3BaseDataByHosting() {
		
		List<HostingCategories> HostingsCategoriesList = HostingsCategoriesDAO.getAllByHosting(0);
		for(HostingCategories HostingsCategories: HostingsCategoriesList)			
			assertEquals(HostingsCategories.getIdh(),0);			
	}
	
	@Test
	public void test4Add(){
		HostingCategories HostingsCategories01 = new HostingCategories();
		HostingsCategories01.setIdh(3);
		HostingsCategories01.setIdct(0);
		HostingsCategoriesDAO.add(HostingsCategories01);
		
		HostingCategories HostingsCategories02 = HostingsCategoriesDAO.get(3,0);
		
		assertEquals(3,HostingsCategories02.getIdh());
		assertEquals(0,HostingsCategories02.getIdct());
				
	}
	
	
	@Test
	public void test5Modify(){
		
		HostingCategories HostingsCategories01 = HostingsCategoriesDAO.get(3,0);
		HostingsCategories01.setIdct(1);
		HostingsCategoriesDAO.save(HostingsCategories01);
		
		HostingCategories HostingsCategories02 = HostingsCategoriesDAO.get(3,1);
		assertEquals(3,HostingsCategories02.getIdh());
		assertEquals(1,HostingsCategories02.getIdct());
	}
	
	@Test
	public void test6Delete(){
		 
		HostingsCategoriesDAO.delete(3,1);
		List<HostingCategories> HostingsCategoriesList = HostingsCategoriesDAO.getAll();
		 
		 HostingCategories HostingsCategories01 = new HostingCategories();
		 HostingsCategories01.setIdh(3);
		 HostingsCategories01.setIdct(1);
		 		 
		for(HostingCategories HostingsCategories: HostingsCategoriesList) {
				assertNotEquals(HostingsCategories,HostingsCategories01);
		}
		 
	}

}
