/**   
 * @Title: IQryInnerUserService.java 
 * @Package bros.provider.parent.activiti.user.service 
 * @Description: 用一句话描述该文件做什么 
 * @author weiyancheng
 * @date 2016年7月12日 下午1:41:16 
 * @version 1.0   
 */
package bros.provider.parent.activiti.user.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IQryInnerUserService 
 * @Description: 查询内部用户信息服务
 * @author weiyancheng
 * @date 2016年7月12日 下午1:41:16 
 * @version 1.0 
 */
public interface IQryInnerUserService {

	/**
	 * @Title: qryTellerIdByTellCode 
	 * @Description: 根据法人、柜员编号查询柜员id
	 * @param legalId 法人ID
	 * @param tellerNo 柜员编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryTellerIdByTellCode(String legalId,String tellerNo) throws ServiceException;
	/**
	 * @Title: qryTellerIdByTellCode 
	 * @Description: 根据柜员id查询柜员角色
	 * @param param
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryTellerRoleIdByTellerId(String tellerId) throws ServiceException;
	/**
	 * @Title: qrySameRoleTellerListByOneTellerIdAndBranch 
	 * @Description: 根据柜员id、法人id,机构号，查询该机构下与该柜员角色相同的柜员列表
	 * @param tellerId 柜员id
	 * @param branchNo 机构号
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qrySameRoleTellerListByOneTellerIdAndBranch(String legalId,String tellerId,String branchNo) throws ServiceException;
	
	/**
	 * @Title: qryTellerListByBranchAndRoleList 
	 * @Description: 根据柜员id、法人id,机构号，角色列表本机构及上级机构的符合角色列表的柜员，同时这些柜员要排除传入的柜员id
	 * @param legalId 法人id
	 * @param tellerId 柜员id
	 * @param branchNo 机构号
	 * @param roleIdList 角色列表
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryTellerListByBranchAndRoleList(String legalId,String tellerId,String branchNo,List<Map<String, Object>>  roleIdList) throws ServiceException;
	/**
	 * @Title: qryAuthUserAndNumber 
	 * @Description: 根据授权规则取对应的柜员和人数
	 * @param legalId 法人
	 * @param userId 柜员号
	 * @param branchNo 机构号
	 * @param authRuleMap 授权规则
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryAuthUserAndNumberByAuthRule(String legalId,String userId,String branchNo,Map<String, Object> authRuleMap)throws ServiceException;
	/**
	 * @Title: qryTellerInfoById 
	 * @Description: 根据柜员ID List 查询柜员信息列表
	 * @param userIdList 柜员ID List
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryTellerInfoById(List<String> userIdList)throws ServiceException;
}
