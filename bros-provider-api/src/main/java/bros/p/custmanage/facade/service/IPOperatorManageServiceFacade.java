package bros.p.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPOperatorManageService 
 * @Description: 操作员信息业务接口
 * @author pengxiangnan 
 * @date 2016年7月13日 下午2:44:40 
 * @version 1.0
 */
public interface IPOperatorManageServiceFacade {
	
	/**
	 * 
	 * @Title: addOperatorManageMethod 
	 * @Description: 添加操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity addOperatorManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryOperatorManageMethod 
	 * @Description: 查询操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryOperatorManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateOperatorManageMethod 
	 * @Description: 更新操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity updateOperatorManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteOperatorManageMethod 
	 * @Description: 删除操作员信息
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity deleteOperatorManageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * @Title: queryOperatorListForPage 
	 * @Description: 查询操作员信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryOperatorListForPage(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
