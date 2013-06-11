package com.liaison.commons.util.settings;

public abstract class PersistenceProperties {

	
	public static final String DB_DRIVER = "com.liaison.DB_DRIVER";
	public static final String DB_PASSWORD = "com.liaison.DB_PASSWORD";
	public static final String DB_URL = "com.liaison.DB_URL";
	public static final String DB_USER = "com.liaison.DB_USER";

	//property of our db connection initialization query
	public static final String INITIALIZATION_QUERY_PROPERTY = "liaison.initializationQuery";
	
	//jpa persistence unit name
	public static final String PERSISTENCE_UNIT_NAME_PROPERTY = "liaison.persistenceUnitName";

}
