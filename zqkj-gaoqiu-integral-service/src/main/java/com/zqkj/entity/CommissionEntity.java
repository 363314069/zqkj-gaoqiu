package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 佣金表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="commissiontab")
public class CommissionEntity extends BaseEntity implements Serializable {
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
	/**类型**/
	@Column
	private Integer type;
	/**状态 0可提现  1审核中  2已提现**/
	@Column
	private Integer state;
	/**提现订单guid**/
	@Column
	private String cashOrderGuid;
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

	/**产品GUID**/
	@Transient
	private String productGuid;
}
