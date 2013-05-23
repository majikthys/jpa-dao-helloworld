package com.liaison.commons.jpa;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

	@Before
	public void setUp() throws Exception
	{
	}

	@After
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
