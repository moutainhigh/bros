package bros.provider.parent.custmanage.accountManage.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IAccountManageService 
 * @Description: 账户管理接口
 * @author 高永静
 * @date 2016年9月22日 上午9:45:17 
 * @version 1.0
 */
public interface IAccountManageService {
	/**
	 * 
	 * @Title: checkInnerAccountByAccNoMethod
	 * @Description: 验证是否行内账户
	 * @param accountNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkInnerAccountByAccNoMethod(String accountNo)throws ServiceException;
	/**
	 * 
	 * @Title: queryAccountInfoMethod
	 * @Description: 账户信息查询
	 * @param accountNo 账号
	 * @param currency 币种
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAccountInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: checkAccountInfoToHostMethod
	 * @Description: 核心通讯账户信息查询
	 * @param accountNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> checkAccountInfoToHostMethod(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAccountMassageMethod
	 * @Description: 核心通讯账户信息查询
	 * @param accountNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> checkAccountMassageMethod(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: queyAccountInfoHostMethod
	 * @Description: 核心通讯账户信息查询、个人账户验证
	 * @param accountNo	账号
	 * @param certType	证件类型
	 * @param certNo	证件号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> queryAccountInfoHostMethod(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: queryAccountInfoSubList
	 * @Description: 核心通讯子账户列表查询（查询定期、活期子账户）
	 * @param accountNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAccountInfoSubListHost(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException;
	/**
	 * 
	 * @Title: checkPassword 
	 * @Description: 校验密码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public Map<String, Object> checkPassword(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: tranProcess 
	 * @Description: 转账汇款
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public Map<String, Object> tranProcess(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
	 * 
	 * @Title: sendSmsAuthenticationCode 
	 * @Description: 发送短信通知(不验签约关系和短信服务状态)
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public Map<String, Object> sendSmsAuthenticationCode(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
}
