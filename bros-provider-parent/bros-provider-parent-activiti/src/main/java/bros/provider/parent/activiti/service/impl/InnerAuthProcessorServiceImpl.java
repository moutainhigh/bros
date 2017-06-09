
package bros.provider.parent.activiti.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.activiti.constants.ActivitiParamsConstants;
import bros.common.core.constants.BodyParameterDefinitionConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.activiti.authmode.service.IQryInnerAuthInfoService;
import bros.provider.parent.activiti.authmode.service.IQryInnerAuthModelService;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.relation.service.IAuthRoleRelService;
import bros.provider.parent.activiti.relation.service.IBsnInfoService;
import bros.provider.parent.activiti.service.IAuthProcessorService;
import bros.provider.parent.activiti.user.service.IQryInnerUserService;
import bros.provider.parent.activiti.util.ActivitiUtil;

/** 
 * @ClassName: InnerAuthProcessorService 
 * @Description: 内部系统授权处理器服务
 * @author weiyancheng
 * @date 2016年7月11日 下午1:48:45 
 * @version 1.0 
 */
@Component("innerAuthProcessorService")
public class InnerAuthProcessorServiceImpl implements IAuthProcessorService {
	
	private static final Logger logger = LoggerFactory.getLogger(InnerAuthProcessorServiceImpl.class);
	
	/**
	 * 查询授权配置信息服务
	 */
	@Autowired
	private IQryInnerAuthInfoService qryInnerAuthInfoService;
	/**
	 * 查询柜员信息服务
	 */
	@Autowired
	private IQryInnerUserService qryInnerUserService;
	/**
	 * 查询内部授权模型服务
	 */
	@Autowired
	private IQryInnerAuthModelService qryInnerAuthModelService;
	/**
	 * 查询授权关联角色服务
	 */
	@Autowired
	private IAuthRoleRelService authRoleRelService;
	/**
	 * 查询业务编码信息
	 */
	@Autowired
	private IBsnInfoService bsnInfoService;
	
