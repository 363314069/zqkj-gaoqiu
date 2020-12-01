package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 活动表
 */
@Data
public class PlaceOrderEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**编号**/
	@Column
	private String id;
	/**guid**/
	@Column
	private String guid;
	/**活动名称**/
	@Column
	private String name;
	/**联系电话**/
	@Column
	private String phone;
	/**'机构guid'**/
	@Column
	private String organizationGuid;
	/**'内容'**/
	@Column
	private String content;
	/**'图片'**/
	@Column
	private String img;
	/**'价格'**/
	@Column
	private Integer price;
	/**'折扣'**/
	@Column
	private String discount;
	/**开始时间**/
	@Column
	private String startTime;
	/**结束时间**/
	@Column
	private String endTime;
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
