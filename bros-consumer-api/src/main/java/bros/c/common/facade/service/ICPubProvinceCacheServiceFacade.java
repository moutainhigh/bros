package bros.c.common.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICPubProvinceCacheServiceFacade 
 * @Description: 省份信息操作接口。
 * @author 何鹏
 * @date 2016年7月11日 下午2:03:44 
 * @version 1.0
 */
public interface ICPubProvinceCacheServiceFacade {
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
