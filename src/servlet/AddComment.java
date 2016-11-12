package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.mapper.Mapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.opengraph.OpenGraph;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import services.auth.AuthentificationTools;
import services.service.CommentsTools;
import services.service.GeneralTools;

/*
 * Cette servlet gère l'ajout d'un commentaire
 */
@SuppressWarnings("serial")
public class AddComment extends HttpServlet{
	public static final String RATP_API_URL = "http://data.ratp.fr/api/records/1.0/search/";
	
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
		String imgid = request.getParameter("imgid");
		String nbimg = request.getParameter("nbimg");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		String addr = request.getParameter("addr");
		// Adresse de reference pour parser les metadata
		String refpage = request.getParameter("refpage");
		
		BasicDBObject jsonrefpage  = new BasicDBObject();

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
			
			// Refpage parsing
			if(refpage != null && !refpage.isEmpty()){
				// Vérifie que c'est un url
				String regex = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(refpage);
				
				
				if( !m.matches() )
					{ out.write( new JSONObject().put("error", "Erreur d'insertion url de reférence").toString());return; }
				
				
				// Parsing des meta données OpenGraph
				OpenGraph site;
				try { site = new OpenGraph(refpage, true); } 
				catch (Exception e) 					
					{ out.write( 
						new JSONObject().put("error", "Erreur d'insertion url de reférence").toString());return; }
				
				
				String ogtitle = site.getContent("title");
				String ogdescription = site.getContent("description");
				String ogimage = site.getContent("image");
				
				
				// Création Objet JSON contenant les metadonnées
			
				jsonrefpage.put("title",  ogtitle);
				jsonrefpage.put("description",  ogdescription); 
				jsonrefpage.put("image",  ogimage);
				jsonrefpage.put("url",  refpage); 

			}
			
			/**
			 * On récupère les donnés des stations RATP à partir de son api REST.
			 */
			
			String RATPquery = genRATPquery(lat, lng, "500");
			
			// Connection à L'API RATP
			URLConnection connection = new URL(RATP_API_URL + "?" + RATPquery).openConnection();
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("Content-Type", "json;charset=utf-8");
			InputStream RATPresponse = connection.getInputStream();
			
			StringWriter writer = new StringWriter();
			IOUtils.copy(RATPresponse, writer, "UTF-8");
			
			String RATPrespStr = writer.toString();
			
			RATPrespStr = RATPrespStr.replaceAll("geofilter.distance", "geofilter-distance");

			System.out.println(RATPrespStr);
			// Conversion JSON string to BasicDBObject
			JSONObject jsonRATPstations = new JSONObject (RATPrespStr);
			Object o = com.mongodb.util.JSON.parse(jsonRATPstations.toString());
			DBObject RATPstations = (DBObject) o;
			
			System.out.println(RATPstations.toString());
			
			CommentsTools.addComment(Integer.parseInt(authorid), 
					comment, 
					price, 
					desc, 
					dim, 
					imgid, 
					nbimg, 
					lat, 
					lng, 
					addr,
					jsonrefpage,
					RATPstations);

			GeneralTools.serverLog(	"Commentaire id: " + authorid +
					" cmt: " +comment + " ajouté");
			
			out.write( new JSONObject().put("data", "Commentaire id: " + authorid +	" ajoute").toString());

		}else{
			GeneralTools.serverLog("Erreur d'insertion du commentaire");
		
			out.write( new JSONObject().put("error", "Erreur d'insertion du commentaire").toString());
				
		}

	}
	
	/**
	 * Génère l'URL pour une query pour l'API RATP
	 * avec les points dans le cercle de rayon donné centré
	 * dans sur le point de coordonnées (lat,lng)
	 */
	private String genRATPquery(String lat,String lng, String rayon ){
		String RATPquery ="dataset=positions-geographiques-des-stations-du-reseau-ratp"
				+ "&rows=10&facet=stop_name"
				+ "&facet=code_postal"
				+ "&facet=departement"
				+ "&geofilter.distance="+lat
				+ "%2C"+lng
				+ "%2C"+rayon;
		return RATPquery;
		
	}

}
