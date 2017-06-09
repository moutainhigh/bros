package bros.p.common.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IPShoppingCartServiceFacade 
 * @Description: 购物车对外接口
 * @author pengxiangnan 
 * @date 2016年9月8日 下午14:35:28 
 * @version 1.0
 */
public interface IPShoppingCartServiceFacade {
	
	/**
	 * 
	 * @Title: queryDiscoverPromotions 
	 * @Description: 查询促销
	 * @param headMap BIP报文头
	 * @param bodyMap 参数报文体
	 * @return ResponseEntity 
	 * @throws ServiceException
	 */
	public ResponseEntity  queryDiscoverPromotions(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryDiscoverHot 
	 * @Description: 查询热销
	 * @param headMap BIP报文头
	 * @param bodyMap 参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  queryDiscoverHot(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	
	/**
	 * 
	 * @Title: calculateShoppingCartPrice 
	 * @Description: 重新结算购物车价格
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  calculateShoppingCartPrice(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	
	/**
	 * 
	 * @Title: externalProcessOrder 
	 * @Description: 重新结算购物车价格
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  externalProcessOrder(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	
	/**
	 * 
	 * @Title: queryStoreListInfo 
	 * @Description: 查询店铺与客户支付工具和客户地址信息列表流程
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  queryStoreListInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	
	/**
	 * 
	 * @Title: queryProductListProcess 
	 * @Description: 查询产品列表流程
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  queryProductListProcess(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	
	
	/**
	 * 
	 * @Title: getOrdersList 
	 * @Description: 查询订单列表
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  getOrdersList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	
	
	/**
	 * 
	 * @Title: getOrderDetail 
	 * @Description: 查询订单详情
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  getOrderDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
 
	
	/**
	 * 
	 * @Title: externalApprovedOrder 
	 * @Description: 确认订单
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  externalApprovedOrder(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 
	
	/**
	 * 
	 * @Title: getSurveyQuestion 
	 * @Description: 调查信息
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  getSurveyQuestion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException; 

	/**
	 * 
	 * @Title: createShoppingListService 
	 * @Description: 需求提交
	 * @param headMap  报文头
	 * @param bodyMap  报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity createShoppingListService(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryQuotedPrice 
	 * @Description: 查询报价列表
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  queryQuotedPrice(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	
	/**
	 * 
	 * @Title: checkShowShelfGoodsInfoMethod 
	 * @Description: 校验是否上架
	 * @param headMap  BIP报文头
	 * @param bodyMap  参数报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	public ResponseEntity  checkShowShelfGoodsInfoMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
}
