package bros.common.core.comm.constants;

/**
 * 
 * @ClassName: HeadRecieveConstants
 * @Description: 接入层上送报文头和返回报文头定义
 * @author 何鹏
 * @date 2016年7月1日 下午1:29:59
 * @version 1.0
 */
public class HeadRecieveConstants {	
	/**
	 * 接收报文头
	 */
	public static final String REQ_HEAD_PARAMS_NAME = "reqHead";
	/**
	 * 接收报文体
	 */
	public static final String REQ_BODY_PARAMS_NAME = "reqBody";
	/**
	 * 返回报文头
	 */
	public static final String RSP_HEAD_PARAMS_NAME = "rspHead";
	/**
	 * 返回报文体
	 */
	public static final String RSP_BODY_PARAMS_NAME = "rspBody";
	

	// 上送报文头
	/**
	 * 统一接入接入：服务名字段
	 */
	public static final String REC_SERVICE_NAME = "serviceName";
	/**
	 * 统一接入接入：调用服务版本号
	 */
	public static final String REC_SERVICE_VERSION = "version";

	
	
	
	// 返回报文头
	/**
	 * 统一接入返回：返回码
	 */
	public static final String SEN_RETURNCODE = "returnCode";
	/**
	 * 统一接入返回：返回信息
	 */
	public static final String SEN_RETURNMSG = "returnMsg";

	/**
	 * 统一接入返回：发起端交易日期
	 */
	public static final String SEN_CHANNELDATE = "channelDate";
	/**
	 * 统一接入返回：交易日期
	 */
	public static final String SEN_TRANDATE = "tranDate";
	/**
	 * 统一接入返回：交易时间
	 */
	public static final String SEN_TRANTIME = "tranTime";
	/**
	 * 统一接入返回：后台流水号
	 */
	public static final String SEN_BACKENDSEQNO = "backendSeqNo";
	/**
	 * 统一接入返回：后台系统ID
	 */
	public static final String SEN_BACKENDSYSID = "backendSysId";
	/**
	 * 统一接入返回：全局流水号
	 */
	public static final String SEN_GLOBALSEQNO = "globalSeqNo";
	/**
	 * 服务提供方返回报文头：会计日期
	 */
	public static final String SEN_ACCDATE = "acctDate";
	/**
	 * 服务提供方返回报文头：用户语言
	 */
	public static final String SEN_LANGCODE = "langCode";
	/**
	 * 服务提供方返回报文头：扩展内容
	 */
	public static final String SEN_RSRVCONTENT = "rsrvContent";
}
