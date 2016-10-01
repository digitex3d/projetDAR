package services.service;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.auth.AuthentificationTools;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;

import database.DBStatic;

/**
 * Class qui contient les fonction pour gèrer les commentaires
 * 
 *
 */
public class CommentsTools {
	public static final String MDB_NAME = "dar";
	
	/**
	 * Ajoute un commentaire à la base mongo
	 * @param authorid
	 * @param text
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public static void addComment(int authorid, String text) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCollection collection = db.getCollection("comments");
		BasicDBObject object = new BasicDBObject();
		String key = ServiceTools.generateRandomKey();
		object.put("id", key );
		object.put("author_id", Integer.toString(authorid) );
		object.put("author_name",  AuthentificationTools.getUserId(authorid) );	
		object.put("text", text);
		LikeTools.userInitLikes(key);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();

		object.put("date", dateFormat.format(today));
		collection.insert(object).toString();
		m.close();
		
	}
	
	/**
	 * Reçoise le commentaire qui correspondent à la query
	 * @param query
	 * @return
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	public static JSONObject getComments(String query) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCursor cursor;
		DBCollection collection = db.getCollection("comments");
		
		DBObject quoteQuery = QueryBuilder.start("text").is(Pattern.compile(query, 
                    Pattern.CASE_INSENSITIVE)).get();
		cursor = collection.find(quoteQuery);
			

		JSONObject resu = new JSONObject();
		JSONArray commentsArray = new JSONArray();
								  
        while (cursor.hasNext()) { 
        	commentsArray.put(cursor.next());
       
        }
        
        try {
			resu.put("comments", commentsArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		m.close();
		return resu;
		
	}
}
