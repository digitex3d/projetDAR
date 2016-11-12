package servlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import database.DBStatic;
import services.service.GeneralTools;

@SuppressWarnings("serial")
/**
 * Cette classe initialise la base de données si c'est pas
 * encore fait.
 * C'est une classe qui s'exécute au demarrage du serveur.
 * 
 * 
 * @author giuseppe
 *
 */
public class InitDB extends HttpServlet{
	private	Connection conn;
	
	public void init() throws ServletException{
		GeneralTools.serverLog("-------- Init MySQL Tables ------------\n", "DBStatic", Level.FINE);
		this.conn = DBStatic.getMySQLConnection();
		this.initLoginTable();
		this.initSessionsTable();
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }
	
	/**
	 * Méthode pour initialiser la table 'login' dans la base.
	 */
	public void initLoginTable(){
		try {
			String query = "SHOW TABLES Like 'login';";
			Statement inst = conn.createStatement();
			ResultSet rs = inst.executeQuery(query);
			
			if(rs.next()){
				return ;
				
			}else{
				String query2 = "CREATE TABLE login( "
						+ "id INT NOT NULL AUTO_INCREMENT, "
						+ "login VARCHAR(20) NOT NULL, "
						+ "password VARCHAR(255) NOT NULL, "
						+ "prenom VARCHAR(20) NOT NULL, "
						+ "nom VARCHAR(20) NOT NULL, "
						+ "mail VARCHAR(20) NOT NULL, "
						+ "PRIMARY KEY ( id )); ";
				inst.executeUpdate(query2);
				ResultSet rs2 = inst.executeQuery(query);
				
				if(rs2.next())
					GeneralTools.serverLog(" Table login crée avec succes\n", this, Level.FINE);
				else
					GeneralTools.serverLog("Impossible de créer la table 'login'\n", this, Level.SEVERE);
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Méthode pour initialiser la table 'sessions' dans la base.
	 */
	public void initSessionsTable(){
		try {
			String query = "SHOW TABLES Like 'sessions';";
			Statement inst = conn.createStatement();
			ResultSet rs = inst.executeQuery(query);
			
			if(rs.next()){
				return ;
				
			}else{
				String query2 = "CREATE TABLE sessions( "
						+ "id INT NOT NULL, "
						+ "clef VARCHAR(255) NOT NULL, "
						+ "admin INT NOT NULL, "
						+ "PRIMARY KEY ( id )); ";
				inst.executeUpdate(query2);
				ResultSet rs2 = inst.executeQuery(query);
				
				if(rs2.next())
					GeneralTools.serverLog("Table sessions crée avec succes\n", this, Level.FINE);
				else
					GeneralTools.serverLog("Impossible de créer la table 'sessions'\n", this, Level.SEVERE);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

}
