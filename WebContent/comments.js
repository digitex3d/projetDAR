//*************************** COMMENTS ******************************** //


//############################ TIMELINE ############################### //
function Timeline(){
	this.comments = [];
	this.size = 0;

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

function Comment (id, author, text, date, score) {
	this.id=id;
	this.author=author;
	this.text=text;
	this.date=date;
	this.likes;

	if (score == undefined) {
		this.score=0;
	}
	else {
		this.score=score;

	}

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

	if(!isLiked(cid)){
		like_button = "<img src=\"like.png\" onClick=\"like('"+cid+"');\"></img>";
	}else{
		like_button = "<img src=\"unlike.png\" onClick=\"like('"+cid+"');\"></img>";
	}

	var resu = 	"<div id=\"comment_" + cid + "\" class=\"comment\">" + 
					"<div class=\"commentDiv\">" +
						add_button+
						"<span class=\"commentAuthor\">" +
							this.author.login+
						"</span>" +
						"<span class=\"commentContent\">" +
							this.text+
						"</span>" +
						"<span class=\"commentDate\">" +
							this.date+
						"</span>" +
							"<div class=\"likes\">"+	
								like_button+
								"<span  id=\"likes_number_" + cid +"\"> </span>"+
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

				obj = new Comment (comment.id, author,comment.text,comment.date, comment.score);
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

//Insère un commentaire dans la base de données
function insertCmt (form){
	var user=environnement.actif;
	var comment = encodeURIComponent(form.commentInput.value);
	if( user != undefined){
		$.ajax({
			type: "GET",
			data: 	"id="+ user.id+ 
					"&cmt="+comment+
					"&key="+environnement.key,
			url: server_path + "addComment",
			dataType: "json",
			scriptCharset: "utf-8" ,
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