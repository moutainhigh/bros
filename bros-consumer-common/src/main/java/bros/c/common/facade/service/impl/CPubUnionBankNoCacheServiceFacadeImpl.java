package bros.c.common.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.common.facade.service.ICPubUnionBankNoCacheServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPPubUnionBankNoCacheServiceFacade;
/**
 * 
 * @ClassName: CPubUnionBankNoCacheServiceFacadeImpl 
 * @Description: 网银互联联行号查询服务接口服务实现类
 * @author haojinhui 
 * @date 2016年9月20日
 * @version 1.0
 */
@Component("cpubunionBankNoCacheServiceFacade")
public class CPubUnionBankNoCacheServiceFacadeImpl implements ICPubUnionBankNoCacheServiceFacade {
	/**
	 * 联行号查询业务服务
	 */
	@Autowired
	private IPPubUnionBankNoCacheServiceFacade ppubunionBankNoCacheServiceFacad;
	
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
		return ppubunionBankNoCacheServiceFacad.queryAllPubUnionBankNo(headMap, bodyMap);
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
		return ppubunionBankNoCacheServiceFacad.queryPubUnionBankNoByName(headMap, bodyMap);
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
		return ppubunionBankNoCacheServiceFacad.queryPubUnionBankNoByBankNo(headMap, bodyMap);
	}
}
