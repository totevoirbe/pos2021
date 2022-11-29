package pos.xml.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Document implements ModelValidator {

	private Long source;
	private Boolean cancelled;
	private String personnel;
	private Long date;
	private String company;
	private String computername;
	private String takeonplace;
	private List<Item> item;
	private List<Payement> payement;

	public Document() {
	}

	public Document(Long source, Boolean cancelled, String personnel, Long date, String company, String computername,
			String takeonplace, List<Item> item, List<Payement> payement) {
		super();
		this.source = source;
		this.cancelled = cancelled;
		this.personnel = personnel;
		this.date = date;
		this.company = company;
		this.computername = computername;
		this.takeonplace = takeonplace;
		this.item = item;
		this.payement = payement;
	}

	@XmlAttribute
	public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	@XmlAttribute
	public Boolean getCancelled() {
		if (cancelled == null || !cancelled) {
			if (item == null) {
				// check if sum of item quantity * price != 0
				boolean paymentValueNul = true;
				for (Payement payementInstance : payement) {
					if (payementInstance.getQuantity() != null && payementInstance.getValue() != null
							&& payementInstance.getQuantity() != 0 && payementInstance.getValue() != 0) {
						paymentValueNul = false;
					}
				}
				if (paymentValueNul) {
					cancelled = true;
				}
			}
			if (payement == null) {
				// check if sum of item quantity * price != 0
				boolean itemValueNul = true;
				for (Item itemInstance : item) {
					if (itemInstance.getQuantity() != null && itemInstance.getUnitPrice() != null
							&& itemInstance.getQuantity() != 0 && itemInstance.getUnitPrice() != 0) {
						itemValueNul = false;
					}
				}
				if (itemValueNul) {
					cancelled = true;
				}
			}
		}
		return cancelled;
	}

	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}

	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getComputername() {
		return computername;
	}

	public void setComputername(String computername) {
		this.computername = computername;
	}

	public String getTakeonplace() {
		return takeonplace;
	}

	public void setTakeonplace(String takeonplace) {
		this.takeonplace = takeonplace;
	}

	@XmlElement
	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	@XmlElement
	public List<Payement> getPayement() {
		return payement;
	}

	public void setPayement(List<Payement> payement) {
		this.payement = payement;
	}

	@Override
	public String toString() {
		return "Document [source=" + source + ", cancelled=" + cancelled + ", personnel=" + personnel + ", date=" + date
				+ ", company=" + company + ", computername=" + computername + ", takeonplace=" + takeonplace + ", item="
				+ item + ", payement=" + payement + "]";
	}

	@Override
	public boolean validate() throws ModelValidatorException {

		boolean valid = true;

		String messageError = "";

		if (source == null) {
			messageError += "(source is null)";
			valid = false;
		}
		if (personnel == null || personnel.trim().length() == 0) {
			messageError += "(personnel is null)";
			valid = false;
		}
		if (date == null) {
			messageError += "(date is null)";
			valid = false;
		}
		if (company == null || company.trim().length() == 0) {
			messageError += "(company is null)";
			valid = false;
		}
		if (computername == null || computername.trim().length() == 0) {
			messageError += "(computername is null)";
			valid = false;
		}
		if (takeonplace == null) {
			messageError += "(takeonplace is null)";
			valid = false;
		}

		if (!valid) {
			throw new ModelValidatorException("[Document-" + source + ":" + messageError + "]");
		}

		return true;
	}

}