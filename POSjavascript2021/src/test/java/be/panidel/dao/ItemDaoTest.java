package be.panidel.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

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

import be.panidel.model.ItemModel;
import be.panidel.model.PriceCategory;
import be.panidel.model.ProductModel;

public class ItemDaoTest {

	private final static Logger LOG = LoggerFactory.getLogger(ItemDaoTest.class);

	private static ItemDao itemDao;
	private static ProductDao productDao;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pos");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		itemDao = new ItemDao();
		productDao = new ProductDao();

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			txn.begin();
			ItemModel itemModel = ModelMakerForTest.itemMock(888881, 999991);
			itemDao.create(itemModel, em);

			itemModel = ModelMakerForTest.itemMock(888882, 999992);
			itemDao.create(itemModel, em);

			itemModel = ModelMakerForTest.itemMock(888883, 999993);
			itemDao.create(itemModel, em);
			txn.commit();

		} catch (Exception e) {
			LOG.error("", e);

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

		long identifier = 888888;
		ItemModel itemModel = ModelMakerForTest.itemMock(identifier, -1);

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			txn.begin();
			itemDao.create(itemModel, em);
			txn.commit();

		} catch (Exception e) {
			LOG.error("", e);
			fail(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}

		ItemModel itemModelCheck = itemDao.find(identifier, em);
		LOG.info("" + itemModelCheck);
		assertNotNull(itemModelCheck);

		try {
			txn.begin();
			itemModel.setProductModels(null);
			txn.commit();
			txn.begin();
			itemDao.delete(identifier, em);
			txn.commit();

		} catch (Exception e) {
			LOG.error("", e);
			fail(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		assertNull(itemDao.find(identifier, em));

	}

	@Test
	public void testFind() {

		EntityManager em = emf.createEntityManager();

		ItemModel itemModel = itemDao.find(888881L, em);
		assertNotNull(itemModel);
		LOG.info("" + itemModel);

		itemModel = itemDao.find(888882L, em);
		assertNotNull(itemModel);
		LOG.info("" + itemModel);

		itemModel = itemDao.find(888883L, em);
		assertNotNull(itemModel);
		LOG.info("" + itemModel);

		ProductModel productModel = productDao.find(999992L, em);
		assertNotNull(productModel);
		LOG.info("" + productModel);

	}

	@Test
	public void testAssociateExistingProduct() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		try {

			Long productIdentifier = 9999994L;

			txn.begin();

			ProductModel productModel = ModelMakerForTest.productMock(productIdentifier, null);
			productDao.create(productModel, em);

			ItemModel itemModel = ModelMakerForTest.itemMock(888884, -1);
			itemDao.create(itemModel, em);

			ProductModel productModel2 = ModelMakerForTest.productMock(productIdentifier + 1, null);
			productDao.create(productModel2, em);
			productModel2.setAfficheDetail("autre chose");

			itemDao.joinProduct(itemModel, productModel2, em);

			LOG.info("" + itemModel);
			txn.commit();

		} catch (Exception e) {
			LOG.error("", e);
			fail(e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}

		List<ItemModel> itemModels = itemDao.findAll(em);
		LOG.info("" + itemModels);
		List<ProductModel> productModels = productDao.findAll(em);
		LOG.info("" + productModels);

	}

	@Test
	public void testUpdate() {

		EntityManager em = emf.createEntityManager();

		ItemModel itemModel = itemDao.find(888882L, em);
		itemModel.setPriceCategory(PriceCategory.FITMINI);
		assertTrue(itemDao.find(888882L, em).getPriceCategory().equals(PriceCategory.FITMINI));
	}

}