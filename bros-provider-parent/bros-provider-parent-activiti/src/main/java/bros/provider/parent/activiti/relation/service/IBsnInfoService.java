package bros.provider.parent.activiti.relation.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IBsnInfoService 
 * @Description: 业务信息服务接口
 * @author weiyancheng
 * @date 2016年8月3日 上午9:22:59 
 * @version 1.0 
 */
public interface IBsnInfoService {

	/**
	 * @Title: qryBsnInfo 
	 * @Description: 根据渠道+业务编码查询业务授权配置
	 * @param channel 渠道
	 * @param bsnCode 业务编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryBsnInfo(String channel,String bsnCode)throws ServiceException;
}
