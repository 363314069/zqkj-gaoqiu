package com.zqkj.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.zqkj.utils.annotation.SqlFieldIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 球场表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 10:49:45
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="reservationtab")
@SqlFieldIgnore("content")
public class ReservationEntity extends BaseEntity implements Serializable {
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
	/**前缀**/
	@Column
	private String prefix;
	/**简介**/
	@Column
	private String profile;
	/**地址编码**/
	@Column
	private String addressCode;
	/**地址**/
	@Column
	private String address;
	/**GPS坐标**/
	@Column
	private String coordinate;
	/**场地类型（丘陵场地）**/
	@Column
	private String resType;
	/**建立时间**/
	@Column
	private String resTime;
	/**球场面积**/
	@Column
	private String resArea;
	/**果岭草种**/
	@Column
	private String greenGrass;
	/**球场数据**/
	@Column
	private String resData;
	/**设计师**/
	@Column
	private String designer;
	/**球道长度**/
	@Column
	private String resLength;
	/**球道草种**/
	@Column
	private String fairwayGrass;
	/**球道详情**/
	@Column
	private String fairwayDetails;
	/**联系电话**/
	@Column
	private String phone;
	/**内容**/
	@Column
	private String content;
	/**图片**/
	@Column
	private String img;
	/**价格**/
	@Column
	private Integer price;
	/**折扣**/
	@Column
	private String discount;
	/**预定天数**/
	@Column
	private Integer days;
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
	/**时间段json**/
	@Column
	private String timeJson;
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


	/**场地信息**/
	@Transient
	private List<IntroductionEntity> introductionList;
}
