package be.panidel.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;

abstract class GenericDaoImpl<T> implements GenericDao<T> {

	private Class<T> type;
	// EntityManager em;
	// EntityManagerFactory emf = Persistence.createEntityManagerFactory("pos");

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {

		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];

		// emf = Persistence.createEntityManagerFactory("pos");

		// EntityManagerFactory emf = Persistence.createEntityManagerFactory("pos");
		// em = emf.createEntityManager();

	}

	// @Override
	// public EntityManager getEntityManager() {
	// EntityManager em = emf.createEntityManager();
	// return em;
	// }

	@Override
	public T create(final T t, EntityManager em) {

		em.persist(t);

		return t;

	}

	@Override
	public void delete(final Object id, EntityManager em) {

		em.remove(em.getReference(type, id));

	}

	@Override
	public T find(final Object id, EntityManager em) {
		return em.find(type, id);
	}

	@Override
	public T update(final T t, EntityManager em) {

		em.merge(t);

		return t;

	}

}