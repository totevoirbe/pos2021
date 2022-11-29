package pos.model;

import java.sql.Date;
import java.util.List;

public class Operation {

	private Date date;
	private PayType payType;
	private List<ProductItem> productItems;
	private List<PayItem> payItems;

	public Operation(Date date) {
		super();
		this.date = date;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public List<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}

	public List<PayItem> getPayItems() {
		return payItems;
	}

	public void setPayItems(List<PayItem> payItems) {
		this.payItems = payItems;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Operation [date=" + date + ", payType=" + payType + ", productItems=" + productItems + ", payItems="
				+ payItems + "]";
	}

}
