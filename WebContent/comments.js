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

function Comment (id, author, text, date, score, prix, desc, dim, imgid, nbimg) {
	this.id=id;
	this.author=author;
	this.text=text;
	this.date=date;
	this.likes;
	this.prix=prix;
	this.desc=desc;
	this.dim=dim;
	this.imgid=imgid;
	this.nbimg=nbimg;

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
		coloc_button = "<a id=\"propcoloc\" href=\"javascript:coloc('"+cid+"')\">Propose colocation</a>";
	}else{
		coloc_button =  "<a id=\"propcoloc\" href=\"javascript:coloc('"+cid+"')\">Enlève proposition colocation</a>";
	}
	
	var description = 
	
		"<div class=\"prix_logement\" id=\"prix_id_"+cid+"\">" +
				"<p class='prix'>Prix:"
				+this.prix + "€</p>" +
		"</div>" +
		"<div class=\"desc_logement\" id=\"desc_id_"+cid+"\">" +
				"<p class='description'>Déscription:" +
				this.desc +"</p>" +
		"</div>" +
		"<div class=\"dim_logement\" id=\"dim_id_"+cid+"\">" +
				"<p class='dimension'>Dimension:" +
				this.dim + "m²</p>"+
		"</div>";
	
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

		// Images
		this.getImagesDiv(false) +

		// Description
		genHTMLtag("div", "description-containter_"+cid, "description", description)
		+
		coloc_button+
		// Colocataires
		"<div class=\"colocataires\"><p>Colocataires</p>"+	
	
		"<span  id=\"coloc_names_" + cid +"\"> </span>"+
		"</div>" +
		
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
	
	if( !onlyFirst ){
		for( var i = 0; i < this.nbimg; i ++){
			imagesDivHTML += "<div class='imageAnnonce' id='image_"+cid+i+"'>"+
			"<img src='"+locimagePath+i+".png?width=200&height=200"+"'></img>"+
			"</div>";
			
		}
		
	} else {
		imagesDivHTML += "<div class='image' id='image_"+cid+"0'>"+
		"<img src='"+locimagePath+"0.png?width=200&height=200"+"'></img>"+
		"</div>";
		
	}
	
	
	imagesDivHTML += "</div>";
	
	
	return imagesDivHTML;
}

//Retourne code html pour un commentaire
Comment.prototype.getHtml = function() {
	var user=environnement.actif;
	var add_button = "<span class=\"friendAdd\">" +
						"</span>";
	var cid = this.id;
	var like_button;
	

	
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


	
	
	
	
	
	var resu = 	"<div id=\"comment_" + cid + "\" class=\"comment\">" + 
					"<div class=\"commentDiv\">" +
						add_button+
						this.getImagesDiv(true)
						+
						"<span class=\"commentAuthor\">" +
							this.author.login+
						"</span>" +
						"<a class=\"annonce_link\""+
						" href=\" javascript: extItem('"+ cid +"'); \" >"+
						"<span class=\"commentContent\">" +
							this.text+
						"</span>" + "</a>" +
						"<span class=\"commentDate\">" +
							this.date+
						"</span>" +
						"<div class=\"prix_logement\" id=\"prix_id_"+cid+"\">" +
								this.prix +
						"</div>" +
						"<div class=\"desc_logement\" id=\"desc_id_"+cid+"\">" +
							this.desc +
						"</div>" +
						"<div class=\"dim_logement\" id=\"dim_id_"+cid+"\">" +
							this.dim +
						"</div>" +
						"<div class=\"line_separator\"></div>" +
					"</div>" +
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
						comment.nbimg);
				environnement.timeline.addComment(obj);

	}

	// Sort sur la date du commentaire
	environnement.timeline.comments.sort(function(a, b) 
			{return new Date(b.date) - new Date(a.date);});

	timeline = environnement.timeline.getHtml();
	$("#comment_box").html(timeline);
	updateLikes();
	$("#circularG").css("visibility","hidden");
	
};

//############################ FIN COMMENT ###############################

// ########################## INSERTION COMMENTAIRE ######################


function extItem(cid){
	var timeline=environnement.timeline;
	var annonce = timeline.getComment(cid);
	
	$("#timeline").html(annonce.getExtHtml());
	
	// Afficher les colocataires
	//TODO: changer nom
	updateLikes();
	
	
};


//Insère un commentaire dans la base de données
function insertCmt (form){
	var user=environnement.actif;
	var comment = encodeURIComponent(form.commentInput.value);
	var price = encodeURIComponent(form.priceInput.value);
	var desc = encodeURIComponent(form.descInput.value);
	var dim = encodeURIComponent(form.dimInput.value);
	var lat = marker.getPosition().lat();
	var lng = marker.getPosition().lng();
	var addr = encodeURIComponent(form.adressInput.value);
	
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
			error: traiteReponseErreur

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
			likes();
			
		}

	}

}