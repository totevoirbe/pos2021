var ticket;
var displayTotal;
var itemEditBodyQtyQty;
var itemEditBodyQtyQtyUp;
var itemEditBodyQtyQtyDown;
var itemEditBodyQtyValue;
var itemEditBodyQtyValueUp;
var itemEditBodyQtyValueDown;
var itemEditBodyDeleteModal;
var itemEditBodyUndeleteModal;
var itemEdit;
var itemEditBodyValidateModal;
var kbdRemainStatusButton;
var kbdMainDiv;
var kbdSelDiv;
var kbdSelButtons;
var kbdSessionButtons;
var guiConfig;
var nomClavierDiv;

function viewInit() {

	itemEdit = $("#item-edit");
	itemEditBodyQtyQty = $("#item-edit-body-qty input");
	itemEditBodyQtyQtyUp = $("#item-edit-body-qty button:first-child");
	itemEditBodyQtyQtyDown = $("#item-edit-body-qty button:nth-child(2)");
	itemEditBodyQtyValue = $("#item-edit-body-value input");
	itemEditBodyQtyValueUp = $("#item-edit-body-value button:first-child");
	itemEditBodyQtyValueDown = $("#item-edit-body-value button:nth-child(2)");
	itemEditBodyValidateModal = $("#item-edit-body-validate-modal");
	itemEditBodyDeleteModal = $("#item-edit-body-delete-modal");
	itemEditBodyUndeleteModal = $("#item-edit-body-undelete-modal");
	
	
	
	kbdRemainStatusButton = $(".kbd-remain-status");
	kbdMainDiv = $("#kbd-main");
	kbdSelDiv = $("#kbd-sel");
	kbdSessionButtons = $(".kbd-session");
	nomClavierDiv = $("#nom-clavier")

	guiConfig = new GuiConfig();
	ticket = new Ticket();

	guiConfig.setSelKbd();
	guiConfig.setMainKbd("sdwnormal");
	guiConfig.setButtons($('.kbd-lg'),"sdwnormal");

	displayTotal = new DisplayTotal();
	ticket.initModal();
	ticket.displayLongTicket(false);

	$('[data-toggle="tooltip"]').tooltip();

	setSaleEngine();
	setCalculator();

	guiConfig.selectSession("session1");

}

