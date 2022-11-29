function GuiDao() {

	this.products = new Array();

	var productArray = JSON.parse(PRODUCTS_JSON);

	for (var index = 0; index < productArray.products.length; index++) {
		var productModel = productArray.products[index];
		var product = new Product(productModel.id, productModel.name,
				productModel.htmlKeyLabel, productModel.type,
				productModel.image, productModel.codeTva, productModel.mini,
				productModel.normal, productModel.geant, productModel.fitmini,
				productModel.fitnormal, productModel.fitgeant,
				productModel.webDetail)
		this.products.push(product);

		console.log("dao:" + product.name + "/" + product.image + "/"
				+ product.geant + " - " + productModel.name + "/"
				+ productModel.image + "/" + productModel.geant);

	}

	this.findById = function(id) {
		for (var int = 0; int < this.products.length; int++) {
			var product = this.products[int];
			if (product.id === id) {
				return product;
			}
		}
	}
}

function Product(id, name, htmlKeyLabel, type, image, codeTva, mini, normal,
		geant, fitmini, fitnormal, fitgeant, webDetail) {
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
