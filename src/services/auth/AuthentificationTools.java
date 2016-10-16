package services.auth;
import java.sql.*;

import services.service.ServiceTools;
import database.DBStatic;

/**
 * 
 * Class qui gère l'authenification d'un utilisateur
 *
 */
public class AuthentificationTools {
	public static void createUser(String login, String password, String prenom, String nom){
		try {
			Connection conn= DBStatic.getMySQLConnection();
			String query = "INSERT INTO login VALUES (null, '"+login+"', PASSWORD('"+password+"'), '"+prenom+"','"+nom+"');";
			Statement inst = conn.createStatement();
			inst.executeUpdate(query);
			inst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}
	
	
	/**
	 * Supprime une clé de la base de données
	 * @param key
	 */
	public static void removeKey(String key){
		try{
			Connection conn= DBStatic.getMySQLConnection();
			String query =  "DELETE FROM sessions WHERE clef='"+key+"';";
			Statement inst = conn.createStatement();
			inst.executeUpdate(query);
			System.out.println("Je supprime la clef "+ key);
			
		}catch (SQLException e) {
	        System.out.println("J'arrive pas a supprimer la clé "+ key +" - SQLException");
	        e.printStackTrace();
	        
	    }
		
	}
	
	/**
	 * Vérifie le mot de passe dans le database mySQL
	 * @param login
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String login, String password){
		boolean res = false;
		try{
			Connection conn= DBStatic.getMySQLConnection();
			String query =  "SELECT 'id' FROM login WHERE login='"+login+"' and password=PASSWORD('"+ password + "');";
			Statement inst = conn.createStatement();
			inst.executeQuery(query);
			ResultSet rs = inst.getResultSet();
			if (rs.next()) {
				res = true; 
				}
			else {
				res = false; 
				}
			rs.close();
			inst.close();
			conn.close();
			return res;
		}catch(Exception e){
			System.out.println(e);
		}
		return res;
		
	}
	
	/**
	 * renvoie le id de l'utilisateur 
	 * @param login
	 * @return id
	 */
	public static int getIDUser(String login){
		
		try {
			Connection conn= DBStatic.getMySQLConnection();
			String query = "SELECT id FROM login WHERE login='" + login + "';";
			Statement inst = conn.createStatement();
			
			ResultSet rs = inst.executeQuery(query);
			if(rs.next()){
				return rs.getInt(1);
				
			}else{
				return -1;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		return -1;
		
	}
	
	/**
	 * Renvoie le login de l'utilisateur de id
	 * @param id
	 * @return login
	 */
	public static String getUserId(int id){
		String res = null;
		try {
			
			Connection conn= DBStatic.getMySQLConnection();
			String query = "SELECT login from login where id='"+id+"';";
			Statement inst = conn.createStatement();
			inst.executeQuery(query);
			ResultSet rs = inst.getResultSet();
			if(rs.next()){
				res = rs.getString(1);
			}else{
				res = null;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		return res;
		
	}
	
	/**
	 * Renvoir vrai si l'utilisateur est présent dans la base
	 * @param login
	 * @return 
	 */
	public static boolean userExist(String login){
		boolean res = true;
		try{
			Connection conn= DBStatic.getMySQLConnection();
			String query = "SELECT 'id' FROM login WHERE login='"+ login +"';";
			Statement inst = conn.createStatement();
			inst.executeQuery(query);
			ResultSet rs = inst.getResultSet();
			if(rs.next()){
				res = true;
				
			}else{
				res = false;
				
			}
			rs.close();
			inst.close();
			conn.close();
			
		}catch(Exception ex){
			System.out.print(ex);
			
		}
		
		return res;
		
	}
	
	/*
	 * Verifie si la clé est présente dans la base de données
	 */
	public static boolean getSessionKey(String key){
		boolean res = true;
		try{
			Connection conn= DBStatic.getMySQLConnection();
			String query = "SELECT clef FROM sessions WHERE clef='"+ key +"';";
			Statement inst = conn.createStatement();
			inst.executeQuery(query);
			ResultSet rs = inst.getResultSet();
			if(rs.next()){
				res = true;
				
			}else{
				res = false;
				
			}
			rs.close();
			inst.close();
			conn.close();
			
		}catch(Exception ex){
			System.out.print(ex);
			
		}
		
		return res;
	}
	
	/**
	 * Test if an user has an already opened session.
	 * @param id
	 */
	public static boolean hasKey(int id){
		boolean resu = false;
		
		Connection conn= DBStatic.getMySQLConnection();
		String query = "SELECT * FROM sessions WHERE id='"+id+"';";
	
		
		try {
			Statement inst = conn.createStatement();
			inst.executeQuery(query);
			ResultSet rs = inst.getResultSet();
			if(rs.next())
				resu = true;
		
			rs.close();
			inst.close();
			conn.close();
			
			return resu;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return resu;
		
	}
	
	/**
	 * Get the key of the user 
	 * @param id
	 */
	public static String getUserKey(int id){
		String resu;
		
		if(!hasKey(id)) return null;
		
		Connection conn= DBStatic.getMySQLConnection();
		String query = "SELECT * FROM sessions WHERE id='"+id+"';";
	
		
		try {
			Statement inst = conn.createStatement();
			inst.executeQuery(query);
			ResultSet rs = inst.getResultSet();
			
			if(rs.next())		resu = rs.getString(2);
			else				resu = null;
			
		
			rs.close();
			inst.close();
			conn.close();
			
			return resu;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	/**
	 * Insertion d'une clé de session dans la base MySql
	 * @param id
	 * @param root
	 * @param key
	 */
	private static void insterSessionKey(int id,  boolean root, String key){
		try {
			Connection conn= DBStatic.getMySQLConnection();
			String query = "INSERT INTO sessions VALUES ('"+id+"', '"+key+"', '0');";
			Statement inst = conn.createStatement();
			inst.executeUpdate(query);
			inst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Procèdure d'insertion de session
	 * @param id
	 * @param root
	 * @return
	 */
	public static String insertSession(int id,  boolean root) {
		
		try{
			Connection conn= DBStatic.getMySQLConnection();
			String newKey = null;
			boolean flag = true;
			
			while(flag){
				newKey = ServiceTools.generateRandomKey();
				Statement inst = conn.createStatement();
				String query = "SELECT * FROM sessions WHERE clef='"+ newKey +"';";				
				inst.executeQuery(query);
				ResultSet rs = inst.getResultSet();
				if(rs.next()){
					rs.close();
				
				}else{
					flag= false;
				}
				rs.close();
				inst.close();
			}
			
			insterSessionKey(id, root, newKey);
			
		
			conn.close();
			return newKey;
		}catch(Exception ex){
			System.out.print(ex);
			
		}
		return "null";
		
	}
	
}
