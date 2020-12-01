package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="organizationtab")
public class OrganizationEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**省份GUID**/
	@Column
	private String provinceGuid;
	/**机构名称**/
	@Column
	private String name;
	/**服务器地址**/
	@Column
	private String serverUrl;
	/**Token**/
	@Column
	private String token;
	/**登录api**/
	@Column
	private String loginApi;
	/**回调api**/
	@Column
	private String callbackApi;
	/**子站点**/
	@Column
	private String subSite;
	/**机构地址**/
	@Column
	private String address;
	/**联系人**/
	@Column
	private String contacts;
	/**联系电话**/
	@Column
	private String phone;
	/**邮件地址**/
	@Column
	private String mail;
	/**微信号**/
	@Column
	private String weChat;
	/**QQ号**/
	@Column
	private String qQCode;
	/**logo文件**/
	@Column
	private String logoFile;
	/**显示图标**/
	@Column
	private String showTitle;
	/**机构类型(1为本局，2为关联局，3为厂商)**/
	@Column
	private Integer type;
	/**状态**/
	@Column
	private Integer state;
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
}
