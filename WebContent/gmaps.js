
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


	 var infowindow = new google.maps.InfoWindow();

	// Autocomplete
	var autocomplete = new google.maps.places.Autocomplete(address);
    autocomplete.bindTo('bounds', map);

    autocomplete.addListener('place_changed', function() {
        infowindow.close();
        marker.setVisible(false);
        var place = autocomplete.getPlace();
        if (!place.geometry) {
          // User entered the name of a Place that was not suggested and
          // pressed the Enter key, or the Place Details request failed.
          window.alert("No details available for input: '" + place.name + "'");
          return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
          map.fitBounds(place.geometry.viewport);
        } else {
          map.setCenter(place.geometry.location);
          map.setZoom(17); 
        }
        marker.setIcon(/** @type {google.maps.Icon} */({
          url: place.icon,
          size: new google.maps.Size(71, 71),
          origin: new google.maps.Point(0, 0),
          anchor: new google.maps.Point(17, 34),
          scaledSize: new google.maps.Size(35, 35)
        }));
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);

        var address = '';
        if (place.address_components) {
          address = [
            (place.address_components[0] && place.address_components[0].short_name || ''),
            (place.address_components[1] && place.address_components[1].short_name || ''),
            (place.address_components[2] && place.address_components[2].short_name || '')
          ].join(' ');
        }

        infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
        infowindow.open(map, marker);
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

