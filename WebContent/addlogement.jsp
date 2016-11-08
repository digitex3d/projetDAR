<!DOCTYPE html>

<html lang="fr-FR">

<head>
<title>Ajouter un logement</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="main.css">
<link rel="stylesheet" type="text/css" href="loading.css">
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
							<h1>Ajouter un logement</h1>
							<table>
								<tr>
									<td>
										<div id="comment_insert_box">

											<form onsubmit="insertCmt(this)" action="javascript:"
												id="insertCmtForm" enctype="multipart/form-data">
												<input placeholder="Titre..." name="commentInput" id="cmt"
													class="textInput" /> <input placeholder="Prix..."
													name="priceInput" id="price" class="textInput" /> <input
													placeholder="Description..." name="descInput" id="desc"
													class="textInput" /> <input placeholder="Dimension(m²)..."
													name="dimInput" id="dim" class="textInput" /> <input
													type="file" name="image" id="imageSelect" size="50"
													multiple /> <input placeholder="Adresse..."
													name="adressInput" id="address" class="textInput" /> <input
													type="submit" value="Add">
											</form>

										</div>
									</td>
									<td>
										<div id='map'></div>
									</td>
									<tr>

							</table>
							<div class="onlyFriends">
										<label for="onlyFriends">Seulement amis</label> <input
											type="checkbox" onclick='only_friends(this);' value="None"
											id="onlyFriends_chk" name="check" />
							</div>
							<div id="comment_box"></div>
						
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
		<script async defer
								src="https://maps.googleapis.com/maps/api/js?
			key=AIzaSyCdRvJbx0egU7JQuyBJKou26YIqKwki4c4&callback=initMap&libraries=places">
	
</script>
	<script type="text/javascript" src="uploadImage.js" charset="UTF-8"></script>
	<!-- Google maps outils -->
	<!-- Google maps API -->



	<!-- This script contains tools that helps with the generation of HTML code  -->
	<script type="text/javascript" src="HTMLTools.js" charset="UTF-8"></script>
	<script type="text/javascript" src="js/ajouterLogement.js"
		charset="UTF-8"></script>

	<!-- main script -->
	<script type="text/javascript">
		
	<%String filepath = application.getContextPath();
			out.println("var imagepath=\"" + filepath + "\";");%>
		$(document)
				.ready(

						function() {
	<%Cookie[] cookies = request.getCookies();

			String sessionKey = null;

			// Parsing des cookies pour vérifier la session
			if (cookies != null)
				for (Cookie cookie : cookies)
					if (cookie.getName().equals("session_key"))
						sessionKey = cookie.getValue();

			String id = (String) session.getAttribute("id");
			String login = (String) session.getAttribute("login");

			if (sessionKey != null) {
				out.println("main('" + id + "','" + login + "','" + sessionKey + "');");
				//out.println("main('" + sessionKey + "');");

			} else {
				out.println("main();");

			}%>
			
			// Add an event handler ont the file input change.
			$("#imageSelect").change(uploadImage);

		});
		
		
	</script>

</body>
</html>