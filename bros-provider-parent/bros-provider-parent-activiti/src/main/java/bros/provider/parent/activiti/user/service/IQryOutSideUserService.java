package bros.provider.parent.activiti.user.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IQryOutSideUserService 
 * @Description: 查询企业授权用户服务接口
 * @author weiyancheng
 * @date 2016年7月28日 下午12:46:05 
 * @version 1.0 
 */
public interface IQryOutSideUserService {
	
	/**
	 * @Title: qryOperatorIdByNo 
	 * @Description: 根据法人ID+客户号+操作员编号查询操作员ID
	 * @param legalId 法人ID
	 * @param cstNo 客户号
	 * @param operatorNo 操作员号
	 * @return
	 * @throws ServiceException
	 */
	public String qryOperatorIdByNo(String legalId,String cstNo,String operatorNo)throws ServiceException;

	/**
	 * @Title: qryManageUser 
	 * @Description: 根据法人ID+客户号查询该客户下有管理权限的操作员列表（排除指令提交操作员）
	 * @param legalId 法人ID
	 * @param cstNo   客户号
	 * @param userId   指令提交操作员ID
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryManageUser(String legalId,String cstNo,String userId)throws ServiceException;
	/**
	 * @Title: qryUserByRoleId 
	 * @Description: 根据角色ID查询对应的操作员列表，排除指令提交操作员
	 * @param roleId 角色ID
	 * @param userId 角操作员ID
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryUserByRoleId(String roleId,String userId)throws ServiceException;
	/**
	 * @Title: qryUserByRoleId 
	 * @Description: 根据授权规则查询操作员列表，排除指令提交操作员
	 * @param ruleMap 授权规则
	 * @param userId 指令提交操作员ID
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryUserByAuthRule(Map<String, Object> authRuleMap,String userId)throws ServiceException;
	/**
	 * @Title: qryCstInfoByCstNo 
	 * @Description: 查询客户渠道信息
	 * @param legalId 法人ID
	 * @param cstNo 客户号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryCstInfoByCstNo(String legalId,String cstNo)throws ServiceException;
}
