package com.liaison.commons.util.settings;

public abstract class PersistenceProperties {

	
	public static final String DB_DRIVER = "DB_DRIVER";
	public static final String DB_PASSWORD = "DB_PASSWORD";
	public static final String DB_URL = "DB_URL";
	public static final String DB_USER = "DB_USER";

	//property of our db connection initialization query
	public static final String INITIALIZATION_QUERY_PROPERTY = "liaison.initializationQuery";
	
	//jpa persistence unit name
	public static final String PERSISTENCE_UNIT_NAME_PROPERTY = "liaison.persistenceUnitName";

}
