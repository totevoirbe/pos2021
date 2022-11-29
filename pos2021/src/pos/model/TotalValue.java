package pos.model;

import java.math.BigDecimal;

public class TotalValue {

	private BigDecimal valueQty = BigDecimal.ZERO;
	private BigDecimal valueTotal = BigDecimal.ZERO;

	public TotalValue() {
		super();
	}

	public void add(TotalValue totalValue) {
		valueQty = valueQty.add(totalValue.getValueQty());
		valueTotal = valueTotal.add(totalValue.getValueTotal());
	}

	public BigDecimal getValueQty() {
		return valueQty;
	}

	public void setValueQty(BigDecimal valueQty) {
		this.valueQty = valueQty;
	}

	public BigDecimal getValueTotal() {
		return valueTotal;
	}

	public void setValueTotal(BigDecimal valueTotal) {
		this.valueTotal = valueTotal;
	}

	@Override
	public String toString() {
		return "TotalValue [valueQty=" + valueQty + ", valueTotal=" + valueTotal + "]";
	}

}
