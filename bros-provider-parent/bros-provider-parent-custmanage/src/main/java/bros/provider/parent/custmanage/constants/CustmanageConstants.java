package bros.provider.parent.custmanage.constants;

import java.util.regex.Pattern;


/**
 * 
 * @ClassName: CustmanageConstants 
 * @Description: 客户管理模块常量类
 * @author mazhilei 
 * @date 2016年7月4日 下午4:50:10 
 * @version 1.0
 */
public class CustmanageConstants {
	
	/**
	 * 操作员员状态  0：正常
	 */
	public static final int OPERATORSTT_0 = 0;
	
	/**
	 * 操作员员状态 1：冻结
	 */
	public static final int OPERATORSTT_1 = 1;	
	
	/**
	 * 操作员员状态 2：注销
	 */
	public static final int OPERATORSTT_2 = 2;
	
	/**
	 * 正则（模型人数）
	 */
	public static final String  REG_PERSON_NUM = "^\\d+$"; 
	
	/**
	 * 正则Pattern（模型人数）
	 */
	public static final Pattern  PATTERN_PERSON_NUM = Pattern.compile(REG_PERSON_NUM); 
	
	/**
	 * 对客授权模型金额类型 0=无金额
	 */
	public static  final String IS_MONEY_TYPE_0 = "0";
	
	/**
	 * 对客授权模型金额类型 1=有金额
	 */
	public static  final String IS_MONEY_TYPE_1 = "1";
	
	/**
	 * 对客授权模型发送类型 0=手工发送
	 */
	public static  final String IS_SEND_TYPE_0 = "0";
	
	/**
	 * 对客授权模型发送类型 1=自动发送
	 */
	public static  final String IS_SEND_TYPE_1 = "1";
	
	/**
	 * 对客授权模型状态0=停用
	 */
	public static  final String AUTHMODEL_STATE_0 = "0";
	
	/**
	 * 对客授权模型状态1=正常
	 */
	public static  final String AUTHMODEL_STATE_1 = "1";

	/**
	 * 默认打印次数为0
	 */
	public static final String DEFAULT_PRINTNUM = "0";
}
