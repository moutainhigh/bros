package bros.c.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICSavingsAcountServiceFacade 
 * @Description: 储蓄账户服务发布接口
 * @author huangdazhou 
 * @date 2016年10月19日 上午10:45:02 
 * @version 1.0
 */
public interface ICSavingsAcountServiceFacade {
	/**
	 * 
	 * @Title: queryDepositType 
	 * @Description: 储种查询
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回对象
	 * @throws ServiceException
	 */
	public ResponseEntity queryDepositType(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: tranCurrentToFix 
	 * @Description: 活期转定期
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException 异常信息
	 */
	public ResponseEntity tranCurrentToFix(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryAccountList 
	 * @Description: 定转活账号查询
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryAccountList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: tranFixToCurrent 
	 * @Description: 定转活
	 * @param headMap
	 * @param bodyMap
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity tranFixToCurrent(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
