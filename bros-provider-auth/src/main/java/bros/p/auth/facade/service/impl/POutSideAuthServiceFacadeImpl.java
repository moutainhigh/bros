package bros.p.auth.facade.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.activiti.outside.service.IOutSideActivitiHistoryService;
import bros.common.core.activiti.outside.service.IOutSideActivitiRuntimeService;
import bros.common.core.activiti.outside.service.IOutSideActivitiTaskService;
import bros.common.core.annotation.Validation;
import bros.common.core.constants.ActivitiParamsConstants;
import bros.common.core.constants.BaseParamsConstants;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.constants.TradeStatusParamsConstants;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.common.core.flow.db.service.IBsnFlowCfgDaoService;
import bros.common.core.flow.jdbc.updater.service.IOrderUpdaterService;
import bros.common.core.flow.service.IFlowProcessorService;
import bros.common.core.redis.util.GetCacheDataUtil;
import bros.common.core.util.BaseUtil;
import bros.common.core.util.DataUtil;
import bros.common.core.util.DateUtil;
import bros.common.core.util.ValidateUtil;
import bros.p.auth.facade.service.IPOutSideAuthSerivceFacade;
import bros.provider.auth.constants.AuthErrorCodeConstants;
import bros.provider.auth.constants.AuthParamsConstants;
import bros.provider.parent.activiti.composite.service.IAuthShowService;
import bros.provider.parent.activiti.relation.service.IBsnInfoService;
import bros.provider.parent.activiti.service.IAuthBusinessFlowService;
import bros.provider.parent.auth.outside.composite.service.IOutSideAuthCompositeService;
import bros.provider.parent.auth.outside.operator.service.IOperatorEntitySerivce;
import bros.provider.parent.auth.outside.role.service.ITtpRoleEntitySerivce;

/**
 * 
 * @ClassName: POutSideAuthServiceFacadeImpl 
 * @Description: 对客授权任务中心对外发布服务
 * @author weiyancheng 
 * @date 2016年8月2日 上午9:29:06 
 * @version 1.0
 */
@Component("poutSideAuthServiceFacade")
public class POutSideAuthServiceFacadeImpl implements IPOutSideAuthSerivceFacade {
	
	private static final  Logger logger = LoggerFactory.getLogger(POutSideAuthServiceFacadeImpl.class);
	
	/**
	 * 对客授权任务中心组合服务
	 */
	@Autowired
	private IOutSideAuthCompositeService outSideAuthCompositeService;
	
	/**
	 * 外部授权工作流任务服务
	 */
	@Autowired
	private IOutSideActivitiTaskService outSideActivitiTaskService; 
	
	/**
	 * 对客授权工作流运行时服务
	 */
	@Autowired
	private IOutSideActivitiRuntimeService outSideActivitiRuntimeService;
	
	/**
	 * 对客授权工作流历史服务
	 */
	@Autowired
	private IOutSideActivitiHistoryService outSideActivitiHistoryService;
	
	/**
	 * 操作员实体服务
	 */
	@Autowired
	private IOperatorEntitySerivce operatorEntitySerivce;
	
	/**
	 * 角色实体服务
	 */
	@Autowired
	private ITtpRoleEntitySerivce outSideTtpRoleEntitySerivce;
	
	/**
	 * 流水号服务
	 */
	@Autowired
	private IAuthBusinessFlowService authBusinessFlowService;
	/**
	 * 查询业务编码信息
	 */
	@Autowired
	private IBsnInfoService bsnInfoService;
	/**
	 * 查询业务规则服务
	 **/
	@Autowired
	private IBsnFlowCfgDaoService bsnFlowCfgDaoService;
	/**
	 * 流水指令处理器服务
	 */
	@Autowired
	private IFlowProcessorService flowProcessorService;
	/**
	 * 授权流程图展示服务
	 */
	@Autowired
	private IAuthShowService authShowService;
	
