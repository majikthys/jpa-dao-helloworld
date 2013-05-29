package com.liaison.commons.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.liaison.commons.util.settings.PersistencePropertyManager;
import static com.liaison.commons.util.settings.PersistenceProperties.PERSISTENCE_UNIT_NAME_PROPERTY;

/**
 * Proxy for entity manager factory. 
 * 
 * @author max
 */
public class EMFactory {

	protected static String getPersistenceUnitName() {
		String persistenceUnitName = PersistencePropertyManager.instance().getProperty(PERSISTENCE_UNIT_NAME_PROPERTY);
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
					_emf = Persistence.createEntityManagerFactory(PERISTENCE_UNIT_NAME);
				}
			}
		}
		return (_emf);
	}

}
