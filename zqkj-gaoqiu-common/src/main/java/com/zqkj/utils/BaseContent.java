package com.zqkj.utils;

/**
 * Created by ace on 2017/8/29.
 */
public class BaseContent {
	/** 系统编码统一UTF-8 **/
	public static final String SYSTEM_CODING = "UTF-8";
	/** 系统异常码500 **/
	public static final Integer EX_OTHER_CODE = 500;
	/** 当前用户ID KEY **/
	public static final String CONTEXT_KEY_USER_ID = "userId";
	/** 当前用户GUID KEY **/
	public static final String CONTEXT_KEY_USER_GUID = "userGuid";
	/** 当前用户姓名 KEY **/
	public static final String CONTEXT_KEY_USER_NAME = "userName";
	/** 当前用户帐号 KEY **/
	public static final String CONTEXT_KEY_USER_LOGINNAME = "loginName";
	/** 当前用户Token KEY **/
	public static final String CONTEXT_KEY_USER_TOKEN = "token";
	/** 当前用户组织ID KEY **/
	public static final String CONTEXT_KEY_USER_ORGANIZATIONID = "organizationId";
	/** JWTInfo KEY **/
	public static final String CONTEXT_KEY_USER_JWTINFO = "jwtInfo";
	/** 当前用户组织GUID KEY **/
	public static final String CONTEXT_KEY_USER_ORGANIZATIONGUID = "organizationGuid";
	/** 当前用户IP KEY **/
	public static final String CONTEXT_KEY_USER_IP = "userIp";
	/** 登录验证码 KEY **/
	public static final String CONTEXT_KEY_LOGIN_CODE = "loginCode";

	/** 用户未登录,请登录 **/
	public static final int STATUS_CODE_NOT_LOGIN = 4001;
	/** 参数错误 **/
	public static final int STATUS_CODE_PARAM_ERROR_CODE = 5006;
	/** 数据成功插入 **/
	public static final int STATUS_CODE_DATA_INPUT_OK = 2001;
	/** 数据删除成功 **/
	public static final int STATUS_CODE_DATA_DEL_OK = 2002;
	/** 数据更新成功 **/
	public static final int STATUS_CODE_DATA_UPDATE_OK = 2003;
	
	// #4xxx系列：
	/** 用户未登录 **/
	public static final int STATUS_CODE_4001 = 4001;
	/** 验证码错误，请重新输入 **/
	public static final int STATUS_CODE_4002 = 4002;
	/** SOURCE AUTHORIZATION ERROR **/
	public static final int STATUS_CODE_4003 = 4003;
	/** 令牌过期 **/
	public static final int STATUS_CODE_4004 = 4004;
	/** 用户类型错误，请登录相应客户端 **/
	public static final int STATUS_CODE_4005 = 4005;
	/** 您的权限不足 **/
	public static final int STATUS_CODE_4006 = 4006;
	/** 请登录后操作 **/
	public static final int STATUS_CODE_4007 = 4007;
	/** 用户名或密码填写错误 **/
	public static final int STATUS_CODE_4008 = 4008;
	/** 您没有权限操作，或资讯不存在 **/
	public static final int STATUS_CODE_4010 = 4010;
	/** 请填写表单内容 **/
	public static final int STATUS_CODE_4011 = 4011;
	/** 您的请求过于频繁，请稍后再试 **/
	public static final int STATUS_CODE_4012 = 4012;
	/** 获取微信信息失败 **/
	public static final int STATUS_CODE_4013 = 4013;
	/** 当前状态为审核成功,不需要更新 **/
	public static final int STATUS_CODE_4051 = 4051;
	/** 请求菜单代码不存在，请联系管理员核实。 **/
	public static final int STATUS_CODE_4052 = 4052;
	/** 请求菜单下的选项卡代码不存在，请联系管理员核实。 **/
	public static final int STATUS_CODE_4053 = 4053;
	/** apiKey代码不存在，请联系管理员核实。 **/
	public static final int STATUS_CODE_4054 = 4054;
	/** atabApiKeyFormat参数不全，请联系管理员核实。 **/
	public static final int STATUS_CODE_4055 = 4055;
	/** 请求菜单代码[未迁移]，请联系管理员核实。 **/
	public static final int STATUS_CODE_4056 = 4056;
	/** 校验登录名长度不合法**/
	public static final int STATUS_CODE_4057 = 4057;
	/** 登录类型不合法**/
	public static final int STATUS_CODE_4058 = 4058;

