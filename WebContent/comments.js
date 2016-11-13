//*************************** COMMENTS ******************************** //


//############################ TIMELINE ############################### //
function Timeline(){
	this.comments = [];
	this.size = 0;

}
// Renvoie un commentaire à partir de son id
Timeline.prototype.getComment = function(id){
	for (var i in this.comments)
		if( this.comments[i].id == id)
			return this.comments[i];

	return null;
	
}

//Ajoute un obj commentaire à la timeline
Timeline.prototype.addComment = function(comment){
	var index = this.size;	

	this.comments[index] = comment;
	this.size++;

};

//Ajoute un obj commentaire à la timeline
Timeline.prototype.flush = function(){
	this.comments = [];
	this.size = 0;

};

//Genère le code html de la timeline
Timeline.prototype.getHtml = function(){
	var resu = "";

	for (var i in this.comments){
		var comment = this.comments[i];
		resu += comment.getHtml();

	}

	return resu;

};


//############################ FIN TIMELINE ###############################

//############################ COMMENT ###############################

function Comment (id,
		author,
		text,
		date,
		score,
		prix, 
		desc,
		dim, 
		imgid,
		nbimg,
		lat, 
		lng, 
		addr,
		refpage,
		RATPStations) {
	this.id=id;
	this.author=author;
	this.text=text;
	this.date=date;
	this.colocs;
	this.prix=prix;
	this.desc=desc;
	this.dim=dim;
	this.imgid=imgid;
	this.nbimg=nbimg;
	this.lat=lat;
	this.lng=lng;
	this.addr=addr;
	this.refpage= refpage;
	this.RATPStations= RATPStations;

	if (score == undefined) {
		this.score=0;
	}
	else {
		this.score=score;

	}

}


Comment.prototype.getExtHtml = function () {
	var user = environnement.actif;
	var cid = this.id;
	
	if(!isColoc(cid)){
		coloc_button = "<a class='btnColocs' style='color:green' id=\"propcoloc\" href=\"javascript:coloc('"+cid+"')\">+</a>";
	}else{
		coloc_button =  "<a class='btnColocsred'  style='color:red' id=\"propcoloc\" href=\"javascript:coloc('"+cid+"')\">-</a>";
	}
	
	var description = 
		"<tr class='annonce'>"+
			"<td class='annonce'>"+
				"<p>Prix</p>"+
			"</td>"+
			"<td class='annonce'>"+
				"<div class=\"prix_logement\" id=\"prix_id_"+cid+"\">" +
						"<p class='prix'>"
						+this.prix + "€</p>" +
				"</div>" +
			"</td>"+
		"</tr>"+
		"<tr class='annonce'>"+
			"<td class='annonce'>"+
				"<p>Dimension</p>"+
			"</td>"+
			"<td class='annonce'>"+
				"<div class=\"dim_logement\" id=\"dim_id_"+cid+"\">" +
					"<p class='dimension'>" +
					this.dim + "m²</p>"+
					"</div>"+
			"</td>"+
	"</tr>"+
	"<tr class='annonce'>"+
			"<td class='annonce'>"+
				"<p>Description</p><br>"+
				"</td>"+
				"<td class='annonce'>"+
				"<div class=\"desc_logement\" id=\"desc_id_"+cid+"\">" +
				"<p class='description'>" +
				this.desc +"</p>" +
			"</div>" +
			"</td>"+
			"</tr>"+
			"<tr class='annonce'>"+
				"<td class='annonce'>"+
					"<p>Lieu</p><br>"+
				"</td>"+
				"<td class='annonce'>"+
				
				"<div id='map'></div>"+
				"<div id='pano'></div>"+
				"</td>"+
			"</tr>"+
			"<tr class='annonce'>"+
			"<td class='annonce'>"+
				"<p>Adresse</p><br>"+
			"</td>"+
			"<td class='annonce'>"+
			this.addr+
			"</td>"+
		"</tr>"+
		"<tr class='annonce'>"+
			"<td class='annonce'>"+
				"<p>Stations RATP</p><br>"+
		"</td>"+
		"<td class='annonce'>"+
			"<div id='mapRATP'></div>"+
		"</td>"+
	"</tr>"+
			
	
	"<tr class='annonce'>"+
		"<td class='annonce'>"+
			"<p>Colocataires</p><br>"+
	"</td>"+
	"<td class='annonce'>"+
	coloc_button+
	"<span  id=\"coloc_names_" + cid +"\"> </span>"+
	"</div>" +
	"</td>"+
"</tr>"
		
		
		
		
		
		;

	if( this.refpage.title != null){

		var refpageHTML = "<tr class='annonce'>"+
		"<td class='annonce'>"+
		"<p>Site de référence</p><br>"+
		"</td>"+
	"<td class='annonce'>"+
		"<a href='"+ this.refpage.url +"'>Annonce originale</a><br>"+
		"<p>Titre: "+ this.refpage.title +"</p><br>"+
		"<img src='" + this.refpage.image +"' height='250' width='250'></img><br>"+
		"<p>Déscription: "+ this.refpage.description +"</p>"+

	"</td>"+
	"</tr>";
		
		description += refpageHTML;
	}
	
	var resu = 	
		"<div id=\"comment_" + cid + "\" class=\"comment\">" + 
		// Title
		"<h1 class=\"commentContent\">" +
		this.text+
		"</h1>" +
		
		// date
		"<div class=\"commentDate\">" +
		this.date+
		"</div>" +
		"<table>"+
		"<tr>"+
			"<td>"+
			
			"</td>"+
			"<td>"+
			// Images
			this.getImagesDiv(false) +
			"</td>"+
		"</tr>"+
		
		"</table>"+

		

		"<table class='annonce'>"+
		
		// Description
		genHTMLtag("div", "description-containter_"+cid, "description", description)+
		"</table>"+
		
				
				
				"</div>\n"+
		
		"<div class=\"line_separator\"></div>";
	



return resu.toString();
	
}

