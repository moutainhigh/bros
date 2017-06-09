package bros.p.custmanage.facade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.p.custmanage.facade.service.IPSavingsAcountServiceFacade;
import bros.provider.custmanage.constants.CustManageFormatCodeConstants;
import bros.provider.parent.custmanage.savings.service.ISavingsAcountService;
/**
 * 
 * @ClassName: PSavingsAcountServiceFacadeImpl 
 * @Description: 储蓄账户服务发布实现接口
 * @author huangdazhou 
 * @date 2016年10月19日 上午10:49:43 
 * @version 1.0
 */
@Component("psavingsAcountServiceFacade")
public class PSavingsAcountServiceFacadeImpl implements IPSavingsAcountServiceFacade {
	@Autowired
	private ISavingsAcountService savingsAcountService;
	/**
	 * 通讯
	 */
	@Autowired
	private IHttpClientRouter httpClientRouter;
	/**
	 * @Title: queryDepositType 
	 * @Description: 储种查询
	 */
	public ResponseEntity queryDepositType(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=savingsAcountService.queryDepositTypeMethod(headMap, bodyMap);
		responseEntity = new ResponseEntity(responseMap);	
		return responseEntity;
	}
	
	/**
	 * 
	 * @Title: tranCurrentToFix 
	 * @Description: 活期转定期
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    异常信息
	 */
	public ResponseEntity tranCurrentToFix(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000031);
		responseEntity = new ResponseEntity(responseMap);
		return responseEntity;
	}

	/**
	 * 
	 * @Title: queryProductList 
	 * @Description: 定转活账号列表查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity queryAccountList(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=savingsAcountService.queryAccountList(headMap, bodyMap);
		responseEntity = new ResponseEntity(responseMap);	
		return responseEntity;
	}
	/**
	 * 
	 * @Title: tranFixToCurrent 
	 * @Description: 定期转活期
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException    设定文件
	 */
	@Override
	public ResponseEntity tranFixToCurrent(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		ResponseEntity  responseEntity =  new  ResponseEntity();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap=httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000032);
		responseEntity = new ResponseEntity(responseMap);
		return responseEntity;
	}

}
