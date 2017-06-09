/**   
 * @Title: InsideTaskServiceImpl.java 
 * @Package bros.provider.parent.activiti.inside.service.impl 
 * @Description: 用一句话描述该文件做什么 
 * @author weiyancheng
 * @date 2016年7月25日 上午11:13:10 
 * @version 1.0   
 */
package bros.provider.parent.activiti.inside.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.common.core.activiti.constants.ActivitiParamsConstants;
import bros.common.core.activiti.inside.service.IInSideActivitiTaskService;
import bros.common.core.constants.HeadParameterDefinitionConstants;
import bros.common.core.db.IMyBatisDaoSysDao;
import bros.common.core.exception.ServiceException;
import bros.provider.parent.activiti.constants.ActivitiErrorCodeConstants;
import bros.provider.parent.activiti.inside.service.IInsideTaskService;

/** 
 * @ClassName: InsideTaskServiceImpl 
 * @Description: 内部系统授权任务服务实现
 * @author weiyancheng
 * @date 2016年7月25日 上午11:13:10 
 * @version 1.0 
 */
@Component("insideTaskService")
public class InsideTaskServiceImpl implements IInsideTaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(InsideTaskServiceImpl.class);
	
	@Autowired
	private IInSideActivitiTaskService inSideActivitiTaskService;
	
	/**
	 * 数据库操作服务
	 */
	@Autowired
	private IMyBatisDaoSysDao activitiMyBatisDaoSysDao;

	/** 
	 * 根据法人记录编号+用户编号查询已签收待处理任务列表
	 */
	@Override
	public List<Map<String, Object>> getTaskListByUserId(String legalId,
			String userId, int firstResult, int maxResults)
			throws ServiceException {
		try{
			List<Task> tasks = inSideActivitiTaskService.getTaskListByUserId(legalId, userId, firstResult, maxResults);
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			if(null!=tasks && tasks.size()>0){
				for(Task task:tasks){
					Map<String, Object> taskMap = new HashMap<String, Object>();
					taskMap.put("taskId", task.getId());
					resultList.add(taskMap);
				}
			}
			return resultList;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".getTaskListByUserId");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".getTaskListByUserId");
			throw new ServiceException(e);
		}
	}

	/** 
	 * 根据法人记录编号+任务办理人查询已签收任务总记录数
	 */
	@Override
	public long queryClaimTaskTotalNum(String legalId, String userId)
			throws ServiceException {
		try{
			long num = inSideActivitiTaskService.queryClaimTaskTotalNum(legalId, userId);
			return num;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".queryClaimTaskTotalNum");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".queryClaimTaskTotalNum");
			throw new ServiceException(e);
		}
	}

	/** 
	 * 根据法人记录编号+用户编号+任务ID查询已签收待处理任务
	 */
	@Override
	public Map<String, Object> getTaskByUserIdAndTaskId(String legalId,
			String userId, String taskId) throws ServiceException {
		try{
			Task task = inSideActivitiTaskService.getTaskByUserIdAndTaskId(legalId, userId, taskId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(null!=task){
				resultMap.put("taskId", task.getId());
			}
			return resultMap;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".getTaskByUserIdAndTaskId");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".getTaskByUserIdAndTaskId");
			throw new ServiceException(e);
		}
	}

	/** 
	 * 根据法人记录编号+用户编号+业务编号分页查询待签收和已签收待处理任务列表
	 */
	@Override
	public List<Map<String, Object>> queryAuthQueueTaskList(String legalId,
			String userId, String bsnCode, int firstResult, int maxResults)
			throws ServiceException {
		try{
			List<Task> tasks = inSideActivitiTaskService.queryAuthQueueTaskList(legalId, userId, bsnCode, firstResult, maxResults);
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			if(null!=tasks && tasks.size()>0){
				for(Task task:tasks){
					Map<String, Object> taskMap = new HashMap<String, Object>();
					taskMap.put("taskId", task.getId());
					taskMap.put("name",task.getName());
					taskMap.put("owner",task.getOwner());
					taskMap.put("assignee",task.getAssignee());
					taskMap.put("createTime",task.getCreateTime());
	 				taskMap.put("description",task.getDescription());
	 				taskMap.put("dueDate",task.getDueDate());
	 				taskMap.put("executionId",task.getExecutionId());
	 				taskMap.put("parentTaskId",task.getParentTaskId());
	 				taskMap.put("priority",task.getPriority());
	 				taskMap.put("processDefinitionId",task.getProcessDefinitionId());
	 				taskMap.put("processInstanceId",task.getProcessInstanceId());
	 				//取得流程启动时存储的交易信息数据
	 				taskMap.put("processVariables",task.getProcessVariables());
	 				taskMap.put("taskDefinitionKey",task.getTaskDefinitionKey());
	 				taskMap.put("taskLocalVariables",task.getTaskLocalVariables());
					resultList.add(taskMap);
				}
			}
			return resultList;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".queryAuthQueueTaskList");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".queryAuthQueueTaskList");
			throw new ServiceException(e);
		}
	}

	/** 
	 * 根据法人记录编号+用户编号+业务编号查询待签收和已签收待处理任务总记录数
	 */
	@Override
	public long queryAuthQueueTaskTotalNum(String legalId, String userId,
			String bsnCode) throws ServiceException {
		try{
			long num = inSideActivitiTaskService.queryAuthQueueTaskTotalNum(legalId, userId, bsnCode);
			return num;
		}catch (ServiceException se) {
			logger.error("Exception from " + this.getClass() + ".queryAuthQueueTaskTotalNum");
			throw se;
		} catch (Exception e) {
			logger.error("Exception from " + this.getClass() + ".queryAuthQueueTaskTotalNum");
			throw new ServiceException(e);
		}
	}

	/** 
	 * 根据操作柜员ID+法人ID+业务类型编号统计待签收笔数信息列表
	 */
	@Override
	public List<Map<String, Object>> qryWaitClaimTaskViewList(String legalId,
			String userId, String bsnType) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			//操作柜员编号
			parmIN.put("userId", userId);
			//业务类型
			parmIN.put("bsnType", bsnType);
			//任务状态
			parmIN.put("state", 1);  //激活
			//业务编号在流程变量中保存的名称
			parmIN.put("bsnCodeFieldName", HeadParameterDefinitionConstants.REC_BSNCODE); 
			//任务提交人操作柜员编号在流程变量中保存的名称
			parmIN.put("submitUserIdFieldName", ActivitiParamsConstants.FIELD_SUBMIT_USERID);
			//授权类型在流程变量中保存的名称
			parmIN.put("authShapFieldName", ActivitiParamsConstants.ACT_FIELD_AUTHSHAP);
			//授权类型 0-审核式
			parmIN.put("authShape", ActivitiParamsConstants.ACT_AUTHSHAP_0);
			//业务类型在流程变量中保存的名称
			parmIN.put("bsnTypeFieldName", "bsnType");
			List<Map<String, Object>> resultList = 
					(List<Map<String, Object>>)activitiMyBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.actrutask.qryWaitClaimTaskViewListByCondition", parmIN);
			
			return resultList;
		}catch(ServiceException e){
			logger.error("Exception from" + this.getClass().getName() + "'s " + "qryWaitClaimTaskViewList method.", e);
			throw new ServiceException(e);
		}catch(Exception ex){
			logger.error("Exception from" + this.getClass().getName() + "'s " + "qryWaitClaimTaskViewList method.", ex);
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0026,"查询待授权指令失败");
		}
	}

	/** 
	 * 根据用户ID+法人ID+业务编号统计已签收笔数信息列表
	 */
	@Override
	public List<Map<String, Object>> qryClaimEdTaskViewList(String legalId,
			String userId, String bsnType) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			//法人ID
			parmIN.put("legalId", legalId);
			//操作员ID
			parmIN.put("userId", userId);
			//业务类型
			parmIN.put("bsnType", bsnType);
			//任务状态
			parmIN.put("state", 1);  //激活
			//业务编号在流程变量中保存的名称
			parmIN.put("bsnCodeFieldName", HeadParameterDefinitionConstants.REC_BSNCODE); 
			//授权类型在流程变量中保存的名称
			parmIN.put("authShapFieldName", ActivitiParamsConstants.ACT_FIELD_AUTHSHAP);
			//授权类型 0-审核式
			parmIN.put("authShape", ActivitiParamsConstants.ACT_AUTHSHAP_0);
			//法人ID在流程变量中保存的名称
			parmIN.put("legalIdFieldName", HeadParameterDefinitionConstants.REC_LEGALID);
			//业务类型在流程变量中保存的名称
			parmIN.put("bsnTypeFieldName", "bsnType");
			List<Map<String, Object>> resultList = 
					(List<Map<String, Object>>)activitiMyBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.actrutask.qryClaimEdTaskViewListByCondition", parmIN);
			
			return resultList;
		}catch(ServiceException e){
			logger.error("Exception from" + this.getClass().getName() + "'s " + "qryClaimEdTaskViewList method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from" + this.getClass().getName() + "'s " + "qryClaimEdTaskViewList method.", ex);
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0026,"查询待授权指令失败");
		}
	}

	/** 
	 * 根据法人ID，用户ID查询待签收指令概览
	 */
	@Override
	public List<Map<String, Object>> qryWaitClaimTaskOverViewList(
			String legalId, String userId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			//用户ID
			parmIN.put("userId", userId);
			//法人ID
			parmIN.put("legalId", legalId);
			//业务类型字段域名称
			parmIN.put("bsnTypeFieldName", ActivitiParamsConstants.FIELD_BSNTYPE);
			//任务状态
			parmIN.put("state", 1);  //激活
			//法人id字段域名称
			parmIN.put("legalIdFieldName", HeadParameterDefinitionConstants.REC_LEGALID);
			//授权形式字段域名称
			parmIN.put("authShapeFiledName", ActivitiParamsConstants.ACT_FIELD_AUTHSHAP);
			//授权类型 0-审核式
			parmIN.put("authShape", ActivitiParamsConstants.ACT_AUTHSHAP_0);
			//指令提交人字段域名称
			parmIN.put("submitTellerNoField", ActivitiParamsConstants.FIELD_SUBMIT_USERID);
			
			List<Map<String, Object>> resultList = 
					(List<Map<String, Object>>)activitiMyBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.actrutask.qryWaitClaimTaskOverViewListByCondition", parmIN);
			
			return resultList;
		}catch(ServiceException e){
			logger.error("Exception from" + this.getClass().getName() + "'s " + "qryWaitClaimTaskOverViewList method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from" + this.getClass().getName() + "'s " + "qryWaitClaimTaskOverViewList method.", ex);
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0027,"查询待授权指令概览失败");
		}
	}

	/** 
	 * 根据法人ID，用户ID查询已签收指令概览
	 */
	@Override
	public List<Map<String, Object>> qryClaimEdTaskOverViewList(String legalId,
			String userId) throws ServiceException {
		try{
			Map<String, Object> parmIN = new HashMap<String, Object>();
			//用户ID
			parmIN.put("userId", userId);
			//法人ID
			parmIN.put("legalId", legalId);
			//业务类型字段域名称
			parmIN.put("bsnTypeFieldName", ActivitiParamsConstants.FIELD_BSNTYPE);
			//任务状态
			parmIN.put("state", 1);  //激活
			//法人id字段域名称
			parmIN.put("legalIdFieldName", HeadParameterDefinitionConstants.REC_LEGALID);
			//授权形式字段域名称
			parmIN.put("authShapeFieldName", ActivitiParamsConstants.ACT_FIELD_AUTHSHAP);
			//授权类型 0-审核式
			parmIN.put("authShape", ActivitiParamsConstants.ACT_AUTHSHAP_0);
			
			List<Map<String, Object>> resultList = 
					(List<Map<String, Object>>)activitiMyBatisDaoSysDao.selectList("mybatis.mapper.activiti.inside.table.actrutask.qryClaimEdTaskOverViewListByCondition", parmIN);
			
			return resultList;
		}catch(ServiceException e){
			logger.error("Exception from" + this.getClass().getName() + "'s " + "qryClaimEdTaskOverViewList method.", e);
			throw e;
		}catch(Exception ex){
			logger.error("Exception from" + this.getClass().getName() + "'s " + "qryClaimEdTaskOverViewList method.", ex);
			throw new ServiceException(ActivitiErrorCodeConstants.PPAI0027,"查询待授权指令概览失败");
		}
	}

}