function GuiConfig() {

	var currentMainKbdName;
	var currentSpanStatus; // session selection button
	var resumeSaleWindow;
	var resumeSaleDiv;

	this.showSaleResumeWindow = function(detail) {

		if (!this.resumeSaleWindow || this.resumeSaleWindow.closed) {

			this.resumeSaleWindow = window.open('guiResume.html', '',
					'width=800,height=400,resizable=1');
			
			setTimeout(function(){
				guiConfig.resumeSaleDiv = $(guiConfig.resumeSaleWindow.document).find('#sale-resume');
				guiConfig.resumeSaleDiv.html(detail);
				    }, 300);
		}
		guiConfig.resumeSaleDiv.html(detail);
		this.resumeSaleWindow.focus();

	}

	this.showAdminWindow = function() {

		this.adminWindow = window.open('guiAdmin.html', '',
					'width=800,height=400,resizable=1');
		this.adminWindow.focus();

	}

	this.selectSession = function(sessionId) {

		for (var index = 0; index < kbdSessionButtons.length; index++) {
			var kbdSessionButton = kbdSessionButtons[index];
			var span = kbdSessionButton.getElementsByTagName("span")[0];
			if (kbdSessionButton.id == sessionId) {
				kbdSessionButton.style.background = 'orange';
				kbdSessionButton.style.color = 'white';
				this.currentSpanStatus = span;
			} else {
				kbdSessionButton.style.background = 'yellow';
				kbdSessionButton.style.color = 'white';
			}
		}
		commandSale.switchSession(sessionId);
		ticket.display();
	}

	this.setSelKbd = function() {

		kbdSelDiv.html("");

		var kbdSelRows = guiDao.kbdSelRows;

		kbdSelRows.kbdSelRows.forEach(function(kbdSelRow) {

			var selKbdRowDiv = document.createElement('div');
			selKbdRowDiv.setAttribute('class', 'row');
			kbdSelDiv[0].appendChild(selKbdRowDiv);

			kbdSelRow.kbdSelKeys.forEach(function(kbdSelKey) {
				var selKbdButton = document.createElement('button');
				selKbdButton.setAttribute('class',
						'btn btn-lg btn-default kbd-sel');
				selKbdButton.setAttribute('id', kbdSelKey.keyId);
				selKbdButton.addEventListener("click", function(event) {
					guiConfig.setMainKbd(kbdSelKey.keyId);
				});
				selKbdButton.innerHTML = kbdSelKey.label;
				selKbdRowDiv.appendChild(selKbdButton);
			})
		});

		kbdSelButtons = $(".kbd-sel");

	}

	this.setMainKbd = function(name) {

		for (var index = 0; index < kbdSelButtons.length; index++) {
			var kbdSelButton = kbdSelButtons[index];
			if (kbdSelButton.id == name) {
				kbdSelButton.style.background = 'green';
				kbdSelButton.style.color = 'white';
			} else {
				kbdSelButton.style.background = 'blue';
				kbdSelButton.style.color = 'white';
			}
		}

		kbdMainDiv.html("");

		var kbd = guiDao.getKbd(name);
		var kbdSelector = guiDao.getkbdSelector(name);

		nomClavierDiv.html('<img alt="Nom du clavier" src="' + kbdSelector.img
				+ '">' + ' ' + kbdSelector.title);

		kbd.kbdRows.forEach(function(kbdRow) {

			var mainKbdRowDiv = document.createElement('div');
			mainKbdRowDiv.setAttribute('class', 'row');
			kbdMainDiv[0].appendChild(mainKbdRowDiv);

			kbdRow.kbdFcts.forEach(function(kbdFct) {
				var mainKbdButton = document.createElement('button');
				mainKbdButton.setAttribute('class',
						'btn btn-lg btn-primary kbd');
				mainKbdButton.setAttribute('type', 'button');
				mainKbdButton.setAttribute('id', kbdFct.keyId);
				mainKbdRowDiv.appendChild(mainKbdButton);
				guiConfig.setButton(mainKbdButton, name);

			})
		});

		this.currentMainKbdName = name;

	}

	this.setButton = function(kbdButton, kbdName) {

		var operation = guiDao.getProductById(kbdButton.id);

		if (operation) {

			if (operation.label) {

				
				var operationSplit = operation.label.split('<br>');

				if (operationSplit[0]) {
					operation.label = operationSplit[0].substr(0, 7) + '<br>';
				}

				if (operationSplit[1]) {
					operation.label += operationSplit[1].substr(0, 7) + '<br>';
				} else {
					operation.label += '<br>';
				}

				kbdButton.innerHTML = operation.label;
			}

			if (Product.prototype.isPrototypeOf(operation)) {

				var kbdPrice = operation.getPrice(kbdName);

				var span = document.createElement('span');
				span.appendChild(document.createTextNode(kbdPrice));
				span.setAttribute('class', 'badge');
				kbdButton.appendChild(span);

				kbdButton.addEventListener("click", function(event) {
					commandSale.addProduct(event.target.id);
				});

			} else if (PayType.prototype.isPrototypeOf(operation)) {

				var value = kbdButton.value;

				console.log("The kbd value : "+operation.label+"/"+value);

				
				if (value) {

					var span = document.createElement('span');
					span.appendChild(document.createTextNode(value));
					span.setAttribute('class', 'badge');
					kbdButton.appendChild(span);

					kbdButton.addEventListener("click", function(event) {
						commandSale.addPayment(event.target.id, value);
					});
				} else {
					kbdButton.addEventListener("click", function(event) {
						commandSale.addPayment(event.target.id);
					});
				}

			}
		}
	}

	this.setButtons = function(kbdButtons, kbdName) {

		for (var int = 0; int < kbdButtons.length; int++) {

			var kbdButton = kbdButtons[int]
			guiConfig.setButton(kbdButton, kbdName);

		}
	}

	this.displayResume = function(sale) {


		var detail = '';
		if (sale.status == "open") {
			detail += 'Vente en cours : ';
		} else if (sale.status == "done") {
			detail += 'Détail vente terminée : ';
		} else if (sale.status == "cancel") {
			detail += 'Détail vente annulée : ';
		} else {
			detail += 'Aucune vente a afficher';
		}

		if (sale.status == "open" || sale.status == "done"
				|| sale.status == "cancel") {
			detail += sale.nbArticles
			detail += ' produit(s) pour un total de '
			detail += sale.saleTotal;
			detail += '<br>Payé : '
			detail += sale.payTotal;
			if (sale.remainValue
					&& sale.remainValue == 0) {
				detail += '<br>Soldé.'
			} else if (sale.remainValue
					&& sale.remainValue > 0) {
				detail += '<br>Reste à payer : '
				detail += sale.remainValue;
			} else if (sale.remainValue
					&& sale.remainValue < 0) {
				detail += '<br>A rendre : '
				detail += (-sale.remainValue);
			}
			detail += "<br>Début : "
			detail += getDateAndTime(sale.openDate);
			if (sale.endDate) {
				detail += "<br>Fin : "
				detail += getDateAndTime(sale.endDate)
			}
		}

		guiConfig.showSaleResumeWindow(detail);

	}

}