	/** 今日已签到 **/
	public static final int STATUS_CODE_4101 = 4101;
	/** ip不在范围内  **/
	public static final int STATUS_CODE_4201 = 4201;
	
	/** 数据更新失败 **/
	public static final int STATUS_CODE_5001 = 5001;
	/** 文件上传失败 **/
	public static final int STATUS_CODE_5002 = 5002;
	/** 数据删除失败 **/
	public static final int STATUS_CODE_5003 = 5003;
	/** 查询数据为空 **/
	public static final int STATUS_CODE_5004 = 5004;
	/** 数据添加失败 **/
	public static final int STATUS_CODE_5005 = 5005;
	/** 参数不合法 **/
	public static final int STATUS_CODE_5006 = 5006;
	/** 操作失败 **/
	public static final int STATUS_CODE_5007 = 5007;
	/** 请不要重复提交问题 **/
	public static final int STATUS_CODE_5008 = 5008;
	/** 您所发送的内容含有违禁字符，请检查后重新提交。 **/
	public static final int STATUS_CODE_5009 = 5009;
	/** 数据已经存在,无需重新添加 **/
	public static final int STATUS_CODE_5010 = 5010;
	/** 微信号未绑定手机 **/
	public static final int STATUS_CODE_5011 = 5011;
	/** 该手机号已经绑定过其他微信号 **/
	public static final int STATUS_CODE_5012 = 5012;
	/** 该手机号已经绑定过其他QQ号 **/
	public static final int STATUS_CODE_5013 = 5013;
	/** 名称重复 **/
	public static final int STATUS_CODE_5014 = 5014;
	/** 不能重复 **/
	public static final int STATUS_CODE_5015 = 5015;
	/** 文本框输入内容有重复省/市,请检查! **/
	public static final int STATUS_CODE_5016 = 5016;
	/** 会员卡绑定失败 **/
	public static final int STATUS_CODE_5017 = 5017;

	/** 已关注 **/
	public static final int STATUS_CODE_5110 = 5110;
	/** 您已经赞过了 **/
	public static final int STATUS_CODE_5111 = 5111;
	/** 取消关注失败 **/
	public static final int STATUS_CODE_5112 = 5112;
	/** 您已经取消过了 **/
	public static final int STATUS_CODE_5113 = 5113;
	/** 当前分类下有视频资源,请先把视频删除 **/
	public static final int STATUS_CODE_5114 = 5114;
	/** 当前话题已下架 **/
	public static final int STATUS_CODE_5115 = 5115;
	/** 未关注 **/
	public static final int STATUS_CODE_5116 = 5116;

	/** 请重新获取验证码 **/
	public static final int STATUS_CODE_5201 = 5201;
	/** 请输入短信验证码 **/
	public static final int STATUS_CODE_5202 = 5202;
	/** 验证码错误，请重新输入 **/
	public static final int STATUS_CODE_5203 = 5203;
	/** 请输入正确的手机号 **/
	public static final int STATUS_CODE_5204 = 5204;
	/** 每天验证码最多下发5次，请您明天再试 **/
	public static final int STATUS_CODE_5205 = 5205;
	/** 验证码已过期，请重新获取 **/
	public static final int STATUS_CODE_5206 = 5206;
	/** 验证码不能为空 **/
	public static final int STATUS_CODE_5207 = 5207;
	/** 未查询到手机号码 **/
	public static final int STATUS_CODE_5208 = 5208;
	/** 当前分类不存在 **/
	public static final int STATUS_CODE_5209 = 5209;
	/** 主键不能为空! **/
	public static final int STATUS_CODE_5210 = 5210;
	/** 名称不能为空! **/
	public static final int STATUS_CODE_5211 = 5211;
	
