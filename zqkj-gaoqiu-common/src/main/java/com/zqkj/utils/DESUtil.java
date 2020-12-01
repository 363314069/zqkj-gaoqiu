package com.zqkj.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtil {
	private static Map<String, DESUtil> map = new HashMap<String, DESUtil>();
	//KeyGenerator 提供对称密钥生成器的功能，支持各种算法
	//private KeyGenerator keygen;
	//SecretKey 负责保存对称密钥
	private SecretKey deskey;
	// Cipher负责完成加密或解密工作
	private Cipher c;
	// 该字节数组负责保存加密的结果
	private byte[] cipherByte;

	public DESUtil(String strKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		// 生成Cipher对象,指定其支持的DES算法
		DESKeySpec desKey = new DESKeySpec(strKey.getBytes());
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		deskey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成加密操作
		c = Cipher.getInstance("DES");
	}

	public static DESUtil getInstance(String strKey) {
		DESUtil des = map.get(strKey);
		try {
			if (des == null) {
				des = new DESUtil(strKey);
				map.put(strKey, des);
			}
		} catch (Exception e) {
		}
		return des;
	}
			
	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Encrytor(byte[] src) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		cipherByte = c.doFinal(src);
		return cipherByte;
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String Encrytor(String str) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte src[] = str.getBytes(Content.SYSTEM_CODING);
		cipherByte = c.doFinal(src);
		return encryptBASE64(cipherByte);
	}
	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String EncrytorHex(String str) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte src[] = str.getBytes(Content.SYSTEM_CODING);
		cipherByte = c.doFinal(src);
		return byteToHex(cipherByte);
	}
	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Decryptor(byte[] buff) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		c.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = c.doFinal(buff);
		return cipherByte;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String Decryptor(String buff) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, IOException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		buff = buff.replaceAll("[\r\n]", "").trim();
		c.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = c.doFinal(decryptBASE64(buff));
		return new String(cipherByte, Content.SYSTEM_CODING);
	}
	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String DecryptorHex(String buff) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, IOException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		buff = buff.replaceAll("[\r\n]", "").trim();
		c.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = c.doFinal(hexToByte(buff));
		return new String(cipherByte, Content.SYSTEM_CODING);
	}
	/**
	 * BASE64解密 http://www.bt285.cn http://www.5a520.cn
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws IOException {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
	// 把字符串转换为进制字符串
	// 如：a变成（即进制的）；abc变成
	public String byteToHex(byte[] b) {
		String str = "";
		char hexes[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',	'b', 'c', 'd', 'e', 'f' };

		for (int i = 0; i < (b.length); i++) {
			str = str + hexes[(b[i] >>> 4) & 0xf] + hexes[b[i] & 0xf];
		}
		return str;
	}

	// 16进制字符串转换为字符串
	// 如：（即进制的）变成a；变成abc
	public byte[] hexToByte(String s) {
		byte b[] = new byte[s.length()/2];
		for (int i = 0; i < s.length(); i += 2) {
			b[i/2] = (byte)Integer.parseInt(s.substring(i, i + 2), 16);
			
		}
		return b;
	}
	/**
	 * @param args
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 */
	public static void main(String[] args) throws Exception {
		String msg = "aaaabbbbe"; 
		//7f79121d13d705e5141204a856ff1c7238eb9f11f91dfb7d
		//7f79121d13d705e53ca60745d0d2fa50
		DESUtil des1 = new DESUtil("aaaaddd22d");
		byte src[] = msg.getBytes(Content.SYSTEM_CODING);
		System.out.println("====>" + src.length);
		src = des1.Encrytor(src);
		System.out.println("====>" + src.length);
		System.out.println(des1.EncrytorHex(msg));
	}
}