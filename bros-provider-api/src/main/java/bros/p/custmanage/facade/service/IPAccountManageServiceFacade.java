package bros.p.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IPAccountManageServiceFacade 
 * @Description: 账户管理接口
 * @author gaoyongjing 
 * @date 2016年9月7日 下午2:44:40 
 * @version 1.0
 */
public interface IPAccountManageServiceFacade {
	
	/**
	 * 
	 * @Title: queryAccountInfoList 
	 * @Description: 签约账户列表(卡折)查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity queryAccountInfoList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAccDetailList 
	 * @Description: 动账交易明细查询,交易明细查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryAccDetailList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: suspendAccount 
	 * @Description: 账户挂失
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity suspendAccount(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: addAccountInfo 
	 * @Description: 账户加挂
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity addAccountInfo(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteAccountInfo 
	 * @Description: 账户解挂
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity deleteAccountInfo(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: setAccountAlias 
	 * @Description: 账户别名设置
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity setAccountAlias(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateDefaultAccountInfo 
	 * @Description: 默认账户修改
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateDefaultAccountInfo(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryAccountInfoMethod
	 * @Description: 账户信息查询
	 * @param accountNo 账号
	 * @param currency 币种
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryAccountInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: queryAccountInfoSubList 
	 * @Description: 子账户列表查询（查询定期、活期子账户）
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity queryAccountInfoSubList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
}