function Ticket() {

	this.longTicket = false;
	this.ticketTableDiv = $('#ticket_table')[0];
	this.ticketBodyDiv = $("#ticket_body")[0];

	this.initModal = function() {

		this.modalValueEngineQty = new modalValueEngine(itemEditBodyQtyQty);
		this.modalValueEngineValue = new modalValueEngine(itemEditBodyQtyValue);

	}

	this.displayLongTicket = function(forceLong) {

		if (this.longTicket === true || forceLong === false) {
			this.longTicket = false;
			this.ticketBodyDiv.style.height = "335px";
		} else {
			this.longTicket = true;
			this.ticketBodyDiv.style.height = "880px";
		}

	}

	this.scrollDownTicket = function() {
		
		var scrollTop = this.ticketBodyDiv.scrollTop;
		this.ticketBodyDiv.scrollTop = scrollTop += 35;

	}

	this.scrollUpTicket = function() {

		var scrollTop = this.ticketBodyDiv.scrollTop;
		scrollTop -= 20;
		this.ticketBodyDiv.scrollTop = scrollTop;

	}

	this.display = function() {

		var firstRowIndex = 1;

		this.displayLongTicket(false);
		this.ticketBodyDiv.scrollTop = 0;

		while (this.ticketTableDiv.rows.length - 1) {
			this.ticketTableDiv.deleteRow(1);
		}

		if (currentSession.workItem) {
			this.insertRowItem(currentSession.workItem, firstRowIndex);
			firstRowIndex += 1;
		}

		if (currentSession.sale.items && currentSession.sale.items.length > 0) {
			currentSession.sale.items.forEach(function(item) {
				ticket.insertRowItem(item, firstRowIndex);
			});
		}
		displayTotal.displaySaleStatus(currentSession.sale);

	}

	this.insertRowItem = function(item, rowIndex) {

		var row = this.ticketTableDiv.insertRow(rowIndex);
		var qtyCell = row.insertCell(0);
		var labelCell = row.insertCell(1);
		var priceCell = row.insertCell(2);

		var linkLabelCell = document.createElement('a');
		labelCell.appendChild(linkLabelCell);

		var linkQtyCell = document.createElement('a');
		qtyCell.appendChild(linkQtyCell);

		// set Unit price
		linkQtyCell.innerHTML = item.quantity;
		priceCell.innerHTML = item.unitPrice;
		if (item.operation) {
			linkLabelCell.innerHTML = item.operation.getTicketLabel(item.priceCategory);
			if (PayType.prototype.isPrototypeOf(item.operation)) {
				if (item.quantity * item.unitPrice >= 0) {
					labelCell.style.color = 'blue';
					qtyCell.style.color = 'blue';
					priceCell.style.color = 'blue';
				} else {
					labelCell.style.color = 'red';
					qtyCell.style.color = 'red';
					priceCell.style.color = 'red';
				}
			}
		}
		linkLabelCell.addEventListener("click", function(event) {
			showModal(item);
		});
		linkQtyCell.addEventListener("click", function(event) {
			showModal(item);
		});
		if (item.deleted) {
			row.className = 'ticket_item_deleted';
		}
	}

}

