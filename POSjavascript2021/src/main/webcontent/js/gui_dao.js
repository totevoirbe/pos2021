var SALE_TYPE = 8;
var ITEM_TYPE = 9;
var PRODUCT_TYPE = 7;
var ID_CAISSE = 9;

function GuiDao() {

	this.payType;
	this.operations;

	this.kbdList;
	this.kbdSelRowsList;
	this.kbdSelKeys;
	this.kbds;
	this.kbdSelRows;

	this.init = function() {

		this.operations = new Array();
		this.payType = new PayType(payTypesJson);

		this.kbdList = JSON.parse(kbdDefJson).kbds;
		this.kbdSelRowsList = JSON.parse(kbdListJson).kbdSelRows;
		this.kbdSelKeys = new Array();
		this.kbds = new Kbds();
		this.kbdSelRows = new KbdSelRows();

		var productArray = JSON.parse(productsJson);

		for (var index = 0; index < productArray.products.length; index++) {

			var productModel = productArray.products[index];

			var id = productModel.id;
			var label = productModel.htmlKeyLabel;
			var ticketLabel = productModel.name;

			var mini = productModel.mini;
			var normal = productModel.normal;
			var geant = productModel.geant;
			var fitmini = productModel.fitmini;
			var fitnormal = productModel.fitnormal;
			var fitgeant = productModel.fitgeant;

			this.operations.push(new Product(id, label, ticketLabel, mini,
					normal, geant, fitmini, fitnormal, fitgeant));

		}

		var payTypeArray = JSON.parse(payTypesJson);

		for (var index = 0; index < payTypeArray.payTypes.length; index++) {
			var payTypeDef = payTypeArray.payTypes[index];
			this.operations.push(new PayType(payTypeDef.id, payTypeDef.label,
					payTypeDef.ticketLabel));
		}

		for (var kbdIndex = 0; kbdIndex < this.kbdList.length; kbdIndex++) {
			var kbdObj = this.kbdList[kbdIndex];
			var kbd = new Kbd(kbdObj.name);
			this.kbds.kbds.push(kbd);
			for (var kbdRowIndex = 0; kbdRowIndex < kbdObj.kbdRows.length; kbdRowIndex++) {
				var kbdRowObj = kbdObj.kbdRows[kbdRowIndex];
				var kbdRow = new KbdRow();
				kbd.kbdRows.push(kbdRow);
				for (var kbdFctIndex = 0; kbdFctIndex < kbdRowObj.kbdFcts.length; kbdFctIndex++) {
					var kbdFctObj = kbdRowObj.kbdFcts[kbdFctIndex];
					var kbdFct = new KbdFct(kbdFctObj.id);
					kbdRow.kbdFcts.push(kbdFct);
				}
			}
		}

		for (var kbdSelRowIndex = 0; kbdSelRowIndex < this.kbdSelRowsList.length; kbdSelRowIndex++) {
			var kbdSelRowObj = this.kbdSelRowsList[kbdSelRowIndex];
			var kbdSelRow = new KbdSelRow();
			this.kbdSelRows.kbdSelRows.push(kbdSelRow);
			for (var kbdSelKeyIndex = 0; kbdSelKeyIndex < kbdSelRowObj.kbdSelKeys.length; kbdSelKeyIndex++) {
				var kbdSelKeyObj = kbdSelRowObj.kbdSelKeys[kbdSelKeyIndex];
				var kbdSelKey = new KbdSelKey(kbdSelKeyObj.id,
						kbdSelKeyObj.label, kbdSelKeyObj.title,
						kbdSelKeyObj.img);
				this.kbdSelKeys.push(kbdSelKey);
				kbdSelRow.kbdSelKeys.push(kbdSelKey);
			}
		}

	}

	this.getNewItem = function(operation, quantity, unitPrice, priceCategory) {

		var id = new Date().getTime() * 100 + ID_CAISSE * 10 + ITEM_TYPE;

		var item = new ItemModel(id, operation, quantity, unitPrice,
				priceCategory)
		return item

	}

	this.getNewSale = function() {

		var sale = new SaleModel();
		sale.id = new Date().getTime() * 100 + ID_CAISSE * 10 + SALE_TYPE;

		return sale

	}

	this.getProductByIndex = function(index) {
		return operations[index];
	}

	this.getProductById = function(id) {
		for (var index = 0; index < this.operations.length; index++) {
			var operation = this.operations[index];
			if (operation.id === id) {
				return operation;
			}
		}
	}

	this.getkbdSelector = function(name) {
		for (var index = 0; index < this.kbdSelKeys.length; index++) {
			var kbdSelKey = this.kbdSelKeys[index];
			if (kbdSelKey.keyId == name) {
				return kbdSelKey;
			}
		}
	}

	this.getKbdNumberOfRows = function(kbdIndex) {
		return this.kbds.kbds[kbdIndex].kbdRows.length();
	}

	this.getRowNumberOfFcts = function(kbdIndex, kbdRowIndex) {
		return this.kbds.kbds[kbdIndex].kbdRows[kbdRowIndex].kbdFcts.length();
	}

	this.get = function(kbdIndex, kbdRowIndex, kbdFctIndex) {
		return this.kbds.kbds[kbdIndex].kbdRows[kbdRowIndex].kbdFcts[kbdFctIndex].keyId;
	}

	this.getKbd = function(name) {
		var kbds = guiDao.kbds.kbds;

		for (var integer = 0; integer < kbds.length; integer++) {
			var kbd = kbds[integer];
			if (kbd.name == name) {
				return kbd
			}
		}
	}

	this.submitSale = function(sale) {
		console.log("[commandeSale.submitSale] " + sale);
		localStorageQueue.pushLocalStorage(sale);
	}

	this.writeSale = function(jsonMessage, postAction, posIndex,postError) {

		console.log("[guiDao.writeSale]json message : " + jsonMessage);

		$.ajax({
			type : 'POST',
			url : 'http://localhost:8080/pos/writeSale',
			contentType : "application/json",
			data : jsonMessage,
			timeout : 10000,
			postActionValue : postAction,
			postErrorValue : postError,
			posIndexValue : posIndex,
			success : function(data) {

				var returnedMessage = "[guiDao.writeSale]message["
						+ data.message + "], responseResult["
						+ data.responseResult + "], date[" + data.date + "]";

				console.log(returnedMessage);

				if ("SUCCESS" == data.responseResult && this.postActionValue) {
					this.postActionValue(this.posIndexValue);
				}

			},
			error : function(data) {
				var message = '[guiDao.writeSale]La requête n\'a pas abouti'
				console.error(message);
				console.error(data);
				if (this.postErrorValue) {
					this.postErrorValue(message);
				}
			}
		});

	};

	this.errorHandler = function(e) {
		var msg = '';

		switch (e.code) {
		case FileError.QUOTA_EXCEEDED_ERR:
			msg = 'QUOTA_EXCEEDED_ERR';
			break;
		case FileError.NOT_FOUND_ERR:
			msg = 'NOT_FOUND_ERR';
			break;
		case FileError.SECURITY_ERR:
			msg = 'SECURITY_ERR';
			break;
		case FileError.INVALID_MODIFICATION_ERR:
			msg = 'INVALID_MODIFICATION_ERR';
			break;
		case FileError.INVALID_STATE_ERR:
			msg = 'INVALID_STATE_ERR';
			break;
		default:
			msg = 'Unknown Error';
			break;
		}
		;

		console.log('Error: ' + msg);
	}
}

