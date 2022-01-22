package jdh.test.api.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class AES256 {

	public static String getKey() throws Exception{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		generator.init(128, random);
		Key key = generator.generateKey();
		String keyStr = Base64.encodeBase64String(key.getEncoded());
		return keyStr;
	}
	
	public static String encrypt(String key, String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
		String iv = key.substring(0, 16);
		byte[] textBytes = str.getBytes(StandardCharsets.UTF_8.name());
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8.name()), "AES");
		Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		byte[] encrypted = cipher.doFinal(textBytes);
		String enStr = new String(Base64.encodeBase64(encrypted));
		return enStr;
	}

	public static String decrypt(String key, String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
		String iv = key.substring(0, 16);
		byte[] textBytes = Base64.decodeBase64(str.getBytes());
		// byte[] textBytes = str.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8.name()), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
		return new String(cipher.doFinal(textBytes), StandardCharsets.UTF_8.name());
	}
}