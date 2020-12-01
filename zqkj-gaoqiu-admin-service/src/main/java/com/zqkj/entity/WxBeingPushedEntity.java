package com.zqkj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 消息内容实体
 */
@Data
public class WxBeingPushedEntity{
	/**标题**/
	private String title;
	/**产品名称**/
	private String productName;
	/**跳转链接**/
	private String url;
	/**预约时间**/
	private String date;
	/**内容**/
	private String data;
}
