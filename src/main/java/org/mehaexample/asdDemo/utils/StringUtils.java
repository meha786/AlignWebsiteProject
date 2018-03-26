package org.mehaexample.asdDemo.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class StringUtils {
	
	public static boolean isNullOrEmpty(String str){
		
		if (str == null || str.trim().length() == 0){
			return true;
		}
		
		return false;
	}

	public static String createHash(String strToHash){
//		// generate a secure random number generator
//		SecureRandom random = new SecureRandom();
//		byte salt[] = new byte[20];
//		// generate 20 random bytes
//		random.nextBytes(salt);
		
		byte[] salt = { (byte) 204, 29, (byte) 207, (byte) 217 };
		try {
			salt = "saltforhasing#239874".getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		KeySpec spec = new PBEKeySpec(strToHash.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] hash = new byte[2];
		try {
			hash = f.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Base64.Encoder enc = Base64.getEncoder();
				
		String myPasswordHash = enc.encodeToString(hash);

		return myPasswordHash;
	}
}
