package bros.c.custmanage.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.custmanage.facade.service.ICAccountManageServiceFacade;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.custmanage.facade.service.IPAccountManageServiceFacade;

/**
 * 
 * @ClassName: caccountManageServiceFacade 
 * @Description: 账户管理接口
 * @author gaoyongjing 
 * @date 2016年9月7日 下午2:44:40 
 * @version 1.0
 */
@Component("caccountManageServiceFacade")
public class CAccountManageServiceFacadeImpl implements
		ICAccountManageServiceFacade {
	/**
	 * 通讯
	 */
	@Autowired
	private IPAccountManageServiceFacade paccountManageServiceFacade;
	/**
	 * 
	 * @Title: queryAccountInfoList 
	 * @Description: 签约账户列表(卡折)查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccountInfoListMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		 
		return paccountManageServiceFacade.queryAccountInfoList(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryAccDetailList 
	 * @Description: 动账交易明细查询,交易明细查询
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccDetailListMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		 
		return paccountManageServiceFacade.queryAccDetailList(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: suspendAccountMethod 
	 * @Description: 账户挂失
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity suspendAccountMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		return paccountManageServiceFacade.suspendAccount(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: addAccountInfoMethod 
	 * @Description: 账户加挂
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity addAccountInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		return paccountManageServiceFacade.addAccountInfo(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: deleteAccountInfoMethod 
	 * @Description: 账户解挂
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity deleteAccountInfoMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		return paccountManageServiceFacade.deleteAccountInfo(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: setAccountAliasMethod 
	 * @Description: 账户别名设置
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity setAccountAliasMethod(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		
		return paccountManageServiceFacade.setAccountAlias(headMap, bodyMap);
	}
	
	/**
	 * 
	 * @Title: updateDefaultAccountInfo 
	 * @Description: 默认账户修改
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity updateDefaultAccountInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return paccountManageServiceFacade.updateDefaultAccountInfo(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryAccountInfoMethod
	 * @Description: 账户信息查询
	 * @param accountNo 账号
	 * @param currency 币种
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccountInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException{
		return paccountManageServiceFacade.queryAccountInfoMethod(headMap, bodyMap);
	}
	/**
	 * 
	 * @Title: queryAccountInfoSubList 
	 * @Description: 账户信息列表查询（查询定期、活期子账户）
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity queryAccountInfoSubList(Map<String,Object> headMap,Map<String,Object> bodyMap) throws ServiceException{
		return paccountManageServiceFacade.queryAccountInfoSubList(headMap, bodyMap);
	}
}
