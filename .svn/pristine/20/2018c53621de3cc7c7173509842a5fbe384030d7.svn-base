package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.zqkj.utils.annotation.SqlFieldIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 场地信息
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:02:40
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="introductiontab")
@SqlFieldIgnore("content")
public class IntroductionEntity extends BaseEntity implements Serializable {
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
	/**名称**/
	@Column
	private String name;
	/**前缀**/
	@Column
	private String prefix;
	/**球洞数**/
	@Column
	private Integer numberHoles;
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
	/**周期设置  1重复  2一次  3默认**/
	@Column
	private Integer cycleSetting;
	/**周期设置时间段**/
	@Column
	private String cycleSettingDate;
	/**类型  1正常时段  2特惠时段**/
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
	/**费用包含**/
	@Column
	private String priceInclude;
	/**可使用金币数量**/
	@Column
	private Integer goldSum;
	/**返现积分**/
	@Column
	private Integer integralSum;
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


	/**优惠券GUID数组**/
	@Transient
	private String[] preferentialList;
}
