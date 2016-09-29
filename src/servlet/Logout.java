package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.auth.AuthentificationTools;
import services.service.GeneralTools;

/**
 * Servlet qui gère la déconnexion
 * 
 *
 */
@SuppressWarnings("serial")
public class Logout extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException
					{
						processRequest(request, response);
					}


	// Method to handle POST method request.
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		doGet(request, response);
	}
		  
		  protected void processRequest(HttpServletRequest request, 
				  HttpServletResponse response) throws ServletException, IOException{
			  // On récupère la clé
			  String key = request.getParameter("key");
			  
			  // On supprime la clé de la base
			  if(key != null && !key.equals("null")){
				  AuthentificationTools.removeKey(key);
				  GeneralTools.serverLog("Clé "+ key + " supprimé", this);
				  
			  }
				 
		  }
		  
}