	/** 您输入的两次密码不一致 **/
	public static final int STATUS_CODE_5301 = 5301;
	/** 请输入用户名 **/
	public static final int STATUS_CODE_5302 = 5302;
	/** 请输入正确密码 **/
	public static final int STATUS_CODE_5303 = 5303;
	/** 该用户名已注册 **/
	public static final int STATUS_CODE_5304 = 5304;
	/** 该手机号已注册 **/
	public static final int STATUS_CODE_5305 = 5305;
	/** 图片验证码输入有误 **/
	public static final int STATUS_CODE_5306 = 5306;
	/** 您的用户名或密码输入有误 **/
	public static final int STATUS_CODE_5307 = 5307;
	/** 创建用户失败 **/
	public static final int STATUS_CODE_5308 = 5308;
	/** 登出用户失败 **/
	public static final int STATUS_CODE_5309 = 5309;
	/** 修改密码失败，请保证输入信息的准确 **/
	public static final int STATUS_CODE_5310 = 5310;
	/** 请确认用户类型 **/
	public static final int STATUS_CODE_5311 = 5311;
	/** 该用户不存在 **/
	public static final int STATUS_CODE_5312 = 5312;
	/** 您输入的用户名含有屏蔽字段,如“猪猪乐”. **/
	public static final int STATUS_CODE_5313 = 5313;
	/** 该邮箱已经注册 **/
	public static final int STATUS_CODE_5314 = 5314;
	/** 注册链接失效，请重新注册 **/
	public static final int STATUS_CODE_5315 = 5315;
	/** 该邮箱未注册过，请确认 **/
	public static final int STATUS_CODE_5316 = 5316;
	/** 该用户已激活 **/
	public static final int STATUS_CODE_5317 = 5317;
	/** 激活成功 **/
	public static final int STATUS_CODE_5318 = 5318;
	/** 激活失败，请重新激活 **/
	public static final int STATUS_CODE_5319 = 5319;
	/** 新密码和原密码相同 **/
	public static final int STATUS_CODE_5320 = 5320;
	/** 原密码输入错误,请重新输入 **/
	public static final int STATUS_CODE_5321 = 5321;
	/** 该用户已停用 **/
	public static final int STATUS_CODE_5322 = 5322;
	/** 邮箱格式不合法 **/
	public static final int STATUS_CODE_5323 = 5323;
	/** 邮件发送失败 **/
	public static final int STATUS_CODE_5324 = 5324;
	/** 登录密码由字母和数字构成，不能超过16位**/
	public static final int STATUS_CODE_5325 = 5325;
	/** 登录名由字母数字下划线组成且开头必须是字母，不能超过16位**/
	public static final int STATUS_CODE_5326 = 5326;
	
	
	/** 没有该经销商的信息 **/
	public static final int STATUS_CODE_5401 = 5401;
	/** 该用户积分不足 **/
	public static final int STATUS_CODE_5402 = 5402;
	/** 没有该用户积分信息 **/
	public static final int STATUS_CODE_5403 = 5403;
	/** 请先完善兽医信息 **/
	public static final int STATUS_CODE_5404 = 5504;

	/** 修改订阅失败 **/
	public static final int STATUS_CODE_5601 = 5601;
	/** 订阅成功 **/
	public static final int STATUS_CODE_5602 = 5602;
	/** 您已订阅 **/
	public static final int STATUS_CODE_5603 = 5603;

	/** 该优惠券无效,请确认 **/
	public static final int STATUS_CODE_5701 = 5701;
	/** 该优惠券已领取 **/
	public static final int STATUS_CODE_5702 = 5702;
	/** 优惠券领取失败,请重新操作 **/
	public static final int STATUS_CODE_5703 = 5703;
	/** 无可用优惠券 **/
	public static final int STATUS_CODE_5704 = 5704;

	/** 通知异常 **/
	public static final int STATUS_CODE_5801 = 5801;

