package com.zqkj.utils;

import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Created by ace on 2017/9/10.
 */
public class StringUtil {
    
	/**
	 * @Title: getStringValue   
	 * @Description: TODO(任意类型数据转换为String)   
	 * @param: @param obj
	 * @return: String      
	 */
	public static String getStringValue(Object obj){
        return obj==null?null:obj.toString();
    }
    
	/**
	 * @Title: getIntegerValue   
	 * @Description: TODO(任意类型数据转换为Integer)   
	 * @param: @param obj
	 * @return: Integer      
	 */
    public static Integer getIntegerValue(Object obj){
        return obj==null?null:(Integer)obj;
    }
    /**
     * 判段字符串是否为空  为空返回true 不为空返回false
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return (str == null || "".equals(str));
    }
    /**
     * @Title: generateGUID   
     * @Description: TODO(GUID生成)   
     * @param: @return      
     * @return: String      
     */
    public static String generateGUID() {
    	return UUID.randomUUID().toString();
    }

    /**
     * @Title: isNumeric   
     * @Description: TODO(验证字符串是否是有效数字)   
     * @param: @param str
     * @return: boolean      
     */
	public static boolean isNumeric(String str){
		//正则判断 整数 非0开头小数 验证
		Pattern pattern = Pattern.compile("^[1-9][0-9]*\\.?[0-9]{0,}");
	    return pattern.matcher(str).matches();    
	}
	
