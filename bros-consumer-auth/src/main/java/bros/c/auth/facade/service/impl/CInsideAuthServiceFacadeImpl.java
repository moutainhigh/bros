package bros.c.auth.facade.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bros.c.auth.facade.service.ICInsideAuthServiceFacade;
import bros.common.core.annotation.Validation;
import bros.common.core.entity.ResponseEntity;
import bros.common.core.exception.ServiceException;
import bros.p.auth.facade.service.IPInsideAuthServiceFacade;

/**
 * 
 * @ClassName: CInsideAuthServiceFacadeImpl 
 * @Description: 内部授权服务接口实现
 * @author 何鹏
 * @date 2016年9月7日 上午11:15:51 
 * @version 1.0
 */
@Component("cinsideAuthServiceFacade")
public class CInsideAuthServiceFacadeImpl implements ICInsideAuthServiceFacade {

	@Autowired
	private IPInsideAuthServiceFacade pinsideAuthServiceFacade;
	
	/**
	 * 交易类型待授权指令概览统计
	 */
	@Override
	public ResponseEntity qryTaskCenterView(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.qryTaskCenterView(headMap, bodyMap);
	}

	/**
	 * 业务编码待授权指令概览统计
	 */
	@Override
	public ResponseEntity qryTaskCenterViewByBsnType(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pinsideAuthServiceFacade.qryTaskCenterViewByBsnType(headMap, bodyMap);
	}

	/**
	 * 查询待授权指令列表
	 */
	@Validation(value="c0000601")
	@Override
	public ResponseEntity qryAuthQueueList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.qryAuthQueueList(headMap, bodyMap);
	}

	/**
	 * 签收任务退回（暂时未使用）
	 */
	@Validation(value="c0000602")
	@Override
	public ResponseEntity unClaimTaskJob(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.unClaimTaskJob(headMap, bodyMap);
	}

	/**
	 * 单笔授权（临柜）
	 */
	@Validation(value="c0000603")
	@Override
	public ResponseEntity singleCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.singleCompleteTask(headMap, bodyMap);
	}

	/**
	 * 单笔授权（审核式）
	 */
	@Validation(value="c0000604")
	@Override
	public ResponseEntity authSingleCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.authSingleCompleteTask(headMap, bodyMap);
	}

	/**
	 * 批量授权
	 */
	@Validation(value="c0000605")
	@Override
	public ResponseEntity batchCompleteTask(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.batchCompleteTask(headMap, bodyMap);
	}

	/**
	 * 查询指令详情列表（用于凭证打印））
	 */
	@Validation(value="c0000606")
	@Override
	public ResponseEntity qryOrderDetailListByBatchNo(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pinsideAuthServiceFacade.qryOrderDetailListByBatchNo(headMap, bodyMap);
	}

	/**
	 * 根据业务流水号（businessKey）查询审批意见历史信息
	 */
	@Validation(value="c0000607")
	@Override
	public ResponseEntity qryCommentListByTaskId(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.qryCommentListByTaskId(headMap, bodyMap);
	}

	/**
	 * 根据业务流水号（指令流水号）+业务编号查询授权详情页面展示数据
	 */
	@Validation(value="c0000608")
	@Override
	public ResponseEntity qryAuthDetailPageDataByBusinessKey(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pinsideAuthServiceFacade.qryAuthDetailPageDataByBusinessKey(headMap, bodyMap);
	}

	/**
	 * 根据法人记录唯一标识+任务提交柜员ID查询可撤销流程实例列表
	 */
	@Validation(value="c0000609")
	@Override
	public ResponseEntity queryCancelProcessInstanceList(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pinsideAuthServiceFacade.queryCancelProcessInstanceList(headMap, bodyMap);
	}

	/**
	 * 根据法人记录唯一标识+任务提交柜员ID+30位全局流水号撤销授权申请
	 */
	@Validation(value="c0000610")
	@Override
	public ResponseEntity cancelProcessInstance(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.cancelProcessInstance(headMap, bodyMap);
	}

	/**
	 * 根据条件，查询授权历史信息列表(已办事宜列表查询)
	 */
	@Validation(value="c0000611")
	@Override
	public ResponseEntity queryAuthHistoryList(Map<String, Object> headMap,
			Map<String, Object> bodyMap) throws ServiceException {
		return pinsideAuthServiceFacade.queryAuthHistoryList(headMap, bodyMap);
	}

	/**
	 * 显示流程图生成，上传到ftp服务器
	 */
	@Validation(value="c0000622")
	@Override
	public ResponseEntity addAuthProcessPhotoToFtpServer(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pinsideAuthServiceFacade.addAuthProcessPhotoToFtpServer(headMap, bodyMap);
	}

	/**
	 * 查询授权流程节点坐标和明细信息
	 */
	@Validation(value="c0000623")
	@Override
	public ResponseEntity queryAuthProcessNodeDetail(
			Map<String, Object> headMap, Map<String, Object> bodyMap)
			throws ServiceException {
		return pinsideAuthServiceFacade.queryAuthProcessNodeDetail(headMap, bodyMap);
	}
	
}
