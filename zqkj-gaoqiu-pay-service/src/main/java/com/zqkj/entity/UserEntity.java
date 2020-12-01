package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 用户表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Data
public class UserEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**应用程序ID**/
	@Column
	private Integer appId;
	/**openid**/
	@Column
	private String openid;
	/**登录名称**/
	@Column
	private String loginName;
	/**登录密码**/
	@Column
	private String loginPassword;
	/**机构GUID**/
	@Column
	private String organizationGuid;
	/**分组GUID**/
	@Column
	private String userGroupGuid;
	/**姓名**/
	@Column
	private String name;
	/**Token**/
	@Column
	private String token;
	/**机构地址**/
	@Column
	private String address;
	/**联系人**/
	@Column
	private String contacts;
	/**联系电话**/
	@Column
	private String phone;
	/**初始电话**/
	@Column
	private String initPhone;
	/**邮件地址**/
	@Column
	private String mail;
	/**微信号**/
	@Column
	private String weChat;
	/**QQ号**/
	@Column
	private String qqCode;
	/**用户类型**/
	@Column
	private Integer type;
	/**状态**/
	@Column
	private Integer state;
	/**头像**/
	@Column
	private String image;
	/**差点**/
	@Column
	private String gap;
	/**邀请数量**/
	@Column
	private Integer inviteSum;
	/**备注**/
	@Column
	private String remark;
	/**创建者**/
	@Column
	private String creator;
	/**创建时间**/
	@Column
	private String createTime;
	/**审核用户**/
	@Column
	private String checkUser;
	/**审核时间**/
	@Column
	private String checkTime;
	/**扩展整型**/
	@Column
	private Integer extInt;
	/**扩展字符**/
	@Column
	private String extStr;
	
	/********************************* 扩展字符 *************************/
	/**登录IP**/
	@Transient
	private String loginIp;
	/**验证码**/
	@Transient
	private String code;
	/**分组名称**/
	@Transient
	private String groupName;
	/**机构名称**/
	@Transient
	private String organizationName;
	/**权限**/
	@Transient
	private int[] auth;
	/**权限唯一标识**/
	@Transient
	private long authId;
	/**当前时间**/
	@Transient
	private long time;
	/**登录密码**/
	@Transient
	private String loginPasswordTwo;
	@Transient
	private String datades;
	@Transient
	private List<String> listRole;
	@Transient
	private int loginType;
}
