jQuery(function($) {

	$(document).on('online', function() {
		alert("online");
	});
	$(document).on('offline', function() {
		alert("offline");
	});

});