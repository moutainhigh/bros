package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICPersonOverviewServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPPersonOverviewServiceFacade;
/**
 * 
 * @ClassName: CPersonOverviewServiceFacadeImpl 
 * @Description: 消费方客户总览服务接口实现
 * @author huangdazhou 
 * @date 2016年11月1日 上午9:54:52 
 * @version 1.0
 */
@Component("cpersonOverviewServiceFacade")
public class CPersonOverviewServiceFacadeImpl implements
		ICPersonOverviewServiceFacade {
	@Autowired
	private IPPersonOverviewServiceFacade personOverviewServiceFacade;

	/**
	 * 查询客户总览信息
	 */
	public ResponseEntity queryPersonOverview(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return personOverviewServiceFacade.queryPersonOverview(headMap, bodyMap) ;
	}

	/**
	 *  客户资产明细查询
	 */
	public ResponseEntity queryCustPropertyDetail(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return personOverviewServiceFacade.queryCustPropertyDetail(headMap, bodyMap);
	}
	/**
	 * 根据partyId查询客户产品视图
	 */
	@Override
	public ResponseEntity queryCustomerProductServiceByPartyId(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		return personOverviewServiceFacade.queryCustomerProductServiceByPartyId(headMap,bodyMap);
	}

}
