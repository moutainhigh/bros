package bros.c.auth.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.auth.facade.service.ICOutSideAuthSerivceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.auth.facade.service.IPOutSideAuthSerivceFacade;

/**
 * 
 * @ClassName: COutSideAuthSerivceFacadeImpl 
 * @Description: 对客授权任务中心对外发布服务接口实现
 * @author 何鹏
 * @date 2016年9月7日 上午11:16:21 
 * @version 1.0
 */
@Component("coutSideAuthSerivceFacade")
public class COutSideAuthSerivceFacadeImpl implements ICOutSideAuthSerivceFacade {

	@Autowired
	private IPOutSideAuthSerivceFacade poutSideAuthServiceFacade;

	/**
	 * 交易类型待授权指令概览统计
	 */
	@Override
	public ResponseEntity qryTaskCenterView(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return poutSideAuthServiceFacade.qryTaskCenterView(headMap, bodyMap);
	}

	/**
	 * 业务编码待授权指令概览统计
	 */
	@Override
	public ResponseEntity qryTaskCenterViewByBsnType(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return poutSideAuthServiceFacade.qryTaskCenterViewByBsnType(headMap, bodyMap);
	}

	/**
	 * 查询待授权指令列表
	 */
	@Validation(value="c0000612")
	@Override
	public ResponseEntity qryAuthQueueList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return poutSideAuthServiceFacade.qryAuthQueueList(headMap, bodyMap);
	}

	/**
	 * 签收任务退回（暂时未使用）
	 */
	@Validation(value="c0000613")
	@Override
	public ResponseEntity unClaimTaskJob(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return poutSideAuthServiceFacade.unClaimTaskJob(headMap, bodyMap);
	}

	/**
	 * 单笔授权
	 */
	@Validation(value="c0000614")
	@Override
	public ResponseEntity singleCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return poutSideAuthServiceFacade.singleCompleteTask(headMap, bodyMap);
	}

	/**
	 * 批量授权
	 */
	@Validation(value="c0000615")
	@Override
	public ResponseEntity batchCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return poutSideAuthServiceFacade.batchCompleteTask(headMap, bodyMap);
	}

	/**
	 * 查询指令详情列表（用于凭证打印））
	 */
	@Validation(value="c0000616")
	@Override
	public ResponseEntity qryOrderDetailListByBatchNo(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return poutSideAuthServiceFacade.qryOrderDetailListByBatchNo(headMap, bodyMap);
	}

	/**
	 * 根据业务流水号（businessKey）查询审批意见历史信息
	 */
	@Validation(value="c0000617")
	@Override
	public ResponseEntity qryCommentListByTaskId(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return poutSideAuthServiceFacade.qryCommentListByTaskId(headMap, bodyMap);
	}

	/**
	 * 根据业务流水号（指令流水号）+业务编号查询授权详情页面展示数据
	 */
	@Validation(value="c0000618")
	@Override
	public ResponseEntity qryAuthDetailPageDataByBusinessKey(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return poutSideAuthServiceFacade.qryAuthDetailPageDataByBusinessKey(headMap, bodyMap);
	}

	/**
	 * 根据法人记录唯一标识+客户编号+任务提交人操作员ID查询可撤销流程实例列表
	 */
	@Validation(value="c0000619")
	@Override
	public ResponseEntity queryCancelProcessInstanceList(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return poutSideAuthServiceFacade.queryCancelProcessInstanceList(headMap, bodyMap);
	}

	/**
	 * 根据法人记录唯一标识+客户编号+任务提交人操作员ID+30位全局流水号撤销授权申请
	 */
	@Validation(value="c0000620")
	@Override
	public ResponseEntity cancelProcessInstance(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return poutSideAuthServiceFacade.cancelProcessInstance(headMap, bodyMap);
	}

	/**
	 * 根据条件，查询授权历史信息列表(已办事宜列表查询)
	 */
	@Validation(value="c0000621")
	@Override
	public ResponseEntity queryAuthHistoryList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return poutSideAuthServiceFacade.queryAuthHistoryList(headMap, bodyMap);
	}
	
	/**
	 * 显示流程图生成，上传到ftp服务器
	 */
	@Validation(value="c0000624")
	@Override
	public ResponseEntity addAuthProcessPhotoToFtpServer(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return poutSideAuthServiceFacade.addAuthProcessPhotoToFtpServer(headMap, bodyMap);
	}

	/**
	 * 查询授权流程节点坐标和明细信息
	 */
	@Validation(value="c0000625")
	@Override
	public ResponseEntity queryAuthProcessNodeDetail(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return poutSideAuthServiceFacade.queryAuthProcessNodeDetail(headMap, bodyMap);
	}
}
