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
 * Classe qui gère les requêtes mongo pour les amis
 * @author peppe
 *
 */
public class FriendsTools {
	public static final String MDB_NAME = "dar";

	/*
	 * Ajoute un ami dans le tableau "friends" de la base de données
	 */
	public static void addFriend(String adderId, String firendID) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCollection collection = db.getCollection("friends");
		

	

		//db.friends.update( {"id": adderId }, { $push:{ "friends": firendID }})
		DBObject object = new BasicDBObject("$addToSet", new BasicDBObject("friends",firendID));
		DBObject query = new BasicDBObject("id",adderId );

		GeneralTools.serverLog("Query: {"+ query.toString() + ", " + object.toString()+" }");
		collection.update(query, object, true,false);

		m.close();
	

	}

	/*
	 * Supprime un ami dans le tableau "friends" de la base de données
	 */
	public static void removeFriend(String adderId, String firendID) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCollection collection = db.getCollection("friends");


		DBObject object = new BasicDBObject("$pull", new BasicDBObject("friends",firendID));
		DBObject query = new BasicDBObject("id",adderId );

		GeneralTools.serverLog("Query: {"+ query.toString() + ", " + object.toString()+" }");
		collection.update(query, object);

		m.close();

	}

	/*
	 * Retourne une liste d'amis 
	 */
	public static  JSONArray getFriends(String id) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCursor cursor;
		DBCollection collection = db.getCollection("friends");

		DBObject  query = new BasicDBObject("id", id);
		cursor = collection.find(query);

		JSONArray resu = new JSONArray();


		while (cursor.hasNext()) { 
			resu.put(cursor.next());

		}

		m.close();
		return resu;

	}

	//Fonction qui initialise le tableau des amis
	public static void userInitFriends(String id) throws UnknownHostException, MongoException{
		Mongo m = DBStatic.getMongoConnection();
		DB db = m.getDB(MDB_NAME);
		DBCollection collection = db.getCollection("friends");
		DBObject resu= new BasicDBObject();
		resu.put("id", id);
		resu.put("friends", new ArrayList<String>());

		collection.insert(resu);
		m.close();

	}

}
