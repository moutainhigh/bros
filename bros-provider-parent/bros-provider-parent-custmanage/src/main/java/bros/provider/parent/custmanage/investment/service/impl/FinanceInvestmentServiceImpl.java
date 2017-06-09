package bros.provider.parent.custmanage.investment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.provider.parent.custmanage.constants.CustManageFormatCodeConstants;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.investment.service.IFinanceInvestmentService;

/**
 * 
 * @ClassName: FinanceInvestmentServiceImpl 
 * @Description: 投资理财接口实现
 * @author lichen
 * @date 2016年12月14日 上午10:07:43 
 * @version 1.0
 */
@Repository(value = "financeInvestmentService")
public class FinanceInvestmentServiceImpl implements IFinanceInvestmentService {
	private static final Logger logger = LoggerFactory.getLogger(FinanceInvestmentServiceImpl.class);
	@Autowired
	private IHttpClientRouter httpClientRouter;
	@Override
	/**
	 * 
	 * @Title: queryFinancialProductList 
	 * @Description: 购买理财产品查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	public Map<String, Object> queryFinancialProductList(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants. PSVR000047);
		} catch (ServiceException se) {
			logger.error("理财产品查询失败   " + this.getClass() + ".queryFinancialProductList",se);
			throw se;
		} catch (Exception e) {
			logger.error("理财产品查询失败    " + this.getClass() + ".queryFinancialProductList",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0063,"理财产品查询失败", e);
		}
		return resultMap;
	}
	
	@Override
	/**
	 * 
	 * @Title: queryCustomerHoldingProduct 
	 * @Description: 查询顾客持有的理财产品
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	public Map<String, Object> queryCustomerHoldingProduct(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants. PSVR000048);
		} catch (ServiceException se) {
			logger.error("持有理财产品查询失败   " + this.getClass() + ".queryCustomerHoldingProduct",se);
			throw se;
		} catch (Exception e) {
			logger.error("持有理财产品查询失败    " + this.getClass() + ".queryCustomerHoldingProduct",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0063,"理财产品查询失败", e);
		}
		return resultMap;
	}
/**
 * 
 * @Title: queryProductsTradeDetailList 
 * @Description: 查询理财产品交易明细
 * @param headMap
 * @param bodyMap
 * @return
 * @throws ServiceException    设定文件
 */
	@Override
	public Map<String, Object> queryProductsTradeDetailList(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants. PSVR000084);
		} catch (ServiceException se) {
			logger.error("理财产品明细查询失败   " + this.getClass() + ".queryProductsTradeDetailList",se);
			throw se;
		} catch (Exception e) {
			logger.error("理财产品明细查询失败    " + this.getClass() + ".queryProductsTradeDetailList",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0065,"理财产品明细查询失败", e);
		}
		return resultMap;
	}

}
