package services.service;


import java.net.UnknownHostException;
import java.util.ArrayList;


import org.json.JSONArray;



import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


import database.DBStatic;

/**
 * Class qui gère les colocataires
 * 
 *
 */
public class ColocTools {
public static final String MDB_NAME = "dar";
	
	/*
	 * Ajoute un colocataire dans le tableau "colocs" de la base de données
	 */
	public static boolean coloc(String userId, String commentId) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCollection collection = db.getCollection("colocs");
		boolean status = status(userId, commentId );
		String option = ( status) ? "$pull" : "$addToSet";
		
		//db.friends.update( {"id": commentId }, { $option:{ "colocs": userId }})
		DBObject object = new BasicDBObject(option, new BasicDBObject("colocs",userId));
		DBObject query = new BasicDBObject("id", commentId );
		
		GeneralTools.serverLog("Query: {"+ query.toString() + ", " + object.toString()+" }");
		collection.update(query, object,true,false);
		
		m.close();
		return status;
		
	}
	
	/*
	 * Supprime un ami dans le tableau "friends" de la base de données
	 */
	public static boolean status(String userId, String commentId) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCollection collection = db.getCollection("colocs");
		
		//db.colocs.update( { $and: [ {"id": commentId }, { colocs: { $in: [ userId ] } } ] } )
		ArrayList<String> in = new ArrayList<String>();
		in.add(userId);
		DBObject object = new BasicDBObject("$in", in);
		DBObject object2 = new BasicDBObject("colocs", object);
		DBObject object3 = new BasicDBObject("id", commentId );
		ArrayList<DBObject> and = new ArrayList<DBObject>();
		and.add(object3);
		and.add(object2);
		DBObject query = new BasicDBObject( "$and" , and );
		
		GeneralTools.serverLog("Query: {"+ query.toString() + " }");
		DBCursor cursor = collection.find(query);
		
		boolean resu = cursor.hasNext();
	
		m.close();
		return resu;
		
	}
	
	
	//Fonction qui initialise le tableau des amis
	public static void userInitColocs(String comment_id) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCollection collection = db.getCollection("colocs");
		DBObject resu= new BasicDBObject();
		resu.put("id", comment_id);
		resu.put("colocs", new ArrayList<String>());
	
		GeneralTools.serverLog("Colocs de " + comment_id + " initialisé");
		collection.insert(resu);
		m.close();
		
	}

	/*
	 * Retourne une liste avec les colocs
	 */
	public static  JSONArray getColocs() throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCursor cursor;
		DBCollection collection = db.getCollection("colocs");
		cursor = collection.find();
		
		JSONArray resu = new JSONArray();
	
								  
        while (cursor.hasNext()) { 
        	resu.put(cursor.next());
       
        }
  
		m.close();
		return resu;
		
	}
	
}
