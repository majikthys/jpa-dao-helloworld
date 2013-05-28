package com.liaison.commons.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class DAOUtil {
	public static void init() {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			Query q = em.createNativeQuery("SELECT * FROM DUAL"); // TODO Make
																	// this a
																	// parameter/property.
																	// Double
																	// check if
																	// there is
																	// a API to
																	// do this
																	// init.
			q.getResultList();
			tx.commit();
		} catch (Throwable t) {
			// just moved TODO remove this comment
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

	private static EntityManager getEntityManager() {
		return (EMFactory.getEntityManagerFactory().createEntityManager());
	}

	public static EntityManager getEM() {
		return (getEntityManager());
	}

	public static <T> List<T> fetch(Operation o) throws Exception {
		EntityManager em = getEntityManager();
		try {
			return (o.perform(em));
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public static void persist(Object o) throws Exception {
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

	public static void perform(Operation o) throws Exception // TODO add
																// annotation
																// introspection
																// to aid in
																// development.
	{
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
