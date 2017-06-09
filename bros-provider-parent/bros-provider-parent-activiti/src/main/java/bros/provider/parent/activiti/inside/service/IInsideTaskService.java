package bros.provider.parent.activiti.inside.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IInsideAuthCenterService 
 * @Description: 内部系统授权任务服务接口
 * @author weiyancheng
 * @date 2016年7月19日 下午1:15:38 
 * @version 1.0 
 */
public interface IInsideTaskService {

	/**
	 * 
	 * @Title: getTaskListByUserId 
	 * @Description: 根据法人记录编号+用户编号查询已签收待处理任务列表
	 * @param legalId 法人记录id
	 * @param userId  用户id
	 * @param firstResult 起始条数
	 * @param maxResults 结束条数
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> getTaskListByUserId(String legalId, String userId, int firstResult, int maxResults) throws ServiceException;
	/**
	 * 
	 * @Title: queryClaimTaskTotalNum 
	 * @Description: 根据法人记录编号+任务办理人查询已签收任务总记录数
	 * @param legalId 法人id
	 * @param userId 用户id
	 * @return
	 * @throws ServiceException
	 */
	public long queryClaimTaskTotalNum(String legalId, String userId) throws ServiceException;
	/**
	 * 
	 * @Title: getTaskByUserIdAndTaskId 
	 * @Description: 根据法人记录编号+用户编号+任务ID查询已签收待处理任务
	 * @param legalId 法人id
	 * @param userId 用户id
	 * @param taskId 任务id
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> getTaskByUserIdAndTaskId(String legalId, String userId, String taskId)throws ServiceException;
	/**
	 * @Title: queryAuthQueueTaskList 
	 * @Description: 根据法人记录编号+用户编号+业务编号分页查询待签收和已签收待处理任务列表
	 * @param legalId 法人id
	 * @param userId 用户id
	 * @param bsnCode 业务编号
	 * @param firstResult 起始记录数
	 * @param maxResults  结束记录数
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryAuthQueueTaskList(String legalId, String userId, String bsnCode, 
			int firstResult, int maxResults) throws ServiceException;
	/**
	 * @Title: queryAuthQueueTaskTotalNum 
	 * @Description: 根据法人记录编号+用户编号+业务编号查询待签收和已签收待处理任务总记录数
	 * @param legalId 法人id
	 * @param userId 用户id
	 * @param bsnCode 业务编号
	 * @return
	 * @throws ServiceException
	 */
	public long queryAuthQueueTaskTotalNum(String legalId, String userId, String bsnCode) throws ServiceException;
	
	/**
	 * 
	 * @Title: qryWaitClaimTaskViewList 
	 * @Description: 根据操作柜员编号（签收人不能是任务提交人）+业务类型统计待签收笔数信息列表
	 * @param legalId 法人ID
	 * @param userId 用户ID
	 * @param bsnType 业务类型
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryWaitClaimTaskViewList(String legalId, String userId, String bsnType) throws ServiceException;
	/**
	 * @Title: qryClaimEdTaskViewList 
	 * @Description: 根据操作柜员编号（签收人不能是任务提交人）+业务编号统计已签收笔数信息列表
	 * @param legalId 法人ID
	 * @param userId 用户ID
	 * @param bsnType 业务类型
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryClaimEdTaskViewList(String legalId, String userId, String bsnType) throws ServiceException;
	/**
	 * 
	 * @Title: qryWaitClaimTaskOverViewList 
	 * @Description: 根据法人ID，用户ID查询待签收指令概览
	 * @param legalId 法人ID
	 * @param userId 用户ID
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryWaitClaimTaskOverViewList(String legalId, String userId) throws ServiceException;
	/**
	 * @Title: qryClaimEdTaskOverViewList 
	 * @Description: 根据法人ID，用户ID查询已签收指令概览
	 * @param legalId 法人ID
	 * @param userId 用户ID
	 * @param bsnType 业务类型
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> qryClaimEdTaskOverViewList(String legalId, String userId) throws ServiceException;
}
