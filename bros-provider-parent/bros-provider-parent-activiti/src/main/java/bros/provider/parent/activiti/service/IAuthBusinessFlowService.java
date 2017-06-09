package bros.provider.parent.activiti.service;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IAuthBusinessFlowService 
 * @Description: 授权业务指令流水服务接口
 * @author weiyancheng
 * @date 2016年7月29日 下午1:57:54 
 * @version 1.0 
 */
public interface IAuthBusinessFlowService {

	/**
	 * @Title: updateFlowBatchNoAndStt 
	 * @Description: 根据指令流水号更新流水批次号或者状态
	 * @param bsnCode 业务编号
	 * @param flowSeq 流水号
	 * @param batchNo 批次号
	 * @param stt 状态
	 * @throws ServiceException
	 */
	public void updateFlowBatchNoAndStt(String bsnCode,String flowSeq,String batchNo,String stt,String channel)throws ServiceException;
}
