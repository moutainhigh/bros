package bros.p.common.facade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPCityCacheServiceFacade;
import bros.provider.parent.cache.provinceandcityinfo.IPubCityCacheInfo;
/**
 * 
 * @ClassName: PCityCacheServiceFacadeImpl 
 * @Description: 城市信息数据库及缓存操作
 * @author mazhilei 
 * @date 2016年5月30日 下午5:23:39 
 * @version 1.0
 */
@Component("pcityCacheServiceFacade")
public class PCityCacheServiceFacadeImpl implements IPCityCacheServiceFacade {
	@Autowired
	private IPubCityCacheInfo pubCityCacheInfo;
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
	public ResponseEntity queryAllPubCityInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubCityCacheInfo.queryAllPubCityInfo();
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
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
	@Validation(value="p0000201")
	@Override
	public ResponseEntity queryPubCityInfoByName(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String name = (String) (bodyMap.get("name")==null?"":bodyMap.get("name"));
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubCityCacheInfo.queryPubCityInfoByName(name);
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
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
	public ResponseEntity queryPubCityInfoByCode(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String provinceId = (String) (bodyMap.get("provinceId")==null?"":bodyMap.get("provinceId"));
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubCityCacheInfo.queryPubCityInfoByCode(provinceId);
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
	}
}
