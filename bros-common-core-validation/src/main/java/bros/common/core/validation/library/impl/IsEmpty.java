package bros.common.core.validation.library.impl;

import java.io.Serializable;
import java.util.Map;

import bros.common.core.exception.ServiceException;
import bros.common.core.validation.library.ValidationLibrary;
/**
 * 
 * @ClassName: IsEmpty 
 * @Description: 判断字符串是否为空
 * @author 何鹏
 * @date 2016年5月20日 上午11:38:47 
 * @version 1.0
 */
public class IsEmpty implements ValidationLibrary,Serializable {

	private static final long serialVersionUID = -8077355359230859960L;

	/**
	 * 
	 * @Title: validator 
	 * @Description: 校验方法
	 * @param str  需要校验的对象（java八大基本对象）
	 * @param attrMap 校验方法的属性集合
	 * @return true：校验不通过，抛出错误信息      false：校验通过，不抛出信息
	 * @throws ServiceException
	 */
	public boolean validator(String str, Map<String, Object> attrMap) throws ServiceException {
		if(str==null||str.trim().length()==0){
			return true;
		}else{
			return false;
		}
	}
}
