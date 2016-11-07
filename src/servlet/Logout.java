package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			  HttpSession session=request.getSession();
			  // On récupère la clé
			  String key = request.getParameter("key");
			  
			  // On supprime la clé de la base
			  if(key != null && !key.equals("null")){
				  AuthentificationTools.removeKey(key);
				  eraseCookie(request, response);
				    session.invalidate();
				  GeneralTools.serverLog("Clé "+ key + " supprimé", this);
				  
			  }
				 
		  }
		  
		  private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
	
				    Cookie[] cookies = req.getCookies();

				    if (cookies != null) {
				        for (Cookie cookie : cookies) {
				                cookie.setValue(null);
				                cookie.setMaxAge(0);
				                resp.addCookie(cookie);
				            }
				        }
				    }

			
		  
}
