function fonc_erreur(e, msg){
	var msg_box = "<div class='erreur_msg_box'><p>"+ msg +"</p></div>";
	var old_msg_box = $(".erreur_msg_box");
	if(old_msg_box.length > 0){
		old_msg_box.remove();
		$(".error_boxs").append(msg_box);
	}else{
		$(".error_boxs").append(msg_box);
	}



	if(e != null){ 
		e.css({	"box-shadow": 	"0px 0px 10px red",
		"border"	:	"2px solid red"}); 
	}
	
	$(".error_boxs").css({"display":"block"});

}

function traiteReponseErreur (xhr, ajaxOptions, thrownError) {
	fonc_erreur(null,"Erreur serveur " +  xhr.status + " " + thrownError);
	       
}


// Valider une entrée de type input du form
function validateEntry(e){
	
	if ( e ==null || e.val()==""){
		fonc_erreur(e, e.attr("name") + " ne peut pas être vide");
		return false;
		
	}else if (e.val().length > 20){
		fonc_erreur(e, e.attr("name") + " est trop grand");
		return false;
		
	}
	return true;
	
}

// Enlève tous les erreurs
function remove_errors(){
	$(".error_boxs").css({"display":"none"});
	$( ".ids" ).each(
		function() {
			$( this ).children("input").removeAttr('style');

		});

}


