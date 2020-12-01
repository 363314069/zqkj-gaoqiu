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
 * @date 2019-07-05 16:45:51
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="newssorttab")
public class NewsSortEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键ID**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**父节点**/
	@Column
	private Integer parentId;
	/**GUID**/
	@Column
	private String guid;
	/**机构GUID**/
	@Column
	private String organizationGuid;
	/**名称**/
	@Column
	private String name;
	/**类型**/
	@Column
	private Integer type;
	/**分类级别**/
	@Column
	private Integer level;
	/**索引ID**/
	@Column
	private String indexId;
	/**为父节点**/
	@Column
	private Boolean isParent;
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
