// ############# ImageUpload Tools ###########
// Author: Giuseppe Federico

//Upload images to the server associated with the entry.
function uploadImage(event){
	var files = event.target.files;
	var formData = new FormData();

	for (var i = 0, file; file = files[i]; i++) 
		formData.append("image"+i, file);	
		

	$.ajax({
		type: "POST",
		data: formData,	
		url: server_path + "uploadimage",
		processData: false,
		contentType: false,
		success: traiteReponseUpload,
		error: traiteReponseErreur

	});

}

function traiteReponseUpload(data){
	environnement.images = data;
	
}

