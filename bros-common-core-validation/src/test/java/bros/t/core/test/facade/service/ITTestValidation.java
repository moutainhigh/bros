package bros.t.core.test.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: ITTestValidation 
 * @Description: 校验模板测试接口
 * @author 何鹏
 * @date 2016年8月15日 下午1:57:08 
 * @version 1.0
 */
public interface ITTestValidation {
	/**
	 * 
	 * @Title: getAllBranchNotValidation 
	 * @Description: 获取所有机构信息
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity getAllBranchNotValidation(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: getAllBranchValidation 
	 * @Description: 获取所有机构信息
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity getAllBranchValidation(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: getAllBranchValidationNotModel 
	 * @Description: 获取所有机构信息
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity getAllBranchValidationNotModel(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: getAllBranchValidationManyParams 
	 * @Description: 获取所有机构信息
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity getAllBranchValidationManyParams(Map<String,Object> headMap,Map<String,Object> bodyMap,Map<String,Object> result) throws ServiceException;
	public ResponseEntity getAllBranchValidationManyParams1(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
