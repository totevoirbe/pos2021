package pos.report;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import pos.model.Operation;
import pos.model.PayItem;
import pos.model.PayType;
import pos.model.ProductItem;
import pos.xml.model.Document;
import pos.xml.model.Item;
import pos.xml.model.ModelValidatorException;
import pos.xml.model.Payement;

public class SalesReportHelper {

	public static Operation get(Document document) throws ModelValidatorException {

		document.validate();

		Date date = new Date(document.getDate());

		List<ProductItem> productItems = new ArrayList<ProductItem>();
		List<PayItem> payItems = new ArrayList<PayItem>();

		Operation operation = new Operation(date);

		if (document.getPayement() != null) {

			for (Payement payement : document.getPayement()) {

				payement.validate();

				String payementDescription = payement.getDescription();
				PayType payementType = PayType.getPayType(payement.getMode());
				BigDecimal payementQty = new BigDecimal(payement.getQuantity()).divide(new BigDecimal(100));
				BigDecimal payementTotal = payementQty
						.multiply(new BigDecimal(payement.getValue()).divide(new BigDecimal(100)));

				// check sale table compatibility
				if (operation.getPayType() == null) {
					operation.setPayType(payementType);
				} else if (payementType.isSale() != operation.getPayType().isSale()) {
					throw new ModelValidatorException("Sale Type Error : " + document);
				}

				PayItem payItem = null;

				for (PayItem payItemInLIst : payItems) {
					if (payItemInLIst.getPayType().equals(payementType)) {
						payItem = payItemInLIst;
						break;
					}
				}
				if (payItem == null) {
					payItems.add(new PayItem(payementType, payementQty, payementDescription, payementTotal));
				} else {
					payItem.setQuantity(payementQty.add(payItem.getQuantity()));
					payItem.setTotal(payementTotal.add(payItem.getTotal()));
				}
			}
			if (payItems.size() > 0) {
				operation.setPayItems(payItems);
			}
		}

		if (document.getItem() != null) {

			for (Item item : document.getItem()) {

				item.validate();

				String itemProduct = item.getProduct();
				String itemDescription = item.getDescription();
				BigDecimal itemQty = new BigDecimal(item.getQuantity()).divide(new BigDecimal(100));
				BigDecimal itemTotal = itemQty
						.multiply(new BigDecimal(item.getUnitPrice()).divide(new BigDecimal(100)));

				ProductItem productItem = null;

				for (ProductItem productItemInLIst : productItems) {
					if (productItemInLIst.getProduct().equals(item.getProduct())) {
						productItem = productItemInLIst;
						break;
					}
				}
				if (productItem == null) {
					productItems.add(new ProductItem(itemQty, itemProduct, itemDescription, itemTotal));
				} else {
					productItem.setQuantity(itemQty.add(productItem.getQuantity()));
					productItem.setTotal(itemTotal.add(productItem.getTotal()));
				}

			}
			if (productItems.size() > 0) {
				operation.setProductItems(productItems);
			}
		}
		return operation;
	}
}