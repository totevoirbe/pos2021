package pos.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBException;

import pos.xml.model.Documentlist;
import pos.xml.model.ModelValidatorException;

public class XmlReader {

	public static final String REPOSITORY_NAME = "/home/tote/Desktop/posSales";

	public static void main(String[] args) {

		Date from = new GregorianCalendar(2020, 1 - 1, 1).getTime();
		Date to = new GregorianCalendar(2020, 12 - 1, 31).getTime();

		try {
			List<Documentlist> documentLists = extractSales(from, to);

			for (Documentlist documentlist : documentLists) {
				try {
					documentlist.validate();
				} catch (ModelValidatorException e) {
					e.printStackTrace();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static List<Documentlist> extractSales(Date from, Date to) throws JAXBException {

		List<Documentlist> documentLists = new ArrayList<Documentlist>();

		File folder = new File(REPOSITORY_NAME);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {

			if (file.isFile()) {

				int year;
				int month;
				int day;

				if (file.getName() != null && file.getName().length() > 8) {

					year = Integer.parseInt(file.getName().substring(0, 4));
					month = Integer.parseInt(file.getName().substring(4, 6)) - 1;
					day = Integer.parseInt(file.getName().substring(6, 8));

					Date fileDate = new GregorianCalendar(year, month, day).getTime();

					if ((fileDate.after(from) && fileDate.before(to)) || fileDate.equals(from) || fileDate.equals(to)) {
						Documentlist documentList = PosXmlHelper.extractSales(file);
						documentLists.add(documentList);
					}
				}

			}
		}

		return documentLists;

	}

}
