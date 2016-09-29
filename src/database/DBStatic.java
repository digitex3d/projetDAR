package database;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import services.service.GeneralTools;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * 
 * Class qui gère des fonctions statiques pour la connexion aux DB
 *
 */
public class DBStatic {
	

	private static final String DB_NAME = System.getenv("OPENSHIFT_APP_NAME");
	private static final String DB_HOST = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
	private static final String DB_PORT = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String DB_CONNECTION = "jdbc:mysql://"+ DB_HOST+":"+DB_PORT +"/" + DB_NAME;
	private static final String DB_USER = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
	private static final String DB_PASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
	private static final String MONGO_ADR = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
	private static final Integer MONGO_PORT = Integer.decode(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
	
	/*
	 * Retourne une connexion Mongo
	 */
	public static Mongo getMongoConnection() throws UnknownHostException, MongoException{
		System.out.println("-------- MONGO Connection Testing ------------\n");
		
		Mongo m = new Mongo(MONGO_ADR, MONGO_PORT);	
		System.out.println("Connecté a MongoDB\n" + MONGO_ADR +":"+MONGO_PORT);
		return m;
			
	}
	
	/*
	 * Retourne une connexion à MySQL
	 */
	public static Connection getMySQLConnection() throws SQLException{
		System.out.println("-------- MySQL JDBC Connection Testing ------------\n");
		 
		try {
			Class.forName(DB_DRIVER);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Je ne peux pas trouver le class");
			e.printStackTrace();
		
		}
	 
		System.out.println("MySQL JDBC Driver enregistré");
		Connection connection = null;
	 
		
		connection = DriverManager.getConnection(DB_CONNECTION,DB_USER , DB_PASSWORD);
	 
	 
		if (connection != null) {
			GeneralTools.serverLog("Connexion à MySQL réussie!");
			
		} else {
			GeneralTools.serverLog("Pas possible de se connecter à MySQL!");
			
		}
		
		return connection;
	
	}
	
}
