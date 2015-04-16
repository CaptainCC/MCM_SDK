package cn.mcm.cryption;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import android.util.Log;

public class AESHelper {
	public static final String TAG = AESHelper.class.getSimpleName();

	Runtime mRuntime = Runtime.getRuntime();

	@SuppressWarnings("resource")
	public boolean AESCipher(int cipherMode, String sourceFilePath,
			String targetFilePath, String seed) {
		boolean result = false;
		FileChannel sourceFC = null;
		FileChannel targetFC = null;

		try {

			if (cipherMode != Cipher.ENCRYPT_MODE
					&& cipherMode != Cipher.DECRYPT_MODE) {
				Log.d(TAG,
						"Operation mode error, should be encrypt or decrypt!");
				return false;
			}

			Cipher mCipher = Cipher.getInstance("AES/CFB/NoPadding");

			byte[] rawkey = getRawKey(seed.getBytes());
			File sourceFile = new File(sourceFilePath);
			File targetFile = new File(targetFilePath);

			sourceFC = new RandomAccessFile(sourceFile, "r").getChannel();
			targetFC = new RandomAccessFile(targetFile, "rw").getChannel();

			SecretKeySpec secretKey = new SecretKeySpec(rawkey, "AES");

			mCipher.init(cipherMode, secretKey, new IvParameterSpec(
					new byte[mCipher.getBlockSize()]));

			ByteBuffer byteData = ByteBuffer.allocate(1024);
			while (sourceFC.read(byteData) != -1) {
				// ͨ��ͨ����д������С�
				// ��������׼��Ϊ���ݴ���״̬
				byteData.flip();

				byte[] byteList = new byte[byteData.remaining()];
				byteData.get(byteList, 0, byteList.length);
//�˴�������ʹ��������ܽ��ܻ�ʧ�ܣ���Ϊ��byteData�ﲻ��1024��ʱ�����ܷ�ʽ��ͬ�Կհ��ֽڵĴ���Ҳ����ͬ���Ӷ����³ɹ���ʧ�ܡ� 
				byte[] bytes = mCipher.doFinal(byteList);
				targetFC.write(ByteBuffer.wrap(bytes));
				byteData.clear();
			}

			result = true;
		} catch (IOException | NoSuchAlgorithmException | InvalidKeyException
				| InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException
				| NoSuchPaddingException e) {
			Log.d(TAG, e.getMessage());

		} finally {
			try {
				if (sourceFC != null) {
					sourceFC.close();
				}
				if (targetFC != null) {
					targetFC.close();
				}
			} catch (IOException e) {
				Log.d(TAG, e.getMessage());
			}
		}

		return result;
	}

	/**
	 * ���ܺ���ַ���
	 * 
	 * @param seed
	 * @param clearText
	 * @return
	 */
	public String encrypt(String seed, String source) {
		// Log.d(TAG, "����ǰ��seed=" + seed + ",����Ϊ:" + clearText);
		byte[] result = null;
		try {
			byte[] rawkey = getRawKey(seed.getBytes());
			result = encrypt(rawkey, source.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String content = toHex(result);
		return content;

	}

	/**
	 * ���ܺ���ַ���
	 * 
	 * @param seed
	 * @param encrypted
	 * @return
	 */
	public String decrypt(String seed, String encrypted) {
		byte[] rawKey;
		try {
			rawKey = getRawKey(seed.getBytes());
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(rawKey, enc);
			String coentn = new String(result);
			return coentn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ʹ��һ����ȫ�������������һ���ܳ�,�ܳ׼���ʹ�õ�
	 * 
	 * @param seed
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private byte[] getRawKey(byte[] seed) throws NoSuchAlgorithmException {
		// ���һ�������������Ĳ���ΪĬ�Ϸ�ʽ��
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		// ����һ������,һ�����û��趨������
		sr.setSeed(seed);
		// ���һ��key��������AES����ģʽ��
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		// �����ܳ׳���128λ
		keyGen.init(128, sr);
		// ����ܳ�
		SecretKey key = keyGen.generateKey();
		// �����ܳ׵�byte���鹩�ӽ���ʹ��
		byte[] raw = key.getEncoded();
		return raw;
	}

	/**
	 * �����Կ���ɼ��ܺ������
	 * 
	 * @param raw
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private byte[] encrypt(byte[] raw, byte[] input) throws Exception {
		// ������һ�����ɵ��ܳ�ָ��һ���ܳ�
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		// Cipher cipher = Cipher.getInstance("AES");
		// �����㷨������ģʽ����䷽ʽ�����ֻ�ָ��������
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// ��ʼ��ģʽΪ����ģʽ����ָ���ܳ�
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
				new byte[cipher.getBlockSize()]));
		byte[] encrypted = cipher.doFinal(input);
		return encrypted;
	}

	/**
	 * ������Կ�����Ѿ����ܵ�����
	 * 
	 * @param raw
	 * @param encrypted
	 * @return
	 * @throws Exception
	 */
	private byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
				new byte[cipher.getBlockSize()]));
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	public String toHex(byte[] buf) {
		if (buf == null || buf.length <= 0)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private void appendHex(StringBuffer sb, byte b) {
		final String HEX = "0123456789ABCDEF";
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}