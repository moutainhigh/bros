package bros.provider.parent.custmanage.authorize.service;

import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;


/**
 * 
 * @ClassName: ITtpAuthModelRuleGpEntitySerivce 
 * @Description: 对客授权模型规则实体服务接口
 * @author pengxiangnan 
 * @date 2016年7月19日 下午3:41:43 
 * @version 1.0
 */
public interface ITtpAuthModelRuleGpEntitySerivce {
	
	/**
	 * 
	 * @Title: saveTtpAuthModelRuleGp 
	 * @Description: 保存授权模型规则
	 * @param parmINList 授权模型规则列表
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int saveTtpAuthModelRuleGp(List<Map<String,Object>> parmINList) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteTtpAuthModelRuleGp 
	 * @Description: 删除授权模型规则
	 * @param authorizeId 授权模型编号
	 * @return int 影响记录数
	 * @throws ServiceException
	 */
	public int deleteTtpAuthModelRuleGp(String authorizeId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTtpAuthModelRuleByAuthModelId 
	 * @Description: 根据授权模型ID查询授权规则
	 * @param authorizeId 授权模型编号
	 * @return List<Map<String, Object>> 授权规则列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpAuthModelRuleByAuthModelId (String authorizeId) throws ServiceException;
	
	/**
	 * 
	 * @Title: queryTtpAuthModelRuleByAuthModelIdAndAmount 
	 * @Description: 根据授权模型编号和金额查询授权规则
	 * @param authorizeId 授权模型编号
	 * @param amount 金额
	 * @return List<Map<String, Object>> 授权规则列表
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> queryTtpAuthModelRuleByAuthModelIdAndAmount(String authorizeId, String amount) throws ServiceException;
}
