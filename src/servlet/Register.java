package servlet;
 
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.auth.AuthentificationTools;
import services.service.FriendsTools;
import services.service.GeneralTools;



@SuppressWarnings("serial")
public class Register extends HttpServlet{
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
			  response.setContentType("application/json;charset=UTF-8");
			  PrintWriter out = response.getWriter(); 
			  
			  ArrayList<String> params = new ArrayList<String>();
			  String login;
			  String password;

			  // On récupère les paramètres
			  params.add(login = request.getParameter("login"));
			  params.add(password = request.getParameter("password"));

			  if(GeneralTools.paramVerif(params)){

				  if( !AuthentificationTools.userExist(login)){
					  AuthentificationTools.createUser(login, password,null, null);
					  int id = AuthentificationTools.getIDUser(login);
					  // Initialise la base de données mongo
					  try {
						  FriendsTools.userInitFriends( Integer.toString(id));
					  }catch(Exception e){
						
							  out.write( new JSONObject().put("error", "Je ne peux pas initialiser la liste des amis").toString());
						
					  }

					  GeneralTools.serverLog("Enregistré avec succès.");
					  out.write( new JSONObject().put("message", "Enregistré avec succès.").toString());
			
				  }else{
					  GeneralTools.serverLog("L'utilisateur existe déjà", this);
					  out.write( new JSONObject().put("error", "L'utilisateur existe déjà").toString());
					
				  }
			  }else{
				  GeneralTools.serverLog("Erreur dans les paramètres d'inscription", this);
				  out.write( new JSONObject().put("error", "Erreur dans les paramètres d'inscription").toString());
				  
			  }


		  }
		  
			  
}