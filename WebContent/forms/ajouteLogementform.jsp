	<h1>Ajouter un logement</h1>
<table><tr>
<td>
<div id="comment_insert_box" >

	<form onsubmit="insertCmt(this)" action="javascript:"
		id="insertCmtForm" enctype="multipart/form-data">
		<input placeholder="Titre..." name="commentInput" id="cmt"
			class="textInput" /> 
			<input placeholder="Prix..." name="priceInput"
			id="price" class="textInput" /> 
			<input placeholder="Description..."
			name="descInput" id="desc" class="textInput" />
			
			<input placeholder="Dimension(m²)..."
			name="dimInput" id="dim" class="textInput" />
			
			<input type="file" name="image"			id="imageSelect" size="50" multiple /> 
		 	<input	placeholder="Adresse..." 
		 			name="adressInput" 
		 			id="address"	
		 			class="textInput" /> 
			<input type="submit"			value="Add">
	</form>
	
</div>
</td>
<td>
<div id='map'></div>
</td>
<tr>
</table>


<script async defer
	src="https://maps.googleapis.com/maps/api/js?
			key=AIzaSyCdRvJbx0egU7JQuyBJKou26YIqKwki4c4&callback=initMap">
	
	</script>
