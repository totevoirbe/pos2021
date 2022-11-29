package be.panidel.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.panidel.exception.IllegalTypeException;
import be.panidel.model.ProductModel;
import be.panidel.model.ProductModels;

public class XlsAfficheBodyWriter {

	private final static Logger LOG = LoggerFactory.getLogger(XlsAfficheBodyWriter.class);

	/**
	 * generate data for affiche as excel file
	 * 
	 * @param outputStream
	 * @param rootAfficheDef
	 * @param productModels
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws IllegalTypeException
	 */
	public static void writeXlsxfile(OutputStream outputStream, RootAfficheDef rootAfficheDef,
			ProductModels productModels) throws InvalidFormatException, IOException, IllegalTypeException {

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

		for (PageAfficheDef pageAfficheDef : rootAfficheDef.getPage()) {

			Sheet sheet = xssfWorkbook.createSheet(pageAfficheDef.getName());

			int rowIndex = 0;
			// int productIndex = 1;

			XlsAfficheBodyWriterHelper.setColumnsWith(sheet);

			Row rowHeader = XlsAfficheBodyWriterHelper.createRow(sheet, rowIndex++,
					XlsAfficheBodyWriterHelper.HEADER_ROW_HEIGHT_IN_POINTS);
			XlsAfficheBodyWriterHelper.setHeader(xssfWorkbook, rowHeader);

			for (String productCode : pageAfficheDef.getTr()) {

				if (productCode.trim().isEmpty()) {

					// add empty row
					Row row = XlsAfficheBodyWriterHelper.createRow(sheet, rowIndex++,
							XlsAfficheBodyWriterHelper.EMPTY_ROW_HEIGHT_IN_POINTS);

					XlsAfficheBodyWriterHelper.setEmptyRow(xssfWorkbook, sheet, row);

					continue;

				} else if (productCode.startsWith("[")) {

					// add title row
					Row rowTitle = XlsAfficheBodyWriterHelper.createRow(sheet, rowIndex++,
							XlsAfficheBodyWriterHelper.TITLE_ROW_HEIGHT_IN_POINTS);
					XlsAfficheBodyWriterHelper.setTitle(xssfWorkbook, sheet, rowTitle, productCode);
					continue;

				}

				ProductModel productModel = productModels.getProductModel(productCode);

				if (productModel == null) {

					LOG.error(productCode + " is null");

				} else {

					// add product row
					Row rowProduct = XlsAfficheBodyWriterHelper.createRow(sheet, rowIndex++,
							XlsAfficheBodyWriterHelper.PRODUCT_ROW_HEIGHT_IN_POINTS);
					// XlsAfficheBodyWriterHelper.setIndexCell(xssfWorkbook, rowProduct,
					// productIndex++);0

					XlsAfficheBodyWriterHelper.setLogo(xssfWorkbook, sheet, rowProduct, productModel.getImage());

					XlsAfficheBodyWriterHelper.setLabelCell(xssfWorkbook, rowProduct, productModel.getName());

					if (productModel.getGeant() == null || productModel.getGeant().compareTo(BigDecimal.ZERO) <= 0) {
						XlsAfficheBodyWriterHelper.setPrice1Cell(xssfWorkbook, rowProduct, -1);
//						XlsAfficheBodyWriterHelper.setPrice2Cell(xssfWorkbook, rowProduct,
//								convertStorageValue(productModel.getNormal()));
						XlsAfficheBodyWriterHelper.setPrice2Cell(xssfWorkbook, rowProduct,
								productModel.getNormal().doubleValue());
						sheet.addMergedRegion(new CellRangeAddress(rowProduct.getRowNum(), rowProduct.getRowNum(),
								XlsAfficheBodyWriterHelper.LABEL_COL_INDEX,
								XlsAfficheBodyWriterHelper.PRICE_1_COL_INDEX));
					} else {
//						XlsAfficheBodyWriterHelper.setPrice1Cell(xssfWorkbook, rowProduct,
//								convertStorageValue(productModel.getNormal()));
//						XlsAfficheBodyWriterHelper.setPrice2Cell(xssfWorkbook, rowProduct,
//								convertStorageValue(productModel.getGeant()));
						XlsAfficheBodyWriterHelper.setPrice1Cell(xssfWorkbook, rowProduct,
								productModel.getNormal().doubleValue());
						XlsAfficheBodyWriterHelper.setPrice2Cell(xssfWorkbook, rowProduct,
								productModel.getGeant().doubleValue());
					}

					if (productModel.getAfficheDetail() != null && productModel.getAfficheDetail().trim().length() > 0
							&& (!"NULL".equals(productModel.getAfficheDetail().trim().toUpperCase()))) {

						// add detail row
						Row rowDescription = XlsAfficheBodyWriterHelper.createRow(sheet, rowIndex++,
								XlsAfficheBodyWriterHelper.DESCRIPTION_ROW_HEIGHT_IN_POINTS);
						XlsAfficheBodyWriterHelper.setDescriptionCell(xssfWorkbook, sheet, rowDescription,
								": " + productModel.getAfficheDetail());
						// rowDescription.setHeightInPoints(
						// XlsAfficheBodyWriterHelper.DESCRIPTION_ROW_HEIGHT_IN_POINTS_BYLINE * 2);
						rowDescription.setHeightInPoints(0);

					}
				}
			}
		}

		xssfWorkbook.write(outputStream);

	}

//	private static Double convertStorageValue(Integer storagePrice) {
//		if (storagePrice == null) {
//			return null;
//		}
//		BigDecimal bigDecimalValue = new BigDecimal(storagePrice);
////		return bigDecimalValue.divide(new BigDecimal(100)).doubleValue();
//		return bigDecimalValue.doubleValue();
//	}

}
