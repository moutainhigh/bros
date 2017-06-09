package bros.common.core.validation.factory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
 * @ClassName: ValidationCacheFactory 
 * @Description: 校验工厂类（直接加载校验模板文件到缓存中）
 * @author 何鹏
 * @date 2016年5月19日 上午11:53:35 
 * @version 1.0
 */
public class ValidationCacheFactory {
	
	@SuppressWarnings("unused")
	private static final  Logger logger = LoggerFactory.getLogger(ValidationCacheFactory.class);
	
	/**
	 * 
	 * @Title: initFormat 
	 * @Description: 初始化校验模板定义文件
	 * @param file[]
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
//	public static Map<String,Object> initFormat(File[] file) throws ServiceException{shaoxu 2017/06/05
	public static Map initFormat(Object file) throws ServiceException{
		File[] fileArr=(File[])file;
		//初始化校验模板定义缓存对象,以文件名为键值
		Map<String,Object> validatinMap = new HashMap<String, Object>();
		//解析报文格式定义
		for(int i=0;i<fileArr.length;i++){
			getConfig(fileArr[i], validatinMap);
		}
		
		//替换文件中的报文头文件
		if(validatinMap.size()>0){
			replaceRelMethod(validatinMap);
		}
		
		return validatinMap;
	}

	/**
	 * 
	 * @Title: replaceRelMethod 
	 * @Description: 替换validationMap中的rel标签
	 * @param validatinMap
	 */
	public static void replaceRelMethod(Map<String, Object> validatinMap) {
		for(Iterator<String> it = validatinMap.keySet().iterator();it.hasNext();){
			String key = it.next();
			Map<String,ValidationEntity> entityMap = (Map<String, ValidationEntity>) validatinMap.get(key);
			
			Map<String,Map<String, ValidationEntity>> entityRel = new HashMap<String,Map<String, ValidationEntity>>();
			
			for(Iterator<String> its = entityMap.keySet().iterator();its.hasNext();){
				String keys = its.next();
				ValidationEntity entity = entityMap.get(keys);
				Map<String,Object> attrs = entity.getAttrs();
				String tagName = (String) (attrs.get("tagName")==null?"":attrs.get("tagName"));
				String rel = (String) (attrs.get("rel")==null?"":attrs.get("rel"));
				if("xmlRel".equals(tagName)){
					Map<String, ValidationEntity> entityRelTemp = (Map<String, ValidationEntity>) validatinMap.get(rel);
					//entityMap.remove(rel);
					//entityMap.putAll(entityRel);
					entityRel.put(rel, entityRelTemp);
				}else{//本身不是xmlRel,需要对下一级进行遍历判断是否存在XmlRel
					List<ValidationEntity> list = entity.getNextEntityList();
					if(list != null && list.size()>0){
						List<ValidationEntity> listTemp = new ArrayList<ValidationEntity>();
						
						for(ValidationEntity entityTemp : list){
							Map<String,Object> attrsTemp = entityTemp.getAttrs();
							String tagNameTemp = (String) (attrsTemp.get("tagName")==null?"":attrsTemp.get("tagName"));
							String relTemp = (String) (attrsTemp.get("rel")==null?"":attrsTemp.get("rel"));
							if("xmlRel".equals(tagNameTemp)){
								Map<String, ValidationEntity> entityRelTemp = (Map<String, ValidationEntity>) validatinMap.get(relTemp);
								if(entityRelTemp != null && entityRelTemp.size()>0){
									for(Iterator<String> itTemp=entityRelTemp.keySet().iterator();itTemp.hasNext();){
										String keyTemp = itTemp.next();
										ValidationEntity validationEntityTemp = entityRelTemp.get(keyTemp);
										if(validationEntityTemp != null){
											listTemp.add(validationEntityTemp);
										}
									}
								}
							}else{
								listTemp.add(entityTemp);
							}
						}
						
						entity.setNextEntityList(listTemp);
					}
				}
			}
			
			if(entityRel != null && entityRel.size()>0){
				for(Iterator<String> its = entityRel.keySet().iterator();its.hasNext();){
					String keys = its.next();
					Map<String, ValidationEntity> entityRelTemp = (Map<String, ValidationEntity>) entityRel.get(keys);
					entityMap.remove(keys);
					entityMap.putAll(entityRelTemp);
				}
			}
			
		}
	}
	
