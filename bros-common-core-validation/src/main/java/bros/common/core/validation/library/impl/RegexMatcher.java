package bros.common.core.validation.library.impl;

import java.io.Serializable;
import java.util.Map;

import bros.common.core.constants.BaseErrorCodeConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.common.core.validation.library.ValidationLibrary;

/** 
 * @ClassName: RegexMatcher 
 * @Description: 匹配正则表达式校验器
 * @author weiyancheng
 * @date 2016年5月25日 下午2:12:00 
 * @version 1.0 
 */
public class RegexMatcher implements ValidationLibrary,Serializable {
	
	private static final long serialVersionUID = 4538792551819284206L;

	/** 
	 * @Title: validator 
	 * @Description: 匹配正则表达式校验
	 * @param str
	 * @param attrMap
	 * @return	true：校验不通过，抛出错误信息      false：校验通过，不抛出信息
	 * @throws ServiceException
	 */
	@Override
	public boolean validator(String str, Map<String, Object> attrMap)
			throws ServiceException {
			String patternStr = (String) attrMap.get("pattern");
			if(ValidateUtil.isEmpty(patternStr)){
				throw new ServiceException(BaseErrorCodeConstants.CCOE0024,"校验模板中的pattern属性不能为空");
			}else{
				return !ValidateUtil.regexPattern(str, patternStr);
			}
	}

}