function LocalStorageQueue() {

	this.maxSize = localStorage.getItem('maxSize');
	this.lockPurge = false;

	if (!this.maxSize || this.maxSize == 'NaN') {

		this.maxSize = 5;
		localStorage.setItem('maxSize', this.maxSize);

	}

	this.pushLocalStorage = function(action, postError) {

		var posIndex = -1;

		for (var i = 0; i < this.maxSize; i++) {
			var storedAction = localStorage.getItem('action' + i);
			if (storedAction == null) {
				posIndex = i;
				break;
			}
		}

		if (posIndex == -1) {
			posIndex = this.maxSize;
			this.maxSize += 5;
			localStorage.setItem('maxSize', this.maxSize);
		}

		console.log("[LocalStorageQueue.pushLocalStorage] next empty case["
				+ posIndex + "], maxSize[" + this.maxSize + "]");

		var jsonMessage = JSON.stringify(action);

		localStorage.setItem('action' + posIndex, jsonMessage);

		this.purgeLocalStorage(localStorageQueue.removeLocalStorage, postError);

		return posIndex;

	}

	this.purgeLocalStorage = function(postAction, postError) {

		if (this.lockPurge) {
			console.log("[LocalStorageQueue.purgeLocalStorage] is locked");
			return;
		}

		this.lockPurge = true;

		console.log("[LocalStorageQueue.purgeLocalStorage] start");

		for (var index = this.maxSize - 1; index >= 0; index--) {
			var action = localStorage.getItem('action' + index);
			if (action != null) {
				var jsonMessage = localStorage.getItem('action' + index);
				guiDao.writeSale(jsonMessage, postAction, index, postError);

			}

		}

		console.log("[LocalStorageQueue.purgeLocalStorage] end");

		this.lockPurge = false;

	}

	this.removeLocalStorage = function(posIndex) {

		localStorage.removeItem('action' + posIndex);

		for (var index = this.maxSize - 1; index >= 5 && storedAction == null; index--) {
			var storedAction = localStorage.getItem('action' + index);
			if (storedAction == null) {
				this.maxSize = index + 1;
				localStorage.setItem('maxSize', this.maxSize);
			}
		}

	}

	this.clearLocalStorage = function() {
		localStorage.clear();
		this.maxSize = 5;
		localStorage.setItem('maxSize', this.maxSize);
	}

	this.getAll = function() {

		var actionList = new Array();

		for (var i = 0; i < this.maxSize; i++) {
			var action = localStorage.getItem('action' + i);
			actionList.push(action);
		}

		return actionList;
	}

	this.listLocalStorageQueue = function() {

		var actionList = this.getAll();
		console.log("[html.listLocalStorageQueue]action list size "
				+ actionList.length);

		for (var i = 0; i < actionList.length; i++) {
			var action = actionList[i];
			console.log("local storage " + i + " : " + action);
		}
	}

}