Comment.prototype.getImagesDiv = function(onlyFirst) {
	var cid = this.id;
	// Retrieve the current server path 
	var locimagePath = imagepath+"/imgs/"+this.imgid;
	
	// Populate images divs
	var imagesDivHTML = 
		"<div class='imagescontainer' id='imagecontainer_" + cid + "'>";
	
	if( this.imgid == null ){
		imagesDivHTML += "<div class='imageAnnonce' id='image_"+cid+"0'>"+
		"<img src='house-icon.png' width='200' height='200'></img>"+
		"</div></div>";
		
		return imagesDivHTML;
		
	}
	
	if( !onlyFirst ){
		for( var i = 0; i < this.nbimg; i ++){
			imagesDivHTML += "<div class='imageAnnonce' id='image_"+cid+i+"'>"+
			"<img src='"+locimagePath+i+".png?width=200&height=200"+"'></img>"+
			"</div>"		
			;
			
		}
		
	} else {
		imagesDivHTML += "<div class='imageAnnonce' id='image_"+cid+"0'>"+
		"<img src='"+locimagePath+"0.png?width=200&height=200"+"'></img>"+
		"</div>";
		
	}
	
	
	imagesDivHTML += "</div>";
	
	
	return imagesDivHTML;
}

//Retourne code html pour un logement
Comment.prototype.getHtml = function() {
	var user=environnement.actif;
	var add_button = "<span class=\"friendAdd\">" +
						"</span>";
	var cid = this.id;
	var coloc_button;
	

	
	if( user != undefined ){
		if(		!isFriend(this.author.id) &&
				user.id != this.author.id){
			add_button = 	"<span class=\"friendAdd\">" +
								"<a class=\"add_button\""+
								" href=\" javascript: addFriend("+ this.author.id +"); \" >"+
								"<img src=\"add_friend.png\" alert=\"Ajoute ami\"></img>"+
								"</a>"+   					
							"</span>";
		}

	}


	
	var description = 	"<table class='annoncel' style='margin: 25px 0px;'>" +
			"<tr class='annoncel'>"+
	"<td class='annoncel'>"+
	"<p>Prix</p>"+
"</td>"+
"<td class='annoncel'>"+
	"<div class=\"prix_logement\" id=\"prix_id_"+cid+"\">" +
			"<p class='prix'>"
			+this.prix + "€</p>" +
	"</div>" +
"</td>"+
"</tr>"+
"<tr class='annoncel'>"+
"<td class='annoncel'>"+
"<p>Description</p>"+
"</td>"+
"<td class='annoncel'>"+
"<div class=\"desc_logement\" id=\"desc_id_"+cid+"\">" +
"<p class='description'>" +
this.desc +"</p>" +
"</div>" +
"</td>"+
"</tr>"+
"<tr class='annoncel'>"+
"<td class='annoncel'>"+
	"<p>Dimension</p>"+
"</td>"+
"<td class='annoncel'>"+
	"<div class=\"dim_logement\" id=\"dim_id_"+cid+"\">" +
		"<p class='dimension'>" +
		this.dim + "m²</p>"+
		"</div>"+
"</td>"+
"</tr>"+
"<tr class='annoncel'>"+
"<td class='annoncel'>"+
	"<p>Auteur</p>"+
"</td>"+
"<td class='annoncel'>"+
	"<div class=\"auteur_logement\" id=\"auteur_id_"+cid+"\">" +
		add_button+
		"<span class=\"commentAuthor\">" +
		this.author.login+
		"</span>" +
		"</div>"+
"</td>"+
"</tr>"+
"</table>";
	
	
	
	var resu = 	"<div id=\"comment_" + cid + "\" class=\"comment\">" + 
					"<div class=\"commentDiv\">" +
					"<a class=\"annonce_link\""+
					" href='annonce.jsp?id="+ cid +"' >"+
					"<h2 class=\"commentContent\">" +
					this.text+
					
					"</h2>" + "</a>" +
					"<table>"+
						"<tr>"+
							"<th>"+
						
								this.getImagesDiv(true)+
							"</th>"+
							"<th style='width: 100%'>"+
							
									
									
										
											"<span class=\"commentDate\">" +
											this.date+
											"</span>" +
											
											description+
					
							"</th>"+
						"</tr>"+
					"</table>"+
					"<div class=\"line_separator\"></div>" +
				"</div>\n";
	

	return resu.toString();

};


