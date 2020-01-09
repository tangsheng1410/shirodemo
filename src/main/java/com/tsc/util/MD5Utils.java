package com.tsc.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Utils {

	public static String encrty(String username, String password) {
		String newPassword = new SimpleHash("MD5", password, ByteSource.Util.bytes(password + username), 2).toHex();
		return newPassword;

	}

	public static void main(String[] args) {
		System.out.println(encrty("test", "123456"));
	}

}
