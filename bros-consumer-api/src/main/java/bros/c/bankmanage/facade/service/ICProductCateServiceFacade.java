package bros.c.bankmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICProductCateServiceFacade 
 * @Description: 产品目录服务发布接口
 * @author huangdazhou
 * @date 2016年6月30日 下午2:20:34 
 * @version 1.0
 */
public interface ICProductCateServiceFacade {
	/**
	 * 
	 * @Title: addProductCateMethod 
	 * @Description: 新增产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity addProductCateMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateProductCateMethod 
	 * @Description: 修改产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity updateProductCateMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteProductCateMethod 
	 * @Description: 删除产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity deleteProductCateMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductCateMethod 
	 * @Description: 查询产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity queryProductCateMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductCatePageMethod 
	 * @Description: 分页查询产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity queryProductCatePageMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductCateOneMethod 
	 * @Description: 单笔查询产品目录方法
	 * @param headMap 报文头map
	 * @param bodyMap 报文体map
	 * @return ResponseEntity 结果对象
	 * @throws ServiceException
	 */
	public ResponseEntity queryProductCateOneMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
