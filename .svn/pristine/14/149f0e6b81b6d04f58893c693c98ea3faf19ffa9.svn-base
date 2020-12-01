package com.zqkj.utils;

public class HexUtil {
	private static final char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	private static final int[] DEC = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13,
			14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, 10, 11, 12, 13, 14, 15 };

	public static String toHex(byte[] b) {
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < b.length; i++) {
			buf.append(hex[(b[i] & 0xf0) >>> 4]);
			buf.append(hex[b[i] & 0xf]);
		}
		return buf.toString();
	}

	public static byte[] toBinary(String hex) {
		char chars[] = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) ((DEC[chars[i << 1] - '0'] << 4) ^ (DEC[chars[i << 1 ^ 1] - '0']));
		}
		return b;
	}

	public static void main(String[] args) {
		String str = "eyJhbGciOiJSUzI1NiJ9";
		String hexstr = toBinary(str).toString();
		System.out.println(hexstr);
		//hexstr = toHex(b);
		System.out.println(hexstr);
	}
}
