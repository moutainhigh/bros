package bros.unified.receive.constants;

/**
 * 
 * @ClassName: RecieveErrorCodeConstants 
 * @Description: 统一接入模块错误码常量类
 * @author 何鹏
 * @date 2016年6月23日 上午10:15:35 
 * @version 1.0
 */
public class RecieveErrorCodeConstants {
	/**
	 * receive模块：系统错误
	 */
	public static final String UREE0000 = "UREE0000";
	/**
	 * receive模块：报文非json格式
	 */
	public static final String UREE0001 = "UREE0001";
	/**
	 * receive模块：业务处理未知错误
	 */
	public static final String UREE0002 = "UREE0002";
	/**
	 * receive模块：serviceName字段不能为空
	 */
	public static final String UREE0003 = "UREE0003";
	/**
	 * receive模块：version字段不能为空
	 */
	public static final String UREE0004 = "UREE0004";
	/**
	 * receive模块：请求报文头不存在
	 */
	public static final String UREE0005 = "UREE0005";
	/**
	 * receive模块：请求报文体不存在
	 */
	public static final String UREE0006 = "UREE0006";
	/**
	 * receive模块：请求报文头格式不正确
	 */
	public static final String UREE0007 = "UREE0007";
	/**
	 * receive模块：请求报文体格式不正确
	 */
	public static final String UREE0008 = "UREE0008";
	/**
	 * receive模块：返回对象不能为空
	 */
	public static final String UREE0009 = "UREE0009";
	/**
	 * receive模块：返回对象不为ResponseEntity
	 */
	public static final String UREE0010 = "UREE0010";
	/**
	 * receive模块：统一接入服务配置不正确
	 */
	public static final String UREE0011 = "UREE0011";
	/**
	 * receive模块：封装异常返回信息失败
	 */
	public static final String UREE0012 = "UREE0012";
	/**
	 * receive模块：服务版本获取失败
	 */
	public static final String UREE0013 = "UREE0013";
	/**
	 * receive模块：legalId字段不能为空
	 */
	public static final String UREE0014 = "UREE0014";
	/**
	 * receive模块：未配置服务版本信息，请到消费方的bma_version_config表中进行配置
	 */
	public static final String UREE0015 = "UREE0015";
	/**
	 * receive模块：channel字段不能为空
	 */
	public static final String UREE0016 = "UREE0016";
}
