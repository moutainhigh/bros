package bros.p.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPTtpAuthModelSerivceFacade 
 * @Description: 对客授权模型管理对外发布服务接口
 * @author pengxiangnan 
 * @date 2016年7月20日 上午10:16:49 
 * @version 1.0
 */
public interface IPTtpAuthModelSerivceFacade {
	
	/**
	 * 
	 * @Title: saveAuthorizationModel 
	 * @Description: 新增授权模型
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity saveAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
		
	/**
	 * 
	 * @Title: deleteAuthorizationModel 
	 * @Description: 删除授权模型
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity deleteAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateAuthorizationModel 
	 * @Description: 修改授权模型
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity updateAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAuthorizationModelListForPage 
	 * @Description: 分页查询授权模型列表
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity queryAuthorizationModelListForPage(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAuthorizationModelDetail 
	 * @Description: 根据授权模型编号查询授权模型详细信息
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity queryAuthorizationModelDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateFunctionAuthorizationModel 
	 * @Description: 授权模型分配
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity updateFunctionAuthorizationModel(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAuthorizationModelList 
	 * @Description: 查询授权模型列表不分页
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity queryAuthorizationModelList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	
	/**
	 * 
	 * @Title: queryTtpFuncAuthList 
	 * @Description: 查询功能列表
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity queryTtpFuncAuthList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryMenuUnionFuncion 
	 * @Description: 查询菜单树
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity queryMenuUnionFuncion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException ;
}
