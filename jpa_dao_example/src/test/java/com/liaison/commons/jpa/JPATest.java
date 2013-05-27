package com.liaison.commons.jpa;



import org.testng.annotations.*;

import com.liaison.commons.util.PrintUtil;

public class JPATest
{
	static InitTestData _initData = null; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		_initData = new InitTestData(); 
		_initData.initTestData();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		_initData.deleteTestData();
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
	public void test() throws Exception
	{
    	long iTime = System.currentTimeMillis();
	
    	PrintUtil.timePrint( "testFindWorlds", iTime );
	}

}