	/**
	 * @Title: isMobile   
	 * @Description: TODO(验证字符串是否是手机号)   
	 * @param: @param str
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isMobile(String str){
		//正则判断 整数 非0开头小数 验证
		Pattern pattern = Pattern.compile("^1[0-9]\\d{9}");
	    return pattern.matcher(str).matches();    
	}
	
	
	/**
	 * 
	 * @Title: isPhone   
	 * @Description: TODO(大陆号码或香港号码均可 )   
	 * @param: @param str
	 * @param: @throws PatternSyntaxException      
	 * @return: boolean      
	 */
    public static boolean isPhone(String str)throws PatternSyntaxException {  
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);  
    }  
	
	
	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有： 
     * 13+任意数 
     * 15+除4的任意数 
     * 18+除1和4的任意数 
     * 17+除9的任意数 
     * 147 
	 * @Title: isChinaPhoneLegal   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param str
	 * @param: @throws PatternSyntaxException      
	 * @return: boolean      
	 */
	 public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
	        return isMobile(str);  
	  }
	 
	 
  /**
   * @Title: isHKPhoneLegal   
   * @Description: TODO(香港手机号码8位数，5|6|8|9开头+7位任意数 )   
   * @param: @param str
   * @param: @return
   * @param: @throws PatternSyntaxException      
   * @return: boolean      
   * @throws
   */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {  
        String regExp = "^(5|6|8|9)\\d{7}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  
	    
	    
  /**
   * 
   * @Title: strNumCompile   
   * @Description: TODO(字符串是否纯数字)   
   * @param: @param str
   * @param: @return      
   * @return: boolean      
   * @throws
   */
    public static boolean strNumCompile(String str){  
        Pattern p = Pattern.compile("^\\d+$");  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  

	/**
	* 比较长度,截取字段
	* @Title: strSubLength 
	* @Description: TODO(比较长度,截取字段) 
	* @param @param str  字符串
	* @param @param compareLen 比较长度
	* @param @param startLen 截取起始位置
	* @param @param EndLen 截取最终位置
	 */
	public static  String  strSubLength(String str,int compareLen,int startLen,int EndLen){
		 if(org.apache.commons.lang.StringUtils.isBlank(str)){
			 return "";
		 }
		 //长度超过截取
		 if(str.length()>compareLen){
			 str=str.substring(startLen, EndLen);
		 }
		 return str.trim(); 
	}
	    
		
	/**
	 * 
	* @Title: StrToURLDecoder 
	* @Description: TODO(URL解码) 
	* @param @param source
	* @return String    返回类型 
	 */
	public static String strToURLDecoder(String source){
		String temp = null;
		try{
			temp = URLDecoder.decode(source, "UTF-8");
		}catch(Exception e){
			temp =  source;
		}
		return temp;
	}
	    
	/**
	 * 
	 * @Title: isChinese   
	 * @Description: TODO(判断是不是中文)   
	 * @param: @param str
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isChinese(String str) {

		String regEx = "[\\u4e00-\\u9fa5]+";

		Pattern p = Pattern.compile(regEx);

		Matcher m = p.matcher(str);

		if (m.find()) {
			return true;
		}else {
			return false;
		}

	}
		
	/**
	 * 
	 * @Title: isEnglish   
	 * @Description: TODO(判断是否是英文)   
	 * @param: @param charaString
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	  public static boolean isEnglish(String charaString){
	      return charaString.matches("^[a-zA-Z]*");
	   }

		  
   	/**
   	 * 
   	 * @Title: checkEmaile   
   	 * @Description: TODO(判断邮箱是否合法)   
   	 * @param: @param email
   	 * @param: @return      
   	 * @return: boolean      
   	 * @throws
   	 */
	public static boolean checkEmaile(String email) {
		/**
		 * ^匹配输入字符串的开始位置 $结束的位置 \转义字符 eg:\. 匹配一个. 字符 不是任意字符 ，转义之后让他失去原有的功能 \t制表符 \n换行符
		 * \\w匹配字符串 eg:\w不能匹配 因为转义了 \w匹配包括字母数字下划线的任何单词字符 \s包括空格制表符换行符 *匹配前面的子表达式任意次
		 * .小数点可以匹配任意字符 +表达式至少出现一次 ?表达式0次或者1次 {10}重复10次 {1,3}至少1-3次 {0,5}最多5次 {0,}至少0次
		 * 不出现或者出现任意次都可以 可以用*号代替 {1,}至少1次 一般用+来代替 []自定义集合 eg:[abcd] abcd集合里任意字符 [^abc]取非
		 * 除abc以外的任意字符 | 将两个匹配条件进行逻辑“或”（Or）运算 [1-9] 1到9 省略123456789 邮箱匹配 eg:
		 * ^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$
		 * 
		 */
		String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		// 正则表达式的模式 编译正则表达式
		Pattern p = Pattern.compile(RULE_EMAIL);
		// 正则表达式的匹配器
		Matcher m = p.matcher(email);
		// 进行正则匹配
		return m.matches();
	}  

		  
	
	/**
	 * 判断字段真实长度（中文2个字符，英文1个字符）的方法
	* @Title: strReallen 
	* @param @param value
	 */
	public static int strReallen(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	
	/**
	 * 将多个联系人转为列表，分隔符为逗号或者分号
	 * 
	 * @param addresses 多个联系人，如果为空返回null
	 * @return 联系人列表
	 */
	public static List<String> splitAddress(String addresses){
		if(StrUtil.isBlank(addresses)) {
			return null;
		}
		
		List<String> result;
		if(StrUtil.contains(addresses, ',')) {
			result = StrUtil.splitTrim(addresses, ',');
		}else if(StrUtil.contains(addresses, ';')) {
			result = StrUtil.splitTrim(addresses, ';');
		}else {
			result = CollUtil.newArrayList(addresses);
		}
		return result;
	}
	
    
	/**
	 * 登录密码格式、长度校验
	 * 包含大小写字母及数字且在6-16位
	 * @param password
	 * @return boolean  true=正确，false=登录密码由字母和数字构成，不能超过16位
	 */
	public static boolean loginPassWordRegex(String password) {
	    boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
	    boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
	    for (int i = 0; i < password.length(); i++) {
	        if (Character.isDigit(password.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
	            isDigit = true;
	        } else if (Character.isLetter(password.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
	            isLetter = true;
	        }
	    }
	    String regex = "^[a-zA-Z0-9]{6,16}$";
	    boolean isRight = isDigit && isLetter && password.matches(regex);
	    return isRight;
	}
    
	
    /**
     * 登录名校验(字母开头)
     * @param loginName
     * @return boolean true=正确，false=登录名由字母数字下划线组成且开头必须是字母，不能超过16位
     */
    public static boolean loginNameRegex(String loginName){
		String regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{5,15}$";
		Pattern pattern = Pattern.compile(regex);
		if(!pattern.matcher(loginName).find()) {
			return false;
		}
		return true;
    }
    
	
    /**
     * 字符串转数组
     * @param str 字符串
     * @param splitStr 分隔符
     * @return
     */
    public static String[] StringToArray(String str,String splitStr){
      String[] arrayStr = null;
      if(!"".equals(str) && str != null){
        if(str.indexOf(splitStr)!=-1){
          arrayStr = str.split(splitStr);
        }else{
          arrayStr = new String[1];
          arrayStr[0] = str;
        }
      }
      return arrayStr;
    }
    
}
