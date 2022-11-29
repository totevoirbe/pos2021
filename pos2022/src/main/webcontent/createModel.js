var ID_CAISSE_TEST = 1;

function createItem(idParam) {

	var id = new Date().getTime() * 10 + ID_CAISSE_TEST;
	var productModels = '';
	var quantity = 2;
	var unitPrice = 3;
	var deleted = true;
	var priceCategory = 'SDWMINI';
	var item = new ItemModel(id, productModels, quantity, unitPrice, deleted,
			priceCategory);

	return item;

}

function createSale(idParam) {

	var id = new Date().getTime() * 10 + ID_CAISSE_TEST;
	var status = 'open';// {undefined}, open, close, cancel}
	var openDate = new Date();
	var endDate = new Date();
	var identifier = idParam;
	var source = '';
	var saleTotal = idParam + 0.1;
	var payTotal = idParam + 0.2;
	var nbArticles = idParam + 0.3;
	var remainValue = idParam + 0.4;
	var paySubTotal = idParam + 0.5;

	var items = new Array(createItem(idParam));

	var sale = new SaleModel(id, status, openDate, endDate, identifier, source,
			items, saleTotal, payTotal, nbArticles, remainValue, paySubTotal);

	return sale;

}
