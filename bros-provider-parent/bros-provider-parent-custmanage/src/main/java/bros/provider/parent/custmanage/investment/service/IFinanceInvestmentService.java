package bros.provider.parent.custmanage.investment.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IFinanceInvestmentService 
 * @Description: 投资理财服务接口
 * @author lichen
 * @date 2016年12月14日 上午9:57:40 
 * @version 1.0
 */

public interface IFinanceInvestmentService {
	/**
	 * 
	 * @Title: queryFinancialProductList 
	 * @Description: 购买理财产品查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryFinancialProductList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryCustomerHoldingProduct 
	 * @Description: 查询顾客持有的理财产品
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryCustomerHoldingProduct(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryProductsTradeDetailList 
	 * @Description: 查询理财产品交易明细
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryProductsTradeDetailList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
