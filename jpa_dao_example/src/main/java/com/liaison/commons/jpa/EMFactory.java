package com.liaison.commons.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
	// property of our db connection initialization query
	public static final String PERSISTENCE_UNIT_NAME_PROPERTY = "liaison.persistenceUnitName";

	protected static String getPersistenceUnitName() {
		String persistenceUnitName = System
				.getProperty(PERSISTENCE_UNIT_NAME_PROPERTY);
		if (persistenceUnitName == null) {
			return "Hello"; // get persistence name
		}
		return persistenceUnitName;
	}

	public static final String PERISTENCE_UNIT_NAME = getPersistenceUnitName(); 
	
	private static EntityManagerFactory _emf = null;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (_emf == null) {
			synchronized (EMFactory.class) {
				if (_emf == null) {
					_emf = Persistence
							.createEntityManagerFactory(PERISTENCE_UNIT_NAME);
				}
			}
		}
		return (_emf);
	}

}
