package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import services.auth.AuthentificationTools;
import services.service.GeneralTools;
import services.service.LikeTools;

@SuppressWarnings("serial")
public class Like extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException
					{
		try {
			processRequest(request, response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					}

	// Method to handle POST method request.
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		doGet(request, response);

	}

	protected void processRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException, JSONException{
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");

		String mode;
		String userId;       
		String commentId;      
		String key;           
		ArrayList<String> params = new ArrayList<String>();

		// On récupère les paramètres
		params.add(userId  = request.getParameter("userId"));
		params.add(commentId = request.getParameter("commentId"));
		params.add(key = request.getParameter("key"));
		params.add(mode = request.getParameter("mode"));

		// On ajoute le commentaire
		if( GeneralTools.paramVerif(params)){
			// Si la clé n'est pas présent dans la base 
			if(  !AuthentificationTools.getSessionKey(key) ){
				GeneralTools.serverLog("Erreur d'authentification: la clé n'est pas présente dans la base", this);
				out.write( new JSONObject().put("error", "Erreur d'authentification: la clé n'est pas présente dans la base").toString());

				return; 

			}

			int mode_int = Integer.parseInt(mode);

			switch( mode_int){
			case 0:
				out.write( new JSONObject().put("liked", like(userId , commentId, out ) ).toString());
				break;
			case 1:
				out.write( getLikes(out).toString());
				break;

			}
		}else{
			GeneralTools.serverLog("Erreur d'insertion du like, vérifier paramètres", this);
			try {
				out.write( new JSONObject().put("error", "Erreur d'insertion du like, vérifier paramètres").toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	/*
	 * Methode statique qui récupère la liste des amis 
	 */
	public static JSONArray getLikes(PrintWriter out ) throws JSONException{
		JSONArray resu = new JSONArray();
		try{
			resu = LikeTools.getLikes();

		}catch (UnknownHostException e){
			out.write( new JSONObject().put("error", "Erreur du serveur, " + e.getMessage()).toString());

		}catch  (MongoException e){
			out.write( new JSONObject().put("error", "Erreur du serveur, " + e.getMessage()).toString());

		}

		return resu;

	}
	/*
	 * Methode statique qui récupère la liste des amis 
	 */
	public static boolean like(String userId , String commentId ,PrintWriter out ) throws JSONException{
		boolean resu= false;
		try{
			resu = LikeTools.like(userId, commentId);

		}catch (UnknownHostException e){
			out.write( new JSONObject().put("error", "Erreur d'insertion du like, " + e.getMessage()).toString());

		}catch  (MongoException e){
			out.write( new JSONObject().put("error", "Erreur d'insertion du like, " + e.getMessage()).toString());

		}

		return resu;

	}

}



