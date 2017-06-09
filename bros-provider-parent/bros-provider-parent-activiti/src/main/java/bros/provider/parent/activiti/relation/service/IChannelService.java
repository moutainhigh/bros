
package bros.provider.parent.activiti.relation.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IChannelService 
 * @Description: 查询渠道信息服务
 * @author weiyancheng
 * @date 2016年7月22日 上午11:36:11 
 * @version 1.0 
 */
public interface IChannelService {
	
	/** 
	 * @Title: qryChannelFlag 
	 * @Description: 根据渠道编号查询渠道标记
	 * @param channel 渠道编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryChannelFlag(String channel) throws ServiceException;

}
