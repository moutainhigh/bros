package bros.provider.auth;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.constants.BodyParameterDefinitionConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.DateUtil;
import bros.p.auth.facade.service.IPInsideAuthServiceFacade;

/** 
 * @ClassName: AuthTest 
 * @Description: 这里用一句话描述这个类的作用
 * @author weiyancheng
 * @date 2016年8月2日 下午3:30:23 
 * @version 1.0 
 */
public class AuthTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthTest.class);
	/** 
	 * @Title: main 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param args 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		IPInsideAuthServiceFacade testService = (IPInsideAuthServiceFacade) context.getBean("testService");
		
		Map<String, Object> headMap = new HashMap<String, Object>();
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		headMap.put(HeadParameterDefinitionConstants.REC_GLOBALSEQNO,"998456789012345678901234567892");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANSEQNO,"9984567890123456789012345678921234567890");
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
		headMap.put(HeadParameterDefinitionConstants.REC_BSNCODE,"CB02030003");
		headMap.put(HeadParameterDefinitionConstants.REC_SERVICE,"");
		
		bodyMap.put(BodyParameterDefinitionConstants.BODY_TRANSAMT, "200.00");
		try {
			ResponseEntity re = testService.qryTaskCenterView(headMap, bodyMap);
			logger.info(re.toString());
			System.out.println(re.toString());
		} catch (ServiceException e) {
			logger.info("服务调用失败",e);
		}

	}

}
