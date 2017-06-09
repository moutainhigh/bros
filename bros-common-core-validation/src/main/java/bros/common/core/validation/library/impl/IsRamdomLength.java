package bros.common.core.validation.library.impl;

import java.io.Serializable;
import java.util.Map;

import bros.common.core.constants.BaseErrorCodeConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.common.core.validation.library.ValidationLibrary;
/**
 * 
 * @ClassName: IsRamdomLength 
 * @Description: 字符串长度去掉空格后判断字符串长度是否在某个范围之内
 * @author 何鹏
 * @date 2016年6月4日 下午12:14:34 
 * @version 1.0
 */
public class IsRamdomLength implements ValidationLibrary,Serializable {

	private static final long serialVersionUID = 7081974040695795016L;

	/**
	 * 
	 * @Title: validator 
	 * @Description: 校验方法
	 * @param str  需要校验的对象（java八大基本对象）
	 * @param attrMap 校验方法的属性集合
	 * @return true：不等于，抛出错误信息      false：等于，不抛出信息
	 * @throws ServiceException
	 */
	@Override
	public boolean validator(String str, Map<String, Object> attrMap)
			throws ServiceException {
		String maxStr = (String) (attrMap.get("max")==null?"":attrMap.get("max"));
		String minStr = (String) (attrMap.get("min")==null?"":attrMap.get("min"));
		// r：在某个范围之间   l：小于   le：小于等于     g：大于    ge：大于等于      e：等于
		String symbol = (String) (attrMap.get("symbol")==null?"":attrMap.get("symbol"));
		symbol = symbol.trim();
		if(ValidateUtil.isEmpty(symbol)){
			throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的symbol属性不能为空");
		}
		int max = 0;
		int min = 0;
		int length = str.trim().length();
		
		if("r".equals(symbol)){
			if(ValidateUtil.isEmpty(maxStr)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的max属性不能为空");
			}
			if(ValidateUtil.isEmpty(minStr)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的min属性不能为空");
			}
			max = Integer.parseInt(maxStr);
			min = Integer.parseInt(minStr);
			
			if(length>=min && length<=max){
				return false;
			}
		}else if("g".equals(symbol)){
			if(ValidateUtil.isEmpty(maxStr)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的max属性不能为空");
			}
			max = Integer.parseInt(maxStr);
			
			if(length>max){
				return false;
			}
		}else if("ge".equals(symbol)){
			if(ValidateUtil.isEmpty(maxStr)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的max属性不能为空");
			}
			max = Integer.parseInt(maxStr);
			
			if(length>=max){
				return false;
			}
		}else if("l".equals(symbol)){
			if(ValidateUtil.isEmpty(minStr)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的min属性不能为空");
			}
			min = Integer.parseInt(minStr);
			if(length<min){
				return false;
			}
		}else if("le".equals(symbol)){
			if(ValidateUtil.isEmpty(minStr)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的min属性不能为空");
			}
			min = Integer.parseInt(minStr);
			if(length<=min){
				return false;
			}
		}else if("e".equals(symbol)){
			if(ValidateUtil.isEmpty(minStr)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的min属性不能为空");
			}
			min = Integer.parseInt(minStr);
			if(length==min){
				return false;
			}
		}else{
			throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中个symbol属性值不能识别");
		}
		
		return true;
	}

}
