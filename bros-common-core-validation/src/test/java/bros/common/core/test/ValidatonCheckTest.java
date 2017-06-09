package bros.common.core.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bros.common.core.exception.ServiceException;
import bros.common.core.validation.ValidationCheck;
import bros.common.core.validation.bean.ValidationEntity;
import bros.common.core.validation.library.ValidationLibrary;
import bros.common.core.validation.library.impl.IsEmpty;

public class ValidatonCheckTest {
	@SuppressWarnings("unused")
	private ApplicationContext context;
	
	@Before
	public void setUp(){
		context = new FileSystemXmlApplicationContext("classpath:spring/spring-context.xml");
	}
	
	@Test
	public void TestValidationCheck() throws Exception{
		try{
			
			Object[] objectArr = new Object[2];
			String[] paramArr = new String[2];
			String templeteName = "p0100001";
			ValidationCheck.validator(objectArr, paramArr, templeteName);
			
		}catch(ServiceException se){
			System.out.println(se.getErrorCode()+"|"+se.getErrorMsg());
		}
	}
	
	//====================replacePlaceholder测试=================
	@Test
	public void TestReplacePlaceholder() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String,Object>();
		String message = "{0}不能为空";
		attrs.put("dataName", "cstNoBody");
		String resultMessage = ValidationCheck.replacePlaceholder(attrs, message);
		assertEquals("cstNoBody不能为空", resultMessage);
	}
	
	@Test
	public void TestReplacePlaceholder1() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		String message = "{1}不能为空";
		attrs.put("min", "1");
		attrs.put("dataName", "cstNoBody");
		attrs.put("max", null);
		String resultMessage = ValidationCheck.replacePlaceholder(attrs,message);
		assertEquals("cstNoBody不能为空", resultMessage);
	}
	
	@Test
	public void TestReplacePlaceholder2() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		String message = "{1}不能为空";
		attrs.put("min", "1");
		attrs.put("dataName", null);
		attrs.put("max", null);
		String resultMessage = ValidationCheck.replacePlaceholder(attrs,message);
		assertEquals("{1}不能为空", resultMessage);
	}
	
	@Test
	public void TestReplacePlaceholder3() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		String message = "{1}不能为空";
		attrs.put("min", "1");
		attrs.put("dataName", 234);
		attrs.put("max", null);
		String resultMessage = ValidationCheck.replacePlaceholder(attrs,message);
		assertEquals("{1}不能为空", resultMessage);
	}
	
	@Test
	public void TestReplacePlaceholder4() throws Exception{
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		String message = "{1}不能为空";
		attrs.put("min", "1");
		attrs.put("dataName", "");
		attrs.put("max", null);
		String resultMessage = ValidationCheck.replacePlaceholder(attrs,message);
		assertEquals("不能为空", resultMessage);
	}
	//================basicTypeToString测试==========
	@Test
	public void TestBasicTypeToString() throws Exception{
		String str = "14";
		Byte bytes = Byte.parseByte(str);
		String desc = ValidationCheck.basicTypeToString(bytes);
		assertEquals("14", desc);
	}
	
	@Test
	public void TestBasicTypeToString1() throws Exception{
		Boolean bool = true;
		String desc = ValidationCheck.basicTypeToString(bool);
		assertEquals("true", desc);
	}
	
	@Test
	public void TestBasicTypeToString2() throws Exception{
		int i = 13;
		String desc = ValidationCheck.basicTypeToString(i);
		assertEquals("13", desc);
	}
	
	@Test
	public void TestBasicTypeToString3() throws Exception{
		Double d = 13d;
		String desc = ValidationCheck.basicTypeToString(d);
		assertEquals("13.0", desc);
	}
	
	@Test
	public void TestBasicTypeToString4() throws Exception{
		Float d = 13f;
		String desc = ValidationCheck.basicTypeToString(d);
		assertEquals("13.0", desc);
	}
	
	@Test
	public void TestBasicTypeToString5() throws Exception{
		Short s = 13;
		String desc = ValidationCheck.basicTypeToString(s);
		assertEquals("13", desc);
	}
	
	@Test
	public void TestBasicTypeToString6() throws Exception{
		Character c = new Character('a');
		String desc = ValidationCheck.basicTypeToString(c);
		assertEquals("a", desc);
	}
	
	@Test
	public void TestBasicTypeToString7() throws Exception{
		Long l = 1l;
		String desc = ValidationCheck.basicTypeToString(l);
		assertEquals("1", desc);
	}
	
	@Test
	public void TestBasicTypeToString9() throws Exception{
		String src = "我是一个兵,aaaaa,121";
		String desc = ValidationCheck.basicTypeToString(src);
		assertEquals(src, desc);
	}
	//======================getExceptionMessage测试=========
	@Test
	public void TestGetExceptionMessage() throws Exception{
		Map<String,Object> attrsMap = new LinkedHashMap<String, Object>();
		String message = "数据库操作错误";
		String result = ValidationCheck.getExceptionMessage(attrsMap, message);
		assertEquals(message, result);
	}
	
	@Test
	public void TestGetExceptionMessage1() throws Exception{
		Map<String,Object> attrsMap = new LinkedHashMap<String, Object>();
		attrsMap.put("dataName", "cstNo");
		String message = "${CECO000000001}";
		String result = ValidationCheck.getExceptionMessage(attrsMap, message);
		assertEquals("cstNo不能为空", result);
	}
	
	@Test
	public void TestGetExceptionMessage2() throws Exception{
		Map<String,Object> attrsMap = new LinkedHashMap<String, Object>();
		attrsMap.put("dataName", "cstNo");
		String message = "${CECO999999999}";
		String result = ValidationCheck.getExceptionMessage(attrsMap, message);
		assertEquals(message, result);
	}
	//=======================validatorXmlField测试=========
	@Test
	public void TestValidatorXmlField() throws Exception{
		List<Map<String, Object>> validatorList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		ValidationLibrary validationLibrary = new IsEmpty();
		map.put("dataName", "cstNo");
		map.put("validationLibrary", validationLibrary);
		map.put("message", "${CECO000000001}");
		validatorList.add(map);
		String obj = "aaa";
		String errorCode = "";
		try{
			ValidationCheck.validatorXmlField(obj, validatorList);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorXmlField1() throws Exception{
		List<Map<String, Object>> validatorList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		ValidationLibrary validationLibrary = new IsEmpty();
		map.put("dataName", "cstNo");
		map.put("validationLibrary", validationLibrary);
		map.put("message", "${CECO000000001}");
		validatorList.add(map);
		String obj = "";
		String errorCode = "";
		try{
			ValidationCheck.validatorXmlField(obj, validatorList);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024", errorCode);
	}
	
	@Test
	public void TestValidatorXmlField2() throws Exception{
		List<Map<String, Object>> validatorList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		ValidationLibrary validationLibrary = new IsEmpty();
		map.put("dataName", "cstNo");
		map.put("validationLibrary", validationLibrary);
		validatorList.add(map);
		String obj = "";
		String errorCode = "";
		try{
			ValidationCheck.validatorXmlField(obj, validatorList);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0025", errorCode);
	}
	//==========================validatorXmlList测试==========
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void TestValidatorXmlList() throws Exception{
		String errorCode = "";
		try{
			List list = new ArrayList();
			List<ValidationEntity> entityList = new ArrayList<ValidationEntity>();
			ValidationEntity entity = new ValidationEntity();
			Map<String,Object> attrs = new HashMap<String, Object>();
			attrs.put("dataName", "cstNo");
			attrs.put("tagName", "xmlField");
			attrs.put("message", "${CECO000000001}");
			entity.setAttrs(attrs);
			entityList.add(entity);
			
			list.add("12345");
			ValidationCheck.validatorXmlList(list, entityList);
		}catch(Exception e){
			errorCode = "q123123";
		}
		assertEquals("", errorCode);
	}
	
	//=======================validatorXmlTag测试===========
	@Test
	public void TestValidatorXmlTag() throws Exception{
		String errorCode = "";
		try{
			
			List<ValidationEntity> entityList = new ArrayList<ValidationEntity>();
			ValidationEntity entity = new ValidationEntity();
			Map<String,Object> attrs = new HashMap<String, Object>();
			attrs.put("dataName", "cstNo");
			attrs.put("tagName", "xmlField");
			attrs.put("message", "${CECO000000001}");
			entity.setAttrs(attrs);
			entityList.add(entity);
			
			Map<String,Object> mapTemp = new HashMap<String, Object>();
			mapTemp.put("cstNo", "123456");
			
			ValidationCheck.validatorXmlTag(mapTemp, entityList);
		}catch(Exception e){
			errorCode = "q123123";
		}
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorXmlTag1() throws Exception{
		String errorCode = "";
		try{
			
			List<ValidationEntity> entityList = new ArrayList<ValidationEntity>();
			ValidationEntity entity = new ValidationEntity();
			Map<String,Object> attrs = new HashMap<String, Object>();
			attrs.put("tagName", "xmlField");
			attrs.put("message", "${CECO000000001}");
			entity.setAttrs(attrs);
			entityList.add(entity);
			
			Map<String,Object> mapTemp = new HashMap<String, Object>();
			mapTemp.put("cstNo", "123456");
			
			ValidationCheck.validatorXmlTag(mapTemp, entityList);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("CCOE0013", errorCode);
	}
	
	//=============================validatorCombineTag测试=============
	@Test
	public void TestValidatorCombineTag() throws Exception{
		Object object = "123123";
		ValidationEntity entity = null;
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(Exception e){
			errorCode = "adfas";
		}
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag1() throws Exception{
		Object object = "123123";
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		entity.setAttrs(attrs);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(Exception e){
			errorCode = "adfas";
		}
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag2() throws Exception{
		Object object = null;
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		entity.setAttrs(attrs);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("CCOE0025", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag3() throws Exception{
		Object object = null;
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("CCOE0024", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag4() throws Exception{
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("cstNo", "123456");
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlTag");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag5() throws Exception{
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("cstNo", "123456");
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlTag");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		entity.setNextEntityList(null);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag6() throws Exception{
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("cstNo", "123456");
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlTag");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		entity.setNextEntityList(new ArrayList<ValidationEntity>());
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag7() throws Exception{
		List object = new ArrayList();
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlTag");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		entity.setNextEntityList(null);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("CCOE0026", errorCode);
	}
	
	
	@Test
	public void TestValidatorCombineTag8() throws Exception{
		List object = new ArrayList();
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlList");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		entity.setNextEntityList(null);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag9() throws Exception{
		List object = new ArrayList();
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlList");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		entity.setNextEntityList(new ArrayList<ValidationEntity>());
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		
		assertEquals("", errorCode);
	}
	
	
	@Test
	public void TestValidatorCombineTag10() throws Exception{
		Map<String,Object> object = new HashMap<String, Object>();
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlList");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		entity.setNextEntityList(new ArrayList<ValidationEntity>());
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("CCOE0026", errorCode);
	}
	
	
	@Test
	public void TestValidatorCombineTag11() throws Exception{
		List object = new ArrayList();
		object.add("123123");
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlList");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		List<ValidationEntity> tempList = new ArrayList<ValidationEntity>();
		ValidationEntity entity1 = new ValidationEntity();
		Map<String,Object> attrs1 = new HashMap<String, Object>();
		attrs1.put("tagName", "xmlField");
		attrs1.put("dataName", "cstNo");
		attrs1.put("message", "${CECO000000001}");
		entity1.setAttrs(attrs1);
		tempList.add(entity1);
		
		entity.setNextEntityList(tempList);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		
		assertEquals("", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag12() throws Exception{
		List object = new ArrayList();
		object.add("123123");
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlList");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		List<ValidationEntity> tempList = new ArrayList<ValidationEntity>();
		ValidationEntity entity1 = new ValidationEntity();
		Map<String,Object> attrs1 = new HashMap<String, Object>();
		attrs1.put("tagName", "xmlField");
		attrs1.put("dataName", "cstNo");
		attrs1.put("message", "${CECO000000001}");
		entity1.setAttrs(attrs1);
		tempList.add(entity1);
		
		ValidationEntity entity2 = new ValidationEntity();
		Map<String,Object> attrs2 = new HashMap<String, Object>();
		attrs2.put("tagName", "xmlField");
		attrs2.put("dataName", "cstNo");
		attrs2.put("message", "${CECO000000001}");
		entity2.setAttrs(attrs2);
		tempList.add(entity2);
		
		entity.setNextEntityList(tempList);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("CCOE0027", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag13() throws Exception{
		String object = "";
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlField");
		attrs.put("dataName", "cstNo");
		attrs.put("message", "${CECO000000001}");
		entity.setAttrs(attrs);
		entity.setNextEntityList(null);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("CCOE0024", errorCode);
	}
	
	@Test
	public void TestValidatorCombineTag14() throws Exception{
		String object = "";
		ValidationEntity entity = new ValidationEntity();
		Map<String,Object> attrs = new HashMap<String, Object>();
		attrs.put("tagName", "xmlField");
		attrs.put("dataName", "cstNo");
		entity.setAttrs(attrs);
		entity.setNextEntityList(null);
		String errorCode = "";
		try{
			ValidationCheck.validatorCombineTag(object, entity);
		}catch(ServiceException e){
			errorCode = e.getErrorCode();
		}
		assertEquals("CCOE0028", errorCode);
	}
}
