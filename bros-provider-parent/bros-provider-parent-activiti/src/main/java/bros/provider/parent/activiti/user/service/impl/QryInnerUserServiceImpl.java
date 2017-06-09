/**   
 * @Title: QryInnerUserServiceImpl.java 
 * @Package bros.provider.parent.activiti.user.service.impl 
 * @Description: 用一句话描述该文件做什么 
 * @author weiyancheng
 * @date 2016年7月12日 下午1:43:22 
 * @version 1.0   
 */
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
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.user.service.IQryInnerUserService;
import bros.provider.parent.activiti.util.ActivitiUtil;

/** 
 * @ClassName: QryInnerUserServiceImpl 
 * @Description: 查询内部用户服务实现
 * @author weiyancheng
 * @date 2016年7月12日 下午1:43:22 
 * @version 1.0 
 */
@Component("qryInnerUserService")
public class QryInnerUserServiceImpl implements IQryInnerUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(QryInnerUserServiceImpl.class);
	
	@Autowired
	private IMyBatisDaoSysDao myBatisDaoSysDao;

	/** 
	 * @Title: qryTellerIdByTellCode 
	 * @Description: 根据法人、柜员编号查询柜员id
	 * @param param
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> qryTellerIdByTellCode(String legalId,String tellerNo)
			throws ServiceException {
		try{
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			parmIn.put("legalId", legalId);
			parmIn.put("tellerCode", tellerNo);
			
			List<Map<String, Object>> tellerCodeList  =  new ArrayList<Map<String, Object>>();
			//查询柜员信息列表
			tellerCodeList = myBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.workflowinneruser.queryTellerIdByCode", parmIn);
			if(tellerCodeList==null || tellerCodeList.size()<1){
				throw new ServiceException(ActivitiErrorCodeConstants.PPAI0017,"柜员信息不存在");
			}
			//组装返回数据
			return tellerCodeList.get(0);
		}catch (ServiceException se) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".qryTellerIdByTellCode");
			throw se;
		} catch (Exception e) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".qryTellerIdByTellCode");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0001,"查询柜员信息操作失败", e);
		}
	}

	/** 
	 * @Title: qryTellerRoleIdByTellCode 
	 * @Description: 根据柜员ID查询柜员的角色id
	 * @param tellerId
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Map<String, Object> qryTellerRoleIdByTellerId(String tellerId)
			throws ServiceException {
		try{
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			parmIn.put("tellerId", tellerId);
			
			List<Map<String, Object>> tellerRoleList  =  new ArrayList<Map<String, Object>>();
			//查询柜员信息列表
			tellerRoleList = myBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.workflowinneruser.queryTellerRoleByTellerId", parmIn);
			if(tellerRoleList==null || tellerRoleList.size()<1){
				throw new ServiceException(ActivitiErrorCodeConstants.PPAI0011,"柜员角色不存在");
			}
			//组装返回数据
			return tellerRoleList.get(0);
		}catch (ServiceException se) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".qryTellerRoleIdByTellCode");
			throw se;
		} catch (Exception e) {
			logger.error("查询柜员信息失败   " + this.getClass() + ".qryTellerRoleIdByTellCode");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0001,"查询柜员信息操作失败", e);
		}
	}

	/**
	 * @Title: qrySameRoleTellerListByOneTellerIdAndBranch 
	 * @Description: 根据柜员id、法人id,机构号，查询该机构下与该柜员角色相同的柜员列表
	 * @param tellerId 柜员id
	 * @param branchNo 机构号
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<Map<String, Object>> qrySameRoleTellerListByOneTellerIdAndBranch(
			String legalId, String tellerId, String branchNo)
			throws ServiceException {
		try{
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			parmIn.put("userId", tellerId);
			parmIn.put("legalId", legalId);
			parmIn.put("branchNo", branchNo);
			
			List<Map<String, Object>> tellerList  =  new ArrayList<Map<String, Object>>();
			//查询柜员信息列表
			tellerList = myBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.workflowinneruser.querySameRoleTellerListByTellerIdAndBranchNo", parmIn);
			return tellerList;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qrySameRoleTellerListByOneTellerIdAndBranch");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from  " + this.getClass() + ".qrySameRoleTellerListByOneTellerIdAndBranch");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0001,"查询柜员信息操作失败", e);
		}
	}

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
	@Override
	public List<Map<String, Object>> qryTellerListByBranchAndRoleList(
			String legalId, String tellerId, String branchNo,
			List<Map<String, Object>> roleIdList) throws ServiceException {
		try{
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			parmIn.put("tellerCode", tellerId);
			parmIn.put("legal", legalId);
			parmIn.put("branchNo", branchNo);
			parmIn.put("roleIdList", ActivitiUtil.switchRoleIdList(roleIdList,"roleId"));
			List<String> sttList = new ArrayList<String>();
			sttList.add("2");
			sttList.add("3");
			sttList.add("4");
			parmIn.put("sttList", sttList);
			
			//查询柜员信息列表
			List<Map<String, Object>> userIdList = qryTeller(parmIn);
			
			return userIdList;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryTellerListByBranchAndRoleList");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from  " + this.getClass() + ".qryTellerListByBranchAndRoleList");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0001,"查询柜员信息操作失败", e);
		}
	}
	
	private List<Map<String, Object>> qryTellerListByBranchAndRole(String legalId, String tellerId, String branchNo,
			String roleId) throws ServiceException{
		try{
			Map<String, Object> parmIn = new HashMap<String,Object>();
			
			parmIn.put("tellerCode", tellerId);
			parmIn.put("legal", legalId);
			parmIn.put("branchNo", branchNo);
			List<String> roleIdList = new ArrayList<String>();
			roleIdList.add(roleId);
			parmIn.put("roleIdList", roleIdList);
			List<String> sttList = new ArrayList<String>();
			sttList.add("2");
			sttList.add("3");
			sttList.add("4");
			parmIn.put("sttList", sttList);
			
			//查询柜员信息列表
			List<Map<String, Object>> userIdList = qryTeller(parmIn);
			return userIdList;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryTellerListByBranchAndRoleList");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from  " + this.getClass() + ".qryTellerListByBranchAndRoleList");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0001,"查询柜员信息操作失败", e);
		}
	}

	/** 
	 * 根据授权规则取对应柜员和人数
	 */
	@Override
	public Map<String, Object> qryAuthUserAndNumberByAuthRule(String legalId,String userId,String branchNo,Map<String, Object> authRuleMap)throws ServiceException {
		
		String authRnRole1 = (String)authRuleMap.get("authRnrole1");  //一级授权角色ID
		String authRnRole2 = (String)authRuleMap.get("authRnrole2");  //二级授权角色ID
		String authRnRole3 = (String)authRuleMap.get("authRnrole3");  //三级授权角色ID
		String authRnRole4 = (String)authRuleMap.get("authRnrole4");  //四级授权角色ID
		String authRnRole5 = (String)authRuleMap.get("authRnrole5");  //五级授权角色ID
		
		int roleNumber1 = Integer.parseInt((String)authRuleMap.get("roleNumber1"));  //一级授权人数
		int roleNumber2 = Integer.parseInt((String)authRuleMap.get("roleNumber2"));  //二级授权人数
		int roleNumber3 = Integer.parseInt((String)authRuleMap.get("roleNumber3"));  //三级授权人数
		int roleNumber4 = Integer.parseInt((String)authRuleMap.get("roleNumber4"));  //四级授权人数
		int roleNumber5 = Integer.parseInt((String)authRuleMap.get("roleNumber5"));  //五级授权人数
		
		Map<String, Object> tellerMap = new HashMap<String, Object>();
		List<String> userId1List = new ArrayList<String>();
		List<String> userId2List = new ArrayList<String>();
		List<String> userId3List = new ArrayList<String>();
		List<String> userId4List = new ArrayList<String>();
		List<String> userId5List = new ArrayList<String>();
		try{
			if(roleNumber1>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryTellerListByBranchAndRole(legalId, userId, branchNo, authRnRole1);
				if(null==tellerCodeList || tellerCodeList.size()<roleNumber1){
					throw new ServiceException(ActivitiErrorCodeConstants.PPAI0020,"该业务授权人不足");
				}
				userId1List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_1, userId1List);
				tellerMap.put(ActivitiParamsConstants.ACT_ROLENUM_1, roleNumber1);
			}
			if(roleNumber2>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryTellerListByBranchAndRole(legalId, userId, branchNo, authRnRole2);
				if(null==tellerCodeList || tellerCodeList.size()<roleNumber2){
					throw new ServiceException(ActivitiErrorCodeConstants.PPAI0020,"该业务授权人不足");
				}
				userId2List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_2, userId2List);
				tellerMap.put(ActivitiParamsConstants.ACT_ROLENUM_2, roleNumber2);
			}
			if(roleNumber3>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryTellerListByBranchAndRole(legalId, userId, branchNo, authRnRole3);
				if(null==tellerCodeList || tellerCodeList.size()<roleNumber3){
					throw new ServiceException(ActivitiErrorCodeConstants.PPAI0020,"该业务授权人不足");
				}
				userId3List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_3, userId3List);
				tellerMap.put(ActivitiParamsConstants.ACT_ROLENUM_3, roleNumber3);
			}
			if(roleNumber4>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryTellerListByBranchAndRole(legalId, userId, branchNo, authRnRole4);
				if(null==tellerCodeList || tellerCodeList.size()<roleNumber4){
					throw new ServiceException(ActivitiErrorCodeConstants.PPAI0020,"该业务授权人不足");
				}
				userId4List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_4, userId4List);
				tellerMap.put(ActivitiParamsConstants.ACT_ROLENUM_4, roleNumber4);
			}
			if(roleNumber5>0){
				//查询柜员信息列表
				List<Map<String, Object>> tellerCodeList = qryTellerListByBranchAndRole(legalId, userId, branchNo, authRnRole5);
				if(null==tellerCodeList || tellerCodeList.size()<roleNumber5){
					throw new ServiceException(ActivitiErrorCodeConstants.PPAI0020,"该业务授权人不足");
				}
				userId5List = ActivitiUtil.switchUserIdList(tellerCodeList,"userId");
				//组装返回数据
				tellerMap.put(ActivitiParamsConstants.ACT_USERID_LIST_5, userId5List);
				tellerMap.put(ActivitiParamsConstants.ACT_ROLENUM_5, roleNumber5);
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
			logger.error("Exception from " + this.getClass() + ".qryAuthUserAndNumber");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".qryAuthUserAndNumber");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0001,"查询柜员信息操作失败", e);
		}
		return tellerMap;
	}
	/**
	 * @Title: qryTeller 
	 * @Description: 取授权柜员列表
	 * @param param
	 * @return
	 * @throws ServiceException
	 */
	private List<Map<String, Object>> qryTeller(Map<String, Object> param) throws ServiceException{
		try{
			//查询柜员信息列表
			List<Map<String, Object>> userIdList = myBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.teller-role-branch.queryTellerByRoleAndBranch", param);
			return userIdList;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryTellerListByBranchAndRoleList");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from  " + this.getClass() + ".qryTellerListByBranchAndRoleList");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0001,"查询柜员信息操作失败", e);
		}
	}

	/** 
	 * 根据柜员ID查询柜员信息
	 */
	@Override
	public List<Map<String, Object>> qryTellerInfoById(List<String> userIdList)
			throws ServiceException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdList", userIdList);
		try{
			//查询柜员信息列表
			List<Map<String, Object>> result = myBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.workflowinneruser.queryTellerInfoById", param);
			return result;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".qryTellerInfoById");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from  " + this.getClass() + ".qryTellerInfoById");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0001,"查询柜员信息失败", e);
		}
	}
	
	

}
