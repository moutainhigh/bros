package bros.p.common.facade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPPubProvinceCacheServiceFacade;
import bros.provider.parent.cache.provinceandcityinfo.IPubProvinceCacheInfo;
/**
 * 
 * @ClassName: PPubProvinceCacheServiceFacadeImpl 
 * @Description: 省份信息操作接口。
 * @author mazhilei 
 * @date 2016年5月30日 下午5:39:18 
 * @version 1.0
 */
@Component("ppubProvinceCacheServiceFacade")
public class PPubProvinceCacheServiceFacadeImpl implements IPPubProvinceCacheServiceFacade {

	@Autowired
	private IPubProvinceCacheInfo pubProvinceCacheInfo;
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
	public ResponseEntity queryAllPubProvinceCacheInfo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubProvinceCacheInfo.queryAllPubProvinceCacheInfo();
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
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
	@Validation(value="p0000203")
	@Override
	public ResponseEntity queryPubProvinceCacheInfoByName(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String name = (String) (bodyMap.get("name")==null?"":bodyMap.get("name"));
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubProvinceCacheInfo.queryPubProvinceCacheInfoByName(name);
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
	}

}
