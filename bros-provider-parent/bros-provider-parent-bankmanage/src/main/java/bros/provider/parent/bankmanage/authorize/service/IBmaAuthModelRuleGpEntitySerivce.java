package bros.provider.parent.bankmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;

/**
 * 
 * @ClassName: IBmaAuthModelRuleGpEntitySerivce 
 * @Description: 内部授权模型规则实体服务接口
 * @author pengxiangnan 
 * @date 2016年7月18日 上午10:33:53 
 * @version 1.0
 */
public interface IBmaAuthModelRuleGpEntitySerivce {
	
	/**
	 * 
	 * @Title: saveBmaAuthModelRuleGp 
	 * @Description: 保存授权模型规则
	 * @param parmINList 授权模型规则列表
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int saveBmaAuthModelRuleGp(List<Map<String, Object>> parmINList) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteBmaAuthModelRuleGp 
	 * @Description: 删除授权模型规则
	 * @param authorizeId 授权模型编号
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int deleteBmaAuthModelRuleGp(String authorizeId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaAuthModelRuleByAuthModelIdAndAmount 
	 * @Description: 根据授权模型编号和金额查询授权规则
	 * @param authorizeId 授权模型编号
	 * @param amount 金额
	 * @return List<Map<String, Object>> 授权规则列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaAuthModelRuleByAuthModelIdAndAmount(String authorizeId, String amount) 
			throws ServiceException;
	
	/**
	 * 
	 * @Title: queryBmaAuthModelRuleByAuthModelId 
	 * @Description: 根据授权模型编号查询授权规则定义列表
	 * @param authorizeId 授权模型编号
	 * @return List<Map<String, Object>> 授权规则定义列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryBmaAuthModelRuleByAuthModelId (String authorizeId) throws ServiceException;
}
