// Formulaire pour ajouter un logement
function ajouterUnLogementPage(){
	
	
	$.ajax({
		  url: "forms/ajouteLogementform.jsp",
		  success: function(data){
				$("#timeline").html(data);
		  }
		});
	
}

//Validate du from d'insertion d'un logement
function validateAddForm(form){
	// Effacer les erreurs précédents
	remove_errors();
	var flag = true;
	
	// Vérifier la validté de l'entrée pour tout objet de la classe .ids 
	$("#oblig").each(function(){
		var e = $( this ).children("input");
		flag &= validateEntry(e);
		});
	
	
	if(flag){
		insertCmt(form);
		window.location.href = "main.jsp";
		
	}

}