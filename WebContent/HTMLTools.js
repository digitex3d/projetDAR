/**
 * ################################# Tools #####################################
 * This script contains tools that helps with the generation of HTML code.
 */

/**
 * Géneration d'un tag html qui contient content 
 * @param {String} tag - tag qu'il faut générer name of the tag
 * @param {String} className - name of the tag
 * @param {String} id - id of the tag
 * @return {String} HTML code of the code
 */ 
function genHTMLtag(tag, id,className, content){
	return 	"<"+tag+" id='" + id + "' class='" + className + "'>\n" 
				+ content +
			"</"+tag+">\n";
	
}


