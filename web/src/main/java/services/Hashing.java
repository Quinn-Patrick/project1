package services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashing {
	public static String hash(String in) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		KeySpec spec = new PBEKeySpec(in.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		StringBuilder hashString = new StringBuilder("");
		
		byte[] hash = factory.generateSecret(spec).getEncoded();

		
		for(byte b : hash) {
			//System.out.println(b);
			hashString.append(b);
		}
		
		return hashString.toString();
	}

}
