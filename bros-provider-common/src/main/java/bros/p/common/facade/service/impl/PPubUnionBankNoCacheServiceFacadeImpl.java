package bros.p.common.facade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPPubUnionBankNoCacheServiceFacade;
import bros.provider.parent.cache.unionbankno.IPubUnionBankNo;
/**
 * 
 * @ClassName: PPubUnionBankNoCacheServiceFacadeImpl 
 * @Description: 网银互联联行号查询服务接口服务实现类
 * @author haojinhui 
 * @date 2016年9月20日
 * @version 1.0
 */
@Component("ppubunionBankNoCacheServiceFacade")
public class PPubUnionBankNoCacheServiceFacadeImpl implements IPPubUnionBankNoCacheServiceFacade {
	/**
	 * 联行号查询业务服务
	 */
	@Autowired
	private IPubUnionBankNo pubUnionBankNo;
	
	/**
	 * 
	 * @Title: queryAllPubUnionBankNo 
	 * @Description: 查询全部网银互联联行号信息（bodyMap为空）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAllPubUnionBankNo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubUnionBankNo.queryAllPubUnionBankNo();
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
	}

	/**
	 * 
	 * @Title: queryPubUnionBankNoByName 
	 * @Description: 根据名称模糊查询网银互联联行号信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryPubUnionBankNoByName(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String name = (String) (bodyMap.get("name")==null?"":bodyMap.get("name"));
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> list = pubUnionBankNo.queryPubUnionBankNoByName(name);
		map.put("list", list);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
	}
	/**
	 * 
	 * @Title: queryPubUnionBankNoByBankNo 
	 * @Description: 根据行号查行别信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryPubUnionBankNoByBankNo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		String bankNo = (String) (bodyMap.get("bankNo")==null?"":bodyMap.get("bankNo"));
		Map<String,Object> map = new HashMap<String, Object>();
		map = pubUnionBankNo.queryPubUnionBankNoByBankNo(bankNo);
		ResponseEntity entity = new ResponseEntity(map);
		return entity;
	}

}
