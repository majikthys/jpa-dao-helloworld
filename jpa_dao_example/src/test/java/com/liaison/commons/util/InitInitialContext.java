package com.liaison.commons.util;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;

import com.liaison.commons.util.settings.PropMan;

public class InitInitialContext {
	public static void init() throws SQLException, ClassNotFoundException, NamingException {
		final class LocalDataSource implements DataSource, Serializable {
			private static final long serialVersionUID = 1L;
			private String connectionString;
			private String username;
			private String password;

			LocalDataSource(String connectionString, String username,
					String password) {
				this.connectionString = connectionString;
				this.username = username;
				this.password = password;
			}

			public Connection getConnection() throws SQLException {
				return (DriverManager.getConnection(connectionString, username,
						password));
			}

			public Connection getConnection(String arg0, String arg1)
					throws SQLException {
				return (getConnection());
			}

			public PrintWriter getLogWriter() throws SQLException {
				return (null);
			}

			public int getLoginTimeout() throws SQLException {
				return (0);
			}

			public void setLogWriter(PrintWriter out) throws SQLException {
			}

			public void setLoginTimeout(int seconds) throws SQLException {
			}

			@Override
			public Logger getParentLogger()
					throws SQLFeatureNotSupportedException {
				return (null);
			}

			@Override
			public boolean isWrapperFor(Class<?> arg0) throws SQLException {
				return (false);
			}

			@Override
			public <T> T unwrap(Class<T> arg0) throws SQLException {
				return (null);
			}
		}

		final class DatabaseContext extends InitialContext {
			DatabaseContext() throws NamingException {
			}

			@Override
			public Object lookup(String strName) throws NamingException {
				PropMan pm = PropMan.instance();
				Properties systemProps = pm.getProperties();
				String strPassword = pm.getSecureProperty(PropMan.DB_PASSWORD);

				try {
					// Our connection strings
					// -----------------------
					Class.forName(systemProps.getProperty(PropMan.DB_DRIVER));
					DataSource ds1 = new LocalDataSource(
							systemProps.getProperty(PropMan.DB_URL),
							systemProps.getProperty(PropMan.DB_USER),
							strPassword);

					Properties props = new Properties();
					props.put("g2:/oracleDS", ds1);

					Object value = props.get(strName);

					return ((value != null) ? value : super.lookup(strName));
				} catch (Exception e) {
					System.err.println("Lookup Problem " + e.getMessage());
					e.printStackTrace();
				}

				return (null);
			}
		}

		final class DatabaseContextFactory implements InitialContextFactory,
				InitialContextFactoryBuilder {
			public Context getInitialContext(Hashtable<?, ?> environment)
					throws NamingException {
				return (new DatabaseContext());
			}

			public InitialContextFactory createInitialContextFactory(
					Hashtable<?, ?> environment) throws NamingException {
				return (new DatabaseContextFactory());
			}
		}

		NamingManager.setInitialContextFactoryBuilder(new DatabaseContextFactory());
	}
}
