// ########################### SEARCH ######################################### 

// Cette fonction demande au serveur la liste des commentaires
function search (query) {
	$("#rechercheInput").val("");
	$("#circularG").css("visibility","visible");
	var friendsOnly = ((environnement.isOnline) ? environnement.friendsOnly : "0");
	
	if (query == undefined) query="";
	$.ajax({
		data:"query="+query,      
		type: "GET",
		url: server_path+"search",
		dataType: "json",
		success: Comment.traiterResponseJSON,
		error: traiteReponseErreur
		
	});

}

