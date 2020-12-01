package com.zqkj.utils.jwt;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by ace on 2017/9/10.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class JWTInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**WEB端**/
	public static final int APP_TYPE_WEB = 1;
	/**WAP端**/
	public static final int APP_TYPE_WAP  = 2;
	/**安卓端**/
	public static final int APP_TYPE_ANDROID  = 3;
	/**IOS端**/
	public static final int APP_TYPE_IOS  = 4;
	
	/*应用程序类型*/
	private Integer appType;
	/**id**/
	private Integer id;
	/**guid**/
	private String guid;
	/**帐号**/
	private String loginName;
	/**姓名**/
	private String userName;
	/**机构guid**/
	private String organizationGuid;
	/**登录Ip**/
	private String loginIp;
	/**类型**/
	private Integer type;
	/**关联guid**/
	private String reGuid;
	/**备用字段**/
	private String  extStr;
	/**用户角色**/
	private List<String> listRole;
	/**openid**/
	private String openid;
}
