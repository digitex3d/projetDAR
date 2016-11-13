/**
 * ################################# COLOCS ##############################################
 */

//Fonction qui initialise les colocs
function colocs(){
	var user=environnement.actif;
	if( user != undefined){
		  $.ajax({
	          type: "GET",	  
	          data: 	"mode=1"+
	       	   			"&userId=none"+
	          			"&commentId=none"+
	          			"&key="+environnement.key,
	          url: server_path + "coloc",
	          dataType: "json",
	          scriptCharset: "utf-8" ,
	          success: ColocsList.traiterResponseJSON ,
	          error: traiteReponseErreur
	          
	    });
		  
	}
	
}

/*
 * Fonction qui fait une requête pour ajouter une coloc
 */
function coloc(comment_id){
	var user=environnement.actif;
	if( user != undefined){
	    $.ajax({
	           type: "GET",	  
	           data: 	"mode=0"+
	        	   		"&userId="+ user.id+ 
	           			"&commentId="+comment_id+
	           			"&key="+environnement.key,
	           url: server_path + "coloc",
	           dataType: "json",
	           scriptCharset: "utf-8" ,
	           success: traiteReponseAddColoc,
	           error: traiteReponseErreur
	           
	     });
	    
	}
}

// Ajoute une coloc
function traiteReponseAddColoc(data){
	   if (data.error != undefined) {
	    	 fonc_erreur(null , data.error);
	    }
	    else {
	    	colocs();
	    	
	    }
	   
}

// ######################### Colocation LIST #############################
/*
 * Class qui contient une liste des colocs
 */
function ColocsList(){
	this.colocs = [];
	this.size = 0;
	
}

// Ajoute un obj commentairede la friendLIST
ColocsList.prototype.addColocs = function(colocs, comment_id){
	this.colocs[comment_id] = colocs;
	this.size++;
	
};

//Ajoute un obj commentaire de la friendLIST
ColocsList.prototype.flush = function(){
	this.colocs = [];
	this.size = 0;
	
};

//Traite la reponse d'une list query
ColocsList.traiterResponseJSON = function(data){
	environnement.colocsList.flush();
	
	for( var i in data ){
		var comment = data[i];
		environnement.colocsList.addColocs(comment.colocs, comment.id);
		
	}
	
	search();
};

//######################### COLOCS LIST END #############################

// fonction qui met à jour le nombre des colocs
function  updateColocs(){
	var user=environnement.actif;
	var users = environnement.users;
	var colocs = environnement.colocsList.colocs;
	var colocNamesHTML = "";
	
	if(user != undefined &&
			environnement.timeline.comments.size != 0){
		for (var comment_id in colocs) {
			colocNamesHTML = "";
			var coloc_ids =colocs[comment_id];
			for ( var user_id in coloc_ids ){
				var cuser = users[coloc_ids[user_id]];
				if( cuser )
					colocNamesHTML += "<p>" + cuser.getHtml() + "</p>"; 
				
			}
				var nb = coloc_ids.length;
			$("#coloc_names_"+ comment_id).html(colocNamesHTML);

		}
	}
}

// Retourne true si colocd false sinon
function isColoc(comment_id){
	var user = environnement.actif;
	var colocs = environnement.colocsList.colocs;

	if (user != undefined){
			var coloc_ids = colocs[comment_id];
			for(var i in coloc_ids ){
				if(user.id == coloc_ids[i]){
					return true;
				}

			}
		
	}
	return false;

}

