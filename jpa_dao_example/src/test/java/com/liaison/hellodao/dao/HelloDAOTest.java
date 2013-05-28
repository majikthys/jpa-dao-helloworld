package com.liaison.hellodao.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;
import static org.testng.AssertJUnit.*;

import com.liaison.commons.jpa.DAOUtil;
import com.liaison.commons.jpa.Operation;
import com.liaison.commons.util.InitInitialContext;
import com.liaison.hellodao.model.HelloMoon;
import com.liaison.hellodao.model.HelloWorld;



public class HelloDAOTest
{
	static HelloDAO _dao = null;
//	static InitTestData _initDao = null; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		InitInitialContext.init();
		_dao = new HelloDAO();
		initHelloWorldDaos();
	}

	@AfterClass //TODO why does this error?
	public void tearDownAfterClass() throws Exception
	{
		deleteTestData();
	}

	@BeforeTest
	public void setUp() throws Exception
	{
	}

	@AfterTest
	public void tearDown() throws Exception
	{
	}
	
	@Test
	public void testFindWorlds() throws Exception
	{
    	long iTime = System.currentTimeMillis();
		
    	Operation op = new Operation()
    	{
			@SuppressWarnings("unchecked")
			@Override
			public <T> List<T> perform( EntityManager em ) throws Exception
			{
				List<T> list = (List<T>) _dao.findHelloWorld( em, normalizeSGUID( TEST_WORLD_SIID + "A" ) );
				return ( (List<T>) list );
			}
    	};
    	
    	List<Object []> list = DAOUtil.<Object []>fetch( op );
		
    	assertTrue( "list greater then zero",  list.size() > 0 );
    	
		for ( Object [] oar : list )
    	{
			HelloWorld helloWorld = null;
			HelloMoon helloMoon = null;
    		
    		for ( Object obj : oar )
	    	{
	        	if ( obj instanceof HelloWorld )
	            { 
	        		helloWorld = (HelloWorld)obj;
	            }
	            else if ( obj instanceof HelloMoon )
	            {
	            	helloMoon = (HelloMoon)obj;
	            }
	            else
	            {
	            	assertTrue( "Object Type Expected", false );
	            }
	    	}

        	assertTrue( "Fetched Expected Objects",  helloWorld != null && helloMoon != null );
    	}
	
    	//PrintUtil.timePrint( "testFindWorlds", iTime );
	}
	
	@Test
	public void testFindWorldsAgain() throws Exception
	{
		testFindWorlds();
	}
	
	@Test
	public void fakeTest() {
		assertTrue(true);
	}
	
	/**
	 * pads SGUID with spaces to fill to 32 characters
	 * @param strSGUID
	 * @return
	 */
	public static String normalizeSGUID(String strSGUID) {
		int iLength = 32 - strSGUID.length();
		for (int i = 0; i < iLength; i++) {
			strSGUID += " ";
		}
		return (strSGUID);
	}
	
	public static final String TEST_WORLD_SIID = RandomStringUtils.randomAlphanumeric(4);

	/**
	 * Test data array
	 * @author jeremyfranklin-ross
	 */
	private enum Planet {
		SATURN(TEST_WORLD_SIID + "A", "Mimas", "Tethys", "Dione", "Rhea", "Titan"), 
		JUPITER(TEST_WORLD_SIID + "B", "Metis", "Adrastea","Amalthea", "Thebe", "Io"), 
		EARTH(TEST_WORLD_SIID + "C", "TheMoon");
		String siSGUID;
		List<String> moons = new ArrayList<String>();

		Planet(String siSGUID, String... moonNames) {
			this.siSGUID = siSGUID;
			moons.addAll(Arrays.asList(moonNames));
		}

		List<String> getMoons() {
			return moons;
		}

		String getSiSGUID() {
			return siSGUID;
		}
	}

	/** 
	 * DAOUtil.persist to save newly created HelloWorlds with their 
	 * associated HelloMoons to DB.
	 * 
	 * @throws Exception
	 */
	public static void initHelloWorldDaos() throws Exception {
		System.out.println("Creating data...");

		for (Planet planet : Planet.values()) {
			HelloWorld helloWorld = new HelloWorld();
			helloWorld.setName(planet.toString());
			helloWorld.setSiSguid(planet.getSiSGUID());
			helloWorld.setHelloMoons(new ArrayList<HelloMoon>()); // TODO... fix
																	// encapsulation

			for (String moonName : planet.getMoons()) {
				HelloMoon helloMoon = new HelloMoon();
				helloMoon.setName(moonName);
				helloWorld.getHelloMoons().add(helloMoon);
			}
			
			//Important bit right here:
			DAOUtil.persist(helloWorld);
		}
	}
	
	/**
	 * Delete all HelloWorlds and associated moons
	 * @throws Exception
	 */
	public void deleteTestData() throws Exception {
		System.out.println("Deleting data...");

		Operation op = new Operation() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> List<T> perform(EntityManager em) throws Exception {
				Query q = em.createQuery("SELECT FROM HelloWorld hw");
				List<T> worlds = q.getResultList();
				for (T world : worlds) {
					HelloWorld helloWorld = (HelloWorld) world;

					for (HelloMoon helloMoon : helloWorld.getHelloMoons()) {
						em.remove(helloMoon);
					}

					em.remove(helloWorld);
				}

				return (null);
			}
		};

		DAOUtil.perform(op);
	}
	
}
