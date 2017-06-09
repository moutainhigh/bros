package bros.c.common.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.common.facade.service.ICCityCacheServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPCityCacheServiceFacade;
/**
 * 
 * @ClassName: CCityCacheServiceFacadeImpl 
 * @Description: 城市信息操作实现
 * @author 何鹏
 * @date 2016年7月11日 下午2:03:12 
 * @version 1.0
 */
@Component("ccityCacheServiceFacade")
public class CCityCacheServiceFacadeImpl implements ICCityCacheServiceFacade {

	@Autowired
	private IPCityCacheServiceFacade pcityCacheServiceFacade;
	/**
	 * 
	 * @Title: queryAllPubCityInfo 
	 * @Description: 查询所有城市信息（不需要上送变量，需要上送报文头）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAllPubCityInfo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pcityCacheServiceFacade.queryAllPubCityInfo(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryPubCityInfoByName 
	 * @Description: 根据名称模糊查询城市信息
	 * @param headMap 报文头
	 * @param bodyMap （name名称字段）
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="c0000201")
	@Override
	public ResponseEntity queryPubCityInfoByName(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pcityCacheServiceFacade.queryPubCityInfoByName(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryPubCityInfoByCode 
	 * @Description: 根据省份code查询城市信息服务
	 * @param headMap 报文头
	 * @param bodyMap 省份code
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryPubCityInfoByCode(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pcityCacheServiceFacade.queryPubCityInfoByCode(headMap, bodyMap);
	}
}
