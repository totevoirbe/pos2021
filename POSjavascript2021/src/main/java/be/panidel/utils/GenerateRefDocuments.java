package be.panidel.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.panidel.exception.IllegalTypeException;
import be.panidel.model.ProductModels;

public class GenerateRefDocuments {

	private final static Logger LOG = LoggerFactory.getLogger(GenerateRefDocuments.class);

	private final static String DEFINITION = "";
	private final static String BASE = "resultat/";

	public static void main(String[] args) {

		try {

			// read product data from excel
			String xlsFileSce = DEFINITION + "POSproducts.xlsx";
			ProductModels productModels = XlsProductDataReaderHelper.parseXlsxfile(xlsFileSce);

			// generate data for affiche as excel file
			File xmlAfficheFileSce = new File(DEFINITION + "affichesDefinition.xml");
			String xlsAfficheBodyName = BASE + "AfficheBody.xlsx";
			RootAfficheDef rootAfficheDef = (RootAfficheDef) MarshalHelper.unmarchalXml(RootAfficheDef.class,
					xmlAfficheFileSce);
			OutputStream xlsOutputStream = new FileOutputStream(new File(xlsAfficheBodyName));
			XlsAfficheBodyWriter.writeXlsxfile(xlsOutputStream, rootAfficheDef, productModels);
			LOG.debug("" + rootAfficheDef);

			// generate data model for POS
			File fileDest = new File(BASE + "products.xml");
			RootPos10 rootPos10 = PosDataModelHelper.generatePos10Model(xlsFileSce, productModels);
			MarshalHelper.marchalToXml(rootPos10, fileDest);

			// check POS generated file
			RootPos10 rootread = (RootPos10) MarshalHelper.unmarchalXml(RootPos10.class, fileDest);
			LOG.debug("" + rootread);

		} catch (JAXBException | InvalidFormatException | IOException | IllegalTypeException e) {
			e.printStackTrace();
		}
	}

}