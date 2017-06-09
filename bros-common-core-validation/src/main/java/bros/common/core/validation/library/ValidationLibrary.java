package bros.common.core.validation.library;

import java.util.Map;

import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: ValidationLibrary 
 * @Description: 校验器父接口（所有的校验器必须实现该接口）
 * @author 何鹏
 * @date 2016年5月20日 上午11:37:00 
 * @version 1.0
 */
public interface ValidationLibrary{
	/**
	 * 
	 * @Title: validator 
	 * @Description: 校验方法
	 * @param str  需要校验的对象（java八大基本对象）
	 * @param attrMap 校验方法的属性集合
	 * @return	true：校验不通过，抛出错误信息      false：校验通过，不抛出信息
	 * @throws ServiceException
	 */
	public boolean validator(String str,Map<String,Object> attrMap) throws ServiceException;
}
