package bros.p.common.facade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPPmsBankNoCacheServiceFacade;
import bros.provider.parent.cache.pmsbanknoinfo.IPubPmsBankNoCacheInfo;
/**
 * 
 * @ClassName: PPmsBankNoCacheServiceFacadeImpl 
 * @Description: 联行号查询服务接口服务实现类
 * @author mazhilei 
 * @date 2016年5月30日 下午4:27:45 
 * @version 1.0
 */
@Component("ppmsBankNoCacheServiceFacade")
public class PPmsBankNoCacheServiceFacadeImpl implements IPPmsBankNoCacheServiceFacade {
	/**
	 * 联行号查询业务服务
	 */
	@Autowired
	private IPubPmsBankNoCacheInfo pubPmsBankNoCacheInfo;
	
	/**
	 * 
	 * @Title: queryAllPubPmsBankNo 
	 * @Description: 查询全部人行联行号信息（bodyMap为空）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAllPubPmsBankNo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubPmsBankNoCacheInfo.queryAllPubPmsBankNo();
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
	}

	/**
	 * 
	 * @Title: queryPubPmsBankNoByName 
	 * @Description: 根据模糊名称，城市代码，行别代码查询人行联行号信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryPubPmsBankNoByName(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String name = (String) (bodyMap.get("name")==null?"":bodyMap.get("name"));
		String bankType = (String) (bodyMap.get("bankType")==null?"":bodyMap.get("bankType"));
		String cityCode = (String) (bodyMap.get("cityCode")==null?"":bodyMap.get("cityCode"));
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubPmsBankNoCacheInfo.queryPubPmsBankNoByName(name,bankType,cityCode);
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
	}
	
	/**
	 * 
	 * @Title: queryPubBankType 
	 * @Description: 查询全部行别信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryPubBankType(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubPmsBankNoCacheInfo.queryPubBankType();
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
	}
}
