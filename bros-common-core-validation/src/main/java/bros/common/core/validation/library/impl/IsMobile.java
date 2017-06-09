package bros.common.core.validation.library.impl;

import java.io.Serializable;
import java.util.Map;

import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.common.core.validation.library.ValidationLibrary;

/** 
 * @ClassName: IsMobile 
 * @Description: 手机号校验器
 * @author weiyancheng
 * @date 2016年5月25日 下午2:42:46 
 * @version 1.0 
 */
public class IsMobile implements ValidationLibrary,Serializable {
	
	private static final long serialVersionUID = 1409074187006584494L;

	/** 
	 * @Title: validator 
	 * @Description: 校验是否是手机号
	 * @param str
	 * @param attrMap
	 * @return	true：校验不通过，抛出错误信息      false：校验通过，不抛出信息
	 * @throws ServiceException
	 */
	@Override
	public boolean validator(String str, Map<String, Object> attrMap)
			throws ServiceException {
		String pattarn = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[0-9])\\d{8}$";
		return !ValidateUtil.regexPattern(str, pattarn);
	}

}
