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
 * @date 2020-04-22 11:21:50
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="goldtab")
public class GoldEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**流水号**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**GUID**/
	@Column
	private String guid;
	/**来源GUID（订单）**/
	@Column
	private String sourceGuid;
	/**金币**/
	@Column
	private Integer gold;
	/**类型  1购买获取   2取消订单返回**/
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

	/**接收产品GUID参数**/
	@Transient
	private String productGuid;
	/**购买产品数量**/
	@Transient
	private Integer sum;
}
