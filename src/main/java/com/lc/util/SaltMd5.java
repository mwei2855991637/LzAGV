package com.lc.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/** 
* @author MWEI E-mail: 574185505@qq.com
* @version 2019年7月25日 下午5:40:36 
* Class Explain: 盐值加密工具
*/
public class SaltMd5 {
	public static final String md5(String password, String salt){
	    //加密方式
	    String hashAlgorithmName = "MD5";
	    //盐：为了即使相同的密码不同的盐加密后的结果也不同
	    ByteSource byteSalt = ByteSource.Util.bytes(salt);
	    //密码
	    Object source = password;
	    //加密次数
	    int hashIterations = 1024;
	    SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
	    return result.toString();
	}
	public static void main(String[] args) {
	    String password = md5("admin", "admin");
	    System.out.println(password);
	}
}
