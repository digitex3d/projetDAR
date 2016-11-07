package servlet;
 
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import services.auth.AuthentificationTools;
import services.service.GeneralTools;

/*
 * Servlet pour le login
 */
@SuppressWarnings("serial")
public class Login extends HttpServlet{
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
		HttpSession session=request.getSession();
		PrintWriter out = response.getWriter(); 

		// On sauvegarde les paramètres
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		if(login!=null && !login.equals("null")){
			if( AuthentificationTools.userExist(login) ){
				if(password!=null &&!password.equals("null")){
					System.out.println("Connexion: "+login+" "+password);
					if(AuthentificationTools.checkPassword(login, password)){

						GeneralTools.serverLog("Mot de pass bon.", this);
						int id = AuthentificationTools.getIDUser(login);
						
						String key;
						
						// If the user hasn't an opened session
						// Create a new session for the user
						if( !AuthentificationTools.hasKey(id) )
							key = AuthentificationTools.
									insertSession( id, false);
						
						 else 
							key = AuthentificationTools.getUserKey(id);

						System.out.println("Connection de "+login+" reussiec clef:" + key);	     				          

						JSONObject repobjs = new JSONObject();
						
						// Cookie de session
						Cookie cookieId = new Cookie("session_key", key);
						cookieId.setMaxAge(60*60*24*15);
						session.setAttribute("session_key", key);
						session.setAttribute("id", Integer.toString(id));
						session.setAttribute("login", login);
						response.addCookie(cookieId);
						

						/*repobjs.put("id", Integer.toString(id));
						repobjs.put("login", login);
						repobjs.put("key", key);*/

						out.write(repobjs.toString());

					}else{
						GeneralTools.serverLog("Mot de pass pas bon.");

						out.write( new JSONObject().put("error", "Mot de pass pas bon.").toString());

					}

				}else{
					GeneralTools.serverLog("L'username "+ login + " n'existe pas");
					out.write( new JSONObject().put("error", "L'username "+ login + " n'existe pas").toString());


				}
			}else{	
				GeneralTools.serverLog("L'username "+ login + "est vide");
				out.write( new JSONObject().put("error", "L'username "+ login + "est vide").toString());

			}

		}
	}

}


