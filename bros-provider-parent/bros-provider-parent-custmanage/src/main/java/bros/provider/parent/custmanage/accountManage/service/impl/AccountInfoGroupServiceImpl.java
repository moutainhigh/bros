package bros.provider.parent.custmanage.accountManage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bros.common.core.exception.ServiceException;
import bros.provider.parent.custmanage.accountManage.service.IAccountInfoGroupService;
import bros.provider.parent.custmanage.accountManage.service.IAccountInfoService;
/**
 * 
 * @ClassName: AccountInfoGroupServiceImpl 
 * @Description: 账户管理
 * @author 高永静
 * @date 2016年10月08日 上午9:45:17 
 * @version 1.0
 */
@Repository(value = "accountInfoGroupService")
public class AccountInfoGroupServiceImpl implements IAccountInfoGroupService {
	/**
	 * 账户信息服务
	 */
	@Autowired
	private IAccountInfoService accountInfoService;
	/**
	 * 
	 * @Title: queryAccountByAccNoMethod
	 * @Description: 根据账号查询本人网银签约账号信息
	 * @param cstId	客户id 
	 * @param accNo 账号
	 * @param channel 渠道
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> queryAccountByAccNoMethod(String cstId, String accNo,String channel) throws ServiceException {
		List<Map<String, Object>> resultList = null;
		Map<String,Object> result = null;
		resultList = accountInfoService.queryAccInfChannelBySttAccNoMethod(cstId, accNo, "0","");
		if(resultList != null){
			result = resultList.get(0);
			result.putAll(accountInfoService.queryAccInfByAccNoMethod(accNo));
		}
		return result;
	}
}