function DisplayTotal() {

	this.jQueryRemainValueDiv = $("#calc_result");
	this.remainValueDiv = this.jQueryRemainValueDiv[0];

	this.displayCalcResult = function(value) {

		if (value.length > 16) {
			alert("longueur maximum atteinte");
		}

		this.remainValueDiv.style.textAlign = 'right';
		this.remainValueDiv.innerHTML = 'Calc:' + value;

		if (value > 0) {
			this.remainValueDiv.style.color = 'blue';
		} else if (value == 0) {
			this.remainValueDiv.style.color = 'green';
		} else if (value < 0) {
			this.remainValueDiv.style.color = 'red';
		} else {
			this.remainValueDiv.style.color = 'grey';
		}

	}

	this.displaySaleStatus = function(sale) {

		var detail = '';
		var kbdRemainStatusBackgroundColor;
		var kbdRemainStatusTextColor;
		this.remainValueDiv.style.textAlign = 'right';
		this.remainValueDiv.style.backgroundColor = 'white';
		this.jQueryRemainValueDiv.off('click');

		if (sale.remainValue > 0
				&& sale.status == 'open') {
			this.remainValueDiv.style.color = 'blue';
			kbdRemainStatusBackgroundColor = 'blue';
			kbdRemainStatusTextColor = 'white';
			this.remainValueDiv.innerHTML = detail + 'A payer:'
					+ sale.remainValue;
		} else if (sale.remainValue == 0
				&& sale.status == 'open') {
			this.remainValueDiv.style.color = 'white';
			this.remainValueDiv.style.backgroundColor = 'green';
			kbdRemainStatusBackgroundColor = 'green';
			kbdRemainStatusTextColor = 'white';
			this.remainValueDiv.innerHTML = detail + 'click - fin';
			this.remainValueDiv.style.textAlign = 'center';
			this.jQueryRemainValueDiv.on('click', function(e) {
				commandSale.endSale()
			});
		} else if (sale.remainValue < 0
				&& sale.status == 'open') {
			this.remainValueDiv.style.color = 'red';
			this.remainValueDiv.style.backgroundColor = 'aqua';
			kbdRemainStatusBackgroundColor = 'red';
			kbdRemainStatusTextColor = 'white';
			this.remainValueDiv.innerHTML = detail + 'A rendre:'
					+ (-sale.remainValue);
			this.jQueryRemainValueDiv.on('click', function(e) {
				commandSale.addPayment('cash');
				commandSale.endSale()
			});
		} else {
			this.remainValueDiv.style.color = 'grey';
			kbdRemainStatusBackgroundColor = 'grey';
			kbdRemainStatusTextColor = 'white';
			if (sale.status == 'open') {
				this.remainValueDiv.innerHTML = detail + '-:'
						+ sale.remainValue;
			} else {
				this.remainValueDiv.innerHTML = '---';
			}
		}

		for (var index = 0; index < kbdRemainStatusButton.length; index++) {
			var kbdRemainStatusItem = kbdRemainStatusButton[index];
			kbdRemainStatusItem.style.background = kbdRemainStatusBackgroundColor;
			kbdRemainStatusItem.style.color = kbdRemainStatusTextColor;
		}

		if (guiConfig.currentSpanStatus) {
			if (currentSession && currentSession.sale) {
				if (currentSession.sale.status == "open") {
					guiConfig.currentSpanStatus.innerHTML = 'vente en cours';
					guiConfig.currentSpanStatus.style.backgroundColor = 'green';
					guiConfig.currentSpanStatus.style.color = 'white';
				} else if (currentSession.sale.status == "done") {
					guiConfig.currentSpanStatus.innerHTML = 'vente terminée';
					guiConfig.currentSpanStatus.style.backgroundColor = 'white';
					guiConfig.currentSpanStatus.style.color = 'black';
				} else if (currentSession.sale.status == "cancel") {
					guiConfig.currentSpanStatus.innerHTML = 'vente annulée';
					guiConfig.currentSpanStatus.style.backgroundColor = 'red';
					guiConfig.currentSpanStatus.style.color = 'white';
				} else {
					guiConfig.currentSpanStatus.innerHTML = "-";
					guiConfig.currentSpanStatus.style.backgroundColor = 'white';
					guiConfig.currentSpanStatus.style.color = 'white';
				}
			} else {
				guiConfig.currentSpanStatus.innerHTML = "-";
			}
		}
	}

	this.getInnerHTML = function() {
		if (this.remainValueDiv.innerHTML === this.noSale) {
			return "";
		} else {
			return this.remainValueDiv.innerHTML;
		}
	}

}

