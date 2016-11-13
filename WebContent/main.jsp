<!DOCTYPE html>

<html lang="fr-FR">

<head>
<title>Jecoloc</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="main.css">
<link rel="stylesheet" type="text/css" href="CSS/annonce.css">
<link rel="icon" 
      type="image/png" 
      href="house-icon.png">
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
							<h1>Logements</h1>
							<div class="onlyFriends">
								<label for="onlyFriends">Seulement following</label> <input
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
	<script type="text/javascript" src="colocs.js" charset="UTF-8"></script>
	<script type="text/javascript" src="main.js" charset="UTF-8"></script>
	<script type="text/javascript" src="uploadImage.js" charset="UTF-8"></script>
	
	<!-- Google maps API -->



	<!-- This script contains tools that helps with the generation of HTML code  -->
	<script type="text/javascript" src="HTMLTools.js" charset="UTF-8"></script>
	<script type="text/javascript" src="js/ajouterLogement.js"
		charset="UTF-8"></script>

	<!-- main script -->
		<%@ include file="fragments/mainScripts.jspf"%>

</body>
</html>