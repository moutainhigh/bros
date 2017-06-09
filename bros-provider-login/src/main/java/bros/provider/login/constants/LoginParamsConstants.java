package bros.provider.login.constants;

/**
 * 
 * @ClassName: LoginParamsConstants 
 * @Description: 登录模块常量类
 * @author huangcanhui 
 * @date 2016年10月8日 下午11:46:31 
 * @version 1.0
 */
public class LoginParamsConstants {
	
	/**
	 * 个人登录方式 1-昵称[别名]
	 */
	public static final String PERSON_LOGIN_TYPE_1 = "1";
	/**
	 * 个人登录方式 2-证件号码
	 */
	public static final String PERSON_LOGIN_TYPE_2 = "2";
	/**
	 * 个人登录方式 3-账号
	 */
	public static final String PERSON_LOGIN_TYPE_3 = "3";
	/**
	 * 个人登录方式 4-手机号
	 */
	public static final String PERSON_LOGIN_TYPE_4 = "4";
	
	/**
	 * 个人客户状态 0-正常
	 */
	public static final String PERSON_CUSTOMER_STATE_0 = "0";
	/**
	 * 个人客户状态 1-暂停
	 */
	public static final String PERSON_CUSTOMER_STATE_1 = "1";
	/**
	 * 个人客户状态 2-冻结(密码连续输错超限;用户永久停用操作;内管冻结操作)
	 */
	public static final String PERSON_CUSTOMER_STATE_2 = "2";
	/**
	 * 个人客户状态 3-注销
	 */
	public static final String PERSON_CUSTOMER_STATE_3 = "3";
	
	/**
	 * 个人客户类型 OL-网上签约客户
	 */
	public static final String PERSON_CUSTOMER_TYPE_OL = "OL";
	/**
	 * 个人客户类型 TL-柜台签约客户
	 */
	public static final String PERSON_CUSTOMER_TTYPE_TL = "TL";
	
	/**
	 * 个人登录结果类型  0-正常登录
	 */	
	public static final String PERSON_LOGON_RESULT_0 = "0";
	/**
	 * 个人登录结果类型  1-首次登录
	 */	
	public static final String PERSON_LOGON_RESULT_1 = "1";
	/**
	 * 个人登录结果类型  2-临时停用
	 */	
	public static final String PERSON_LOGON_RESULT_2 = "2";
	/**
	 * 个人登录结果类型  3-密码3个月未修改
	 */	
	public static final String PERSON_LOGON_RESULT_3 = "3";
	/**
	 * 个人登录结果类型  4-重置私密问题
	 */	
	public static final String PERSON_LOGON_RESULT_4 = "4";
	/**
	 * 个人登录结果类型  5-强制修改密码
	 */	
	public static final String PERSON_LOGON_RESULT_5 = "5";
	
	/**
	 * 个人当日密码最多失败次数
	 */
	public static final int PERSON_DAY_PWD_MAX_ERR_TIMES = 5;
	/**
	 * 个人密码累计最多失败次数
	 */
	public static final int PERSON_SUM_PWD_MAX_ERR_TIMES = 10;
	
	/**
	 * 个人重置私密问题标识  0-否
	 */	
	public static final String PERSON_QUESTION_RESET_0 = "0";
	/**
	 * 个人重置私密问题标识  1-是
	 */	
	public static final String PERSON_QUESTION_RESET_1 = "1";
	
	/**
	 * 个人重置密码标识  0-否
	 */	
	public static final String PERSON_PASSWORD_RESET_0 = "0";
	/**
	 * 个人重置密码标识  1-是
	 */	
	public static final String PERSON_PASSWORD_RESET_1 = "1";
}
