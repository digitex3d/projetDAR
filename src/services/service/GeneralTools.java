package services.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Class qui contient des methodes static qui vont
 * améliorer la lisibilité et l'écriture du code.
 */
public class GeneralTools {
	private static final Logger LOGGER = Logger.getLogger( GeneralTools.class.getName() );
	
	/*
	 * Verifie les paramètres de la reponse
	 */
	public static boolean paramVerif(ArrayList<String> params){
		for (String p : params)
			 if( p ==null || p.equals("null") ) return false;
					 
		return true;
		
	}
	
	/*
	 * Affiche un message de log avec la date et le nom de la class
	 */
	public static void serverLog(String msg, Object errorClass, Level lvl){
		String className = errorClass.getClass().getName();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		LOGGER.log( lvl, dateFormat.format(today) + ": " + msg + " Class: " + className );
		
	}
	
	/*
	 * Affiche un message de log avec la date et le nom de la class
	 */
	public static void serverLog(String msg, Object errorClass){
		String className = errorClass.getClass().getName();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		LOGGER.log( Level.FINE, dateFormat.format(today) + ": " + msg + " Class: " + className );
		
	}
	
	/*
	 * Affiche un message de log avec la date
	 */
	public static void serverLog(String msg){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date today = new Date();
		LOGGER.log( Level.FINE, dateFormat.format(today) + ": " + msg );
		
	}
	
	
}
