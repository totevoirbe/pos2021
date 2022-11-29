package be.panidel.utils;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsAfficheBodyWriterHelper {

	enum ItalicFont {

		ITALIC_FONT(true), NOT_ITALIC_FONT(false);

		private boolean value;

		ItalicFont(boolean value) {
			this.value = value;
		}

		public boolean istrue() {
			return value;
		}
	};

	private final static String GENERIC_FONT_NAME = "Times New Roman";
	private final static String DESCRIPTION_FONT_NAME = "Times New Roman";

	// public static final int INDEX_REF_COL_INDEX = 0;
	// public static final double INDEX_REF_FONT_HEIGHT = 36;
	public static final int LOGO_COL_INDEX = 0;

	public static final int LABEL_COL_INDEX = 1;
	public static final double LABEL_FONT_HEIGHT = 60;

	public static final int TITLE_COL_INDEX = 1;
	public static final double TITLE_FONT_HEIGHT = 88;
	public static final float TITLE_ROW_HEIGHT_IN_POINTS = new Float(TITLE_FONT_HEIGHT) + 5;
	public static final XSSFColor TITLE_COLOR = new XSSFColor(new Color(127, 127, 127));

	public static final int PRICE_1_COL_INDEX = 2;
	public static final double PRICE_1_FONT_HEIGHT = 40;

	public static final int PRICE_2_COL_INDEX = 3;
	public static final double PRICE_2_FONT_HEIGHT = 36;

	public static final int DESCRIPTION_COL_INDEX = 1;
	public static final double DESCRIPTION_FONT_HEIGHT = 36;
	public static final float DESCRIPTION_ROW_HEIGHT_IN_POINTS = new Float(DESCRIPTION_FONT_HEIGHT) + 5;
	public static final XSSFColor DESCRPTION_COLOR = new XSSFColor(new Color(0, 0, 0));
	public static final float DESCRIPTION_ROW_HEIGHT_IN_POINTS_BYLINE = new Float(DESCRIPTION_FONT_HEIGHT) + 5;

	public static final float HEADER_ROW_HEIGHT_IN_POINTS = 53;
	public static final XSSFColor HEADER_COLOR = new XSSFColor(new Color(127, 127, 127));

	public static final float PRODUCT_ROW_HEIGHT_IN_POINTS = new Float(LABEL_FONT_HEIGHT) + 5;
	public static final XSSFColor PRODUCT_COLOR = new XSSFColor(new Color(56, 87, 35));

	public static final float EMPTY_ROW_HEIGHT_IN_POINTS = new Float(PRODUCT_ROW_HEIGHT_IN_POINTS) + 5;

	public static void setColumnsWith(Sheet sheet) {
		sheet.setColumnWidth(LOGO_COL_INDEX, 1500 * 1);
		sheet.setColumnWidth(LABEL_COL_INDEX, 20000 * 1);
		sheet.setColumnWidth(PRICE_1_COL_INDEX, 5000 * 1);
		sheet.setColumnWidth(PRICE_2_COL_INDEX, 4000 * 1);
	}

	public static Row createRow(Sheet sheet, int rowIndex, float rowHeightInPoints) {

		Row row = sheet.createRow(rowIndex);
		row.setHeightInPoints(rowHeightInPoints);

		return row;
	}

	public static void setHeader(XSSFWorkbook xssfWorkbook, Row row) {

		XSSFFont xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, 30.0f, ItalicFont.NOT_ITALIC_FONT, HEADER_COLOR);
		XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.CENTER);
		Cell cell = row.createCell(0);
		cell.setCellStyle(xssfCellStyle);

		xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, 30.0f, ItalicFont.NOT_ITALIC_FONT, HEADER_COLOR);
		xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.CENTER);
		cell = row.createCell(1);
		cell.setCellStyle(xssfCellStyle);

		xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, 30.0f, ItalicFont.NOT_ITALIC_FONT, HEADER_COLOR);
		xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.CENTER);
		cell = row.createCell(PRICE_1_COL_INDEX);
		cell.setCellStyle(xssfCellStyle);
		cell.setCellValue("normal");

		xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, 25.0f, ItalicFont.ITALIC_FONT, HEADER_COLOR);
		xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.CENTER);
		cell = row.createCell(PRICE_2_COL_INDEX);
		cell.setCellStyle(xssfCellStyle);
		cell.setCellValue("géant");

	}

	public static void setTitle(XSSFWorkbook xssfWorkbook, Sheet sheet, Row row, String value) {

		XSSFFont xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, TITLE_FONT_HEIGHT, ItalicFont.ITALIC_FONT,
				TITLE_COLOR);
		XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.CENTER);

		xssfCellStyle.setWrapText(true);

		setBorder(xssfCellStyle);

		Cell cell = row.createCell(TITLE_COL_INDEX);
		cell.setCellStyle(xssfCellStyle);

		value = value.substring(1, value.length() - 1);
		cell.setCellValue(value);

		sheet.addMergedRegion(
				new CellRangeAddress(row.getRowNum(), row.getRowNum(), TITLE_COL_INDEX, PRICE_2_COL_INDEX));

	}

	// public static void setIndexCell(XSSFWorkbook xssfWorkbook, Row row, int
	// value) {
	//
	// XSSFFont xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME,
	// INDEX_REF_FONT_HEIGHT, ItalicFont.NOT_ITALIC_FONT,
	// PRODUCT_COLOR);
	// XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont,
	// HorizontalAlignment.CENTER);
	//
	// setBorder(xssfCellStyle);
	//
	// Cell cell = row.createCell(INDEX_REF_COL_INDEX);
	// cell.setCellStyle(xssfCellStyle);
	// cell.setCellValue("" + value);
	//
	// }

	public static void setEmptyRow(XSSFWorkbook xssfWorkbook, Sheet sheet, Row row) {

		XSSFFont xssfFont = setFont(xssfWorkbook, DESCRIPTION_FONT_NAME, DESCRIPTION_FONT_HEIGHT,
				ItalicFont.NOT_ITALIC_FONT, DESCRPTION_COLOR);
		XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.LEFT);

		Cell cell = row.createCell(0);
		cell.setCellStyle(xssfCellStyle);

		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, PRICE_2_COL_INDEX));

	}

	public static void setLabelCell(XSSFWorkbook xssfWorkbook, Row row, String value) {

		XSSFFont xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, LABEL_FONT_HEIGHT, ItalicFont.ITALIC_FONT,
				PRODUCT_COLOR);
		XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.LEFT);

		xssfCellStyle.setWrapText(true);

		setBorder(xssfCellStyle);

		Cell cell = row.createCell(LABEL_COL_INDEX);
		cell.setCellStyle(xssfCellStyle);
		cell.setCellValue(value);

	}

	public static void setDescriptionCell(XSSFWorkbook xssfWorkbook, Sheet sheet, Row row, String value) {

		XSSFFont xssfFont = setFont(xssfWorkbook, DESCRIPTION_FONT_NAME, DESCRIPTION_FONT_HEIGHT,
				ItalicFont.NOT_ITALIC_FONT, DESCRPTION_COLOR);
		XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.LEFT);

		Cell cell = row.createCell(0);
		cell.setCellStyle(xssfCellStyle);

		xssfFont = setFont(xssfWorkbook, DESCRIPTION_FONT_NAME, DESCRIPTION_FONT_HEIGHT, ItalicFont.NOT_ITALIC_FONT,
				DESCRPTION_COLOR);
		xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.LEFT);

		xssfCellStyle.setWrapText(true);

		setBorder(xssfCellStyle);

		cell = row.createCell(DESCRIPTION_COL_INDEX);
		cell.setCellStyle(xssfCellStyle);
		cell.setCellValue(value);

		sheet.addMergedRegion(
				new CellRangeAddress(row.getRowNum(), row.getRowNum(), DESCRIPTION_COL_INDEX, PRICE_2_COL_INDEX));

	}

	public static void setPrice1Cell(XSSFWorkbook xssfWorkbook, Row row, double value) {

		XSSFFont xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, PRICE_1_FONT_HEIGHT, ItalicFont.NOT_ITALIC_FONT,
				PRODUCT_COLOR);
		XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.CENTER);
		setBorder(xssfCellStyle);
		Cell cell = row.createCell(PRICE_1_COL_INDEX);
		cell.setCellStyle(xssfCellStyle);

		if (value > 0) {
			cell.setCellValue(value);
			XSSFDataFormat xssfDataFormat = xssfWorkbook.createDataFormat();
			xssfCellStyle.setDataFormat(xssfDataFormat.getFormat("#,##0.00"));
		}

	}

	public static void setPrice2Cell(XSSFWorkbook xssfWorkbook, Row row, double value) {

		XSSFFont xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, PRICE_2_FONT_HEIGHT, ItalicFont.ITALIC_FONT,
				PRODUCT_COLOR);
		XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.CENTER);
		setBorder(xssfCellStyle);
		Cell cell = row.createCell(PRICE_2_COL_INDEX);
		cell.setCellStyle(xssfCellStyle);

		if (value > 0) {
			cell.setCellValue(value);
			XSSFDataFormat xssfDataFormat = xssfWorkbook.createDataFormat();
			xssfCellStyle.setDataFormat(xssfDataFormat.getFormat("#,##0.00"));
		}

	}

	public static void setLogo(XSSFWorkbook xssfWorkbook, Sheet sheet, Row row, String imagePath) throws IOException {

		XSSFFont xssfFont = setFont(xssfWorkbook, GENERIC_FONT_NAME, PRICE_2_FONT_HEIGHT, ItalicFont.ITALIC_FONT,
				PRODUCT_COLOR);
		XSSFCellStyle xssfCellStyle = setBackground(xssfWorkbook, xssfFont, HorizontalAlignment.CENTER);

		Cell cell = row.createCell(LOGO_COL_INDEX);
		cell.setCellStyle(xssfCellStyle);

		if (imagePath != null && !imagePath.isEmpty() && !"NULL".equals(imagePath.toUpperCase())) {

			imagePath = "image/" + imagePath + ".png";

			final InputStream stream = XlsAfficheBodyWriterHelper.class.getClassLoader().getResourceAsStream(imagePath);
			final CreationHelper helper = xssfWorkbook.getCreationHelper();
			final Drawing<?> drawing = sheet.createDrawingPatriarch();

			final ClientAnchor anchor = helper.createClientAnchor();
			anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

			final int pictureIndex = xssfWorkbook.addPicture(IOUtils.toByteArray(stream), Workbook.PICTURE_TYPE_PNG);

			anchor.setCol1(0);
			anchor.setCol2(0);
			anchor.setRow1(row.getRowNum());
			anchor.setRow2(row.getRowNum());

			final Picture pict = drawing.createPicture(anchor, pictureIndex);
			pict.resize(1, 0.5);

		}
	}

	public static XSSFFont setFont(XSSFWorkbook xssfWorkbook, String fontName, double fontHeight, ItalicFont italicFont,
			XSSFColor xssfColor) {
		XSSFFont xssfFont = xssfWorkbook.createFont();
		xssfFont.setFontHeight(fontHeight);
		xssfFont.setFontName(fontName);
		xssfFont.setColor(xssfColor);
		xssfFont.setItalic(italicFont.value);
		return xssfFont;
	}

	public static XSSFCellStyle setBackground(XSSFWorkbook xssfWorkbook, XSSFFont xssfFont,
			HorizontalAlignment horizontalAlignment) {
		XSSFCellStyle xssfCellStyle = xssfWorkbook.createCellStyle();
		xssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		xssfCellStyle.setAlignment(horizontalAlignment);
		xssfCellStyle.setFont(xssfFont);
		xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		xssfCellStyle.setFillForegroundColor(new XSSFColor(new Color(252, 238, 228)));
		xssfCellStyle.setFillBackgroundColor(new XSSFColor(new Color(252, 238, 228)));
		return xssfCellStyle;
	}

	public static void setBorder(XSSFCellStyle xssfCellStyle) {

		xssfCellStyle.setBorderBottom(BorderStyle.MEDIUM);
		xssfCellStyle.setBorderTop(BorderStyle.MEDIUM);
		xssfCellStyle.setBorderRight(BorderStyle.MEDIUM);
		xssfCellStyle.setBorderLeft(BorderStyle.MEDIUM);
		xssfCellStyle.setBottomBorderColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
		xssfCellStyle.setTopBorderColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
		xssfCellStyle.setRightBorderColor(new XSSFColor(new java.awt.Color(255, 255, 255)));
		xssfCellStyle.setLeftBorderColor(new XSSFColor(new java.awt.Color(255, 255, 255)));

	}

}