	/**
	 * 
	 * @Title: getConfig 
	 * @Description: 根据文件读取模板，解析后放入到列表中（解析一个模板）
	 * @param file 文件对象
	 * @param result 校验定义缓存对象
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public static void getConfig(File file, Map<String,Object> result) throws ServiceException{
		try {
			String fileName = file.getName();
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(file);
			Element root = document.getRootElement();
			
			Map<String,ValidationEntity> entityMap = new HashMap<String, ValidationEntity>();
			
			for (Iterator<Element> iter = root.elementIterator(); iter.hasNext();) {
				ValidationEntity entityTemp= new ValidationEntity();
				Element element = (Element) iter.next();
				String name = element.getName();
				String dataName = element.attributeValue("dataName");
				String rel = element.attributeValue("rel");
				
//	shaoxu2017/06/07			if(!name.equalsIgnoreCase("xmlRel") && ValidateUtil.isEmpty(dataName)){
//					throw new ServiceException(BaseErrorCodeConstants.CCOE0013, "标签dataName属性不存在");
//				}
				
				if(name.equalsIgnoreCase("xmlTag")){//变量为map
					validationXmlTag(element,entityTemp);
				}else if(name.equalsIgnoreCase("xmlList")){//变量为list
					validationXmlList(element,entityTemp);
				}else if(name.equalsIgnoreCase("xmlField")){//如果是第一级的化，将遍历号的method方法直接一索引为主键存储
					validationXmlField(element,entityTemp);
				}else if(name.equalsIgnoreCase("xmlRel")){
					dataName = rel;
					Map<String,Object> attrs = getElementAttrs("",element);
					attrs.put("tagName", "xmlRel");
					entityTemp.setAttrs(attrs);
				}
				
				entityMap.put(dataName, entityTemp);
			}
			
			if(entityMap.size()>0){
				result.put(fileName, entityMap);
			}
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0012,"解析验证模板失败",e);
		}	
	}
	

	/**
	 * 
	 * @Title: validationXmlTag 
	 * @Description: 解析验证框架模板的XmlTag标签
	 * @param element 元素
	 * @param entity 数据存储实体
	 * @param keyvalueMap 模板中错误数据
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public static void validationXmlTag(Element element,ValidationEntity entity) throws ServiceException{
		try{
			
			Map<String,Object> attrs = getElementAttrs("",element);//当前元素的所有属性
			attrs.put("tagName", "xmlTag");
			List<ValidationEntity> nextEntityList = new ArrayList<ValidationEntity>();
			
			for (Iterator<Element> iterTag = element.elementIterator(); iterTag.hasNext();) {
				Element item = iterTag.next();
				String label = item.getName();
				ValidationEntity nextEntity = null;
				if("xmlTag".equals(label)){
					nextEntity = new ValidationEntity();
					validationXmlTag(item,nextEntity);
				}else if("xmlList".equals(label)){
					nextEntity = new ValidationEntity();
					validationXmlList(item,nextEntity);
				}else if("xmlField".equals(label)){
					nextEntity = new ValidationEntity();
					validationXmlField(item,nextEntity);
				}else if("xmlRel".equals(label)){
					nextEntity = new ValidationEntity();
					Map<String,Object> attrsXmlRel = getElementAttrs("",item);//当前元素的所有属性
					attrsXmlRel.put("tagName", "xmlRel");
					nextEntity.setAttrs(attrsXmlRel);
				}
				if(nextEntity != null){
					nextEntityList.add(nextEntity);
				}
			}
			
			entity.setAttrs(attrs);
			entity.setNextEntityList(nextEntityList);
			
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0014,"解析xmlTag标签失败",e);
		}	
	}
	

	/**
	 * 
	 * @Title: validationXmlList 
	 * @Description: 解析验证框架模板的XmlList标签
	 * @param element 元素
	 * @param entity 数据存储对象
	 * @param keyvalueMap 校验信息数据
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public static void validationXmlList(Element element,ValidationEntity entity) throws ServiceException{
		try{			
			
			Map<String,Object> attrs = getElementAttrs("",element);//当前元素的所有属性
			attrs.put("tagName", "xmlList");
			List<ValidationEntity> nextEntityList = new ArrayList<ValidationEntity>();
			
			for (Iterator<Element> iterTag = element.elementIterator(); iterTag.hasNext();) {
				Element item = iterTag.next();
				String label = item.getName();
				ValidationEntity nextEntity = null;
				
				if("xmlTag".equals(label)){
					nextEntity = new ValidationEntity();
					validationXmlTag(item,nextEntity);
				}else if("xmlList".equals(label)){
					nextEntity = new ValidationEntity();
					validationXmlList(item,nextEntity);
				}else if("xmlField".equals(label)){
					nextEntity = new ValidationEntity();
					validationXmlField(item,nextEntity);
				}else if("xmlRel".equals(label)){
					nextEntity = new ValidationEntity();
					Map<String,Object> attrsXmlRel = getElementAttrs("",item);//当前元素的所有属性
					attrsXmlRel.put("tagName", "xmlRel");
					nextEntity.setAttrs(attrsXmlRel);
				}
				
				if(nextEntity != null){
					nextEntityList.add(nextEntity);
				}
			}
			
			entity.setAttrs(attrs);
			entity.setNextEntityList(nextEntityList);
			
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0015,"解析xmlList标签失败",e);
		}	
	}
	

	/**
	 * 
	 * @Title: validationXmlField 
	 * @Description: 解析验证框架模板的XmlField标签
	 * @param element 元素对象
	 * @param entity 存储数据的实体
	 * @param keyvalueMap 校验数据存放位置
	 * @throws ServiceException
	 */
	@SuppressWarnings("rawtypes")
	public static void validationXmlField(Element element,ValidationEntity entity) throws ServiceException{
		try{
				String dataName = element.attributeValue("dataName");//xmlField必须含有dataName值
				
				if(ValidateUtil.isEmpty(dataName)){
					throw new ServiceException(BaseErrorCodeConstants.CCOE0013,"标签dataName属性不存在");
				}
				
				Map<String,Object> attrs = getElementAttrs("",element);//当前元素的所有属性
				
				attrs.put("tagName", "xmlField");
				//xmlField的子元素下的validator校验器属性
				List<Map<String,Object>> validatorList = new ArrayList<Map<String,Object>>();
				//便利xmlField下的所有validator子节点
				for (Iterator i = element.elementIterator(); i.hasNext();) {
					Element item = (Element) i.next();
					Map<String,Object> validatorAttrs = getElementAttrs(dataName,item);
					String className = (String) (validatorAttrs.get("class")==null?"":validatorAttrs.get("class"));//获取实现类
					if(ValidateUtil.isEmpty(className)){
						throw new ServiceException(BaseErrorCodeConstants.CCOE0017,"validator标签class属性未配置");
					}
					
					if(className.indexOf(".")==-1){
						className="bros.common.core.validation.library.impl."+className;
					}
					
					try{
						ValidationLibrary validationLibrary = (ValidationLibrary) Class.forName(className).newInstance();//创建校验对象
						validatorAttrs.put("validationLibrary", validationLibrary);
					}catch(Exception e){
						throw new ServiceException(BaseErrorCodeConstants.CCOE0018, "校验对象创建失败", e);
					}
					
					validatorList.add(validatorAttrs);
				}
				
				entity.setAttrs(attrs);
				entity.setValidatorList(validatorList);
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0016,"解析xmlField标签失败",e);
		}		
	}

	/**
	 * 
	 * @Title: getElementAttrs 
	 * @Description: 获取某个元素的所有属性
	 * @param varableName	元素的dataName属性值
	 * @param element	元素对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getElementAttrs(String varableName,Element element) {
		Map<String,Object> attrs = new LinkedHashMap<String, Object>();
		List<DefaultAttribute> list = element.attributes();//当前属性值
		
		String variable = "";
		String dataName = "";
		
		attrs.put("variable", variable);
		
		//遍历method里的所有属性
		for(DefaultAttribute attr : list){
			String label = attr.getName();
			String value = attr.getValue();
			if("variable".equals(label)){
				variable = value;
				continue;
			}else if("dataName".equals(label)){
				dataName = value;
			}
			attrs.put(label, value);
		}
		
		if(ValidateUtil.isEmpty(variable)){
			if(!ValidateUtil.isEmpty(varableName)){//如果是validator属性元素，无variable，需要填写
				variable = varableName;
			}else{
				if(!ValidateUtil.isEmpty(dataName)){
					variable = dataName;
				}
			}
		}
		
		attrs.put("variable", variable);
		
		//替换message中的er表达式
		replaceMessageEl(attrs);
				
		return attrs;
	}
	
	/**
	 * 
	 * @Title: replaceMessageEl 
	 * @Description: 替换属性中的el表达式
	 * @param attrs	属性结果集
	 * @param keyvalueMap 数据错误信息表中的信息
	 */
	public static void replaceMessageEl(Map<String,Object> attrs){
		if(attrs != null && attrs.size()>0){
			String message = (String) (attrs.get("message")==null?"":attrs.get("message"));
			if(!ValidateUtil.isEmpty(message)){
				/*
				String regEx = "^[$][{].+[}]$";//是否是${}表达式
				if(BaseUtil.matchaPattern(message,regEx)){//需要去缓存中去对象
				}
				*/
				replacePlaceholder(attrs, message);//替换其中过的占位符
			}
		}
	}

	/**
	 * 
	 * @Title: replacePlaceholder 
	 * @Description: 替换属性中的占位符
	 * @param attrs	属性结果集
	 * @param message 需要替换的信息
	 */
	public static void replacePlaceholder(Map<String, Object> attrs,String message) {
		int length = attrs.size();
		for(int i=0;i<length;i++){
			String index = "{"+i+"}";
		    int count = 0;
			if(message.indexOf(index) != -1){//需要替换其中的表达式
				for (Entry<String, Object> entry : attrs.entrySet()) {
						if(count == i){
							Object obj = entry.getValue();
							if(obj != null && obj instanceof java.lang.String){
								String value = (String) obj;
								message = message.replace(index, value);
							}
							break;
						}else{
							count++;
						}
				}
			}
		}
		attrs.put("message",message);
	}
}
