package com.liaison.commons.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory
{
	public static final String PERISTENCE_NAME = "Hello"; // TODO Should be property driven 
	private static EntityManagerFactory _emf = null;
	
	public static EntityManagerFactory getEntityManagerFactory()
	{
		if ( _emf == null )
		{
			synchronized( EMFactory.class )
			{
				if ( _emf == null )
				{
					_emf = Persistence.createEntityManagerFactory( PERISTENCE_NAME );
				}
			}
		}
		return ( _emf );
	}

}
