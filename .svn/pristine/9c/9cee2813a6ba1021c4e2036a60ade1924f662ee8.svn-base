package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zqkj.utils.annotation.SqlFieldIgnore;

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
@Table(name="newstab")
@SqlFieldIgnore("content")
public class NewsEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**主键ID**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**栏目节点**/
	@Column
	private String newsSortGuid;
	/**机构GUID**/
	@Column
	private String organizationGuid;
	/**标题**/
	@Column
	private String title;
	/**作者**/
	@Column
	private String author;
	/**Value**/
	@Column
	private String content;
	/**发布时间**/
	@Column
	private String releaseTime;
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
