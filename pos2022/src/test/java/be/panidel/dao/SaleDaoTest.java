package be.panidel.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.panidel.model.SaleModel;

public class SaleDaoTest {

	private final static Logger LOG = LoggerFactory.getLogger(SaleDaoTest.class);

	private static SaleDao saleDao;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pos");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		saleDao = new SaleDao();

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			txn.begin();
			SaleModel saleModel = ModelMakerForTest.saleMock(888881);
			saleDao.create(saleModel, em);

			saleModel = ModelMakerForTest.saleMock(888882);
			saleDao.create(saleModel, em);

			saleModel = ModelMakerForTest.saleMock(888883);
			saleDao.create(saleModel, em);
			txn.commit();

		} catch (Exception e) {
			LOG.error("", e);
			fail(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test

	public void testCreateAndDelete() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			txn.begin();
			long identifier = 888888;
			SaleModel saleModel = ModelMakerForTest.saleMock(identifier);
			saleDao.create(saleModel, em);
			txn.commit();
			assertNotNull(saleDao.find(identifier, em));
			txn.begin();
			saleDao.delete(identifier, em);
			txn.commit();
			assertNull(saleDao.find(identifier, em));

		} catch (Exception e) {
			LOG.error("", e);
			fail(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

	@Test
	public void testFind() {

		EntityManager em = emf.createEntityManager();

		SaleModel saleModel = saleDao.find(888881L, em);
		assertNotNull(saleModel);
		LOG.info("" + saleModel);

		saleModel = saleDao.find(888882L, em);
		assertNotNull(saleModel);
		LOG.info("" + saleModel);

		saleModel = saleDao.find(888883L, em);
		assertNotNull(saleModel);
		LOG.info("" + saleModel);

	}

	@Test
	public void testUpdate() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			txn.begin();
			SaleModel saleModel = saleDao.find(888882L, em);
			saleModel.setPayTotal(BigDecimal.TEN);
			assertTrue(saleDao.find(888882L, em).getPayTotal().equals(BigDecimal.TEN));
			SaleModel saleModel2 = saleDao.find(888882L, em);
			saleModel2.setPayTotal(BigDecimal.ZERO);
			assertTrue(saleDao.find(888882L, em).getPayTotal().equals(BigDecimal.ZERO));
			txn.commit();

		} catch (Exception e) {
			LOG.error("", e);
			fail(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}

}