	/** 项目名称不能重复,请重新输入 **/
	public static final int STATUS_CODE_6001 = 6001;
	/** 排序号不能重复,请重新输入 **/
	public static final int STATUS_CODE_6002 = 6002;
	/** 项目名称超出设定长度,请重新输入 **/
	public static final int STATUS_CODE_6003 = 6003;
	/** 当前选择节点下存在子节点,不可删除 **/
	public static final int STATUS_CODE_6004 = 6004;
	/** 查询Code不存在,请重新操作 **/
	public static final int STATUS_CODE_6005 = 6005;
	/** 请输入需要查询的项目名称 **/
	public static final int STATUS_CODE_6006 = 6006;
	/** 已存在的角色名称 **/
	public static final int STATUS_CODE_6007 = 6007;
	/** 已超出设定的长度,请重新输入 **/
	public static final int STATUS_CODE_6008 = 6008;
	/** 该菜单已存在,不需要重新添加 **/
	public static final int STATUS_CODE_6009 = 6009;
	/** 格式不正确,请重新输入 **/
	public static final int STATUS_CODE_6010 = 6010;
	/** 超出设定长度,请重新输入 **/
	public static final int STATUS_CODE_6011 = 6011;

	/** WX统一下单,请求参数缺少 **/
	public static final int STATUS_CODE_7001 = 7001;
	/** WX统一下单-openId不存在 **/
	public static final int STATUS_CODE_7002 = 7002;
	/** WX统一下单APPID不存在 **/
	public static final int STATUS_CODE_7003 = 7003;
	/** WX统一下单失败 **/
	public static final int STATUS_CODE_7004 = 7004;

	/** 今日抽奖次数已到上限 **/
	public static final int STATUS_CODE_8001 = 8001;
	/** 积分消费失败 **/
	public static final int STATUS_CODE_8002 = 8002;
	/** 奖品池初始化错误 **/
	public static final int STATUS_CODE_8003 = 8003;
	/** 今日抽奖次数无法初始化 **/
	public static final int STATUS_CODE_8004 = 8004;

	/** 遇到未知错误,请过段时间再试一下 **/
	public static final int STATUS_CODE_9001 = 9001;

	/** 文件类型错误！ **/
	public static final int STATUS_CODE_10006 = 10006;
	/** 文件传输出错！ **/
	public static final int STATUS_CODE_10007 = 10007;
	/** 文件内容为空，请重新选择文件！ **/
	public static final int STATUS_CODE_10008 = 10008;
	/** 文件扩展名只能是 .pdf 或 .PDF格式，请重新选择文件上传！ **/
	public static final int STATUS_CODE_10009 = 10009;
	/** 导出Excel工作目录异常 **/
	public static final int STATUS_CODE_10010 = 10010;
	/** 抗体检测无数据,无法导出Excel **/
	public static final int STATUS_CODE_10011 = 10011;
	/** 未找到相关导出项,itemCode exception **/
	public static final int STATUS_CODE_10012 = 10012;
	
	/**帐号不能为空**/
	public static final int STATUS_CODE_10101 = 10101;
	/**帐号由\"a-zA-Z0-9_\"组成必须以字母的5到16位"**/
	public static final int STATUS_CODE_10102 = 10102;
	/**密码不能为空**/
	public static final int STATUS_CODE_10103 = 10103;
	/**密码不能超过16位**/
	public static final int STATUS_CODE_10104 = 10104;
	/**标题不能为空!**/
	public static final int STATUS_CODE_10105 = 10105;
	/**发布时间不能为空!**/
	public static final int STATUS_CODE_10106 = 10106;
	/**两次输入密码不一致!**/
	public static final int STATUS_CODE_10107 = 10107;
	
	/**角色guid不能为空!**/
	public static final int STATUS_CODE_10201 = 10201;
	/**用户guid不能为空!**/
	public static final int STATUS_CODE_10202 = 10202;
	/**分组guid不能为空!**/
	public static final int STATUS_CODE_10203 = 10203;
	/**ip不能为空!**/
	public static final int STATUS_CODE_10303 = 10303;

	/**guid不能为空!**/
	public static final int STATUS_CODE_20000 = 20000;


	/**参数为空!**/
	public static final int STATUS_CODE_20100 = 20100;
}
