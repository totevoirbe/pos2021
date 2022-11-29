var calculator;
var saleEngine;
var commandSale;
var currentSession;
var sessions;
var guiDao;
var localStorageQueue;

$(document).ready(
		function() {

			localStorageQueue = new LocalStorageQueue();
			guiDao = new GuiDao();
			calculator = new Calculator();
			saleEngine = new SaleEngine();
			commandSale = new CommandSale();
			sessions = new Array();

			guiDao.init();
			viewInit();

			commandSale.switchSession("session1");
			commandSale.newSale();

			localStorageQueue.purgeLocalStorage(localStorageQueue
					.removeLocalStorage(index), postError);

			$(document).on('online', function() {
				console.log('online');
				localStorageQueue.purgeLocalStorage(localStorageQueue
						.removeLocalStorage(index), postError);
			});

			$(document).on('offline', function() {
				console.log('offline');
			});

		});

function CommandSale() {

	this.displayResume = function() {
		guiConfig.displayResume(currentSession.sale);
	}

	this.displayAdmin = function() {
		guiConfig.showAdminWindow();
	}

	this.cancelWorkItem = function() {
		currentSession.workItem = undefined;
	}

	this.switchSession = function(sessionId) {

		if (!currentSession) {
			currentSession = this.getNewSession(sessionId);
			sessions.push(currentSession);
		}

		if (sessionId == currentSession.sessionId) {
			return;
		}

		currentSession.currentMainKbdName = guiConfig.currentMainKbdName;
		var session = this.getSession(sessionId);
		if (session) {
			currentSession = session;
			guiConfig.setMainKbd(currentSession.currentMainKbdName);
		} else {
			currentSession = this.getNewSession(sessionId);
			sessions.push(currentSession);
		}

	}

	this.getSession = function(sessionId) {

		for (var integer = 0; integer < sessions.length; integer++) {
			var session = sessions[integer];
			if (session.sessionId == sessionId) {
				return session;
			}
		}

	}

	this.getNewSession = function(sessionId) {

		var session = new Session();

		session.calculatorCurrentDigitChain = "";
		session.saleEngineCurrentDigitChain = "";
		session.saleEngineIsQuantityField = true;
		session.sessionId = sessionId;
		session.sale = guiDao.getNewSale();

		return session;

	}

	this.isOpen = function() {

		if (currentSession.sale) {
			return currentSession.sale.status == 'open';
		}
		return false;

	}

	this.newSale = function() {

		calculator.reset();
		saleEngine.reset();
		this.cancelWorkItem();
		currentSession.sale = guiDao.getNewSale();

	}

	this.openSale = function() {

		this.newSale();
		currentSession.sale.status = 'open';
		currentSession.sale.openDate = new Date();
		currentSession.sale.items = new Array();

		return true;

	}

	this.endSale = function() {

		if (commandSale.isOpen()) {
			currentSession.sale.status = "done";
			this.cancelWorkItem();
			this.writeSale(currentSession.sale);
		}
		ticket.display();

	}

	this.cancelSale = function() {

		if (commandSale.isOpen()) {
			var ok = confirm("Confirmer annulation!");
			if (ok == true) {
				currentSession.sale.status = "cancel";
				this.cancelWorkItem();
				this.writeSale(currentSession.sale);
			}
		}
		ticket.display();
	}

	this.addProduct = function(operationId) {

		if (!this.isOpen()) {
			if (!this.openSale()) {
				return;
			}
		}

		var operation = guiDao.getProductById(operationId);
		var item;
		var price = operation.getPrice(guiConfig.currentMainKbdName);

		if (currentSession.workItem) {
			currentSession.workItem.operation = operation;
			if (!currentSession.workItem.unitPrice) {
				currentSession.workItem.unitPrice = price;
			}
			currentSession.workItem.quantity = roundValue(eval(currentSession.workItem.quantity));
			item = currentSession.workItem;
			this.saleAddItem(currentSession.sale, currentSession.workItem);
			this.cancelWorkItem();
		} else {
			item = guiDao.getNewItem(operation, 1, price,
					guiConfig.currentMainKbdName)
			this.saleAddItem(currentSession.sale, item);
		}

		this.computeSale(currentSession.sale);
		saleEngine.reset();
		calculator.reset();
		ticket.display();

	}

	this.addPayment = function(operationId, value) {

		if (!this.isOpen()) {
			return;
		}

		var operation = guiDao.getProductById(operationId);
		var item;

		if (currentSession.workItem) {
			currentSession.workItem.operation = operation;
			currentSession.workItem.quantity = roundValue(eval(currentSession.workItem.quantity));
			item = currentSession.workItem;
			if (value) {
				item.unitPrice = value;
			}
			if (item.unitPrice == 0) {
				item.unitPrice = item.quantity;
				item.quantity = 1;
			}
			this.saleAddItem(currentSession.sale, this.workItem);
			this.cancelWorkItem();
		} else {
			this.computeSale(currentSession.sale);
			if (value) {
				item = guiDao.getNewItem(operation, 1, value);
				this.saleAddItem(currentSession.sale, item);
			} else if (currentSession.sale.remainValue) {
				item = guiDao.getNewItem(operation, 1,
						currentSession.sale.remainValue);
				this.saleAddItem(currentSession.sale, item);
			}
		}

		this.computeSale(currentSession.sale);

		saleEngine.reset();
		calculator.reset();
		ticket.display();

	}

	this.deleteItem = function(item) {

		this.saleDeleteItem(currentSession.sale, item);

		this.computeSale(currentSession.sale);
		saleEngine.reset();
		calculator.reset();
		ticket.display();

	}

	this.updateItem = function(item, qty, value) {

		if (item.deleted) {
			this.saleUndeleteItem(currentSession.sale, item);
		}
		this.saleUpdateItem(currentSession.sale, item, qty, value);

		this.computeSale(currentSession.sale);
		saleEngine.reset();
		calculator.reset();
		ticket.display();

	}

	this.setQuantity = function(quantity) {
		if (!currentSession.workItem) {
			currentSession.workItem = guiDao.getNewItem()
			currentSession.workItem.unitPrice = 0;
		}
		currentSession.workItem.quantity = quantity;
		ticket.display();
	}

	this.setUnitPrice = function(unitPrice) {
		if (!currentSession.workItem) {
			currentSession.workItem = guiDao.getNewItem()
			currentSession.workItem.quantity = 1;
		}
		currentSession.workItem.unitPrice = unitPrice;
		ticket.display();
	}

	this.computeSale = function(sale) {

		sale.saleTotal = 0;
		sale.payTotal = 0;
		sale.nbArticles = 0;
		sale.remainValue = 0;
		sale.paySubTotal = new Array();

		sale.items
				.forEach(function(item) {
					if (!item.deleted) {
						if (Product.prototype.isPrototypeOf(item.operation)) {
							sale.saleTotal += item.quantity * item.unitPrice;
							sale.nbArticles += item.quantity;
						} else if (PayType.prototype
								.isPrototypeOf(item.operation)) {
							sale.payTotal += item.quantity * item.unitPrice;

							for (var index = 0; index < sale.paySubTotal.length; index++) {
								var subPay = sale.paySubTotal[index];
								if (subPay.operation == item.operation
										&& subPay.unitPrice == item.unitPrice) {
									subPay.quantity += item.quantity
								} else {
									subPay = guiDao.getNewItem(operation,
											quantity, unitPrice)
								}

							}
						} else {
							console.error(item.operation.prototype);
						}
					}
				});
		sale.saleTotal = roundValue(sale.saleTotal);
		sale.payTotal = roundValue(sale.payTotal);
		sale.nbArticles = roundValue(sale.nbArticles);
		sale.remainValue = roundValue(sale.saleTotal - sale.payTotal);
		console.log('compute');
	}

	this.setPriceOnLastItem = function(unitPrice) {
		if (currentSession.workItem) {
			if (currentSession.workItem.operation) {
				currentSession.workItem.unitPrice = unitPrice;
			} else {
				this.saleGetLastItem(currentSession.sale).unitPrice = unitPrice;
				commandSale.cancelWorkItem()
			}
		} else {
			this.saleGetLastItem(currentSession.sale).unitPrice = unitPrice;
		}
		ticket.display();
	}

	this.writeSale = function(sale) {
		sale.endDate = new Date();
		guiDao.submitSale(sale);
	}

	this.saleAddItem = function(sale, item) {
		var itemInTicket = this.saleGetItem(sale, item.operation,
				item.unitPrice);
		if (itemInTicket === undefined) {
			sale.items.push(item);
		} else {
			itemInTicket.quantity = itemInTicket.quantity + item.quantity;
		}
	}

	this.saleDeleteItem = function(sale, item) {
		if (!item) {
			console.err("no item to delete");
			return;
		}

		item.deleted = true;
	}

	this.saleUndeleteItem = function(sale, item) {
		if (!item) {
			console.err("no item to delete");
			return;
		}

		item.deleted = false;
	}

	this.saleUpdateItem = function(sale, item, qty, value) {
		if (!item) {
			console.err("no item to delete");
			return;
		}

		item.quantity = qty;
		item.unitPrice = value;

	}

	this.saleGetLastItem = function(sale) {
		return sale.items[sale.items.length - 1];
	}

	this.saleGetItem = function(sale, operation, unitPrice) {
		if (operation.canMerge()) {
			for (var index = 0; index < sale.items.length; index++) {
				var item = sale.items[index];
				if (!item.deleted && operation.id == item.operation.id
						&& item.unitPrice == unitPrice) {
					return item;
				}
			}

		}
	}

}

