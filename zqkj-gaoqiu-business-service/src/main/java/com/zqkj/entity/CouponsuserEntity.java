package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 优惠券用户关联
 * @author zqkj
 * @email root@qq.com
 * @date 2020-03-23 14:30:22
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="couponsusertab")
public class CouponsuserEntity extends BaseEntity implements Serializable {
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
	/**用户GUID**/
	@Column
	private String userGuid;
	/**优惠卷GUID**/
	@Column
	private String couponsGuid;
	/**是否已使用 0未使用  1已使用**/
	@Column
	private Integer ifUse;
	/**订单GUID**/
	@Column
	private String orderGuid;
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
	/**审核用户
            **/
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

	/**优惠券**/
	@Transient
	private CouponsEntity couponsEntity;

	/**所属那个会员卡下，查询条件**/
	@Transient
	private String vipCardGuid;

	/**数量**/
	@Transient
	private Integer sum;
}
