package bros.provider.parent.activiti.inside.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IInsideAuthService 
 * @Description: 内部授权服务接口
 * @author weiyancheng
 * @date 2016年7月22日 下午4:51:36 
 * @version 1.0 
 */
public interface IInsideAuthService {

	/**
	 * 
	 * @Title: tranQryTaskCenterView 
	 * @Description: 待授权指令概览统计
	 * @param param 参数
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> tranQryTaskCenterView(Map<String, Object> param) throws ServiceException;
	/**
	 * 
	 * @Title: tranQryTaskCenterView 
	 * @Description: 根据业务类型查询待授权指令概览统计
	 * @param param 参数
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> tranQryTaskCenterViewByBsnType(Map<String, Object> param) throws ServiceException;

	/**
	 * @Title: tranQryAuthQueueList 
	 * @Description: 根据业务编号查询待授权指令列表
	 * @param legalId 法人ID
	 * @param userId 用户ID
	 * @param bsnCode 业务编号
	 * @param firstResult 起始条数
	 * @param maxResults  结束条数
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> tranQryAuthQueueList(String legalId,String userId,String bsnCode,int firstResult,int maxResults)throws ServiceException;

	
	/**
	 * @Title: tranUnClaimTaskJob 
	 * @Description: 退回任务
	 * @param userId 用户ID
	 * @param taskId 任务ID
	 * @throws ServiceException
	 */
	public void tranUnClaimTaskJob(String userId,String taskId) throws ServiceException;
	
	/**
	 * @Title: tranQryOrderDetailByBusinessKey 
	 * @Description: 根据业务流水号（指令流水号）+业务编号查询指令详情
	 * @param businessKey 业务流水号
	 * @param bsnCode     业务编号
	 * @param channel     渠道
	 * @throws ServiceException
	 */
    public Map<String, Object> tranQryOrderDetailByBusinessKey(String businessKey,String bsnCode,String channel) throws ServiceException;
	
    /**
     * 
     * @Title: tranSingleCompleteTask 
     * @Description: 单笔授权（临柜）
     * @param context 交易上下文
     * @throws ServiceException
     */
	public Map<String, Object> tranSingleCompleteTask(Map<String,Object> param) throws ServiceException;
	
	/**
	 * 
	 * @Title: tranAuthSingleCompleteTask 
	 * @Description: 单笔授权（审核式）
	 * @param context 交易上下文
	 * @throws ServiceException
	 */
	public Map<String, Object> tranAuthSingleCompleteTask(Map<String,Object> param) throws ServiceException;
	
	/**
	 * 
	 * @Title: tranBatchCompleteTask 
	 * @Description: 批量授权
	 * @param context 交易上下文
	 * @throws ServiceException
	 */
	public Map<String, Object> tranBatchCompleteTask(Map<String,Object> param) throws ServiceException;
	
	/**
	 * 
	 * @Title: tranQryOrderDetailListByBatchNo 
	 * @Description: 根据批次号查询指令详情列表（用于凭证打印）
	 * @param bsnCode 业务编号
	 * @param batchNo 批次号
	 * @param channel 渠道
	 * @throws ServiceException
	 */
    public List<Map<String, Object>> tranQryOrderDetailListByBatchNo(String bsnCode,String batchNo,String channel) throws ServiceException;
    /**
     * @Title: tranQryCommentListByTaskId
     * @Description: 根据任务ID查询审批意见历史信息
     * @param taskId 任务ID
     * @throws ServiceException
     */
    public List<Map<String, Object>> tranQryCommentListByTaskId(String taskId) throws ServiceException;
}
