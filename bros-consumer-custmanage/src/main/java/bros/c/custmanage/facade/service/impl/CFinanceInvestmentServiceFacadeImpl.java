package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICFinanceInvestmentServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPFinanceInvestmentServiceFacade;
/**
 * 
 * @ClassName: CFinanceInvestmentServiceFacadeImpl 
 * @Description: 投资理财接口实现
 * @author lichen 
 * @date 2016年12月14日 上午11:30:09 
 * @version 1.0
 */
@Component("cfinanceInvestmentServiceFacade")
public class CFinanceInvestmentServiceFacadeImpl implements ICFinanceInvestmentServiceFacade {
	@Autowired
	IPFinanceInvestmentServiceFacade pfinanceInvestmentServiceFacade;
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
	public ResponseEntity queryFinancialProductList(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		return pfinanceInvestmentServiceFacade.queryFinancialProductList(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: financialProductsPurchased 
	 * @Description: 理财产品购买
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity financialProductsPurchased(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		return pfinanceInvestmentServiceFacade.financialProductsPurchased(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryCustomerHoldingProduct 
	 * @Description: 查询顾客持有的理财产品
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity queryCustomerHoldingProduct(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		return pfinanceInvestmentServiceFacade.queryCustomerHoldingProduct(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryProductsTradeDetailList 
	 * @Description: 理财产品明细查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity queryProductsTradeDetailList(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		return pfinanceInvestmentServiceFacade.queryProductsTradeDetailList(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: productsCancle 
	 * @Description: 理财产品撤单
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity productsCancle(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		return pfinanceInvestmentServiceFacade.productsCancle(headMap, bodyMap);
	}

}
