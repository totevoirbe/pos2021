package pos.xml.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Documentlist implements ModelValidator {

	private Integer date;
	private List<Document> document;

	public Documentlist() {
	}

	public Documentlist(Integer date, List<Document> document) {
		super();
		this.date = date;
		this.document = document;
	}

	@XmlAttribute
	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	@XmlElement
	public List<Document> getDocument() {
		return document;
	}

	public void setDocument(List<Document> document) {
		this.document = document;
	}

	@Override
	public String toString() {
		return "Documentlist [date=" + date + ", document=" + document + "]";
	}

	@Override
	public boolean validate() throws ModelValidatorException {

		boolean valid = true;

		String messageError = "";

		if (date == null) {
			messageError += "(date is null)";
			valid = false;
		}

		if (document == null) {
			messageError += "(document is null)";
			valid = false;
		}

		if (!valid) {
			throw new ModelValidatorException("[DocumentList-" + date + ":" + messageError + "]");
		}

		return true;
	}
}