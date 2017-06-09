package bros.common.core.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import bros.common.core.exception.ServiceException;
import bros.common.core.validation.library.impl.IsEmail;
import bros.common.core.validation.library.impl.IsEmpty;
import bros.common.core.validation.library.impl.IsMobile;
import bros.common.core.validation.library.impl.IsRamdomLength;
import bros.common.core.validation.library.impl.RegexMatcher;

public class ValidationLibraryTest {
	private IsEmail isEmail;
	private IsEmpty isEmpty;
	private IsMobile isMobile;
	private IsRamdomLength isRandomLength;
	private RegexMatcher regexMatcher;
	
	@Before
	public void setUp(){
		isEmail = new IsEmail();
		isEmpty = new IsEmpty();
		isMobile = new IsMobile();
		isRandomLength = new IsRamdomLength();
		regexMatcher = new RegexMatcher();
	}
	
	@Test
	public void validationIsEmail(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		String str = "438895913@qq.com";
		boolean bool=true;
		try{
			bool = isEmail.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsNotEmail(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		String str = "4111111111qq.com";
		boolean bool=true;
		try{
			bool = isEmail.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	//===============================================
	@Test
	public void validationIsEmpty(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		String str = "4111111111";
		boolean bool=true;
		try{
			bool = isEmpty.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsNotEmpty(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		String str = "    ";
		boolean bool=true;
		try{
			bool = isEmpty.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	@Test
	public void validationIsNotNullEmpty(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		String str = null;
		boolean bool=true;
		try{
			bool = isEmpty.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	//====================================
	@Test
	public void validationIsMobile(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		String str = "18810315678";
		boolean bool=true;
		try{
			bool = isMobile.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsNotMobile(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		String str = "123123123123";
		boolean bool=true;
		try{
			bool = isMobile.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	//====================================
	@Test
	public void validationIsRLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("min", "2");
		attrMap.put("symbol","r");
		
		String str = "123";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsR1Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("min", "2");
		attrMap.put("symbol","r");
		
		String str = "123456";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	@Test
	public void validationIsR2Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("min", "2");
		attrMap.put("symbol","r");
		
		String str = "1";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}

	
	@Test
	public void validationIsRSymbolLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("min", "2");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	@Test
	public void validationIsRMaxNullLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "2");
		attrMap.put("symbol","r");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	@Test
	public void validationIsRMinNullLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("symbol","r");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	@Test
	public void validationIsNotRLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("min", "2");
		attrMap.put("symbol","r");
		
		String str = "123123123123";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	@Test
	public void validationIsGLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("symbol","g");
		
		String str = "123456";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsG1Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("symbol","g");
		
		String str = "12345";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	@Test
	public void validationIsG2Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("symbol","g");
		
		String str = "1234556";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	
	@Test
	public void validationIsGMaxNullLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("symbol","g");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	@Test
	public void validationIsGeLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("symbol","ge");
		
		String str = "123456";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsGe1Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("symbol","ge");
		
		String str = "12345";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsGe2Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("max", "5");
		attrMap.put("symbol","ge");
		
		String str = "1234";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	
	@Test
	public void validationIsGeMaxNullLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("symbol","ge");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	@Test
	public void validationIsLLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","l");
		
		String str = "1234";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsL1Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","l");
		
		String str = "12345";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	@Test
	public void validationIsL2Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","l");
		
		String str = "123456";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	@Test
	public void validationIsLMinNullLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("symbol","l");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	@Test
	public void validationIsLeLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","le");
		
		String str = "1234";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsLe1Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","le");
		
		String str = "12345";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsLe2Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","le");
		
		String str = "123456";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	
	@Test
	public void validationIsLeMinNullLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("symbol","le");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	
	
	
	@Test
	public void validationIsELength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","e");
		
		String str = "12345";
		boolean bool=true;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationIsE1Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","e");
		
		String str = "123456";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	@Test
	public void validationIsE2Length(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("min", "5");
		attrMap.put("symbol","e");
		
		String str = "1234";
		boolean bool=false;
		try{
			bool = isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
	
	@Test
	public void validationIsEMinNullLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("symbol","e");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	@Test
	public void validationIsNotSymbolNullLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("symbol","eaaa");
		
		String str = "123";
		String errorCode = "";
		try{
			isRandomLength.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	
	//================================================
	@Test
	public void validationIsNotRegexMatcherLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		
		String str = "123";
		String errorCode = "";
		try{
			regexMatcher.validator(str, attrMap);
		}catch(ServiceException se){
			errorCode = se.getErrorCode();
		}
		assertEquals("CCOE0024",errorCode);
	}
	
	@Test
	public void validationIsRegexMatcherLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("pattern", "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[0-9])\\d{8}$");
		
		String str = "18810315678";
		boolean bool=true;
		try{
			bool = regexMatcher.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(false,bool);
	}
	
	@Test
	public void validationNotIsRegexMatcherLength(){
		Map<String,Object> attrMap = new HashMap<String, Object>();
		attrMap.put("pattern", "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[0-9])\\d{8}$");
		
		String str = "19910317180";
		boolean bool=false;
		try{
			bool = regexMatcher.validator(str, attrMap);
		}catch(ServiceException se){

		}
		assertEquals(true,bool);
	}
	
}
