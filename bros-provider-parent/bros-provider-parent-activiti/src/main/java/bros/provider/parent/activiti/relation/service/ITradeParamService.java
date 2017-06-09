package bros.provider.parent.activiti.relation.service;

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: ITradeParamService 
 * @Description: 工作流回调服务所需数据处理服务接口
 * @author weiyancheng
 * @date 2016年7月18日 上午10:52:23 
 * @version 1.0 
 */
public interface ITradeParamService {
	/**
	 * @Title: qryTradeParam 
	 * @Description: 查询工作流服务回调参数
	 * @param businessKey
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryTradeParam(String businessKey)throws ServiceException;
	/**
	 * @Title: deleteTradeParam 
	 * @Description: 删除工作流服务回调参数
	 * @param businessKey
	 * @throws ServiceException
	 */
	public void deleteTradeParam(String businessKey)throws ServiceException;
	/**
	 * @Title: insertTradeParam 
	 * @Description: 插入工作流服务回调参数
	 * @param businessKey
	 * @param bizData
	 * @throws ServiceException
	 */
	public void insertTradeParam(Map<String, Object> param)throws ServiceException;
	/**
	 * @Title: insertTradeParam 
	 * @Description: 插入工作流服务回调参数(用于拦截器中)
	 * @param businessKey
	 * @param bizData
	 * @throws ServiceException
	 */
	public void insertCallBackServiceData(MethodInvocation invocation)throws ServiceException;

}
