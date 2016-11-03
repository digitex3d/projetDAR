
var marker = null;

// Outils pour Google Maps
function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 11,
		center: {lat: 48.856, lng: 2.3522}
	
	});
	var geocoder = new google.maps.Geocoder();
	marker = new google.maps.Marker({
		position: {lat: 48.856, lng: 2.3522},
		map: map
	});


	$( "#address" ).change(function() {
		geocodeAddress(geocoder, map);
		});
	
	// When the map is clicked


    
    map.addListener('click', function(e) {
        placeMarkerAndPanTo(e.latLng, map);
      });
    }

function placeMarkerAndPanTo(latLng, map) {

		marker.setPosition(latLng);

	
	map.panTo(latLng);
}

	


function geocodeAddress(geocoder, resultsMap) {
	var address = $( "#address" ).val();
	geocoder.geocode({'address': address}, function(results, status) {
		if (status === 'OK') {
			resultsMap.setCenter(results[0].geometry.location);

			marker.setPosition(results[0].geometry.location);

		} else {
			alert('Geocode was not successful for the following reason: ' + status);
		}
	});
}
