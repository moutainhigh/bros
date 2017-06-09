
package bros.provider.parent.activiti.authmode.service;

import java.util.Map;

import bros.common.core.exception.ServiceException;

/** 
 * @ClassName: IQryInnerAuthModelService 
 * @Description: 授权模型查询服务
 * @author weiyancheng
 * @date 2016年7月11日 下午4:37:15 
 * @version 1.0 
 */
public interface IQryInnerAuthModelService {

	/**
	 * @Title: qryAuthModel 
	 * @Description: 根据授权模型查询内部授权模型
	 * @param authModelFk 授权模型编号
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryAuthModel(String authModelFk) throws ServiceException;
	/**
	 * @Title: qryAuthRule 
	 * @Description: 根据授权模型id（或者授权模型id+金额）查询授权规则
	 * @param authModelFk 授权模型id
	 * @param tranAmt 金额
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> qryAuthRule(String authModelFk,String tranAmt) throws ServiceException;
}
