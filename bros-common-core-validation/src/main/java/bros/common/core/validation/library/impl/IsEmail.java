package bros.common.core.validation.library.impl;

import java.io.Serializable;
import java.util.Map;

import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.common.core.validation.library.ValidationLibrary;

/** 
 * @ClassName: IsEmail 
 * @Description: 邮箱校验器
 * @author weiyancheng
 * @date 2016年5月25日 下午3:18:16 
 * @version 1.0 
 */
public class IsEmail implements ValidationLibrary,Serializable {
	
	private static final long serialVersionUID = -6732189878632244954L;

	/** 
	 * @Title: validator 
	 * @Description: 校验是否是邮箱
	 * @param str
	 * @param attrMap
	 * @return	true：校验不通过，抛出错误信息      false：校验通过，不抛出信息
	 * @throws ServiceException
	 */
	@Override
	public boolean validator(String str, Map<String, Object> attrMap)
			throws ServiceException {
		String patternStr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return !ValidateUtil.regexPattern(str, patternStr);		
	}

}
