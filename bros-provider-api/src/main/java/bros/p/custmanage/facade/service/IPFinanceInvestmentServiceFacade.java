package bros.p.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
/**
 * 
 * @ClassName: IPFinanceInvestmentServiceFacade 
 * @Description: 投资理财服务接口
 * @author lichen
 * @date 2016年12月14日 上午10:37:20 
 * @version 1.0
 */
public interface IPFinanceInvestmentServiceFacade {
	/**
	 * 
	 * @Title: queryFinancialProductList 
	 * @Description: 购买理财产品查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryFinancialProductList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: financialProductsPurchased 
	 * @Description: 理财产品购买
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity financialProductsPurchased(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryCustomerHoldingProduct 
	 * @Description: 查询顾客持有的理财产品
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryCustomerHoldingProduct(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductsTradeDetailList 
	 * @Description: 查询理财交易明细
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryProductsTradeDetailList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: productsCancle 
	 * @Description: 理财产品撤单
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity productsCancle(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;

}
