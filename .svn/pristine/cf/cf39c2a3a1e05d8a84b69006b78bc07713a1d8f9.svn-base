package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-03 16:01:17
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="vipcouponstab")
public class VipCouponsEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**机构GUID**/
	@Column
	private String organizationGuid;
	/**名称**/
	@Column
	private String name;
	/**内容**/
	@Column
	private String content;
	/**回调url**/
	@Column
	private String callbackUrl;
	/**开始时间**/
	@Column
	private String startTime;
	/**结束时间**/
	@Column
	private String endTime;
	/**购买起期限**/
	@Column
	private Integer term;
	/**1、统一结束期限（用EndTime计算）
2、天（Term表示天数）
3、月（Term表示月数）
4、年（Term表示年数）**/
	@Column
	private Integer termType;
	/**类型（卡的类型，通过参数表管理）**/
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
