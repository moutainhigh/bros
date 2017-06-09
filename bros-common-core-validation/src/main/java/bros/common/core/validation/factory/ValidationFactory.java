package bros.common.core.validation.factory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bros.common.core.constants.BaseErrorCodeConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.common.core.validation.bean.ValidationEntity;
import bros.common.core.validation.library.ValidationLibrary;



/**
 * 
 * @ClassName: ValidationFactory 
 * @Description: 校验工厂类（直接加载校验模板文件到HashMap中）
 * @author 何鹏
 * @date 2016年5月19日 上午11:53:35 
 * @version 1.0
 */
public class ValidationFactory {
	
//	@SuppressWarnings("unused")
//	private static final  Logger logger = LoggerFactory.getLogger(ValidationFactory.class);
//	/**
//	 * 校验定义缓存对象（主键值就是文件名）
//	 */
//	private static Map<String, Object> validatinMap = null;
//	
//	/**
//	 * 
//	 * @Title: initFormat 
//	 * @Description: 初始化校验模板定义文件
//	 * @param filelist 文件列表
//	 * @throws ServiceException
//	 */
//	public static void initFormat(File[] filelist) throws ServiceException{
//		//初始化校验模板定义缓存对象,以文件名为键值
//		validatinMap = new HashMap<String, Object>();
//		//解析报文格式定义
//		for(int i=0;i<filelist.length;i++) {
//				getConfig(filelist[i], validatinMap);
//		}
//	}
//	
//	/**
//	 * 
//	 * @Title: getConfig 
//	 * @Description: 根据文件读取模板，解析后放入到列表中（解析一个模板）
//	 * @param file 文件对象
//	 * @param result 校验定义缓存对象
//	 * @throws ServiceException
//	 */
//	@SuppressWarnings("unchecked")
//	private static void getConfig(File file, Map<String, Object> result) throws ServiceException{
//		try {
//			String fileName = file.getName();
//			fileName = fileName.substring(0, fileName.lastIndexOf("."));
//			
//			SAXReader saxReader = new SAXReader();
//			Document document = saxReader.read(file);
//			Element root = document.getRootElement();
//			
//			Map<String,ValidationEntity> entityMap = new HashMap<String, ValidationEntity>();
//			
//			for (Iterator<Element> iter = root.elementIterator(); iter.hasNext();) {
//				ValidationEntity entityTemp= new ValidationEntity();
//				Element element = (Element) iter.next();
//				String name = element.getName();
//				String dataName = element.attributeValue("dataName");
//				
//				if(ValidateUtil.isEmpty(dataName)){
//					throw new ServiceException(BaseErrorCodeConstants.CCOE0013, "标签dataName属性不存在");
//				}
//				
//				if(name.equalsIgnoreCase("xmlTag")){//变量为map
//					validationXmlTag(element,entityTemp);
//				}else if(name.equalsIgnoreCase("xmlList")){//变量为list
//					validationXmlList(element,entityTemp);
//				}else if(name.equalsIgnoreCase("xmlField")){//如果是第一级的化，将遍历号的method方法直接一索引为主键存储
//					validationXmlField(element,entityTemp);
//				}
//				
//				entityMap.put(dataName, entityTemp);
//			}
//			
//			if(entityMap.size()>0){
//				result.put(fileName, entityMap);
//			}
//		}catch(ServiceException ex){
//			throw ex;
//		}catch(Exception e){
//			throw new ServiceException(BaseErrorCodeConstants.CCOE0012,"解析验证模板失败",e);
//		}	
//	}
//	
//
//	/**
//	 * 
//	 * @Title: validationXmlTag 
//	 * @Description: 解析验证框架模板的XmlTag标签
//	 * @param element 元素
//	 * @param entity 数据存储实体
//	 * @throws ServiceException
//	 */
//	@SuppressWarnings("unchecked")
//	public static void validationXmlTag(Element element,ValidationEntity entity) throws ServiceException{
//		try{
//			
//			Map<String,Object> attrs = getElementAttrs(element);//当前元素的所有属性
//			attrs.put("tagName", "xmlTag");
//			List<ValidationEntity> nextEntityList = new ArrayList<ValidationEntity>();
//			
//			for (Iterator<Element> iterTag = element.elementIterator(); iterTag.hasNext();) {
//				Element item = iterTag.next();
//				String label = item.getName();
//				ValidationEntity nextEntity = null;
//				if("xmlTag".equals(label)){
//					nextEntity = new ValidationEntity();
//					validationXmlTag(item,nextEntity);
//				}else if("xmlList".equals(label)){
//					nextEntity = new ValidationEntity();
//					validationXmlList(item,nextEntity);
//				}else if("xmlField".equals(label)){
//					nextEntity = new ValidationEntity();
//					validationXmlField(item,nextEntity);
//				}
//				if(nextEntity != null){
//					nextEntityList.add(nextEntity);
//				}
//			}
//			
//			entity.setAttrs(attrs);
//			entity.setNextEntityList(nextEntityList);
//			
//		}catch(ServiceException ex){
//			throw ex;
//		}catch(Exception e){
//			throw new ServiceException(BaseErrorCodeConstants.CCOE0014,"解析xmlTag标签失败",e);
//		}	
//	}
//	
//
//	/**
//	 * 
//	 * @Title: validationXmlList 
//	 * @Description: 解析验证框架模板的XmlList标签
//	 * @param element 元素
//	 * @param entity 数据存储对象
//	 * @throws ServiceException
//	 */
//	@SuppressWarnings("unchecked")
//	public static void validationXmlList(Element element,ValidationEntity entity) throws ServiceException{
//		try{			
//			
//			Map<String,Object> attrs = getElementAttrs(element);//当前元素的所有属性
//			attrs.put("tagName", "xmlList");
//			List<ValidationEntity> nextEntityList = new ArrayList<ValidationEntity>();
//			
//			for (Iterator<Element> iterTag = element.elementIterator(); iterTag.hasNext();) {
//				Element item = iterTag.next();
//				String label = item.getName();
//				ValidationEntity nextEntity = null;
//				
//				if("xmlTag".equals(label)){
//					nextEntity = new ValidationEntity();
//					validationXmlTag(item,nextEntity);
//				}else if("xmlList".equals(label)){
//					nextEntity = new ValidationEntity();
//					validationXmlList(item,nextEntity);
//				}else if("xmlField".equals(label)){
//					nextEntity = new ValidationEntity();
//					validationXmlField(item,nextEntity);
//				}
//				
//				if(nextEntity != null){
//					nextEntityList.add(nextEntity);
//				}
//			}
//			
//			entity.setAttrs(attrs);
//			entity.setNextEntityList(nextEntityList);
//			
//		}catch(ServiceException ex){
//			throw ex;
//		}catch(Exception e){
//			throw new ServiceException(BaseErrorCodeConstants.CCOE0015,"解析xmlList标签失败",e);
//		}	
//	}
//	
//
//	/**
//	 * 
//	 * @Title: validationXmlField 
//	 * @Description: 解析验证框架模板的XmlField标签
//	 * @param element 元素对象
//	 * @param entity 存储数据的实体
//	 * @throws ServiceException
//	 */
//	@SuppressWarnings("rawtypes")
//	public static void validationXmlField(Element element,ValidationEntity entity) throws ServiceException{
//		try{
//				String dataName = element.attributeValue("dataName");//xmlField必须含有dataName值
//				
//				if(ValidateUtil.isEmpty(dataName)){
//					throw new ServiceException(BaseErrorCodeConstants.CCOE0013,"标签dataName属性不存在");
//				}
//				
//				Map<String,Object> attrs = getElementAttrs(element);//当前元素的所有属性
//				
//				attrs.put("tagName", "xmlField");
//				//xmlField的子元素下的validator校验器属性
//				List<Map<String,Object>> validatorList = new ArrayList<Map<String,Object>>();
//				//便利xmlField下的所有validator子节点
//				for (Iterator i = element.elementIterator(); i.hasNext();) {
//					Element item = (Element) i.next();
//					Map<String,Object> validatorAttrs = getElementAttrs(item);
//					String className = (String) (validatorAttrs.get("class")==null?"":validatorAttrs.get("class"));//获取实现类
//					if(ValidateUtil.isEmpty(className)){
//						throw new ServiceException(BaseErrorCodeConstants.CCOE0017,"validator标签class属性未配置");
//					}
//					
//					if(className.indexOf(".")==-1){
//						className="bros.common.core.validation.library.impl."+className;
//					}
//					
//					try{
//						ValidationLibrary validationLibrary = (ValidationLibrary) Class.forName(className).newInstance();//创建校验对象
//						validatorAttrs.put("validationLibrary", validationLibrary);
//					}catch(Exception e){
//						throw new ServiceException(BaseErrorCodeConstants.CCOE0018, "校验对象创建失败", e);
//					}
//					
//					validatorList.add(validatorAttrs);
//				}
//				
//				entity.setAttrs(attrs);
//				entity.setValidatorList(validatorList);
//		}catch(ServiceException ex){
//			throw ex;
//		}catch(Exception e){
//			throw new ServiceException(BaseErrorCodeConstants.CCOE0016,"解析xmlField标签失败",e);
//		}		
//	}
//
//	/**
//	 * 
//	 * @Title: getElementAttrs 
//	 * @Description: 获取某个元素的所有属性
//	 * @param element
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	private static Map<String,Object> getElementAttrs(Element element) {
//		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
//		List<DefaultAttribute> list = element.attributes();//当前属性值
//		//遍历method里的所有属性
//		for(DefaultAttribute attr : list){
//			String label = attr.getName();
//			String value = attr.getValue();
//			attrs.put(label, value);
//		}
//		
//		//替换message中的er表达式
//		replaceMessageEl(attrs);
//				
//		return attrs;
//	}
//	
//	/**
//	 * 
//	 * @Title: replaceMessageEl 
//	 * @Description: 替换属性中的el表达式
//	 * @param attrs
//	 */
//	private static void replaceMessageEl(Map<String,Object> attrs){
//		if(attrs != null && attrs.size()>0){
//			String message = (String) (attrs.get("message")==null?"":attrs.get("message"));
//			if(!ValidateUtil.isEmpty(message)){
//				String regEx = "^[$][{].+[}]$";//是否是${}表达式
//				if(matchaPattern(message,regEx)){//需要去缓存中去对象
//					System.out.println("需要去缓存中取对象====="+message);
//				}else{
//					int length = attrs.size();
//					for(int i=0;i<length;i++){
//						String index = "{"+i+"}";
//		                int count = 0;
//						if(message.indexOf(index) != -1){//需要替换其中的表达式
//							for (Entry<String, Object> entry : attrs.entrySet()) {
//									if(count == i){
//										Object obj = entry.getValue();
//										if(null==obj || obj instanceof java.lang.String){
//											String value = (String) (obj==null?"":obj);
//											message = message.replace(index, value);
//										}
//										break;
//									}else{
//										count++;
//									}
//							}
//						}
//					}
//					attrs.put("message",message);
//				}
//			}
//		}
//	}
//	
//	/**
//	 * 
//	 * @Title: matchPattern 
//	 * @Description: 匹配正则表达式
//	 * @param str
//	 * @param regEx
//	 * @return
//	 */
//	private static boolean matchaPattern(String str,String regEx){
//		Pattern pattern = Pattern.compile(regEx);
//		Matcher matcher = pattern.matcher(str);
//		return matcher.find();
//	}
//	
//	/**
//	 * 
//	 * @Title: getValidationModel 
//	 * @Description: 根据校验文件名称获取校验模板对象
//	 * @param validationName 校验模板名称（初始化的主键值）
//	 * @return 对应的xml对象
//	 * @throws ServiceException
//	 */
//	@SuppressWarnings("unchecked")
//	public static Map<String,Object> getValidationModel(String validationName) throws ServiceException {
//		if(validatinMap==null) {
//			throw new ServiceException(BaseErrorCodeConstants.CCOE0019,"校验模板不存在");
//		}
//		return (Map<String,Object>) validatinMap.get(validationName);
//	}
//	
}
