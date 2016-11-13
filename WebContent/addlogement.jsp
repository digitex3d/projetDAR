<!DOCTYPE html>

<html lang="fr-FR">

<head>
<title>Ajouter un logement</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="main.css">
<link rel="stylesheet" type="text/css" href="loading.css">
<link rel="stylesheet" type="text/css" href="CSS/ajouterlogement.css">
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

							<div class="form-style-10">
								<h1>
									Ajouter un logement<span>Vouillez insérer les
										informations du logement</span>
								</h1>
								<form onsubmit="validateAddForm(this)" action="javascript:"
									id="insertCmtForm" enctype="multipart/form-data">
									<div class="section">
										<span>1</span>Informations
									</div>
									<div class="inner-wrap obligatoire">
										<label id="oblig">Titre de l'annonce <input
											placeholder="Titre..." name="commentInput" id="cmt"
											class="textInput" />
										</label> <label>Adresse <input placeholder="Adresse..."
											name="adressInput" id="address" class="textInput" /></label>
										<div id='map'></div>

									</div>

									<div class="section">
										<span>2</span>Détails 
									</div>
									<div class="inner-wrap" >
										<label id="oblig">Prix du logement<input placeholder="Prix..."
											name="priceInput" id="price" class="textInput" /></label> <label>Dimension
											du logement en m2 <input placeholder="Dimension(m²)..."
											name="dimInput" id="dim" class="textInput" />
										</label>
									</div>

									<div class="section">
										<span>3</span>Photos
									</div>
									<div class="inner-wrap">
										<label>Photos du logement <input type="file"
											name="image" id="imageSelect" size="50" multiple />
										</label> 
									</div>

									<div class="section">
										<span>3</span>Déscription
									</div>
									<div class="inner-wrap">
										<label id="oblig">Une déscription du logement <input
											placeholder="Description..." name="descInput" id="desc"
											class="textArea" />
										</label>
									</div>
									<div class="section">
										<span>3</span>URL de référence ( Optionel )
									</div>
									<div class="inner-wrap">
										<label>Si vous faites référence à une autre annonce <input
											placeholder="URL de reférence..." name="refpageInput"
											id="ref" class="textInput" />
										</label>
									</div>

									<div class="button-section">
										<input type="submit" name="Ajoute" /> 
									</div>
								</form>
							</div>
						</div>
					</td>
					<td></td>
				<tr>
			</table>
		
			

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
	<%@ include file="fragments/mainScripts.jspf"%>

</body>
</html>