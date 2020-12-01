package com.zqkj.utils.jwt;

import java.lang.reflect.Type;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.joda.time.DateTime;

import com.zqkj.utils.GsonUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by ace on 2017/9/10.
 */
public class JWTHelper {

	/**
	 * 密钥加密token
	 *
	 * @param jwtInfo
	 * @param priKeyPath
	 * @param expire
	 * @return
	 * @throws Exception
	 */
	public static String toJwt(Object obj, PrivateKey privateKey, Integer expire) throws Exception {
		String compactJws = null;
		if(expire != null)
			compactJws = Jwts.builder()
				.setSubject(GsonUtil.toJson(obj))
				//.claim(JWTInfo.NAME, jwtInfo.getName())
				.setExpiration(DateTime.now().plusSeconds(expire).toDate()) // 过其时间
				.signWith(SignatureAlgorithm.RS256, privateKey)
				.compact();
		else
			compactJws = Jwts.builder()
			.setSubject(GsonUtil.toJson(obj))
			.signWith(SignatureAlgorithm.RS256, privateKey)
			.compact();
		return compactJws;
	}

	/**
	 * 公钥解析token
	 *
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Jws<Claims> parserJwt(String jwt, PublicKey publicKey) throws Exception {
		Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(publicKey)
				.parseClaimsJws(jwt);
		return claimsJws;
	}

	/**
	 * 获取token中的用户信息
	 *
	 * @param token
	 * @param pubKeyPath
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromJwt(String jwt, PublicKey publicKey, Class<T> classOfT)throws Exception{
		Jws<Claims> claimsJws = parserJwt(jwt, publicKey);
		Claims body = claimsJws.getBody();
		T t = GsonUtil.fromJson(body.getSubject(), classOfT);
		return t;
	}
	
	public static <T> T fromJwt(String jwt, PublicKey publicKey, Type typeOfT)throws Exception{
		Jws<Claims> claimsJws = parserJwt(jwt, publicKey);
		Claims body = claimsJws.getBody();
		T t = GsonUtil.fromJson(body.getSubject(), typeOfT);
		return t;
	}
}
