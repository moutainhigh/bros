package bros.p.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPPersonOverviewServiceFacade 
 * @Description: 客户总览服务发布接口
 * @author huangdazhou 
 * @date 2016年10月31日 下午2:53:28 
 * @version 1.0
 */
public interface IPPersonOverviewServiceFacade {
	/**
	 * 
	 * @Title: queryPersonOverview 
	 * @Description: 查询客户总览信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryPersonOverview(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryCustPropertyDetail 
	 * @Description: 客户资产明细查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryCustPropertyDetail(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 根据partyId查询客户产品视图
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryCustomerProductServiceByPartyId(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;

}