	/**
	 * @Title: authProcess 
	 * @Description: 流程处理方法(判断是否需要授权以及流程变量处理)
	 * @param param
	 * @return true:需要授权，false:无需授权
	 * @throws ServiceException
	 */
	@Override
	public boolean authProcess(Map<String, Object> varialable,Map<String, Object> headMap,Map<String, Object> bodyMap,Map<String, Object> resultMap,Map<String, Object> activitiMap)
			throws ServiceException {
		try{
			//指令提交柜员
			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);
			varialable.put(HeadParameterDefinitionConstants.REC_TRANTELLERNO, tellerNo);
			//机构编号
			String branchNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_BRANCHID);
			varialable.put(HeadParameterDefinitionConstants.REC_BRANCHID, branchNo);
			//法人id
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			varialable.put(HeadParameterDefinitionConstants.REC_LEGALID, legalId);
			//业务编号
			String bsnCode = (String) headMap.get(HeadParameterDefinitionConstants.REC_BSNCODE);
			varialable.put(HeadParameterDefinitionConstants.REC_BSNCODE, bsnCode);
			//渠道
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			varialable.put(HeadParameterDefinitionConstants.REC_CHANNEL, channel);
			//全局流水号
			String flowNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_GLOBALSEQNO);
			varialable.put("businessKey", flowNo);
			//根据法人id、渠道编号、功能编号查询授权配置信息
			Map<String, Object> bsnAuthMap = qryInnerAuthInfoService.qryAuthInfo(legalId, channel, bsnCode);
			if(bsnAuthMap==null || bsnAuthMap.size()<1){
				throw new ServiceException(ActivitiErrorCodeConstants.PPAI0007,"该业务授权配置信息不存在");
			}
			//授权标识
			String authFlag = (String) bsnAuthMap.get("authFlag");
			resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, authFlag);
			//不需要授权
			if(null != authFlag && authFlag.equals(ActivitiParamsConstants.ACT_AUTHFLAG_NO)){
				return false;
			}
			//授权用流程id,启动流程要用
			String procDefId = (String) bsnAuthMap.get("procDefId");
			activitiMap.put("procDefId", procDefId);
			
			//根据法人、柜员号查询柜员id
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId,tellerNo);
			String userId = (String) tellerMap.get("userId");
			
			activitiMap.put("userId", userId);//启动流程要用
			varialable.put(ActivitiParamsConstants.FIELD_SUBMIT_USERID, userId);//指令提交柜员ID
			
			//根据业务编号查询业务类型编号
			Map<String, Object> bsnMap = bsnInfoService.qryBsnInfo(channel, bsnCode);
			String bsnType = (String) bsnMap.get("type");
			varialable.put(ActivitiParamsConstants.FIELD_BSNTYPE, bsnType);//业务类型
			//授权处理
			//授权模型id
			String authModelFk = (String) bsnAuthMap.get("authModelFk");
			//根据授权模型id查询授权模型
			Map<String, Object> authModelMap = qryInnerAuthModelService.qryAuthModel(authModelFk);
			if(null==authModelMap || authModelMap.size()<1){
				throw new ServiceException(ActivitiErrorCodeConstants.PPAI0008,"授权模型不存在");
			}
			//授权级别（单人，多人）
			String authLevel = (String) authModelMap.get("authLevel");
			resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHLEVEL, authLevel);
			//授权顺序
			String orderly = (String) authModelMap.get("orderly");
			varialable.put(ActivitiParamsConstants.ACT_FIELD_ORDERLY, orderly);//流程变量
			//授权类型0：即时生效，无需授权 1：互为授权  2：指定授权
			String authType = (String) authModelMap.get("authType");
			resultMap.put("authType", authType);
			//授权形式0：审核式1：临柜
			String authShap = (String) authModelMap.get("authShape");
			resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHSHAP, authShap);
			varialable.put(ActivitiParamsConstants.ACT_FIELD_AUTHSHAP, authShap);//指令提交柜员ID
			//授权方式：0：额度授权 1：强制授权 2：条件授权
			String authMode = (String) authModelMap.get("authMode");
			resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHMODE, authMode);
			//单人授权，逻辑上不用区分是临柜式还是审核式，因为单人模式的临柜式和审核式所走的流程一样，模型配置和规则配置方式也一样
			if(ActivitiParamsConstants.ACT_AUTHLEVEL_0.equals(authLevel)){//单人授权
				//区分授权类型
				if(ActivitiParamsConstants.ACT_AUTHTYPE_1.equals(authType)){//单人、互为授权
					//区分授权方式
					if(ActivitiParamsConstants.ACT_AUTHMODE_0.equals(authMode)){//额度授权
						String transAmt = (String) bodyMap.get(BodyParameterDefinitionConstants.BODY_TRANSAMT);
						if(ValidateUtil.isEmpty(transAmt)){
							throw new ServiceException(ActivitiErrorCodeConstants.PPAI0021,"无法匹配授权路径");
						}
						//根据授权模型id和金额查询授权规则
						Map<String, Object> authRuleMap = qryInnerAuthModelService.qryAuthRule(authModelFk, transAmt);
						if(null == authRuleMap || authRuleMap.size()<1){
							resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_NO);
							//没有对应此金额的授权规则配置，表示不需要授权
							return false;
						}
						//根据当前柜员userId查询出与该柜员角色相同的同机构柜员信息（查出一个存放柜员id的list)
						List<Map<String, Object>> userList = qryInnerUserService.qrySameRoleTellerListByOneTellerIdAndBranch(legalId, userId, branchNo);
						//把候选用户id信息放入流程变量
						varialable.put("authUserIdList", ActivitiUtil.switchUserIdList(userList, "userId"));
						return true;
					}else if(ActivitiParamsConstants.ACT_AUTHMODE_1.equals(authMode)){//强制授权
						//根据当前柜员userId查询出与该柜员角色相同的同机构柜员信息（查出一个存放柜员id的list)
						List<Map<String, Object>> userList = qryInnerUserService.qrySameRoleTellerListByOneTellerIdAndBranch(legalId, userId, branchNo);
						if(null==userList || userList.size()<1){
							throw new ServiceException(ActivitiErrorCodeConstants.PPAI0020,"授权人不足");
						}
						//把候选用户id信息放入流程变量
						varialable.put("authUserIdList",  ActivitiUtil.switchUserIdList(userList, "userId"));
						return true;
					}else{//条件授权
						//根据当前柜员id查出该柜员的角色id
						Map<String, Object> roleMap = qryInnerUserService.qryTellerRoleIdByTellerId(userId);
						String authRoleId = (String) roleMap.get("roleId");
						List<String> roleList = new ArrayList<String>();
						roleList.add(authRoleId);
						resultMap.put("authRoleList", roleList);
						
						return true;						
					}
				}else if(ActivitiParamsConstants.ACT_AUTHTYPE_2.equals(authType)){//单人、指定授权
					//区分授权方式
					if(ActivitiParamsConstants.ACT_AUTHMODE_0.equals(authMode)){//额度授权
						String transAmt = (String) bodyMap.get(BodyParameterDefinitionConstants.BODY_TRANSAMT);
						if(ValidateUtil.isEmpty(transAmt)){
							throw new ServiceException(ActivitiErrorCodeConstants.PPAI0021,"无法匹配授权路径");
						}
						//根据授权模型id和金额查询授权规则
						Map<String, Object> authRuleMap = qryInnerAuthModelService.qryAuthRule(authModelFk, transAmt);
						if(null == authRuleMap || authRuleMap.size()<1){
							resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_NO);
							//没有对应此金额的授权规则配置，表示不需要授权
							return false;
						}
						//角色关联id
						String authRuleRoleRelId = (String) authRuleMap.get("authRnrole1");
						//根据角色关联id查询出候选角色id
						List<Map<String, Object>> roleIdList =authRoleRelService.qryAuthRoleByRelId(authRuleRoleRelId);
						
						//根据角色列表查询出对应的本机构及上级机构柜员信息（查出一个存放柜员id的list）
						List<Map<String, Object>> userList = qryInnerUserService.qryTellerListByBranchAndRoleList(legalId, userId, branchNo, roleIdList);
						//把候选用户id信息放入流程变量
						varialable.put("authUserIdList", ActivitiUtil.switchUserIdList(userList, "userId"));
						return true;
					}else if(ActivitiParamsConstants.ACT_AUTHMODE_1.equals(authMode)){//强制授权
						//从授权模型信息中取出角色关联id
						String authModeRoleRelId = (String) authModelMap.get("authRoleRel");
						//根据角色关联id从角色关联表查询出候选角色id
						List<Map<String, Object>> roleIdList = authRoleRelService.qryAuthRoleByRelId(authModeRoleRelId);
						
						//根据角色列表查询出对应的本机构及上级机构柜员信息（查出一个存放柜员id的list）
						List<Map<String, Object>> userList = qryInnerUserService.qryTellerListByBranchAndRoleList(legalId, userId, branchNo, roleIdList);
						//把候选用户id信息放入流程变量
						varialable.put("authUserIdList", ActivitiUtil.switchUserIdList(userList, "userId"));
						return true;						
					}else{//条件授权
						//从授权模型信息中取出角色关联id
						String authModeRoleRelId = (String) authModelMap.get("authRoleRel");
						//根据角色关联id从角色关联表查询出候选角色id
						List<Map<String, Object>> roleIdList = authRoleRelService.qryAuthRoleByRelId(authModeRoleRelId);
						
						resultMap.put("authRoleList", ActivitiUtil.switchRoleIdList(roleIdList, "roleId"));
						return true;
					}
				}else{//无需授权，立即生效
					resultMap.put("authFlag", ActivitiParamsConstants.ACT_AUTHFLAG_NO);
					return false;
				}
			}else if(ActivitiParamsConstants.ACT_AUTHLEVEL_1.equals(authLevel)){//多人授权
				if(authShap.equals(ActivitiParamsConstants.ACT_AUTHSHAP_0)){//多人、审核式授权
					if(ActivitiParamsConstants.ACT_AUTHTYPE_0.equals(authType)){//立即生效
						resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_NO);
						//没有对应此金额的授权规则配置，表示不需要授权
						return false;
					}else{//需要授权
						if(ActivitiParamsConstants.ACT_AUTHMODE_0.equals(authMode)){//多人、审核式、额度授权
							String transAmt = (String) bodyMap.get(BodyParameterDefinitionConstants.BODY_TRANSAMT);
							if(ValidateUtil.isEmpty(transAmt)){
								throw new ServiceException(ActivitiErrorCodeConstants.PPAI0021,"无法匹配授权路径");
							}
							//根据授权模型id和金额查询授权规则
							Map<String, Object> authRuleMap = qryInnerAuthModelService.qryAuthRule(authModelFk, transAmt);
							if(null == authRuleMap || authRuleMap.size()<1){
								resultMap.put("authFlag", ActivitiParamsConstants.ACT_AUTHFLAG_NO);
								//没有对应此金额的授权规则配置，表示不需要授权
								return false;
							}
							//根据授权规则查询对应的柜员信息（指令提交柜员所在本级机构及其上级机构）
							Map<String, Object> usersMap = qryInnerUserService.qryAuthUserAndNumberByAuthRule(legalId, userId, branchNo, authRuleMap);
							varialable.putAll(usersMap);
							return true;
						}else if(ActivitiParamsConstants.ACT_AUTHMODE_1.equals(authMode)){//多人、审核式、强制授权
							//根据授权模型id和授权规则
							Map<String, Object> authRuleMap = qryInnerAuthModelService.qryAuthRule(authModelFk, null);
							if(null == authRuleMap || authRuleMap.size()<1){
								//未配置授权规则
								throw new ServiceException(ActivitiErrorCodeConstants.PPAI0018,"未配置授权规则");
							}
							//根据授权规则查询对应的柜员信息（指令提交柜员所在本级机构及其上级机构）
							Map<String, Object> usersMap = qryInnerUserService.qryAuthUserAndNumberByAuthRule(legalId, userId, branchNo, authRuleMap);
							varialable.putAll(usersMap);
							return true;
						}
					}
				}else{
					//多人授权只有审核式
					throw new ServiceException(ActivitiErrorCodeConstants.PPAI0016,"授权类型配置不正确");
				}
			}
			return false;
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass() + ".authProcess");
			throw se;
		}catch(Exception e){
			logger.error("Exception from " + this.getClass() + ".authProcess");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0029, "内部授权处理失败", e);
		}
	}

}
