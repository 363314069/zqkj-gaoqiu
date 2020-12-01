package com.zqkj.bean;

import java.io.Serializable;



/**
 * 
 * 
 * @author zqkj
 * @email root@qq.com
 * 
 */
public class SystemLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//GUID
	private String guid;
	//机构GUID
	private String organizationGuid;
	//用户GUID
	private String userGuid;
	//操作方式
	private String operation;
	//方法
	private String method;
	//方法参数
	private String params;
	//日志内容
	private String content;
	//访问IP
	private String visitIP;
	//编号
	private Integer code;
	//运行时间
	private Integer runTime;
	//创建时间
	private String createTime;
	//状态
	private Integer state;
	//备注
	private String remark;
	//扩展整型
	private Integer extInt;
	//扩展字符
	private String extStr;

	/**
	 * 设置：编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：GUID
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	/**
	 * 获取：GUID
	 */
	public String getGuid() {
		return guid;
	}
	/**
	 * 设置：机构GUID
	 */
	public void setOrganizationGuid(String organizationGuid) {
		this.organizationGuid = organizationGuid;
	}
	/**
	 * 获取：机构GUID
	 */
	public String getOrganizationGuid() {
		return organizationGuid;
	}
	/**
	 * 设置：用户GUID
	 */
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	/**
	 * 获取：用户GUID
	 */
	public String getUserGuid() {
		return userGuid;
	}
	/**
	 * 设置：操作方式
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * 获取：操作方式
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * 设置：方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：方法
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：方法参数
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 获取：方法参数
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设置：日志内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：日志内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：访问IP
	 */
	public void setVisitIP(String visitIP) {
		this.visitIP = visitIP;
	}
	/**
	 * 获取：访问IP
	 */
	public String getVisitIP() {
		return visitIP;
	}
	/**
	 * 设置：编号
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取：编号
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置：运行时间
	 */
	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}
	/**
	 * 获取：运行时间
	 */
	public Integer getRunTime() {
		return runTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：状态
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：状态
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：扩展整型
	 */
	public void setExtInt(Integer extInt) {
		this.extInt = extInt;
	}
	/**
	 * 获取：扩展整型
	 */
	public Integer getExtInt() {
		return extInt;
	}
	/**
	 * 设置：扩展字符
	 */
	public void setExtStr(String extStr) {
		this.extStr = extStr;
	}
	/**
	 * 获取：扩展字符
	 */
	public String getExtStr() {
		return extStr;
	}
}
