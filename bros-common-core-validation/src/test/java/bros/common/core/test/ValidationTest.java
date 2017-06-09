package bros.common.core.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.t.core.test.facade.service.ITTestValidation;

/**
 * 
 * @ClassName: ValidationTest 
 * @Description: 校验模板单元测试
 * @author 何鹏
 * @date 2016年8月15日 下午2:17:03 
 * @version 1.0
 */
public class ValidationTest {
	
	private ApplicationContext context;
	private Map<String,Object> returnMap = null;
	private Map<String,Object> headMap = null;
	private Map<String,Object> bodyMap = null;
	
	@Before
	public void setUp(){
		context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		
		returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> result1 = new HashMap<String, Object>();
		result1.put("branchCode", "1001");
		result1.put("branchName", "立水桥支行");
		list.add(result1);

		Map<String, Object> result2 = new HashMap<String, Object>();
		result2.put("branchCode", "1002");
		result2.put("branchName", "安德门支行");
		list.add(result2);
		
		returnMap.put("list", list);
		
		headMap = new HashMap<String,Object>();
		bodyMap = new HashMap<String,Object>();
		
		headMap.put("serviceName", "queryAllBranchManageAction");
		headMap.put("globalSeqNo", "301001111122222222223333333333");
		headMap.put("tranSeqNo", "3010011111222222222233333333334444444444");
		headMap.put("branchId", "4444");
		headMap.put("tranTellerNo", "55555");
		headMap.put("consumerId", "6666");
		headMap.put("tranDate", "20150910");
		headMap.put("tranTime", "8888888");
		headMap.put("channelDate", "99999999");
		headMap.put("channel", "2222");
		headMap.put("sceneCode", "1111");
		headMap.put("legalCode", "branchLegal-01");
		headMap.put("legalId", "123456789012345678901234567890123456");
		headMap.put("flag", "1");
		headMap.put("tradeType", "2");
		headMap.put("version", "1.0.0");
		
		bodyMap.put("cstNoBody", "123456");
		
	}
	/**
	 * 
	 * @Title: notValidation 
	 * @Description: 不校验测试
	 * @throws Exception
	 */
	@Test
	public void notValidation()throws Exception{
		String resultOld = returnMap.toString();
		String result = "";
		try{
			ITTestValidation ttestValidation = (ITTestValidation) context.getBean("ttestValidation");
			ResponseEntity entity = ttestValidation.getAllBranchNotValidation(headMap, bodyMap);
			result = entity.getResponseMap().toString();
		}catch(Exception e){
			
		}
		assertEquals(resultOld,result);
	}
	
	/**
	 * 
	 * @Title: validation 
	 * @Description: 校验正确测试
	 * @throws Exception
	 */
	@Test
	public void validation()throws Exception{
		String resultOld = returnMap.toString();
		String result = "";
		try{
			ITTestValidation ttestValidation = (ITTestValidation) context.getBean("ttestValidation");
			ResponseEntity entity = ttestValidation.getAllBranchValidation(headMap, bodyMap);
			result = entity.getResponseMap().toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(resultOld,result);
	}
	
	/**
	 * 
	 * @Title: validationNotModel 
	 * @Description: 校验模板不存在测试
	 * @throws Exception
	 */
	@Test
	public void validationNotModel()throws Exception{
		String errorCode = "";
		try{
			ITTestValidation ttestValidation = (ITTestValidation) context.getBean("ttestValidation");
			ttestValidation.getAllBranchValidationNotModel(headMap, bodyMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0019",errorCode);
	}
	

	@Test
	public void validationNotModel1()throws Exception{
		String errorCode = "";
		try{
			ITTestValidation ttestValidation = (ITTestValidation) context.getBean("ttestValidation");
			Map<String,Object> result = null;
			ttestValidation.getAllBranchValidationManyParams(headMap, bodyMap,result);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0001",errorCode);
	}
	@Test
	public void validationNotModel2()throws Exception{
		String errorCode = "";
		try{
			ITTestValidation ttestValidation = (ITTestValidation) context.getBean("ttestValidation");
			ttestValidation.getAllBranchValidationManyParams1(headMap, bodyMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0031",errorCode);
	}
	
}
