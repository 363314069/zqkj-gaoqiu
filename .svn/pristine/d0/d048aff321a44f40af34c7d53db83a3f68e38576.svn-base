package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2020-07-07 14:43:39
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="teamtab")
public class TeamEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**机构GUID**/
	@Column
	private String organizationGuid;
	/**队名**/
	@Column
	private String name;
	/**队标**/
	@Column
	private String logo;
	/**积分**/
	@Column
	private Integer integral;
	/**用户guid**/
	@Column
	private String userGuid;
	/**类型**/
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
