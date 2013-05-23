package com.liaison.commons.util.settings;

import java.util.Properties;

public class PropMan
{
	public static final String DB_USER = "DB_USER"; 
	public static final String DB_URL = "DB_URL";
	public static final String DB_PASSWORD = "DB_PASSWORD";
	public static final String DB_DRIVER = "DB_DRIVER";
	protected Properties _props = new Properties(); 
	protected Properties _propsSecure = new Properties();
	protected static PropMan _pm = null;
	
	
	protected PropMan()
	{
		initStubData();
	}
	
	public static PropMan instance()
	{
		if ( _pm == null )
		{
			synchronized( PropMan.class )
			{
				if ( _pm == null )
				{
					_pm = new PropMan();
				}
			}
		}
		return ( _pm );
	}
	
	public Properties getProperties()
	{
		return ( _props );
	}
	
	public Properties getSecureProperties()
	{
		return ( _propsSecure );
	}
	
	public String getSecureProperty( String strName )
	{
		return ( _propsSecure.getProperty( strName ) );
	}
	
	public String getProperty( String strName )
	{
		return ( _props.getProperty( strName ) );
	}
	
	/**
	 * TODO This temporary for testing only
	 */
	protected void initStubData()
	{
		_props.put( DB_USER, "MAXC_G2HELLO_DBA" );
		_props.put( DB_URL, "jdbc:oracle:thin:@seadv01-db03:1521:kili1" );
		_props.put( DB_DRIVER, "oracle.jdbc.OracleDriver" );
		_propsSecure.put( DB_PASSWORD, "12345678" );
	}
}
