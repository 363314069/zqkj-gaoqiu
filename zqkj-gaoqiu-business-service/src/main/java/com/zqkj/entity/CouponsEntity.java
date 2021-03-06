package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 优惠券表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-23 14:30:22
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="couponstab")
public class CouponsEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**流水号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**机构guid**/
	@Column
	private String organizationGuid;
	/**名称**/
	@Column
	private String name;
	/**面值金额**/
	@Column
	private Integer faceValue;
	/**满多少可以使用**/
	@Column
	private Integer moneyConform;
	/**面值折扣**/
	@Column
	private String faceValueDiscount;
	/**开始时间**/
	@Column
	private String startTime;
	/**结束时间**/
	@Column
	private String endTime;
	/**使用规则**/
	@Column
	private String useRules;
	/**价格**/
	@Column
	private Integer price;
	/**使用类型（1、可叠加   0、不可叠加）**/
	@Column
	private Integer useType;
	/**一次最多使用几张**/
	@Column
	private Integer maxUseSum;
	/**类型  1金额抵扣  2场次抵扣**/
	@Column
	private Integer type;
	/**状态**/
	@Column
	private Integer state;
	/**单位**/
	@Column
	private String unit;
	/**发布数量**/
	@Column
	private Integer seleaseNumber;
	/**领取量**/
	@Column
	private Integer receiveAmount;
	/**购买起期限**/
	@Column
	private Integer term;
	/**1、统一结束期限（用EndTime计算）2、天（Term表示天数）3、月（Term表示月数）4、年（Term表示年数）**/
	@Column
	private Integer termType;
	/**使用范围1、全品类 2、指定商品 3、指定商家全品类 4、指定商家指定商品   **/
	@Column
	private Integer scope;
	/**产品GUID，固定只能某个产品使用，针对会员验证添加的功能**/
	@Column
	private String productGuid;
	/**所属那个会员卡下，可为空**/
	@Column
	private String vipCardGuid;
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

	/**商家数组**/
	@Transient
	private String[] organizationGuids;

	/**用户优惠券分组查询：记录用户优惠券数量**/
	@Transient
	private Integer countUserCoupons;
}
