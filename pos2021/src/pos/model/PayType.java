package pos.model;

public enum PayType {

	JETE(10), GRATUIT(20), IMPAYE(30), FIDELITE(40), CHEQUE_REPAS(50), CASH(60), FACTURE(70);

	private SaleType saleType;
	private String description;

	public static PayType getPayType(int mode) {

		switch (mode) {
		// 10 - Jeté
		case 10: {
			return PayType.JETE;
		}
		// 20 - Gratuit
		case 20: {
			return PayType.GRATUIT;
		}
		// 30 - Impayé
		case 30: {
			return PayType.IMPAYE;
		}
		// 40 - Carte de fidélité
		case 40: {
			return PayType.FIDELITE;
		}
		// 50 - Chèque repas
		case 50: {
			return PayType.CHEQUE_REPAS;
		}
		// 60 - Cash
		case 60: {
			return PayType.CASH;
		}
		// 70 - Facture
		case 70: {
			return PayType.FACTURE;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + mode);
		}
	}

	private PayType(int mode) {

		switch (mode) {

		// 10 - Jeté
		case 10: {
			saleType = SaleType.STOCK_AND_FREE_AND_OUT_OF_SALE;
			description = "Jeté";
			break;
		}

		// 20 - Gratuit
		case 20: {
			saleType = SaleType.STOCK_AND_FREE;
			description = "Gratuit";
			break;
		}

		// 30 - Impayé
		case 30: {
			saleType = SaleType.STOCK_AND_FREE;
			description = "Impayé";
			break;
		}

		// 40 - Carte de fidélité
		case 40: {
			saleType = SaleType.STOCK_AND_FREE;
			description = "Carte de fidélité";
			break;
		}

		// 50 - Chèque repas
		case 50: {
			saleType = SaleType.STOCK_AND_CHARGE;
			description = "Cheque repas";
			break;
		}

		// 60 - Cash
		case 60: {
			saleType = SaleType.STOCK_AND_CHARGE;
			description = "Cash";
			break;
		}

		// 70 - Facture
		case 70: {
			saleType = SaleType.STOCK_AND_CHARGE;
			description = "Facture";
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + mode);
		}

	}

	public SaleType getSaleType() {
		return saleType;
	}

	public String getDescription() {
		return description;
	}

	public boolean isSale() {
		return saleType.isSale();
	}

	public boolean isToBeCharges() {
		return saleType.isToBeCharges();
	}

}
