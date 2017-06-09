package bros.c.auth.facade.service;

import java.util.Map;

import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: ICOutSideAuthSerivceFacade 
 * @Description: 对客授权任务中心对外发布服务接口
 * @author 何鹏
 * @date 2016年9月7日 上午11:16:21 
 * @version 1.0
 */
public interface ICOutSideAuthSerivceFacade {
	/**
	 * 
	 * @Title: qryTaskCenterView 
	 * @Description: 交易类型待授权指令概览统计
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity qryTaskCenterView(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * @Title: qryTaskCenterViewByBsnType 
	 * @Description: 业务编码待授权指令概览统计
	 * @param headMap 报文头域
	 * @param bodyMap 报文体域
	 * @return
	 * @throws ServiceException
	 */
	public ResponseEntity qryTaskCenterViewByBsnType(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: qryAuthQueueList 
	 * @Description: 查询待授权指令列表
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity qryAuthQueueList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
   	 * 
   	 * @Title: unClaimTask 
   	 * @Description: 签收任务退回（暂时未使用）
   	 * @param headMap 报文头
   	 * @param bodyMap 报文体
   	 * @return ResponseEntity 返回结果
   	 * @throws ServiceException
   	 */
	public ResponseEntity unClaimTaskJob(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	 /**
     * 
     * @Title: singleCompleteTask 
     * @Description: 单笔授权
     * @param headMap 报文头
     * @param bodyMap 报文体
     * @return ResponseEntity 返回结果
     * @throws ServiceException
     */
   	public ResponseEntity singleCompleteTask(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
   	
   	/**
     * 
     * @Title: batchCompleteTask 
     * @Description: 批量授权
     * @param headMap 报文头
     * @param bodyMap 报文体
     * @return ResponseEntity 返回结果
     * @throws ServiceException
     */
   	public ResponseEntity batchCompleteTask(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
   	/**
	 * 
	 * @Title: qryOrderDetailListByBatchNo 
	 * @Description: 查询指令详情列表（用于凭证打印））
	 * @param headMap 报文头域
	 * @param bodyMap 报文体域
	 * @throws ServiceException
	 */
    public ResponseEntity qryOrderDetailListByBatchNo(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
     * @Title: qryCommentListByTaskId
     * @Description: 根据业务流水号（businessKey）查询审批意见历史信息
     * @param headMap 报文头域
	 * @param bodyMap 报文体域
     * @throws ServiceException
     */
    public ResponseEntity qryCommentListByTaskId(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
    
    /**
	 * @Title: qryAuthDetailPageDataByBusinessKey 
	 * @Description: 根据业务流水号（指令流水号）+业务编号查询授权详情页面展示数据
	 * @param headMap 报文头域
	 * @param bodyMap 报文体域
	 * @throws ServiceException
	 */
    public ResponseEntity qryAuthDetailPageDataByBusinessKey(Map<String, Object> headMap,Map<String, Object> bodyMap) throws ServiceException;
  
	/**
	 * 
	 * @Title: queryCancelProcessInstanceList 
	 * @Description: 根据法人记录唯一标识+客户编号+任务提交人操作员ID查询可撤销流程实例列表
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
	public ResponseEntity queryCancelProcessInstanceList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: cancelProcessInstance 
	 * @Description: 根据法人记录唯一标识+客户编号+任务提交人操作员ID+30位全局流水号撤销授权申请
	 * @param headMap 报文头
	 * @param bodyMap 报文体
	 * @return ResponseEntity 返回结果
	 * @throws ServiceException
	 */
    public ResponseEntity cancelProcessInstance(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
    
    /**
     * 
     * @Title: queryAuthHistoryList 
     * @Description: 根据条件，查询授权历史信息列表(已办事宜列表查询)
     * @param headMap 报文头
     * @param bodyMap 报文体
     * @return ResponseEntity 返回结果
     * @throws ServiceException
     */
	public ResponseEntity queryAuthHistoryList(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
	/**
     * 
     * @Title: addAuthProcessPhotoToFtpServer 
     * @Description: 显示流程图生成，上传到ftp服务器
     * @param headMap
     * @param bodyMap
     * @return
     * @throws ServiceException
     */
    public ResponseEntity addAuthProcessPhotoToFtpServer(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
    /**
     * 
     * @Title: queryAuthProcessNodeDetail 
     * @Description: 查询授权流程节点坐标和明细信息
     * @param headMap
     * @param bodyMap
     * @return
     * @throws ServiceException
     */
    public ResponseEntity queryAuthProcessNodeDetail(Map<String, Object> headMap, Map<String, Object> bodyMap) throws ServiceException;
}
