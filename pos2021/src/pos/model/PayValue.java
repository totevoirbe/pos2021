package pos.model;

import pos.xml.model.ModelValidatorException;

public class PayValue {

	public TotalValue valueAsJete = new TotalValue();
	public TotalValue valueAsGratuit = new TotalValue();
	public TotalValue valueAsIimpaye = new TotalValue();
	public TotalValue valueAsFidelite = new TotalValue();
	public TotalValue valueAsChequeRepas = new TotalValue();
	public TotalValue valueAsCash = new TotalValue();
	public TotalValue valueAsFacture = new TotalValue();

	public TotalValue valueTotal = new TotalValue();

	public PayValue() {
	}

	public void add(PayValue payValue) {
		valueAsJete.add(payValue.getValueAsJete());
		valueAsGratuit.add(payValue.getValueAsGratuit());
		valueAsIimpaye.add(payValue.getValueAsIimpaye());
		valueAsFidelite.add(payValue.getValueAsFidelite());
		valueAsChequeRepas.add(payValue.getValueAsChequeRepas());
		valueAsCash.add(payValue.getValueAsCash());
		valueAsFacture.add(payValue.getValueAsFacture());
		valueTotal.add(payValue.getValueTotal());
	}

	public void add(PayItem payItem) throws ModelValidatorException {

		TotalValue totalValue = null;

		PayType payType = payItem.getPayType();
		switch (payType) {

		case JETE: {
			totalValue = valueAsJete;
			break;
		}

		case GRATUIT: {
			totalValue = valueAsGratuit;
			break;
		}

		case IMPAYE: {
			totalValue = valueAsIimpaye;
			break;
		}

		case FIDELITE: {
			totalValue = valueAsFidelite;
			break;
		}

		case CHEQUE_REPAS: {
			totalValue = valueAsChequeRepas;
			break;
		}

		case CASH: {
			totalValue = valueAsCash;
			break;
		}

		case FACTURE: {
			totalValue = valueAsFacture;
			break;
		}

		default: {
			throw new ModelValidatorException("Unknown payType : " + payItem);
		}
		}

		if (payItem.getQuantity() != null) {
			totalValue.setValueQty(payItem.getQuantity().add(totalValue.getValueQty()));
			valueTotal.setValueQty(payItem.getQuantity().add(valueTotal.getValueQty()));
		}
		if (payItem.getTotal() != null) {
			totalValue.setValueTotal(payItem.getTotal().add(totalValue.getValueTotal()));
			valueTotal.setValueTotal(payItem.getTotal().add(valueTotal.getValueTotal()));
		}
	}

	public TotalValue getValueAsJete() {
		return valueAsJete;
	}

	public TotalValue getValueAsGratuit() {
		return valueAsGratuit;
	}

	public TotalValue getValueAsIimpaye() {
		return valueAsIimpaye;
	}

	public TotalValue getValueAsFidelite() {
		return valueAsFidelite;
	}

	public TotalValue getValueAsChequeRepas() {
		return valueAsChequeRepas;
	}

	public TotalValue getValueAsCash() {
		return valueAsCash;
	}

	public TotalValue getValueAsFacture() {
		return valueAsFacture;
	}

	public TotalValue getValueTotal() {
		return valueTotal;
	}

	@Override
	public String toString() {
		return "PayValue [valueAsJete=" + valueAsJete + ", valueAsGratuit=" + valueAsGratuit + ", valueAsIimpaye="
				+ valueAsIimpaye + ", valueAsFidelite=" + valueAsFidelite + ", valueAsChequeRepas=" + valueAsChequeRepas
				+ ", valueAsCash=" + valueAsCash + ", valueAsFacture=" + valueAsFacture + ", valueTotal=" + valueTotal
				+ "]";
	}

}