// ################################## MAIN FUNCTION ##########################################


// path du serveur
server_path="http://localhost:8080/projetDAR/";


/*
 * Fonction principale 
 * 
 */
function main(id, login, key) {
	environnement={};
	
	// Utilisateurs
	environnement.users= new Array();
	// Commentaires
	environnement.timeline = new Timeline();
	// Liste des amis
	environnement.friendsList = new FriendsList();
	// Liste des colocs
	environnement.colocsList = new ColocsList();
	// Images temporaires
	environnement.images = {};
	
	if( ( id != null) && 
		(login!=null) &&
		(key!=null)) {
		environnement.key=key;
		environnement.actif=new User(id,login);
		search();
		colocs();
		friends();
		gererDivConnexion();

	
	}
	else {
		search();
		gererDivConnexion();
		
	}
   
} 

//################################# Gestion page #######################################

//Cette foncion gère l'affichage des divs en lode connecté/déconnecté
function gererDivConnexion(){
	var user=environnement.actif;
	if(user == undefined) {
		// On affiche les panneaux en ligne
		$("#disconnect").css("display","none") ;
		$(".onlyFriends").css("display","none");
		$(".offline_panels").css("display","block" ) ;
		$("#dashboard").css("display","none"); 
		$(".colocs").css("display","none"); 
		
	}
	else {
		// On affiche les panneaux en ligne
		$("#disconnect").css("display","block" );
		$(".offline_panels").css("display","none" );
		$("#left_panel").css("display","block"); 
		$("#fullname").html("<p>Bonjour, " + "<font color=\"red\">"+user.login+"</font></p>");
		$(".onlyFriends").css("display","block");
		$(".colocs").css("display","block"); 

	}
	
}


// Cette fonction déconnecte l'utilisateur
function deconnect(){
	$.get( server_path+"logout?key="+environnement.key, function( data ) {
			window.location.href="main.jsp";
		});
	
}

// Fonction qui montre le panneau d'enregistrement
function show_register(){
	var user=environnement.actif;
	if(user == undefined){
		$("#register_panel").css("display","block" );
		
	}

}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

// Validate du form register
function reg_validateForm(){
	// Effacer les erreurs précédents
	remove_errors();
	var flag = true;
	
	// Vérifier la validté de l'entrée pour tout objet de la classe .ids 
	$( ".ids_reg" ).each(function() {
		var e = $( this ).children("input");
		flag &= validateEntry(e);
		});
	
	if( !validateEmail($("#mail_register").val()) ){
		fonc_erreur($("#mail_register"), "Le mail n'est pas valide");
		return;
	}
	
	if($("#pass_register").val() != $("#repass_register").val()){
		fonc_erreur($("#repass_register"), "Les mots de pass ne sont pas égales");
		
		return;
	}
	
	if(flag){
		register(	$("#login_register").val(),
					$("#mail_register").val(),
					$("#nom_register").val(),
					$("#prenom_register").val(),
					$("#pass_register").val()
		
		);
		
	}
}

// Validate du from connecte
function validateForm(){
	// Effacer les erreurs précédents
	remove_errors();
	var flag = true;
	
	// Vérifier la validté de l'entrée pour tout objet de la classe .ids 
	$(".ids").each(function(){
		var e = $( this ).children("input");
		flag &= validateEntry(e);
		});
	
	
	if(flag){
		connecte($("#login").val(), $("#pass").val());
		
	}

}


//################################### FIN GESTION ########################################


// ################################### USER ########################################
function User(id, login){
	 this.id = id;
	 this.login = login;
	 this.contact = false;
	 
	 this.modifieStatus = modifieStatus;
	 function modifieStatus(){
		 this.status =  (this.status ? false:true);
		 
	 }
	 
	 environnement.users[this.id] = this;
	 
}

User.prototype.getHtml = function(){
	var user=environnement.actif;
	var remove_button = "<span class=\"friendRemove\">" +
					 	"</span>";
	
	if( user != undefined & environnement.actif != this){
		remove_button = "<a class=\"add_button\" href=\" javascript: removeFriend("+ this.id +"); \" >" +
				"<img src=\"remove_friend.png\" alert=\"Supprime ami\"></img>"+
				"</a>";  					
						
	}
	
	
	
    var resu = 	"<div id=\"friend_" + this.id + "\" class=\"friend\"><p>" + 
    					remove_button+
    					"<span class=\"friendName\" id='friend_id_"+ this.id +"'  >" +
    						this.login+
    					"</span>" +
  
    			"</p></div>\n"+
    			"<div class='popoutpanel' id='popoutpanel_" + this.id + "'>\n"+
    				
    			"</div>";

    
    
    return resu.toString();
	
};



// fonction de callback pour obtenir une réponse d'une manière
// "pseudo synchrone".
function getHTMLfromServer(data) {
    return data;
    
}

// Appel ajax pour obtenir les infromations de l'utilisateur.
// passer en paramètre un id et un fonction pour traiter les données
function getuserinfo(id) {
    $.ajax({
    	data:	"id="+id,
		url: server_path+"getuserinfo",
		type:"GET",
		success: function (data){
			  $("#popoutpanel_" + id).html(data);
			  $( "#popoutpanel_"+ id ).show();
		},
		error: traiteReponseErreur
    });
}

//################################### FIN USER ########################################

//################################### AUTH ########################################

// Enregistrer un nouveau utilisateur
function register(login, mail, nom, prenom, password){
	// Vider les champs
	$("#login_register").val("");
	$("#pass_register").val("");
	$("#mail_register").val("");
	$("#nom_register").val("");
	$("#prenom_register").val("");
	$("#repass_register").val("");
	
	$.ajax({
		data:	"login="+login+
				"&password="+password+
				"&mail="+mail+
				"&nom="+nom+
				"&prenom="+prenom,
		url: server_path+"register",
		type:"GET",
		success: traiteReponseRegister,
		dataType:"json",
		error: traiteReponseErreur
		
	});
	
}

// Fonction qui connecte l'utilisateur
function connecte(login, pass){
	$.ajax({
		data:"login="+login+"&password="+pass,
		url: server_path+"login",
		type:"GET",
		success:  traiteReponseConnexion,
		dataType:"json",
		error: traiteReponseErreur
		
	});
	
}

//Fonction traite la reponse de la servlet Register
function traiteReponseRegister(rep){
	if(rep.error != undefined){
		fonc_erreur(null, rep.error);
		
	}else{
		fonc_erreur(null, "Utilisateur enregistré.");
		
	}
	
}

//Fonction traite la reponse de la servlet Connextion
function traiteReponseConnexion(rep){
	if(rep.error != undefined){
		fonc_erreur(null, rep.error);
	}else{
		window.location.href=	"main.jsp";
		
	}
	
}
