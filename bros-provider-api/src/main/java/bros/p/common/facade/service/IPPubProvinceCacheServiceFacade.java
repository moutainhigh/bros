package bros.p.common.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPPubProvinceCacheServiceFacade 
 * @Description: 省份信息操作接口。
 * @author mazhilei 
 * @date 2016年5月30日 下午2:40:27 
 * @version 1.0
 */
public interface IPPubProvinceCacheServiceFacade {
	/**
	 * 
	 * @Title: queryAllPubProvinceCacheInfo 
	 * @Description: 查询全部省份信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryAllPubProvinceCacheInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubProvinceCacheInfoByName 
	 * @Description: 根据名称模糊查询省份信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryPubProvinceCacheInfoByName(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
}
