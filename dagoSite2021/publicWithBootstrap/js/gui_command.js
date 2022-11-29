$(document).ready(

	function() {

		var guiDao = new GuiDao();

		var productRowDivs = $('.product-row');

		for (var i = 0; i < productRowDivs.length; i++) {

			var productRowDiv = productRowDivs[i];

			var product = guiDao.findById(productRowDiv.id.toUpperCase());

			productRowDiv.className = 'row product-row';

			if (product) {

				// add label
				var productColLabel = document.createElement('div');
				productRowDiv.appendChild(productColLabel);
				productColLabel.className = 'col';
				productColLabel.innerHTML = product.name;

				// add logo
				if (product.image && 'null' != product.image) {
					var productColImg = document.createElement('div');
					productRowDiv.appendChild(productColImg);
					productColImg.className = 'col-2 imageProduct';
					productColImg.innerHTML = '<img src="images/' + product.image
						+ '.png" />'
				}

				// add normal price
				var productColNormalPrice = document.createElement('div');
				productRowDiv.appendChild(productColNormalPrice);
				productColNormalPrice.className = 'col-2';
				productColNormalPrice.innerHTML = product.normal.toFixed(2);

				// add geant price
				if (product.geant && 'null' != product.geant) {
					var productColGeantPrice = document.createElement('div');
					productRowDiv.appendChild(productColGeantPrice);
					productColGeantPrice.className = 'col-1 geantPrice';
					productColGeantPrice.innerHTML = product.geant.toFixed(2);
				}

				// add web detail
				if (product.webDetail && 'null' != product.webDetail) {
					var webdetailRow = document.createElement('div');
					productRowDiv.appendChild(webdetailRow);
					webdetailRow.className = 'webDetail';
					webdetailRow.innerHTML = product.webDetail;
				}


			} else {

				productRowDiv.innerHTML = productRowDiv.id.toUpperCase();

			}
		}

		var productHeaderDivs = $('.sdwHeader');

		for (var i = 0; i < productHeaderDivs.length; i++) {

			var productRowDiv = productHeaderDivs[i];

			productRowDiv.className = 'row product-header';

			// add label
			var productColLabel = document.createElement('div');
			productRowDiv.appendChild(productColLabel);
			productColLabel.className = 'col';
			/*			productColLabel.innerHTML = product.name;
			*/


			// add normal price
			var productColNormalPrice = document.createElement('div');
			productRowDiv.appendChild(productColNormalPrice);
			productColNormalPrice.className = 'col-2 normalHeader';
			productColNormalPrice.innerHTML = 'normal';

			// add geant price
			var productColGeantPrice = document.createElement('div');
			productRowDiv.appendChild(productColGeantPrice);
			productColGeantPrice.className = 'col-1 geantHeader';
			productColGeantPrice.innerHTML = 'géant';

		}

		var navMain = $(".navbar-collapse");

		navMain.on("click", "a", null, function() {
			navMain.collapse('hide');
		});
	});

function scrolldivbyparam() {


	const params = new URLSearchParams(window.location.search);

	const paramlinkparam = params.get('pageLink');

	var paramlinkelem = document.getElementById(paramlinkparam);

	if (paramlinkelem) { paramlinkelem.scrollIntoView(); }

};