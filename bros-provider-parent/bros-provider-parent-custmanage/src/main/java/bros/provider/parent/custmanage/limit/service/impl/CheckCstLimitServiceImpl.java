package bros.provider.parent.custmanage.limit.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.custmanage.accountManage.service.IAccountInfoGroupService;
import bros.provider.parent.custmanage.accountManage.service.IAccountManageService;
import bros.provider.parent.custmanage.constants.CustmanageErrorCodeConstants;
import bros.provider.parent.custmanage.limit.service.ICheckCstLimitService;


/**
 * @ClassName: ICheckCstLimitService
 * @Description: TODO(统一限额检查服务实现类,包括：本行本人账户限额和本行他人账户限额)
 * @author gaoyongjing
 * @date 2016-10-11 下午04:42:42
 * 
 */
@Service(value = "checkCstLimitServiceImpl")
public class CheckCstLimitServiceImpl implements ICheckCstLimitService {
	/**
	 * 账户管理服务
	 */
	@Autowired
	private IAccountManageService accountManageService;
	/**
	 * 账户信息服务
	 */
	@Autowired
	private IAccountInfoGroupService accountInfoGroupService;
	
	/**
	 * 限额检查
	 */
	public boolean checkCstLimitBefore(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		String payAccNo = (String) bodyMap.get("payAccNo");
		
		
		if (isSameNameTrans(headMap,bodyMap)){
			return false;
		}

		bodyMap.put("accNo", payAccNo);
		if (isCheckInternalLimit(bodyMap)) {
			return true;
 			//检查限额设置
			//limitCheckService.checkLimit(reqMap);
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: isSameNameTrans
	 * @Description: TODO(付款账号和收款账号都是本人本行账号,不受限额控制)
	 * @param legalId 
	 * 			  法人id
	 * @param cstNo
	 *            客户号
	 * @param payAccNo
	 *            付款账号
	 * @param rcvAccNo
	 *            收款账号
	 * @param channel
	 *            渠道标志
	 * @return boolean true-同名转账; false-非同名转账
	 * @throws ServiceException
	 * 
	 */
	private boolean isSameNameTrans(Map<String, Object> headMap,Map<String,Object> bodyMap) throws ServiceException {
		String cstId = (String) bodyMap.get("cstId");
		String payAccNo = (String) bodyMap.get("payAccNo");
		String rcvAccNo = (String) bodyMap.get("rcvAccNo");
		String channel = (String) bodyMap.get("channel");
		
		String isPayAccExit = checkAccSelf(cstId, payAccNo, channel);
		if ("0".equals(isPayAccExit)){
			String isRcvAccExit = checkAccSelf(cstId, rcvAccNo, channel);
			if("0".equals(isRcvAccExit)){
				return true;
			}else{//如果不能从渠道整合平台查询出账号信息，就去核心查询
				Map<String,Object> resultMap = new HashMap<String,Object>();
				bodyMap.put("accountNo", payAccNo);
				try{
					resultMap = goHostCheckAccSelf(headMap,bodyMap);
				}catch(ServiceException ex){
					throw new ServiceException(ex.getErrorCode(),"付款人账户信息不存在");
				}catch(Exception e){
					throw new ServiceException(CustmanageErrorCodeConstants.PPCG0042,"账户信息查询失败");
				}
				String custNo = (String) resultMap.get("custNo");
				bodyMap.put("accountNo", rcvAccNo);
				try{
					goHostCheckAccSelf(headMap,bodyMap);
				}catch(ServiceException ex){
					resultMap.put(HeadParameterDefinitionConstants.SEN_RETURNCODE, "00000000");
					resultMap.put(HeadParameterDefinitionConstants.SEN_RETURNMSG, "处理成功!");
					return false;
				}catch(Exception e){
					throw new ServiceException(CustmanageErrorCodeConstants.PPCG0042,"账户信息查询失败");
				}
				String custNo1 = (String) resultMap.get("custNo");
				if(custNo.equals(custNo1)){
					return true;
				}
			}
		}
		//根据账号去核心查询账号信息，比较收付款人是否是同一个人
		return false;
	}

	/**
	 * 
	 * @Title: isCheckInternalLimit
	 * @Description: TODO(付款账号是本人本行账号,检查网银限额)
	 * @param cstNo
	 *            客户号
	 * @param payAccNo
	 *            付款账号
	 * @param channel
	 *            渠道标志
	 * @return boolean true-检查网银限额; false-不检查网银限额
	 * @throws ServiceException
	 * 
	 */
	private boolean isCheckInternalLimit(Map<String,Object> reqMap) throws ServiceException {
		String cstId = (String) reqMap.get("cstId");
		String accNo = (String) reqMap.get("accNo");
		String channel = (String) reqMap.get("channel");
		if ("0".equals(checkAccSelf(cstId, accNo, channel))) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: checkAccSelf
	 * @Description: TODO(判断账号是否为本行本人账号)
	 * @param cstNo
	 *            客户号
	 * @param accNo
	 *            账号
	 * @param channel
	 *            渠道标志
	 * @return String 归属标志，0-本行本人账号 1-非本行本人账号
	 * @throws ServiceException
	 * 
	 */
	private String checkAccSelf(String cstId,String accNo,String channel) throws ServiceException {
		// 应该判断全渠道...
		Map<String,Object> tmpvo = accountInfoGroupService.queryAccountByAccNoMethod(cstId, accNo, channel);
		return tmpvo == null ? "1" : "0";
	}
	
	private Map<String,Object> goHostCheckAccSelf(Map<String, Object> headMap,Map<String,Object> bodyMap) throws ServiceException {
		// 应该判断全渠道...
		try{
			Map<String,Object> resultMap = accountManageService.checkAccountInfoToHostMethod(headMap, bodyMap);
			return resultMap;
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(CustmanageErrorCodeConstants.PPCG0042,"账户信息查询失败");
		}
	}

}
