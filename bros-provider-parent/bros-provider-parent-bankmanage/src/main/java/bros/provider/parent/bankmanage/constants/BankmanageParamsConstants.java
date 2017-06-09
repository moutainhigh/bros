package bros.provider.parent.bankmanage.constants;

import java.util.regex.Pattern;


/**
 * 
 * @ClassName: BankmanageParamsConstants 
 * @Description: 行内管理模块参数常量类
 * @author huangcanhui 
 * @date 2016年7月18日 下午1:35:03 
 * @version 1.0
 */
public class BankmanageParamsConstants {
	
	/**
	 * 正则（模型人数）
	 */
	public static final String  REG_PERSON_NUM = "^\\d+$"; 
	
	/**
	 * 正则Pattern（模型人数）
	 */
	public static final Pattern  PATTERN_PERSON_NUM = Pattern.compile(REG_PERSON_NUM); 
	
	/**
	 * 内部授权模型 授权方式 0=额度
	 */
	public static  final String AUTH_MODEL_0= "0";
	
	/**
	 * 内部授权模型 授权方式 1=强制
	 */
	public static  final String AUTH_MODEL_1 = "1";
	
	/**
	 * 内部授权模型 授权方式 2=条件
	 */
	public static  final String AUTH_MODEL_2 = "2";
	
	/**
	 * 内部授权模型状态0=停用
	 */
	public static  final String AUTHMODEL_STATE_0 = "0";
	
	/**
	 * 内部授权模型状态1=正常
	 */
	public static  final String AUTHMODEL_STATE_1 = "1";

}
