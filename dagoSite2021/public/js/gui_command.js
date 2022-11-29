$(document).ready(

	function() {

		$('.vk-slidedoor').vkSlidedoor({
			autoplay: true,
			direction: 'ltr',
			looptimeout: 6000
		});

		var guiDao = new GuiDao();

		var tableRows = $('tr.product');

		for (var i = 0; i < tableRows.length; i++) {

			var tableRow = tableRows[i];

			var product = guiDao.findById(tableRow.id.toUpperCase());

			if (product) {

				console.log(product.name + "/" + product.image + "/"
					+ product.geant);

				// add logo
				var cell = tableRow.insertCell();
				if (product.image && 'null' != product.image) {
					cell.className = "imageProduct";
					cell.innerHTML = '<img src="images/' + product.image
						+ '.png" />'
				}

				// add label
				var cell = tableRow.insertCell();
				if (product.webDetail && 'null' != product.webDetail) {
					cell.innerHTML = product.name + '<p style="color:black;">' + product.webDetail + '</p>';
				} else { cell.innerHTML = product.name }

				// add normal price
				var cell = tableRow.insertCell();
				cell.className = "normalCharPrice";
				cell.innerHTML = (product.normal).toFixed(2);

				// add geant price
				if (product.geant && 'null' != product.geant) {
					var cell = tableRow.insertCell();
					cell.className = "smallCharPrice";
					cell.innerHTML = (product.geant).toFixed(2);
				}
				/*				if (product.webDetail && 'null' != product.webDetail) {
									console.log(tableRow.rowIndex);
									console.log(tableRow.parentNode.nodeName);
									console.log(tableRow.parentNode.parentNode.nodeName);
				
									var rowIndex = tableRow.rowIndex;
									var table = tableRow.parentNode.parentNode;
									var detailTableRow = table.insertRow(rowIndex + 1);
				
									var cell = detailTableRow.insertCell();
									cell.className = "description";
									cell.colSpan = "4";
									cell.innerHTML = product.webDetail;
				
								}
				*/
			} else {

				// add logo
				tableRow.insertCell();

				// add label
				var cell = tableRow.insertCell();
				cell.innerHTML = tableRow.id.toUpperCase();

			}

		}

	});

function getParameter(sParam) {

	var searchString = window.location.search.substring(1), params = searchString
		.split("&"), hash = {};

	if (searchString == "")
		return 0;
	for (var i = 0; i < params.length; i++) {
		var val = params[i].split("=");
		hash[unescape(val[0])] = unescape(val[1]);

		if (unescape(val[0]) == sParam) {
			return unescape(val[1])
		}
		;

	}

	return 0;

}
