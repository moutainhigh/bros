package bros.provider.parent.custmanage.authorize.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import bros.common.core.exception.ServiceException;


/**
 * 
 * @ClassName: ITtpAuthModelSerivce 
 * @Description: 对客授权模型组合服务接口
 * @author pengxiangnan 
 * @date 2016年7月20日 上午9:48:44 
 * @version 1.0
 */
public interface ITtpAuthModelSerivce {
	
	/**
	 * 
	 * @Title: saveAuthorizationModel 
	 * @Description: 新增授权模型
	 * @param authModelMap 授权模型集合
	 * @param authRuleList 授权规则定义列表
	 * @throws ServiceException
	 */
	public void saveAuthorizationModel(Map<String, Object> authModelMap, List<Map<String,Object>> authRuleList) throws ServiceException;
	
	/**
	 * 
	 * @Title: deleteAuthorizationModel 
	 * @Description: 删除授权模型
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一标识
	 * @param cstNo 客户编号
	 * @param authorizeId 授权模型编号
	 * @throws ServiceException
	 */
	public void deleteAuthorizationModel(String channel, String legalId , String cstNo, String authorizeId) throws ServiceException;
	
	/**
	 * 
	 * @Title: updateAuthorizationModel 
	 * @Description: 修改授权模型
	 * @param authModelMap 授权模型集合
	 * @param authRuleList 授权规则定义列表
	 * @throws ServiceException
	 */
	public void updateAuthorizationModel(Map<String, Object> authModelMap, List<Map<String,Object>> authRuleList) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAuthorizeModelRule 
	 * @Description:检查规则数据及组装数据
	 * @param authRuleList 授权规则定义列表
	 * @param moneyType 是否有金额（0：无金额，1：有金额）
	 * @return Map<String, Object> 返回结果集
	 * @throws ServiceException
	 */
	public Map<String, Object>  checkAuthorizeModelRule(List<Map<String,Object>> authRuleList, String moneyType, 
    		 String authorizeId)  throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAmountRange 
	 * @Description: 校验金额区间的完整性 
	 * @param moneyType 是否有金额
	 * @param amountMap 金额区间集合
	 * @throws ServiceException
	 */
	public void checkAmountRange(String moneyType, Map<Integer, Map<String, BigDecimal>> amountMap) throws ServiceException;
	
	/**
	 * 
	 * @Title: checkAuthModelIsExist 
	 * @Description: 检查授权模型是否存在
	 * @param channel 渠道编号
	 * @param legalId 法人记录唯一编号
	 * @param cstNo 客户编号
	 * @param authModelName 授权模型名称
	 * @throws ServiceException
	 */
	public void checkAuthModelIsExist(String channel, String legalId, String cstNo, String authModelName) throws ServiceException;
}
