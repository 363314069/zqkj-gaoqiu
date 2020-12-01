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
 * 新版会员卡
 * @author zqkj
 * @email root@qq.com
 * @date 2020-10-15 10:28:43
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="vipcardtab")
public class VipcardEntity extends BaseEntity implements Serializable {
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
	/**开始时间**/
	@Column
	private String startTime;
	/**结束时间**/
	@Column
	private String endTime;
	/**回调url**/
	@Column
	private String callbackUrl;
	/**svg地址**/
	@Column
	private String svgPath;
	/**购买起限期**/
	@Column
	private Integer term;
	/**"1、统一结束期限（用EndTime计算）2、天（Term表示天数）3、月（Term表示月数）4、年（Term表示年数）**/
	@Column
	private Integer termType;
	/**使用范围1、全品类  2、指定商品 3、指定商家全品类  4、指定商家指定商品   **/
	@Column
	private Integer scope;
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
}
