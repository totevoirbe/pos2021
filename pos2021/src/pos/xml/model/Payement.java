package pos.xml.model;

import pos.model.PayType;

public class Payement implements ModelValidator {

	private Integer quantity;
	private String description;
	private Integer mode;
	private Double value;

	public Payement() {
	}

	public Payement(Integer quantity, String description, Integer mode, Double value) {
		super();
		this.quantity = quantity;
		this.description = description;
		this.mode = mode;
		this.value = value;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Payement [quantity=" + quantity + ", description=" + description + ", mode=" + mode + ", value=" + value
				+ "]";
	}

	@Override
	public boolean validate() throws ModelValidatorException {

		boolean valid = true;

		String messageError = "";

		if (quantity == null) {
			messageError += "(quantity is null)";
			valid = false;
		}
		if (description == null || description.trim().length() == 0) {
			messageError += "(description is null)";
			valid = false;
		}
		if (mode == null) {
			messageError += "(mode is null)";
			valid = false;
		} else {
			PayType payType = PayType.getPayType(mode);
			if (description == null || !description.trim().equals(payType.getDescription())) {
				messageError += "(payement mode is " + mode + " and not " + payType.getDescription() + ")";
				valid = false;
			}

		}
		if (value == null) {
			messageError += "(value is null)";
			valid = false;
		}

		if (!valid) {
			throw new ModelValidatorException("[Payement-" + mode + ":" + messageError + "]");
		}

		return true;
	}

}
