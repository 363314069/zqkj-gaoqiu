package com.zqkj.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="roletab")
public class RoleEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**机构编号**/
	@Column
	private String organizationGuid;
	/**名称**/
	@Column
	private String name;
	/**角色类型**/
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
