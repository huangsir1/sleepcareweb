/**
 * 
 */
package taiyi.web.utils;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;
/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.utils
 *
 * 2016年6月2日
 */


public class EncryptUtils{
	
	public static void main(String[] args) {
		System.err.println(encryptOriginalTaiirPassword("guest"));
		//CBD5516C394B4BAC0176A7BFFE1FCBBC:2F361F1B

		System.out.println(checkOriginalTaiirPassword("admin","CBD5516C394B4BAC0176A7BFFE1FCBBC:2F361F1B"));
		
	}
	
	
	public final static boolean checkOriginalTaiirPassword(String originalPassword,String passwordInDB) {
		return encryptOriginalTaiirPassword(originalPassword,passwordInDB.split(":")[1]).equals(passwordInDB);
	}
	


	public final static String encryptOriginalTaiirPassword(String originalPassword) {
		return encryptMD5TaiirPassword(encryptMD5(originalPassword));
	}
	
	public static String encryptOriginalTaiirPassword(String originalPassword, String salt) {
		return encryptMD5TaiirPassword(encryptMD5(originalPassword),salt);
	}
	
	public final static boolean checkMD5TaiirPassword(String originalMD5Password,String passwordInDB) {
		return encryptMD5TaiirPassword(originalMD5Password,passwordInDB.split(":")[1]).equals(passwordInDB);
	}
	public final static String encryptMD5TaiirPassword(String originalMD5Password,String salt) {
		String encryptPassword = encryptMD5(encryptMD5(originalMD5Password+salt)+salt) + ":" + salt;
		return encryptPassword;
	}
	
	public final static String encryptMD5TaiirPassword(String originalMD5Password) {
		String salt = UUID.randomUUID().toString().split("-")[0].toUpperCase();
		String encryptPassword = encryptMD5(encryptMD5(originalMD5Password+salt)+salt) + ":" + salt;
		return encryptPassword;
	}
	/**
	 * Encrypt string using MD5 algorithm
	 */
	public final static String encryptMD5(String source) {
		if (source == null) {
			source = "";
		}
		String result = "";
		try {
			result = encrypt(source, "MD5");
		} catch (NoSuchAlgorithmException ex) {
			// this should never happen
			throw new RuntimeException(ex);
		}
		return result;
	}

	/**
	 * Encrypt string using SHA algorithm
	 */
	public final static String encryptSHA(String source) {
		if (source == null) {
			source = "";
		}
		String result = "";
		try {
			result = encrypt(source, "SHA");
		} catch (NoSuchAlgorithmException ex) {
			// this should never happen
			throw new RuntimeException(ex);
		}
		return result;
	}

	/**
	 * Encrypt string
	 */
	private final static String encrypt(String source, String algorithm) throws NoSuchAlgorithmException {
		byte[] resByteArray = encrypt(source.getBytes(), algorithm);
		return toHexString(resByteArray);
	}

	/**
	 * Encrypt byte array.
	 */
	private final static byte[] encrypt(byte[] source, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.reset();
		md.update(source);
		return md.digest();
	}

	/**
	 * Get hex string from byte array
	 */
	private final static String toHexString(byte[] res) {
		StringBuffer sb = new StringBuffer(res.length << 1);
		for (int i = 0; i < res.length; i++) {
			String digit = Integer.toHexString(0xFF & res[i]);
			if (digit.length() == 1) {
				digit = '0' + digit;
			}
			sb.append(digit);
		}
		return sb.toString().toUpperCase();
	}
}