// Sale engine
function SaleEngine() {

	this.deleteDigit = function() {
		this.reset();
		ticket.display();
	}

	this.concatenateDigit = function(digit) {
		if (digit == 0
				&& (currentSession.saleEngineCurrentDigitChain === "" || currentSession.saleEngineCurrentDigitChain === '0')) {
			currentSession.saleEngineCurrentDigitChain = '0';
		} else {
			currentSession.saleEngineCurrentDigitChain += digit;
		}
		this.setValue(currentSession.saleEngineCurrentDigitChain);
	}

	this.operatorDigit = function(digit) {
		if (digit === "*") {
			if (currentSession.saleEngineIsQuantityField) {
				if (currentSession.saleEngineCurrentDigitChain !== "") {
					commandSale
							.setQuantity(eval(currentSession.saleEngineCurrentDigitChain));
				} else {
					commandSale.setQuantity(1);
				}
				currentSession.saleEngineCurrentDigitChain = "";
				currentSession.saleEngineIsQuantityField = false;
			}
		}
	}

	this.priceOperator = function() {
		if (currentSession.saleEngineCurrentDigitChain !== "") {
			commandSale
					.setPriceOnLastItem(eval(currentSession.saleEngineCurrentDigitChain));
			currentSession.saleEngineCurrentDigitChain = "";
		}
	}

	this.periodDigit = function() {
		if (currentSession.saleEngineCurrentDigitChain === "") {
			currentSession.saleEngineCurrentDigitChain = "0.";
		} else {
			currentSession.saleEngineCurrentDigitChain += ".";
		}
		this.setValue(currentSession.saleEngineCurrentDigitChain);
	}

	this.setValue = function() {
		if (currentSession.saleEngineIsQuantityField) {
			commandSale.setQuantity(currentSession.saleEngineCurrentDigitChain);
		} else {
			commandSale
					.setUnitPrice(currentSession.saleEngineCurrentDigitChain);
		}
	}

	this.reset = function() {
		currentSession.saleEngineCurrentDigitChain = "";
		currentSession.saleEngineIsQuantityField = true;
	}
}