	/**
	 * 交易类型待授权指令概览统计
	 */
	@Override
	public ResponseEntity qryTaskCenterView(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人记录唯一标识
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));//客户号
 			String bsnTypeBody = String.valueOf(bodyMap.get("bsnType"));//交易类型
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			String userNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));//操作员编号
 			if(ValidateUtil.isEmpty(userNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			
 			Map<String, Object> resultMap = new HashMap<String, Object>();
 			 
 			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   			if(null!=userInfMap && userInfMap.size()>0){
   				String userId = String.valueOf(userInfMap.get("id"));//操作员id
   	 			//待授权任务概览
   	 		    List<Map<String, Object>> waitClaimViewList = outSideAuthCompositeService.qryTaskOverViewBsnTypeList(legalId, cstNo, userId,bsnTypeBody);
   	 			
   	 		    List<Map<String, Object>> viewList = new ArrayList<Map<String,Object>>();//返回结果集
   	 			if( null!=waitClaimViewList && waitClaimViewList.size()>=0 ){
   	 				for(Map<String, Object> waitClaimMap : waitClaimViewList){
   	 				    Map<String, Object> dataMap = new HashMap<String, Object>();
   	 					String bsnType = String.valueOf(waitClaimMap.get("bsnType"));
   	 					String recordNum = String.valueOf(waitClaimMap.get("recordNum"));
   	 				    String bsnTypeName = GetCacheDataUtil.getAppParShowMsgByCache("bsndefTradeType", bsnType);
   	 				    dataMap.put("orderCount", recordNum);
   	 				    dataMap.put("bsnType", bsnType);
   	 				    dataMap.put("bsnTypeName", bsnTypeName);
   	 				    viewList.add(dataMap);
   	 				}
   	 			}
   				
   	 		    resultMap.put("outSideTaskViewList", viewList);
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNo+"]");
   			}

			return new ResponseEntity(resultMap);
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0028, "查询概览统计失败", ex);
		}
	}
	
	/**
	 * 业务编码待授权指令概览统计
	 */
	@Override
	public ResponseEntity qryTaskCenterViewByBsnType(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人记录唯一标识
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));//客户号
 			String bsnType = String.valueOf(bodyMap.get("bsnType"));//业务类型
 			String bsnCodeBody =String.valueOf(bodyMap.get("bsnCode"));//业务编码
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			String userNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));//操作员编号
 			if(ValidateUtil.isEmpty(userNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			
 			
 			Map<String, Object> resultMap = new HashMap<String, Object>();
 			 
 			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   			if(null!=userInfMap && userInfMap.size()>0){
   				String userId = String.valueOf(userInfMap.get("id"));//操作员id
   	 			//待授权任务概览
   	 		    List<Map<String, Object>> waitClaimViewList = outSideAuthCompositeService.qryTaskViewByBsnTypeList(legalId, cstNo, userId,bsnType,bsnCodeBody);
   	 			
   	 		    List<Map<String, Object>> viewList = new ArrayList<Map<String,Object>>();//返回结果集
   	 			if( null!=waitClaimViewList && waitClaimViewList.size()>=0 ){
   	 				for(Map<String, Object> waitClaimMap : waitClaimViewList){
   	 				    Map<String, Object> dataMap = new HashMap<String, Object>();
   	 					String bsnCode = String.valueOf(waitClaimMap.get("bsnCode"));
   	 					String recordNum = String.valueOf(waitClaimMap.get("recordNum"));
	   	 				String bsnName = "";
	 					Map<String,Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCode);
	 					if(bsnMap != null && bsnMap.size()>0){
	 						bsnName = String.valueOf(bsnMap.get("bsnName")==null?"":bsnMap.get("bsnName"));
	 					}
   	 				    dataMap.put("orderCount", recordNum);
   	 				    dataMap.put("bsnCode", bsnCode);
   	 				    dataMap.put("bsnName", bsnName);
   	 				    viewList.add(dataMap);
   	 				}
   	 			}
   				
   	 		    resultMap.put("outSideTaskViewList", viewList);
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNo+"]");
   			}

			return new ResponseEntity(resultMap);
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0028, "查询概览统计失败", ex);
		}
	}
	
	/**
	 * 查询待授权指令列表
	 */
	@Validation(value="p0000612")
	@Override
	public ResponseEntity qryAuthQueueList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try {
 			//渠道编号
 			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
 			//法人记录唯一标识
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
 			//客户号
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			//操作员编号
 			String userNoBody = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));
 			if(ValidateUtil.isEmpty(userNoBody)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			
 			//业务类型编号
 			String bsnType = String.valueOf(bodyMap.get("bsnType"));
 			//业务编码
 			String bsnCode = String.valueOf(bodyMap.get("bsnCode"));
 			
 			int pageNo = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")))-1;
 			String pageSizeStr = String.valueOf(bodyMap.get("pageSize")==null?BaseParamsConstants.SYS_DEFAULT_PAGESIZE:bodyMap.get("pageSize"));
 			int pageSize = Integer.parseInt(pageSizeStr);
 			
 			Map<String, Object> resultMap = new HashMap<String, Object>();
 			
 			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNoBody);
   			if(null!=userInfMap && userInfMap.size()>0){
   				String userId = (String)userInfMap.get("id");
   				List<Map<String,Object>> authQueueList = new ArrayList<Map<String,Object>>();
   	 			
   	 			//调用组合服务查询待授权任务列表
   	 			List<Map<String,Object>> authQueueResultList = outSideAuthCompositeService.qryAuthQueueList(legalId, cstNo, userId, bsnType, bsnCode,pageNo, pageSize);
   				if(authQueueResultList != null && authQueueResultList.size()>0){
   					for(Map<String,Object> map : authQueueResultList){
   						Map<String,Object> task = new HashMap<String, Object>();
   						
   						//根据任务ID查询流程变量
   						String taskId = String.valueOf(map.get("id"));
   						List<String> variableNames = new ArrayList<String>();
   						variableNames.add("businessKey");//指令流水号
   						variableNames.add("bsnCode");//业务编码
   						variableNames.add("bsnType");
   						Map<String,Object> varsMap = outSideActivitiTaskService.getVariableInstanceValueByTaskId(taskId, variableNames);
   						
   						String bsnCodeVar = String.valueOf(varsMap.get("bsnCode"));
   						String businessKey = String.valueOf(varsMap.get("businessKey"));
   						String bsnTypeVar = String.valueOf(varsMap.get("bsnType"));
   						String bsnTypeName = GetCacheDataUtil.getAppParShowMsgByCache("bsndefTradeType", bsnTypeVar);
   						String bsnName = "";
   						String canBatch = "";
   						Map<String,Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCodeVar);
   						if(bsnMap != null && bsnMap.size()>0){
   							bsnName = String.valueOf(bsnMap.get("bsnName")==null?"":bsnMap.get("bsnName"));
   							canBatch = String.valueOf(bsnMap.get("canBatch")==null?"0":bsnMap.get("canBatch"));
   						}
   						
   						task.put("taskId", taskId);//任务id
   						task.put("businessKey", businessKey);//指令流水号
   						task.put("bsnCode", bsnCodeVar);//业务编码
   						task.put("bsnType", bsnTypeVar);//交易类型
   						task.put("bsnTypeName", bsnTypeName);//交易类型名称
   						task.put("bsnName", bsnName);//业务名称
   						task.put("canBatch", canBatch);//是否可以批量授权
   						
   						Map<String, Object> detailMap = outSideAuthCompositeService.queryMainAndDetailFlowByGblflowSeq(businessKey, bsnCodeVar, channel);
   						String userNo = String.valueOf(detailMap.get("tellerNo"));
   						String userName = "";//录入操作员名称
   						task.put("submitUserId", userNo);        //录入操作员
   						String tranTime = "";
   						String tranDate = "";
   						String transStartTime = String.valueOf(detailMap.get("transStartTime"));//制单时间
   						if(!ValidateUtil.isEmpty(transStartTime)){
   							tranDate = transStartTime.substring(0,8);
   							tranTime = transStartTime.substring(8);
   						}
   						task.put("createTime", tranTime);                //制单时间
   						task.put("createDate", tranDate);                //制单日期
   						task.put("transtt", detailMap.get("transtt"));                 //交易状态
   						
   						Map<String, Object> userInfoMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   						if(userInfoMap != null && userInfoMap.size()>0){
   							userName = String.valueOf(userInfoMap.get("userName"));
   						}
   					
   						task.put("submitUserName", userName);
   						
   						authQueueList.add(task);
   					}
   				}
   	 			
   				//总记录数
   				long totalNum = outSideActivitiTaskService.queryAuthQueueTaskTotalNum(legalId, cstNo, userId, bsnType,bsnCode);
   				//总页数
   				int totalPage = DataUtil.getTotalPage((int)totalNum, pageSize);
   				
   				resultMap.put("outSideTaskQueueList", authQueueList);
   	            resultMap.put("totalNum", String.valueOf(totalNum));
   	            resultMap.put("totalPage", String.valueOf(totalPage));
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNoBody+"]");
   			}

			return new ResponseEntity(resultMap);
		}catch(ServiceException e) {
			throw e;
		}catch(Exception ex) {
			throw new ServiceException(AuthErrorCodeConstants.PAUH0000, "查询待授权任务失败", ex);
		}
	}
	
	/**
	 * 签收任务退回（暂时未使用）
	 */
	@Validation(value="p0000613")
	@Override
	public ResponseEntity unClaimTaskJob(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			//法人记录唯一标识
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
 			//客户号
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			//操作员编号
 			String userNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));
 			if(ValidateUtil.isEmpty(userNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			//任务ID
 			String taskId = String.valueOf(bodyMap.get("taskId"));
 			
 			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   			if(null!=userInfMap && userInfMap.size()>0){
   				String userId = String.valueOf(userInfMap.get("id"));
   			    //判断当前操作员是否已经签收当前任务
				boolean isClaimTask = outSideActivitiTaskService.checkIsClaimTask(userId, taskId);
				if(isClaimTask){
					outSideActivitiTaskService.unClaimTask(userId, taskId);
				}else{
					throw new ServiceException(AuthErrorCodeConstants.PAUH0019, "操作员未成功签收任务");
				}
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNo+"]");
   			}
 			
   			return new ResponseEntity();
		}catch(ServiceException e) {
			throw e;
		}catch(Exception ex) {
			throw new ServiceException(AuthErrorCodeConstants.PAUH0000, "退回任务失败", ex);
		}	
	}
	
	/**
	 *  单笔授权（审核式、管理授权）
	 */
	@Validation(value="p0000614")
	@Override
	public ResponseEntity singleCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
 			//法人记录唯一标识
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
 			//客户号
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			//操作员编号
 			String userNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));
 			if(ValidateUtil.isEmpty(userNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			
 			String taskId = String.valueOf(bodyMap.get("taskId"));                         //任务ID		
   			String isPass = String.valueOf(bodyMap.get("isPass"));                         //授权结果（0：拒绝；1：通过）
   			String authOpinion = String.valueOf(bodyMap.get("authOpinion"));               //授权意见
   			String batchNo = "";//批次编号
   			
   			Map<String, Object> resultMap = new HashMap<String, Object>();
   			
			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   			if(null!=userInfMap){
   				String userId = String.valueOf(userInfMap.get("id"));//当前操作员的id
   			    //根据法人记录唯一标识+客户编号+操作员ID+任务ID查询待处理的任务
   				Map<String, Object> taskMap = outSideAuthCompositeService.queryTaskByTaskIdAndUserId(legalId, cstNo, userId, taskId);
   				if(null==taskMap || taskMap.size()==0){
   					throw new ServiceException(AuthErrorCodeConstants.PAUH0017, "操作员["+userNo+"]无授权权限");
   				}
   	   			
   				//获取流程变量
   	   			List<String> variableNames = new ArrayList<String>();
   	   			variableNames.add("businessKey");   //30位全局流水号
   	   			variableNames.add("bsnCode");   //业务编码
   	   			variableNames.add("channel");   //渠道标识
   	   			Map<String, Object> varsMap = outSideActivitiTaskService.getVariableInstanceValueByTaskId(taskId, variableNames);
   	   			String businessKey = (String)varsMap.get("businessKey");

   	   			//判断当前操作员是否参与过该授权流程
   	   			List<Map<String, Object>> hisTaskList = outSideAuthCompositeService.queryFinishedHisTaskByBusinessKey(legalId, cstNo, userId, businessKey);
   	   			if(null!=hisTaskList && hisTaskList.size()>0){
   	   		        logger.error("操作员["+userNo+"]已参与过业务流水号[" + businessKey + "]流程的授权处理...");
   	    		    throw new ServiceException(AuthErrorCodeConstants.PAUH0018, "操作员已参与过授权处理，不能重复授权");
   	    		}
   	   			
   	   	        //判断当前操作柜员是否已经签收当前任务   如果没有签收，就签收该任务
				boolean isClaimTask = outSideActivitiTaskService.checkIsClaimTask(userId, taskId);
				if(!isClaimTask){
					//是否允许签收标识
					boolean claimFlag = outSideActivitiTaskService.checkCanClaimTask(userId, taskId);
					
					//领取任务
					if(claimFlag){
						//备注
						String message = "操作员[" + userNo + "]签收任务";
						isClaimTask = true;
						outSideActivitiTaskService.claimTask(userId, taskId, message);
					}else{
						logger.error("操作员[" + userNo + "]无授权权限");
						throw new ServiceException(AuthErrorCodeConstants.PAUH0017, "操作员["+userNo+"]无授权权限");
					}
				}
   	   			
			
				if(!isClaimTask){
					logger.error("操作员[" + userNo +"]未签收任务[" + taskId + "]");
					throw new ServiceException(AuthErrorCodeConstants.PAUH0019, "操作员["+userNo+"]未成功签收任务[" + taskId + "]");
				}
				
				//默认为拒绝
   	   			boolean isAgree = false;
   	   			String authOpinionTemp = "授权拒绝";
   	   			if(!ValidateUtil.isEmpty(isPass) && isPass.equals(AuthParamsConstants.IS_PASS_1)){    //通过
   	   				isAgree = true;
   	   				authOpinionTemp = "授权通过";
   	   			}
   	   			if(ValidateUtil.isEmpty(authOpinion)){
   	   				authOpinion = authOpinionTemp;
   	   			}

				String orderState = "";//授权交易指令状态
   	   			try{
   	   		        //完成任务并保存审核意见
   	   	   		    outSideActivitiTaskService.completeTask(userId, taskId, isAgree, authOpinion);
   	   	   	        orderState = TradeStatusParamsConstants.TRADE_STATE_90;
   	   			}catch(Exception e){
   	   				logger.info("任务完成失败",e);
   	   				orderState = TradeStatusParamsConstants.TRADE_STATE_91;
   	   			}finally{
		   	   			String tradeStt = "";
		   	   			String refuseReason = "";//交易拒绝，才进行更新操作
		   	   			if(orderState.equals(TradeStatusParamsConstants.TRADE_STATE_90)){//交易成功
			   	   			if(!isAgree){//授权拒绝   交易成功
								tradeStt = TradeStatusParamsConstants.TRADE_STATE_11;
								refuseReason = authOpinion;
							}
			   	   			batchNo = BaseUtil.createUUID();
		   	   			}
						
						String bsnCode = String.valueOf(varsMap.get(HeadParameterDefinitionConstants.REC_BSNCODE));
						String channel = String.valueOf(varsMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));
				
						Map<String,Object> tradeStateMap = authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey, batchNo,tradeStt,refuseReason,channel);
						if(tradeStateMap != null && tradeStateMap.size()>0){
							resultMap.put(TradeStatusParamsConstants.FIELD_TRADE_STATE, String.valueOf(tradeStateMap.get("transtt")));
							resultMap.put("bsnCode", bsnCode);//业务编码
							String submitUserId = String.valueOf(tradeStateMap.get("tellerNo"));
							String submitUserName = "";
							resultMap.put("submitUserId", submitUserId);//录入操作员编号
							Map<String,Object> userInfoMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, submitUserId);
							if(userInfoMap != null && userInfoMap.size()>0){
								submitUserName = String.valueOf(userInfoMap.get("userName"));
							}
							resultMap.put("submitUserName", submitUserName);//录入操作员名称
							resultMap.put("createTime", String.valueOf(tradeStateMap.get("transStartTime")));//制单时间
							//根据渠道功能编号查询功能定义信息
							Map<String, Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCode);
				 			String bsnName = String.valueOf(bsnMap.get("bsnName"));
				 			resultMap.put("bsnName", bsnName);//制单时间
						}
						
   	   					resultMap.put(TradeStatusParamsConstants.FIELD_ORDER_STATE, orderState);
   	   					resultMap.put(TradeStatusParamsConstants.FIELD_AUTH_RESULT, isPass);
   	   					resultMap.put("businessKey", businessKey);
   	   					resultMap.put("taskId", taskId);
   	   					resultMap.put("batchNo", batchNo);
   	   			}
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNo+"]");
   			}

   		    return new ResponseEntity(resultMap);
   		}catch(ServiceException e){
   			throw e;
   		}catch(Exception ex){
   			throw new ServiceException(AuthErrorCodeConstants.PAUH0000, "完成任务失败", ex);
   		}	
	}
	
	/**
	 * 批量授权
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000615")
	@Override
	public ResponseEntity batchCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> taskAuthResultList = new ArrayList<Map<String, Object>>();//授权结果列表
		try{
			//法人记录唯一标识
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
 			//客户号
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			//操作员编号
 			String userNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));
 			if(ValidateUtil.isEmpty(userNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			List<Map<String,Object>> taskAuthList = (List<Map<String, Object>>) bodyMap.get("taskAuthList");//授权任务id列表
   			String isPass = String.valueOf(bodyMap.get("isPass"));                         //授权结果（0：拒绝；1：通过）
   			String authOpinion = String.valueOf(bodyMap.get("authOpinion"));               //授权意见
   			String batchNo = BaseUtil.createUUID();//批次编号
   			
   			int authSuccess = 0;//
   			int authFailure = 0;//
			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   			if(null!=userInfMap){
   				String userId = String.valueOf(userInfMap.get("id"));//当前操作员的id
   				//批量指令处理
   				for(Map<String, Object> taskAuthMap : taskAuthList){
   					String businessKey = "";
   		 			String channel = "";
   		 			String bsnCode = "";
   		 			String orderState = "";
   		 			
   		 			Map<String, Object> resultMap = new HashMap<String, Object>();//当前记录返回结果集
   					//任务ID
   					String taskId = (String)taskAuthMap.get("taskId");
   					String orderMsg = "";
   					try{
   						List<String> variableNames = new ArrayList<String>();
   						variableNames.add(ActivitiParamsConstants.FIELD_SUBMIT_USERID);  //任务提交人
   						variableNames.add("businessKey");  //业务流水号
   						variableNames.add(HeadParameterDefinitionConstants.REC_BSNCODE);  //业务编号
   						variableNames.add(HeadParameterDefinitionConstants.REC_CHANNEL);  //渠道
   						Map<String, Object> varsMap = outSideActivitiTaskService.getVariableInstanceValueByTaskId(taskId, variableNames);
   						
   						businessKey = String.valueOf(varsMap.get("businessKey")); //指令流水号
   						channel = String.valueOf(varsMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));//渠道
   						bsnCode = String.valueOf(varsMap.get(HeadParameterDefinitionConstants.REC_BSNCODE));//bsnCode;
   						
   						//校验授权柜员是否是提交柜员
   						String submitUserId = (String)varsMap.get(ActivitiParamsConstants.FIELD_SUBMIT_USERID);
   						if(userId.equals(submitUserId)){
   							logger.error("提交指令操作员["+userId+"]无权限对任务[" + taskId + "]进行授权");
   							throw new ServiceException(AuthErrorCodeConstants.PAUH0041, "授权操作员不能与指令提交操作员是同一人");
   						}
   						
   					    //判断当前操作柜员是否已经签收当前任务   如果没有签收，就签收该任务
   						boolean isClaimTask = outSideActivitiTaskService.checkIsClaimTask(userId, taskId);
   						if(!isClaimTask){
   							//是否允许签收标识
   							boolean claimFlag = outSideActivitiTaskService.checkCanClaimTask(userId, taskId);
   							
   							//领取任务
   							if(claimFlag){
   								//备注
   								String message = "操作员[" + userNo + "]签收任务";
   								isClaimTask = true;
   								outSideActivitiTaskService.claimTask(userId, taskId, message);
   							}else{
   								logger.error("操作员[" + userNo + "]无授权权限");
   								throw new ServiceException(AuthErrorCodeConstants.PAUH0017, "操作员["+userNo+"]无授权权限");
   							}
   						}
   		   	   			
   					
   						if(!isClaimTask){
   							logger.error("操作员[" + userNo +"]未签收任务[" + taskId + "]");
   							throw new ServiceException(AuthErrorCodeConstants.PAUH0019, "操作员["+userNo+"]未成功签收任务[" + taskId + "]");
   						}
   						
   						//默认为拒绝
   		   	   			boolean isAgree = false;
   		   	   			String authOpinionTemp = "授权拒绝";
   		   	   			if(!ValidateUtil.isEmpty(isPass) && isPass.equals(AuthParamsConstants.IS_PASS_1)){    //通过
   		   	   				isAgree = true;
   		   	   				authOpinionTemp = "授权通过";
   		   	   			}
   		   	   			if(ValidateUtil.isEmpty(authOpinion)){
   		   	   				authOpinion = authOpinionTemp;
   		   	   			}
   		   	   			
   		   	   	        //1-等待授权
   						try{
   							//完成任务并保存审核意见
   							outSideActivitiTaskService.completeTask(userId, taskId, isAgree, authOpinion);
   							//90-交易成功
   							orderState = TradeStatusParamsConstants.TRADE_STATE_90;
   							orderMsg = "授权成功";
   							authSuccess++;
   						}catch(Exception e){
   							logger.info("任务完成失败",e);
   							//99-交易失败
   							orderState = TradeStatusParamsConstants.TRADE_STATE_91;
   							orderMsg = "授权失败";
   							authFailure++;
   						}
   					}catch(ServiceException se){
   						logger.info("任务完成失败",se);
   						orderMsg = se.getErrorMsg();
   						orderState = TradeStatusParamsConstants.TRADE_STATE_91;
   						authFailure++;
   					}catch(Exception e){//授权交易失败
   						logger.info("任务完成失败",e);
   						orderState = TradeStatusParamsConstants.TRADE_STATE_91;
   						orderMsg = "授权失败";
   						authFailure++;
   					}finally{
   						String batchNoTemp = "";
   						String tradeStt = "";//交易状态
   						if(orderState.equals(TradeStatusParamsConstants.TRADE_STATE_90)){//交易成功
   							batchNoTemp = batchNo;
   						}
   						if(!ValidateUtil.isEmpty(isPass) && isPass.equals(ActivitiParamsConstants.ACT_ISPASS_0)){//授权拒绝
   							tradeStt = TradeStatusParamsConstants.TRADE_STATE_11;
   						}
   						
   						try{
   							Map<String,Object> tradeStateMap = authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey,batchNoTemp,tradeStt,authOpinion,channel);
   							if(tradeStateMap != null && tradeStateMap.size()>0){
   								resultMap.put(TradeStatusParamsConstants.FIELD_TRADE_STATE, String.valueOf(tradeStateMap.get("transtt")));//交易状态
   								resultMap.put("bsnCode", bsnCode);//业务编码
   								String submitUserId = String.valueOf(tradeStateMap.get("tellerNo"));
   								String submitUserName = "";
   								resultMap.put("submitUserId", submitUserId);//录入操作员编号
   								resultMap.put("createTime", String.valueOf(tradeStateMap.get("tranTime")));//制单时间
   								
   								Map<String,Object> userInfoMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, submitUserId);
   								if(userInfoMap != null && userInfoMap.size()>0){
   									submitUserName = String.valueOf(userInfoMap.get("userName"));
   								}
   								resultMap.put("submitUserName", submitUserName);//录入操作员名称
   								//根据渠道功能编号查询功能定义信息
   								Map<String, Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCode);
   					 			String bsnName = String.valueOf(bsnMap.get("bsnName"));
   					 			resultMap.put("bsnName", bsnName);//制单时间
   							}
   						}catch(ServiceException e){
   							logger.error("根据指令流水号[" + businessKey +"]更新、获取交易信息失败",e);
   						}
   							
   						resultMap.put(TradeStatusParamsConstants.FIELD_ORDER_STATE, orderState);
   						resultMap.put(TradeStatusParamsConstants.FIELD_AUTH_RESULT, isPass);
   						resultMap.put("taskId", taskId);
   						resultMap.put("businessKey", businessKey);
   						resultMap.put("batchNo", batchNoTemp);
   						resultMap.put("orderMsg", orderMsg);
   						taskAuthResultList.add(resultMap);
   					}
   				}
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNo+"]");
   			}
			
			
   			result.put("batchNo", batchNo);
   			result.put("authFailure", authFailure);
   			result.put("authSuccess", authSuccess);
   			result.put("taskAuthResultList", taskAuthResultList);
			return new ResponseEntity(result);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0009, "对指令进行授权操作失败",e);
		}
	}
	
	/**
	 * 查询指令详情列表（用于凭证打印））
	 */
	@Validation(value="p0000616")
	@Override
	public ResponseEntity qryOrderDetailListByBatchNo(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		try{
			String bsnCode = (String) bodyMap.get("funcCode");
			String batchNo = (String) bodyMap.get("batchNo");
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			//根据业务编号查询出业务对应的指令提供器和sql提供器
			Map<String, Object> cfgMap = bsnFlowCfgDaoService.queryBsnFlowCfg(bsnCode,channel);
			String commdMessage = (String) cfgMap.get("updaterBeanId");
			String sqlAllPath = (String) cfgMap.get("providerBeanId");
			IOrderUpdaterService service = flowProcessorService.createProcessor(commdMessage, sqlAllPath);
			List<Map<String, Object>> flowSeqList = service.queryGblFlowSeqByBatchNo(batchNo);
			if(null!=flowSeqList && flowSeqList.size()>0){
				for(Map<String, Object> map:flowSeqList){
					String gblflowSeq = (String) map.get("gblflowSeq");
					//根据流水号查询指令详情
					Map<String, Object> detailMap = service.queryMainAndDetailFlowForOne(gblflowSeq);
					resultList.add(detailMap);
				}
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("resultList", resultList);
			return new ResponseEntity(resultMap);
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0012, "查询指令批量详情列表失败",ex);
		}
	}
	
	/**
	 * 根据业务流水号（businessKey）查询审批意见历史信息
	 */
	@Validation(value="p0000617")
	@Override
	public ResponseEntity qryCommentListByTaskId(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
 			//法人记录唯一标识
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
 			//客户号
 			String cstNo = (String) (headMap.get(HeadParameterDefinitionConstants.REC_CSTNO)==null?"":headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			//30位全局流水号
    		String businessKey = String.valueOf(bodyMap.get("businessKey"));

    		//根据业务流水号查询历史流程实例
    		Map<String, Object> procIntMap = outSideAuthCompositeService.queryHistoricProcessInstanceByBusinessKey(businessKey);
    		
    		List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
    		if( null!=procIntMap && procIntMap.size()>0 ){
    			String processInstanceId = String.valueOf(procIntMap.get("id"));
        		
        		//根据流程实例ID查询审批意见历史信息
        		List<Map<String, Object>> resultList = outSideAuthCompositeService.queryCommentListByProcessInstanceId(processInstanceId);
        		for(Map<String, Object> comment:resultList){
        			String authContent = String.valueOf(comment.get("fullMessage"));//柜员[" + userId + "]签收任务
        			//如果是签收任务意见的化，就过滤掉
        			if(!ValidateUtil.isEmpty(authContent) && 
        					authContent.startsWith("操作员[") && 
        					authContent.endsWith("]签收任务")){
        				continue;
        			}
        			
        			Map<String, Object> comMap = new HashMap<String, Object>();
        			Date dealTime = (Date)comment.get("time");
        			comMap.put("dealTime", DateUtil.formatDate(dealTime, DateUtil.DEFAULT_TIME_FORMAT_DB));
        			String userId = String.valueOf(comment.get("userId"));
        			String userName = "";
        			String userNo = "";
        			comMap.put("authResult", String.valueOf(comment.get("authResult")));
        			comMap.put("authContent", authContent);
        			
        			Map<String, Object> userInfoMap = operatorEntitySerivce.queryTtpUserIdByUserIdCondition(legalId, cstNo, userId);
					if(userInfoMap != null && userInfoMap.size()>0){
						userName = String.valueOf(userInfoMap.get("userName"));
						userNo = String.valueOf(userInfoMap.get("userNo"));
					}
					comMap.put("operatorId", userId);
					comMap.put("operatorNo", userNo);
					comMap.put("operatorName", userName);
        			commentList.add(comMap);
        		}
        		
    		}
    		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
	        resultMap.put("outSideCommentList", commentList);
            
			return new ResponseEntity(resultMap);
    	}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0000, "查询授权历史明细列表失败", ex);
		}
	}
	
	/**
	 * 根据业务流水号（指令流水号）+业务编号查询授权详情页面展示数据
	 */
	@Validation(value="p0000618")
	@Override
	public ResponseEntity qryAuthDetailPageDataByBusinessKey(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
    		//渠道编号
 			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
 			//30位全局流水号
    		String businessKey = (String)bodyMap.get("businessKey");
    		//业务编号
    		String funcCode = (String)bodyMap.get("funcCode");
    		
    		Map<String, Object> detailMap = outSideAuthCompositeService.queryMainAndDetailFlowByGblflowSeq(businessKey, funcCode, channel);
    		
    		return new ResponseEntity(detailMap);
    	}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0029, "获取授权详情模板数据失败",ex);
		}
	}
	
	/**
	 * 根据法人记录唯一标识+客户编号+任务提交人操作员ID查询可撤销流程实例列表（只能是提交柜员，查询授权流程没有走完的））
	 */
	@Validation(value="p0000619")
	@Override
	public ResponseEntity queryCancelProcessInstanceList(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		try{
    		//渠道编号
 			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
    		//法人记录唯一标识
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
 			//客户号
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			//操作员编号
 			String userNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));
 			if(ValidateUtil.isEmpty(userNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			
 			//当前页数
 			int pageNo = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")))-1;
 			//每页显示记录数
 			String pageSizeStr = String.valueOf(bodyMap.get("pageSize")==null?BaseParamsConstants.SYS_DEFAULT_PAGESIZE:bodyMap.get("pageSize"));
 			int pageSize = Integer.parseInt(pageSizeStr);
 			
 			Map<String, Object> resultMap = new HashMap<String, Object>();
 			
 			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfoMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   			if(null!=userInfoMap && userInfoMap.size()>0){
   				String userId = String.valueOf(userInfoMap.get("id"));
   				String userName = String.valueOf(userInfoMap.get("userName"));
   			    //查询可撤销流程实例列表
   	    		List<Map<String, Object>> processList = outSideAuthCompositeService.qryProcessInstanceListBySubmitUserId(legalId, cstNo, userId, pageNo*pageSize, pageSize);
   	    		
   	    		List<Map<String, Object>> processInstanceList = new ArrayList<Map<String, Object>>();
   	    		if(processList != null && processList.size()>0){
   	    			for(Map<String, Object> map:processList){
   	   	    			String processInstanceId = String.valueOf(map.get("processInstanceId"));//流程实例id
   	   					List<String> variableNames = new ArrayList<String>();
   	   					variableNames.add("businessKey");                           //业务流水号
   	   					variableNames.add("bsnCode");                               //业务编号
   	   					Map<String, Object> varsMap = outSideActivitiTaskService.getVariableInstanceValue(processInstanceId, variableNames);
   	   					
   	   					Map<String, Object> hisProcessInstance = new HashMap<String, Object>();
   	   					hisProcessInstance.put("processInstanceId", processInstanceId);              //流程实例ID
   	   					String businessKey = String.valueOf(varsMap.get("businessKey"));
   	   					hisProcessInstance.put("businessKey", businessKey);           //业务流水号
   	   					   	   					
   	   					String bsnCode = String.valueOf(varsMap.get("bsnCode"));
   	   					String bsnName = "";
   	 					Map<String,Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCode);
   	 					if(bsnMap != null && bsnMap.size()>0){
   	 						bsnName = String.valueOf(bsnMap.get("bsnName")==null?"":bsnMap.get("bsnName"));
   	 					}
   	 					hisProcessInstance.put("bsnCode", bsnCode);                   //业务编号
   	 					hisProcessInstance.put("bsnName", bsnName);                   //业务名称
   	 					hisProcessInstance.put("submitUserId", userNo);               //操作员编码
   	 					hisProcessInstance.put("submitUserName", userName);			  //操作员名称
   	 					
   	 				    Map<String,Object> tradeStateMap = authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey, "","","",channel);
   	 				    if(tradeStateMap != null && tradeStateMap.size()>0){
   	 				    	hisProcessInstance.put("createTime", String.valueOf(tradeStateMap.get("transStartTime")));//制单时间	
   	 				    }
   	   					processInstanceList.add(hisProcessInstance);
   	   	    		}
   	    		}
   	    		
				//查可撤销流程实例总记录数
				long totalNum = outSideActivitiRuntimeService.qryProcessInstanceTotalNum(legalId, cstNo, userId);
				//总页数
   				int totalPage = DataUtil.getTotalPage((int)totalNum, pageSize);
   				
   				resultMap.put("outSideProcessInstanceList", processInstanceList);
   	            resultMap.put("totalNum", totalNum);
   	            resultMap.put("totalPage", totalPage);
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNo+"]");
   			}
   			
 			return new ResponseEntity(resultMap);
    	}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0021, "查询可撤销流程列表失败", ex);
		}
	}
	
	/**
	 * 根据法人记录唯一标识+客户编号+任务提交人操作员ID+30位全局流水号撤销授权申请
	 */
	@Validation(value="p0000620")
	@Override
	public ResponseEntity cancelProcessInstance(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
    		//渠道编号
 			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
    		//法人记录唯一标识
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
 			//客户号
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			//操作员编号
 			String userNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));
 			if(ValidateUtil.isEmpty(userNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			//30位全局流水号
 			String businessKey = String.valueOf(bodyMap.get("businessKey"));
 			//交易撤销原因
 			String revokeReason = String.valueOf(bodyMap.get("revokeReason"));
 			if(ValidateUtil.isEmpty(revokeReason)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0038,"revokeReason不能为空");
 			}
    		
 			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   			if(null!=userInfMap && userInfMap.size()>0){
   				String userId = String.valueOf(userInfMap.get("id"));
   				
   				
   				
   				Map<String, Object> piMap = outSideAuthCompositeService.qryProcessInstanceByBusinessKey(legalId, cstNo, userId, businessKey);
   	 			if(null==piMap || piMap.size()==0){
   	 				logger.error("业务流水号["+businessKey+"]关联的流程实例不存在或状态不正常");
   	 				throw new ServiceException(AuthErrorCodeConstants.PAUH0022, "流程实例不存在或状态不正常");
   	 			}
   	 			
   	 			//查询流程变量
   	 			String processInstanceId = String.valueOf(piMap.get("processInstanceId"));//流程实例id
   	 			List<String> variableNames = new ArrayList<String>();
   	 		    variableNames.add("bsnCode");                               //业务编号
   	 		    Map<String,Object> varsMap = outSideActivitiTaskService.getVariableInstanceValue(processInstanceId, variableNames);
   	 			
   	 		    //查询流程实例对应的运行时任务
   	 			List<Map<String, Object>> taskList = outSideAuthCompositeService.getTaskListByBusinessKeyAndSubmitUserId(legalId, cstNo, userId, businessKey);
   	 			if(null==taskList || taskList.size()==0){
   	 				logger.error("业务流水号["+businessKey+"]关联的流程实例不存在或尚未启动");
   	 				throw new ServiceException(AuthErrorCodeConstants.PAUH0023, "流程实例不存在或尚未启动");
   	 			}
   	 			
	   	 		for(Map<String, Object> task:taskList){
	 				String assignee = (String)task.get("assignee");
	 				//流程实例下的任务已被签收，不允许撤销流程
	 				if(!ValidateUtil.isEmpty(assignee)){
	 					logger.error("业务流水号["+businessKey+"]关联的流程实例正在处理中，不允许撤销");
	 					throw new ServiceException(AuthErrorCodeConstants.PAUH0024, "流程实例正在处理中，不允许撤销");
	 				}
	 			}
	   	 		
	   	 	    //查询流程实例对应的历史任务列表
	 			List<Map<String, Object>> hisTaskList = 
	 					outSideAuthCompositeService.qryHistoricTaskInstanceList(legalId, cstNo, userId, businessKey);
	 			if(null!=hisTaskList){
	 				for(Map<String, Object> hisTask:hisTaskList){
	 	 				String assignee = String.valueOf(hisTask.get("assignee"));                //任务办理人
	 	 				String deleteReason = String.valueOf(hisTask.get("deleteReason"));        //删除原因
	 	 				//流程实例下的任务已被人为签收或已被正常处理，不允许撤销流程
	 	 				if( !ValidateUtil.isEmpty(assignee) 
	 	 						|| (!ValidateUtil.isEmpty(deleteReason) && deleteReason.equalsIgnoreCase("completed")) )
	 	 				{
	 	 					logger.error("业务流水号["+businessKey+"]关联的流程实例正在处理中，不允许撤销");
	 	 					throw new ServiceException(AuthErrorCodeConstants.PAUH0024, "流程实例正在处理中，不允许撤销");
	 	 				}
	 	 			}
	 			}
	 			
	 			//根据流程实例ID级联删除运行时流程实例
	 			String message = "deleted";          //activiti记录删除标识
	 			outSideActivitiRuntimeService.delProcessInstanceById(processInstanceId, message);
	 			//根据流程实例记录数判断删除是否成功
	 			long count = outSideActivitiRuntimeService.getProcessInstanceNumById(processInstanceId);
	 			if(count!=0){
	 				logger.error("删除业务流水号["+businessKey+"]关联的流程实例失败");
	 				throw new ServiceException(AuthErrorCodeConstants.PAUH0025, "删除业务流水号["+businessKey+"]关联的流程实例失败");
	 			}
	 			
	 			if(varsMap != null && varsMap.size()>0){
	 				String bsnCode = String.valueOf(varsMap.get("bsnCode"));
	 				if(!ValidateUtil.isEmpty(bsnCode)){
	 					//更新交易状态
	 		 			authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey,"",TradeStatusParamsConstants.TRADE_STATE_12,revokeReason,channel);
	 				}
	 			}
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNo+"]");
   			}
   			
   			return new ResponseEntity();
    	}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0026, "撤销授权申请失败", ex);
		}
	}
	
	/**
	 * 根据条件，查询授权历史信息列表(已办事宜列表查询)
	 */
	@Validation(value="p0000621")
	@Override
	public ResponseEntity queryAuthHistoryList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		try{
			//渠道编号
 			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			//法人记录唯一标识
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
 			//客户号
 			String cstNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_CSTNO));
 			if(ValidateUtil.isEmpty(cstNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0033,HeadParameterDefinitionConstants.REC_CSTNO+"不能为空");
 			}
 			//操作员编号
 			String userNo = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_OPERATORNO));
 			if(ValidateUtil.isEmpty(userNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0034,HeadParameterDefinitionConstants.REC_OPERATORNO+"不能为空");
 			}
 			
 			//业务类型(为空查所有业务分类)
 			String bsnType = (String) (bodyMap.get("bsnType")==null?"":bodyMap.get("bsnType"));
 			//操作类型 01：本人经办；02：本人授权（流程已完成）；03：本人授权（流程未完成）；
 			String operateType = (String)bodyMap.get("operateType");
 			//开始日期
 			String beginDate = (String)bodyMap.get("beginDate");
 			//结束日期
			String endDate = (String)bodyMap.get("endDate"); 
 			//当前页数
			int pageNo = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")));  
			//每页显示记录数
			String pageSizeStr = String.valueOf(bodyMap.get("pageSize")==null?BaseParamsConstants.SYS_DEFAULT_PAGESIZE:bodyMap.get("pageSize"));
			int pageSize = Integer.parseInt(pageSizeStr);          
			//结果排序方式 0:ASC；1:DESC
			String sortType = (String) (bodyMap.get("sortType")==null?"1":bodyMap.get("sortType"));                      
			
			if(!ValidateUtil.isEmpty(beginDate)){
				beginDate = DateUtil.formatDate(beginDate, DateUtil.DEFAULT_DATE_FORMAT) + "000000";
			}
			
			if(!ValidateUtil.isEmpty(endDate)){
				endDate = DateUtil.formatDate(endDate, DateUtil.DEFAULT_DATE_FORMAT) + "235959";
			}
			
			if(ValidateUtil.isEmpty(bsnType)){
				bsnType = null;
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();

			List<Map<String,Object>> historyList = new ArrayList<Map<String,Object>>();
			//根据法人记录唯一标识+客户编号+操作员编号查询操作员信息
   			Map<String, Object> userInfMap = operatorEntitySerivce.queryTtpUserIdByCondition(legalId, cstNo, userNo);
   			if(null!=userInfMap && userInfMap.size()>0){
   				String userId = (String)userInfMap.get("id");
   				
   			    //1：经办
   				if( !ValidateUtil.isEmpty(operateType) 
   						&& operateType.equals(AuthParamsConstants.OPERATE_TYPE_1) )
   				{
   					historyList = outSideAuthCompositeService.qryAuthHistoryList(legalId, cstNo, userId, bsnType, beginDate, endDate, sortType, pageNo, pageSize);
   				}
   				
   				//2：授权，授权流程已完成
   				if(!ValidateUtil.isEmpty(operateType) 
   						&& operateType.equals(AuthParamsConstants.OPERATE_TYPE_2) )
   				{
   					historyList = outSideAuthCompositeService.qryFinishedHisProcInstListByUserId(userId, bsnType, beginDate, endDate, sortType, pageNo, pageSize);
   				}
   				
   				//3：待办，授权流程未完成
   				if(!ValidateUtil.isEmpty(operateType) 
   						&& operateType.equals(AuthParamsConstants.OPERATE_TYPE_3) )
   				{
   					historyList = outSideAuthCompositeService.qryDoingHisProcInstListByUserId(userId, bsnType, beginDate, endDate, sortType, pageNo, pageSize);
   				}
   				
   				
   				List<Map<String, Object>> processInstanceList = new ArrayList<Map<String, Object>>();
   				for(Map<String, Object> map : historyList){
   					String processInstanceId = null;
   					if(operateType.equals(AuthParamsConstants.OPERATE_TYPE_1)){
   						processInstanceId = String.valueOf(map.get("id"));
   					}else{
   						processInstanceId = String.valueOf(map.get("procInstId"));
   					}
   					List<String> variableNames = new ArrayList<String>();
   					variableNames.add("businessKey");                           //业务流水号
   					variableNames.add("submitUserId");                          //任务提交人编号
   					variableNames.add("bsnCode");                               //业务编号
   					variableNames.add("authResult");                            //授权结果
   					
   					Map<String, Object> varsMap = outSideActivitiHistoryService.getHistoryValues(processInstanceId, variableNames);
   					if(null==varsMap || varsMap.size()==0){
   						varsMap = outSideActivitiTaskService.getVariableInstanceValue(processInstanceId, variableNames);
   					}
   							
   					String businessKey = String.valueOf(varsMap.get("businessKey"));
   					String authResult = String.valueOf(varsMap.get("authResult"));                       //授权结果
   					
   					Map<String, Object> hisProcessInstance = new HashMap<String, Object>();
   					
   					hisProcessInstance.put("businessKey", businessKey);                          //30位全局流水号
   					hisProcessInstance.put("processInstanceId", processInstanceId);              //流程实例ID	                                
   					hisProcessInstance.put("authResult", authResult);                            //授权结果
   					
   					//业务信息查询
   					String bsnCode = String.valueOf(varsMap.get("bsnCode"));
   					String bsnName = "";
 					Map<String,Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCode);
 					if(bsnMap != null && bsnMap.size()>0){
 						bsnName = String.valueOf(bsnMap.get("bsnName")==null?"":bsnMap.get("bsnName"));
 					}
 					hisProcessInstance.put("bsnCode", bsnCode);                   //业务编号
 					hisProcessInstance.put("bsnName", bsnName);                     //业务名称
 					
 					//操作员信息查询
   					String submitUserId = (String)varsMap.get("submitUserId");
 					Map<String, Object> userInfoMap = operatorEntitySerivce.queryTtpUserIdByUserIdCondition(legalId, cstNo, submitUserId);
 					String submitUserNo = "";//操作员编号
 					String submitUserName = "";//操作员名称
 		   			if(null!=userInfoMap && userInfoMap.size()>0){
 		   				submitUserNo = String.valueOf(userInfoMap.get("userNo"));
 		   			    submitUserName = String.valueOf(userInfoMap.get("userName"));
 		   			}
 		   		    hisProcessInstance.put("submitUserId", submitUserNo);                   //操作员编号
					hisProcessInstance.put("submitUserName", submitUserName);               //操作员名称
					
					Map<String,Object> tradeStateMap = authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey,"", "","",channel);
 				    if(tradeStateMap != null && tradeStateMap.size()>0){
 				    	hisProcessInstance.put("createTime", String.valueOf(tradeStateMap.get("transStartTime")));//制单时间	
 				    	hisProcessInstance.put(TradeStatusParamsConstants.FIELD_TRADE_STATE, String.valueOf(tradeStateMap.get("transtt")));
 				    	hisProcessInstance.put("returnMsg", String.valueOf(tradeStateMap.get("returnMsg")));
 				    }
   					
   					processInstanceList.add(hisProcessInstance);
   				}

   				//查询历史任务总记录数
   				long totalNum = 0;
   				
   				//1：经办
   				if( !ValidateUtil.isEmpty(operateType) 
   						&& operateType.equals(AuthParamsConstants.OPERATE_TYPE_1) )
   				{
   					totalNum = outSideActivitiHistoryService.qryHistoricProcessInstanceTotalNum(legalId, cstNo, userId, bsnType, beginDate, endDate);
   				}
   				
   				//2：授权，授权流程已完成
   				if( !ValidateUtil.isEmpty(operateType) 
   						&& operateType.equals(AuthParamsConstants.OPERATE_TYPE_2) )
   				{
   					totalNum = outSideAuthCompositeService.qryFinishedHisProcInstListTotalNumByUserId(userId, bsnType, beginDate, endDate);
   				}
   				
   				//3：待办，授权流程未完成
   				if( !ValidateUtil.isEmpty(operateType) 
   						&& operateType.equals(AuthParamsConstants.OPERATE_TYPE_3) )
   				{
   					totalNum = outSideAuthCompositeService.qryDoingHisProcInstListTotalNumByUserId(userId, bsnType, beginDate, endDate);
   				}	
   				
   				int totalPage = DataUtil.getTotalPage((int)totalNum, pageSize);
   				resultMap.put("outSideHisTaskList", processInstanceList);
   				resultMap.put("totalNum", String.valueOf(totalNum));
   				resultMap.put("totalPage", String.valueOf(totalPage));
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0037, "操作员信息不存在["+userNo+"]");
   			}
   			
   			return new ResponseEntity(resultMap);
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0027, "查询已办事宜列表失败", ex);
		}
	}

	/**
	 * 显示流程图生成，上传到ftp服务器
	 */
	@Validation(value="p0000624")
	@Override
	public ResponseEntity addAuthProcessPhotoToFtpServer(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		String taskId = String.valueOf(bodyMap.get("taskId"));//任务id
		String ftpFilePath = authShowService.uploadImageFile(taskId);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ftpAbsolutePath", ftpFilePath);
		return new ResponseEntity(resultMap);
	}

	/**
	 * 查询授权流程节点坐标和明细信息
	 */
	@Validation(value="p0000625")
	@Override
	public ResponseEntity queryAuthProcessNodeDetail(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		String taskId = String.valueOf(bodyMap.get("taskId"));//任务id
		List<Map<String,Object>> list = authShowService.getAllNodePosition(taskId, "2");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("positionDetailList", list);
		return new ResponseEntity(resultMap);
	}
	
	
	
}
