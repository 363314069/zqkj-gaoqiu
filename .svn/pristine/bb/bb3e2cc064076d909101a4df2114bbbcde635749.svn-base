package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 支付流水表
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="paymentrecordtab")
public class PayMentrecordEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**组织机构GUID**/
	@Column
	private String organizationGuid;
	/**微信订单号**/
	@Column
	private String transactionId;
	/**用户GUID**/
	@Column
	private String userGuid;
	/**订单guid**/
	@Column
	private String orderGuid;
	/**系统生成订单号**/
	@Column
	private String orderNumber;
	/**支付金额**/
	@Column
	private Integer payMount;
	/**支付方式  0：微信支付  1：支付宝支付   **/
	@Column
	private Integer type;
	/**0：消费 1：退款  2：充值 3：提现**/
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
