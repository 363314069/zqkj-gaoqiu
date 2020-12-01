package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.zqkj.utils.annotation.SqlFieldIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-14 11:14:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="teatimetab")
@SqlFieldIgnore("content")
public class TeatimeEntity extends BaseEntity implements Serializable {
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
	/**每天日期GIUD**/
	@Column
	private String reservationDateGuid;
	/**用户GUID**/
	@Column
	private String userGuid;
	/**时间**/
	@Column
	private String time;
	/**日期**/
	@Column
	private String date;
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
	/**订单guid**/
	@Transient
	private String orderGuid;
}
