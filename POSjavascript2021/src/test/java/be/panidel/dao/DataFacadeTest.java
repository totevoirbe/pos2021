package be.panidel.dao;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.panidel.model.ItemModel;
import be.panidel.model.SaleModel;

public class DataFacadeTest {

	private final static Logger LOG = LoggerFactory.getLogger(DataFacadeTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testCreateSale() {

		SaleModel saleModel = ModelMakerForTest.saleMock(55557);
		DataFacade.instance.createSale(saleModel);

		saleModel = ModelMakerForTest.saleMock(55558);
		DataFacade.instance.createSale(saleModel);

		saleModel = ModelMakerForTest.saleMock(55559);
		DataFacade.instance.createSale(saleModel);
	}

	@Test
	public void testCreateSaleAndAddItem() {

		SaleModel saleModel = ModelMakerForTest.saleMock(55556);
		ItemModel itemModel = ModelMakerForTest.itemMock(55556, 55556);
		DataFacade.instance.createSale(saleModel, itemModel);

		saleModel = ModelMakerForTest.saleMock(55555);
		itemModel = ModelMakerForTest.itemMock(55555, 55555);
		DataFacade.instance.createSale(saleModel, itemModel);
	}

	@Test
	public void testGetAll() {

		List<SaleModel> saleModels = DataFacade.instance.getAll();

		LOG.info("Number of sales : " + saleModels.size());
		for (SaleModel saleModel : saleModels) {
			LOG.info("" + saleModel);
		}

	}

	@Test
	public void testDeleteAll() {

		List<SaleModel> saleModels = DataFacade.instance.getAll();
		LOG.info("Number of sales : " + saleModels.size());

		for (SaleModel saleModel : saleModels) {
			DataFacade.instance.deleteSale(saleModel);
		}

		saleModels = DataFacade.instance.getAll();
		LOG.info("Number of sales : " + saleModels.size());

	}

}