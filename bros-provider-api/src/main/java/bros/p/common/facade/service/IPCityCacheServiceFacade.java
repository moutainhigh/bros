package bros.p.common.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPCityCacheServiceFacade 
 * @Description: 城市信息操作接口
 * @author mazhilei 
 * @date 2016年5月30日 下午2:44:00 
 * @version 1.0
 */
public interface IPCityCacheServiceFacade {
	/**
	 * 
	 * @Title: queryAllPubCityInfo 
	 * @Description: 查询所有城市信息（不需要上送变量，需要上送报文头）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryAllPubCityInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubCityInfoByName 
	 * @Description: 根据名称模糊查询城市信息
	 * @param headMap 报文头
	 * @param bodyMap （name名称字段）
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryPubCityInfoByName(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubCityInfoByCode 
	 * @Description: 根据省份code查询城市信息服务
	 * @param headMap 报文头
	 * @param bodyMap 省份code
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryPubCityInfoByCode(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;

}
