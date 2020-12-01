package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 预定表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="reservetab")
public class ReserveEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**guid**/
	@Column
	private String guid;
	/**'机构guid'**/
	@Column
	private String organizationGuid;
	/**用户GUID**/
	@Column
	private String userGuid;
	/**用户名称**/
	@Column
	private String userName;
	/**联系电话**/
	@Column
	private String userPhone;
	/**'订单GUID'**/
	@Column
	private String orderGuid;
	/**'商品GUID'**/
	@Column
	private String activityGuid;
	/**'商品名称'**/
	@Column
	private String activityName;
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
