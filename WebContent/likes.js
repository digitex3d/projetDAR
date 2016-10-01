/**
 * ################################# LIKES ##############################################
 */

//Fonction qui initialise les likes
function likes(){
	var user=environnement.actif;
	if( user != undefined){
		  $.ajax({
	          type: "GET",	  
	          data: 	"mode=1"+
	       	   			"&userId=none"+
	          			"&commentId=none"+
	          			"&key="+environnement.key,
	          url: server_path + "like",
	          dataType: "json",
	          scriptCharset: "utf-8" ,
	          success: LikesList.traiterResponseJSON ,
	          error: traiteReponseErreur
	          
	    });
		  
	}
	
}

/*
 * Fonction qui fait une requête pour ajouter un like
 */
function like(comment_id){
	var user=environnement.actif;
	if( user != undefined){
	    $.ajax({
	           type: "GET",	  
	           data: 	"mode=0"+
	        	   		"&userId="+ user.id+ 
	           			"&commentId="+comment_id+
	           			"&key="+environnement.key,
	           url: server_path + "like",
	           dataType: "json",
	           scriptCharset: "utf-8" ,
	           success: traiteReponseAddLike,
	           error: traiteReponseErreur
	           
	     });
	    
	}
}

// Ajoute un like
function traiteReponseAddLike(data){
	   if (data.error != undefined) {
	    	 fonc_erreur(null , data.error);
	    }
	    else {
	    	likes();
	    	
	    }
	   
}

// ######################### LIKES LIST #############################
/*
 * Class qui contient une liste de likes
 */
function LikesList(){
	this.likes = [];
	this.size = 0;
	
}

// Ajoute un obj commentairede la friendLIST
LikesList.prototype.addLikes = function(likes, comment_id){
	this.likes[comment_id] = likes;
	this.size++;
	
};

//Ajoute un obj commentaire de la friendLIST
LikesList.prototype.flush = function(){
	this.likes = [];
	this.size = 0;
	
};

//Traite la reponse d'une list query
LikesList.traiterResponseJSON = function(data){
	environnement.likesList.flush();
	
	for( var i in data ){
		var comment = data[i];
		environnement.likesList.addLikes(comment.likes, comment.id);
		
	}
	
	search();
};

//######################### LIKES LIST END #############################

// fonction qui met à jour le nombre des likes
function  updateLikes(){
	var user=environnement.actif;
	var likes = environnement.likesList.likes;

	if(user != undefined &&
			environnement.timeline.comments.size != 0){
		for (var comment_id in likes) {
			var like_ids = likes[comment_id];
			var nb = like_ids.length;
			$("#likes_number_"+ comment_id).html(nb);

		}
	}
}

// Retourne true si liked false sinon
function isLiked(comment_id){
	var user = environnement.actif;
	var likes = environnement.likesList.likes;

	if (user != undefined){
			var like_ids = likes[comment_id];
			for(var i in like_ids ){
				if(user.id == like_ids[i]){
					return true;
				}

			}
		
	}
	return false;

}
