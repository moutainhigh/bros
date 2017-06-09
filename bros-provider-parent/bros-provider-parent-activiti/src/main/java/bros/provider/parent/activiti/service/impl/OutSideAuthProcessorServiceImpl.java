package bros.provider.parent.activiti.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.activiti.constants.ActivitiParamsConstants;
import bros.common.core.constants.BodyParameterDefinitionConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.constants.UserStatusConstants;
import bros.common.core.exception.ServiceException;
import bros.common.core.util.ValidateUtil;
import bros.provider.parent.activiti.authmode.service.IQryOutSideAuthInfoService;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.relation.service.IBsnInfoService;
import bros.provider.parent.activiti.service.IAuthProcessorService;
import bros.provider.parent.activiti.user.service.IQryOutSideUserService;
import bros.provider.parent.activiti.util.ActivitiUtil;

/** 
 * @ClassName: OutSideAuthProcessorServiceImpl 
 * @Description: 外部授权指令提交处理器
 * @author weiyancheng
 * @date 2016年7月27日 下午1:47:16 
 * @version 1.0 
 */
@Component("outSideAuthProcessorService")
public class OutSideAuthProcessorServiceImpl implements IAuthProcessorService {
	
	private static final Logger logger = LoggerFactory.getLogger(InnerAuthProcessorServiceImpl.class);
	
	@Autowired
	private IQryOutSideAuthInfoService qryOutSideAuthInfoService;
	@Autowired
	private IQryOutSideUserService qryOutSideUserService;
	/**
	 * 查询业务编码信息
	 */
	@Autowired
	private IBsnInfoService bsnInfoService;