// Calculator
function setCalculator() {

	$('.kbd_digit').on('click', function(event) {
		calculator.concatenateDigit(event.target.value);
	});

	$(".kbd_operator").on("click", function(event) {
		calculator.operatorDigit(event.target.value);
	});

	$("#kbd_period").on("click", function(event) {
		calculator.periodDigit();
	});

	$("#kbd_equal").on("click", function(event) {
		calculator.equalDigit();
	});

	$("#kbd_delete").on("click", function(event) {
		calculator.deleteDigit();
	});

}

// Sale engine
function setSaleEngine() {

	$('.kbd_digit').on('click', function(event) {
		saleEngine.concatenateDigit(event.target.value);
	});

	$(".kbd_operator").on("click", function(event) {
		saleEngine.operatorDigit(event.target.value);
	});

	$("#kbd_period").on("click", function(event) {
		saleEngine.periodDigit();
	});

	$("#kbd_delete").on("click", function(event) {
		saleEngine.deleteDigit();
	});

	$("#kbd_price").on("click", function(event) {
		saleEngine.priceOperator();
	});

}

function requestFullScreen(elem) {

	var requestMethod = elem.requestFullScreen || elem.webkitRequestFullScreen
			|| elem.mozRequestFullScreen || elem.msRequestFullScreen;
	if (requestMethod) {
		requestMethod.call(elem);
	} else if (typeof window.ActiveXObject !== undefined) {
		var wscript = new ActiveXObject("WScript.Shell");
		if (wscript !== null) {
			wscript.SendKeys("{F11}");
		}
	}
}

function toggleFullScreen(elem) {
	
	if ((document.fullScreenElement !== undefined && document.fullScreenElement === null)
			|| (document.msFullscreenElement !== undefined && document.msFullscreenElement === null)
			|| (document.mozFullScreen !== undefined && !document.mozFullScreen)
			|| (document.webkitIsFullScreen !== undefined && !document.webkitIsFullScreen)) {
		if (elem.requestFullScreen) {
			elem.requestFullScreen();
		} else if (elem.mozRequestFullScreen) {
			elem.mozRequestFullScreen();
		} else if (elem.webkitRequestFullScreen) {
			elem.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
		} else if (elem.msRequestFullscreen) {
			elem.msRequestFullscreen();
		}
	} else {
		if (document.cancelFullScreen) {
			document.cancelFullScreen();
		} else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if (document.webkitCancelFullScreen) {
			document.webkitCancelFullScreen();
		} else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		}
	}

}

function modalValueEngine(htmlInputValue) {

	this.value;
	this.operation;
	this.item;

	this.setValue = function(value) {
		this.value = value;
		this.htmlUpdate();
	}

	this.inc = function() {
		this.value += 1;
		this.htmlUpdate();
	}

	this.dec = function() {
		this.value -= 1;
		this.htmlUpdate();
	}

	this.htmlUpdate = function() {
		this.value = roundValue(this.value);
		if (Product.prototype.isPrototypeOf(this.operation)) {
			if (this.value < 0) {
				this.value = 0;
			}
		}
		htmlInputValue[0].value = this.value;
	}
}

function showModal(item) {

	if (!commandSale.isOpen()) {
		alert("La vente est cloturée.");
		return;
	}

	var header = '<h2 class="modal-title">' + item.operation.ticketLabel + '</h2>';
	if (item.deleted) {
		header = '<h2 class="modal-title">' + item.operation.ticketLabel + '<red>(deleted)</red></h2>';
	}
	
	$("#item-edit-header").html(header);

	ticket.modalValueEngineQty.value = item.quantity;
	ticket.modalValueEngineQty.operation = item.operation;
	ticket.modalValueEngineQty.item = item;
	ticket.modalValueEngineQty.htmlUpdate();

	ticket.modalValueEngineValue.value = item.unitPrice;
	ticket.modalValueEngineValue.operation = item.operation;
	ticket.modalValueEngineValue.item = item;
	ticket.modalValueEngineValue.htmlUpdate();

	if (item.deleted) {
		itemEditBodyDeleteModal.hide();		
		itemEditBodyUndeleteModal.show();		
	} else {
		itemEditBodyDeleteModal.show();		
		itemEditBodyUndeleteModal.hide();		
	}
	itemEdit.modal("show");
	
}
