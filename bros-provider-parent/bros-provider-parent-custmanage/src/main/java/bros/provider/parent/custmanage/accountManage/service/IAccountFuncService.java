package bros.provider.parent.custmanage.accountManage.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IAccountFuncService 
 * @Description: 账户转账权限功能维护
 * @author 高永静
 * @date 2016年10月08日 上午9:45:17 
 * @version 1.0
 */
public interface IAccountFuncService {
	/**
	 * 
	 * @Title: queryAccFuncInfoNoChannelMethod
	 * @Description: 根据客户号查询账户功能信息
	 * @param cstId	客户id 
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAccFuncInfoNoChannelMethod(String cstId)throws ServiceException;
	/**
	 * 
	 * @Title: checkAccRightsByBsnCodeMethod
	 * @Description: 检查账户操作权限-根据svrCode
	 * @param legalId 法人id
	 * @param cstNo	客户号
	 * @param bsnCode 业务编码
	 * @param accNo 账号
	 * @param channel 渠道号
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkAccRightsByBsnCodeMethod(String legalId, String cstNo,String bsnCode,String accNo,String channel)throws ServiceException;
	/**
	 * 
	 * @Title: queryFuncClassBsnRelInfoByBsnCodeMethod
	 * @Description: 根据业务代码查询个人网银账户功能业务分类与业务关系
	 * @param legalId 法人id
	 * @param bsnCode 业务编码
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryFuncClassBsnRelInfoByBsnCodeMethod(String legalId,String bsnCode)throws ServiceException;
	
}
