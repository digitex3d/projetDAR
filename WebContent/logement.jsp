<!DOCTYPE html>

<html lang="fr-FR">

<head>
<title>projetDAR</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="main.css">
<link rel="stylesheet" type="text/css" href="annonce.css">
<link rel="stylesheet" type="text/css" href="loading.css">
</head>
<body>
	<!-- TOP MENU -->
	<div class="header">
		<span id="disconnect"><a href="javascript:  deconnect();">Log
				out</a></span>

	</div>
	<!-- ##################### FIN TOP MENU ##################### -->

	<!-- ##################### MAIN SECTION ##################### -->
	<div id="main">

		<!--##################### CONTENU PRINCIPAL ##################### -->
		<div class="main_content">
		
					<!-- ##################### TIMELINE ##################### -->
						<div id="timeline" class="panel">
							<h1>Logements</h1>
							<div class="onlyFriends">
								<label for="onlyFriends">Seulement amis</label> <input
									type="checkbox" onclick='only_friends(this);' value="None"
										id="onlyFriends_chk" name="check" />
							</div>
							<div id="comment_box">
								<%	String logement = request.getParameter("logid");
						if( logement != null ){
							out.println(logement);
							
						}
					%>
							
							</div>
						</div>
					<!--  #################### END TIMELINE ################### -->
			
	</div>
	<!-- CONTENU PRINCIPAL -->
	<!-- ##################### END MAIN SECTION ##################### -->
	
	<!-- FOOTER -->
	<div id="footer">Copyright © Giuseppe Federico</div>

	<script type="text/javascript" src="jquery-1.11.0.js" charset="UTF-8"></script>
	<script type="text/javascript" src="search.js" charset="UTF-8"></script>
	<script type="text/javascript" src="comments.js" charset="UTF-8"></script>
	<script type="text/javascript" src="friends.js" charset="UTF-8"></script>
	<script type="text/javascript" src="errors.js" charset="UTF-8"></script>
	<script type="text/javascript" src="likes.js" charset="UTF-8"></script>
	<script type="text/javascript" src="main.js" charset="UTF-8"></script>


</body>
</html>