package com.liaison.commons.jpa;

import java.util.List;

import javax.persistence.EntityManager;

public interface Op
{
	public <T> List<T> perform( EntityManager em ) throws Exception;
}
