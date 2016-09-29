package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import services.service.CommentsTools;
import services.service.GeneralTools;

/*
 * Service résponsable de la recherche de commentaires dans la base Mongo
 */
@SuppressWarnings("serial")
public class Search extends HttpServlet{
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

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		doGet(request, response);
	}

	protected void processRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException, JSONException{
		response.setContentType("application/json;charset=UTF-8");

		String query;

		ArrayList<String> params = new ArrayList<String>();
		PrintWriter out = response.getWriter(); 

		// On récupère les paramètres
		params.add(query = request.getParameter("query"));

		if( GeneralTools.paramVerif(params) ){
			try{
				out.write( CommentsTools.getComments(query).toString());
				GeneralTools.serverLog("Query exécuté", this);
				
			}catch (UnknownHostException e){
				out.write( new JSONObject().put("error", "Erreur de search, " + 
													e.getMessage()).toString());
				
			}catch (MongoException e){
				out.write( new JSONObject().put("error", "Erreur de search, " +
													e.getMessage()).toString());
				
			}

		}else{
			GeneralTools.serverLog("Verifier les paramètres", this);
			out.write( new JSONObject().put("error", "Erreur de search, verifier les paramètres").toString());

		}

	}

}
