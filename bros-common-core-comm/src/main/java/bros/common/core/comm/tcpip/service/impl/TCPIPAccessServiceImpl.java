package bros.common.core.comm.tcpip.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.exception.CommunicationException;
import bros.common.core.comm.exception.FormatException;
import bros.common.core.comm.exception.TimeOutException;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.route.service.ICommunicationService;
import bros.common.core.comm.tcpip.service.ITCPIPService;
import bros.common.core.crypto.service.impl.EncryptDecryptUtilImpl;
import bros.common.core.exception.ServiceException;

/**
 *
 * <b>功能描述：</b><br>
 * 该交易实现TCPIP通信服务，向主机发送请求，得到主机返回数据的功能。<br>
 * 首先将请求数据按照SendFormatName指定的格式打包，向主机发起请求，<br>
 * 然后接受主机的响应，把返回数据按照ReceiveFormatName指定的格式解包。<br>
 *
 * <b>参数说明:</b><br>
 * tcpipService--通讯服务<br>
 * formatService--格式化报文服务
 * timeOut--通讯超时时间(ms)<br>
 * identityField--报文唯一标识域<br>
 * encoding--字符编码<br>
 */
public class TCPIPAccessServiceImpl implements ICommunicationService{
	private static final  Logger logger = LoggerFactory.getLogger(TCPIPAccessServiceImpl.class);
	/**
	 * 标识是否在输出日志时将密码域屏蔽
	 */
	public static final boolean MASK_PASSWORD = true;
	/**
	 * TCP/IP通讯服务
	 */
	protected ITCPIPService tcpipService;
	/**
	 * 超时时间，以毫秒为单位
	 */
	protected int timeOut = 62 * 1000;
	/**
	 * 格式化报文服务
	 */
	protected ISeverFormatService formatService;	
	/**
	 * 报文唯一标识域
	 */
	protected String identityField;
	/**
	 * 通信中使用的字符编码
	 */
	protected String encoding;
	/**
	 * 报文校验码   md5密钥
	 */
	protected String mdKey;

	public TCPIPAccessServiceImpl() {
		super();
	}

	@Override
	public Map<String,Object> client(Map<String,Object> sendMap) throws ServiceException{
		try {
				// 准备发送报文体数据
				String sendMessage = null;						
				try
				{
					sendMessage = (String) formatService.format(sendMap);
				}
				catch ( FormatException ex )
				{
					logger.error(CommErrorCodeConstants.CCCM0005 + " 组包失败" + ex.getMessage());
					throw new ServiceException(CommErrorCodeConstants.CCCM0005, "组包失败", ex);
				}			
				if ( sendMessage == null )
				{
					sendMessage = "";
				}
				
				byte[] reqMsg;

				sendMessage = EncryptDecryptUtilImpl.getInstance().md5Encode(sendMessage, mdKey)+sendMessage;//添加32位校验位
				
				String tmp = (String)sendMessage;
				if( encoding == null ){
					reqMsg = tmp.getBytes();
				}
				else {
					reqMsg = tmp.getBytes( encoding );
				}
				
				byte[] repMsg = null;
				long beg = System.currentTimeMillis();
				
				Object identity = null;
				
				if( identityField != null ){
					identity = sendMap.get(identityField);
				}
				
				//发送和接收返回报文
				repMsg = tcpipService.sendAndWait(identity, reqMsg, timeOut );
				
				long curTime = System.currentTimeMillis();
				long intvl = curTime - beg;
				logger.info("TCPIP Communication ["+ this.getClass().getName() + "] takes "+String.valueOf(intvl)+"(ms)");
								
				String receivePackage = null;				
				if( encoding == null ){
					receivePackage = new String(repMsg);
				}
				else{
					receivePackage = new String(repMsg, encoding );
				}
				
				//校验报文是否合法
				String recieveValidatorCode = receivePackage.substring(0, 32);
				String recieveBody = receivePackage.substring(32);
				if(!EncryptDecryptUtilImpl.getInstance().md5DecodeValid(recieveValidatorCode, recieveBody, mdKey)){
//				shaoxu2017/06/07	throw new ServiceException(CommErrorCodeConstants.CCCM0013,"数据非法，被篡改");
				}
				
				
				Map<String,Object> returnMap = new HashMap<String, Object>();
				try{
					returnMap = formatService.unformat(recieveBody);
				}catch ( FormatException ex ){
					throw new ServiceException(CommErrorCodeConstants.CCCM0006, "解包失败", ex );
				}
				
				return returnMap;
		}catch(CommunicationException ire){//time Out
			throw new ServiceException(ire.getErrorCode(),ire.getErrorMsg(),ire);
		}catch(TimeOutException et){
			logger.error("Communication time out.", et);
			throw new ServiceException(CommErrorCodeConstants.CCCM0002, "通讯超时", et);
		}catch(ServiceException ex){
			logger.error("发送报文异常",ex);
			throw ex;
		}catch (Exception e) {
			logger.error("ZJKTCPIPAccessService:  Fail to sendAnd wait to host.", e);
			throw new ServiceException(CommErrorCodeConstants.CCCM0001, "通讯异常", e);
		}
	}
	
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	public int getTimeOut() {
		return timeOut;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setIdentityField(String identityField) {
		this.identityField = identityField;
	}	

	public String getIdentityField() {
		return identityField;
	}
	public ITCPIPService getTcpipService() {
		return tcpipService;
	}
	public void setTcpipService(ITCPIPService tcpipService) {
		this.tcpipService = tcpipService;
	}

	public ISeverFormatService getFormatService() {
		return formatService;
	}

	public void setFormatService(ISeverFormatService formatService) {
		this.formatService = formatService;
	}

	public String getMdKey() {
		return mdKey;
	}

	public void setMdKey(String mdKey) {
		this.mdKey = mdKey;
	}
	
}