//Traite la reponse d'un search
Comment.traiterResponseJSON = function(data){
	var obj;
	var comment;
	var author; 
	var timeline;
	
	environnement.timeline.flush();
	
	// parse sur la reponse
	for (var i in data.comments) {
		comment =  data.comments[i];
		author = (environnement.users[comment.author_id]==undefined) ? 
				new User(comment.author_id, comment.author_name) : 
					environnement.users[comment.author_id];
				
				obj = new Comment (comment.id, 
						author,
						comment.text,
						comment.date,
						comment.score,
						comment.price,
						comment.desc,
						comment.dim,
						comment.imgid,
						comment.nbimg,
						comment.lat,
						comment.lng,
						comment.addr,
						comment.refpage,
						comment.RATPStations);
				environnement.timeline.addComment(obj);

	}

	// Sort sur la date du commentaire
	environnement.timeline.comments.sort(function(a, b) 
			{
		
		return new Date(b.date) - new Date(a.date);});
	
	environnement.timeline.comments.reverse();

	timeline = environnement.timeline.getHtml();
	$("#comment_box").html(timeline);
	updateColocs();
	$("#circularG").css("visibility","hidden");
	
};

//############################ FIN COMMENT ###############################

// ########################## INSERTION COMMENTAIRE ######################


function extItem(cid){
	if( environnement.timeline != null)
		var timeline=environnement.timeline;
	else return;
	
	if( timeline.getComment(cid) != null){
		var annonce = timeline.getComment(cid);
	
		$("#timeline").html(annonce.getExtHtml());
	
	// Afficher les colocataires
	//TODO: changer nom
		updateColocs();
}else{
	alert(timeline.getComment(cid));
	return;
}
	
	
};


//Insère un commentaire dans la base de données
function insertCmt (form){
	var user=environnement.actif;
	var comment = form.commentInput.value;
	var price = form.priceInput.value;
	var desc = form.descInput.value;
	var dim = form.dimInput.value;
	var lat = marker.getPosition().lat();
	var lng = marker.getPosition().lng();
	var refpage =  form.refpageInput.value;
	var addr = form.adressInput.value;

	
	// Convert form to formData type
	//var formData = new FormData();
	
	var formData = { "key" : environnement.key,
			"id": user.id,
			"cmt": comment, 
			"price": price, 
			"desc": desc,  
			"dim": dim,
			"addr": addr,
			"imgid": environnement.images.imgid,
			"nbimg": environnement.images.nbimg,
			"lat" : lat, 
			"lng": lng,
			"refpage": refpage
			
	}
	
	
/*	// Append user id and key
	formData.append("key", environnement.key);
	formData.append("id", user.id);
	
	// Append entry infos
	formData.append("cmt", comment);
	formData.append("price", price);
	formData.append("desc", desc);
	formData.append("dim", dim);
	
	// Append the number of images and the id
	// of the images to associate the entry with 
	// the images.
	formData.append("imgid", environnement.images.imgid);
	formData.append("nbimg", environnement.images.nbimg);*/

	
	if( user != undefined){
		$.ajax({
			type: "POST",
			data: formData,	
			url: server_path + "addComment",
			success: traiteReponseInsert,
			error: traiteReponseErreur,
		
		});

	}

}


//TODO: modifier gestion erreurs
function traiteReponseInsert(data) {
	if (data.error != undefined) {
		fonc_erreur(null , data.error);
		
	}
	else {
		$("#insertCmtForm input").val("");
		if (data.status=="error") {
			errorHandle (data.code);
			
		}
		else {
			colocs();
			
		}

	}

}