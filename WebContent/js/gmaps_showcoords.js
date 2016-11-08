var map=null;
var marker = null;

//Outils pour Google Maps
function initMap() {


	// Ajoute logement map
	map = new google.maps.Map(document.getElementById('mapLittle'), {
		zoom: 11,
		center: {lat: 48.856, lng: 2.3522}

	});
	
	marker = new google.maps.Marker({
		position: {lat: 48.856, lng: 2.3522},
		map: map
	});

}

function placeMarkerAndPanTo(lat , lng) {
	var myLatlng = new google.maps.LatLng(lat,lng);
	if( marker != null)
		marker.setPosition(latLng);

	if(map != null)
		map.panTo(latLng);
}

