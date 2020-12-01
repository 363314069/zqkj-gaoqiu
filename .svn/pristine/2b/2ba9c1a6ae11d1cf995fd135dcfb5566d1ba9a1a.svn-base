package com.zqkj.utils.ip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IP地址
 * 
 */
public class IPUtil {
	private static Logger logger = LoggerFactory.getLogger(IPUtil.class);
	
	public IPUtil(){}
	@Data
	@EqualsAndHashCode(callSuper = false)
	public class IPBean implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * ip段开始longip
		 */
		private long startLongIp;
		/**
		 * ip段结束longip
		 */
		private long endLongIp;
		/**
		 * ip段开始strip
		 */
		private String startStrIp;
		/**
		 * ip段结束strip
		 */
		private String endStrIp;
		/**
		 * 子网掩码
		 */
		private long mask;
		/**
		 * 地区
		 */
		private String country;
		/**
		 * 地方
		 */
		private String area;
	}

	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	logger.error("IPUtils ERROR ", e);
        }
        return ip;
    }
	
	/**单个IP解析成数字格式如:192.168.0.1 返回为：192.168.000.001**/
	public static String ipComplement(String ip) {
		ip = ipClear(ip);
		if(ip == null)
			return null;
		String ips[] = ip.split("\\.");
		StringBuilder ipstr =  new StringBuilder();
		for (int i = 0; i < ips.length; i++) {
			if(i != 0)
				ipstr.append(".");
			ipstr.append(iplength(ips[i]));
		}
		return ipstr.toString();
	}
	/**单个IP解析成数字格式如:192.168.0.1 返回为：192.168.000.001**/
	public static String ipComplementLong(String ip) {
		ip = ipClear(ip);
		if(ip == null)
			return null;
		String ips[] = ip.split("\\.");
		StringBuilder ipstr =  new StringBuilder();
		for (int i = 0; i < ips.length; i++) {
			ipstr.append(iplength(ips[i]));
		}
		return ipstr.toString();
	}
	private static String iplength(String name) {
		name = name.trim();
		if (name.length() == 1) {
			name = "00" + name;
		} else if (name.length() == 2) {
			name = "0" + name;
		}
		return name;
	}

	// 单个IP段解析成数字 格式如:192.168.0.1-192.168.0.2 返回为：986136576-986136831
	public static IPBean ipPart2Long(String ip) {
		Long ipLong1 = 0L;
		Long ipLong2 = 0L;
		IPBean ipBean = new IPUtil().new IPBean();
		ip = ip.trim();
		long mask = 4294967295L;
		if (ip.indexOf("-") != -1) {
			String ipString[] = ip.split("-");
			ipLong1 = ip2long(ipString[0]);
			ipLong2 = ip2long(ipString[1]);
			if (ipLong1 > ipLong2) {
				long iptmp = ipLong2;
				ipLong2 = ipLong1;
				ipLong1 = iptmp;
			}
			mask = getMask(ipLong1, ipLong2);
		} else if (ip.indexOf("/") != -1) {
			String ipString[] = ip.split("/");
			ipLong1 = ip2long(ipString[0]);
			if (ipString[1].length() > 3) {
				mask = ip2long(ipString[1]);
				long maskDigit =  Long.toBinaryString(4294967295L ^ mask).length();
				mask = 4294967295L >>>  maskDigit << maskDigit;
			} else {
				mask = 4294967295L & (4294967295L << (32 - Integer.parseInt(ipString[1])));
			}
			ipLong1 = ipLong1 & mask;
			ipLong2 = ipLong1 ^ (4294967295L & (~mask));
		} else {
			ipLong1 = ipLong2 = ip2long(ip);
		}

		ipBean.setStartLongIp(ipLong1);
		ipBean.setEndLongIp(ipLong2);
		ipBean.setStartStrIp(long2ip(ipLong1));
		ipBean.setEndStrIp(long2ip(ipLong2));
		ipBean.setMask(mask);
		return ipBean;
	}

	/**
	 * 将127.0.0.1 形式的ip地址转换成10进制整数
	 * 
	 * @param strip
	 * @return
	 */
	public static long ip2long(String strip) {
		strip = ipClear(strip);
		if(strip == null)
			return 0;
		String ips[] = strip.split("\\.");
		long[] ip = new long[4];
		ip[0] = Long.parseLong(ips[0]);
		ip[1] = Long.parseLong(ips[1]);
		ip[2] = Long.parseLong(ips[2]);
		ip[3] = Long.parseLong(ips[3]);
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3]; // ip1*256*256*256+ip2*256*256+ip3*256+ip4
	}

	/**
	 * 将长整形转化为字符串
	 * 
	 * @create 2006-12-3下午05:37:10
	 * @param longip
	 * @return
	 */
	public static String long2ip(long longip) {
		// 将10进制整数形式转换成127.0.0.1形式的ip地址，在命令提示符下输入ping 3396362403l
		StringBuffer sb = new StringBuffer("");
		sb.append(iplength(String.valueOf(longip >>> 24)));// 直接右移24位
		sb.append(".");
		sb.append(iplength(String.valueOf((longip & 0x00ffffff) >>> 16))); // h后右移16位
		sb.append(".");
		sb.append(iplength(String.valueOf((longip & 0x0000ffff) >>> 8)));
		sb.append(".");
		sb.append(iplength(String.valueOf(longip & 0x000000ff)));
		return sb.toString();
	}

	public static long getMask(long ipLong1, long ipLong2) {
		if (ipLong1 > ipLong2) {
			long ip = ipLong2;
			ipLong2 = ipLong1;
			ipLong1 = ip;
		}
		if(ipLong1 == ipLong2)
			return 0xffffffff;
		int maskDigit = Long.toBinaryString(ipLong2 ^ ipLong1).length();
		long mask = 4294967295L >>> maskDigit << maskDigit;
		//maskDigit = Long.toBinaryString((ipLong2 & mask ^ (4294967295L & ~mask)) - (ipLong1 & mask)).length();
		//mask = 4294967295L >> maskDigit << maskDigit;
		return mask;
	}

	/**
	 * 字符串中提取IP放到list中
	 * 
	 * @param ip
	 * @return
	 */
	public static List<IPBean> str2IpList(String ip) {
		ip = ip.replaceAll("\\s*[\\.．、。]\\s*", ".");
		ip = ip.replaceAll("\\s*[\\\\＼／/]\\s*", "/");
		ip = ip.replaceAll("\\s*[-－——_]\\s*", "-");
		List<IPBean> ipList = new ArrayList<IPBean>();
		String regex = "[0-9]{1,3}\\.[0-9]*\\.[0-9]*\\.[0-9]*\\s*-\\s*[0-9]*\\.[0-9]*\\.[0-9]*\\.[0-9]{1,3}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(ip);
		while (m.find()) {
			ipList.add(ipPart2Long(m.group(0)));
		}
		ip = m.replaceAll("");
		regex = "[0-9]{1,3}\\.[0-9]*\\.[0-9]*\\.[0-9]*\\s*/\\s*255\\.255\\.[0-9]*\\.[0-9]{1,3}|[0-9]{1,3}\\.[0-9]*\\.[0-9]*\\.[0-9]*\\s*/\\s*[0-9]{1,2}";
		p = Pattern.compile(regex);
		m = p.matcher(ip);
		while (m.find()) {
			ipList.add(ipPart2Long(m.group(0)));
		}
		ip = m.replaceAll("");
		regex = "[0-9]*\\.[0-9]*\\.[0-9]*\\.[0-9]*";
		p = Pattern.compile(regex);
		m = p.matcher(ip);
		while (m.find()) {
			ipList.add(ipPart2Long(m.group(0)));
		}
		return ipList;
	}
	public static String ipClear(String ip){
		if(StringUtils.isBlank(ip))
			return null;
		ip = ip.replaceAll("[.．、。。。]", ".").replaceAll("[^0-9.]*", "");
		if(!ipVerify(ip))
			return null;
		return ip;
	}
	
	public static boolean ipVerify(String ip){
		if (!ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"))
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		
		//String ip="61.138.230.214/30 218.104.142.64/27 58.199.64.0/24 121.192.160.0/24 110.85.4.0/27 192.168.0.1-192.168.0.10 192.168.0.1,192.168.0.2 \n 192.168.0.3 192.168.0.4 192.168.0.4/255.255.255.23 192.168.0.5/24 192.168.0.5 - 192.168.0.6 124.119.15.209/28";
		String ip="";
		List<IPBean> ipList=IPUtil.str2IpList(ip);
    	for(int i=0;i<ipList.size();i++){
    		//System.out.println(IpPeriod2SubnetMask(ipList.get(i).toString()));
    		System.out.println(ipList.get(i));
    		System.out.println(IPUtil.long2ip(ipList.get(i).getMask()));
    	}
    	
		IPBean ipEntry = ipPart2Long("192.168.0.4/255.255.255.23");
    	System.out.println(ipEntry);
    	System.out.println(IPUtil.long2ip(ipEntry.getMask()));
    	System.out.println(IPUtil.ip2long("220.162.205.134"));
    	
	}
}
