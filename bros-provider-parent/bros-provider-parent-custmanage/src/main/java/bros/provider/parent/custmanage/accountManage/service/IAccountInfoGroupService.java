package bros.provider.parent.custmanage.accountManage.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IAccountInfoGroupService 
 * @Description: 账户管理
 * @author 高永静
 * @date 2016年10月08日 上午9:45:17 
 * @version 1.0
 */
public interface IAccountInfoGroupService {
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
	public Map<String, Object> queryAccountByAccNoMethod(String cstId,String accNo,String channel)throws ServiceException;
}
