package jdh.test.api.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CipherUtil {
	public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {
		SecureRandom random= SecureRandom.getInstance("SHA1PRNG");
		KeyPairGenerator gen;
		gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(2048, random);
		KeyPair keyPair = gen.genKeyPair();
		return keyPair;
	}
	
	public static String encryptRSA(String plainText, PublicKey publicKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");

		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytePlain = cipher.doFinal(plainText.getBytes());
		String encrypted = Base64.getEncoder().encodeToString(bytePlain);
		return encrypted;
	}
	
	public static String encryptRSAPrivateKey(String plainText, PrivateKey privateKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] bytePlain = cipher.doFinal(plainText.getBytes());
		String encrypted = Base64.getEncoder().encodeToString(bytePlain);
		return encrypted;
	}
	
	public static String decryptRSA(String encrypted, PrivateKey privateKey)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException {
		byte[] byteEncrypted = Base64.getDecoder().decode(encrypted);
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytePlain = cipher.doFinal(byteEncrypted);
		String decrypted = new String(bytePlain, "utf-8");
		return decrypted;
	}
	
	public static String decryptRSAPublicKey(String encrypted, PublicKey publicKey)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
		byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] bytePlain = cipher.doFinal(byteEncrypted);
		String decrypted = new String(bytePlain, "utf-8");
		return decrypted;
	}
	
	public static PublicKey strToPublicKey(String base64PublicKey)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] bytePublicKey = Base64.getDecoder().decode(base64PublicKey.getBytes());
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		return publicKey;
	}
	
	public static PrivateKey strToPrivateKey(String base64PrivateKey) 
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] bytePrivateKey = Base64.getDecoder().decode(base64PrivateKey.getBytes());
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		return privateKey;
	}
	
	public static String publicKeyToStr(PublicKey publicKey) 
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException {
		byte[] bytePublicKey = publicKey.getEncoded();
		String base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
		return base64PublicKey;
	}
	
	public static String privateKeyToStr(PrivateKey privateKey) 
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException {
		byte[] bytePrivateKey = privateKey.getEncoded();
		String base64PrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);
		return base64PrivateKey;
	}
}
