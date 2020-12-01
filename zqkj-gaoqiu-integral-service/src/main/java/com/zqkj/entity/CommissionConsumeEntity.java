package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 佣金消费表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="commissionconsumetab")
public class CommissionConsumeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**guid**/
	@Column
	private String guid;
	/**来源GUID（订单）**/
	@Column
	private String sourceGuid;
	/**佣金**/
	@Column
	private Integer integral;
	/**类型（1订单，2提现）**/
	@Column
	private Integer type;
	/**状态（1正常 0删除）**/
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
