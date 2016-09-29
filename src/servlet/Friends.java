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
import services.service.FriendsTools;
import services.service.GeneralTools;

@SuppressWarnings("serial")
public class Friends extends HttpServlet{
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
		String friendId;      
		String key;           
		ArrayList<String> params = new ArrayList<String>();
		
		// On récupère les paramètres
		params.add(mode = request.getParameter("mode"));
		params.add(userId  = request.getParameter("userId"));
		params.add(friendId = request.getParameter("friendId"));
		params.add(key = request.getParameter("key"));
		
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
				addFriend(userId, friendId, out );
				
				break;
				
			case 1:
				// On renvoie la liste des amis
				out.write( ((JSONArray) getFriends(userId, out)).toString() );
				GeneralTools.serverLog(((JSONArray) getFriends(userId, out)).toString(), this);
				break;
				
			case 2:
				// On supprime un amis
				removeFriend(userId, friendId, out );
				break;
				
			}
			
		}else{
			GeneralTools.serverLog("Verifier les paramètres", this);
			out.write( new JSONObject().put("error", "Erreur d'insertion d'amis, verifier les paramétres").toString());
		
		}

	}
	
	/*
	 * Methode statique qui récupère la liste des amis 
	 */
	public static JSONArray getFriends(String userId , PrintWriter out ) throws JSONException{
		JSONArray resu = new JSONArray();
		try{
			resu = FriendsTools.getFriends(userId);
			
		}catch (UnknownHostException e){
			out.write( new JSONObject().put("error", "Erreur d'insertion d'amis, " + e.getMessage()).toString());
			
		}catch  (MongoException e){
			out.write( new JSONObject().put("error", "Erreur d'insertion d'amis, " + e.getMessage()).toString());
		
		}
		
		return resu;
		
	}
	
	/*
	 * Methode statique qui ajoute un ami 
	 */
	public static void addFriend(String userId, String friendId, PrintWriter out ) throws JSONException{
		try{
			FriendsTools.addFriend(userId, friendId);
			
			
		}catch (UnknownHostException e){
			out.write( new JSONObject().put("error", "Erreur d'insertion d'amis, " + e.getMessage()).toString());
			
		}catch  (MongoException e){
			out.write( new JSONObject().put("error", "Erreur d'insertion d'amis, " + e.getMessage()).toString());
		
		}
		
		GeneralTools.serverLog(	"Amis id: " + friendId +
				" ajouté a " +userId);
		
		out.write( new JSONObject().put("data", "Amis id: " + friendId +
					" ajouté a " +userId).toString());
		
	}
	
	/*
	 * Methode statique qui supprime un ami 
	 */
	public static void removeFriend(String userId, String friendId, PrintWriter out ) throws JSONException{
		try{
			FriendsTools.removeFriend(userId, friendId);
			
			
		}catch (UnknownHostException e){
			out.write( new JSONObject().put("error", "Erreur d'insertion d'amis, " + e.getMessage()).toString());
			
		}catch  (MongoException e){
			out.write( new JSONObject().put("error", "Erreur d'insertion d'amis, " + e.getMessage()).toString());
		
		}
		
		GeneralTools.serverLog(	"Amis id: " + friendId +
				" supprimé a " +userId);
		
		out.write( new JSONObject().put("data", "Amis id: " + friendId +
					" supprimé a " +userId).toString());
		
	}

}
