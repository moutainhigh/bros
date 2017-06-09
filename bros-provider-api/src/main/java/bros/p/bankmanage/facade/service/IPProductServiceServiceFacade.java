package bros.p.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IPProductServiceServiceFacade 
 * @Description: 产品服务对外发布接口
 * @author huangdazhou
 * @date 2016年6月30日 上午9:18:45 
 * @version 1.0
 */
public interface IPProductServiceServiceFacade {
	/**
	 * 
	 * @Title: addProductServiceMethod 
	 * @Description: 新增产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity addProductServiceMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateProductServiceMethod 
	 * @Description: 修改产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity updateProductServiceMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteProductServiceMethod 
	 * @Description: 删除产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity deleteProductServiceMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductServiceMethod 
	 * @Description: 查询产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity queryProductServiceMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductServicePageMethod 
	 * @Description: 分页查询产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity queryProductServicePageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductServiceOneMethod 
	 * @Description: 单笔查询产品服务方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity queryProductServiceOneMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
}