	/** 
	 *  外部授权指令提交处理
	 */
	@Override
	public boolean authProcess(Map<String, Object> varialable,
			Map<String, Object> headMap, Map<String, Object> bodyMap,
			Map<String, Object> resultMap, Map<String, Object> activitiMap)
			throws ServiceException {
		try{
			//指令提交客户号
			String cstNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_CSTNO);
			varialable.put(HeadParameterDefinitionConstants.REC_CSTNO, cstNo);
			//指令提交操作员号
			String operatorNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO);
			varialable.put(HeadParameterDefinitionConstants.REC_OPERATORNO, operatorNo);
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
			//根据渠道功能编号查询功能定义信息
			Map<String, Object> bsnMap = bsnInfoService.qryBsnInfo(channel, bsnCode);
			varialable.put(ActivitiParamsConstants.FIELD_BSNTYPE, bsnMap.get("type"));//业务类型
			//是否需要授权（0:无需授权、1:金额相关、2:金额无关、3:管理类授权）
			String requireAuth = (String) bsnMap.get("requireAuth");
			//该功能是无需授权
			if(ActivitiParamsConstants.ACT_REQUIREAUTH_0.equals(requireAuth)){
				resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_NO);
				//没有对应此金额的授权规则配置，表示不需要授权
				return false;
			}else if(ActivitiParamsConstants.ACT_REQUIREAUTH_3.equals(requireAuth)){//管理类授权
				//判断当前客户管理模式（0：单人管理、1：双人管理）
				//根据法人ID，客户号查询客户渠道信息
				Map<String, Object> cstMap = qryOutSideUserService.qryCstInfoByCstNo(legalId, cstNo);
				String manage = (String) cstMap.get("manage");
				if(!ValidateUtil.isEmpty(manage) &&UserStatusConstants.TTP_CST_MANAGE_0.equals(manage)){
					//单人管理，不需要授权
					resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_NO);
					return false;
				}else{//双人管理
					//根据指令提交操作员查询该操作员ID
					String userId = qryOutSideUserService.qryOperatorIdByNo(legalId, cstNo, operatorNo);
					//如果为双人管理，管理类交易需要授权，授权用户为该企业客户下有管理权限的操作员
					//查询该客户下有管理权限的操作员,排除指令提交操作员-----------此处待查询
					List<Map<String, Object>> userList = qryOutSideUserService.qryManageUser(legalId, cstNo,userId);
					if(null==userList || userList.size()<1){
						throw new ServiceException(ActivitiErrorCodeConstants.PPAI0037,"没有足够的管理员参与本交易授权操作");
					}
					List<String> userIdList = ActivitiUtil.switchUserIdList(userList, "userId");
					varialable.put("userIdList", userIdList);
					resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_YES);
					activitiMap.put("authType", "0");//管理类授权
					activitiMap.put("userId", userId);//指令提交柜员ID
					varialable.put(ActivitiParamsConstants.FIELD_SUBMIT_USERID, userId);
					return true;
				}
			}else{//金额相关与金额无关
				//根据法人+客户号+业务编号查询该业务的授权配置信息
				Map<String, Object> authMap = qryOutSideAuthInfoService.qryAuthInfo(legalId, cstNo, bsnCode);
				//授权模型ID
				String authModelFk = (String) authMap.get("authModelFk");
				if(ValidateUtil.isEmpty(authModelFk)){
					//不需要授权
					resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_NO);
					return false;
				}else{
					//根据授权模型编号查询授权模型
					Map<String, Object> authModelMap = qryOutSideAuthInfoService.qryAuthModel(authModelFk);
					if(null==authModelMap || authModelMap.size()<1){
						//授权模型未配置
						throw new ServiceException(ActivitiErrorCodeConstants.PPAI0040,"未配置企业授权模型");
					}else{
						String moneyType = (String) authModelMap.get("moneyType");
						String orderly = (String) authModelMap.get("orderly");
						varialable.put(ActivitiParamsConstants.ACT_FIELD_ORDERLY, orderly);
						if(!ValidateUtil.isEmpty(moneyType) && ActivitiParamsConstants.ACT_MONEYTYPE_0.equals(moneyType)){
							//无金额模式
							//根据授权模型ID查询授权规则
							Map<String, Object> authRuleMap = qryOutSideAuthInfoService.qryAuthRule(authModelFk, null);
							if(null==authRuleMap || authRuleMap.size()<1){
								//授权规则不存在
								throw new ServiceException(ActivitiErrorCodeConstants.PPAI0041,"未配置企业授权规则");
							}else{
								//根据指令提交操作员查询该操作员ID
								String userId = qryOutSideUserService.qryOperatorIdByNo(legalId, cstNo, operatorNo);
								//根据授权规则查询授权授权用户
								Map<String, Object> usersMap = qryOutSideUserService.qryUserByAuthRule(authRuleMap, userId);
								//把授权用户放入流程变量中
								varialable.putAll(usersMap);
								resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_YES);
								activitiMap.put("authType", "1");//业务类授权
								activitiMap.put("userId", userId);//指令提交柜员ID
								varialable.put(ActivitiParamsConstants.FIELD_SUBMIT_USERID, userId);
								return true;
							}
						}else{
							//有金额模式
							//交易金额
							String tranAmt = (String) bodyMap.get(BodyParameterDefinitionConstants.BODY_TRANSAMT);
							//根据交易金额+授权模型ID查询授权规则
							Map<String, Object> authRuleMap = qryOutSideAuthInfoService.qryAuthRule(authModelFk, tranAmt);
							if(null==authRuleMap || authRuleMap.size()<1){
								//授权规则不存在
								throw new ServiceException(ActivitiErrorCodeConstants.PPAI0041,"未配置企业授权规则");
							}else{
								//根据指令提交操作员查询该操作员ID
								String userId = qryOutSideUserService.qryOperatorIdByNo(legalId, cstNo, operatorNo);
								//根据授权规则查询授权授权用户
								Map<String, Object> usersMap = qryOutSideUserService.qryUserByAuthRule(authRuleMap, userId);
								//把授权用户放入流程变量中
								varialable.putAll(usersMap);
								resultMap.put(ActivitiParamsConstants.ACT_FIELD_AUTHFLAG, ActivitiParamsConstants.ACT_AUTHFLAG_YES);
								activitiMap.put("authType", "1");//业务类授权
								activitiMap.put("userId", userId);//指令提交柜员ID
								varialable.put(ActivitiParamsConstants.FIELD_SUBMIT_USERID, userId);
								return true;
							}
						}
					}
				}
			}
		}catch(ServiceException se){
			logger.error("Exception from " + this.getClass() + ".authProcess");
			throw se;
		}catch(Exception e){
			logger.error("Exception from " + this.getClass() + ".authProcess");
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0030, "外部授权处理失败", e);
		}
	}

}
