package bros.provider.parent.custmanage.accountManage.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.constants.PersonConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.router.service.IHttpClientRouter;
import bros.provider.parent.custmanage.accountManage.service.IAccountManageService;
import bros.provider.parent.custmanage.constants.CustManageFormatCodeConstants;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
/**
 * 
 * @ClassName: AccountManageServiceImpl 
 * @Description: 账户管理接口实现类
 * @author 高永静
 * @date 2016年9月22日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "accountManageService")
public class AccountManageServiceImpl implements IAccountManageService {
	private static final Logger logger = LoggerFactory.getLogger(AccountManageServiceImpl.class);
	/**
	 * 通讯
	 */
	@Autowired
	private IHttpClientRouter httpClientRouter;
	/**
	 * 
	 * @Title: checkInnerAccountByAccNoMethod
	 * @Description: 验证是否行内账户
	 * @param accountNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public boolean checkInnerAccountByAccNoMethod(String accountNo)
			throws ServiceException {
		try{
			if(null != accountNo && accountNo.length() >= 6){
				if(PersonConstants.SELF_ACC_BIN.indexOf(accountNo.substring(0, 6)) != -1){
					return true;
				}
			}
			return false;
		}catch(Exception e){
			logger.error("验证是否本行账户失败  "+this.getClass()+".checkInnerAccount", e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0041,"验证是否本行账户失败");
		}
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
	public Map<String,Object> queryAccountInfoMethod(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000002);
		} catch (ServiceException se) {
			logger.error("查询账户信息失败   " + this.getClass() + ".queryAccountInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询账户信息失败    " + this.getClass() + ".queryAccountInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0042,"查询账户信息失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: checkAccountInfoToHostMethod
	 * @Description: 核心通讯账户信息查询
	 * @param accountNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> checkAccountInfoToHostMethod(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000175);
		} catch (ServiceException se) {
			logger.error("查询账户信息失败   " + this.getClass() + ".queryAccountInfoMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询账户信息失败    " + this.getClass() + ".queryAccountInfoMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0042,"查询账户信息失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: checkAccountMassageMethod
	 * @Description: 核心通讯账户信息查询
	 * @param accountNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> checkAccountMassageMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000083);
		} catch (ServiceException se) {
			logger.error("查询账户信息失败   " + this.getClass() + ".checkAccountMassageMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询账户信息失败    " + this.getClass() + ".checkAccountMassageMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0042,"查询账户信息失败", e);
		}
		return resultMap;
	}
	
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
	@Override
	public Map<String, Object> queryAccountInfoHostMethod(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.PSVR000054);
		} catch (ServiceException se) {
			logger.error("个人账户验证失败   " + this.getClass() + ".queryAccountInfoHostMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("个人账户验证失败    " + this.getClass() + ".queryAccountInfoHostMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0043,"个人账户验证失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: checkAccountMassageMethod
	 * @Description: 核心通讯子账户列表查询（查询定期、活期子账户）
	 * @param accountNo 账号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String,Object> queryAccountInfoSubListHost(Map<String, Object> headMap,
			Map<String, Object> bodyMap)throws ServiceException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, "PSVR000052");
		} catch (ServiceException se) {
			logger.error("查询子账户列表失败   " + this.getClass() + ".queryAccountInfoSubListHost",se);
			throw se;
		} catch (Exception e) {
			logger.error("查询子账户列表失败    " + this.getClass() + ".queryAccountInfoSubListHost",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0046,"查询子账户列表失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: checkPassword 
	 * @Description: 校验密码
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public Map<String, Object> checkPassword(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.CFCODE0005);
		} catch (ServiceException se) {
			logger.error("校验密码失败   " + this.getClass() + ".queyAccountInfoHostMethod",se);
			throw se;
		} catch (Exception e) {
			logger.error("校验密码失败    " + this.getClass() + ".queyAccountInfoHostMethod",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0048,"校验密码失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: tranProcess 
	 * @Description: 转账汇款
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public Map<String, Object> tranProcess(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.TRANSFER_CODE);
		} catch (ServiceException se) {
			logger.error("转账汇款失败   " + this.getClass() + ".tranProcess",se);
			throw se;
		} catch (Exception e) {
			logger.error("转账汇款失败    " + this.getClass() + ".tranProcess",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0049,"转账汇款失败", e);
		}
		return resultMap;
	}
	/**
	 * 
	 * @Title: sendSmsAuthenticationCode 
	 * @Description: 发送短信通知(不验签约关系和短信服务状态)
	 * @param headMap 公共报文头
	 * @param bodyMap 报文体
	 * @throws ServiceException
	 */
	public Map<String, Object> sendSmsAuthenticationCode(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap = httpClientRouter.send(headMap, bodyMap, CustManageFormatCodeConstants.SENDSMSAUTHENTICATION_CODE);
		} catch (ServiceException se) {
			logger.error("发送短信通知失败   " + this.getClass() + ".sendSmsAuthenticationCode",se);
			throw se;
		} catch (Exception e) {
			logger.error("发送短信通知失败    " + this.getClass() + ".sendSmsAuthenticationCode",e);
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0050,"发送短信通知失败", e);
		}
		return resultMap;
	}
}
