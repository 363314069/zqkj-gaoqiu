package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 会员验证表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-10-12 15:32:13
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="membersverifytab")
public class MembersverifyEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**guid**/
	@Column
	private String guid;
	/**名称**/
	@Column
	private String name;
	/**联系电话**/
	@Column
	private String phone;
	/**身份证号码**/
	@Column
	private String idCard;
	/**绑定GUID**/
	@Column
	private String bindGuid;
	/**用户类型**/
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
	/**酒店类型**/
	@Transient
	private Integer hotelType;
}
