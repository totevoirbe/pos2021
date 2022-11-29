package be.panidel.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.panidel.exception.IllegalTypeException;
import be.panidel.model.ProductModel;
import be.panidel.model.ProductModels;

public class PosDataModelHelper {

	private final static Logger LOG = LoggerFactory.getLogger(PosDataModelHelper.class);

	/**
	 * Generate generate data model for POS ATTENTION les prix sont multipliés par
	 * 100 pour éviter les décimales dans le stockage !
	 * 
	 * @param fileName
	 * @param productModels
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws IllegalTypeException
	 */
	public static RootPos10 generatePos10Model(String fileName, ProductModels productModels)
			throws InvalidFormatException, IOException, IllegalTypeException {

		ProductsPos10 productsPos10 = new ProductsPos10();
		productsPos10.setProductPos10s(new ArrayList<>());
		RootPos10 rootPos10 = new RootPos10();
		rootPos10.setProductsPos10(productsPos10);

		for (ProductModel product : productModels.getProducts()) {

			BigDecimal prix = null;
			String code = null;

			if (product.getMini() != null && product.getMini().compareTo(BigDecimal.ZERO) > 0) {
				prix = product.getMini();
				code = "M" + product.getCode();
				ProductPos10 product10 = implementProduct(product, prix, code);
				productsPos10.getProductPos10s().add(product10);
			}
			if (product.getNormal() != null && product.getNormal().compareTo(BigDecimal.ZERO) > 0) {
				prix = product.getNormal();
				code = product.getCode();
				ProductPos10 product10 = implementProduct(product, prix, code);
				productsPos10.getProductPos10s().add(product10);
			}
			if (product.getGeant() != null && product.getGeant().compareTo(BigDecimal.ZERO) > 0) {
				prix = product.getGeant();
				code = "G" + product.getCode();
				ProductPos10 product10 = implementProduct(product, prix, code);
				productsPos10.getProductPos10s().add(product10);
			}

			if (product.getFitmini() != null && product.getFitmini().compareTo(BigDecimal.ZERO) > 0) {
				prix = product.getFitmini();
				code = "M" + product.getCode() + "FIT";
				ProductPos10 product10 = implementProduct(product, prix, code);
				productsPos10.getProductPos10s().add(product10);
			}
			if (product.getFitnormal() != null && product.getFitnormal().compareTo(BigDecimal.ZERO) > 0) {
				prix = product.getFitnormal();
				code = product.getCode() + "FIT";
				ProductPos10 product10 = implementProduct(product, prix, code);
				productsPos10.getProductPos10s().add(product10);
			}
			if (product.getFitgeant() != null && product.getFitgeant().compareTo(BigDecimal.ZERO) > 0) {
				prix = product.getFitgeant();
				code = "G" + product.getCode() + "FIT";
				ProductPos10 product10 = implementProduct(product, prix, code);
				productsPos10.getProductPos10s().add(product10);
			}

		}

		parseXlsxfileForOldRefs(fileName, rootPos10);

		return rootPos10;
	}

	public static void parseXlsxfileForOldRefs(String fileName, RootPos10 rootPos10)
			throws InvalidFormatException, IOException, IllegalTypeException {

		InputStream inputStream = GenerateRefDocuments.class.getClassLoader().getResourceAsStream(fileName);
		Workbook workbook = WorkbookFactory.create(inputStream);

		Sheet sheet = workbook.getSheetAt(1);

		String[] headers = new String[100];

		for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext();) {

			Map<String, Object> rowCells = new HashMap<>();

			Row row = rowIterator.next();

			for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {
				Cell cell = cellIterator.next();
				if (row.getRowNum() == sheet.getFirstRowNum()) {
					headers[cell.getColumnIndex()] = "" + XlsProductDataReaderHelper.parseCell(cell);
				} else {
					Object cellObject = XlsProductDataReaderHelper.parseCell(cell);
					if (cellObject != null) {
						rowCells.put(headers[cell.getColumnIndex()], cellObject);
					}
				}
			}
			if (row.getRowNum() != sheet.getFirstRowNum()) {

				if (rowCells.get("id") != null) {
					String id = "" + ((Double) rowCells.get("id")).intValue();
					String code = "" + rowCells.get("code");
					String group = "" + rowCells.get("group");
					ProductPos10 productPos10 = rootPos10.getProductPos10(code);
					if (productPos10 != null) {
						productPos10.setId(id);
						productPos10.setGroup(group);
					} else {
						LOG.error("Not found : " + id + "/" + code);
					}
				}
			}
		}
	}

	private static ProductPos10 implementProduct(ProductModel product, BigDecimal prix, String code) {

		String id = null;
		String nom = product.getName();
		String description = product.getWebDetail();
		if (description == null || "null".equals(description)) {
			description = "-";
		}
		String htmlKeyLabel = product.getHtmlKeyLabel().replaceAll("<br>", ";");
		BigDecimal prixachat = BigDecimal.ZERO;
		BigDecimal prixvente = prix.multiply(new BigDecimal(100));

		Integer tvaTakeAway = tvaTakeAwayConverter(product.getCodeTva());
		Integer tvaTakeOnPlace = tvaTakeOnPlaceConverter(product.getCodeTva());
		String group = null;
		List<SubproductPos10> subproducts = null;

		ProductPos10 product10 = new ProductPos10(id, code, nom, description, htmlKeyLabel, prixachat, prixvente,
				tvaTakeAway, tvaTakeOnPlace, group, subproducts);

		return product10;

	}

	private static Integer tvaTakeAwayConverter(String tva) {
		return 600;
	}

	private static Integer tvaTakeOnPlaceConverter(String tva) {
		return 1200;
	}

}
