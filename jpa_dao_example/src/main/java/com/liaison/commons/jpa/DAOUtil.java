package com.liaison.commons.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import static com.liaison.commons.util.settings.PersistenceProperties.INITIALIZATION_QUERY_PROPERTY;
import com.liaison.commons.util.settings.PersistencePropertyManager;

/**
 * 
 * A set of utility methods to be used to execute persistence Operations 
 * on JPA entities.
 * 
 * @author Max
 * @see EMFactory
 * @see Operation
 */
public class DAOUtil {
	protected static String getInitialQuery() {
		String initQuery = PersistencePropertyManager.instance().getProperty(INITIALIZATION_QUERY_PROPERTY);
		if (initQuery == null) {
			return "SELECT * FROM DUAL"; //Hope it's oracle!			
		}
		return initQuery;
	}

	/**
	 * Executes a query to get first connection hot.
	 * 
	 * It is advised that developers utilize this method when bringing up a container.
	 * 
	 */
	public static void init() {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			Query q = em.createNativeQuery(getInitialQuery());
			q.getResultList();
			tx.commit();
		} catch (Throwable t) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw (t);
		} finally {

			if (em != null) {
				em.close();
			}
		}
	}



	/**
	 * Provides an entity manager when needed from EntityManager Factory
	 * @return
	 */
	public static EntityManager getEntityManager() {
		return (EMFactory.getEntityManagerFactory().createEntityManager());
	}

	/**
	 * Fetches a list of (potentially heterogenous) entities via given Operation
	 */
	public static <T> List<T> fetch(Operation o)    {
		EntityManager em = getEntityManager();
		try {
			return (o.perform(em));
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}


	public static <T> void persist(T o)  { 
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(o);
			tx.commit();
		} catch (Throwable t) {
			if (tx.isActive()) {
				tx.rollback();
			}

			throw (t);
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	

	public static void perform(Operation o) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			o.perform(em);
			tx.commit();

		} catch (Throwable t) {

			if (tx.isActive()) {
				tx.rollback();
			}
			throw (t);
		} finally {

			if (em != null) {
				em.close();
			}
		}
	}
	

}
