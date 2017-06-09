package bros.p.custmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.p.custmanage.facade.service.IPPersonOverviewServiceFacade;
import bros.provider.custmanage.constants.CustManageFormatCodeConstants;
/**
 * 
 * @ClassName: PPersonOverviewServiceFacadeImpl 
 * @Description: 客户总览服务接口实现
 * @author huangdazhou 
 * @date 2016年10月31日 下午2:57:17 
 * @version 1.0
 */
@Component("personOverviewServiceFacade")
public class PPersonOverviewServiceFacadeImpl implements IPPersonOverviewServiceFacade {
	/**
	 * 通讯
	 */
	@Autowired
	private IHttpClientRouter httpClientRouter;

	/**
	 * 查询客户总览信息
	 */
	public ResponseEntity queryPersonOverview(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.SVR00000013);
		responseEntity = new ResponseEntity(responseMap);
		return responseEntity;
	}

	/**
	 * 客户资产明细查询
	 */
	public ResponseEntity queryCustPropertyDetail(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.SVR00000014);
		responseEntity = new ResponseEntity(responseMap);
		return responseEntity;
	}
	
	/**
	 * 根据partyId查询客户产品视图
	 */
	@Override
	public ResponseEntity queryCustomerProductServiceByPartyId(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.SVR00000015);
		responseEntity = new ResponseEntity(responseMap);
		return responseEntity;
	}

}
