package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分组
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-06-18 11:15:05
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="usergrouptab")
public class UserGroupEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**父编号**/
	@Column
	private Integer parentId;
	/**GUID**/
	@Column
	private String guid;
	/**机构编号**/
	@Column
	private String organizationGuid;
	/**名称**/
	@Column
	private String name;
	/**图标**/
	@Column
	private String icon;
	/**排序**/
	@Column
	private Integer sort;
	/**级别**/
	@Column
	private Integer level;
	/**是否父节点**/
	@Column
	private Integer isParent;
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
