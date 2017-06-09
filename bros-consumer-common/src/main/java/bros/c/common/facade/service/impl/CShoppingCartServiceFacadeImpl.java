package bros.c.common.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.common.facade.service.ICShoppingCartServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPShoppingCartServiceFacade;

/**
 * 
 * @ClassName: CAccountServiceFacadeImpl 
 * @Description: 购物车消费实现接口
 * @author pengxiangnan 
 * @date 2016年9月8日 下午14:54:03 
 * @version 1.0
 */
@Component("cshoppingCartServiceFacade")
public class CShoppingCartServiceFacadeImpl implements ICShoppingCartServiceFacade {
	
      
	/**
	 * 提供方
	 */
	  @Autowired
	  private IPShoppingCartServiceFacade pshoppingCartServiceFacade;
  
	/**
	 * 查询促销
	 */
	@Validation(value="c0000500")
	@Override
	public ResponseEntity queryDiscoverPromotions(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.queryDiscoverPromotions(headMap, bodyMap);
	}
    
	/**
	 * 查询热销
	 */
	@Validation(value="c0000501")
	@Override
	public ResponseEntity queryDiscoverHot(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.queryDiscoverHot(headMap, bodyMap);
	}
    
	/**
	 * 重新计算购物车价格
	 */
	@Validation(value="c0000503")
	@Override
	public ResponseEntity calculateShoppingCartPrice(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.calculateShoppingCartPrice(headMap, bodyMap);
	}
    
	/**
	 * 执行订单
	 */
	@Validation(value="c0000502")
	@Override
	public ResponseEntity externalProcessOrder(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.externalProcessOrder(headMap, bodyMap);
	}
	 
	 
	/**
	 * 查询店铺与客户支付工具和客户地址信息列表流程
	 */
	@Validation(value="c0000504")
	@Override
	public ResponseEntity queryStoreListInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.queryStoreListInfo(headMap, bodyMap);
	}
    
	/**
	 * 查询产品列表流程
	 */
	@Validation(value="c0000505")
	@Override
	public ResponseEntity queryProductListProcess(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.queryProductListProcess(headMap, bodyMap);
	}
    
	/**
	 * 查询订单列表
	 */
	@Validation(value="c0000506")
	@Override
	public ResponseEntity getOrdersList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.getOrdersList(headMap, bodyMap);
	}
    
	/**
	 * 查询订单详情
	 */
	@Validation(value="c0000507")
	@Override
	public ResponseEntity getOrderDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.getOrderDetail(headMap, bodyMap);
	}
	
	/**
	 * 查询报价列表
	 */ 
	//@Validation(value="c0000510")
	@Override
	public ResponseEntity queryQuotedPrice(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.queryQuotedPrice(headMap, bodyMap);
	}

	/**
	 * 订单确认
	 */
	@Validation(value="c0000508")
	@Override
	public ResponseEntity externalApprovedOrder(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.externalApprovedOrder(headMap, bodyMap);
	}
	
	/**
	 * 获取调查信息
	 */ 
	@Validation(value="c0000509")
	@Override
	public ResponseEntity getSurveyQuestion(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.getSurveyQuestion(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: createShoppingListService 
	 * @Description: 需求提交
	 * @param headMap  报文头
	 * @param bodyMap  报文体
	 * @return ResponseEntity
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity createShoppingListService(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return pshoppingCartServiceFacade.createShoppingListService(headMap, bodyMap);
	}
    
	/**
	 * 校验商品是否上架
	 */
	@Validation(value="c0000510")
	@Override
	public ResponseEntity checkShowShelfGoodsInfoMethod(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return pshoppingCartServiceFacade.checkShowShelfGoodsInfoMethod(headMap, bodyMap);
	}
}
