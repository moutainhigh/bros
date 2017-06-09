package bros.p.auth.facade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.activiti.inside.service.IInSideActivitiHistoryService;
import bros.common.core.activiti.inside.service.IInSideActivitiRuntimeService;
import bros.common.core.activiti.inside.service.IInSideActivitiTaskService;
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
import bros.p.auth.facade.service.IPInsideAuthServiceFacade;
import bros.provider.auth.constants.AuthErrorCodeConstants;
import bros.provider.auth.constants.AuthParamsConstants;
import bros.provider.parent.activiti.composite.service.IAuthShowService;
import bros.provider.parent.activiti.inside.service.IInsideTaskService;
import bros.provider.parent.activiti.relation.service.IBsnInfoService;
import bros.provider.parent.activiti.service.IAuthBusinessFlowService;
import bros.provider.parent.activiti.user.service.IQryInnerUserService;
import bros.provider.parent.auth.inside.composite.service.IInSideAuthCompositeService;

/** 
 * @ClassName: InsideAuthServiceImpl 
 * @Description: 内部授权服务接口
 * @author weiyancheng
 * @date 2016年7月22日 下午5:29:14 
 * @version 1.0 
 */
@Component("pinsideAuthServiceFacade")
public class PInsideAuthServiceFacadeImpl implements IPInsideAuthServiceFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(PInsideAuthServiceFacadeImpl.class);

	/**
	 * 内部授权工作流任务服务
	 */
	@Autowired
	private IInsideTaskService insideTaskService;
	@Autowired
	private IInSideActivitiTaskService inSideActivitiTaskService;
	@Autowired
	private IInSideActivitiHistoryService inSideActivitiHistoryService;
	@Autowired
	private IInSideActivitiRuntimeService inSideActivitiRuntimeService;
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
	
	@Autowired
	private IAuthBusinessFlowService authBusinessFlowService;
	@Autowired
	private IQryInnerUserService qryInnerUserService;
	/**
	 * 查询业务编码信息
	 */
	@Autowired
	private IBsnInfoService bsnInfoService;
	/**
	 * 业务组装
	 */
	@Autowired
	private IInSideAuthCompositeService inSideAuthCompositeService;
	/**
	 * 授权流程图展示服务
	 */
	@Autowired
	private IAuthShowService authShowService;
	/** 
	 * @Title: tranQryTaskCenterView 
	 * @Description: 待授权指令概览统计
	 * @param param
	 * @throws ServiceException
	 */
	@Override
	public ResponseEntity qryTaskCenterView(Map<String, Object> headMap,Map<String, Object> bodyMap)throws ServiceException {
		try {
			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//操作柜员编号
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人id
			String bsnTypeBody =String.valueOf(bodyMap.get("bsnType"));//交易类型
			
			//根据柜员号和法人ID查询柜员ID
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
            String userId = (String) tellerMap.get("userId");//当前柜员的用户id
			
 			//根据法人ID+用户ID查询待授权任务分类概览
 			List<Map<String, Object>> waitClaimViewList = insideTaskService.qryTaskOverViewBsnTypeList(legalId, userId,bsnTypeBody);
 			
 			List<Map<String,Object>> taskViewList = new ArrayList<Map<String,Object>>();
 			//业务编号+业务名称+待签收任务笔数
 			if(waitClaimViewList !=null && waitClaimViewList.size()>0 ){
 				for(Map<String, Object> waitClaimMap : waitClaimViewList){
 					Map<String, Object> viewMap = new HashMap<String, Object>();
 					String bsnType = String.valueOf(waitClaimMap.get("bsnType"));
 					String recordNum = String.valueOf(waitClaimMap.get("recordNum")==null?"0":waitClaimMap.get("recordNum"));
 					viewMap.put("bsnType", bsnType);
 					viewMap.put("orderCount", recordNum);
 					String bsnTypeName = GetCacheDataUtil.getAppParShowMsgByCache("bsndefTradeType", bsnType);
 					viewMap.put("bsnTypeName", bsnTypeName);
 					taskViewList.add(viewMap);
 				}
 			}
 			
 			Map<String, Object> resultMap = new HashMap<String, Object>();
 			resultMap.put("taskViewList", taskViewList);
 			return new ResponseEntity(resultMap);
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0002,"查询待授权指令概览失败",ex);
		}
	}

	
	/** 
	 * 根据业务类型查询待授权指令概览统计
	 */
	@Override
	public ResponseEntity qryTaskCenterViewByBsnType(
			Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException {
		try {
			
			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//操作柜员号
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人Id
			String bsnType = String.valueOf(bodyMap.get("bsnType")); //业务类型
			String bsnCodeResult = String.valueOf(bodyMap.get("bsnCode"));//业务编码
			
			//根据柜员号和法人ID查询柜员ID
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
            String userId = (String) tellerMap.get("userId");
           
 			//根据业务编号统计待授权功能笔数信息列表
 			List<Map<String, Object>> waitClaimViewList = insideTaskService.qryTaskViewByBsnTypeList(legalId, userId, bsnType,bsnCodeResult);
 			
 			List<Map<String,Object>> taskViewList = new ArrayList<Map<String,Object>>();//返回结果集
 			//业务编号+业务名称+待签收任务笔数
 			if( null!=waitClaimViewList && waitClaimViewList.size()>=0 ){
 				for(Map<String,Object> waitClaimMap : waitClaimViewList){
 					Map<String,Object> resultTempMap = new HashMap<String, Object>();
 					String bsnCode = (String)waitClaimMap.get("bsnCode");//功能编码
 					String recordNum = String.valueOf(waitClaimMap.get("recordNum")==null?"0":waitClaimMap.get("recordNum"));//记录条数
 					String bsnName = "";
 					Map<String,Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCode);
 					if(bsnMap != null && bsnMap.size()>0){
 						bsnName = String.valueOf(bsnMap.get("bsnName")==null?"":bsnMap.get("bsnName"));
 					}
 					resultTempMap.put("orderCount", recordNum);
 					resultTempMap.put("bsnCode", bsnCode);
 					resultTempMap.put("bsnName", bsnName);
 					
 					taskViewList.add(resultTempMap);
 				}
 			}
 			
 			Map<String, Object> resultMap = new HashMap<String, Object>();
 			resultMap.put("taskViewList", taskViewList);
 			return new ResponseEntity(resultMap);
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0013, "查询待授权指令列表失败",ex);
		}
	}
	
	/**
	 * @Title: qryAuthQueueList 
	 * @Description: 根据业务编号查询待授权指令列表
	 * @param headMap 报文头数据域
	 * @param bodyMap 报文体数据域
	 * @return
	 * @throws ServiceException
	 */
	@Validation(value="p0000601")
	@Override
	public ResponseEntity qryAuthQueueList(Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		try {
			//操作柜员编号
			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);
			//渠道编号
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			//法人编号
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			//功能编码
			String funcCode = String.valueOf(bodyMap.get("funcCode"));
			//交易分类
			String funcType = String.valueOf(bodyMap.get("funcType"));
			
			//开始页数
            String pageNo = String.valueOf(bodyMap.get("pageNo"));
            //每页显示记录数
            String pageSize = String.valueOf(bodyMap.get("pageSize")==null?BaseParamsConstants.SYS_DEFAULT_PAGESIZE:bodyMap.get("pageSize"));
            int firstResult = (Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize);
            int maxResults = Integer.parseInt(pageSize);
            
			//根据柜员号和法人ID查询柜员ID
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
            String userId = (String) tellerMap.get("userId");
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
 			//根据业务编号统计待签收和已签收任务列表
 			List<Map<String, Object>> authQueueList = insideTaskService.queryAuthQueueTaskList(legalId, userId, funcCode,funcType, firstResult, maxResults);
 			
			List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>(); 
			for(Map<String, Object> map : authQueueList){
				Map<String, Object> task = new HashMap<String, Object>();

				//根据流程实例ID
				String taskId = (String)map.get("taskId");
				List<String> variableNames = new ArrayList<String>();
				variableNames.add("businessKey");  //指令流水号
				variableNames.add("bsnCode");
				variableNames.add("bsnType");
				//查询流程变量
				Map<String, Object> varsMap = inSideActivitiTaskService.getVariableInstanceValueByTaskId(taskId, variableNames);
				
				String flowNo = (String)varsMap.get("businessKey");
				String funcCodeTask = (String) varsMap.get("bsnCode");
				String bsnType = String.valueOf(varsMap.get("bsnType"));
				String bsnTypeName = GetCacheDataUtil.getAppParShowMsgByCache("bsndefTradeType", bsnType);
				String bsnName = "";
				String canBatch = "";
				Map<String,Object> bsnMap = bsnInfoService.qryBsnInfo(funcCodeTask);
				if(bsnMap != null && bsnMap.size()>0){
					bsnName = String.valueOf(bsnMap.get("bsnName")==null?"":bsnMap.get("bsnName"));
					canBatch = String.valueOf(bsnMap.get("canBatch")==null?"0":bsnMap.get("canBatch"));
				}
				
				task.put("businessKey", flowNo);        //指令流水号
				task.put("taskId", taskId);             //任务ID
				task.put("bsnName", bsnName);           //功能名称
				task.put("bsnCode", funcCodeTask);      //功能码
				task.put("bsnType", bsnType);      //交易分类
				task.put("bsnTypeName", bsnTypeName);      //交易分类名称
				task.put("canBatch", canBatch);         //是否允许批量
				//查询指令详情
				Map<String, Object> orderMap = qryOrderDetail(flowNo, funcCodeTask, channel);
				task.put("submitTellerNo", orderMap.get("tellerNo"));        //录入柜员编号
				String tranTime = "";
				String tranDate = "";
				String transStartTime = String.valueOf(orderMap.get("transStartTime"));//制单时间
				if(!ValidateUtil.isEmpty(transStartTime)){
					tranDate = transStartTime.substring(0,8);
					tranTime = transStartTime.substring(8);
				}
				task.put("createTime", tranTime);                //制单时间
				task.put("createDate", tranDate);                //制单日期
				task.put("transtt", orderMap.get("transtt"));                 //交易状态
			
				taskList.add(task);
			}
 			
 			//记录数
 			long num = insideTaskService.queryAuthQueueTaskTotalNum(legalId, userId, funcCode,funcType);
 			
 			//总页数
			int totalPage = DataUtil.getTotalPage((int)num, Integer.parseInt(pageSize));
 			resultMap.put("resultList", taskList);
 			resultMap.put("totalNum", num+"");
 			resultMap.put("totalPage", totalPage+"");
 			return new ResponseEntity(resultMap);
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0004, "根据业务编号查询待授权交易列表失败",ex);
		}

	}
	
	/** 
	 * 退回任务
	 */
	@Validation(value="p0000602")
	@Override
	public ResponseEntity unClaimTaskJob(Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			//操作柜员编号
			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);
			//根据柜员号和法人ID查询柜员ID
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
            String userId = (String) tellerMap.get("userId");
            //任务ID
            String taskId = (String) bodyMap.get("taskId");
			inSideActivitiTaskService.unClaimTask(userId, taskId);
			return new ResponseEntity();
		}catch(ServiceException e) {
			throw e;
		}catch(Exception ex) {
			throw new ServiceException(AuthErrorCodeConstants.PAUH0005, "退回任务失败",ex);
		}
	}

	/** 
	 * 根据业务流水号（指令流水号）+业务编号查询指令详情
	 */
	private Map<String, Object> qryOrderDetail(String businessKey,String bsnCode,String channel)
			throws ServiceException {
		try{
			//根据业务编号查询出业务对应的指令提供器和sql提供器
			Map<String, Object> cfgMap = bsnFlowCfgDaoService.queryBsnFlowCfg(bsnCode,channel);
			if(cfgMap == null || cfgMap.size()<=0){
				throw new ServiceException(AuthErrorCodeConstants.PAUH0030,"流水记录表pub_bsnflow_cfg中该场景记录不存在【"+bsnCode+"】");
			}
			String commdMessage = (String) cfgMap.get("updaterBeanId");
			String sqlAllPath = (String) cfgMap.get("providerBeanId");
			IOrderUpdaterService service = flowProcessorService.createProcessor(commdMessage, sqlAllPath);
			//用具体指令处理器查询指令详情
			Map<String, Object> detailMap = service.queryMainAndDetailFlowForOne(businessKey);
			return detailMap;
		}catch(ServiceException e) {
			throw e;
		}catch(Exception ex) {
			throw new ServiceException(AuthErrorCodeConstants.PAUH0006, "查询指令详情失败",ex);
		}

	}

	/** 
	 * 单笔授权（临柜）
	 */
	@SuppressWarnings("unused")
	@Validation(value="p0000603")
	@Override
	public ResponseEntity singleCompleteTask(Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			//操作柜员编号
			String legalId = String.valueOf(headMap.get(HeadParameterDefinitionConstants.REC_LEGALID));
			String processInstanceId = String.valueOf(bodyMap.get("processInstanceId")); //流程实例Id
			String isPass = String.valueOf(bodyMap.get("isPass")); //审核结果（1-通过; 0-拒绝）	
			String authOpinion = String.valueOf(bodyMap.get("authOpinion"));//审核意见
			String authTellerNo = String.valueOf(bodyMap.get("authTellerNo"));//授权柜员
			String authPwdType = String.valueOf(bodyMap.get("authPwdType"));//密码类型   1指纹     2密码
			String authPwd = String.valueOf(bodyMap.get("authPwd")); //授权密码/指纹
			String batchNo = "";//批次编号   用于打印凭证
			//根据柜员号和法人ID查询柜员ID
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, authTellerNo);
            String userId = (String) tellerMap.get("userId");
            
			//查询该流程实例id下一步的活动任务
			Task task = inSideActivitiTaskService.queryTaskByCondition(legalId, userId, processInstanceId);
		    
		    if(null == task){
		    	throw new ServiceException(AuthErrorCodeConstants.PAUH0007, "待授权指令不存在");
		    }
		    //任务ID
			String taskId = task.getId();
			//根据流程实例ID查询流程变量
			List<String> variableNames = new ArrayList<String>();
			variableNames.add("businessKey");  //业务流水号
			variableNames.add(HeadParameterDefinitionConstants.REC_BSNCODE);  //业务流水号
			variableNames.add(HeadParameterDefinitionConstants.REC_CHANNEL);  //业务流水号
            //查询流程变量
			Map<String, Object> varsMap = inSideActivitiTaskService.getVariableInstanceValueByTaskId(taskId, variableNames);
			
			//临柜授权身份认证，如果身份认证非法，则抛出异常和指定错误码
			boolean isConfirm = false;
			//身份验证
			if("111111".equals(authPwd)){
				isConfirm = true;
			}
			if(!isConfirm){
				logger.error("授权密码或指纹验证错误");
				throw new ServiceException(AuthErrorCodeConstants.PAUH0008, "授权密码或指纹验证错误");
			}
			//判断当前当前任务是否已经签收
			boolean isClaimTask = inSideActivitiTaskService.checkIsClaimTask(taskId);
			//未签收
			if(!isClaimTask){
				String message = "柜员[" + userId + "]签收任务";
				//任务签收
				inSideActivitiTaskService.claimTask(userId, taskId, message);
			}
			boolean isAgree = false;
			String authOpinionTemp = "授权拒绝";
			if(!ValidateUtil.isEmpty(isPass) && isPass.equals(ActivitiParamsConstants.ACT_ISPASS_1)){
				isAgree = true;
				authOpinionTemp = "授权通过";
			}
			
			if(ValidateUtil.isEmpty(authOpinion)){
				authOpinion = authOpinionTemp;
			}
			
			//1-等待授权
			String orderState = "";//授权交易状态
			try{
				//完成任务并保存审核意见
				inSideActivitiTaskService.completeTask(userId, taskId, isAgree, authOpinion);
				//90-交易成功
				orderState = TradeStatusParamsConstants.TRADE_STATE_90;
			}catch(Exception e){
				logger.info("任务完成异常",e);
				//99-交易失败
				orderState = TradeStatusParamsConstants.TRADE_STATE_91;
			}
			
			//根据指令流水号获取交易状态
			String businessKey = (String)varsMap.get("businessKey"); //30位全局流水号
			String bsnCode = (String) varsMap.get(HeadParameterDefinitionConstants.REC_BSNCODE);//业务编号
			String channel = (String) varsMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道
			try{
				String tradeStt = "";
				String authOpinionResultTemp = "";
				if(orderState.equals(TradeStatusParamsConstants.TRADE_STATE_90)){//交易成功
					if(!isAgree){//授权拒绝
						tradeStt = TradeStatusParamsConstants.TRADE_STATE_11;
						authOpinionResultTemp = authOpinion;
					}
					batchNo = BaseUtil.createUUID();
				}
				
				Map<String,Object> tradeStateMap = authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey,batchNo,tradeStt,authOpinionResultTemp, channel);
				if(tradeStateMap != null && tradeStateMap.size()>0){
					returnMap.put(TradeStatusParamsConstants.FIELD_TRADE_STATE, String.valueOf(tradeStateMap.get("transtt")));
					returnMap.put("bsnCode", bsnCode);//业务编码
					returnMap.put("submitTellerNo", String.valueOf(tradeStateMap.get("tellerNo")));//录入柜员编号
					returnMap.put("createTime", String.valueOf(tradeStateMap.get("tranTime")));//制单时间
					
					//根据渠道功能编号查询功能定义信息
					Map<String, Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCode);
		 			String bsnName = String.valueOf(bsnMap.get("bsnName"));
		 			returnMap.put("bsnName", bsnName);//制单时间
				}
			}catch(ServiceException e){
				logger.error("根据指令流水号[" + businessKey +"]更新、获取交易状态失败",e);
			}
			returnMap.put(TradeStatusParamsConstants.FIELD_ORDER_STATE, orderState);
			returnMap.put(TradeStatusParamsConstants.FIELD_AUTH_RESULT, isPass);
			returnMap.put("taskId", taskId);
			returnMap.put("businessKey", businessKey);
			returnMap.put("batchNo", batchNo);
			
			return new ResponseEntity(returnMap);
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0009, "对指令进行授权操作失败",e);
		}
	}

	/** 
	 * 单笔授权（审核）
	 */
	@Validation(value="p0000604")
	@Override
	public ResponseEntity authSingleCompleteTask(Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);////操作柜员编号
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人编号
			String taskId = String.valueOf(bodyMap.get("taskId")); //任务ID
			String isPass = String.valueOf(bodyMap.get("isPass")); //审核结果（1-通过; 0-拒绝）
			String authOpinion = String.valueOf(bodyMap.get("authOpinion"));//审核意见
			String batchNo = "";//批次编号
			
			//根据柜员号和法人ID查询柜员ID
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
            String userId = (String) tellerMap.get("userId");
			
			
			List<String> variableNames = new ArrayList<String>();
			variableNames.add(ActivitiParamsConstants.FIELD_SUBMIT_USERID);  //任务提交人
			variableNames.add("businessKey");  //业务流水号
			variableNames.add(HeadParameterDefinitionConstants.REC_BSNCODE);  //业务流水号
			variableNames.add(HeadParameterDefinitionConstants.REC_CHANNEL);  //渠道
			
			//查询流程变量
			Map<String, Object> varsMap = inSideActivitiTaskService.getVariableInstanceValueByTaskId(taskId, variableNames);
			//校验当前操作员是否是提交柜员
			String submitUserId = (String)varsMap.get(ActivitiParamsConstants.FIELD_SUBMIT_USERID);
			if(userId.equals(submitUserId)){
				logger.error("提交指令柜员["+userId+"]无权限对任务[" + taskId + "]进行授权");
				throw new ServiceException(AuthErrorCodeConstants.PAUH0010, "授权柜员不能与指令提交柜员是同一人");
			}
			
			//判断任务是否已被签收
			boolean isClaim = inSideActivitiTaskService.checkIsClaimTask(taskId);
			//未已签收
			if(!isClaim){
				//判断该用户是否可以签收该任务
				boolean can = inSideActivitiTaskService.checkCanClaimTask(legalId, userId, taskId);
				if(can){
					//签收任务
					String message = "柜员[" + userId + "]签收任务";
					//任务签收
					inSideActivitiTaskService.claimTask(userId, taskId, message);
				}else{
					logger.error("提交指令柜员["+userId+"]无权限对任务[" + taskId + "]进行授权");
					throw new ServiceException(AuthErrorCodeConstants.PAUH0011, "您无权对该指令进行授权处理");
				}
			}else{//已签收
				//判断该用户是否是该任务的处理人
				boolean isAssgine = inSideActivitiTaskService.checkUserIsAssignee(userId, taskId);
				if(!isAssgine){
					throw new ServiceException(AuthErrorCodeConstants.PAUH0011, "您无权对该指令进行授权处理");
				}
			}
			
			boolean isAgree = false;
			String authOpinionTemp = "授权拒绝";
			if(!ValidateUtil.isEmpty(isPass) && isPass.equals(ActivitiParamsConstants.ACT_ISPASS_1)){
				isAgree = true;
				authOpinionTemp = "授权通过";
			}
			if(ValidateUtil.isEmpty(authOpinion)){
				authOpinion = authOpinionTemp;
			}
			
			//1-等待授权
			String orderState = "";//授权交易指令状态
			try{
				//完成任务并保存审核意见
				inSideActivitiTaskService.completeTask(userId, taskId, isAgree, authOpinion);
				//90-交易成功
				orderState = TradeStatusParamsConstants.TRADE_STATE_90;
			}catch(Exception e){
				logger.info("任务完成异常",e);
				//99-交易失败
				orderState = TradeStatusParamsConstants.TRADE_STATE_91;
			}
			
			//根据指令流水号获取交易状态
			String businessKey = (String)varsMap.get("businessKey"); //30位全局流水号
			String bsnCode = (String) varsMap.get(HeadParameterDefinitionConstants.REC_BSNCODE);//业务编号
			String channel = (String) varsMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道
			try{
				String tradeStt = "";
				String authOpinionResultTemp = "";
				if(orderState.equals(TradeStatusParamsConstants.TRADE_STATE_90)){//交易成功
					if(!isAgree){//授权拒绝
						tradeStt = TradeStatusParamsConstants.TRADE_STATE_11;
						authOpinionResultTemp = authOpinion;
					}
					batchNo = BaseUtil.createUUID();
				}
				Map<String,Object> tradeStateMap = authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey, batchNo,tradeStt,authOpinionResultTemp,channel);
				if(tradeStateMap != null && tradeStateMap.size()>0){
					returnMap.put(TradeStatusParamsConstants.FIELD_TRADE_STATE, String.valueOf(tradeStateMap.get("transtt")));
					returnMap.put("bsnCode", bsnCode);//业务编码
					String userNo = String.valueOf(tradeStateMap.get("tellerNo"));
					String submitTellerName = "";
					returnMap.put("submitTellerNo", userNo);//录入柜员编号
					returnMap.put("createTime", String.valueOf(tradeStateMap.get("tranTime")));//制单时间
					Map<String,Object> userInfoMap = qryInnerUserService.qryTellerInfoByOneUserNo(legalId,userNo);
					if(userInfoMap != null && userInfoMap.size()>0){
						submitTellerName = String.valueOf(userInfoMap.get("tellerName"));
					}
					returnMap.put("submitTellerName", submitTellerName);//录入柜员名称
					//根据渠道功能编号查询功能定义信息
					Map<String, Object> bsnMap = bsnInfoService.qryBsnInfo(bsnCode);
		 			String bsnName = String.valueOf(bsnMap.get("bsnName"));
		 			returnMap.put("bsnName", bsnName);//制单时间
				}
			}catch(ServiceException e){
				logger.error("根据指令流水号[" + businessKey +"]更新、获取交易状态失败",e);
			}
			
			returnMap.put(TradeStatusParamsConstants.FIELD_ORDER_STATE, orderState);
			returnMap.put(TradeStatusParamsConstants.FIELD_AUTH_RESULT, isPass);
			returnMap.put("taskId", taskId);
			returnMap.put("businessKey", businessKey);
			returnMap.put("batchNo", batchNo);
			
			return new ResponseEntity(returnMap);
		}catch(ServiceException ex){
			throw ex;
		}catch(Exception e){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0009, "对指令进行授权操作失败",e);
		}
	}

	/** 
	 * 批量授权（审核）
	 */
	@SuppressWarnings("unchecked")
	@Validation(value="p0000605")
	@Override
	public ResponseEntity batchCompleteTask(Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			
			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//操作柜员编号
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人Id
			List<Map<String, Object>> taskAuthList = (List<Map<String, Object>>) bodyMap.get("taskAuthList");//待授权指令列表
			String isPass = String.valueOf(bodyMap.get("isPass")); //审核结果（1-通过; 0-拒绝）
			String authOpinion = String.valueOf(bodyMap.get("authOpinion"));//审核意见
			String batchNo = BaseUtil.createUUID();//批量授权批次号
			
			//根据柜员号和法人ID查询柜员ID
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
            String userId = (String) tellerMap.get("userId");
			
			List<Map<String, Object>> taskAuthResultList = new ArrayList<Map<String, Object>>();//授权结果列表
			int authSuccess = 0;//授权成功笔数
			int authFailure = 0;//授权失败笔数
			
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
					//查询流程变量
					Map<String, Object> varsMap = inSideActivitiTaskService.getVariableInstanceValueByTaskId(taskId, variableNames);
					businessKey = String.valueOf(varsMap.get("businessKey")); //指令流水号
					channel = String.valueOf(varsMap.get(HeadParameterDefinitionConstants.REC_CHANNEL));//渠道
					bsnCode = String.valueOf(varsMap.get(HeadParameterDefinitionConstants.REC_BSNCODE));//bsnCode;
					
					//校验授权柜员是否是提交柜员
					String submitUserId = (String)varsMap.get(ActivitiParamsConstants.FIELD_SUBMIT_USERID);
					if(userId.equals(submitUserId)){
						logger.error("提交指令柜员["+userId+"]无权限对任务[" + taskId + "]进行授权");
						throw new ServiceException(AuthErrorCodeConstants.PAUH0010, "授权柜员不能与指令提交柜员是同一人");
					}
					
					//判断任务是否已被签收
					boolean isClaim = inSideActivitiTaskService.checkIsClaimTask(taskId);
					//未已签收
					if(!isClaim){
						//判断该用户是否可以签收该任务
						boolean can = inSideActivitiTaskService.checkCanClaimTask(legalId, userId, taskId);
						if(can){
							//签收任务
							String message = "柜员[" + userId + "]签收任务";
							//任务签收
							inSideActivitiTaskService.claimTask(userId, taskId, message);
						}else{
							logger.error("提交指令柜员["+userId+"]无权限对任务[" + taskId + "]进行授权");
							throw new ServiceException(AuthErrorCodeConstants.PAUH0011, "您无权对该指令进行授权处理");
						}
					}else{//已签收
						//判断该用户是否是该任务的处理人
						boolean isAssgine = inSideActivitiTaskService.checkUserIsAssignee(userId, taskId);
						if(!isAssgine){
							throw new ServiceException(AuthErrorCodeConstants.PAUH0011, "您无权对该指令进行授权处理");
						}
					}
					
					boolean isAgree = false;
					String authOpinionTemp = "授权拒绝";
					if(!ValidateUtil.isEmpty(isPass) && isPass.equals(ActivitiParamsConstants.ACT_ISPASS_1)){
						isAgree = true;
						authOpinionTemp = "授权通过";
					}
					if(ValidateUtil.isEmpty(authOpinion)){
						authOpinion = authOpinionTemp;
					}
					
					//1-等待授权
					try{
						//完成任务并保存审核意见
						inSideActivitiTaskService.completeTask(userId, taskId, isAgree, authOpinion);
						//90-交易成功
						orderState = TradeStatusParamsConstants.TRADE_STATE_90;
						orderMsg = "授权成功";
						authSuccess++;
					}catch(Exception e){
						logger.info("完成任务失败",e);
						//99-交易失败
						orderState = TradeStatusParamsConstants.TRADE_STATE_91;
						orderMsg = "授权失败";
						authFailure++;
					}
				}catch(ServiceException se){
					logger.info("任务完成失败",se);
					orderState = TradeStatusParamsConstants.TRADE_STATE_91;
					orderMsg = se.getErrorMsg();
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
							String userNo = String.valueOf(tradeStateMap.get("tellerNo"));
							String submitTellerName = "";
							resultMap.put("submitTellerNo", userNo);//录入柜员编号
							resultMap.put("createTime", String.valueOf(tradeStateMap.get("tranTime")));//制单时间
							
							Map<String,Object> userInfoMap = qryInnerUserService.qryTellerInfoByOneUserNo(legalId,userNo);
							if(userInfoMap != null && userInfoMap.size()>0){
								submitTellerName = String.valueOf(userInfoMap.get("tellerName"));
							}
							resultMap.put("submitTellerName", submitTellerName);//录入柜员名称
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
					resultMap.put("orderMsg", orderMsg);
					resultMap.put("taskId", taskId);
					resultMap.put("businessKey", businessKey);
					resultMap.put("batchNo", batchNoTemp);
					taskAuthResultList.add(resultMap);
				}
			}
			
			returnMap.put("batchNo", batchNo);
			returnMap.put("authFailure", String.valueOf(authFailure));
			returnMap.put("authSuccess", String.valueOf(authSuccess));
			returnMap.put("taskAuthResultList", taskAuthResultList);
			return new ResponseEntity(returnMap);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0009, "对指令进行授权操作失败",e);
		}
	}

	/** 
	 * 根据批次号+业务编号查询指令详情列表（用于凭证打印）
	 */
	@Validation(value="p0000606")
	@Override
	public ResponseEntity qryOrderDetailListByBatchNo(Map<String, Object> headMap,Map<String, Object> bodyMap)
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
			if(flowSeqList != null && flowSeqList.size()>0){
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
	@Validation(value="p0000607")
	@Override
	public ResponseEntity qryCommentListByTaskId(Map<String, Object> headMap,Map<String, Object> bodyMap)
			throws ServiceException {
		try{
			String legalId = (String) headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人Id
			String businessKey = (String) bodyMap.get("businessKey");//30位全局流水号
			//根据流水号查询历史流程实例
			HistoricProcessInstance hpi = inSideActivitiHistoryService.queryHistoricProcessInstanceByBusinessKey(businessKey);
			//流程实例ID
			String proceId = hpi.getId();
    		//根据流程实例ID查询审批意见历史信息
    		List<Comment> commentList = inSideActivitiTaskService.getProcessInstanceComments(proceId);
    		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    		for(Comment comment:commentList){
    			Map<String, Object> comMap = new HashMap<String, Object>();
    			comMap.put("dealTime", DateUtil.formatDate(comment.getTime(), DateUtil.DEFAULT_TIME_FORMAT_DB));
    			String userId = comment.getUserId();
    			String processInstanceId = comment.getProcessInstanceId();
    			String authContent = comment.getFullMessage();//柜员[" + userId + "]签收任务
    			//如果是签收任务意见的化，就过滤掉
    			if(!ValidateUtil.isEmpty(authContent) && 
    					authContent.startsWith("柜员[") && 
    					authContent.endsWith("]签收任务")){
    				continue;
    			}
    			String tellerCode = "";
    			String tellerName = "";
    			Map<String,Object> userInfoMap = qryInnerUserService.qryTellerInfoByOneUserId(legalId, userId);
    			if(userInfoMap != null && userInfoMap.size()>0){
    				tellerCode = String.valueOf(userInfoMap.get("tellerCode"));
    				tellerName = String.valueOf(userInfoMap.get("tellerName"));
    			}
    			
    			comMap.put("userId", userId);
    			comMap.put("tellerName", tellerName);
				comMap.put("tellerCode", tellerCode);
    			comMap.put("authContent", authContent);
    			
    			List<String> variableNames = new ArrayList<String>();
    			variableNames.add("AUDITACTION");
    			Map<String, Object> varsMap = new HashMap<String, Object>();
				varsMap = inSideActivitiHistoryService.getHistoryValues(processInstanceId, variableNames);
				if(null==varsMap || varsMap.size()==0){
					varsMap = inSideActivitiHistoryService.getVariableInstanceValue(processInstanceId, variableNames);
				}
    			String authResult = String.valueOf(varsMap.get("AUDITACTION"));
    			comMap.put("authResult", authResult);//授权结果
    			resultList.add(comMap);
    		}
    		
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("resultList", resultList);
    		return new ResponseEntity(map);
		}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0014, "查询审批意见历史信息失败",ex);
		}
	}

	/**
	 * @Title: qryAuthDetailPageDataByBusinessKey 
	 * @Description: 根据业务流水号（指令流水号）+业务编号查询授权详情页面展示数据
	 * @param headMap 报文头域
	 * @param bodyMap 报文体域
	 * @throws ServiceException
	 */
	@Validation(value="p0000608")
	@Override
	public ResponseEntity qryAuthDetailPageDataByBusinessKey(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
			String channel = (String) headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);
			
			String businessKey = (String) bodyMap.get("businessKey");
			String bsnCode = (String) bodyMap.get("funcCode");
			
			//用具体指令处理器查询指令详情
			Map<String, Object> detailMap = queryMainAndDetailFlowByGblflowSeq(businessKey,bsnCode,channel);
			return new ResponseEntity(detailMap);
		}catch(ServiceException e) {
			throw e;
		}catch(Exception ex) {
			throw new ServiceException(AuthErrorCodeConstants.PAUH0029, "获取授权详情模板数据失败",ex);
		}
	}
	
	/**
	 *  根据30位全局流水号+业务编号+渠道编号查询主流水和明细信息
	 */
	private Map<String, Object> queryMainAndDetailFlowByGblflowSeq(String gblflowSeq,String businessCode,String channel) throws ServiceException {
		try{
			//根据业务编号查询出业务对应的指令提供器和sql提供器
			Map<String, Object> cfgMap = bsnFlowCfgDaoService.queryBsnFlowCfg(businessCode,channel);
			if(cfgMap == null || cfgMap.size()<=0){
				throw new ServiceException(AuthErrorCodeConstants.PAUH0030,"流水记录表pub_bsnflow_cfg中该场景记录不存在【"+businessCode+"】");
			}
			String commdMessage = (String) cfgMap.get("updaterBeanId");
			String sqlAllPath = (String) cfgMap.get("providerBeanId");
			IOrderUpdaterService service = flowProcessorService.createProcessor(commdMessage, sqlAllPath);
			//用具体指令处理器查询指令详情
			Map<String, Object> detailMap = service.queryMainAndDetailFlowByGblflowSeq(gblflowSeq);
			return detailMap;
		}catch(ServiceException e) {
			throw e;
		}catch(Exception ex) {
			throw new ServiceException(AuthErrorCodeConstants.PAUH0006, "查询指令详情失败",ex);
		}
	}
	
	
	/**
     * 根据法人记录唯一标识+任务提交柜员ID查询可撤销流程实例列表（只能是提交柜员，查询授权流程没有走完的）
     */
	@Validation(value="p0000609")
	@Override
	public ResponseEntity queryCancelProcessInstanceList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException{
    	try{
    		
 			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编号
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人记录唯一标识
 			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//柜员编号
 			if(ValidateUtil.isEmpty(tellerNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0039,HeadParameterDefinitionConstants.REC_TRANTELLERNO+"不能为空");
 			}
 			
 			//当前页数
 			int pageNo = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")))-1;
 			//每页显示记录数
 			String pageSizeStr = String.valueOf(bodyMap.get("pageSize")==null?BaseParamsConstants.SYS_DEFAULT_PAGESIZE:bodyMap.get("pageSize"));
 			int pageSize = Integer.parseInt(pageSizeStr);
 			
 			Map<String, Object> resultMap = new HashMap<String, Object>();
 			
 			//根据柜员号和法人ID查询柜员信息
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
			if(tellerMap != null && tellerMap.size()>0){
				String tellerId = (String) tellerMap.get("userId");//柜员id
				String tellerName = String.valueOf(tellerMap.get("tellerName"));//柜员名称
				//查询可撤销流程实例列表
   	    		List<ProcessInstance> processList = inSideActivitiRuntimeService.qryProcessInstanceListBySubmitUserId(legalId, tellerId, pageNo*pageSize, pageSize);
   	    		List<Map<String, Object>> processInstanceList = new ArrayList<Map<String, Object>>();
   	    		if(processList != null && processList.size()>0){
   	    			for(ProcessInstance processInstance : processList){
   	    				String processInstanceId = processInstance.getProcessInstanceId();//流程实例id
   	    				List<String> variableNames = new ArrayList<String>();
   	   					variableNames.add("businessKey");                           //业务流水号
   	   					variableNames.add("bsnCode");                               //业务编号
   	   					Map<String,Object> varsMap = inSideActivitiTaskService.getVariableInstanceValue(processInstanceId, variableNames);
   	   					
   	   					Map<String, Object> hisProcessInstance = new HashMap<String, Object>();
   	   					hisProcessInstance.put("processInstanceId", processInstanceId);
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
	 					hisProcessInstance.put("submitTellerId", tellerNo);               //提交柜员编码
	 					hisProcessInstance.put("submitTellerName", tellerName);			  //提交柜员名称
	 					Map<String,Object> tradeStateMap = authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey, "","","",channel);
   	 				    if(tradeStateMap != null && tradeStateMap.size()>0){
   	 				    	hisProcessInstance.put("createTime", String.valueOf(tradeStateMap.get("transStartTime")));//制单时间	
   	 				    }
   	 				    processInstanceList.add(hisProcessInstance);
   	    			}
   	    		}
   	    		
   	    	    //查可撤销流程实例总记录数
				long totalNum = inSideActivitiRuntimeService.qryProcessInstanceTotalNum(legalId, tellerId);
				//总页数
   				int totalPage = DataUtil.getTotalPage((int)totalNum, pageSize);
   				
   				resultMap.put("cancleProcessInstanceList", processInstanceList);
   	            resultMap.put("totalNum", totalNum);
   	            resultMap.put("totalPage", totalPage);
			}else{
				throw new ServiceException(AuthErrorCodeConstants.PAUH0040,"柜员信息不存在");
			}
 			return new ResponseEntity(resultMap);
    	}catch(ServiceException e){
			throw e;
		}catch(Exception ex){
			throw new ServiceException(AuthErrorCodeConstants.PAUH0021, "查询可撤销流程列表失败", ex);
		}
    }
    
    
    /**
     * 根据法人记录唯一标识+任务提交柜员ID+30位全局流水号撤销授权申请
     */
	@Validation(value="p0000610")
	@Override
    public ResponseEntity cancelProcessInstance(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
    	try{
 			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编号
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人记录唯一标识
 			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//柜员编号
 			if(ValidateUtil.isEmpty(tellerNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0039,HeadParameterDefinitionConstants.REC_TRANTELLERNO+"不能为空");
 			}
 			
 			String businessKey = String.valueOf(bodyMap.get("businessKey"));//30位全局流水号
 			String revokeReason = String.valueOf(bodyMap.get("revokeReason"));//交易撤销原因
 			if(ValidateUtil.isEmpty(revokeReason)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0038,"revokeReason不能为空");
 			}
    		
 			//根据柜员号和法人ID查询柜员信息
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
			if(tellerMap != null && tellerMap.size()>0){
				String tellerId = (String) tellerMap.get("userId");//柜员id
				ProcessInstance pi = inSideActivitiRuntimeService.qryProcessInstanceByBusinessKey(legalId, tellerId, businessKey);
				if(pi == null){
					throw new ServiceException(AuthErrorCodeConstants.PAUH0022, "流程实例不存在或状态不正常");
				}
				//查询流程变量
   	 			String processInstanceId = pi.getProcessInstanceId();//流程实例id
   	 			List<String> variableNames = new ArrayList<String>();
   	 		    variableNames.add("bsnCode");                               //业务编号
   	 		    Map<String,Object> varsMap = inSideActivitiTaskService.getVariableInstanceValue(processInstanceId, variableNames);
   	 			
   	 		    
   	 		    //查询流程实例对应的运行时任务
   	 			List<Map<String, Object>> taskList = inSideAuthCompositeService.getTaskListByBusinessKeyAndSubmitUserId(legalId, tellerId, businessKey);
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
	 					inSideAuthCompositeService.qryHistoricTaskInstanceList(legalId, tellerId, businessKey);
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
	 			inSideActivitiRuntimeService.delProcessInstanceById(processInstanceId, message);
	 			//根据流程实例记录数判断删除是否成功
	 			if(varsMap != null && varsMap.size()>0){
	 				String bsnCode = String.valueOf(varsMap.get("bsnCode"));
	 				if(!ValidateUtil.isEmpty(bsnCode)){
	 					//更新交易状态
	 		 			authBusinessFlowService.queryMainFlowInfoByGbFlowSeq(bsnCode, businessKey,"",TradeStatusParamsConstants.TRADE_STATE_12,revokeReason,channel);
	 				}
	 			}
			}else{
				throw new ServiceException(AuthErrorCodeConstants.PAUH0040,"柜员信息不存在");
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
	@Validation(value="p0000611")
	@Override
	public ResponseEntity queryAuthHistoryList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException {
		try{
 			String channel = (String)headMap.get(HeadParameterDefinitionConstants.REC_CHANNEL);//渠道编号
 			String legalId = (String)headMap.get(HeadParameterDefinitionConstants.REC_LEGALID);//法人记录唯一标识
 			String tellerNo = (String) headMap.get(HeadParameterDefinitionConstants.REC_TRANTELLERNO);//柜员编号
 			if(ValidateUtil.isEmpty(tellerNo)){
 				throw new ServiceException(AuthErrorCodeConstants.PAUH0039,HeadParameterDefinitionConstants.REC_TRANTELLERNO+"不能为空");
 			}
 			
 			String bsnType = String.valueOf(bodyMap.get("bsnType"));//业务类型(为空查所有业务分类)
 			String operateType = String.valueOf(bodyMap.get("operateType"));//操作类型 01：本人经办；02：本人授权（流程已完成）；03：本人授权（流程未完成）；
 			String beginDate = String.valueOf(bodyMap.get("beginDate"));//开始日期
			String endDate = String.valueOf(bodyMap.get("endDate")); //结束日期
			int pageNo = Integer.parseInt(String.valueOf(bodyMap.get("pageNo")));  //当前页数
			String pageSizeStr = String.valueOf(bodyMap.get("pageSize")==null?BaseParamsConstants.SYS_DEFAULT_PAGESIZE:bodyMap.get("pageSize"));//每页显示记录数
			int pageSize = Integer.parseInt(pageSizeStr);          
			String sortType = (String) (bodyMap.get("sortType")==null?"1":bodyMap.get("sortType")); //结果排序方式 0:ASC；1:DESC                     
			if(!ValidateUtil.isEmpty(beginDate)){
				beginDate = DateUtil.formatDate(beginDate, DateUtil.DEFAULT_DATE_FORMAT) + "000000";
			}
			if(!ValidateUtil.isEmpty(endDate)){
				endDate = DateUtil.formatDate(endDate, DateUtil.DEFAULT_DATE_FORMAT) + "235959";
			}
			if(ValidateUtil.isEmpty(bsnType)){
				bsnType = "";
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<Map<String,Object>> historyList = new ArrayList<Map<String,Object>>();
			
			//根据柜员号和法人ID查询柜员信息
			Map<String, Object> tellerMap = qryInnerUserService.qryTellerIdByTellCode(legalId, tellerNo);
   			if(null!=tellerMap && tellerMap.size()>0){
   				String tellerId = (String) tellerMap.get("userId");//柜员id
   			    
   				if(!ValidateUtil.isEmpty(operateType) && operateType.equals(AuthParamsConstants.OPERATE_TYPE_1)){//1：经办
   					historyList = inSideAuthCompositeService.qryAuthHistoryList(legalId, tellerId, bsnType, beginDate, endDate, sortType, pageNo, pageSize);
   				}else if(!ValidateUtil.isEmpty(operateType) && operateType.equals(AuthParamsConstants.OPERATE_TYPE_2)){//2：授权，授权流程已完成
   					historyList = inSideAuthCompositeService.qryFinishedHisProcInstListByUserId(tellerId, bsnType, beginDate, endDate, sortType, pageNo, pageSize);
   				}else if(!ValidateUtil.isEmpty(operateType) && operateType.equals(AuthParamsConstants.OPERATE_TYPE_3) ){//3：待办，授权流程未完成
   					historyList = inSideAuthCompositeService.qryDoingHisProcInstListByUserId(tellerId, bsnType, beginDate, endDate, sortType, pageNo, pageSize);
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
   					
   					Map<String, Object> varsMap = inSideActivitiHistoryService.getHistoryValues(processInstanceId, variableNames);
   					if(null==varsMap || varsMap.size()==0){
   						varsMap = inSideActivitiHistoryService.getVariableInstanceValue(processInstanceId, variableNames);
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
   					Map<String, Object> userInfoMap = qryInnerUserService.qryTellerInfoByOneUserId(legalId, submitUserId);
 					String submitTellerId = "";//柜员编号
 					String submitTellerName = "";//柜员名称
 		   			if(null!=userInfoMap && userInfoMap.size()>0){
 		   			submitTellerId = String.valueOf(userInfoMap.get("tellerCode"));
 		   				submitTellerName = String.valueOf(userInfoMap.get("tellerName"));
 		   			}
 		   		    hisProcessInstance.put("submitTellerId", submitTellerId);                   //柜员编号
					hisProcessInstance.put("submitTellerName", submitTellerName);               //柜员名称
					
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
   				
   				
   				if( !ValidateUtil.isEmpty(operateType) && operateType.equals(AuthParamsConstants.OPERATE_TYPE_1)){//1：经办
   					totalNum = inSideActivitiHistoryService.qryHistoricProcessInstanceTotalNum(legalId, tellerId, bsnType, beginDate, endDate);
   				}else if( !ValidateUtil.isEmpty(operateType) && operateType.equals(AuthParamsConstants.OPERATE_TYPE_2)){//2：授权，授权流程已完成
   					totalNum = inSideAuthCompositeService.qryFinishedHisProcInstListTotalNumByUserId(tellerId, bsnType, beginDate, endDate);
   				}else if( !ValidateUtil.isEmpty(operateType) && operateType.equals(AuthParamsConstants.OPERATE_TYPE_3)){//3：待办，授权流程未完成
   					totalNum = inSideAuthCompositeService.qryDoingHisProcInstListTotalNumByUserId(tellerId, bsnType, beginDate, endDate);
   				}	
   				
   				int totalPage = DataUtil.getTotalPage((int)totalNum, pageSize);
   				resultMap.put("inSideHisTaskList", processInstanceList);
   				resultMap.put("totalNum", String.valueOf(totalNum));
   				resultMap.put("totalPage", String.valueOf(totalPage));
   			}else{
   				throw new ServiceException(AuthErrorCodeConstants.PAUH0040,"柜员信息不存在");
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
	@Validation(value="p0000622")
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
	@Validation(value="p0000623")
	@Override
	public ResponseEntity queryAuthProcessNodeDetail(Map<String, Object> headMap, Map<String, Object> bodyMap)throws ServiceException {
		String taskId = String.valueOf(bodyMap.get("taskId"));//任务id
		List<Map<String,Object>> list = authShowService.getAllNodePosition(taskId, "1");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("positionDetailList", list);
		return new ResponseEntity(resultMap);
	}
}
