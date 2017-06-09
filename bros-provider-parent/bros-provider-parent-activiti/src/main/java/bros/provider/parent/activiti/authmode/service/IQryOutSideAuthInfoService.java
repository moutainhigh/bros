package bros.provider.parent.activiti.authmode.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IQryOutSideAuthInfoService 
 * @Description: 外部授权用：查询授权配置信息服务接口
 * @author weiyancheng
 * @date 2016年7月27日 下午2:17:12 
 * @version 1.0 
 */
public interface IQryOutSideAuthInfoService {

	/**
	 * @Title: qryAuthInfo 
	 * @Description: 根据法人+客户号+业务编码查询业务授权配置
	 * @param legalId 法人ID
	 * @param channel 客户号
	 * @param bsnCode 业务编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryAuthInfo(String legalId,String cstNo,String bsnCode)throws ServiceException;
	
	/**
	 * @Title: qryAuthModel 
	 * @Description: 根据授权模型ID查询业务授权配置
	 * @param authModelFk 授权模型ID
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryAuthModel(String authModelFk)throws ServiceException;
	/**
	 * @Title: qryAuthModel 
	 * @Description: 根据授权模型ID或者+交易金额查询授权规则
	 * @param authModelFk 授权模型ID
	 * @param tranAmt 金额
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryAuthRule(String authModelFk,String tranAmt)throws ServiceException;
}
