package services.service;
import java.util.Random;

/**
 * Des outils pour les sevices
 * 
 *
 */
public class ServiceTools {
	public static String generateRandomKey(){
		String key = "";
		Random r = new Random();

		String alphabet = "123456789azertyuiopqsdfghjklmwbxncv";
		for (int i = 0; i < 20; i++) {
		    key = key + alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return key;
	}
}
