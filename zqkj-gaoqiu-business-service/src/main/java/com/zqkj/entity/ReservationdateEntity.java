package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.zqkj.utils.annotation.SqlFieldIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 每天订场设置
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:09:36
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="reservationdatetab")
@SqlFieldIgnore("content")
public class ReservationdateEntity extends BaseEntity implements Serializable {
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
	/**订场GUID**/
	@Column
	private String reservationGuid;
	/**场地信息**/
	@Column
	private String introductionGuid;
	/**名称**/
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
	/**日期**/
	@Column
	private String date;
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

	/**起始时间**/
	@Transient
	private String startTime;
	/**结束时间**/
	@Transient
	private String endTime;
}
