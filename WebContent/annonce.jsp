<!DOCTYPE html>

<html lang="fr-FR">

<head>
<title>Annonce</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="main.css">
<link rel="stylesheet" type="text/css" href="loading.css">
<link rel="stylesheet" type="text/css" href="CSS/annonce.css">
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
						
						
								</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- CONTENU PRINCIPAL -->

	<!-- FOOTER -->
	<%@ include file="fragments/footer.jspf"%>

	<script type="text/javascript" src="jquery-1.11.0.js" charset="UTF-8"></script>
	<script type="text/javascript" src="search.js" charset="UTF-8"></script>
	<script type="text/javascript" src="comments.js" charset="UTF-8"></script>
	<script type="text/javascript" src="friends.js" charset="UTF-8"></script>
	<script type="text/javascript" src="errors.js" charset="UTF-8"></script>
	<script type="text/javascript" src="likes.js" charset="UTF-8"></script>
	<script type="text/javascript" src="main.js" charset="UTF-8"></script>
	<script async defer type="text/javascript" src="gmaps.js"
		charset="UTF-8"></script>
	
	<script type="text/javascript" src="uploadImage.js" charset="UTF-8"></script>
	<!-- Google maps outils -->
	<!-- Google maps API -->
		<script async defer src='https://maps.googleapis.com/maps/api/js?"+
			"key=AIzaSyCdRvJbx0egU7JQuyBJKou26YIqKwki4c4&callback=initMap&libraries=places'>"
	
</script>


"<script type='text/javascript' src='js/gmaps_showcoords.js' charset='UTF-8'></script>"+


	<!-- This script contains tools that helps with the generation of HTML code  -->
	<script type="text/javascript" src="HTMLTools.js" charset="UTF-8"></script>

	

	<!-- main script -->
		<script type="text/javascript">
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
			
		}
	

			if (sessionKey != null) {
				out.println("main('" + id + "','" + login + "','" + sessionKey + "');");
				//out.println("main('" + sessionKey + "');");

			} else {
				out.println("main();");

			}%>
			
			// Add an event handler ont the file input change.
			$("#imageSelect").change(uploadImage);
		
			
			$(document)
			.ready(
		
			
					function() {	extItem("<%out.write(request.getParameter("id"));%>");	

										});
		
		
	</script>
		
</body>
</html>