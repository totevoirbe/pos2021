var DEFAULT_PRICE_CATEGORY = "sdwnormal";

function ProductModel(id, name, htmlKeyLabel, type, image, codeTva, mini,
		normal, geant, fitmini, fitnormal, fitgeant, webDetail) {

	this.id = id;
	this.name = name;
	this.htmlKeyLabel = htmlKeyLabel;
	this.type = type;
	this.image = image;
	this.codeTva = codeTva;
	this.mini = mini;
	this.normal = normal;
	this.geant = geant;
	this.fitmini = fitmini;
	this.fitnormal = fitnormal;
	this.fitgeant = fitgeant;
	this.webDetail = webDetail;

}

/**
 * @param id
 * @param label
 * @param ticketLabel
 * @returns
 */
function Operation(id, label, ticketLabel) {

	this.id = id;
	this.label = label;
	this.ticketLabel = ticketLabel;
	this.canMerge = function(item) {
		return true;
	}

}

Operation.prototype = Object.create(Operation.prototype);
Operation.prototype.constructor = Operation;

function Product(id, label, ticketLabel, mini, normal, geant, fitmini,
		fitnormal, fitgeant) {

	Operation.call(this, id, label, ticketLabel);

	this.mini = mini;
	this.normal = normal;
	this.geant = geant;
	this.fitmini = fitmini;
	this.fitnormal = fitnormal;
	this.fitgeant = fitgeant;

	this.getTicketLabel = function(productType) {

		console.log("table, ProductType:" + productType);

		if (productType == "sdwmini") {
			return label + " (mini)";
		} else if (productType == "sdwgeant") {
			return label + " (géant)";
		} else if (productType == "fitmini") {
			return label + " (fit.mini)";
		} else if (productType == "fitnormal") {
			return label + " (fitness)";
		} else if (productType == "fitgeant") {
			return label + " (fit.géant)";
		}

		return label;
	}

	this.getPrice = function(productType) {

		var price = normal;

		console.log("price, ProductType:" + productType);

		if (productType == "sdwmini") {
			price = mini;
		} else if (productType == "sdwnormal") {
			price = normal;
		} else if (productType == "sdwgeant") {
			price = geant;
		} else if (productType == "fitmini") {
			price = fitmini;
		} else if (productType == "fitnormal") {
			price = fitnormal;
		} else if (productType == "fitgeant") {
			price = fitgeant;
		}

		if (price === undefined)
			price = normal;

		return price;
	}

	this.canMerge = function(item) {
		return true;
	}
}

Product.prototype = Object.create(Operation.prototype);
Product.prototype.constructor = Product;

function PayType(id, label, ticketLabel) {
	Operation.call(this, id, label, ticketLabel);

	this.getTicketLabel = function(productType) {
		return label;
	}
}

PayType.prototype = Object.create(Operation.prototype);
PayType.prototype.constructor = PayType;

function ItemModel(id, operation, quantity, unitPrice, priceCategory) {

	this.id = id;
	this.operation = operation;
	this.quantity = quantity;
	this.unitPrice = unitPrice;
	this.deleted = false;

	if (priceCategory) {
		this.priceCategory = priceCategory;
	} else {
		this.priceCategory = DEFAULT_PRICE_CATEGORY;
	}

	this.toString = function() {

		return "id[" + this.id + "],operation[" + this.operation
				+ "], quantity[" + this.quantity + "], unitPrice["
				+ this.unitPrice + "], deleted[" + this.deleted
				+ "], priceCategory[" + this.priceCategory + "]";

	}

}

function SaleModel(id, status, openDate, endDate, identifier, source, items,
		saleTotal, payTotal, nbArticles, remainValue, paySubTotal) {

	this.status=status; // {undefined}, open, close, cancel
	this.openDate=openDate;
	this.endDate=endDate;
	this.identifier=identifier;
	this.source=source;
	this.items=items;
	this.saleTotal=saleTotal;
	this.payTotal=payTotal;
	this.nbArticles=nbArticles;
	this.remainValue=remainValue;
	this.paySubTotal=paySubTotal;
	this.id=id;

}

function Session() {

	this.sessionId;
	this.sale;
	this.workItem;
	this.calculatorCurrentDigitChain;
	this.saleEngineCurrentDigitChain;
	this.saleEngineIsQuantityField;
	this.currentMainKbdName;

}

function Kbds() {

	this.kbds = new Array();

}

function Kbd(name) {

	this.kbdRows = new Array();
	this.name = name;

}

function KbdRow() {

	this.kbdFcts = new Array();

}

function KbdFct(keyId) {

	this.keyId = keyId;

}

function KbdSelRows() {

	this.kbdSelRows = new Array();

}

function KbdSelRow() {

	this.kbdSelKeys = new Array();

}

function KbdSelKey(keyId, label, title, img) {

	this.keyId = keyId;
	this.label = label;
	this.title = title;
	this.img = img;

}