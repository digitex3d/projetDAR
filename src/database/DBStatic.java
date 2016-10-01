package database;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import services.service.GeneralTools;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * 
 * Class qui gère des fonctions statiques pour la connexion aux DB
 *
 */
public class DBStatic {
	

//	private static final String DB_NAME = System.getenv("OPENSHIFT_APP_NAME");
//	private static final String DB_HOST = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
//	private static final String DB_PORT = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
//	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
//	
//	private static final String DB_CONNECTION = "jdbc:mysql://"+ DB_HOST+":"+DB_PORT +"/" + DB_NAME;
//	private static final String DB_USER = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
//	private static final String DB_PASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
//	private static final String MONGO_ADR = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
//	private static final Integer MONGO_PORT = Integer.decode(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
	
	// Configuration à utiliser que en LOCALE 
	
	//  ################## [ conf locale   
	
	private static final String DB_NAME = "dar";
	private static final String DB_HOST = "127.0.0.1";
	private static final String DB_PORT = "3306";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://"+ DB_HOST+":"+DB_PORT +"/" + DB_NAME;
	private static final String DB_USER = "dar";
	private static final String DB_PASSWORD = "dar";
	private static final String MONGO_ADR = "127.0.0.1";
	private static final Integer MONGO_PORT = 27017;
	
	//   					 conf locale ] ##################
	
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
		
		GeneralTools.serverLog("-------- MySQL JDBC Connection Testing ------------\n", "DBStatic", Level.FINER);

		 
		try {
			Class.forName(DB_DRIVER);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Je ne peux pas trouver le class");
			e.printStackTrace();
		
		}
	 
		GeneralTools.serverLog("MySQL JDBC Driver enregistré\n", "DBStatic", Level.FINER);

		Connection connection = null;
	 	
		connection = DriverManager.getConnection(DB_CONNECTION,DB_USER , DB_PASSWORD);
	 
	 
		if (connection != null) {
			GeneralTools.serverLog("Connexion à MySQL réussie!",  Level.FINE);
			
		} else {
			GeneralTools.serverLog("Pas possible de se connecter à MySQL!",  Level.FINE);
			
		}
		
		return connection;
	
	}
	
}
