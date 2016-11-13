/**
 * ################################# FRIENDS ##############################################
 */

// Fonction qui intialise les amis
function friends(){
	var user=environnement.actif;
	if( user != undefined){
		  $.ajax({
	          type: "GET",	  
	          data: 	"mode=1"+
	       	   			"&userId="+ user.id+ 
	          			"&friendId=none"+
	          			"&key="+environnement.key,
	          url: server_path + "friends",
	          dataType: "json",
	          scriptCharset: "utf-8" ,
	          success: FriendsList.traiterResponseJSON ,
	          error: traiteReponseErreur
	          
	    });
		  
	}
	
}

/*
 * Fonction qui fait une requête pour ajouter un ami
 */
function addFriend(id){
	var user=environnement.actif;
	if( user != undefined &&
		id != user.id ){
	    $.ajax({
	           type: "GET",	  
	           data: 	"mode=0"+
	        	   		"&userId="+ user.id+ 
	           			"&friendId="+id+
	           			"&key="+environnement.key,
	           url: server_path + "friends",
	           dataType: "json",
	           scriptCharset: "utf-8" ,
	           success: traiteReponseAddFriend,
	           error: traiteReponseErreur
	           
	     });
	    
	}
	updateColocs();
	friends();
}

/*
 * Fonction qui supprime un ami
 */
function removeFriend(id){
	var user=environnement.actif;
	if( user != undefined &&
		id != user.id ){
	    $.ajax({
	           type: "GET",	  
	           data: 	"mode=2"+
	        	   		"&userId="+ user.id+ 
	           			"&friendId="+id+
	           			"&key="+environnement.key,
	           url: server_path + "friends",
	           dataType: "json",
	           scriptCharset: "utf-8" ,
	           success: traiteReponseRemoveFriend,
	           error: traiteReponseErreur
	           
	     });
	    
	}
	updateColocs();
	friends();
}

// Renvoie true si id est un ami
function isFriend(id){
	var user = environnement.actif;
	var friends = environnement.friendsList.friends;
	
	if (environnement.friendsList.size > 0 && 
			user != undefined){
			for( i in friends){
				var friend = friends[i];
				if(friend.id == id){
					return true;
				}
				
			}
		}
	
	return false;
}

// Fonction qui affiche seulment les amis
function only_friends(cb){
	var user = environnement.actif;
	var friends = environnement.friendsList.friends;
	var comments = environnement.timeline.comments;
	
	if(cb.checked){
		if (environnement.friendsList.size > 0 && 
				user != undefined){
				for( i in comments){
					var comment =  comments[i];
					if( !isFriend(comment.author.id) ){
						$("#comment_"+ comment.id).css("display","none" );
					}
					
				}
			}
		
	}else{
		if (environnement.friendsList.size > 0 && 
				user != undefined){
				for( i in comments){
					var comment =  comments[i];
					if( !isFriend(comment.author.id) ){
						$("#comment_"+ comment.id).css("display","block" );
					}
					
				}
			}
	}
}

// Traite la reponse d'un ajout
function traiteReponseAddFriend(data){
	   if (data.error != undefined) {
	    	 fonc_erreur(null , data.error);
	    }
	    else {
	    	fonc_erreur(null , "Ami ajouté avec succès");
	    	friends();
	    }
}

//Traite la reponse d'un remove
function traiteReponseRemoveFriend(data){
	   if (data.error != undefined) {
	    	 fonc_erreur(null , data.error);
	    }
	    else {
	    	fonc_erreur(null , "Ami supprimé avec succès");
	    	friends();
	    }
}

// ######################### FRIENDS LIST #############################

function FriendsList(){
	this.friends = [];
	this.size = 0;
	
}

// Ajoute un obj commentairede la friendLIST
FriendsList.prototype.addFriend = function(friend){
	var index = this.size;	
	
	this.friends[index] = friend;
	this.size++;
	
};

//Ajoute un obj commentaire de la friendLIST
FriendsList.prototype.flush = function(){
	this.friends = [];
	this.size = 0;
	
};

// Genère le code html de la friendLIST
FriendsList.prototype.getHtml = function(){
	var resu = "";
	var user = environnement.actif;
	if(user != undefined){
		for (var i in this.friends){
			var friend = this.friends[i];
			resu += friend.getHtml();
	
		}
	}
	
	return resu;
	
};

//Traite la reponse d'un friend query
FriendsList.traiterResponseJSON = function(data){
	environnement.friendsList.flush();
	
	if (data[0] != undefined){
		for (var i in data[0].friends) {
			var friend_id =  data[0].friends[i];
			environnement.friendsList.addFriend(environnement.users[friend_id]);
		
		}
	}

	var friends = environnement.friendsList.getHtml();
	$("#friends_list").html(friends);
	$("#nb_amis").html("("+environnement.friendsList.size+")");
	
	// Handler pour le popout
	for (var i in environnement.friendsList.friends) {
		friend_id = environnement.friendsList.friends[i].id;
		$("#friend_id_"+ friend_id).mouseover(
    		function () {getuserinfo( friend_id );  		}		
    		
		);
		
		$("#friend_id_"+ friend_id).mouseout(
	    		function () {
	    			
	    			$( "#popoutpanel_"+ friend_id ).hide(); 
	    		       }		
	    		
			);
	}
	search();	
	
};


//######################### FRIENDS LIST END #############################