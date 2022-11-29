package be.panidel.dao;

import java.util.List;

import javax.persistence.EntityManager;

public interface GenericDao<T> {

	// EntityManager getEntityManager();

	T create(T t, EntityManager em);

	void delete(Object id, EntityManager em);

	T find(Object id, EntityManager em);

	T update(T t, EntityManager em);

	List<? extends T> findAll(EntityManager em);

}