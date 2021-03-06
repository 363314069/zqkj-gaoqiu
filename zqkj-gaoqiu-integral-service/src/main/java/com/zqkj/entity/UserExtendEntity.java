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
 * 用户扩展表
 * @author zqkj
 * @email root@qq.com
 * @date 2020-01-03 16:31:23
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="userextendtab")
public class UserExtendEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**编号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**用户GUID**/
	@Column
	private String userGUID;
	/**ExtLong**/
	@Column
	private Integer extLong;
	/**单位**/
	@Column
	private Integer unit;
	/**类型  1、佣金 2、积分   3、等级  4、余额  5、金币  6、会员卡**/
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
