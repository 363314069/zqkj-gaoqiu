package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 会员卡
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="viptab")
public class VipEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**来原guid  产品guid**/
	@Column
	private String sourceGuid;
	/**名称**/
	@Column
	private String name;
	/**卡号**/
	@Column
	private Long areaCode;
	/**卡号**/
	@Column
	private Integer code;
	/**用户Guid**/
	@Column
	private String userGuid;
	/**联系人**/
	@Column
	private String contacts;
	/**联系电话**/
	@Column
	private String phone;
	/**地址**/
	@Column
	private String address;
	/**初始金额**/
	@Column
	private Integer initMoney;
	/**金额**/
	@Column
	private Integer money;
	/**金钱类型**/
	@Column
	private Integer moneyType;
	/**类型**/
	@Column
	private Integer type;
	/**状态**/
	@Column
	private Integer state;
	/**到期时间**/
	@Column
	private String endTime;
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

	/**订单guid**/
	@Transient
	private String orderGuid;
}
