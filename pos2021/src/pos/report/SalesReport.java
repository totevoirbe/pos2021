package pos.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBException;

import pos.model.ItemValue;
import pos.model.Operation;
import pos.model.PayItem;
import pos.model.PayValue;
import pos.model.ProductItem;
import pos.xml.XmlReader;
import pos.xml.model.Document;
import pos.xml.model.Documentlist;
import pos.xml.model.ModelValidatorException;

public class SalesReport {

	public static void main(String[] args) {

		Date fromDate = new GregorianCalendar(2021, 1 - 1, 1).getTime();
		Date toDate = new GregorianCalendar(2021, 12 - 1, 31).getTime();

		int documentId = 0;
		int errorCount = 0;

		List<ItemValue> itemValues = new ArrayList<ItemValue>();

		PayValue totalPayValue = new PayValue();

		try {

			List<Documentlist> documentLists = XmlReader.extractSales(fromDate, toDate);

			for (Documentlist documentlist : documentLists) {

				for (Document document : documentlist.getDocument()) {

					try {

						if (document.getCancelled() == null || !document.getCancelled()) {

							Operation operation = SalesReportHelper.get(document);

							if (operation.getProductItems() != null) {

								for (ProductItem productItemInList : operation.getProductItems()) {

									ItemValue itemValue = null;

									for (ItemValue itemValueInLIst : itemValues) {
										if (itemValueInLIst.getProductItem().getProduct()
												.equals(productItemInList.getProduct())) {
											itemValue = itemValueInLIst;
											break;
										}
									}

									ItemValue currentItemValue = new ItemValue(productItemInList);

									if (itemValue == null) {
										itemValue = currentItemValue;
										itemValues.add(itemValue);
									} else {
										itemValue.add(currentItemValue);
									}
								}
							}

							if (operation.getPayItems() != null) {
								for (PayItem payItemInList : operation.getPayItems()) {
									totalPayValue.add(payItemInList);
								}
							}

//							if (totalItemValue.getValueTotal().getValueTotal().doubleValue() == totalPayValue.getValueTotal()
//									.getValueTotal().doubleValue()) {
//								this.totalItemValue = documentTotalProduct;
//							} else {
//								throw new ModelValidatorException("Total product : " + documentTotalProduct
//										+ " est différent de total pay : " + documentTotalPay + " - " + document);
//							}

						}
					} catch (ModelValidatorException e) {
						System.err.println(errorCount++);
						System.err.println(e.getMessage());
						System.err.println(document.toString());
						System.err.println();
					}

				}
			}

			for (ItemValue itemValue : itemValues) {
				System.out.println(itemValue.getValueTotal().getValueQty() + ","
						+ itemValue.getProductItem().getProduct() + "," + itemValue.getProductItem().getDescription()
						+ "," + itemValue.getValueTotal().getValueTotal());
			}

			System.out.println();

			System.out.println("Jété : " + totalPayValue.getValueAsJete());
			System.out.println("Gratuit : " + totalPayValue.getValueAsGratuit());
			System.out.println("Impayé : " + totalPayValue.getValueAsIimpaye());
			System.out.println("Fidélité : " + totalPayValue.getValueAsFidelite());
			System.out.println("Chèque repas : " + totalPayValue.getValueAsChequeRepas());
			System.out.println("Cash : " + totalPayValue.getValueAsCash());
			System.out.println("Facture : " + totalPayValue.getValueAsFacture());

			System.out.println("TOTAL : " + totalPayValue.getValueTotal());

		} catch (JAXBException e) {
			System.err.println(e.getMessage());
		}

		System.err.println(documentId + " / " + errorCount);
	}
}
