package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 
 * 订单扩展表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-03 16:31:23
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="orderextendtab")
public class OrderExtendEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**订单GUID**/
	@Column
	private String orderGUID;
	/**ExtLong**/
	@Column
	private Integer extLong;
	/**单位**/
	@Column
	private Integer unit;
	/**类型**/
	@Column
	private Integer type;
	/**状态**/
	@Column
	private Integer state;
	/**扩展整型**/
	@Column
	private Integer extInt;
	/**扩展字符**/
	@Column
	private String extStr;
}
