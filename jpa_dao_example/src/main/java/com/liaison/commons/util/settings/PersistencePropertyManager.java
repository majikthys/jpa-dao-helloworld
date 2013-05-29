package com.liaison.commons.util.settings;

import java.util.Properties;
import static com.liaison.commons.util.settings.PersistenceProperties.*;

/**
 * (Temporary) centralized means to access properties.
 * 
 * @author max
 */
public class PersistencePropertyManager {
	protected Properties _props = new Properties();
	protected Properties _propsSecure = new Properties();
	protected static PersistencePropertyManager _pm = null;

	protected PersistencePropertyManager() {
		initStubData();
	}

	public static PersistencePropertyManager instance() {
		if (_pm == null) {
			synchronized (PersistencePropertyManager.class) {
				if (_pm == null) {
					_pm = new PersistencePropertyManager();
				}
			}
		}
		return (_pm);
	}

	public Properties getProperties() {
		return (_props);
	}

	
	public Properties getSecureProperties() {
		return (_propsSecure);
	}

	/**
	 * NOTE: returns empty char[] if property does not exist
	 * @param strName
	 * @return
	 */
	public char[] getSecureProperty(String strName) {
		if (_propsSecure.containsKey(strName) && _propsSecure.getProperty(strName) != null) {
			return _propsSecure.getProperty(strName).toCharArray();
		}
		return new char[]{};
	}

	public String getProperty(String strName) {
		return (_props.getProperty(strName));
	}

	/**
	 * TODO This temporary for testing only
	 */
	protected void initStubData() {
		_props.put(DB_USER, "JER_G3_G2Hello_DBA");
		_props.put(DB_URL, "jdbc:oracle:thin:@seadv01-db03:1521:kili1");
		_props.put(DB_DRIVER, "oracle.jdbc.OracleDriver");
		_propsSecure.put(DB_PASSWORD, "12345678");
	}
}
