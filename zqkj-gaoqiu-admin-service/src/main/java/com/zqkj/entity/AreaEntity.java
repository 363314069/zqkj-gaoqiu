package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 地区表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="areatab")
public class AreaEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**父编号**/
	@Column
	private String parentId;
	/**地区名称**/
	@Column
	private String name;
	/**地区名称**/
	@Column
	private String nameEn;
	/**地区名称缩写**/
	@Column
	private String abbreviation;
	/**地区代码**/
	@Column
	private Integer code;
	/**国家代码**/
	@Column
	private Integer countryCode;
	/**索引ID**/
	@Column
	private String indexId;
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
