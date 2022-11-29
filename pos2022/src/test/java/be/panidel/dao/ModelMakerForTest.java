package be.panidel.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.panidel.model.ItemModel;
import be.panidel.model.PriceCategory;
import be.panidel.model.ProductModel;
import be.panidel.model.SaleModel;
import be.panidel.model.SaleState;

public class ModelMakerForTest {

	public static ItemModel itemMock(long identifier, long productIdentifier) {

		long id = identifier;
		SaleModel sale = null;
		BigDecimal quantity = new BigDecimal(identifier).add(new BigDecimal("0.1"));
		BigDecimal unitPrice = new BigDecimal(identifier).add(new BigDecimal("0.2"));
		Boolean deleted = false;
		PriceCategory priceCategory = PriceCategory.SDWNORMAL;
		List<ProductModel> productModels = new ArrayList<>();
		ItemModel item = new ItemModel(id, sale, productModels, quantity, unitPrice, deleted, priceCategory);
		if (productIdentifier >= 0) {
			ProductModel productModel = productMock(productIdentifier, item);
			productModels.add(productModel);
		}
		return item;
	}

	public static ProductModel productMock(Long identifier, ItemModel item) {

		long id = identifier;
		String label = identifier + " p label";
		String ticketLabel = identifier + " p ticketlabel";
		String code = "" + identifier;
		String name = identifier + " p";
		String htmlKeyLabel = identifier + " p keylabel";
		String type = identifier + "p type";
		String image = identifier + "p img";
		String codeTva = identifier + "p tva";
		BigDecimal mini = new BigDecimal(identifier.intValue()).add(new BigDecimal(1));
		BigDecimal normal = new BigDecimal(identifier.intValue()).add(new BigDecimal(2));
		BigDecimal geant = new BigDecimal(identifier.intValue()).add(new BigDecimal(3));
		BigDecimal fitmini = new BigDecimal(identifier.intValue()).add(new BigDecimal(4));
		BigDecimal fitnormal = new BigDecimal(identifier.intValue()).add(new BigDecimal(5));
		BigDecimal fitgeant = new BigDecimal(identifier.intValue()).add(new BigDecimal(6));
		String webDetail = identifier + "p web detail";
		String afficheDetail = identifier + "p aff. detail";

		ProductModel operationModel = new ProductModel(id, item, label, ticketLabel, code, name, htmlKeyLabel, type,
				image, codeTva, mini, normal, geant, fitmini, fitnormal, fitgeant, webDetail, afficheDetail);

		return operationModel;
	}

	public static SaleModel saleMock(long id) {

		SaleState status = SaleState.CLOSE;
		Date openDate = new Date();
		Date endDate = new Date();
		String identifier = "" + id;
		String source = "" + identifier;
		List<ItemModel> items = new ArrayList<>();

		BigDecimal saleTotal = new BigDecimal(id).add(new BigDecimal("0.1"));
		BigDecimal payTotal = new BigDecimal(id).add(new BigDecimal("0.2"));
		BigDecimal nbArticles = new BigDecimal(id).add(new BigDecimal("0.3"));
		BigDecimal remainValue = new BigDecimal(id).add(new BigDecimal("0.4"));
		BigDecimal paySubTotal = new BigDecimal(id).add(new BigDecimal("0.5"));

		ItemModel itemModel = itemMock(id * 1000000 + 888881, id * 1000000 + 999991);
		items.add(itemModel);
		itemModel = itemMock(id * 1000000 + 888882, id * 1000000 + 999992);
		items.add(itemModel);
		itemModel = itemMock(id * 1000000 + 888883, id * 1000000 + 999993);
		items.add(itemModel);

		SaleModel saleModel = new SaleModel(id, status, openDate, endDate, identifier, source, saleTotal, payTotal,
				nbArticles, remainValue, paySubTotal, items);

		return saleModel;

	}

}
