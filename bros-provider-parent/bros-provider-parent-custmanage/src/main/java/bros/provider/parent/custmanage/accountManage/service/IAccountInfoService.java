package bros.provider.parent.custmanage.accountManage.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IAccountInfoService 
 * @Description: 账户信息
 * @author 高永静
 * @date 2016年10月08日 上午9:45:17 
 * @version 1.0
 */
public interface IAccountInfoService {
	/**
	 * 
	 * @Title: queryAccInfChannelBySttAccNoMethod
	 * @Description: 根据查询条件查询账号信息
	 * @param cstId	客户id 
	 * @param accNo 账号
	 * @param stt 账号状态
	 * @param accountType 账户类型
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String,Object>> queryAccInfChannelBySttAccNoMethod(String cstId,String accNo,String stt,String accountType)throws ServiceException;
	/**
	 * 
	 * @Title: updateAccInfChannelSttMethod
	 * @Description: 更新账户状态
	 * @param cstId	客户id 
	 * @param accNo 账号
	 * @param stt 账号状态
	 * @return
	 * @throws ServiceException
	 */
	public void updateAccInfChannelSttMethod(String cstId,String accNo,String stt)throws ServiceException;
	/**
	 * 
	 * @Title: queryAccInfByAccNoMethod
	 * @Description: 根据账号查询账号信息
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryAccInfByAccNoMethod(String accNo) throws ServiceException;
	/**
	 * 
	 * @Title: updateAccountAliasMethod
	 * @Description: 更改账户别名
	 * @param cstId 客户标识ID
	 * @param accNo 账号
	 * @param accAlias 账号别名
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> updateAccountAliasMethod(String cstId,String accNo,String accAlias) throws ServiceException;
	/**
	 * 
	 * @Title: deleteTprAccChannelInfoByCstIdAndAccNoMethod
	 * @Description: 根据客户标识及账号删除表tpr_acc_channel_info
	 * @param cstId 客户标识ID
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public void deleteTprAccChannelInfoByCstIdAndAccNoMethod(String cstId,String accNo) throws ServiceException;
	/**
	 * 
	 * @Title: deleteTprAccInfoByCstIdAndAccNoMethod
	 * @Description: 根据客户标识And账号删除tpr_acc_info
	 * @param cstId 客户标识ID
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public void deleteTprAccInfoByCstIdAndAccNoMethod(String cstId, String accNo) throws ServiceException;
	/**
	 * 
	 * @Title: deleteTprAccFuncByCstIdAndAccNoMethod
	 * @Description: 根据客户标识And账号删除TPR_ACC_FUNC
	 * @param cstId 客户标识ID
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public void deleteTprAccFuncByCstIdAndAccNoMethod(String cstId, String accNo) throws ServiceException;
	/**
	 * 
	 * @Title: queryBranchByAccNoMethod
	 * @Description: 根据账号查询开户行行名
	 * @param accNo 账号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,Object> queryBranchByAccNoMethod(String accNo) throws ServiceException;
}
