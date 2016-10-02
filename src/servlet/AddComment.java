package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.auth.AuthentificationTools;
import services.service.CommentsTools;
import services.service.GeneralTools;

/*
 * Cette servlet gère l'ajout d'un commentaire
 */
@SuppressWarnings("serial")
public class AddComment extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException
					{
						try {
							processRequest(request, response);
						} catch (JSONException e) {			
							e.printStackTrace();
							
						}
					}

	// Method to handle POST method request.
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		doGet(request, response);
	}

	/*
	 * Gère la requête
	 */
	protected void processRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException, JSONException{
		
		PrintWriter out = response.getWriter(); 
		response.setContentType("application/json;charset=UTF-8");

		// On récupère les paramètres
		String authorid = request.getParameter("id");
		String comment = request.getParameter("cmt");
		String price = request.getParameter("price");
		String desc = request.getParameter("desc");
		String dim = request.getParameter("dim");
		String key = request.getParameter("key");

		// On ajoute le commentaire
		if(comment != null && !comment.equals("null") &&
				authorid != null && !authorid.equals("null") &&
				key != null && !key.equals("null")){

			// Si la clé n'est pas présent dans la base 
			if(  !AuthentificationTools.getSessionKey(key) ){
				System.out.println("Erreur d'authentification: la clé n'est pas présente dans la base");
					out.write( new JSONObject().put("error", "Erreur d'authentification: la clé n'est pas présente dans la base").toString());
					
				return; 
			}
			
			CommentsTools.addComment(Integer.parseInt(authorid), comment, price, desc, dim);

			GeneralTools.serverLog(	"Commentaire id: " + authorid +
					" cmt: " +comment + " ajouté");
			
			out.write( new JSONObject().put("data", "Commentaire id: " + authorid +	" ajoute").toString());

		}else{
			GeneralTools.serverLog("Erreur d'insertion du commentaire");
		
			out.write( new JSONObject().put("error", "Erreur d'insertion du commentaire").toString());
				
		}

	}

}
