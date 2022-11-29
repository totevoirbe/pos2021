package be.panidel.dao;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import be.panidel.model.ItemModel;
import be.panidel.model.SaleModel;

public class DataFacade {

	public static DataFacade instance = new DataFacade();

	private Lock l;
	private SaleDao saleDao;
	private EntityManagerFactory emf;

	private DataFacade() {
		saleDao = new SaleDao();
		emf = Persistence.createEntityManagerFactory("pos");
		l = new ReentrantLock();
	}

	// public static DataFacade getInstance() {
	// if (instance == null) {
	// instance = new DataFacade();
	// }
	// return instance;
	// }

	public SaleModel createSale(SaleModel saleModel) {

		l.lock();
		SaleModel sale = null;

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			txn.begin();
			sale = saleDao.create(saleModel, em);
			txn.commit();

		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			l.unlock();
		}

		return sale;

	}

	public SaleModel createSale(SaleModel saleModel, ItemModel itemModel) {

		l.lock();
		SaleModel sale = null;

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			txn.begin();
			sale = saleDao.create(saleModel, em);
			saleDao.joinItem(saleModel, itemModel, em);
			txn.commit();

		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			l.unlock();
		}
		return sale;

	}

	public SaleModel getSale(SaleModel saleModel) {

		EntityManager em = emf.createEntityManager();

		SaleModel sale = saleDao.find(saleModel, em);

		return sale;

	}

	SaleModel deleteSale(SaleModel saleModel) {

		l.lock();
		SaleModel sale = null;

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			txn.begin();
//			saleDao.delete(new Long(saleModel.getId()), em);
			saleDao.delete(saleModel.getId(), em);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
			l.unlock();
		}
		return sale;

	}

	public List<SaleModel> getAll() {

		EntityManager em = emf.createEntityManager();

		return saleDao.findAll(em);

	}

}
