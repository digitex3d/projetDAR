<!DOCTYPE html>

<html lang="fr-FR">

<head>
<title>projetDAR</title>
 <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="main.css"> 
<link rel="stylesheet" type="text/css" href="loading.css"> 
</head>
<body>
	<!-- TOP MENU -->
	<div class="header">
		<span id="disconnect"><a href="javascript:  deconnect();">Log
				out</a></span>
		<div id="search_box">
			<form onsubmit="search(this.rechercheInput.value)"
				action="javascript:" id="searchForm">
				<input placeholder="Recherche" name="rechercheInput"
					id="rechercheInput" class="textInput" />
				<div id="circularG">
					<div id="circularG_1" class="circularG">
					</div>
					<div id="circularG_2" class="circularG">
					</div>
					<div id="circularG_3" class="circularG">
					</div>
					<div id="circularG_4" class="circularG">
					</div>
					<div id="circularG_5" class="circularG">
					</div>
					<div id="circularG_6" class="circularG">
					</div>
					<div id="circularG_7" class="circularG">
					</div>
					<div id="circularG_8" class="circularG">
					</div>
				</div>
			</form>
			
		</div>
	</div>
	<!-- ##################### FIN TOP MENU ##################### -->

	<div id="main">

		
		<!-- ##################### REGISTRATION / CONNEXION  ##################### -->
		<div class="offline_panels">
			<table>
				<tr>
				
					<td><div id="login_panel" class="panel">
							<h1>Ouvrir Session</h1>
							<form action="" method="get" id="login_form">
								<div class="ids">
									<input type="text" id="login" name="login"
										placeholder="Username" class="textInput" />
								</div>
								<div class="ids">
									<input type="password" id="pass" name="pass"
										placeholder="Mot de passe" class="textInput" />
								</div>
								<div class="buttons">
									<a href="javascript: validateForm();">Connexion</a> <a
										href="javascript: window.location.href='main.jsp';">Annuler</a>
								</div>
							</form>
							<a href="javascript: show_register();">On ne se connait pas?</a>
						</div></td>
					<td>
						<div id="register_panel" class="panel">
							<h1>Enregistrement</h1>
							<form action="" method="get" name="register_form">
								<div class="ids_reg">
									<input type="text" id="login_register" name="login"
										placeholder="Username" class="textInput" />
								</div>
								<div class="ids_reg">
									<input type="password" id="pass_register" name="pass"
										placeholder="Mot de passe" class="textInput" />
								</div>
								<div class="ids_reg">
									<input type="password" id="repass_register" name="pass"
										placeholder="Retapez mot de passe" class="textInput" />
								</div>
								<div class="buttons">
									<a href="javascript: reg_validateForm();">Enregistrer</a> <a
										href="javascript: window.location.href='main.jsp';">Annuler</a>
								</div>
							</form>

						</div>
					</td>
				</tr>
			</table>
		</div>
		<!--##################### FIN REGISTRATION / CONNEXION  #####################-->
		
		<!--##################### CONTENU PRINCIPAL ##################### -->
		<div class="error_boxs"></div>
		<div class="main_content">
			<table>
				<tr>
					<td>
						<div id="left_panel">
						<!-- ##################### DASHBOARD ##################### -->
							<div id="dashboard" class="panel">
								<h1>Jecoloc</h1>
								<div class="profile">
									<div id="fullname"></div>
									<div id="comment_insert_box">
										<span>Ajouter un logement</span>
										<form onsubmit="insertCmt(this)" action="javascript:"
											id="insertCmtForm"   enctype="multipart/form-data">
											<input placeholder="Titre..."
												name="commentInput" id="cmt" class="textInput" />
											<input placeholder="Prix..."
												name="priceInput" id="price" class="textInput" />
											<input placeholder="Description..."
												name="descInput" id="desc" class="textInput" />
											<input placeholder="Dimension (m�)..."
												name="dimInput" id="dim" class="textInput" />
											<input type="file" 
												name="image" id="imageSelect" size="50" multiple/>
											<input placeholder="Adresse..."
												name="adresseInput" id="adresse" class="textInput" />
											<input id="submit" type="submit" value="Add">
											<div id="map"></div>
										</form>
								
									</div>
								</div>
								
									
							</div>
							<div id="friends_box" class="panel">
								<h1>Amis</h1>
								<div id="nb_amis"></div>
								<div id="friends_list"></div>
							</div>
						
  
						</div>
					</td>
					<td>
					<!-- ##################### TIMELINE ##################### -->
						<div id="timeline" class="panel">
							<h1>Logements</h1>
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
	<div id="footer">Copyright � Giuseppe Federico</div>

	<script type="text/javascript" src="jquery-1.11.0.js" charset="UTF-8"></script>
	<script type="text/javascript" src="search.js" charset="UTF-8"></script>
	<script type="text/javascript" src="comments.js" charset="UTF-8"></script>
	<script type="text/javascript" src="friends.js" charset="UTF-8"></script>
	<script type="text/javascript" src="errors.js" charset="UTF-8"></script>
	<script type="text/javascript" src="likes.js" charset="UTF-8"></script>
	<script type="text/javascript" src="main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="uploadImage.js" charset="UTF-8"></script>
	<!-- Google maps outils -->
	
	<script type="text/javascript" src="gmaps.js" charset="UTF-8"></script>
	<!-- Google maps API -->
	<script async defer 
	src="https://maps.googleapis.com/maps/api/js?
			key=AIzaSyCdRvJbx0egU7JQuyBJKou26YIqKwki4c4&callback=initMap">
	</script>
	
	<!-- This script contains tools that helps with the generation of HTML code  -->
	<script type="text/javascript" src="HTMLTools.js" charset="UTF-8"></script>

	
	<!-- main script -->
	<script type="text/javascript">
	<% String filepath= application.getContextPath();
		out.println("var imagepath=\""+filepath+"\";");%>
		$(document)
				.ready(
			
				
						function() {
	<%String id = request.getParameter("id");
			String login = request.getParameter("login");
			String key = request.getParameter("key");
			
			if ((id != null) && (login != null) && (key != null)) {
				out.println("main('" + id + "','" + login + "','" + key + "');");

			} else {
				out.println("main();");

			}%>
			
			// Add an event handler ont the file input change.
			$("#imageSelect").change(uploadImage);

		});
		
		
	</script>

</body>
</html>