package cn.mcm.cryption;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * @author dlc
 * key长度：128（192、256对android平台貌似不能用）
 * 模式ECB是安全性最差的，到时候可以试试CBC什么的
 */
public class Cryption  {
	public static String encrypt(String data,String key) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			byte[] encrypted = cipher.doFinal(data.getBytes());
			return Base64.encodeToString(encrypted, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String desEncrypt(String data, String key) throws Exception {
		try {
			byte[] encrypted1 = Base64.decode(data.getBytes(), Base64.DEFAULT);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, keyspec);
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static byte[] getRawKey(byte[] seed) throws Exception {     
        KeyGenerator kgen = KeyGenerator.getInstance("AES");   
        // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法  
         SecureRandom sr = null;  
       if (android.os.Build.VERSION.SDK_INT >=  17) {  
         sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");  
       } else {  
         sr = SecureRandom.getInstance("SHA1PRNG");  
       }   
        sr.setSeed(seed);     
        kgen.init(128, sr); //256 bits or 128 bits,192bits  
        SecretKey skey = kgen.generateKey();     
        byte[] raw = skey.getEncoded();     
        return raw;     
    }   
}

