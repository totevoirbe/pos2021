package pos.model;

import java.math.BigDecimal;

public class PayItem {

	private PayType payType;
	private BigDecimal quantity;
	private String description;
	private BigDecimal total;

	public PayItem(PayType payType, BigDecimal quantity, String description, BigDecimal total) {
		super();
		this.payType = payType;
		this.quantity = quantity;
		this.description = description;
		this.total = total;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PayItem [payType=" + payType + ", quantity=" + quantity + ", description=" + description + ", total="
				+ total + "]";
	}

}
