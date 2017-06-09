package bros.c.common.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICPubUnionBankNoCacheServiceFacade 
 * @Description: 网银互联联行号查询服务接口
 * @author haojinhui
 * @date 2016年9月20日
 * @version 1.0
 */
public interface ICPubUnionBankNoCacheServiceFacade {
	/**
	 * 
	 * @Title: queryAllPubUnionBankNo 
	 * @Description: 查询全部网银互联联行号信息（bodyMap为空）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryAllPubUnionBankNo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubUnionBankNoByName 
	 * @Description: 根据名称模糊查询网银互联联行号信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryPubUnionBankNoByName(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubUnionBankNoByBankNo 
	 * @Description: 根据行号查行别信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryPubUnionBankNoByBankNo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
}
