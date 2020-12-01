package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 错题库
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-08-08 20:42:56
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="wrongquestiontab")
public class WrongQuestionEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/****/
	@Column
	private String guid;
	/****/
	@Column
	private String userExamGuid;
	/****/
	@Column
	private Integer suId;
	/****/
	@Column
	private Integer soId;
	/****/
	@Column
	private Integer exId;
	/****/
	@Column
	private Integer quId;
	/****/
	@Column
	private Integer anId;
	/****/
	@Column
	private String answer;
	/****/
	@Column
	private Integer way;
	/****/
	@Column
	private Integer type;
	/****/
	@Column
	private Integer wrongCount;
	/****/
	@Column
	private Integer correctCount;
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
	/**************************************/
}
