
package bros.provider.parent.activiti.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IAuthProcessorService 
 * @Description: 授权处理器接口
 * @author weiyancheng
 * @date 2016年7月11日 下午1:46:31 
 * @version 1.0 
 */
public interface IAuthProcessorService {
	/**
	 * @Title: authProcess 
	 * @Description: 流程处理方法
	 * @param param
	 * @return true:需要授权，false:无需授权
	 * @throws ServiceException
	 */
	/**
	 * @Title: authProcess 
	 * @Description: 流程处理方法
	 * @param varialable 组装的流程变量map
	 * @param headMap 服务上送的headMap
	 * @param bodyMap 服务上送的bodyMap
	 * @param resultMap 组装要返给渠道应用的数据
	 * @param activitiMap 组装流程启动时用到的数据
	 * @return
	 * @throws ServiceException
	 */
	public boolean authProcess(Map<String, Object> varialable,Map<String, Object> headMap,Map<String, Object> bodyMap,Map<String, Object> resultMap,Map<String, Object> activitiMap) throws ServiceException;

}
