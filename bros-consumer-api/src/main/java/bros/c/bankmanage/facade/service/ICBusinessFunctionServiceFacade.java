package bros.c.bankmanage.facade.service;

import java.util.List;
import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICBusinessFunctionServiceFacade 
 * @Description: 业务功能管理服务发布接口
 * @author huangdazhou 
 * @date 2016年12月23日 下午3:51:59 
 * @version 1.0
 */
public interface ICBusinessFunctionServiceFacade {

	/**
	 * 
	 * @Title: queryBsnFun 
	 * @Description: 查询业务功能信息
	 * @param headMap
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryBsnFun(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryBsnFunRelMenudef 
	 * @Description: 查询业务与菜单关联功能
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryBsnFunRelMenudef(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: updateBsnFunRelMenu 
	 * @Description: 修改业务菜单关联
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateBsnFunRelMenu(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * @Title: insertBsndef 
	 * @Description:添加业务功能信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity insertOneBsndef(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: updateBsndef 
	 * @Description: 修改业务功能信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateOneBsndef(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: deleteBsndef 
	 * @Description: 删除业务功能信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity deleteOneBsndef(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
}
