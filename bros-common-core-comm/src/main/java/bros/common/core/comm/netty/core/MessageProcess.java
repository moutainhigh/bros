package bros.common.core.comm.netty.core;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import bros.common.core.comm.business.detail.IBusinessProcessService;
import bros.common.core.comm.constants.CommErrorCodeConstants;
import bros.common.core.comm.constants.HeadRecieveConstants;
import bros.common.core.comm.format.ISeverFormatService;
import bros.common.core.comm.netty.config.AppConfig;
import bros.common.core.crypto.service.impl.EncryptDecryptUtilImpl;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: MessageProcess 
 * @Description: 交易处理调度中心
 * @author 何鹏
 * @date 2016年7月16日 下午9:30:33 
 * @version 1.0
 */
public class MessageProcess {
	
	private final static Logger logger = LoggerFactory.getLogger(MessageProcess.class);
	/**
	 * 
	 * @Title: process 
	 * @Description: 交易调度执行
	 * @param context	spring的context容器
	 * @param businessProcessService	业务处理解析器
	 * @param formatService	报文处理解析器
	 * @param object	收到报文
	 * @return
	 */
	public static String process(final ApplicationContext context,IBusinessProcessService businessProcessService,ISeverFormatService formatService,Object object,AppConfig config){
		String returnMessage = "";
		Map<String,Object> recieveMap = null;//接收变量
		Map<String,Object> returnMap = new HashMap<String, Object>();//返回Map对象
		Map<String,Object> exceptionHeadMap = new HashMap<String, Object>();
		Map<String,Object> exceptionBodyMap = new HashMap<String, Object>();

		String mdKey = config.getMdKey();//校验位校验主密钥
		
		try {
			//进行报文校验
			String recieveMessage = object.toString();
			String recieveValidatorCode = recieveMessage.substring(0,32);//报文传送过来的校验码
			String recieveBody = recieveMessage.substring(32);
			
			if(!EncryptDecryptUtilImpl.getInstance().md5DecodeValid(recieveValidatorCode, recieveBody, mdKey)){
				throw new ServiceException(CommErrorCodeConstants.CCCM0013,"数据非法，被篡改");
			}
			
			try{
				recieveMap = formatService.unformat(recieveBody);
			}catch(Exception e){
				throw new ServiceException(CommErrorCodeConstants.CCCM0007,"报文非json格式");
			}
			//调用业务处理器
			Map<String,Object> result = businessProcessService.businessDetail(context,recieveMap,config);
			returnMap.putAll(result);
			
		} catch(ServiceException se){
			exceptionHeadMap.put(HeadRecieveConstants.SEN_RETURNCODE, se.getErrorCode());
			exceptionHeadMap.put(HeadRecieveConstants.SEN_RETURNMSG, se.getErrorMsg());
			returnMap.put(HeadRecieveConstants.RSP_HEAD_PARAMS_NAME, exceptionHeadMap);
			returnMap.put(HeadRecieveConstants.RSP_BODY_PARAMS_NAME, exceptionBodyMap);
			logger.info("bussiness detail failure:{}", se);
		}catch (Exception ex) {
			exceptionHeadMap.put(HeadRecieveConstants.SEN_RETURNCODE, CommErrorCodeConstants.CCCM0000);
			exceptionHeadMap.put(HeadRecieveConstants.SEN_RETURNMSG, "系统错误");
			returnMap.put(HeadRecieveConstants.RSP_HEAD_PARAMS_NAME, exceptionHeadMap);
			returnMap.put(HeadRecieveConstants.RSP_BODY_PARAMS_NAME, exceptionBodyMap);
			logger.info("bussiness detail failure:{}", ex);
		}finally{
			try{
				returnMessage = formatService.format(returnMap);
				//returnMessage = Crc32Util.getFixedLengthCrc(returnMessage)+returnMessage;
				returnMessage = EncryptDecryptUtilImpl.getInstance().md5Encode(returnMessage, mdKey)+returnMessage;
			}catch(Exception e){
				logger.info("package format failure:{}","组包失败",e);
			}
		}
		
		return returnMessage;
	}
}
