package bros.p.common.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPPmsBankNoCacheServiceFacade 
 * @Description: 联行号查询服务接口
 * @author mazhilei 
 * @date 2016年5月30日 下午4:24:06 
 * @version 1.0
 */
public interface IPPmsBankNoCacheServiceFacade {
	/**
	 * 
	 * @Title: queryAllPubPmsBankNo 
	 * @Description: 查询全部人行联行号信息（bodyMap为空）
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryAllPubPmsBankNo(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubPmsBankNoByName 
	 * @Description: 根据名称模糊查询人行联行号信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryPubPmsBankNoByName(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryPubBankType 
	 * @Description: 查询全部行别信息
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryPubBankType(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
}
