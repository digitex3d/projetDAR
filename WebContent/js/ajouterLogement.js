// Formulaire pour ajouter un logement
function ajouterUnLogementPage(){
	
	
	$.ajax({
		  url: "forms/ajouteLogementform.jsp",
		  success: function(data){
				$("#timeline").html(data);
		  }
		});
	
}
