package com.zqkj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zqkj.utils.annotation.SqlFieldIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * @author yinfu
 * @email root@qq.com
 * @date 2019-07-25 15:50:28
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="userexamtab")
@SqlFieldIgnore("answer")
public class UserExamEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**id**/
	@Id
	@GeneratedValue(generator = "JDBC")
	@Column
	private Integer id;
	/**guid**/
	@Column
	private String guid;
	/**机构guid**/
	@Column
	private String organizationGuid;
	/**试卷名**/
	@Column
	private String name;
	/**成绩**/
	@Column
	private Float fraction;
	/**试卷分数**/
	@Column
	private Integer examFraction;
	/**描述**/
	@Column(name = "`describe`")
	private String describe;
	/**备注**/
	@Column
	private String remark;
	/**答案**/
	@Column
	private String answer;
	/**答错数量**/
	@Column
	private Integer correctCount;
	/**答错数量**/
	@Column
	private Integer wrongCount;
	/**题量**/
	@Column
	private Integer questionCount;
	/**专辑id**/
	@Column
	private Integer suId;
	/**试卷id**/
	@Column
	private Integer exId;
	/**分类id**/
	@Column
	private Integer soId;
	/**答题时间**/
	@Column
	private Integer examTime;
	/**答题状态**/
	@Column
	private Integer answerStatus;
	/**试卷类型**/
	@Column
	private String examType;
	/**分享**/
	@Column
	private Integer share;
	/**点赞**/
	@Column(name = "`like`")
	private Integer like;	
	/**类型**/
	@Column
	private Integer type;
	/**状态**/
	@Column
	private Integer state;
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