// Calculator
function Calculator() {

	var operators = "+-*/%";

	this.deleteDigit = function() {
		this.reset();
		ticket.display();
	}

	this.concatenateDigit = function(digit) {
		if (digit == 0
				&& (currentSession.calculatorCurrentDigitChain === "" || currentSession.calculatorCurrentDigitChain === '0')) {
			currentSession.calculatorCurrentDigitChain = '0';
		} else {
			currentSession.calculatorCurrentDigitChain += digit;
		}
		displayTotal
				.displayCalcResult(currentSession.calculatorCurrentDigitChain);
	}

	this.operatorDigit = function(digit) {
		if (currentSession.calculatorCurrentDigitChain !== "") {
			var lastChar = currentSession.calculatorCurrentDigitChain
					.substr(currentSession.calculatorCurrentDigitChain.length - 1);
			if (operators.indexOf(lastChar) >= 0) {
				currentSession.calculatorCurrentDigitChain = currentSession.calculatorCurrentDigitChain
						.substr(
								0,
								currentSession.calculatorCurrentDigitChain.length - 1)
						+ digit
			} else {
				currentSession.calculatorCurrentDigitChain += digit;
			}
			displayTotal
					.displayCalcResult(currentSession.calculatorCurrentDigitChain);
		}
	}

	this.periodDigit = function() {
		if (currentSession.calculatorCurrentDigitChain === "") {
			currentSession.calculatorCurrentDigitChain = "0.";
		} else {
			currentSession.calculatorCurrentDigitChain += ".";
		}
		displayTotal
				.displayCalcResult(currentSession.calculatorCurrentDigitChain);
	}

	this.equalDigit = function() {
		var total = Math
				.round(eval(currentSession.calculatorCurrentDigitChain) * 100) / 100;
		this.reset();
		saleEngine.reset();
		ticket.display();
		displayTotal.displayCalcResult(total);
	}

	this.reset = function() {
		currentSession.calculatorCurrentDigitChain = "";
	}
}