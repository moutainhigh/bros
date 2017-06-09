package bros.c.common.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.common.facade.service.ICPubProvinceCacheServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPPubProvinceCacheServiceFacade;
/**
 * 
 * @ClassName: CPubProvinceCacheServiceFacadeImpl 
 * @Description: 省份信息操作实现
 * @author 何鹏
 * @date 2016年7月11日 下午2:03:44 
 * @version 1.0
 */
@Component("cpubProvinceCacheServiceFacade")
public class CPubProvinceCacheServiceFacadeImpl implements ICPubProvinceCacheServiceFacade {

	@Autowired
	private IPPubProvinceCacheServiceFacade ppubProvinceCacheServiceFacade;
	
	/**
	 * 
	 * @Title: queryAllPubProvinceCacheInfo 
	 * @Description: 查询全部省份信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAllPubProvinceCacheInfo(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return ppubProvinceCacheServiceFacade.queryAllPubProvinceCacheInfo(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryPubProvinceCacheInfoByName 
	 * @Description: 根据名称模糊查询省份信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000203")
	@Override
	public ResponseEntity queryPubProvinceCacheInfoByName(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return ppubProvinceCacheServiceFacade.queryPubProvinceCacheInfoByName(headMap, bodyMap);
	}

}
