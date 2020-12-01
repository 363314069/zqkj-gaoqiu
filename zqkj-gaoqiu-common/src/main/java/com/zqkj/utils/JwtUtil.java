package com.zqkj.utils;

import java.lang.reflect.Type;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zqkj.utils.jwt.JWTHelper;
import com.zqkj.utils.jwt.JWTInfo;
import com.zqkj.utils.jwt.RsaKeyHelper;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class JwtUtil {

	@Value("${jwt.expire:#{864000}}")
	private int expire;
	@Value("${jwt.pri-key.path:#{key/private.key}}")
	private String priKeyPath;
	@Value("${jwt.pub-key.path:#{key/public.key}}")
	private String pubKeyPath;

	private static PrivateKey privateKey = null;
	private static PublicKey publicKey = null;
	private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

	@PostConstruct
	public void init() throws Exception {
		privateKey = rsaKeyHelper.getPrivateKey(priKeyPath);	//私钥
		publicKey = rsaKeyHelper.getPublicKey(pubKeyPath);		//公钥
	}
	public void init(String priKeyPath, String pubKeyPath) throws Exception {
		privateKey = rsaKeyHelper.getPrivateKey(priKeyPath);	//私钥
		publicKey = rsaKeyHelper.getPublicKey(pubKeyPath);		//公钥
	}
	public String toJwt(Object obj) throws Exception {
		return toJwt(obj, expire);
	}
	
	public String toJwt(Object obj, Integer expire) throws Exception {
		return JWTHelper.toJwt(obj, privateKey, expire);
	}
	
	public <T> T fromJwt(String jwt, Class<T> classOfT) throws Exception {
		jwt = jwt.replaceAll("\"", "");
		return JWTHelper.fromJwt(jwt, publicKey, classOfT);
	}
	
	public <T> T fromJwt(String jwt, Type typeOfT) throws Exception {
		jwt = jwt.replaceAll("\"", "");
		return JWTHelper.fromJwt(jwt, publicKey, typeOfT);
	}
	public static void main(String[] args) {
		String priKeyPath = "key/private.key";
		String pubKeyPath = "key/public.key";
		JwtUtil jwtUtil = new JwtUtil();
		try {
			jwtUtil.init(priKeyPath, pubKeyPath);
			String jwt = jwtUtil.toJwt("sssssssssssss", 6000);
			System.err.println(jwt);
			jwt = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOjEsXCJndWlkXCI6XCJkNDA1M2JmZi1kZGE1LTExZTgtOTViZC0wMDIxY2NjNGVlZThcIixcImxvZ2luTmFtZVwiOlwiYWRtaW5cIixcInVzZXJOYW1lXCI6XCLnrqHnkIblkZhcIixcIm9yZ2FuaXphdGlvbkd1aWRcIjpcImQ0MDUzYmZmLWRkYTUtMTFlOC05NWJkLTAwMjFjY2M0ZWVlOFwiLFwibG9naW5JcFwiOlwiMTkyLjE2OC4wLjc0XCJ9IiwiZXhwIjoxNTUzNzA0ODkyfQ.jc6LhPcdCsMlWYIOqi3eBnV-4hqhJFjX5JABadiZGdw2EBqkjFwfx8pM9fllbnAFC_HGYEGyxHdYgpmh6Vc8kB654VxRBwtR_SsUVZC0Nl2-S53M9l7GXBq1PWVaFsQGKY8qZC-vRoryvU8afG1v75LWtiBPTv5SN-A8rHlIK0k";
			System.err.println(jwtUtil.fromJwt(jwt, JWTInfo.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
