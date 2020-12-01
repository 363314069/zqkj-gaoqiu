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
 * @date 2020-07-07 15:39:42
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="activityextendtab")
public class ActivityextendEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**活动GUID**/
	@Column
	private String activitytabGUID;
	/**关联guid**/
	@Column
	private String relationGuid;
	/**单位**/
	@Column
	private Integer unit;
	/**类型 1活动**/
	@Column
	private Integer type;
	/**状态 1启用 0禁用**/
	@Column
	private Integer state;
	/**扩展整型**/
	@Column
	private Integer extInt;
	/**扩展字符**/
	@Column
	private String extStr;
}
