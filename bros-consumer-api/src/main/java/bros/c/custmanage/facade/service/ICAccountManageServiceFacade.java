package bros.c.custmanage.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICAccountManageServiceFacade 
 * @Description: 账户管理接口
 * @author gaoyongjing 
 * @date 2016年9月7日 下午2:44:40 
 * @version 1.0
 */
public interface ICAccountManageServiceFacade {
	
	/**
	 * 
	 * @Title: queryAccountInfoList 
	 * @Description: 签约账户列表(卡折)查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public ResponseEntity queryAccountInfoListMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryAccDetailList 
	 * @Description: 动账交易明细查询,交易明细查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity queryAccDetailListMethod(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: suspendAccountMethod 
	 * @Description: 账户挂失
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity suspendAccountMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: addAccountInfoMethod 
	 * @Description: 账户加挂
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity addAccountInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: deleteAccountInfoMethod 
	 * @Description: 账户解挂
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity deleteAccountInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: setAccountAliasMethod 
	 * @Description: 账户别名设置
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity setAccountAliasMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: updateDefaultAccountInfo 
	 * @Description: 默认账户修改
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity updateDefaultAccountInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
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
