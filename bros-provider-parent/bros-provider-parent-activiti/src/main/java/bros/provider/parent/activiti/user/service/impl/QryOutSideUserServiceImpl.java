package bros.provider.parent.activiti.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.activiti.constants.ActivitiParamsConstants;
import bros.common.core.constants.UserStatusConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.user.service.IQryOutSideUserService;
import bros.provider.parent.activiti.util.ActivitiUtil;

/** 
 * @ClassName: QryOutSideUserServiceImpl 
 * @Description:  查询企业授权用户服务实现
 * @author weiyancheng
 * @date 2016年7月28日 下午4:38:17 
 * @version 1.0 
 */
@Component("qryOutSideUserService")
public class QryOutSideUserServiceImpl implements IQryOutSideUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(QryOutSideUserServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/** 
	 * 根据法人ID+客户号查询该客户下有管理权限的操作员列表（排除指令提交操作员）
	 */
	@Override
	public List<Map<String, Object>> qryManageUser(String legalId, String cstNo,String userId)
			throws ServiceException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("legalId", legalId);
			param.put("cstNo", cstNo);
			param.put("userId", userId);
			param.put("userState", UserStatusConstants.TTP_CST_STATE_0);
			param.put("operatorState", UserStatusConstants.TTP_OPERATOR_STATE_0);
			List<Map<String, Object>> userList = myBatisDaoSysDao.selectList("mybatis.mapper.activiti.outside.table.workflowoutsideuser.queryManageUserList", param);
			return userList;
		}catch (ServiceException se) {
			logger.error("查询企业管理权限授权操作员列表失败   " + this.getClass() + ".qryManageUser");
			throw se;
		} catch (Exception e) {
			logger.error("查询企业管理权限授权操作员列表失败   " + this.getClass() + ".qryManageUser");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0034,"查询企业管理权限授权操作员列表失败", e);
		}
	}

	/** 
	 * 根据角色ID查询对应的操作员列表，排除指令提交操作员
	 */
	@Override
	public List<Map<String, Object>> qryUserByRoleId(String roleId,String userId)
			throws ServiceException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("roleId", roleId);
			param.put("userId", userId);
			param.put("operatorState", UserStatusConstants.TTP_OPERATOR_STATE_0);
			List<Map<String, Object>> userList = myBatisDaoSysDao.selectList("mybatis.mapper.activiti.outside.table.workflowoutsideuser.queryUserList", param);
			return userList;
		}catch (ServiceException se) {
			logger.error("查询授权操作员失败 " + this.getClass() + ".qryUserByRoleId");
			throw se;
		} catch (Exception e) {
			logger.error("查询授权操作员失败" + this.getClass() + ".qryUserByRoleId");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0035,"查询授权操作员失败", e);
		}
	}

	/** 
	 * 根据授权规则查询操作员列表,
	 */
	@Override
	public Map<String, Object> qryUserByAuthRule(Map<String, Object> authRuleMap,String userId)
			throws ServiceException {
		String authRnRole1 = (String)authRuleMap.get("authRnRole1");  //一级授权角色ID
		String authRnRole2 = (String)authRuleMap.get("authRnRole2");  //二级授权角色ID
		String authRnRole3 = (String)authRuleMap.get("authRnRole3");  //三级授权角色ID
		String authRnRole4 = (String)authRuleMap.get("authRnRole4");  //四级授权角色ID
		String authRnRole5 = (String)authRuleMap.get("authRnRole5");  //五级授权角色ID
		
		int roleNumber1 = ((Integer)authRuleMap.get("roleNumber1")).intValue();  //一级授权人数
		int roleNumber2 = ((Integer)authRuleMap.get("roleNumber2")).intValue();  //二级授权人数
		int roleNumber3 = ((Integer)authRuleMap.get("roleNumber3")).intValue();  //三级授权人数
		int roleNumber4 = ((Integer)authRuleMap.get("roleNumber4")).intValue();  //四级授权人数
		int roleNumber5 = ((Integer)authRuleMap.get("roleNumber5")).intValue();  //五级授权人数
		
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		List<String> userId1List = new ArrayList<String>();
		List<String> userId2List = new ArrayList<String>();
		List<String> userId3List = new ArrayList<String>();
		List<String> userId4List = new ArrayList<String>();
		List<String> userId5List = new ArrayList<String>();
		try{
			if(roleNumber1>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryUserByRoleId(authRnRole1,userId);
				userId1List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_1, userId1List);
			}
			if(roleNumber2>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryUserByRoleId(authRnRole2,userId);
				userId2List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_2, userId2List);
			}
			if(roleNumber3>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryUserByRoleId(authRnRole3,userId);
				userId3List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_3, userId3List);
			}
			if(roleNumber4>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryUserByRoleId(authRnRole4,userId);
				userId4List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_4, userId4List);
			}
			if(roleNumber5>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryUserByRoleId(authRnRole5,userId);
				userId5List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_5, userId5List);
			}
			
			List<String> authUserIdAllList = new ArrayList<String>();
			
			authUserIdAllList.addAll(userId1List);
			authUserIdAllList.addAll(userId2List);
			authUserIdAllList.addAll(userId3List);
			authUserIdAllList.addAll(userId4List);
			authUserIdAllList.addAll(userId5List);
			
			Set<String> filter = new HashSet<String>(authUserIdAllList);
			
			if (filter.size()<(roleNumber1+roleNumber2+roleNumber3+roleNumber4+roleNumber5)) {
				throw new ServiceException(ActivitiErrorCodeConstants.PPAI0005, "可参与授权的人数小于授权规则配置的人数");
			}
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryUserByAuthRule");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryUserByAuthRule");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0036,"根据授权规则查询授权操作员失败", e);
		}
		return tellerMap;
	}

	/** 
	 * @Title: qryOperatorIdByNo 
	 * @Description: 这里用一句话描述这个方法的作用
	 * @param legalId
	 * @param cstNo
	 * @param operatorNo
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public String qryOperatorIdByNo(String legalId, String cstNo,
			String operatorNo) throws ServiceException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("legalId", legalId);
			param.put("cstNo", cstNo);
			param.put("operatorNo", operatorNo);
			Map<String, Object> userList = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.outside.table.workflowoutsideuser.queryUserList", param);
			return (String) userList.get("userId");
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryUserByAuthRule");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryUserByAuthRule");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0038,"查询操作员ID失败", e);
		}
	}

	/** 
	 * 查询客户渠道信息
	 */
	@Override
	public Map<String, Object> qryCstInfoByCstNo(String legalId, String cstNo)
			throws ServiceException {
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("legalId", legalId);
			param.put("cstNo", cstNo);
			Map<String, Object> userMap = myBatisDaoSysDao.selectOne("mybatis.mapper.activiti.outside.table.workflowoutsideuser.queryCstManage", param);
			return userMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryCstInfoByCstNo");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryCstInfoByCstNo");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0042,"企业客户渠道信息查询失败", e);
		}
	}

}
