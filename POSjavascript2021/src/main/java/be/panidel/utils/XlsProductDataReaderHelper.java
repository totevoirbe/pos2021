package be.panidel.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.panidel.exception.IllegalTypeException;
import be.panidel.model.ItemModel;
import be.panidel.model.ProductModel;
import be.panidel.model.ProductModels;

public class XlsProductDataReaderHelper {

	private final static Logger LOG = LoggerFactory.getLogger(XlsProductDataReaderHelper.class);

	/**
	 * read product data from excel
	 * 
	 * @param xlsFileName
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws IllegalTypeException
	 */
	public static ProductModels parseXlsxfile(String xlsFileName)
			throws InvalidFormatException, IOException, IllegalTypeException {

		InputStream inputStream = GenerateRefDocuments.class.getClassLoader().getResourceAsStream(xlsFileName);
		Workbook workbook = WorkbookFactory.create(inputStream);

		Sheet sheet = workbook.getSheetAt(0);

		String[] headers = new String[100];

		be.panidel.model.ProductModels products = new be.panidel.model.ProductModels();

		for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext();) {

			Map<String, Object> rowCells = new HashMap<>();

			Row row = rowIterator.next();

			for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {
				Cell cell = cellIterator.next();
				if (row.getRowNum() == sheet.getFirstRowNum()) {
					headers[cell.getColumnIndex()] = "" + parseCell(cell);
				} else {
					Object cellObject = parseCell(cell);
					if (cellObject != null) {
						rowCells.put(headers[cell.getColumnIndex()], cellObject);
					}
				}
			}
			if (row.getRowNum() != sheet.getFirstRowNum()) {

				int id = row.getRowNum();
				ItemModel item = null;
				String label = parseXlsCellAsString("name", rowCells);
				String ticketLabel = parseXlsCellAsString("name", rowCells);

				String code = parseXlsCellAsString("id", rowCells);
				String name = parseXlsCellAsString("name", rowCells);
				String htmlKeyLabel = parseXlsCellAsString("htmlKeyLabel", rowCells);
				String type = parseXlsCellAsString("type", rowCells);
				String image = parseXlsCellAsString("image", rowCells);
				String codeTva = parseXlsCellAsString("codeTva", rowCells);
				Integer mini = parseXlsCellAsInteger("mini", rowCells, row.getRowNum());
				Integer normal = parseXlsCellAsInteger("normal", rowCells, row.getRowNum());
				Integer geant = parseXlsCellAsInteger("geant", rowCells, row.getRowNum());
				Integer fitmini = parseXlsCellAsInteger("fitmini", rowCells, row.getRowNum());
				Integer fitnormal = parseXlsCellAsInteger("fitnormal", rowCells, row.getRowNum());
				Integer fitgeant = parseXlsCellAsInteger("fitgeant", rowCells, row.getRowNum());
				String webDetail = parseXlsCellAsString("webDetail", rowCells);
				String afficheDetail = parseXlsCellAsString("afficheDetail", rowCells);

				ProductModel product = new ProductModel(id, item, label, ticketLabel, code, name, htmlKeyLabel, type,
						image, codeTva, mini, normal, geant, fitmini, fitnormal, fitgeant, webDetail, afficheDetail);
				products.getProducts().add(product);
			}
		}

		return products;

	}

	/**
	 * @param columnName
	 * @param rowCells
	 * @return integer of null if not an integer
	 */
	private static Integer parseXlsCellAsInteger(String columnName, Map<String, Object> rowCells, int rowNumber) {

		String value = "" + rowCells.get(columnName);

		if (value == null || value.isEmpty() || "null".equals(value)) {
			return null;
		}

		Integer integer;
		try {
			BigDecimal bigDecimal = new BigDecimal(value).multiply(new BigDecimal(100));
			integer = bigDecimal.intValue();
		} catch (NumberFormatException e) {
			LOG.error(value, e);
			return null;
		}

		return integer;

	}

	private static String parseXlsCellAsString(String columnName, Map<String, Object> rowCells) {

		String value = "" + rowCells.get(columnName);

		if (value == null || value.isEmpty()) {
			return null;
		}

		return value;

	}

	public static Object parseCell(Cell cell) throws IllegalTypeException {

		if (cell.getCellTypeEnum() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
			return cell.getNumericCellValue();
		} else if (cell.getCellTypeEnum() == CellType.BLANK) {
			return null;
		} else if (cell.getCellTypeEnum() == CellType.ERROR) {
			return null;
		} else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
			return cell.getBooleanCellValue();
		} else if (cell.getCellTypeEnum() == CellType.STRING && HSSFDateUtil.isCellDateFormatted(cell)) {
			Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
			return date;
		} else if (cell.getCellTypeEnum() == CellType.FORMULA) {
			return null;
		} else {
			throw new IllegalTypeException("" + cell.getCellTypeEnum());
		}

	}

}
