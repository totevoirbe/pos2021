package pos.model;

public enum SaleType {

	STOCK_AND_FREE_AND_OUT_OF_SALE, STOCK_AND_FREE, STOCK_AND_CHARGE;

	public boolean isToBeCharges() {

		switch (this) {
		case STOCK_AND_FREE_AND_OUT_OF_SALE: {
			return false;
		}
		case STOCK_AND_CHARGE: {
			return true;
		}
		case STOCK_AND_FREE: {
			return false;
		}
		default:
			throw new IllegalArgumentException("Saletype is to be charged Unexpected value: " + this);
		}

	}

	public boolean isSale() {

		switch (this) {
		case STOCK_AND_FREE_AND_OUT_OF_SALE: {
			return false;
		}
		case STOCK_AND_CHARGE: {
			return true;
		}
		case STOCK_AND_FREE: {
			return true;
		}
		default:
			throw new IllegalArgumentException("Saletype is sale Unexpected value: " + this);
		}

	}

}
