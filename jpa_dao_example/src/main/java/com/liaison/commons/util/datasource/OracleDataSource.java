package com.liaison.commons.util.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import oracle.ucp.UniversalConnectionPoolAdapter;
import oracle.ucp.admin.UniversalConnectionPoolManager;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceImpl;

import com.liaison.commons.util.settings.PropMan;

public class OracleDataSource
{
	protected static Logger logger = Logger.getLogger( OracleDataSource.class.toString() );
	private static boolean bInit = false;
	private static final String JNDI_SUB_CONTEXT_NAME = "g2:";
	private static final String ORACLE_DATASOURCE_NAME = JNDI_SUB_CONTEXT_NAME + "/oracleDS";
	private static final String ORACLE_CONNECTION_FACTORY = "oracle.jdbc.pool.OracleDataSource";
	private static final String CONNECTION_POOL_NAME = "G2 Connection Pool";
	
	
	static public void initOracleDataSource() throws Exception
	{
		PropMan pm = PropMan.instance();
		Properties props = pm.getProperties();
		String strPassword = pm.getSecureProperty( PropMan.DB_PASSWORD );
		initOracleDataSource( props, strPassword );
	}
	
	static public void initOracleDataSource( Properties props, String strPassword ) throws Exception
	{
		if ( bInit == true )
		{
System.out.println( "JNDI already bound - verifying now" );
			logger.info( "JNDI already bound - verifying now" );
			
			InitialContext ctx;
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = null;
			
			try
			{
				ctx = new InitialContext();
				Object o = ctx.lookup( ORACLE_DATASOURCE_NAME );
				PoolDataSource pds = (PoolDataSource)o;

				// TODO Remove print block
				// ------------------------
System.out.println( "Return context is " + o.getClass().getName() );
System.out.println( "User: " + pds.getUser() );
System.out.println( "DB URL: " + pds.getURL() );
System.out.println( "Connection factory class name: " + pds.getConnectionFactoryClassName() );
System.out.println( "Connection pool name: " + pds.getConnectionPoolName() );

				logger.info( "Return context is " + o.getClass().getName() );
				logger.info( "User: " + pds.getUser() );
				logger.info( "URL: " + pds.getURL() );
				logger.info( "Conn factory class name: " + pds.getConnectionFactoryClassName() );
				logger.info( "Connection pool name: " + pds.getConnectionPoolName() );
			}
			finally
			{
				if ( rs != null )
				{
					try { rs.close(); } catch ( SQLException e ) {}
				}
				
				if ( ps != null )
				{
					try { ps.close(); } catch ( SQLException e ) {}
				}				
				
				if ( con != null )
				{
					try { con.close(); } catch ( SQLException e ) {}
				}
			}
		}
		else
		{
			logger.info( "Initializing data source connection pool!" );
			
			Context ctx = new InitialContext();
			ctx.createSubcontext( JNDI_SUB_CONTEXT_NAME );
			
			// Create a PoolDataSource instance explicitly
			// --------------------------------------------
			PoolDataSource pds = new PoolDataSourceImpl();
			
			pds.setConnectionFactoryClassName( ORACLE_CONNECTION_FACTORY );
			pds.setUser( props.getProperty( PropMan.DB_USER ) );
			pds.setPassword( strPassword );
			pds.setURL( props.getProperty( PropMan.DB_URL ) );
			pds.setConnectionPoolName( CONNECTION_POOL_NAME );
			
			ctx.bind( ORACLE_DATASOURCE_NAME, pds );

System.out.println( "Factory: " + ORACLE_CONNECTION_FACTORY );
System.out.println( "User: " + props.getProperty( PropMan.DB_USER ) );
System.out.println( "Password: " + strPassword );
System.out.println( "URL: " + props.getProperty( PropMan.DB_URL ) );
System.out.println( "Poolname: " + CONNECTION_POOL_NAME );
System.out.println( "Dsname: " + ORACLE_DATASOURCE_NAME );
System.out.println( "Context is bound!" );

			ctx.close();
			
/*
			ctx = new InitialContext();
			Object o = ctx.lookup( ORACLE_DATASOURCE_NAME );
			pds = (PoolDataSource)o;
			Connection con = pds.getConnection();
			con.close();
*/

			logger.info( "Context is bound!" );
			
			bInit = true;
		}
	}
}
