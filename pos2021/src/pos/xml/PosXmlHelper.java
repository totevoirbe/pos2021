package pos.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import pos.xml.model.Documentlist;

public class PosXmlHelper {

	public static Documentlist extractSales(File file) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(Documentlist.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Documentlist documentList = (Documentlist) jaxbUnmarshaller.unmarshal(file);

		return documentList;
	}
}