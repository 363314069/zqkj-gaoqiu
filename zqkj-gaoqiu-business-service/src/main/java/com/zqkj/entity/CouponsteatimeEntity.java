package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-04-17 17:31:53
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="couponsteatimetab")
public class CouponsteatimeEntity extends BaseEntity implements Serializable {
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
	/**球场GUID**/
	@Column
	private String reservationGuid;
	/**厂地GUID**/
	@Column
	private String introductionGuid;
	/**商品名称**/
	@Column
	private String name;
	/**联系电话**/
	@Column
	private String phone;
	/**内容**/
	@Column
	private String content;
	/**图片**/
	@Column
	private String img;
	/**原价**/
	@Column
	private Integer costPrice;
	/**当前价格**/
	@Column
	private Integer price;
	/**折扣**/
	@Column
	private String discount;
	/**预定天数**/
	@Column
	private Integer days;
	/**时间段json**/
	@Column
	private String timeJson;
	/**类型**/
	@Column
	private Integer type;
	/**状态**/
	@Column
	private Integer state;
	/**是否可以使用优惠券、积分等等**/
	@Column
	private Integer preferential;
	/**回调url**/
	@Column
	private String callbackUrl;
	/**开始时间**/
	@Column
	private String startTime;
	/**结束时间**/
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

	/**优惠券GUID数组**/
	@Transient
	private String[] preferentialList;
}
