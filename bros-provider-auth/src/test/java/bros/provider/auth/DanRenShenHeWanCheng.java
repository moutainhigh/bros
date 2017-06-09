package bros.provider.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.DateUtil;
import bros.p.auth.facade.service.IPInsideAuthServiceFacade;

/** 
 * @ClassName: DanRenShenHeWanCheng 
 * @Description: 这里用一句话描述这个类的作用
 * @author weiyancheng
 * @date 2016年8月5日 下午3:09:25 
 * @version 1.0 
 */
public class DanRenShenHeWanCheng {

	public static void main(String[] args) throws ServiceException {
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		IPInsideAuthServiceFacade pinsideAuthServiceFacade = (IPInsideAuthServiceFacade) context.getBean("pinsideAuthServiceFacade");
		Map<String, Object> headMap = new HashMap<String, Object>();
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		headMap.put(HeadParameterDefinitionConstants.REC_GLOBALSEQNO,"g23456789012345678901234567892");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANSEQNO,"g234567890123456789012345678921234567890");
		headMap.put(HeadParameterDefinitionConstants.REC_BRANCHID,"802001");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANTELLERNO,"01131");
		headMap.put(HeadParameterDefinitionConstants.REC_CONSUMERID,"1234");
		headMap.put(HeadParameterDefinitionConstants.REC_TRANDATE,DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_TRANTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_CHANNELDATE,DateUtil.getServerTime(DateUtil.DEFAULT_DATE_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_CHANNEL,"1111");
		headMap.put(HeadParameterDefinitionConstants.REC_SCENECODE,"4321");
		headMap.put(HeadParameterDefinitionConstants.REC_LEGALCODE,"999999");
		headMap.put(HeadParameterDefinitionConstants.REC_TRADETYPE,"4");
		headMap.put(HeadParameterDefinitionConstants.REC_FLAG,"1");
		headMap.put(HeadParameterDefinitionConstants.REC_LEGALID,"80fb68c1-fce5-440d-85a3-9c392ba1ba83");
		headMap.put(HeadParameterDefinitionConstants.REC_SUBMITSTARTTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_SUBMITENDTIME,DateUtil.getServerTime(DateUtil.DEFAULT_TIME_FORMAT));
		headMap.put(HeadParameterDefinitionConstants.REC_BSNCODE,"CB99999991");
		headMap.put(HeadParameterDefinitionConstants.REC_SERVICE,"");
		
		bodyMap.put("funcCode", "CB02030003");
		bodyMap.put("pageNo", "0");
		bodyMap.put("pageSize", "10");
		ResponseEntity ree = pinsideAuthServiceFacade.qryAuthQueueList(headMap, bodyMap);
		
		String taskId = (String) ((List<Map<String, Object>>)ree.getResponseMap().get("resultLit")).get(0).get("taskId");
		bodyMap.clear();
		bodyMap.put("taskId", taskId);
		bodyMap.put("isPass", "1");
		bodyMap.put("authOpinion", "通过");
		ResponseEntity re = pinsideAuthServiceFacade.authSingleCompleteTask(headMap, bodyMap);

		
		System.out.println(re.getResponseMap().toString());

	}

}
