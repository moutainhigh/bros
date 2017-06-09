package bros.provider.auth;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.DateUtil;
import bros.p.auth.facade.service.IPInsideAuthServiceFacade;
import bros.p.auth.facade.service.IPOutSideAuthSerivceFacade;

/**
 * 
 * @ClassName: AuthDetailShowTest 
 * @Description: 授权详情模板数据测试
 * @author 何鹏
 * @date 2016年9月5日 下午4:59:37 
 * @version 1.0
 */
public class AuthDetailShowTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthDetailShowTest.class);
	/** 
	 * @Title: main 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param args 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		//pInsideAuthServiceMethod(context);   //行内授权
		pOutSideAuthServiceMethod(context);
	}
	
	private static void pOutSideAuthServiceMethod(ApplicationContext context){
		IPOutSideAuthSerivceFacade poutSideAuthServiceFacade = (IPOutSideAuthSerivceFacade) context.getBean("poutSideAuthServiceFacade");
		Map<String, Object> headMap = new HashMap<String, Object>();
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		headMap.put(HeadParameterDefinitionConstants.REC_GLOBALSEQNO,"000000000000000001469859469705");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANSEQNO,"0000000000000000014698594697051234567890");
		headMap.put(HeadParameterDefinitionConstants.REC_BRANCHID,"802001");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANTELLERNO,"01130");
		headMap.put(HeadParameterDefinitionConstants.REC_CONSUMERID,"1234");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANDATE,DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_TRANTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_CHANNELDATE,DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_CHANNEL,"1111");
		headMap.put(HeadParameterDefinitionConstants.REC_SCENECODE,"1234");
		headMap.put(HeadParameterDefinitionConstants.REC_LEGALCODE,"999999");
		headMap.put(HeadParameterDefinitionConstants.REC_TRADETYPE,"4");
		headMap.put(HeadParameterDefinitionConstants.REC_FLAG,"1");
		headMap.put(HeadParameterDefinitionConstants.REC_LEGALID,"6af5ca6b-36ee-47b3-b986-78249d0b8270");
		headMap.put(HeadParameterDefinitionConstants.REC_SUBMITSTARTTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_SUBMITENDTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_BSNCODE,"CB88888888");//90010106
		headMap.put(HeadParameterDefinitionConstants.REC_SERVICE,"");
		headMap.put(HeadParameterDefinitionConstants.REC_CSTNO,"123456");
		
		bodyMap.put("businessCode", "CB88888888");
		bodyMap.put("businessKey", "000000000000000001469859469705");
		
		try {
			ResponseEntity entity = poutSideAuthServiceFacade.qryAuthDetailPageDataByBusinessKey(headMap,bodyMap);
			logger.info(entity.getResponseMap().get("recordList").toString());
		} catch (ServiceException e) {
			logger.info("服务调用失败",e);
		}		
	}

	private static void pInsideAuthServiceMethod(ApplicationContext context) {
		IPInsideAuthServiceFacade pinsideAuthServiceFacade = (IPInsideAuthServiceFacade) context.getBean("pinsideAuthServiceFacade");
		
		Map<String, Object> headMap = new HashMap<String, Object>();
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		headMap.put(HeadParameterDefinitionConstants.REC_GLOBALSEQNO,"000000000000000001469859469705");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANSEQNO,"0000000000000000014698594697051234567890");
		headMap.put(HeadParameterDefinitionConstants.REC_BRANCHID,"802001");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANTELLERNO,"01130");
		headMap.put(HeadParameterDefinitionConstants.REC_CONSUMERID,"1234");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANDATE,DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_TRANTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_CHANNELDATE,DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_CHANNEL,"1111");
		headMap.put(HeadParameterDefinitionConstants.REC_SCENECODE,"1234");
		headMap.put(HeadParameterDefinitionConstants.REC_LEGALCODE,"999999");
		headMap.put(HeadParameterDefinitionConstants.REC_TRADETYPE,"4");
		headMap.put(HeadParameterDefinitionConstants.REC_FLAG,"1");
		headMap.put(HeadParameterDefinitionConstants.REC_LEGALID,"80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put(HeadParameterDefinitionConstants.REC_SUBMITSTARTTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_SUBMITENDTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_BSNCODE,"CB88888888");//90010106
		headMap.put(HeadParameterDefinitionConstants.REC_SERVICE,"");
		
		bodyMap.put("funcCode", "CB88888888");
		bodyMap.put("businessKey", "000000000000000001469859469705");
		try {
			
			ResponseEntity entity = pinsideAuthServiceFacade.qryAuthDetailPageDataByBusinessKey(headMap,bodyMap);
			logger.info(entity.getResponseMap().get("recordList").toString());
		} catch (ServiceException e) {
			logger.info("服务调用失败",e);
		}
	}

}
