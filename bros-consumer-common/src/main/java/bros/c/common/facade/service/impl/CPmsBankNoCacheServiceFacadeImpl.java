package bros.c.common.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.common.facade.service.ICPmsBankNoCacheServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.common.facade.service.IPPmsBankNoCacheServiceFacade;
/**
 * 
 * @ClassName: CPmsBankNoCacheServiceFacadeImpl 
 * @Description: 联行号查询服务实现
 * @author 何鹏
 * @date 2016年7月11日 下午2:03:27 
 * @version 1.0
 */
@Component("cpmsBankNoCacheServiceFacade")
public class CPmsBankNoCacheServiceFacadeImpl implements ICPmsBankNoCacheServiceFacade {
	
	@Autowired
	private IPPmsBankNoCacheServiceFacade ppmsBankNoCacheServiceFacade;
	
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
	public ResponseEntity queryAllPubPmsBankNo(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return ppmsBankNoCacheServiceFacade.queryAllPubPmsBankNo(headMap, bodyMap);
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
	public ResponseEntity queryPubPmsBankNoByName(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return ppmsBankNoCacheServiceFacade.queryPubPmsBankNoByName(headMap, bodyMap);
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
	public ResponseEntity queryPubBankType(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return ppmsBankNoCacheServiceFacade.queryPubBankType(headMap, bodyMap);
	}
}
