<!DOCTYPE html>

<html lang="fr-FR">

<head>
<title>Annonce</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="main.css">
<link rel="stylesheet" type="text/css" href="loading.css">
<link rel="stylesheet" type="text/css" href="CSS/annonce.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="search.js" charset="UTF-8"></script>
<script type="text/javascript" src="comments.js" charset="UTF-8"></script>
<script type="text/javascript" src="friends.js" charset="UTF-8"></script>
<script type="text/javascript" src="errors.js" charset="UTF-8"></script>
<script type="text/javascript" src="colocs.js" charset="UTF-8"></script>
<script type="text/javascript" src="main.js" charset="UTF-8"></script>


<script type="text/javascript" src="uploadImage.js" charset="UTF-8"></script>
<!-- Google maps outils -->
<!-- Google maps API -->






<!-- This script contains tools that helps with the generation of HTML code  -->
<script type="text/javascript" src="HTMLTools.js" charset="UTF-8"></script>



</head>
<body>
	<!-- HEADER -->
	<%@ include file="fragments/header.jspf"%>

	<div id="main">
		<!-- ##################### REGISTRATION / CONNEXION  ##################### -->
		<%@ include file="fragments/authpanels.jspf"%>

		<!--##################### CONTENU PRINCIPAL ##################### -->
		<div class="error_boxs"></div>
		<div class="main_content">
			<table>
				<tr>
					<td>
						<!-- SIDEBAR --> <%@ include file="fragments/sidebar.jspf"%>
					</td>
					<td>
						<!-- ##################### TIMELINE ##################### -->
						<div id="timeline" class="panel">
							<div id="maptest"></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- CONTENU PRINCIPAL -->

	<!-- FOOTER -->
	<%@ include file="fragments/footer.jspf"%>

	

	<!-- main script -->
	<script type="text/javascript">
	var googleMapsInit = false;
	<% String filepath= application.getContextPath();
		out.println("var imagepath=\""+filepath+"\";");%>
	
	<%
	Cookie[] cookies = request.getCookies();
	
	String sessionKey = null;
	String id = null;
	String login = null;
	
	// Parsing des cookies pour v�rifier la session
	if(cookies !=null)
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("session_key")) 
				sessionKey = cookie.getValue();
			if(cookie.getName().equals("id")) 
				 id =cookie.getValue();
			if(cookie.getName().equals("login")) 
				login = cookie.getValue();
			
		}%>
	

	
		
		
			
			function initMapAnnonce(annce){
				// Map position
				var latlng = new google.maps.LatLng(annce.lat, annce.lng);
				
				// Ajoute logement map
				var map = new google.maps.Map(document.getElementById("map"), {
						zoom: 15,
						center: latlng

							});
							
						
				// Ajoute StreetView		
				var panorama = new google.maps.StreetViewPanorama(
						document.getElementById("pano"), {
							              position: latlng,
							              pov: {
							                heading: 34,
							                pitch: 10
							              }
							            });
							        map.setStreetView(panorama);
							        
				// Ajoute MAP RATP		
				var mapRATP =  new google.maps.Map(document.getElementById("mapRATP"), {
					zoom: 15,
					center: latlng

						});
							        
			
				
				// Marker position logement
				var marker = new google.maps.Marker({
					position: latlng,
					map: mapRATP
				});
				
				// Initialiser les markers des station metro
				var stations = annce.RATPStations.records;
				var stationsMarker = [];
				
				for( var i in stations){
					var coords = stations[i].geometry.coordinates;
					var latlngStat = new google.maps.LatLng(coords[1], coords[0]);
					
					var currentMarker = new google.maps.Marker({
						position: latlngStat,
						map: mapRATP
					});
					
					currentMarker.setIcon(/** @type {google.maps.Icon} */({
				          url: "resources/ratp.png",
				          size: new google.maps.Size(71, 71),
				          origin: new google.maps.Point(0, 0),
				          anchor: new google.maps.Point(17, 34),
				          scaledSize: new google.maps.Size(35, 35)
				        }));
					
					
					
					stationsMarker.push(currentMarker);
					
					
					
					
				}
				
				 marker.setIcon(/** @type {google.maps.Icon} */({
			          url: "http://downloadicons.net/sites/default/files/little-house-icon-31873.png",
			          size: new google.maps.Size(71, 71),
			          origin: new google.maps.Point(0, 0),
			          anchor: new google.maps.Point(17, 34),
			          scaledSize: new google.maps.Size(35, 35)
			        }));
				

							
					

					
				}	
			
			function fillComment(){

				// Add an event handler ont the file input change.
				$("#imageSelect").change(uploadImage);
		
				
				gmapsAPIkey = "AIzaSyCdRvJbx0egU7JQuyBJKou26YIqKwki4c4";
			

				var comments = environnement.timeline.comments;
			
				for (var i in comments)
					if( comments[i].id == "<%out.write(request.getParameter("id"));%>")
						var annonce = comments[i];
				

				$("#timeline").html( annonce.getExtHtml());
					
						// Afficher les colocataires
						//TODO: changer nom
					colocs();
	    			updateColocs();
				
	    			if( !googleMapsInit){
	    			 $.getScript( "https://maps.googleapis.com/maps/api/js?key=AIzaSyCdRvJbx0egU7JQuyBJKou26YIqKwki4c4", function( ) {
	 					initMapAnnonce(annonce);		
	 				});
	    			 googleMapsInit = true;
	    			}
	 			
			

				
				
			}
			
			window.onload	=
		function() {	
			<%
			if (sessionKey != null) {
				out.println("main('" + id + "','" + login + "','" + sessionKey + "');");
				//out.println("main('" + sessionKey + "');");

			} else {
				out.println("if(main()){ }");

			}
			%>
			
			 fillComment();
		
		
			};
		

	
	
	</script>

</body>
</html>