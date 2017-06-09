package bros.p.demo.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPDemoServiceFacade 
 * @Description: 服务提供方demo接口
 * @author 何鹏
 * @date 2016年8月12日 下午2:42:23 
 * @version 1.0
 */
public interface IPDemoServiceFacade {

	/**
	 * 
	 * @Title: queryBranchDemoInfo 
	 * @Description: 查询机构信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryBranchDemoInfo(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryCustomerDemoInfo 
	 * @Description: 查询客户信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryCustomerDemoInfo(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
}
