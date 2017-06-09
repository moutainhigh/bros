package bros.common.core.validation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bros.common.core.constants.BaseErrorCodeConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.redis.util.GetCacheDataUtil;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.ValidateUtil;
import bros.common.core.validation.bean.ValidationEntity;
import bros.common.core.validation.library.ValidationLibrary;

/**
 * 
 * @ClassName: ValidationCheck 
 * @Description: 校验器进行数据校验
 * @author 何鹏
 * @date 2016年5月19日 下午5:25:19 
 * @version 1.0
 */
public class ValidationCheck {

	/**
	 * 
	 * @Title: validator 
	 * @Description: 进行数据校验
	 * @param objectArr 校验数据
	 * @param paramArr 属性变量名（Validation标签中的paramName属性值）
	 * @param templeteName 模板名称
	 * @throws ServiceException
	 */
	public static void validator(Object[] objectArr,String[] paramArr,String templeteName) throws ServiceException{
		//当前校验模板名称
		try{
			
			//Map<String,Object> templateMap = ValidationFactory.getValidationModel(templeteName);
			Map<String,ValidationEntity> templateMap = GetCacheDataUtil.getValidationCache(templeteName);//从缓存中获取报文模板
			
			if(templateMap != null && templateMap.size() >0){
				for(int i=0;i<objectArr.length;i++){
					Object object = objectArr[i];//当前数据
					String paramName = paramArr[i];
					if(ValidateUtil.isEmpty(paramName)){//为空不需要进行校验
						continue;
					}
					Object templeteObject = templateMap.get(paramName);
					if(templeteObject != null && templeteObject instanceof ValidationEntity){
						ValidationEntity validationEntity = (ValidationEntity) templeteObject;
						validatorCombineTag(object,validationEntity);
					}
				}
			}else{//校验模板不存在
				throw new ServiceException(BaseErrorCodeConstants.CCOE0019, "校验模板不存在");
			}
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0020,"数据校验失败",e);
		}
		
	}
	

	/**
	 * 
	 * @Title: validatorCombineTag 
	 * @Description: 数据校验
	 * @param object 校验数据
	 * @param entity 模板实体
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void validatorCombineTag(Object object,ValidationEntity entity) throws ServiceException{
		try{
			//模板不存在不进行校验
			if(entity == null){
				return;
			}
			Map<String,Object> attrsMap = entity.getAttrs();//当前元素属性
			String tagName = (String) (attrsMap.get("tagName")==null?"":attrsMap.get("tagName"));
			String message = (String) (attrsMap.get("message")==null?"":attrsMap.get("message"));
			
			if(object == null){
				if(!ValidateUtil.isEmpty(message)){
					throw new ServiceException(BaseErrorCodeConstants.CCOE0024, getExceptionMessage(attrsMap,message));
				}else{
					throw new ServiceException(BaseErrorCodeConstants.CCOE0025, "校验错误提示数据不能为空");
				}
			}
			
			if("xmlTag".equals(tagName)){//当前属性为Map
				if(object instanceof java.util.Map){
					
					//判断是否存在需要校验的字段
					List<ValidationEntity> list = entity.getNextEntityList();
					if(null == list || list.size()==0){
						return;
					}
					
					Map<String,Object> xmlTagMap = (Map<String, Object>) object;
					
					//校验xmlTag标签数据
					List<ValidationEntity> validationsEntity = entity.getNextEntityList();
					validatorXmlTag(xmlTagMap,validationsEntity);
				}else{
					throw new ServiceException(BaseErrorCodeConstants.CCOE0026,"数据结构不合法");
				}
			}else if("xmlList".equals(tagName)){//当前对象为List
				if(object instanceof java.util.List){
					
					//判断是否存在需要校验的字段
					List<ValidationEntity> list = entity.getNextEntityList();
					if(null == list || list.size()==0){
						return;
					}
					
					List xmlList = (List) object;
					
					//校验xmlList标签数据
					List<ValidationEntity> validationsEntity = entity.getNextEntityList();
					if(validationsEntity.size() != 1){
						throw new ServiceException(BaseErrorCodeConstants.CCOE0027,"不支持一个list中套多种数据类型");
					}
					validatorXmlList(xmlList,validationsEntity);
				}else{
					throw new ServiceException(BaseErrorCodeConstants.CCOE0026,"数据结构不合法");
				}
			}else if("xmlField".equals(tagName)){//当前对象为xmlField
				
				String obj = basicTypeToString(object);//将数据转换为基础数据
				if(ValidateUtil.isEmpty(obj)){
					if(!ValidateUtil.isEmpty(message)){
						throw new ServiceException(BaseErrorCodeConstants.CCOE0024,getExceptionMessage(attrsMap,message));
					}else{
						throw new ServiceException(BaseErrorCodeConstants.CCOE0028,"校验数据不存在");
					}
				}
				
				List<Map<String, Object>> validatorList = entity.getValidatorList();//校验器
				if(validatorList != null && validatorList.size()>0){//进行数据校验
					validatorXmlField(obj,validatorList);
				}
			}
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0020,"数据校验失败",e);
		}
	}


	/**
	 * 
	 * @Title: validatorXmlTag 
	 * @Description: 校验xmlTag标签数据
	 * @param map 校验数据
	 * @param entity 模板实体
	 * @throws ServiceException
	 */
	public static void validatorXmlTag(Map<String,Object> map,List<ValidationEntity> entity)  throws ServiceException{
		//变量模板
		for(ValidationEntity validationEntity : entity){
			Map<String,Object> attrsMap = validationEntity.getAttrs();//当前属性的dataName必须存在
			String dataName = (String) (attrsMap.get("dataName")==null?"":attrsMap.get("dataName"));
			if(ValidateUtil.isEmpty(dataName)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0013,"标签dataName属性不存在");
			}
			
			Object object = map.get(dataName);//当前元素的值
			
			validatorCombineTag(object,validationEntity);
		}
	}
	
	/**
	 * 
	 * @Title: validatorXmlList 
	 * @Description: 校验xmlList标签数据
	 * @param list 校验数据
	 * @param entity 校验实体
	 * @throws ServiceException
	 */
	@SuppressWarnings("rawtypes")
	public static void validatorXmlList(List list,List<ValidationEntity> entity) throws ServiceException{
		for(ValidationEntity validationEntity : entity){
			for(int i=0;i<list.size();i++){
				Object object = list.get(i);
				validatorCombineTag(object,validationEntity);
			}
		}
	}

	/**
	 * 
	 * @Title: validatorXmlField 
	 * @Description: 进行xmlField校验
	 * @param obj 校验数据
	 * @param validatorList 校验实体
	 * @throws ServiceException
	 */
	public static void validatorXmlField(String obj,List<Map<String, Object>> validatorList) throws ServiceException{
		try{
			for(Map<String,Object> map : validatorList){
				String message = (String) (map.get("message")==null?"":map.get("message"));//校验不通过提示信息
				//String flag = (String) (map.get("flag")==null?"":map.get("flag"));//校验给出提示信息，其值为0和1，默认为0     0：校验为true抛出异常    1：校验为false抛出异常
				String flag = "";//统一返回true时抛出异常，表示校验不通过
				if(ValidateUtil.isEmpty(message)){
					throw new ServiceException(BaseErrorCodeConstants.CCOE0025,"校验错误提示数据不能为空");
				}
				
				if(ValidateUtil.isEmpty(flag)){
					flag = "0";
				}
				
				if(!"0".equals(flag.trim()) && !"1".equals(flag.trim()) ){
					throw new ServiceException(BaseErrorCodeConstants.CCOE0029,"验证提示信息标志不为0（true）或1（false）");
				}
				
				ValidationLibrary validationLibrary = (ValidationLibrary) map.get("validationLibrary");//获取校验器
				Boolean result = validationLibrary.validator(obj,map);
				
				if(("0".equals(flag.trim()) && result) || //校验为true抛出异常
						("1".equals(flag.trim()) && !result)){//校验为false抛出异常
					throw new ServiceException(BaseErrorCodeConstants.CCOE0024,getExceptionMessage(map,message));
				}
			}
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0020,"数据校验失败",e);
		}
	}
	/**
	 * 
	 * @Title: basicTypeToString 
	 * @Description:将八大基本类型转换为string类型
	 * @param obj
	 * @return
	 */
	public static String basicTypeToString(Object obj) {
		String object;
		if(obj instanceof java.lang.Byte){
			object = Byte.toString((byte) obj);
		}else if(obj instanceof java.lang.Boolean){
			object = Boolean.toString((boolean) obj);
		}else if(obj instanceof java.lang.Integer){
			object = Integer.toString((int) obj);
		}else if(obj instanceof java.lang.Double){
			object = Double.toString((double) obj);
		}else if(obj instanceof java.lang.Float){
			object = Float.toString((float) obj);
		}else if(obj instanceof java.lang.Short){
			object = Short.toString((short) obj);
		}else if(obj instanceof java.lang.Character){
			object = Character.toString((char) obj);
		}else if(obj instanceof java.lang.Long){
			object = Long.toString((long) obj);
		}else{
			object = obj.toString();
		}
		return object;
	}
	
	/**
	 * 
	 * @Title: getExceptionMessage 
	 * @Description: 从缓存中获取主键，将其转换为错误信息
	 * @param attrsMap	属性map
	 * @param message	当前值
	 * @return
	 * @throws ServiceException
	 */
	public static String getExceptionMessage(Map<String,Object> attrsMap,String message) throws ServiceException{
		String regEx = "^[$][{].+[}]$";//是否是${}表达式
		if(BaseUtil.matchaPattern(message,regEx)){//需要去缓存中去对象
			String errCode = message.substring(2,message.length()-1);
			String messageTemp = GetCacheDataUtil.getErrMsgByErrCode(errCode);
			if(!ValidateUtil.isEmpty(messageTemp)){
				message = messageTemp;
			}
		}
		
		return replacePlaceholder(attrsMap,message);
	}
	
	/**
	 * 
	 * @Title: replacePlaceholder 
	 * @Description: 替换属性中的占位符
	 * @param attrs	属性结果集
	 * @param message 需要替换的信息
	 */
	public static String replacePlaceholder(Map<String, Object> attrs,String message) {
		int length = attrs.size();
		for(int i=0;i<length;i++){
			String index = "{"+i+"}";
		    int count = 0;
			if(message.indexOf(index) != -1){//需要替换其中的表达式
				for (Entry<String, Object> entry : attrs.entrySet()) {
						if(count == i){
							Object obj = entry.getValue();
							if(obj != null && (obj instanceof java.lang.String)){
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
		return message;
	}
}


