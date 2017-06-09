package bros.p.custmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.p.custmanage.facade.service.IPFinanceInvestmentServiceFacade;
import bros.provider.custmanage.constants.CustManageFormatCodeConstants;
import bros.provider.parent.custmanage.investment.service.IFinanceInvestmentService;
/**
 * 
 * @ClassName: PFinanceInvestmentServiceFacadeImpl 
 * @Description: 购买理财产品接口实现
 * @author lichen 
 * @date 2016年12月14日 上午10:45:53 
 * @version 1.0
 */
@Component("pfinanceInvestmentServiceFacade")
public class PFinanceInvestmentServiceFacadeImpl implements IPFinanceInvestmentServiceFacade {
	@Autowired
	private IFinanceInvestmentService financeInvestmentService;
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
	 * @throws ServiceException    设定文件
	 */
	public ResponseEntity queryFinancialProductList(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=financeInvestmentService.queryFinancialProductList(headMap, bodyMap);
		responseEntity = new ResponseEntity(responseMap);	
		return responseEntity;
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
		ResponseEntity  responseEntity =  new  ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000043);
		responseEntity = new ResponseEntity(responseMap);
		return responseEntity;
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
		ResponseEntity  responseEntity =  new  ResponseEntity();
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=financeInvestmentService.queryCustomerHoldingProduct(headMap, bodyMap);
		responseEntity = new ResponseEntity(responseMap);	
		return responseEntity;
	}
	/**
	 * 
	 * @Title: queryProductsTradeDetailList 
	 * @Description: 查询理财产品明细
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity queryProductsTradeDetailList(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
ResponseEntity  responseEntity =  new  ResponseEntity();
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=financeInvestmentService.queryProductsTradeDetailList(headMap, bodyMap);
		responseEntity = new ResponseEntity(responseMap);	
		return responseEntity;
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
		ResponseEntity  responseEntity =  new  ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000045);
		responseEntity = new ResponseEntity(responseMap);
		return responseEntity;
	}

}
