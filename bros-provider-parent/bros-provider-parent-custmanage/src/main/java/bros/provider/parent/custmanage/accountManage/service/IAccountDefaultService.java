package bros.provider.parent.custmanage.accountManage.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IAccountDefaultService 
 * @Description: 默认账户维护
 * @author 高永静
 * @date 2016年10月08日 上午9:45:17 
 * @version 1.0
 */
public interface IAccountDefaultService {
	/**
	 * 
	 * @Title: queryAccInfDefaultByAccNoandCstNoMethod
	 * @Description: 通过法人id、账号和 客户号   查询默认账户
	 * @param cstId	客户id 
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAccInfDefaultByAccNoandCstNoMethod(String cstId,String accNo)throws ServiceException;
}
