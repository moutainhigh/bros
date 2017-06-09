package bros.common.core.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.exception.ServiceException;
import bros.common.core.validation.factory.ValidationCacheFactory;
/**
 * 
 * @ClassName: ValidationModelXmlTest 
 * @Description: 从xml中获取校验模板单元测试
 * @author 何鹏
 * @date 2016年8月15日 上午10:34:42 
 * @version 1.0
 */
public class ValidationModelXmlTest{
	
	@SuppressWarnings("unused")
	private ApplicationContext context;
	private ValidationCacheFactory validationCacheFactory;
	
	@Before
	public void setUp(){
		context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
		validationCacheFactory = new ValidationCacheFactory();
	}
	
	/**
	 * 
	 * @throws Exception 
	 * @Title: TestGetValidationModelFromExistXml 
	 * @Description: 从存在的xml中获取校验模板
	 */
	@Test
	public void TestGetValidationModelFromExistXml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100001.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);
		assertNotNull(map);
	}
	
	/**
	 * 
	 * @Title: TestGetValidationModelFromNotExistXml 
	 * @Description: 从不存在的xml中获取校验模板
	 * @throws Exception
	 */
	@Test
	public void TestGetValidationModelFromNotExistXml() throws Exception{
		Map<String,Object> map = null;
		try{
				String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
				File headFile = new File(headPath);
				
				String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100002.xml";
				File modelFile = new File(modelPath);
				
				File[] fileArray = new File[2];
				fileArray[0] = headFile;
				fileArray[1] = modelFile;
				
				map = ValidationCacheFactory.initFormat(fileArray);
		}catch(Exception e){
			
		}
		
		assertNull(map);
	}
	
	/**
	 * 
	 * @Title: TestGetValidatonModelFromErrorXml 
	 * @Description: 从存在的xml中获取错误的校验模板
	 * @throws Exception
	 */
	@Test
	public void TestGetValidatonModelFromErrorXml() throws Exception{
		Map<String,Object> map = null;
		try{
			String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
			File headFile = new File(headPath);
			
			String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100003.xml";
			File modelFile = new File(modelPath);
			
			File[] fileArray = new File[2];
			fileArray[0] = headFile;
			fileArray[1] = modelFile;
			
			map = ValidationCacheFactory.initFormat(fileArray);
		}catch(Exception e){
			
		}
		assertNull(map);
	}

	@Test
	public void TestGetValidationModelFromNullXml() throws Exception{
		File[] fileArray = new File[0];
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);
		assertTrue(map.size()==0);
	}
	
	@Test
	public void TestGetValidationModelFromNull1Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100004.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		String errorCode = "";
		try{
			ValidationCacheFactory.initFormat(fileArray);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0013", errorCode);
	}
	
	@Test
	public void TestGetValidationModelFromNull2Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100005.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);
		
		assertNotNull(map);
	}
	
	@Test
	public void TestGetValidationModelFromNull3Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100006.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		String errorCode = "";
		try{
			ValidationCacheFactory.initFormat(fileArray);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		
		assertEquals("CCOE0017",errorCode);
	}
	
	
	@Test
	public void TestGetValidationModelFromNull4Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100007.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		String errorCode = "";
		try{
			ValidationCacheFactory.initFormat(fileArray);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		
		assertEquals("CCOE0018",errorCode);
	}
	
	@Test
	public void TestGetValidationModelFromNull5Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100008.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);

		assertNotNull(map);
	}
	
	@Test
	public void TestGetValidationModelFromNull6Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100009.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);

		assertNotNull(map);
	}
	
	@Test
	public void TestGetValidationModelFromNull7Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100010.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);

		assertNotNull(map);
	}
	
	@Test
	public void TestGetValidationModelFromNull8Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100011.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);

		assertNotNull(map);
	}
	
	@Test
	public void TestGetValidationModelFromNull9Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100012.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);

		assertNotNull(map);
	}
	
	@Test
	public void TestGetValidationModelFromNull10Xml() throws Exception{
		String headPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/head.xml";
		File headFile = new File(headPath);
		
		String modelPath = ValidationModelXmlTest.class.getResource("/").getPath()+"validation/p0100013.xml";
		File modelFile = new File(modelPath);
		
		File[] fileArray = new File[2];
		fileArray[0] = headFile;
		fileArray[1] = modelFile;
		Map<String,Object> map = ValidationCacheFactory.initFormat(fileArray);

		assertNotNull(map);
	}
	//====================replacePlaceholder测试======================
	@SuppressWarnings("static-access")
	@Test
	public void TestReplacePlaceholder() throws Exception{
		Map<String,Object> attrs = new HashMap<String, Object>();
		String message = "{0}不能为空";
		attrs.put("dataName", "cstNoBody");
		
		validationCacheFactory.replacePlaceholder(attrs,message);
		assertEquals("cstNoBody不能为空", attrs.get("message").toString());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestReplacePlaceholder1() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		String message = "{1}不能为空";
		attrs.put("min", "1");
		attrs.put("dataName", "cstNoBody");
		attrs.put("max", null);
		
		validationCacheFactory.replacePlaceholder(attrs,message);
		assertEquals("cstNoBody不能为空", attrs.get("message").toString());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestReplacePlaceholder2() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		String message = "{1}不能为空";
		attrs.put("min", "1");
		attrs.put("dataName", null);
		attrs.put("max", null);
		
		validationCacheFactory.replacePlaceholder(attrs,message);
		assertEquals("{1}不能为空", attrs.get("message").toString());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestReplacePlaceholder3() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		String message = "{1}不能为空";
		attrs.put("min", "1");
		attrs.put("dataName", 234);
		attrs.put("max", null);
		
		validationCacheFactory.replacePlaceholder(attrs,message);
		assertEquals("{1}不能为空", attrs.get("message").toString());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestReplacePlaceholder4() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		String message = "{1}不能为空";
		attrs.put("min", "1");
		attrs.put("dataName", "");
		attrs.put("max", null);
		
		validationCacheFactory.replacePlaceholder(attrs,message);
		assertEquals("不能为空", attrs.get("message").toString());
	}
	//=====================replaceMessageEl测试========
	@SuppressWarnings("static-access")
	@Test
	public void TestReplaceMessageEl() throws Exception{
			Map<String,Object> attrs = new LinkedHashMap<String, Object>();
			validationCacheFactory.replaceMessageEl(attrs);
			assertTrue(attrs.size()==0);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestReplaceMessageEl1() throws Exception{
			Map<String,Object> attrs = null;
			validationCacheFactory.replaceMessageEl(attrs);
			assertNull(attrs);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestReplaceMessageEl2() throws Exception{
			Map<String,Object> attrs = new LinkedHashMap<String, Object>();
			attrs.put("message", null);
			validationCacheFactory.replaceMessageEl(attrs);
			assertNull(attrs.get("message"));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestReplaceMessageEl3() throws Exception{
			Map<String,Object> attrs = new LinkedHashMap<String, Object>();
			attrs.put("message", "cstNoBody不能为空");
			validationCacheFactory.replaceMessageEl(attrs);
			assertEquals("cstNoBody不能为空",attrs.get("message").toString());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestReplaceMessageEl4() throws Exception{
			Map<String,Object> attrs = new LinkedHashMap<String, Object>();
			attrs.put("message", "{1}不能为空");
			attrs.put("dataName", "cstNoBody");
			attrs.put("min", "1");
			attrs.put("max", null);
			validationCacheFactory.replaceMessageEl(attrs);
			assertEquals("cstNoBody不能为空",attrs.get("message").toString());
	}
	
	//=====================getElementAttrs测试==============
	@SuppressWarnings("static-access")
	@Test
	public void TestGetElementAttrs() throws Exception{
		
	}
}
