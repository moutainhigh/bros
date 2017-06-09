package bros.provider.parent.activiti.authmode.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IQryInnerAuthInfoService 
 * @Description: 查询内部授权功能配置信息
 * @author weiyancheng
 * @date 2016年7月11日 下午5:27:01 
 * @version 1.0 
 */
public interface IQryInnerAuthInfoService {

	/**
	 * @Title: qryAuthInfo 
	 * @Description: 根据渠道法人业务编码查询业务授权配置
	 * @param legalId 法人ID
	 * @param channel 渠道
	 * @param bsnCode 业务编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryAuthInfo(String legalId,String channel,String bsnCode)throws ServiceException;
}
